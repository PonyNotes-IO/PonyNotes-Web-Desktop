<template>
  <div class="app-container">
    <!-- 标签页 -->
    <el-tabs v-model="activeTab">
      <el-tab-pane label="历史订单" name="order">
        <!-- 搜索表单 -->
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="订单编号" prop="orderNo">
            <el-input
              v-model="queryParams.orderNo"
              placeholder="请输入订单编号"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="支付时间" prop="payTime">
            <el-date-picker
              v-model="dateRange"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <!-- 订单列表 -->
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:order:add']"
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
              v-hasPermi="['system:order:edit']"
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
              v-hasPermi="['system:order:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['system:order:export']"
            >导出</el-button>
          </el-col>
        </el-row>

        <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="订单ID" align="center" prop="id" />
          <el-table-column label="订单编号" align="center" prop="orderNo" />
          <el-table-column label="产品名称" align="center" prop="productName" />
          <el-table-column label="订单总金额" align="center" prop="amount">
            <template slot-scope="scope">
              <span>{{ scope.row.amount.toFixed(2) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="支付方式" align="center" prop="paymentType">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_payment_type" :value="scope.row.paymentType"/>
            </template>
          </el-table-column>
          <el-table-column label="支付状态" align="center" prop="status">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.order_status" :value="scope.row.status"/>
            </template>
          </el-table-column>
          <el-table-column label="开通时间" align="center" prop="startTime" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="到期时间" align="center" prop="endTime" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>

          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:order:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:order:remove']"
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
      </el-tab-pane>


    </el-tabs>

    <!-- 添加或修改支付订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="产品名称" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入产品名称" />
        </el-form-item>
        <el-form-item label="支付金额(元)" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入支付金额(元)" />
        </el-form-item>
        <el-form-item label="支付方式" prop="paymentType">
          <el-select v-model="form.paymentType" placeholder="请选择支付方式">
            <el-option
              v-for="dict in dict.type.sys_payment_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="支付二维码URL" prop="qrCodeUrl">
          <el-input v-model="form.qrCodeUrl" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="订单状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.order_status"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="支付时间" prop="payTime">
          <el-date-picker clearable
            v-model="form.payTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择支付时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="开通时间" prop="startTime">
          <el-date-picker clearable
            v-model="form.startTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择开通时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="到期时间" prop="endTime">
          <el-date-picker clearable
            v-model="form.endTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择到期时间">
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
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/system/order"
import { getAfUserBySysUserId } from "@/api/afuser/usermgr"

export default {
  name: "Order",
  dicts: ['order_status', 'sys_payment_type'],
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
      // 支付订单表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 当前激活的标签页
      activeTab: 'order',
      // 用户UUID
      uuid: null,
      // 用户ID
      userId: null,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userInfo: null,
        orderNo: null,
        paymentType: null,
        qrCodeUrl: null,
        status: null,
        startPayTime: null,
        endPayTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderNo: [
          { required: true, message: "订单编号不能为空", trigger: "blur" }
        ],
        productName: [
          { required: true, message: "产品名称不能为空", trigger: "blur" }
        ],
        amount: [
          { required: true, message: "支付金额(元)不能为空", trigger: "blur" }
        ],
        paymentType: [
          { required: true, message: "支付方式不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "订单状态不能为空", trigger: "change" }
        ],
        startTime: [
          { required: true, message: "开通时间不能为空", trigger: "blur" }
        ],
        endTime: [
          { required: true, message: "到期时间不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    // 获取URL参数中的userId或uuid
    this.userId = this.$route.query.userId ? parseInt(this.$route.query.userId) : null
    this.userUuid = this.$route.query.uuid || null
    
    if (this.userId) {
      // 根据userId查询af_user获取uuid
      getAfUserBySysUserId(this.userId).then(response => {
        if (response.data && response.data.uuid) {
          this.userUuid = response.data.uuid
          this.queryParams.userInfo = this.userUuid
        }
      }).catch(() => {
        this.$message.error('获取用户信息失败')
      })
    } else if (this.userUuid) {
      // 直接使用uuid参数
      this.queryParams.userInfo = this.userUuid
    }
    this.getList()
  },
  methods: {
    /** 查询支付订单列表 */
    getList() {
      this.loading = true
      // 处理日期范围参数
      if (this.dateRange && this.dateRange.length === 2) {
        this.queryParams.startPayTime = this.dateRange[0]
        this.queryParams.endPayTime = this.dateRange[1]
      } else {
        this.queryParams.startPayTime = null
        this.queryParams.endPayTime = null
      }
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = []
      this.resetForm("queryForm")
      this.handleQuery()
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
        orderNo: null,
        productName: null,
        amount: null,
        paymentType: null,
        qrCodeUrl: null,
        status: null,
        createTime: null,
        updateTime: null,
        payTime: null,
        startTime: null,
        endTime: null
      }
      this.resetForm("form")
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
      this.title = "添加支付订单"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getOrder(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改支付订单"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addOrder(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除支付订单编号为"' + ids + '"的数据项？').then(function() {
        return delOrder(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-header-extra {
  display: flex;
  gap: 10px;
}

.package-info {
  padding: 10px 0;
}

.package-item {
  margin-bottom: 10px;
}

.package-label {
  font-weight: bold;
  margin-right: 10px;
}

.package-value {
  color: #333;
}
</style>
/style>
