<template>
  <div class="app-container">
    <el-card class="mb8" shadow="hover">
      <div slot="header" class="clearfix">
        <span>容量总览</span>
        <el-button style="float: right; padding: 3px 0" type="text" icon="el-icon-refresh" @click="getStorageInfo">刷新</el-button>
      </div>
      <el-row :gutter="20">
        <el-col :span="4">
          <div class="stat-item">
            <div class="stat-label">总体存储容量</div>
            <div class="stat-value">{{ formatSize(storageInfo.totalCapacity) }}</div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="stat-item">
            <div class="stat-label">已用容量</div>
            <div class="stat-value text-danger">{{ formatSize(storageInfo.spaceSize) }}</div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="stat-item">
            <div class="stat-label">剩余容量</div>
            <div class="stat-value text-success">{{ formatSize(storageInfo.remainingCapacity) }}</div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="stat-item">
            <div class="stat-label">利用率</div>
            <div class="stat-value" :class="getUsageRateClass(storageInfo.usageRate)">{{ storageInfo.usageRate }}%</div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="stat-item">
            <div class="stat-label">容量使用进度</div>
            <div class="stat-value small">{{ formatSize(storageInfo.spaceSize) }} / {{ formatSize(storageInfo.totalCapacity) }}</div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="stat-item">
            <div class="stat-label">最后更新时间</div>
            <div class="stat-value small">{{ storageInfo.lastUpdateTime }}</div>
          </div>
        </el-col>
      </el-row>
      <el-progress 
        :percentage="storageInfo.usageRate" 
        :color="progressColor" 
        :stroke-width="20"
        :format="formatProgress"
        style="margin-top: 20px;">
      </el-progress>
    </el-card>

    <el-card class="mb8" shadow="hover" v-if="capacityAnalysis">
      <div slot="header" class="clearfix">
        <span>容量趋势分析（近6个月）</span>
        <el-button style="float: right; padding: 3px 0" type="text" icon="el-icon-refresh" @click="getCapacityTrendData">刷新</el-button>
      </div>
      <el-row :gutter="20" style="margin-bottom: 20px;">
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-label">起始容量</div>
            <div class="stat-value">{{ capacityAnalysis.startCapacityTB }} TB</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-label">结束容量</div>
            <div class="stat-value">{{ capacityAnalysis.endCapacityTB }} TB</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-label">总增长</div>
            <div class="stat-value text-success">{{ capacityAnalysis.totalGrowthTB }} TB</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-label">平均容量</div>
            <div class="stat-value">{{ capacityAnalysis.averageCapacityTB }} TB</div>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" style="margin-bottom: 20px;">
        <el-col :span="8">
          <div class="stat-item">
            <div class="stat-label">总增长率</div>
            <div class="stat-value">{{ capacityAnalysis.totalGrowthRate }}%</div>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="stat-item">
            <div class="stat-label">平均月增长</div>
            <div class="stat-value">{{ capacityAnalysis.averageMonthlyGrowthTB }} TB</div>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="stat-item">
            <div class="stat-label">趋势描述</div>
            <div class="stat-value">{{ capacityAnalysis.trendDescription }}</div>
          </div>
        </el-col>
      </el-row>
      <div ref="capacityChart" style="width: 100%; height: 400px;"></div>
    </el-card>

    <el-card class="mb8" shadow="hover" v-if="capacityAnalysis && capacityAnalysis.storageTypeDistribution">
      <div slot="header" class="clearfix">
        <span>存储类型分布</span>
        <el-button style="float: right; padding: 3px 0" type="text" icon="el-icon-refresh" @click="getCapacityTrendData">刷新</el-button>
      </div>
      <el-row :gutter="20" style="margin-bottom: 20px;">
        <el-col :span="12">
          <div class="stat-item">
            <div class="stat-label">总使用量</div>
            <div class="stat-value">{{ capacityAnalysis.totalCapacityTB }} TB</div>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="stat-item">
            <div class="stat-label">存储类型数量</div>
            <div class="stat-value">{{ capacityAnalysis.storageTypeDistribution.length }} 种</div>
          </div>
        </el-col>
      </el-row>
      <div ref="storageTypeChart" style="width: 100%; height: 400px;"></div>
    </el-card>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { getStorageInfo, getCapacityTrend } from "@/api/system/qiniu"

