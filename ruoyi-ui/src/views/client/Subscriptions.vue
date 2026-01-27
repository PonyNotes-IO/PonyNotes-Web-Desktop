<template>
  <div class="client-subscriptions">
    <el-card>
      <el-table :data="subscriptions" stripe>
        <el-table-column prop="planId" label="计划ID" />
        <el-table-column prop="startDate" label="开始时间" />
        <el-table-column prop="endDate" label="结束时间" />
        <el-table-column prop="status" label="状态" />
      </el-table>
      <div class="pagination" style="margin-top:16px;text-align:right;">
        <el-pagination
          background
          layout="prev, pager, next, sizes, total"
          :page-sizes="[10,20,50]"
          :page-size="pageSize"
          :current-page="pageNum"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script>
import { getMySubscriptions } from '@/api/client/subscriptions'

export default {
  name: 'ClientSubscriptions',
  data() {
    return {
      subscriptions: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      userId: null
    }
  },
  created() {
    // userId 应该从登录信息或路由参数中获取
    this.userId = this.$route.query.userId || null
    this.load()
  },
  methods: {
    async load() {
      if (!this.userId) return
      const res = await getMySubscriptions(this.userId, this.pageNum, this.pageSize)
      if (res && res.rows) {
        this.subscriptions = res.rows
        this.total = res.total
      }
    },
    handleSizeChange(size) {
      this.pageSize = size
      this.pageNum = 1
      this.load()
    },
    handleCurrentChange(page) {
      this.pageNum = page
      this.load()
    }
  }
}
</script>

<style scoped>
.client-subscriptions { padding: 16px; }
</style>
