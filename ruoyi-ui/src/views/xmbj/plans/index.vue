<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['xmbj:plans:add']"
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
          v-hasPermi="['xmbj:plans:edit']"
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
          v-hasPermi="['xmbj:plans:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['xmbj:plans:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-row :gutter="20" v-loading="loading">
      <el-col :span="24">
        <el-card class="comparison-table-card">
          <div class="comparison-table-wrapper">
            <table class="comparison-table">
              <thead>
                <tr>
                  <th class="feature-column">功能</th>
                  <th v-for="plan in plansList" :key="plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    <div class="plan-header">
                      <div class="plan-name">{{ plan.planNameCn || plan.planName }}</div>
                      <div class="plan-code">{{ plan.planCode }}</div>
                      <el-tag :type="plan.isActive === 1 ? 'success' : 'info'" size="mini">
                        {{ plan.isActive === 1 ? '启用' : '禁用' }}
                      </el-tag>
                    </div>
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr class="price-row">
                  <td class="feature-name">月付</td>
                  <td v-for="plan in plansList" :key="'monthly-' + plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    <span class="price-value">¥{{ plan.monthlyPriceYuan }}/月</span>
                  </td>
                </tr>
                <tr class="price-row">
                  <td class="feature-name">年付</td>
                  <td v-for="plan in plansList" :key="'yearly-' + plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    <span class="price-value">¥{{ plan.yearlyPriceYuan }}/年</span>
                  </td>
                </tr>
                <tr class="category-row">
                  <td colspan="100%" class="category-header">一、基础</td>
                </tr>
                <tr>
                  <td class="feature-name">云存储空间</td>
                  <td v-for="plan in plansList" :key="'storage-' + plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    {{ plan.cloudStorageGb ? plan.cloudStorageGb + 'GB' : '-' }}
                  </td>
                </tr>
                <tr>
                  <td class="feature-name">收件箱</td>
                  <td v-for="plan in plansList" :key="'inbox-' + plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    <i :class="plan.hasInbox === 1 ? 'el-icon-check' : 'el-icon-minus'"
                       :style="{color: plan.hasInbox === 1 ? '#67C23A' : '#909399'}"></i>
                  </td>
                </tr>
                <tr>
                  <td class="feature-name">多端同步</td>
                  <td v-for="plan in plansList" :key="'sync-' + plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    <i :class="plan.hasMultiDeviceSync === 1 ? 'el-icon-check' : 'el-icon-minus'"
                       :style="{color: plan.hasMultiDeviceSync === 1 ? '#67C23A' : '#909399'}"></i>
                  </td>
                </tr>
                <tr>
                  <td class="feature-name">支持API</td>
                  <td v-for="plan in plansList" :key="'api-' + plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    <i :class="plan.hasApiSupport === 1 ? 'el-icon-check' : 'el-icon-minus'"
                       :style="{color: plan.hasApiSupport === 1 ? '#67C23A' : '#909399'}"></i>
                  </td>
                </tr>
                <tr>
                  <td class="feature-name">版本历史</td>
                  <td v-for="plan in plansList" :key="'history-' + plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    {{ plan.versionHistoryDays ? plan.versionHistoryDays + '天' : '-' }}
                  </td>
                </tr>
                <tr class="category-row">
                  <td colspan="100%" class="category-header">二、AI功能</td>
                </tr>
                <tr>
                  <td class="feature-name">AI对话</td>
                  <td v-for="plan in plansList" :key="'chat-' + plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    {{ plan.aiChatCountPerMonth ? plan.aiChatCountPerMonth + '次/月' : '-' }}
                  </td>
                </tr>
                <tr>
                  <td class="feature-name">图片生成</td>
                  <td v-for="plan in plansList" :key="'image-' + plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    {{ plan.aiImageGenerationPerMonth ? plan.aiImageGenerationPerMonth + '张/月' : '-' }}
                  </td>
                </tr>
                <tr class="category-row">
                  <td colspan="100%" class="category-header">三、共享和协作</td>
                </tr>
                <tr>
                  <td class="feature-name">分享链接</td>
                  <td v-for="plan in plansList" :key="'share-' + plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    <i :class="plan.hasShareLink === 1 ? 'el-icon-check' : 'el-icon-minus'"
                       :style="{color: plan.hasShareLink === 1 ? '#67C23A' : '#909399'}"></i>
                  </td>
                </tr>
                <tr>
                  <td class="feature-name">发布</td>
                  <td v-for="plan in plansList" :key="'publish-' + plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    <i :class="plan.hasPublish === 1 ? 'el-icon-check' : 'el-icon-minus'"
                       :style="{color: plan.hasPublish === 1 ? '#67C23A' : '#909399'}"></i>
                  </td>
                </tr>
                <tr>
                  <td class="feature-name">工作区成员</td>
                  <td v-for="plan in plansList" :key="'member-' + plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    <i :class="plan.workspaceMemberLimit > 0 ? 'el-icon-check' : 'el-icon-minus'"
                       :style="{color: plan.workspaceMemberLimit > 0 ? '#67C23A' : '#909399'}"></i>
                  </td>
                </tr>
                <tr>
                  <td class="feature-name">协作工作区</td>
                  <td v-for="plan in plansList" :key="'collab-' + plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    {{ plan.collaborativeWorkspaceLimit ? plan.collaborativeWorkspaceLimit + '个' : '-' }}
                  </td>
                </tr>
                <tr class="category-row">
                  <td colspan="100%" class="category-header">四、安全与权限管理</td>
                </tr>
                <tr>
                  <td class="feature-name">页面权限管理</td>
                  <td v-for="plan in plansList" :key="'permission-' + plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    {{ plan.pagePermissionGuestEditors ? plan.pagePermissionGuestEditors + '个访客编辑' : (plan.hasSpaceMemberManagement === 1 ? '仅查看' : '-') }}
                  </td>
                </tr>
                <tr>
                  <td class="feature-name">空间成员管理</td>
                  <td v-for="plan in plansList" :key="'space-member-' + plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    <i :class="plan.hasSpaceMemberManagement === 1 ? 'el-icon-check' : 'el-icon-minus'"
                       :style="{color: plan.hasSpaceMemberManagement === 1 ? '#67C23A' : '#909399'}"></i>
                  </td>
                </tr>
                <tr>
                  <td class="feature-name">空间成员分组</td>
                  <td v-for="plan in plansList" :key="'space-group-' + plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    <i :class="plan.hasSpaceMemberGrouping === 1 ? 'el-icon-check' : 'el-icon-minus'"
                       :style="{color: plan.hasSpaceMemberGrouping === 1 ? '#67C23A' : '#909399'}"></i>
                  </td>
                </tr>
                <tr class="action-row">
                  <td class="feature-name">操作</td>
                  <td v-for="plan in plansList" :key="'action-' + plan.id" :class="{'highlight-column': plan.isActive === 1}">
                    <el-button
                      size="mini"
                      type="primary"
                      icon="el-icon-edit"
                      @click="handleUpdate(plan)"
                      v-hasPermi="['xmbj:plans:edit']"
                    >修改</el-button>
                    <el-button
                      size="mini"
                      type="danger"
                      icon="el-icon-delete"
                      @click="handleDelete(plan)"
                      v-hasPermi="['xmbj:plans:remove']"
                    >删除</el-button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>
    </el-row>

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
          <el-switch v-model="form.hasInbox" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
        <el-form-item label="是否支持多设备同步" prop="hasMultiDeviceSync">
          <el-switch v-model="form.hasMultiDeviceSync" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
        <el-form-item label="是否支持API" prop="hasApiSupport">
          <el-switch v-model="form.hasApiSupport" :active-value="1" :inactive-value="0"></el-switch>
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
          <el-switch v-model="form.hasShareLink" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
        <el-form-item label="是否支持发布" prop="hasPublish">
          <el-switch v-model="form.hasPublish" :active-value="1" :inactive-value="0"></el-switch>
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
          <el-switch v-model="form.hasSpaceMemberManagement" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
        <el-form-item label="是否支持空间成员分组" prop="hasSpaceMemberGrouping">
          <el-switch v-model="form.hasSpaceMemberGrouping" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
        <el-form-item label="是否启用" prop="isActive">
          <el-switch v-model="form.isActive" :active-value="1" :inactive-value="0"></el-switch>
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
import { listPlans, getPlans, delPlans, addPlans, updatePlans } from "@/api/xmbj/plans"

