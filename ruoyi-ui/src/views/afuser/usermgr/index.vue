<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="用户邮箱" prop="email">
        <el-input
          v-model="queryParams.email"
          placeholder="请输入用户邮箱"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="用户名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入用户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>




      <el-form-item label="用户手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入用户手机号"
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
          v-hasPermi="['afuser:usermgr:add']"
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
          v-hasPermi="['afuser:usermgr:edit']"
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
          v-hasPermi="['afuser:usermgr:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['afuser:usermgr:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="usermgrList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户ID" align="center">
        <template slot-scope="scope">
          {{ scope.row.uid.toString() }}
        </template>
      </el-table-column>

      <el-table-column label="用户邮箱" align="center" prop="email" />

      <el-table-column label="用户名称" align="center" prop="name" />
      <el-table-column label="用户头像" align="center" width="120">
        <template slot-scope="scope">
          <el-image
            v-if="scope.row.avatar"
            :src="scope.row.avatar"
            :preview-src-list="[scope.row.avatar]"
            fit="cover"
            style="width: 40px; height: 40px; border-radius: 50%"
          />
          <span v-else>无头像</span>
        </template>
      </el-table-column>
      <el-table-column label="用户元数据" align="center" prop="metadata" />




      <el-table-column label="用户手机号" align="center" prop="phone" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['afuser:usermgr:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['afuser:usermgr:remove']"
          >删除</el-button>

          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            @click="handleViewOrder(scope.row)"
            v-hasPermi="['system:order:list']"
          >订单</el-button>
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

    <!-- 添加或修改用户管理11对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户唯一标识" prop="uuid">
          <el-input v-model="form.uuid" placeholder="请输入用户唯一标识" />
        </el-form-item>
        <el-form-item label="用户邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入用户邮箱" />
        </el-form-item>
        <el-form-item label="用户密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入用户密码" />
        </el-form-item>
        <el-form-item label="用户名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入用户名称" />
        </el-form-item>
        <el-form-item label="用户头像" prop="avatar">
          <el-input v-model="form.avatar" placeholder="请输入头像链接地址" />
        </el-form-item>
        <el-form-item label="用户元数据" prop="metadata">
          <el-input v-model="form.metadata" type="textarea" placeholder="请输入内容" />
        </el-form-item>


        <el-form-item label="更新时间" prop="updatedAt">
          <el-date-picker clearable
            v-model="form.updatedAt"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择更新时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="创建时间" prop="createdAt">
          <el-date-picker clearable
            v-model="form.createdAt"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="用户手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入用户手机号" />
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
import { listUsermgr, getUsermgr, delUsermgr, addUsermgr, updateUsermgr } from "@/api/afuser/usermgr"

export default {
  name: "Usermgr",
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
      // 用户管理11表格数据
      usermgrList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        email: null,
        name: null,
        metadata: null,
        phone: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        uuid: [
          { required: true, message: "用户唯一标识不能为空", trigger: "blur" }
        ],
        password: [
          { required: true, message: "用户密码不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "用户名称不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询用户管理11列表 */
    getList() {
      this.loading = true
      listUsermgr(this.queryParams).then(response => {
        this.usermgrList = response.rows
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
        uid: null,
        uuid: null,
        email: null,
        password: null,
        name: null,
        avatar: null,
        metadata: null,
        updatedAt: null,
        createdAt: null,
        phone: null
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
      // 使用uid，后端API期望Long类型的uid
      this.ids = selection.map(item => item.uid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加用户管理11"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      // 使用uid，后端API期望Long类型的uid
      const uid = row.uid || this.ids
      console.log('修改用户ID:', uid)
      if (!uid) {
        this.$modal.msgError('未找到用户ID')
        return
      }
      getUsermgr(uid).then(response => {
        console.log('API响应:', response)
        if (response && response.data) {
          this.form = response.data
          this.open = true
          this.title = "修改用户管理11"
          // 手动设置表单值，确保Element UI能够正确更新表单
          this.$nextTick(() => {
            if (this.$refs.form) {
              this.$refs.form.setFieldsValue(this.form)
            }
          })
        } else {
          console.error('API返回数据为空')
          this.$modal.msgError('获取用户信息失败，返回数据为空')
        }
      }).catch(error => {
        console.error('API调用错误:', error)
        this.$modal.msgError('获取用户信息失败')
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.uid != null) {
            updateUsermgr(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addUsermgr(this.form).then(response => {
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
      // 使用uid，后端API期望Long类型的uid
      const uids = row.uid || this.ids
      console.log('删除用户ID:', uids)
      if (!uids) {
        this.$modal.msgError('未找到用户ID')
        return
      }
      this.$modal.confirm('是否确认删除用户管理11编号为"' + uids + '"的数据项？').then(() => {
        return delUsermgr(uids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(error => {
        console.error('删除失败:', error)
        this.$modal.msgError('删除失败，请重试')
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('afuser/usermgr/export', {
        ...this.queryParams
      }, `usermgr_${new Date().getTime()}.xlsx`)
    },

    /** 查看订单操作 */
    handleViewOrder(row) {
      // 传递uuid到订单页面
      const uuid = row.uuid
      console.log('查看订单，用户UUID:', uuid)
      if (!uuid) {
        this.$modal.msgError('用户UUID为空')
        return
      }
      // 跳转到订单页面，并传递用户UUID
      this.$router.push({
        path: '/system/order/index',
        query: {
          uuid: uuid
        }
      })
    }
  }
}
</script>
