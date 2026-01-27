<template>
  <div>
    <el-table :data="recordsList" style="width: 100%" v-loading="loading">
      <el-table-column prop="createdAt" label="登录时间" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="provider" label="提供商" width="120"></el-table-column>
      <el-table-column prop="thirdPartyId" label="第三方ID" width="150"></el-table-column>
      <el-table-column prop="ipAddress" label="IP地址" width="150"></el-table-column>
      <el-table-column prop="country" label="国家" width="100"></el-table-column>
      <el-table-column prop="region" label="地区" width="100"></el-table-column>
      <el-table-column prop="city" label="城市" width="100"></el-table-column>
      <el-table-column prop="userAgent" label="用户代理" :show-overflow-tooltip="true" min-width="200"></el-table-column>
      <el-table-column prop="success" label="成功" width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.success ? '是' : '否' }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="errorReason" label="错误原因" :show-overflow-tooltip="true" min-width="150"></el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getRecords"
    />
  </div>
</template>

<script>
import { listSignInLog } from "@/api/client/signInLog";

export default {
  name: "SignInRecords",
  props: {
    userUuid: {
      type: String,
      default: null
    },
    userUid: {
      type: Number,
      default: null
    }
  },
  data() {
    return {
      loading: true,
      recordsList: [],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userUuid: null,
        userUid: null
      }
    };
  },
  watch: {
    userUuid: {
      handler() {
        this.queryParams.userUuid = this.userUuid;
        this.getRecords();
      },
      immediate: true
    },
    userUid: {
      handler() {
        this.queryParams.userUid = this.userUid;
        this.getRecords();
      },
      immediate: true
    }
  },
  methods: {
    getRecords() {
      this.loading = true;
      listSignInLog(this.queryParams).then(response => {
        this.recordsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    }
  }
};
</script>
