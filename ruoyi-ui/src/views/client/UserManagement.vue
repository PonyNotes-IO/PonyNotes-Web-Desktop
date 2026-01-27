<template>
  <div class="app-container">
    <!-- 条件查询区域：字段匹配ClientUser实体 -->
    <el-form :inline="true" :model="queryParams" class="query-form" @keyup.enter="handleQuery">
      <el-form-item label="手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="输入手机号查询用户"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input
          v-model="queryParams.email"
          placeholder="输入邮箱查询用户"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="输入用户名查询用户"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <div class="tool-bar">
      <el-button type="primary" icon="el-icon-plus" @click="handleAdd">添加用户</el-button>
      <el-button type="danger" icon="el-icon-delete" @click="handleBatchDelete" v-if="selectedIds.length > 0">批量删除</el-button>
      <el-button type="warning" icon="el-icon-lock" @click="handleBatchStop" v-if="selectedIds.length > 0">批量停用</el-button>
    </div>

    <!-- 用户列表：字段与ClientUser实体完全一致 -->
    <el-table
      v-loading="loading"
      :data="tableData"
      @selection-change="handleSelectionChange"
      border
      stripe
      :default-sort="{prop: 'createdAt', order: 'descending'}"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="60" align="center" />
      <el-table-column label="用户ID" prop="uid" align="center" width="80" />
      <el-table-column label="用户UUID" prop="uuid" align="center" />
      <el-table-column label="用户名" prop="name" align="center" />
      <el-table-column label="手机号" prop="phone" align="center" />
      <el-table-column label="邮箱" prop="email" align="center" />
      <el-table-column label="加密签名" prop="encryptionSign" align="center" />
      <el-table-column label="创建时间" prop="createdAt" align="center" width="200">
        <template slot-scope="scope">
          {{ scope.row.createdAt ? scope.row.createdAt : '-' }}
        </template>
      </el-table-column>
      <el-table-column label="更新时间" prop="updatedAt" align="center" width="200">
        <template slot-scope="scope">
          {{ scope.row.updatedAt ? scope.row.updatedAt : '-' }}
        </template>
      </el-table-column>
<<<<<<< HEAD
      <el-table-column label="状态" prop="deletedAt" align="center" width="200">
        <template slot-scope="scope">
          {{ scope.row.deletedAt ? "停用" : "正常" }}
        </template>
      </el-table-column>
=======
>>>>>>> 694369914507b779589b6d0d7034a90c7c321bb3
      <el-table-column label="删除时间" prop="deletedAt" align="center" width="200">
        <template slot-scope="scope">
          {{ scope.row.deletedAt ? scope.row.deletedAt : '-' }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="300">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleView(scope.row)">查看</el-button>
          <el-button size="mini" type="success" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row.uid)">删除</el-button>
          <el-button size="mini" type="warning" @click="handleStop(scope.row.uid)">停用</el-button>
          <el-button size="mini" type="info" @click="handleAllSpace(scope.row.uid)">查看工作区</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件：匹配Ruoyi分页规范 -->
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 新增/编辑弹窗：字段与ClientUser实体完全一致 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="600px" append-to-body>
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px">
        <el-form-item label="用户ID" prop="uid">
          <el-input v-model="formData.uid" placeholder="系统生成" disabled />
        </el-form-item>
        <el-form-item label="用户UUID" prop="uuid">
          <el-input v-model="formData.uuid" placeholder="系统生成" disabled />
        </el-form-item>
        <el-form-item label="用户名" prop="name" required>
          <el-input v-model="formData.name" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="formData.phone" placeholder="请输入手机号" maxlength="11" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="formData.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="formData.password" type="password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="加密签名" prop="encryptionSign">
          <el-input v-model="formData.encryptionSign" placeholder="请输入加密签名" />
        </el-form-item>
        <el-form-item label="用户元数据" prop="metadata">
          <el-input v-model="formData.metadata" type="textarea" placeholder="请输入JSON格式元数据" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// 引入匹配后端的API