export default {
  name: "Qiniu",
  data() {
    return {
      loading: false,
      storageInfo: {
        totalCapacity: 0,
        spaceSize: 0,
        remainingCapacity: 0,
        usageRate: 0,
        lastUpdateTime: ''
      },
      capacityAnalysis: null,
      chartOption: null,
      capacityChart: null,
      storageTypeChart: null
    }
  },
  computed: {
    progressColor() {
      const rate = this.storageInfo.usageRate || 0
      if (rate < 50) return '#67C23A'
      if (rate < 80) return '#E6A23C'
      return '#F56C6C'
    }
  },
  created() {
    this.getStorageInfo()
    this.getCapacityTrendData()
  },
  beforeDestroy() {
    if (this.capacityChart) {
      this.capacityChart.dispose()
    }
    if (this.storageTypeChart) {
      this.storageTypeChart.dispose()
    }
  },
  methods: {
    getCapacityTrendData() {
      getCapacityTrend().then(response => {
        this.capacityAnalysis = response.data
        this.$nextTick(() => {
          this.initChart()
          this.initStorageTypeChart()
        })
      })
    },
    initChart() {
      if (!this.capacityAnalysis || !this.capacityAnalysis.trendList) {
        return
      }

      const chartDom = this.$refs.capacityChart
      if (!chartDom) {
        return
      }

      const myChart = echarts.init(chartDom)
      this.capacityChart = myChart

      const months = this.capacityAnalysis.trendList.map(item => item.month)
      const capacities = this.capacityAnalysis.trendList.map(item => item.capacityTB)

      const option = {
        title: {
          text: '七牛云容量使用趋势',
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          formatter: function(params) {
            return params[0].name + '<br/>' + 
                   '容量: ' + params[0].value + ' TB'
          }
        },
        xAxis: {
          type: 'category',
          data: months,
          boundaryGap: false,
          axisLabel: {
            rotate: 45
          }
        },
        yAxis: {
          type: 'value',
          name: '容量（TB）',
          axisLabel: {
            formatter: '{value}'
          }
        },
        series: [
          {
            name: '容量',
            type: 'line',
            data: capacities,
            smooth: true,
            symbol: 'circle',
            symbolSize: 8,
            itemStyle: {
              color: '#409EFF'
            },
            lineStyle: {
              width: 3
            },
            areaStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [
                  { offset: 0, color: 'rgba(64, 158, 255, 0.3)' },
                  { offset: 1, color: 'rgba(64, 158, 255, 0.05)' }
                ]
              }
            }
          }
        ]
      }

      myChart.setOption(option)

      window.addEventListener('resize', () => {
        myChart.resize()
      })
    },
    initStorageTypeChart() {
      if (!this.capacityAnalysis || !this.capacityAnalysis.storageTypeDistribution) {
        return
      }

      const chartDom = this.$refs.storageTypeChart
      if (!chartDom) {
        return
      }

      const myChart = echarts.init(chartDom)
      this.storageTypeChart = myChart

      const distribution = this.capacityAnalysis.storageTypeDistribution
      const storageTypes = distribution.map(item => item.storageType)
      const capacities = distribution.map(item => item.capacityTB)
      const percentages = distribution.map(item => item.percentage)

      const option = {
        title: {
          text: '存储类型分布',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: function(params) {
            return params.name + '<br/>' + 
                   '容量: ' + params.value + ' TB<br/>' +
                   '占比: ' + params.percent + '%'
          }
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          top: 'middle'
        },
        series: [
          {
            name: '存储类型',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 20,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: distribution.map(item => ({
              name: item.storageType,
              value: item.capacityTB
            }))
          }
        ]
      }

      myChart.setOption(option)

      window.addEventListener('resize', () => {
        myChart.resize()
      })
    },
    getList() {
      this.loading = true
      listFiles(this.queryParams).then(response => {
        this.fileList = response.rows || []
        this.total = response.total || 0
        this.loading = false
      })
    },
    getStorageInfo() {
      getStorageInfo().then(response => {
        this.storageInfo = response.data || {
          totalCapacity: 0,
          spaceSize: 0,
          remainingCapacity: 0,
          usageRate: 0,
          lastUpdateTime: ''
        }
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.key)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleView(row) {
      this.form = row
      this.open = true
      this.title = "文件详情"
    },
    handleDownload(row) {
      const url = row.url || `${this.storageInfo.domain}/${row.key}`
      window.open(url, '_blank')
    },
    handleDelete(row) {
      this.$modal.confirm('是否确认删除文件"' + row.key + '"？').then(() => {
        return this.$API.qiniu.deleteFile(row.key)
      }).then(() => {
        this.getList()
        this.getStorageInfo()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    cancel() {
      this.open = false
      this.resetForm("form")
    },
    formatSize(size) {
      if (!size) return '0 B'
      const units = ['B', 'KB', 'MB', 'GB', 'TB', 'PB']
      let index = 0
      while (size >= 1024 && index < units.length - 1) {
        size /= 1024
        index++
      }
      return size.toFixed(2) + ' ' + units[index]
    },
    getUsageRateClass(rate) {
      if (rate < 50) return ''
      if (rate < 80) return 'text-warning'
      return 'text-danger'
    },
    formatProgress(percentage) {
      return `${percentage}%`
    }
  }
}
</script>

<style scoped>
.stat-item {
  text-align: center;
  padding: 15px;
  background: #f5f7fa;
  border-radius: 4px;
}

.stat-label {
  font-size: 14px;
  color: #606266;
  margin-bottom: 10px;
}

.stat-value {
  font-size: 20px;
  font-weight: bold;
  color: #303133;
}

.stat-value.small {
  font-size: 14px;
  font-weight: normal;
}

.text-danger {
  color: #F56C6C;
}

.text-warning {
  color: #E6A23C;
}

.text-success {
  color: #67C23A;
}

.clearfix::after {
  content: "";
  display: table;
  clear: both;
}

.mb8 {
  margin-bottom: 20px;
}
</style>
