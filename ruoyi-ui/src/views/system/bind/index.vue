<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关联登录概况ID" prop="profileId">
        <el-input
          v-model="queryParams.profileId"
          placeholder="请输入关联登录概况ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="第三方平台名称" prop="thirdPlatform">
        <el-input
          v-model="queryParams.thirdPlatform"
          placeholder="请输入第三方平台名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户第三方标识" prop="thirdUserId">
        <el-input
          v-model="queryParams.thirdUserId"
          placeholder="请输入用户第三方标识"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="最后登陆时间" prop="lastLoginTime">
        <el-date-picker clearable
          v-model="queryParams.lastLoginTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择最后登陆时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input
          v-model="queryParams.nickname"
          placeholder="请输入昵称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-input
          v-model="queryParams.gender"
          placeholder="请输入性别"
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
          v-hasPermi="['system:bind:add']"
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
          v-hasPermi="['system:bind:edit']"
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
          v-hasPermi="['system:bind:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:bind:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bindList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="绑定ID" align="center" prop="bindId" />
      <el-table-column label="关联登录概况ID" align="center" prop="profileId" />
      <el-table-column label="第三方平台名称" align="center" prop="thirdPlatform" />
      <el-table-column label="用户第三方标识" align="center" prop="thirdUserId" />
      <el-table-column label="最后登陆时间" align="center" prop="lastLoginTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastLoginTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="昵称" align="center" prop="nickname" />
      <el-table-column label="性别" align="center" prop="gender" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:bind:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:bind:remove']"
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

    <!-- 添加或修改第三方登录绑定对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="关联登录概况ID" prop="profileId">
          <el-input v-model="form.profileId" placeholder="请输入关联登录概况ID" />
        </el-form-item>
        <el-form-item label="第三方平台名称" prop="thirdPlatform">
          <el-input v-model="form.thirdPlatform" placeholder="请输入第三方平台名称" />
        </el-form-item>
        <el-form-item label="用户第三方标识" prop="thirdUserId">
          <el-input v-model="form.thirdUserId" placeholder="请输入用户第三方标识" />
        </el-form-item>
        <el-form-item label="最后登陆时间" prop="lastLoginTime">
          <el-date-picker clearable
            v-model="form.lastLoginTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择最后登陆时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="form.nickname" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-input v-model="form.gender" placeholder="请输入性别" />
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
import { listBind, getBind, delBind, addBind, updateBind } from "@/api/system/bind"

export default {
  name: "Bind",
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
      // 第三方登录绑定表格数据
      bindList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        profileId: null,
        thirdPlatform: null,
        thirdUserId: null,
        lastLoginTime: null,
        nickname: null,
        gender: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        profileId: [
          { required: true, message: "关联登录概况ID不能为空", trigger: "blur" }
        ],
        thirdPlatform: [
          { required: true, message: "第三方平台名称不能为空", trigger: "blur" }
        ],
        thirdUserId: [
          { required: true, message: "用户第三方标识不能为空", trigger: "blur" }
        ],
        lastLoginTime: [
          { required: true, message: "最后登陆时间不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询第三方登录绑定列表 */
    getList() {
      this.loading = true
      listBind(this.queryParams).then(response => {
        this.bindList = response.rows
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
        bindId: null,
        profileId: null,
        thirdPlatform: null,
        thirdUserId: null,
        lastLoginTime: null,
        nickname: null,
        gender: null
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
      this.ids = selection.map(item => item.bindId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加第三方登录绑定"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const bindId = row.bindId || this.ids
      getBind(bindId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改第三方登录绑定"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.bindId != null) {
            updateBind(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addBind(this.form).then(response => {
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
      const bindIds = row.bindId || this.ids
      this.$modal.confirm('是否确认删除第三方登录绑定编号为"' + bindIds + '"的数据项？').then(function() {
        return delBind(bindIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/bind/export', {
        ...this.queryParams
      }, `bind_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
