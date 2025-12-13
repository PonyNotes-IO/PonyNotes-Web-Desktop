<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="数据库存储ID" prop="databaseStorageId">
        <el-input
          v-model="queryParams.databaseStorageId"
          placeholder="请输入数据库存储ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工作空间所有者用户ID" prop="ownerUid">
        <el-input
          v-model="queryParams.ownerUid"
          placeholder="请输入工作空间所有者用户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createdAt">
        <el-date-picker clearable
          v-model="queryParams.createdAt"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="软删除时间" prop="deletedAt">
        <el-date-picker clearable
          v-model="queryParams.deletedAt"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择软删除时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="工作空间名称" prop="workspaceName">
        <el-input
          v-model="queryParams.workspaceName"
          placeholder="请输入工作空间名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否已初始化" prop="isInitialized">
        <el-input
          v-model="queryParams.isInitialized"
          placeholder="请输入是否已初始化"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="默认发布视图ID" prop="defaultPublishedViewId">
        <el-input
          v-model="queryParams.defaultPublishedViewId"
          placeholder="请输入默认发布视图ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:workspace:edit']"
        >修改</el-button>
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
      <el-table-column label="工作空间ID" align="center" prop="workspaceId" />
      <el-table-column label="数据库存储ID" align="center" prop="databaseStorageId" />
      <el-table-column label="工作空间所有者用户ID" align="center" prop="ownerUid" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="工作空间类型" align="center" prop="workspaceType" />
      <el-table-column label="软删除时间" align="center" prop="deletedAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deletedAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="工作空间名称" align="center" prop="workspaceName" />
      <el-table-column label="工作空间图标" align="center" prop="icon" />
      <el-table-column label="工作空间配置" align="center" prop="settings" />
      <el-table-column label="是否已初始化" align="center" prop="isInitialized" />
      <el-table-column label="默认发布视图ID" align="center" prop="defaultPublishedViewId" />
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
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改工作空间对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="数据库存储ID" prop="databaseStorageId">
          <el-input v-model="form.databaseStorageId" placeholder="请输入数据库存储ID" />
        </el-form-item>
        <el-form-item label="工作空间所有者用户ID" prop="ownerUid">
          <el-input v-model="form.ownerUid" placeholder="请输入工作空间所有者用户ID" />
        </el-form-item>
        <el-form-item label="创建时间" prop="createdAt">
          <el-date-picker clearable
            v-model="form.createdAt"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="软删除时间" prop="deletedAt">
          <el-date-picker clearable
            v-model="form.deletedAt"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择软删除时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="工作空间名称" prop="workspaceName">
          <el-input v-model="form.workspaceName" placeholder="请输入工作空间名称" />
        </el-form-item>
        <el-form-item label="工作空间图标" prop="icon">
          <el-input v-model="form.icon" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="工作空间配置" prop="settings">
          <el-input v-model="form.settings" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="是否已初始化" prop="isInitialized">
          <el-input v-model="form.isInitialized" placeholder="请输入是否已初始化" />
        </el-form-item>
        <el-form-item label="默认发布视图ID" prop="defaultPublishedViewId">
          <el-input v-model="form.defaultPublishedViewId" placeholder="请输入默认发布视图ID" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listWorkspace, getWorkspace, delWorkspace, addWorkspace, updateWorkspace } from "@/api/system/workspace"

export default {
  name: "Workspace",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 工作空间表格数据
      workspaceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        databaseStorageId: null,
        ownerUid: null,
        createdAt: null,
        workspaceType: null,
        deletedAt: null,
        workspaceName: null,
        icon: null,
        settings: null,
        isInitialized: null,
        defaultPublishedViewId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        databaseStorageId: [
          { required: true, message: "数据库存储ID不能为空", trigger: "blur" }
        ],
        ownerUid: [
          { required: true, message: "工作空间所有者用户ID不能为空", trigger: "blur" }
        ],
        workspaceType: [
          { required: true, message: "工作空间类型不能为空", trigger: "change" }
        ],
        icon: [
          { required: true, message: "工作空间图标不能为空", trigger: "blur" }
        ],
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
        workspaceId: null,
        databaseStorageId: null,
        ownerUid: null,
        createdAt: null,
        workspaceType: null,
        deletedAt: null,
        workspaceName: null,
        icon: null,
        settings: null,
        isInitialized: null,
        defaultPublishedViewId: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.workspaceId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加工作空间"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const workspaceId = row.workspaceId || this.ids
      getWorkspace(workspaceId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改工作空间"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.workspaceId != null) {
            updateWorkspace(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addWorkspace(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
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
      this.$modal.confirm('是否确认删除工作空间编号为"' + workspaceIds + '"的数据项？').then(function() {
        return delWorkspace(workspaceIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/workspace/export', {
        ...this.queryParams
      }, `workspace_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
