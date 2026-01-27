<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户UUID" prop="userUuid">
        <el-input
          v-model="queryParams.userUuid"
          placeholder="请输入用户UUID"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户UID" prop="userUid">
        <el-input
          v-model="queryParams.userUid"
          placeholder="请输入用户UID"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="提供商" prop="provider">
        <el-input
          v-model="queryParams.provider"
          placeholder="请输入提供商"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="IP地址" prop="ipAddress">
        <el-input
          v-model="queryParams.ipAddress"
          placeholder="请输入IP地址"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="国家" prop="country">
        <el-input
          v-model="queryParams.country"
          placeholder="请输入国家"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="成功" prop="success">
        <el-select v-model="queryParams.success" placeholder="请选择是否成功" clearable style="width: 200px">
          <el-option label="是" :value="true"></el-option>
          <el-option label="否" :value="false"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="params">
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

    <!-- 操作区域 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['auth:signInLog:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="signInLogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户UUID" align="center" prop="userUuid" />
      <el-table-column label="用户UID" align="center" prop="userUid" />
      <el-table-column label="提供商" align="center" prop="provider" />
      <el-table-column label="第三方ID" align="center" prop="thirdPartyId" />
      <el-table-column label="IP地址" align="center" prop="ipAddress" />
      <el-table-column label="国家" align="center" prop="country" />
      <el-table-column label="地区" align="center" prop="region" />
      <el-table-column label="城市" align="center" prop="city" />
      <el-table-column label="用户代理" align="center" prop="userAgent" :show-overflow-tooltip="true" />
      <el-table-column label="成功" align="center" prop="success">
        <template slot-scope="scope">
          <span>{{ scope.row.success ? '是' : '否' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="错误原因" align="center" prop="errorReason" :show-overflow-tooltip="true" />
      <el-table-column label="元数据" align="center" prop="metadata" :show-overflow-tooltip="true" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleOverview(scope.row)">登录概况</el-button>
          <el-button size="mini" type="text" icon="el-icon-document" @click="handleRecords(scope.row)">登录记录</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 登录概况对话框 -->
    <el-dialog :title="overviewTitle" :visible.sync="overviewDialogVisible" width="80%" append-to-body>
      <SignInOverview :user-uuid="selectedUserUuid" :user-uid="selectedUserUid" />
    </el-dialog>

    <!-- 登录记录对话框 -->
    <el-dialog :title="recordsTitle" :visible.sync="recordsDialogVisible" width="80%" append-to-body>
      <SignInRecords :user-uuid="selectedUserUuid" :user-uid="selectedUserUid" />
    </el-dialog>

  </div>
</template>

<script>
import { listSignInLog, exportSignInLog } from "@/api/client/signInLog";
import SignInOverview from "./components/SignInOverview.vue";
import SignInRecords from "./components/SignInRecords.vue";

export default {
  name: "SignInLog",
  components: {
    SignInOverview,
    SignInRecords
  },
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
      // 登录日志表格数据
      signInLogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userUuid: null,
        userUid: null,
        provider: null,
        ipAddress: null,
        country: null,
        success: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      // 登录概况对话框
      overviewDialogVisible: false,
      overviewTitle: "登录概况",
      // 登录记录对话框
      recordsDialogVisible: false,
      recordsTitle: "登录记录",
      // 选中的用户
      selectedUserUuid: null,
      selectedUserUid: null
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询登录日志列表 */
    getList() {
      this.loading = true;
      listSignInLog(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.signInLogList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userUuid: null,
        userUid: null,
        provider: null,
        thirdPartyId: null,
        ipAddress: null,
        country: null,
        region: null,
        city: null,
        userAgent: null,
        success: null,
        errorReason: null,
        metadata: null,
        createdAt: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.dateRange = [];
      this.handleQuery();
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.addDateRange(this.queryParams, this.dateRange);
      this.$confirm('是否确认导出所有登录日志数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return exportSignInLog(queryParams);
      }).then(response => {
        this.download(response.msg);
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 登录概况
    handleOverview(row) {
      this.selectedUserUuid = row.userUuid;
      this.selectedUserUid = row.userUid;
      this.overviewTitle = `登录概况 - ${row.userUuid || row.userUid}`;
      this.overviewDialogVisible = true;
    },
    // 登录记录
    handleRecords(row) {
      this.selectedUserUuid = row.userUuid;
      this.selectedUserUid = row.userUid;
      this.recordsTitle = `登录记录 - ${row.userUuid || row.userUid}`;
      this.recordsDialogVisible = true;
    },
  }
};
</script>