export default {
  name: "Plans",
  dicts: ['sys_yes_no', 'sys_has_api_support'],
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
        planName: null,
        planNameCn: null,
        monthlyPriceYuan: null,
        yearlyPriceYuan: null,
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
          { required: true, message: "是否支持收件箱不能为空", trigger: "change" }
        ],
        hasMultiDeviceSync: [
          { required: true, message: "是否支持多设备同步不能为空", trigger: "change" }
        ],
        hasApiSupport: [
          { required: true, message: "是否支持API不能为空", trigger: "change" }
        ],
        hasShareLink: [
          { required: true, message: "是否支持分享链接不能为空", trigger: "change" }
        ],
        hasPublish: [
          { required: true, message: "是否支持发布不能为空", trigger: "change" }
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
        hasInbox: 0,
        hasMultiDeviceSync: 0,
        hasApiSupport: 0,
        versionHistoryDays: null,
        aiChatCountPerMonth: null,
        aiImageGenerationPerMonth: null,
        hasShareLink: 0,
        hasPublish: 0,
        workspaceMemberLimit: null,
        collaborativeWorkspaceLimit: null,
        pagePermissionGuestEditors: null,
        hasSpaceMemberManagement: 0,
        hasSpaceMemberGrouping: 0,
        isActive: 0,
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
        const data = response.data
        this.form = {
          ...data,
          hasInbox: data.hasInbox === 1 ? 1 : 0,
          hasMultiDeviceSync: data.hasMultiDeviceSync === 1 ? 1 : 0,
          hasApiSupport: data.hasApiSupport === 1 ? 1 : 0,
          hasShareLink: data.hasShareLink === 1 ? 1 : 0,
          hasPublish: data.hasPublish === 1 ? 1 : 0,
          hasSpaceMemberManagement: data.hasSpaceMemberManagement === 1 ? 1 : 0,
          hasSpaceMemberGrouping: data.hasSpaceMemberGrouping === 1 ? 1 : 0,
          isActive: data.isActive === 1 ? 1 : 0
        }
        this.originalForm = JSON.parse(JSON.stringify(this.form))
        this.open = true
        this.title = "修改订阅套餐"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            const updateData = this.getUpdateData()
            updatePlans(updateData).then(response => {
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
    /** 获取需要更新的数据，只包含实际修改的字段 */
    getUpdateData() {
      const updateData = {}
      const booleanFields = ['hasInbox', 'hasMultiDeviceSync', 'hasApiSupport', 'hasShareLink', 'hasPublish', 'hasSpaceMemberManagement', 'hasSpaceMemberGrouping', 'isActive']
      const otherFields = ['planCode', 'planName', 'planNameCn', 'monthlyPriceYuan', 'yearlyPriceYuan', 'cloudStorageGb', 'versionHistoryDays', 'aiChatCountPerMonth', 'aiImageGenerationPerMonth', 'workspaceMemberLimit', 'collaborativeWorkspaceLimit', 'pagePermissionGuestEditors', 'createdAt', 'updatedAt']

      console.log('getUpdateData: this.form =', JSON.stringify(this.form, null, 2))
      console.log('getUpdateData: this.originalForm =', JSON.stringify(this.originalForm, null, 2))

      if (this.originalForm) {
        otherFields.forEach(field => {
          if (this.form[field] !== this.originalForm[field]) {
            updateData[field] = this.form[field]
          }
        })

        booleanFields.forEach(field => {
          if (this.form[field] !== this.originalForm[field]) {
            updateData[field] = this.form[field]
          }
        })
      }

      updateData.id = this.form.id
      console.log('getUpdateData: updateData =', JSON.stringify(updateData, null, 2))
      return updateData
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
      this.download('xmbj/plans/export', {
        ...this.queryParams
      }, `plans_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style scoped>
.comparison-table-card {
  margin: 20px 0;
}

.comparison-table-wrapper {
  overflow-x: auto;
}

.comparison-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
}

.comparison-table thead th {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #ffffff;
  padding: 15px;
  text-align: center;
  border: 1px solid #e0e0e0;
  min-width: 150px;
  font-weight: 600;
  font-size: 15px;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
}

.comparison-table thead th.feature-column {
  background: #f5f7fa;
  color: #1a1a1a;
  min-width: 120px;
  position: sticky;
  left: 0;
  z-index: 10;
  font-weight: 700;
  font-size: 15px;
}

.plan-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 5px;
}

.plan-name {
  font-size: 17px;
  font-weight: bold;
  color: #ffffff;
  text-shadow: 0 1px 3px rgba(0, 0, 0, 0.3);
}

.plan-code {
  font-size: 13px;
  opacity: 1;
  color: #f0f0f0;
  font-weight: 500;
}

.comparison-table tbody td {
  padding: 12px;
  text-align: center;
  border: 1px solid #e0e0e0;
  background: white;
}

.comparison-table tbody td.feature-name {
  background: #f5f7fa;
  font-weight: 500;
  color: #303133;
  position: sticky;
  left: 0;
  z-index: 5;
}

.comparison-table tbody tr:hover td {
  background: #f0f9ff;
}

.comparison-table tbody tr:hover td.feature-name {
  background: #e8f4f8;
}

.price-row td {
  background: linear-gradient(135deg, #667eea15 0%, #764ba215 100%);
}

.price-value {
  font-size: 18px;
  font-weight: bold;
  color: #667eea;
}

.category-row td {
  background: #f0f2f5;
  padding: 8px;
  font-weight: bold;
  color: #303133;
}

.category-header {
  text-align: left;
  padding-left: 20px;
}

.highlight-column {
  background: #fff9e6 !important;
  color: #1a1a1a !important;
}

.highlight-column .plan-name {
  color: #1a1a1a !important;
  text-shadow: none !important;
}

.highlight-column .plan-code {
  color: #606266 !important;
}

.highlight-column:hover {
  background: #fff3cd !important;
}

.el-icon-check {
  font-size: 18px;
  font-weight: bold;
}

.el-icon-minus {
  font-size: 18px;
}

.action-row td {
  padding: 15px;
}

.action-row .el-button {
  margin: 3px;
}
</style>
