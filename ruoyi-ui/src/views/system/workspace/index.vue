<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工作空间名称" prop="workspaceName">
        <el-input
          v-model="queryParams.workspaceName"
          placeholder="请输入工作空间名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工作空间类型" prop="workspaceType">
        <el-select v-model="queryParams.workspaceType" placeholder="请选择工作空间类型" clearable>
          <el-option label="个人空间" value="0" />
          <el-option label="团队空间" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:workspace:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple" 
          @click="handleDelete"
          v-hasPermi="['system:workspace:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:workspace:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="workspaceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工作空间ID" align="center" prop="workspaceId" width="180" />
      <el-table-column label="工作空间名称" align="center" prop="workspaceName" width="180" :show-overflow-tooltip="true" />
      <el-table-column label="工作空间类型" align="center" prop="workspaceType" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.workspaceType === 0 ? 'primary' : 'success'" size="small">
            {{ scope.row.workspaceType === 0 ? '个人空间' : '团队空间' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="所有者ID" align="center" prop="ownerUid" width="100" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:workspace:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:workspace:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改工作空间对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="工作空间ID" prop="workspaceId">
          <el-input v-model="form.workspaceId" placeholder="请输入工作空间ID" />
        </el-form-item>
        <el-form-item label="工作空间名称" prop="workspaceName">
          <el-input v-model="form.workspaceName" placeholder="请输入工作空间名称" />
        </el-form-item>
        <el-form-item label="工作空间类型" prop="workspaceType">
          <el-select v-model="form.workspaceType" placeholder="请选择工作空间类型">
            <el-option label="个人空间" value="0" />
            <el-option label="团队空间" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="所有者ID" prop="ownerUid">
          <el-input v-model="form.ownerUid" placeholder="请输入所有者ID" />
        </el-form-item>
        <el-form-item label="数据库存储ID" prop="databaseStorageId">
          <el-input v-model="form.databaseStorageId" placeholder="请输入数据库存储ID" />
        </el-form-item>
        <el-form-item label="工作空间图标" prop="icon">
          <el-input v-model="form.icon" placeholder="请输入工作空间图标" />
        </el-form-item>
        <el-form-item label="是否初始化" prop="isInitialized">
          <el-select v-model="form.isInitialized" placeholder="请选择是否初始化">
            <el-option label="未初始化" value="0" />
            <el-option label="已初始化" value="1" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listWorkspace, getWorkspace, delWorkspace, addWorkspace, updateWorkspace, exportWorkspace } from '@/api/system/workspace'

export default {
  name: 'Workspace',
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 工作空间表格数据
      workspaceList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        workspaceName: undefined,
        workspaceType: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        workspaceId: [
          { required: true, message: '工作空间ID不能为空', trigger: 'blur' }
        ],
        workspaceName: [
          { required: true, message: '工作空间名称不能为空', trigger: 'blur' }
        ],
        workspaceType: [
          { required: true, message: '工作空间类型不能为空', trigger: 'blur' }
        ],
        ownerUid: [
          { required: true, message: '所有者ID不能为空', trigger: 'blur' }
        ],
        databaseStorageId: [
          { required: true, message: '数据库存储ID不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询工作空间列表 */
    getList() {
      this.loading = true
      listWorkspace(this.queryParams).then(response => {
        this.workspaceList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        workspaceId: undefined,
        databaseStorageId: undefined,
        ownerUid: undefined,
        workspaceType: 0,
        workspaceName: undefined,
        icon: undefined,
        settings: undefined,
        isInitialized: 0,
        defaultPublishedViewId: undefined
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加工作空间'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const workspaceId = row.workspaceId || this.ids
      getWorkspace(workspaceId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改工作空间'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.workspaceId != undefined) {
            updateWorkspace(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addWorkspace(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const workspaceIds = row.workspaceId || this.ids
      this.$modal.confirm('是否确认删除工作空间编号为"' + workspaceIds + '"的数据项?').then(function() {
        return delWorkspace(workspaceIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/workspace/export', { 
        ...this.queryParams
      }, `workspace_${new Date().getTime()}.xlsx`)
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.workspaceId)
      this.multiple = !selection.length
    }
  }
}
</script>

<style scoped>

</style>