import afuserApi from '@/api/client/afUser'
export default {
  name: 'UserManagement',
  data() {
    return {
      loading: false,
      tableData: [], // 存储用户列表，字段与ClientUser一致
      total: 0,
      // 查询参数：字段匹配ClientUser实体
      queryParams: {
        pageNum: 1,
        pageSize: 30, // 每页30条
        phone: '',
        email: '',
        name: ''
      },
      selectedIds: [], // 批量操作选中的用户ID
      dialogVisible: false,
      dialogTitle: '新增用户',
      // 表单数据：字段与ClientUser实体完全一致
      formData: {
        uid: null,
        uuid: '',
        email: '',
        password: '',
        name: '',
        metadata: '',
        encryptionSign: '',
        deletedAt: null,
        updatedAt: null,
        createdAt: null,
        phone: ''
      },
      // 表单校验规则
      formRules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        phone: [
          { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
        ],
        email: [
          { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    // 页面加载即查询列表
    this.getList()
  },
  methods: {
    // 1. 获取用户列表（调用API，参数为ClientUser字段）
    getList() {
      this.loading = true
      afuserApi.listAfUser(this.queryParams).then(response => {
        this.tableData = response.rows // Ruoyi分页返回格式：rows=列表，total=总数
        this.total = response.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    // 2. 条件查询
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    // 3. 重置查询条件
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 30,
        phone: '',
        email: '',
        name: ''
      }
      this.getList()
    },
    // 4. 多选框选中事件
    handleSelectionChange(val) {
      this.selectedIds = val.map(item => item.uid)
    },
    // 5. 新增用户
    handleAdd() {
      this.dialogTitle = '新增用户'
      // 重置表单，字段与ClientUser一致
      this.formData = {
        uid: null,
        uuid: '',
        email: '',
        password: '',
        name: '',
        metadata: '',
        encryptionSign: '',
        deletedAt: null,
        updatedAt: null,
        createdAt: null,
        phone: ''
      }
      this.dialogVisible = true
    },
    // 6. 查看用户详情
    handleView(row) {
      this.dialogTitle = '查看用户'
      this.formData = { ...row } // 直接赋值，字段与ClientUser一致
      this.dialogVisible = true
      // 查看时禁用所有表单字段
      this.$nextTick(() => {
        Object.keys(this.$refs.formRef.fields).forEach(key => {
          this.$refs.formRef.fields[key].disabled = true
        })
      })
    },
    // 7. 编辑用户
    handleEdit(row) {
      this.dialogTitle = '编辑用户'
      afuserApi.getAfUser(row.uid).then(response => {
        this.formData = response.data // 回填后端返回的ClientUser数据
        this.dialogVisible = true
        // 编辑时启用表单字段（除系统生成字段）
        this.$nextTick(() => {
          const disabledFields = ['uid', 'uuid', 'createdAt', 'updatedAt', 'deletedAt']
          Object.keys(this.$refs.formRef.fields).forEach(key => {
            this.$refs.formRef.fields[key].disabled = disabledFields.includes(key)
          })
        })
      })
    },
    // 8. 提交表单（新增/修改）
    submitForm() {
      this.$refs.formRef.validate(valid => {
        if (valid) {
          // 调用修改API（后端暂无新增接口，若有可补充）
          afuserApi.updateAfUser(this.formData).then(response => {
            this.msg.success(this.dialogTitle === '新增用户' ? '新增用户成功' : '修改用户成功')
            this.dialogVisible = false
            this.getList()
          }).catch(error => {
            this.msg.error(this.dialogTitle === '新增用户' ? '新增用户失败' : '修改用户失败')
          })
        }
      })
    },
    // 9. 单条删除用户
    handleDelete(uid) {
      this.confirm('此操作将永久删除该用户, 是否继续?').then(() => {
        afuserApi.removeAfUser([uid]).then(response => {
          this.msg.success('删除用户成功')
          this.getList()
        })
      })
    },
    // 10. 批量删除用户
    handleBatchDelete() {
      if (this.selectedIds.length === 0) {
        this.msg.warning('请选择至少一条用户数据')
        return
      }
      this.confirm('此操作将永久删除选中的用户, 是否继续?').then(() => {
        afuserApi.removeAfUser(this.selectedIds).then(response => {
          this.msg.success(`成功删除${response.data}条用户数据`)
          this.getList()
        })
      })
    },
    // 11. 单条停用用户
    handleStop(uid) {
      this.confirm('确定要停用该用户吗?').then(() => {
        afuserApi.stopAfUser([uid]).then(response => {
          this.msg.success('停用用户成功')
          this.getList()
        })
      })
    },
    // 12. 批量停用用户
    handleBatchStop() {
      if (this.selectedIds.length === 0) {
        this.msg.warning('请选择至少一条用户数据')
        return
      }
      this.confirm('确定要批量停用选中的用户吗?').then(() => {
        afuserApi.stopAfUser(this.selectedIds).then(response => {
          this.msg.success(`成功停用${response.data}个用户`)
          this.getList()
        })
      })
    },
    // 13. 查看用户工作区
    handleAllSpace(uid) {
      afuserApi.getAfUserWorkSpace(uid).then(response => {
        this.msg.info('已获取用户工作区信息')
        // 可弹窗展示工作区详情
        console.log('用户工作区信息：', response.data)
      })
    }
  }
}
</script>

<style scoped>
.query-form {
  margin-bottom: 16px;
}
.tool-bar {
  margin-bottom: 16px;
}
.dialog-footer {
  text-align: center;
}
</style>
