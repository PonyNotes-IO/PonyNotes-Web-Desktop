<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="80px">
      <el-form-item label="订单编号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付方式" prop="paymentType">
        <el-select v-model="queryParams.paymentType" placeholder="请选择支付方式" clearable size="small">
          <el-option label="微信" value="wechat" />
          <el-option label="支付宝" value="alipay" />
        </el-select>
      </el-form-item>
      <el-form-item label="订单状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择订单状态" clearable size="small">
          <el-option label="待支付" value="pending" />
          <el-option label="已支付" value="success" />
          <el-option label="失败" value="failed" />
        </el-select>
      </el-form-item>
      <el-form-item label="商品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入商品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:paymentOrder:add']"
        >
          新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          @click="handleDelete"
          :disabled="selection.length === 0"
          v-hasPermi="['system:paymentOrder:remove']"
        >
          批量删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:paymentOrder:export']"
        >
          导出
        </el-button>
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="paymentOrderList"
      @selection-change="handleSelectionChange"
      border
      fit
      highlight-current-row
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单ID" align="center" prop="id" width="100" />
      <el-table-column label="订单编号" align="center" prop="orderNo" :show-overflow-tooltip="true" width="180" />
      <el-table-column label="支付金额(元)" align="center" prop="amount" width="120" />
      <el-table-column label="支付方式" align="center" prop="paymentType" width="120">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.paymentType === 'wechat'" size="small" type="success">微信</el-tag>
          <el-tag v-else-if="scope.row.paymentType === 'alipay'" size="small" type="primary">支付宝</el-tag>
          <el-tag v-else size="small" type="info">{{ scope.row.paymentType }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="订单状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 'pending'" size="small" type="warning">待支付</el-tag>
          <el-tag v-else-if="scope.row.status === 'success'" size="small" type="success">已支付</el-tag>
          <el-tag v-else-if="scope.row.status === 'failed'" size="small" type="danger">失败</el-tag>
          <el-tag v-else size="small" type="info">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="商品名称" align="center" prop="productName" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180" />
      <el-table-column label="支付时间" align="center" prop="payTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['system:paymentOrder:detail']"
          >
            详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEdit(scope.row)"
            v-hasPermi="['system:paymentOrder:edit']"
          >
            修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleRemove(scope.row)"
            v-hasPermi="['system:paymentOrder:remove']"
          >
            删除
          </el-button>
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

    <!-- 添加或修改支付订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="支付金额" prop="amount">
          <el-input-number v-model="form.amount" :min="0" :step="0.01" :precision="2" placeholder="请输入支付金额" />
        </el-form-item>
        <el-form-item label="支付方式" prop="paymentType">
          <el-select v-model="form.paymentType" placeholder="请选择支付方式">
            <el-option label="微信" value="wechat" />
            <el-option label="支付宝" value="alipay" />
          </el-select>
        </el-form-item>
        <el-form-item label="订单状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择订单状态">
            <el-option label="待支付" value="pending" />
            <el-option label="已支付" value="success" />
            <el-option label="失败" value="failed" />
          </el-select>
        </el-form-item>
        <el-form-item label="商品名称" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="支付二维码" prop="qrCodeUrl">
          <el-input v-model="form.qrCodeUrl" placeholder="请输入支付二维码URL" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog title="订单详情" :visible.sync="detailOpen" width="600px" append-to-body>
      <el-descriptions :column="1" border>
        <el-descriptions-item label="订单编号">{{ detailForm.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="支付金额">{{ detailForm.amount }} 元</el-descriptions-item>
        <el-descriptions-item label="支付方式">
          <el-tag v-if="detailForm.paymentType === 'wechat'" size="small" type="success">微信</el-tag>
          <el-tag v-else-if="detailForm.paymentType === 'alipay'" size="small" type="primary">支付宝</el-tag>
          <el-tag v-else size="small" type="info">{{ detailForm.paymentType }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="订单状态">
          <el-tag v-if="detailForm.status === 'pending'" size="small" type="warning">待支付</el-tag>
          <el-tag v-else-if="detailForm.status === 'success'" size="small" type="success">已支付</el-tag>
          <el-tag v-else-if="detailForm.status === 'failed'" size="small" type="danger">失败</el-tag>
          <el-tag v-else size="small" type="info">{{ detailForm.status }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="商品名称">{{ detailForm.productName }}</el-descriptions-item>
        <el-descriptions-item label="支付二维码">
          <el-image v-if="detailForm.qrCodeUrl" :src="detailForm.qrCodeUrl" style="width: 200px; height: 200px;" />
          <span v-else>无</span>
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ detailForm.createTime }}</el-descriptions-item>
        <el-descriptions-item label="更新时间">{{ detailForm.updateTime }}</el-descriptions-item>
        <el-descriptions-item label="支付时间">{{ detailForm.payTime || '无' }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPaymentOrder, addPaymentOrder, editPaymentOrder, removePaymentOrder, getPaymentOrder } from "@/api/system/paymentOrder";

export default {
  name: "PaymentOrder",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      selection: [],
      // 总条数
      total: 0,
      // 支付订单表格数据
      paymentOrderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 详情对话框
      detailOpen: false,
      // 表单参数
      form: {},
      // 详情表单
      detailForm: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: undefined,
        paymentType: undefined,
        status: undefined,
        productName: undefined
      },
      // 表单校验
      rules: {
        orderNo: [
          { required: true, message: "订单编号不能为空", trigger: "blur" }
        ],
        amount: [
          { required: true, message: "支付金额不能为空", trigger: "blur" }
        ],
        paymentType: [
          { required: true, message: "支付方式不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "订单状态不能为空", trigger: "change" }
        ],
        productName: [
          { required: true, message: "商品名称不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询支付订单列表 */
    getList() {
      this.loading = true;
      listPaymentOrder(this.queryParams).then(response => {
        this.paymentOrderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.$refs.queryForm.resetFields();
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增支付订单";
    },
    /** 修改按钮操作 */
    handleEdit(row) {
      this.reset();
      this.form = row;
      this.open = true;
      this.title = "修改支付订单";
    },
    /** 详情按钮操作 */
    handleDetail(row) {
      this.detailForm = row;
      this.detailOpen = true;
    },
    /** 删除按钮操作 */
    handleDelete() {
      this.$modal.confirm("是否确认删除选中的支付订单？").then(function() {
        return removePaymentOrder(this.selection.map(item => item.id));
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 单条删除按钮操作 */
    handleRemove(row) {
      this.$modal.confirm("是否确认删除订单编号为" + row.orderNo + "的支付订单？").then(function() {
        return removePaymentOrder(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download("/system/paymentOrder/export", { ...this.queryParams }, `paymentOrder_${new Date().getTime()}.xlsx`);
    },
    /** 表单重置 */
    reset() {
      this.form = {
        id: undefined,
        orderNo: undefined,
        amount: undefined,
        paymentType: undefined,
        qrCodeUrl: undefined,
        status: undefined,
        productName: undefined
      };
      this.resetForm("form");
    },
    /** 提交表单 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            editPaymentOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPaymentOrder(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 表格选中行事件 */
    handleSelectionChange(selection) {
      this.selection = selection;
    }
  }
};
</script>