<template>
  <div class="pay-success-container">
    <!-- 成功状态图标 -->
    <div class="success-icon">
      <svg width="64" height="64" viewBox="0 0 64 64" fill="none" xmlns="http://www.w3.org/2000/svg">
        <circle cx="32" cy="32" r="32" fill="#4CD964"/>
        <path d="M20 32L28 40L44 24" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </div>

    <!-- 成功提示文本 -->
    <div class="success-text">
      <h2>支付成功</h2>
      <p>感谢您的购买，订单已确认</p>
    </div>

    <!-- 加载状态 -->
    <div v-if="loading" class="loading-container">
      <div class="loading-spinner"></div>
      <p>正在获取订单信息...</p>
    </div>

    <!-- 错误提示 -->
    <div v-if="error" class="error-message">
      <p>{{ error }}</p>
      <!-- <button class="retry-btn" @click="fetchOrderInfo">重试</button> -->
    </div>

    <!-- 订单信息 -->
    <div v-if="!loading && !error" class="order-info">
      <div class="info-item">
        <span class="label">订单编号：</span>
        <span class="value">{{ orderNo }}</span>
      </div>
      <div class="info-item">
        <span class="label">支付金额：</span>
        <span class="value">¥{{ formattedAmount }}</span>
      </div>
      <div class="info-item">
        <span class="label">支付方式：</span>
        <span class="value">{{ paymentType === 'wechat' ? '微信支付' : '支付宝' }}</span>
      </div>
      <div class="info-item">
        <span class="label">支付时间：</span>
        <span class="value">{{ payTime }}</span>
      </div>
      <div v-if="orderInfo" class="info-item">
        <span class="label">订单状态：</span>
        <span class="value">{{ status === 'success' ? '已完成' : '处理中' }}</span>
      </div>
      <div v-if="orderInfo && orderInfo.productName" class="info-item">
        <span class="label">商品名称：</span>
        <span class="value">{{ productName }}</span>
      </div>
      <div v-if="orderInfo && orderInfo.validDate" class="info-item">
        <span class="label">有效期至：</span>
        <span class="value">{{ orderInfo.validDate }}</span>
      </div>
    </div>

    <!-- 操作按钮 -->
    <div class="action-buttons">
      <button class="default-btn" @click="goToHome">
        返回首页
      </button>
    </div>
  </div>
</template>

<script>
// import { ref, computed } from 'vue'
// import { useRoute, useRouter } from 'vue-router'
// import { useStore } from 'vuex'
import { getPaymentOrder } from '@/api/payment'

// export default {
//   setup() {
//     const route = useRoute()
//     const router = useRouter()
//     // 定义响应式变量
//     const routeParams = computed(() => {
//       const params = {};
//       for (const key in route.query) {
//         params[key] = decodeURIComponent(route.query[key] || '');
//       }
//       return params;
//     });

//     const orderNo = ref(routeParams.value.orderNo);
//     const amount = ref(routeParams.value.amount);
//     const paymentType = ref(routeParams.value.paymentType);
//     const payTime = ref(routeParams.value.payTime);
//     const productName = ref(routeParams.value.productName);
//     const status = ref(routeParams.value.status);
//     const formattedAmount = ref(amount.value ? parseFloat(amount.value).toFixed(2) : '0.00');
//     const loading = ref(false); // 直接使用URL参数，不需要加载
//     const error = ref(null);
//     const orderInfo = ref({
//       orderNo: orderNo.value,
//       amount: amount.value,
//       paymentType: paymentType.value,
//       payTime: payTime.value,
//       productName: productName.value,
//       status: status.value
//     });


//     // 返回首页
//     const goToHome = () => {
//       router.go(-1); // 返回上一页，而不是跳转至首页
//     }

//     return {
//       orderNo,
//       formattedAmount,
//       paymentType,
//       payTime,
//       loading,
//       error,
//       orderInfo,
//       goToHome
//     }
//   }
// }

