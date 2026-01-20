<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="用户账号" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户账号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="登录IP" prop="ipaddr">
        <el-input
          v-model="queryParams.ipaddr"
          placeholder="请输入登录IP"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="登录状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择登录状态" clearable size="small">
          <el-option label="成功" value="0" />
          <el-option label="失败" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          @click="handleDeleteAll"
          :disabled="selection.length === 0"
          v-hasPermi="['monitor:logininfor:remove']"
        >
          批量删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-delete"
          size="mini"
          @click="handleClean"
          v-hasPermi="['monitor:logininfor:remove']"
        >
          清空
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-export"
          size="mini"
          @click="handleExport"
          v-hasPermi="['monitor:logininfor:export']"
        >
          导出
        </el-button>
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="logininforList"
      @selection-change="handleSelectionChange"
      border
      fit
      highlight-current-row
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="访问序号" align="center" prop="infoId" width="80" />
      <el-table-column label="登录IP地址" align="center" prop="ipaddr" :show-overflow-tooltip="true" width="140" />
      <el-table-column label="登录地点" align="center" prop="loginLocation" :show-overflow-tooltip="true" />
      <el-table-column label="用户账号" align="center" prop="userName" :show-overflow-tooltip="true" width="120" />
      <el-table-column label="浏览器" align="center" prop="browser" :show-overflow-tooltip="true" />
      <el-table-column label="操作系统" align="center" prop="os" :show-overflow-tooltip="true" width="120" />
      <el-table-column label="登录状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '0' ? 'success' : 'danger'" size="small">
            {{ scope.row.status === '0' ? '成功' : '失败' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="提示消息" align="center" prop="msg" :show-overflow-tooltip="true" />
      <el-table-column label="登录时间" align="center" prop="loginTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="80">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['monitor:logininfor:remove']"
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
  </div>
</template>

<script>
import { listLogininfor, deleteLogininfor, cleanLogininfor } from "@/api/monitor/logininfor";

export default {
  name: "Logininfor",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      selection: [],
      // 总条数
      total: 0,
      // 登录日志表格数据
      logininforList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        ipaddr: undefined,
        status: undefined
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询登录日志列表 */
    getList() {
      this.loading = true;
      listLogininfor(this.queryParams).then(response => {
        this.logininforList = response.rows;
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
    /** 表格选中行事件 */
    handleSelectionChange(selection) {
      this.selection = selection;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const infoIds = row.infoId || this.selection.map(item => item.infoId);
      this.$modal.confirm("是否确认删除登录日志  " + row.userName + "？").then(function() {
        return deleteLogininfor(infoIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 批量删除按钮操作 */
    handleDeleteAll() {
      this.$modal.confirm("是否确认删除选中的登录日志？").then(function() {
        return deleteLogininfor(this.selection.map(item => item.infoId));
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 清空按钮操作 */
    handleClean() {
      this.$modal.confirm("是否确认清空所有登录日志？").then(function() {
        return cleanLogininfor();
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("清空成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download("/monitor/logininfor/export", { ...this.queryParams }, `logininfor_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>