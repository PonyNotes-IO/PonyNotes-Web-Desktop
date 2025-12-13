<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="套餐编码" prop="planCode">
        <el-input
          v-model="queryParams.planCode"
          placeholder="请输入套餐编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="套餐名称" prop="planName">
        <el-input
          v-model="queryParams.planName"
          placeholder="请输入套餐名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="套餐名称" prop="planNameCn">
        <el-input
          v-model="queryParams.planNameCn"
          placeholder="请输入套餐名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="月付价格" prop="monthlyPriceYuan">
        <el-input
          v-model="queryParams.monthlyPriceYuan"
          placeholder="请输入月付价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年付价格" prop="yearlyPriceYuan">
        <el-input
          v-model="queryParams.yearlyPriceYuan"
          placeholder="请输入年付价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="云存储容量" prop="cloudStorageGb">
        <el-input
          v-model="queryParams.cloudStorageGb"
          placeholder="请输入云存储容量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否支持收件箱" prop="hasInbox">
        <el-input
          v-model="queryParams.hasInbox"
          placeholder="请输入是否支持收件箱"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否支持多设备同步" prop="hasMultiDeviceSync">
        <el-input
          v-model="queryParams.hasMultiDeviceSync"
          placeholder="请输入是否支持多设备同步"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否支持API" prop="hasApiSupport">
        <el-input
          v-model="queryParams.hasApiSupport"
          placeholder="请输入是否支持API"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="版本历史保留天数" prop="versionHistoryDays">
        <el-input
          v-model="queryParams.versionHistoryDays"
          placeholder="请输入版本历史保留天数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="每月AI聊天次数" prop="aiChatCountPerMonth">
        <el-input
          v-model="queryParams.aiChatCountPerMonth"
          placeholder="请输入每月AI聊天次数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="每月AI生成图片次数" prop="aiImageGenerationPerMonth">
        <el-input
          v-model="queryParams.aiImageGenerationPerMonth"
          placeholder="请输入每月AI生成图片次数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否支持分享链接" prop="hasShareLink">
        <el-input
          v-model="queryParams.hasShareLink"
          placeholder="请输入是否支持分享链接"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否支持发布" prop="hasPublish">
        <el-input
          v-model="queryParams.hasPublish"
          placeholder="请输入是否支持发布"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工作空间成员上限" prop="workspaceMemberLimit">
        <el-input
          v-model="queryParams.workspaceMemberLimit"
          placeholder="请输入工作空间成员上限"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="协作工作空间上限" prop="collaborativeWorkspaceLimit">
        <el-input
          v-model="queryParams.collaborativeWorkspaceLimit"
          placeholder="请输入协作工作空间上限"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="页面权限访客编辑者上限" prop="pagePermissionGuestEditors">
        <el-input
          v-model="queryParams.pagePermissionGuestEditors"
          placeholder="请输入页面权限访客编辑者上限"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否支持空间成员管理" prop="hasSpaceMemberManagement">
        <el-input
          v-model="queryParams.hasSpaceMemberManagement"
          placeholder="请输入是否支持空间成员管理"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否支持空间成员分组" prop="hasSpaceMemberGrouping">
        <el-input
          v-model="queryParams.hasSpaceMemberGrouping"
          placeholder="请输入是否支持空间成员分组"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否启用" prop="isActive">
        <el-input
          v-model="queryParams.isActive"
          placeholder="请输入是否启用"
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
      <el-form-item label="更新时间" prop="updatedAt">
        <el-date-picker clearable
          v-model="queryParams.updatedAt"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择更新时间">
        </el-date-picker>
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
          v-hasPermi="['system:plans:add']"
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
          v-hasPermi="['system:plans:edit']"
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
          v-hasPermi="['system:plans:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:plans:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="plansList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="套餐ID" align="center" prop="id" />
      <el-table-column label="套餐编码" align="center" prop="planCode" />
      <el-table-column label="套餐名称" align="center" prop="planName" />
      <el-table-column label="套餐名称" align="center" prop="planNameCn" />
      <el-table-column label="月付价格" align="center" prop="monthlyPriceYuan" />
      <el-table-column label="年付价格" align="center" prop="yearlyPriceYuan" />
      <el-table-column label="云存储容量" align="center" prop="cloudStorageGb" />
      <el-table-column label="是否支持收件箱" align="center" prop="hasInbox" />
      <el-table-column label="是否支持多设备同步" align="center" prop="hasMultiDeviceSync" />
      <el-table-column label="是否支持API" align="center" prop="hasApiSupport" />
      <el-table-column label="版本历史保留天数" align="center" prop="versionHistoryDays" />
      <el-table-column label="每月AI聊天次数" align="center" prop="aiChatCountPerMonth" />
      <el-table-column label="每月AI生成图片次数" align="center" prop="aiImageGenerationPerMonth" />
      <el-table-column label="是否支持分享链接" align="center" prop="hasShareLink" />
      <el-table-column label="是否支持发布" align="center" prop="hasPublish" />
      <el-table-column label="工作空间成员上限" align="center" prop="workspaceMemberLimit" />
      <el-table-column label="协作工作空间上限" align="center" prop="collaborativeWorkspaceLimit" />
      <el-table-column label="页面权限访客编辑者上限" align="center" prop="pagePermissionGuestEditors" />
      <el-table-column label="是否支持空间成员管理" align="center" prop="hasSpaceMemberManagement" />
      <el-table-column label="是否支持空间成员分组" align="center" prop="hasSpaceMemberGrouping" />
      <el-table-column label="是否启用" align="center" prop="isActive" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updatedAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updatedAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:plans:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:plans:remove']"
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

    <!-- 添加或修改订阅套餐对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="套餐编码" prop="planCode">
          <el-input v-model="form.planCode" placeholder="请输入套餐编码" />
        </el-form-item>
        <el-form-item label="套餐名称" prop="planName">
          <el-input v-model="form.planName" placeholder="请输入套餐名称" />
        </el-form-item>
        <el-form-item label="套餐名称" prop="planNameCn">
          <el-input v-model="form.planNameCn" placeholder="请输入套餐名称" />
        </el-form-item>
        <el-form-item label="月付价格" prop="monthlyPriceYuan">
          <el-input v-model="form.monthlyPriceYuan" placeholder="请输入月付价格" />
        </el-form-item>
        <el-form-item label="年付价格" prop="yearlyPriceYuan">
          <el-input v-model="form.yearlyPriceYuan" placeholder="请输入年付价格" />
        </el-form-item>
        <el-form-item label="云存储容量" prop="cloudStorageGb">
          <el-input v-model="form.cloudStorageGb" placeholder="请输入云存储容量" />
        </el-form-item>
        <el-form-item label="是否支持收件箱" prop="hasInbox">
          <el-input v-model="form.hasInbox" placeholder="请输入是否支持收件箱" />
        </el-form-item>
        <el-form-item label="是否支持多设备同步" prop="hasMultiDeviceSync">
          <el-input v-model="form.hasMultiDeviceSync" placeholder="请输入是否支持多设备同步" />
        </el-form-item>
        <el-form-item label="是否支持API" prop="hasApiSupport">
          <el-input v-model="form.hasApiSupport" placeholder="请输入是否支持API" />
        </el-form-item>
        <el-form-item label="版本历史保留天数" prop="versionHistoryDays">
          <el-input v-model="form.versionHistoryDays" placeholder="请输入版本历史保留天数" />
        </el-form-item>
        <el-form-item label="每月AI聊天次数" prop="aiChatCountPerMonth">
          <el-input v-model="form.aiChatCountPerMonth" placeholder="请输入每月AI聊天次数" />
        </el-form-item>
        <el-form-item label="每月AI生成图片次数" prop="aiImageGenerationPerMonth">
          <el-input v-model="form.aiImageGenerationPerMonth" placeholder="请输入每月AI生成图片次数" />
        </el-form-item>
        <el-form-item label="是否支持分享链接" prop="hasShareLink">
          <el-input v-model="form.hasShareLink" placeholder="请输入是否支持分享链接" />
        </el-form-item>
        <el-form-item label="是否支持发布" prop="hasPublish">
          <el-input v-model="form.hasPublish" placeholder="请输入是否支持发布" />
        </el-form-item>
        <el-form-item label="工作空间成员上限" prop="workspaceMemberLimit">
          <el-input v-model="form.workspaceMemberLimit" placeholder="请输入工作空间成员上限" />
        </el-form-item>
        <el-form-item label="协作工作空间上限" prop="collaborativeWorkspaceLimit">
          <el-input v-model="form.collaborativeWorkspaceLimit" placeholder="请输入协作工作空间上限" />
        </el-form-item>
        <el-form-item label="页面权限访客编辑者上限" prop="pagePermissionGuestEditors">
          <el-input v-model="form.pagePermissionGuestEditors" placeholder="请输入页面权限访客编辑者上限" />
        </el-form-item>
        <el-form-item label="是否支持空间成员管理" prop="hasSpaceMemberManagement">
          <el-input v-model="form.hasSpaceMemberManagement" placeholder="请输入是否支持空间成员管理" />
        </el-form-item>
        <el-form-item label="是否支持空间成员分组" prop="hasSpaceMemberGrouping">
          <el-input v-model="form.hasSpaceMemberGrouping" placeholder="请输入是否支持空间成员分组" />
        </el-form-item>
        <el-form-item label="是否启用" prop="isActive">
          <el-input v-model="form.isActive" placeholder="请输入是否启用" />
        </el-form-item>
        <el-form-item label="创建时间" prop="createdAt">
          <el-date-picker clearable
            v-model="form.createdAt"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="更新时间" prop="updatedAt">
          <el-date-picker clearable
            v-model="form.updatedAt"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择更新时间">
          </el-date-picker>
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
import { listPlans, getPlans, delPlans, addPlans, updatePlans } from "@/api/system/plans"

