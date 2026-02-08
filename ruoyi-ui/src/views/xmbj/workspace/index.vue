<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工作空间类型" prop="workspaceType">
        <el-select v-model="queryParams.workspaceType" placeholder="请选择工作空间类型" clearable>
          <el-option label="个人空间" :value="0" />
          <el-option label="团队空间" :value="1" />
        </el-select>
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
        <el-select v-model="queryParams.isInitialized" placeholder="请选择是否已初始化" clearable>
          <el-option label="未初始化" :value="0" />
          <el-option label="已初始化" :value="1" />
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
          v-hasPermi="['xmbj:workspace:add']"
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
          v-hasPermi="['xmbj:workspace:edit']"
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
          v-hasPermi="['xmbj:workspace:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['xmbj:workspace:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="workspaceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工作空间ID" align="center" prop="workspaceId" width="180" />
      <el-table-column label="数据库存储ID" align="center" prop="databaseStorageId" width="180" />
      <el-table-column label="工作空间所有者" align="center" prop="ownerName" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="工作空间类型" align="center" prop="workspaceType">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.workspaceType === 0" type="info">个人空间</el-tag>
          <el-tag v-else-if="scope.row.workspaceType === 1" type="success">团队空间</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="工作空间名称" align="center" prop="workspaceName" />
      <el-table-column label="工作空间图标" align="center" prop="icon" width="100">
        <template slot-scope="scope">
          <el-image v-if="scope.row.icon" :src="scope.row.icon" style="width: 40px; height: 40px" fit="cover" />
        </template>
      </el-table-column>
      <el-table-column label="初始化状态" align="center" prop="isInitialized">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isInitialized === 0" type="info">未初始化</el-tag>
          <el-tag v-else-if="scope.row.isInitialized === 1" type="success">已初始化</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="默认发布视图ID" align="center" prop="defaultPublishedViewId" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="240">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['xmbj:workspace:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            @click="handleViewNote(scope.row)"
            v-hasPermi="['xmbj:note:list']"
          >快速笔记</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['xmbj:workspace:remove']"
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

    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="工作空间ID" prop="workspaceId">
          <el-input v-model="form.workspaceId" placeholder="请输入工作空间ID" disabled />
        </el-form-item>
        <el-form-item label="数据库存储ID" prop="databaseStorageId">
          <el-input v-model="form.databaseStorageId" placeholder="请输入数据库存储ID" />
        </el-form-item>
        <el-form-item label="工作空间所有者用户ID" prop="ownerUid">
          <el-input v-model="form.ownerUid" placeholder="请输入工作空间所有者用户ID" />
        </el-form-item>
        <el-form-item label="工作空间类型" prop="workspaceType">
          <el-radio-group v-model="form.workspaceType">
            <el-radio :label="0">个人空间</el-radio>
            <el-radio :label="1">团队空间</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="工作空间名称" prop="workspaceName">
          <el-input v-model="form.workspaceName" placeholder="请输入工作空间名称" />
        </el-form-item>
        <el-form-item label="工作空间图标" prop="icon">
          <el-input v-model="form.icon" type="textarea" placeholder="请输入工作空间图标URL" />
        </el-form-item>
        <el-form-item label="工作空间配置" prop="settings">
          <el-input v-model="form.settings" type="textarea" placeholder="请输入工作空间配置（JSON格式）" />
        </el-form-item>
        <el-form-item label="是否已初始化" prop="isInitialized">
          <el-radio-group v-model="form.isInitialized">
            <el-radio :label="0">未初始化</el-radio>
            <el-radio :label="1">已初始化</el-radio>
          </el-radio-group>
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
import { listWorkspace, getWorkspace, delWorkspace, addWorkspace, updateWorkspace } from "@/api/xmbj/workspace"

export default {
  name: "Workspace",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      workspaceList: [],
      title: "",
      open: false,
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
      form: {},
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
        workspaceName: [
          { required: true, message: "工作空间名称不能为空", trigger: "blur" }
        ],
        icon: [
          { required: true, message: "工作空间图标不能为空", trigger: "blur" }
        ],
      },
      isInitializedOptions: [
        { label: "未初始化", value: 0 },
        { label: "已初始化", value: 1 }
      ]
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listWorkspace(this.queryParams).then(response => {
        this.workspaceList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        workspaceId: null,
        databaseStorageId: null,
        ownerUid: null,
        createdAt: null,
        workspaceType: 0,
        deletedAt: null,
        workspaceName: null,
        icon: null,
        settings: null,
        isInitialized: 0,
        defaultPublishedViewId: null
      }
      this.resetForm("form")
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.workspaceId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加工作空间"
    },
    handleUpdate(row) {
      this.reset()
      const workspaceId = row.workspaceId || this.ids
      getWorkspace(workspaceId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改工作空间"
      })
    },
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
    handleDelete(row) {
      const workspaceIds = row.workspaceId || this.ids
      this.$modal.confirm('是否确认删除工作空间编号为"' + workspaceIds + '"的数据项？').then(function() {
        return delWorkspace(workspaceIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    handleViewNote(row) {
      const workspaceId = row.workspaceId
      this.$router.push({
        path: '/note',
        query: {
          workspaceId: workspaceId
        }
      })
    },
    handleExport() {
      this.download('xmbj/workspace/export', {
        ...this.queryParams
      }, `workspace_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
