<template>
  <div>
    <el-table :data="overviewData" style="width: 100%">
      <el-table-column prop="userUuid" label="用户UUID" width="200"></el-table-column>
      <el-table-column prop="userUid" label="用户UID" width="150"></el-table-column>
      <el-table-column prop="totalLogins" label="总登录次数" width="120"></el-table-column>
      <el-table-column prop="successfulLogins" label="成功登录次数" width="130"></el-table-column>
      <el-table-column prop="failedLogins" label="失败登录次数" width="120"></el-table-column>
      <el-table-column prop="successRate" label="成功率" width="100">
        <template slot-scope="scope">
          {{ scope.row.successRate }}%
        </template>
      </el-table-column>
      <el-table-column prop="lastLoginTime" label="最后登录时间" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastLoginTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="commonIp" label="常用IP" width="150"></el-table-column>
      <el-table-column prop="commonCountry" label="常用国家" width="120"></el-table-column>
      <el-table-column prop="commonProvider" label="常用提供商" width="120"></el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getSignInOverview } from "@/api/client/signInLog";

export default {
  name: "SignInOverview",
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
      overviewData: []
    };
  },
  watch: {
    userUuid: {
      handler() {
        this.getOverview();
      },
      immediate: true
    },
    userUid: {
      handler() {
        this.getOverview();
      },
      immediate: true
    }
  },
  methods: {
    getOverview() {
      if (this.userUuid || this.userUid) {
        getSignInOverview({ userUuid: this.userUuid, userUid: this.userUid }).then(response => {
          this.overviewData = [response.data]; // 假设返回单个对象
        });
      }
    }
  }
};
</script>