export default {
  name: "Plans",
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
      // 订阅套餐表格数据
      plansList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        planCode: null,
        planName: null,
        planNameCn: null,
        monthlyPriceYuan: null,
        yearlyPriceYuan: null,
        cloudStorageGb: null,
        hasInbox: null,
        hasMultiDeviceSync: null,
        hasApiSupport: null,
        versionHistoryDays: null,
        aiChatCountPerMonth: null,
        aiImageGenerationPerMonth: null,
        hasShareLink: null,
        hasPublish: null,
        workspaceMemberLimit: null,
        collaborativeWorkspaceLimit: null,
        pagePermissionGuestEditors: null,
        hasSpaceMemberManagement: null,
        hasSpaceMemberGrouping: null,
        isActive: null,
        createdAt: null,
        updatedAt: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        planCode: [
          { required: true, message: "套餐编码不能为空", trigger: "blur" }
        ],
        planName: [
          { required: true, message: "套餐名称不能为空", trigger: "blur" }
        ],
        planNameCn: [
          { required: true, message: "套餐名称不能为空", trigger: "blur" }
        ],
        monthlyPriceYuan: [
          { required: true, message: "月付价格不能为空", trigger: "blur" }
        ],
        yearlyPriceYuan: [
          { required: true, message: "年付价格不能为空", trigger: "blur" }
        ],
        hasInbox: [
          { required: true, message: "是否支持收件箱不能为空", trigger: "blur" }
        ],
        hasMultiDeviceSync: [
          { required: true, message: "是否支持多设备同步不能为空", trigger: "blur" }
        ],
        hasApiSupport: [
          { required: true, message: "是否支持API不能为空", trigger: "blur" }
        ],
        hasShareLink: [
          { required: true, message: "是否支持分享链接不能为空", trigger: "blur" }
        ],
        hasPublish: [
          { required: true, message: "是否支持发布不能为空", trigger: "blur" }
        ],
        hasSpaceMemberManagement: [
          { required: true, message: "是否支持空间成员管理不能为空", trigger: "blur" }
        ],
        hasSpaceMemberGrouping: [
          { required: true, message: "是否支持空间成员分组不能为空", trigger: "blur" }
        ],
        isActive: [
          { required: true, message: "是否启用不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询订阅套餐列表 */
    getList() {
      this.loading = true
      listPlans(this.queryParams).then(response => {
        this.plansList = response.rows
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
        id: null,
        planCode: null,
        planName: null,
        planNameCn: null,
        monthlyPriceYuan: null,
        yearlyPriceYuan: null,
        cloudStorageGb: null,
        hasInbox: null,
        hasMultiDeviceSync: null,
        hasApiSupport: null,
        versionHistoryDays: null,
        aiChatCountPerMonth: null,
        aiImageGenerationPerMonth: null,
        hasShareLink: null,
        hasPublish: null,
        workspaceMemberLimit: null,
        collaborativeWorkspaceLimit: null,
        pagePermissionGuestEditors: null,
        hasSpaceMemberManagement: null,
        hasSpaceMemberGrouping: null,
        isActive: null,
        createdAt: null,
        updatedAt: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加订阅套餐"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getPlans(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改订阅套餐"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePlans(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addPlans(this.form).then(response => {
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
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除订阅套餐编号为"' + ids + '"的数据项？').then(function() {
        return delPlans(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/plans/export', {
        ...this.queryParams
      }, `plans_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
