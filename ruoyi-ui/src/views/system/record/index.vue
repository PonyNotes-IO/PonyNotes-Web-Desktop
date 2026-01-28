<template>
  <div class="app-container">
    <!-- 用户登录概况 -->
    <el-card v-if="profileId" class="mb8" shadow="hover">
      <template slot="header">
        <div class="card-header">
          <span>用户登录概况</span>
        </div>
      </template>
      <el-form :model="profile" size="small" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="用户ID">
              <span>{{ profile.userId }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="用户来源">
              <span>{{ profile.userSource }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="用户唯一标识">
              <span>{{ profile.userUniqueId }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="手机号">
              <span>{{ profile.phone }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="登录总次数">
              <span>{{ profile.loginTotalCount }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="登录总时长">
              <span>{{ profile.loginTotalDuration }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="注册时间">
              <span>{{ parseTime(profile.registerTime) }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="创建时间">
              <span>{{ parseTime(profile.createTime) }}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <!-- 第三方登录信息 -->
    <el-card v-if="profileId" class="mb8" shadow="hover">
      <template slot="header">
        <div class="card-header">
          <span>第三方登录信息</span>
        </div>
      </template>
      <el-table v-loading="bindLoading" :data="bindList">
        <el-table-column type="index" label="序号" align="center" width="80" />
        <el-table-column label="第三方登陆名称" align="center" prop="thirdPlatform" />
        <el-table-column label="用户第三方标识" align="center" prop="thirdUserId" />
        <el-table-column label="最后登陆时间" align="center" prop="lastLoginTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.lastLoginTime) }}</span>
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
              @click="handleBindUpdate(scope.row)"
              v-hasPermi="['system:bind:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleBindDelete(scope.row)"
              v-hasPermi="['system:bind:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:record:add']"
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
          v-hasPermi="['system:record:edit']"
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
          v-hasPermi="['system:record:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:record:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="记录ID" align="center" prop="recordId" />
      <el-table-column label="关联登录概况ID" align="center" prop="profileId" />
      <el-table-column label="登陆时间" align="center" prop="loginTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.loginTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="登陆地点" align="center" prop="loginLocation" />
      <el-table-column label="登陆IP" align="center" prop="loginIp" />
      <el-table-column label="登陆方式" align="center" prop="loginMethod" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:record:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:record:remove']"
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

    <!-- 添加或修改用户登录记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="关联登录概况ID" prop="profileId">
          <el-input v-model="form.profileId" placeholder="请输入关联登录概况ID" />
        </el-form-item>
        <el-form-item label="登陆时间" prop="loginTime">
          <el-date-picker clearable
            v-model="form.loginTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择登陆时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="登陆地点" prop="loginLocation">
          <el-input v-model="form.loginLocation" placeholder="请输入登陆地点" />
        </el-form-item>
        <el-form-item label="登陆IP" prop="loginIp">
          <el-input v-model="form.loginIp" placeholder="请输入登陆IP" />
        </el-form-item>
        <el-form-item label="登陆方式" prop="loginMethod">
          <el-input v-model="form.loginMethod" placeholder="请输入登陆方式" />
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
import { listRecord, getRecord, delRecord, addRecord, updateRecord } from "@/api/system/record"
import { getProfile, listProfile } from "@/api/system/profile"
import { listBind } from "@/api/system/bind"

export default {
  name: "Record",
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
      // 用户登录记录表格数据
      recordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        profileId: null,
        loginTime: null,
        loginLocation: null,
        loginIp: null,
        loginMethod: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        profileId: [
          { required: true, message: "关联登录概况ID不能为空", trigger: "blur" }
        ],
        loginTime: [
          { required: true, message: "登陆时间不能为空", trigger: "blur" }
        ],
      },
      // 用户登录概况
      profile: {},
      // 第三方登录信息列表
      bindList: [],
      // 第三方登录信息加载状态
      bindLoading: false,
      // 当前用户ID
      profileId: null
    }
  },
  created() {
    // 获取URL参数中的userId和profileId
    const userId = this.$route.query.userId ? parseInt(this.$route.query.userId) : null
    const profileId = this.$route.query.profileId ? parseInt(this.$route.query.profileId) : null
    
    console.log('created() - userId:', userId)
    console.log('created() - profileId:', profileId)
    
    if (userId) {
      // 通过userId查询对应的profile
      console.log('created() - 查询profile for userId:', userId)
      listProfile({ userId: userId, pageNum: 1, pageSize: 100 }).then(response => {
        console.log('created() - profile response:', response)
        if (response.rows && response.rows.length > 0) {
          this.profileId = response.rows[0].profileId
          this.queryParams.profileId = this.profileId
          console.log('created() - found profileId:', this.profileId)
          this.getProfileInfo()
          this.getBindList()
        } else {
          console.log('created() - no profile found for userId:', userId)
        }
        this.getList()
      }).catch(error => {
        console.log('created() - profile query error:', error)
        this.getList()
      })
    } else if (profileId) {
      this.profileId = profileId
      this.queryParams.profileId = profileId
      console.log('created() - using profileId from URL:', profileId)
      this.getProfileInfo()
      this.getBindList()
      this.getList()
    } else {
      console.log('created() - no userId or profileId provided')
      this.getList()
    }
  },
  methods: {
    /** 查询用户登录记录列表 */
    getList() {
      this.loading = true
      console.log('getList() - queryParams:', this.queryParams)
      listRecord(this.queryParams).then(response => {
        console.log('getList() - response:', response)
        this.recordList = response.rows
        this.total = response.total
        this.loading = false
      }).catch(error => {
        console.log('getList() - error:', error)
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
        recordId: null,
        profileId: null,
        loginTime: null,
        loginLocation: null,
        loginIp: null,
        loginMethod: null
      }
      this.resetForm("form")
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.recordId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加用户登录记录"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const recordId = row.recordId || this.ids
      getRecord(recordId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改用户登录记录"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recordId != null) {
            updateRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addRecord(this.form).then(response => {
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
      const recordIds = row.recordId || this.ids
      this.$modal.confirm('是否确认删除用户登录记录编号为"' + recordIds + '"的数据项？').then(function() {
        return delRecord(recordIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/record/export', {
        ...this.queryParams
      }, `record_${new Date().getTime()}.xlsx`)
    },
    /** 获取用户登录概况 */
    getProfileInfo() {
      getProfile(this.profileId).then(response => {
        this.profile = response.data
      })
    },
    /** 获取第三方登录信息列表 */
    getBindList() {
      this.bindLoading = true
      listBind({ profileId: this.profileId }).then(response => {
        this.bindList = response.rows
        this.bindLoading = false
      })
    },
    /** 修改第三方登录绑定 */
    handleBindUpdate(row) {
      // 这里可以添加修改第三方登录绑定的逻辑
      console.log('修改第三方登录绑定:', row)
    },
    /** 删除第三方登录绑定 */
    handleBindDelete(row) {
      // 这里可以添加删除第三方登录绑定的逻辑
      console.log('删除第三方登录绑定:', row)
    }
  }
}
</script>
