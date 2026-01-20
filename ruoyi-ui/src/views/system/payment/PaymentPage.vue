<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span>支付中心</span>
      </div>

      <el-form :model="form" label-width="120px">
        <el-form-item label="支付金额">
          <el-input-number v-model="form.amount" :min="0.01" :step="0.01" :precision="2" style="width: 200px;" />
        </el-form-item>

        <el-form-item label="支付方式">
          <el-radio-group v-model="form.paymentType">
            <el-radio label="alipay">支付宝</el-radio>
            <el-radio label="wechat">微信支付</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="createOrder">立即支付</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 支付二维码 -->
      <div v-if="qrCode" class="qr-code-container">
        <h3>请使用 {{ form.paymentType === 'alipay' ? '支付宝' : '微信' }} 扫描二维码支付</h3>
        <img :src="qrCode" alt="支付二维码" class="qr-code" />
        <p class="status-text">{{ statusText }}</p>
      </div>

      <!-- 支付结果 -->
      <div v-if="resultMessage" class="result-message">
        <el-alert
          :title="resultMessage"
          :type="resultType"
          :closable="false"
          show-icon
        />
      </div>
    </el-card>
  </div>
</template>

<script>
import { createPayment, pollPaymentStatus } from '@/api/system/payment'

export default {
  name: 'PaymentPage',
  data() {
    return {
      form: {
        amount: 1.00,
        paymentType: 'alipay'
      },
      qrCode: '',
      orderNo: '',
      statusText: '正在生成支付二维码...',
      resultMessage: '',
      resultType: 'info'
    }
  },
  methods: {
    createOrder() {
      const { amount, paymentType } = this.form

      // 调用后端创建支付订单
      createPayment({ amount, paymentType }).then(response => {
        const data = response.data
        if (data.status === 'success') {
          this.orderNo = data.orderNo
          this.qrCode = data.qrCode
          this.statusText = '请扫描二维码完成支付'
          this.startPolling()
        } else {
          this.$message.error('创建支付订单失败: ' + (data.message || '未知错误'))
        }
      }).catch(error => {
        console.error('Error creating payment:', error)
        this.$message.error('创建支付订单失败: ' + error.message)
      })
    },

    startPolling() {
      const maxAttempts = 100
      let attempts = 0
      const poll = () => {
        pollPaymentStatus(this.orderNo, 
          (response) => {
            const status = response.data
            if (status === 'success') {
              this.resultMessage = '支付成功！'
              this.resultType = 'success'
              this.statusText = ''
              this.qrCode = ''
              this.$message.success('支付成功')
            } else if (status === 'failed' || status === 'expired') {
              this.resultMessage = '支付失败或已过期'
              this.resultType = 'error'
              this.statusText = ''
              this.qrCode = ''
              this.$message.error('支付失败')
            } else {
              if (++attempts >= maxAttempts) {
                this.resultMessage = '支付超时，请重新支付'
                this.resultType = 'warning'
                this.statusText = ''
                this.qrCode = ''
                this.$message.warning('支付超时')
              } else {
                setTimeout(poll, 3000)
              }
            }
          },
          (error) => {
            this.$message.error('查询支付状态出错: ' + error.message)
          },
          () => {
            this.$message.warning('支付超时')
          }
        )
      }

      // 开始轮询
      poll()
    },

    resetForm() {
      this.form.amount = 1.00
      this.form.paymentType = 'alipay'
      this.qrCode = ''
      this.orderNo = ''
      this.statusText = '正在生成支付二维码...'
      this.resultMessage = ''
      this.resultType = 'info'
    }
  }
}
</script>

<style scoped>
.qr-code-container {
  margin-top: 20px;
  text-align: center;
}

.qr-code {
  width: 280px;
  height: 280px;
  margin: 0 auto;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.status-text {
  margin-top: 10px;
  font-size: 14px;
  color: #666;
}

.result-message {
  margin-top: 20px;
}
</style>