export default {
  data() {
    return {
      orderNo: '',
      amount: '',
      paymentType: '',
      payTime: '',
      productName: '',
      status: '',
      formattedAmount: '0.00',
      loading: false,
      error: null,
      orderInfo: {}
    }
  },
  created() {
    // 在created钩子中获取路由参数（Vue 2使用this.$route）
    const routeParams = this.getRouteParams()
    this.orderNo = routeParams.orderNo
    this.amount = routeParams.amount
    this.paymentType = routeParams.paymentType
    this.payTime = routeParams.payTime
    this.productName = routeParams.productName
    this.status = routeParams.status
    this.formattedAmount = this.amount ? parseFloat(this.amount).toFixed(2) : '0.00'
    this.orderInfo = {
      orderNo: this.orderNo,
      amount: this.amount,
      paymentType: this.paymentType,
      payTime: this.payTime,
      productName: this.productName,
      status: this.status
    }

    if (this.orderNo) {
      this.fetchOrderInfo()
    }
  },
  methods: {
    // 解析路由参数（Vue 2中通过this.$route.query获取）
    getRouteParams() {
      const params = {}
      for (const key in this.$route.query) {
        params[key] = decodeURIComponent(this.$route.query[key] || '')
      }
      return params
    },
    // 获取订单详情
    async fetchOrderInfo() {
      try {
        this.loading = true
        const res = await getPaymentOrder(this.orderNo)
        this.orderInfo = res.data
        if (res.data.amount) this.amount = res.data.amount
        if (res.data.paymentType) this.paymentType = res.data.paymentType
        if (res.data.payTime) this.payTime = res.data.payTime
        this.formattedAmount = this.amount ? parseFloat(this.amount).toFixed(2) : '0.00'
      } catch (err) {
        this.error = err.message || '获取订单信息失败'
      } finally {
        this.loading = false
      }
    },
    // 返回首页（Vue 2使用this.$router）
    goToHome() {
      this.$router.push({ path: '/' })
    }
  }
}
</script>

<style lang="less">
.pay-success-container {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 2rem 1rem;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;

  .success-icon {
    margin-bottom: 1.5rem;
  }

  .success-text {
    text-align: center;
    margin-bottom: 2rem;

    h2 {
      font-size: 1.5rem;
      color: #333;
      margin: 0 0 0.5rem 0;
    }

    p {
      font-size: 0.9rem;
      color: #666;
      margin: 0;
    }
  }

  .loading-container {
    text-align: center;
    margin: 2rem 0;
    
    .loading-spinner {
      width: 40px;
      height: 40px;
      margin: 0 auto 1rem;
      border: 4px solid #f3f3f3;
      border-top: 4px solid #FF7A45;
      border-radius: 50%;
      animation: spin 1s linear infinite;
    }
    
    p {
      color: #666;
      font-size: 0.9rem;
    }
    
    @keyframes spin {
      0% { transform: rotate(0deg); }
      100% { transform: rotate(360deg); }
    }
  }

  .error-message {
    text-align: center;
    background-color: #fff2f0;
    border: 1px solid #ffccc7;
    border-radius: 0.5rem;
    padding: 1rem;
    margin: 1rem 0;
    max-width: 400px;
    
    p {
      color: #f5222d;
      margin: 0 0 1rem 0;
    }
    
    .retry-btn {
      background-color: #ff4d4f;
      color: white;
      border: none;
      border-radius: 0.25rem;
      padding: 0.5rem 1rem;
      cursor: pointer;
      
      &:hover {
        background-color: #d9363e;
      }
    }
  }

  .order-info {
    width: 100%;
    max-width: 400px;
    background-color: white;
    border-radius: 0.5rem;
    padding: 1.2rem;
    margin-bottom: 2rem;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);

    .info-item {
      display: flex;
      padding: 0.8rem 0;
      border-bottom: 1px solid #f0f0f0;

      &:last-child {
        border-bottom: none;
      }

      .label {
        flex: 1;
        color: #666;
        font-size: 0.5rem;
      }

      .value {
        flex: 2;
        color: #333;
        font-size: 0.5rem;
        word-break: break-all; // 防止订单号过长溢出
      }
    }
  }

  .action-buttons {
    width: 100%;
    max-width: 400px;
    display: flex;
    flex-direction: column;
    gap: 1rem;

    .primary-btn {
      width: 100%;
      padding: 0.8rem;
      background-color: #FF7A45;
      color: white;
      border: none;
      border-radius: 0.5rem;
      font-size: 1rem;
      cursor: pointer;
      transition: background-color 0.2s;

      &:hover {
        background-color: #e66a3a;
      }
    }

    .default-btn {
      width: 100%;
      padding: 0.8rem;
      background-color: white;
      color: #333;
      border: 1px solid #ddd;
      border-radius: 0.5rem;
      font-size: 1rem;
      cursor: pointer;
      transition: background-color 0.2s;

      &:hover {
        background-color: #f9f9f9;
      }
    }
  }
}
</style>