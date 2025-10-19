<template>
  <div>
    <!-- 协议确认 -->
    <div class="agreement">
      <label class="agreement-label">
        <input 
          type="radio" 
          name="agreement"
          class="agreement-checkbox"
          v-model="isAgreed" 
          :value="true"  
        >
        <span class="agreement-text">确认《会员服务》《隐私协议》</span>
      </label>
    </div>
    
    <!-- 支付区域 -->
    <div class="payment-area">
      <div class="payment-info">
        <div class="payment-icons">
          <span>选择支付方式</span>
        </div>

        <!-- 支付方式卡片（微信 + 支付宝 并排） -->
        <div class="payment-grid">
          <div  
            class="payment-card" 
            @click="selectPayment('wechat')" 
            :class="{ 'selected': selectedPayment === 'wechat' }"
          >
            <div class="payment-icon" :style="{ backgroundImage: `url(${require('./assets/img/wx_icon.png')})` }"></div>
            <p class="payment-name">微信支付</p>
          </div>
          <div 
            class="payment-card" 
            @click="selectPayment('alipay')" 
            :class="{ 'selected': selectedPayment === 'alipay' }"
          >
            <div class="payment-icon" :style="{ backgroundImage: `url(${require('./assets/img/alipay_icon.png')})` }"></div>
            <p class="payment-name">支付宝</p>
          </div>
        </div>

        <!-- 二维码区域（仅选中支付方式时显示） -->
        <div class="price-info" v-if="selectedPayment">
          <div class="qr-code">
            <img 
              :src="selectedPayment === 'wechat' ? wechatQrCode : alipayQrCode" 
              alt="支付二维码"
            >
          </div>
          <div class="price-info-detail">
            <p class="price-label">应付金额</p>
            <p class="price-amount">¥{{ formattedAmount }}</p>
          </div>
        </div>
      </div>
      
      <!-- 支付按钮 -->
      <button class="pay-btn" @click="handlePayment">
        使用{{ selectedPayment === 'wechat' ? '微信' : selectedPayment === 'alipay' ? '支付宝' : '' }}支付
      </button>
    </div>
  </div>
</template>

<script>
import { ref, computed } from 'vue';
import axios from 'axios'; // 引入axios用于接口请求

export default {
  props: {
    amount: {
      type: Number,
      required: true
    },
    checked: {
      type: Boolean,
      default: false
    }
  },
  setup(props) {
    // 状态管理
    const isAgreed = ref(props.checked);
    const selectedPayment = ref(''); // 选中的支付方式：wechat/alipay
    const wechatQrCode = ref(''); // 微信支付二维码
    const alipayQrCode = ref(''); // 支付宝支付二维码
    const isLoading = ref(false); // 支付加载状态

    // 格式化金额
    const formattedAmount = computed(() => {
      return props.amount.toFixed(2);
    });

    // 选择支付方式
    const selectPayment = (type) => {
      selectedPayment.value = type;
      // 生成对应支付方式的二维码（调用后端接口）
      generatePaymentQrCode(type);
    };

    // 生成支付二维码（调用后端接口）
    const generatePaymentQrCode = async (type) => {
      try {
        const res = await axios.post('/api/create-payment', {
          amount: props.amount,
          paymentType: type
        });
        if (type === 'wechat') {
          wechatQrCode.value = res.data.qrCodeUrl;
        } else {
          alipayQrCode.value = res.data.qrCodeUrl;
        }
      } catch (err) {
        console.error('生成支付二维码失败:', err);
        alert('支付方式加载失败，请重试');
      }
    };

    // 处理支付
    const handlePayment = async () => {
      // 验证：必须同意协议且选择支付方式
      if (!isAgreed.value) {
        alert('请先确认《会员服务》和《隐私协议》');
        return;
      }
      if (!selectedPayment.value) {
        alert('请选择支付方式');
        return;
      }

      isLoading.value = true;
      try {
        // 调用后端接口确认支付状态（实际项目中会轮询查询）
        const res = await axios.get('/api/check-payment', {
          params: {
            paymentType: selectedPayment.value,
            amount: props.amount
          }
        });
        
        if (res.data.success) {
          alert('支付成功！');
          // 通知父组件支付成功
          this.$emit('paymentSuccess', {
            amount: props.amount,
            paymentType: selectedPayment.value
          });
        } else {
          alert('支付尚未完成，请扫码支付');
        }
      } catch (err) {
        console.error('支付查询失败:', err);
        alert('支付过程出错，请重试');
      } finally {
        isLoading.value = false;
      }
    };

    return {
      isAgreed,
      selectedPayment,
      wechatQrCode,
      alipayQrCode,
      formattedAmount,
      selectPayment,
      handlePayment,
      isLoading
    };
  }
};
</script>

<style lang="less">
@primary: #FF7A45;
@neutral: #F5F5F5;
@gray-text: #666666;
@gray-light-text: #999999;
@border-gray: #d0cfcf;

.agreement {
  margin-bottom: 0.5rem;
  
  .agreement-label {
    display: flex;
    align-items: center;
    cursor: pointer;
  }
  
  .agreement-checkbox {
    appearance: none;
    -webkit-appearance: none;
    width: 0.8rem;
    height: 0.8rem;
    border-radius: 50%;
    border: 1.5px solid @border-gray;
    position: relative;
    outline: none;
    transition: all 0.2s;
    
    &:checked {
      border-color: @primary;
      
      &::after {
        content: '';
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: 0.4rem;
        height: 0.4rem;
        border-radius: 50%;
        background-color: @primary;
      }
    }
  }
  
  .agreement-text {
    margin-left: 0.5rem;
    font-size: 0.6rem;
    color: @gray-text;
  }
}

.payment-area {
  background-color: @neutral;
  border-radius: 0.5rem;
  padding: 0.8rem;
  
  .payment-info {
    margin-bottom: 0.5rem;
  }
  
  .payment-icons {
    font-size: 0.8rem;
    margin-bottom: 0.5rem;
    color: @gray-text;
  }
  
  .payment-grid {
    display: flex;
    gap: 1rem;
    margin-bottom: 1rem;
  }
  
  .payment-card {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 0.5rem;
    background-color: #fff;
    border-radius: 0.5rem;
    border: 2px solid @border-gray;
    cursor: pointer;
    transition: all 0.2s;
    min-height: 120px;
    justify-content: center;
    
    &:hover {
      box-shadow: 0 0 5px rgba(0,0,0,0.1);
    }
    
    // 选中状态：橙色边框
    &.selected {
      border-color: @primary;
      box-shadow: 0 0 5px rgba(255, 122, 69, 0.3);
    }
  }
  
  .payment-icon {
    width: 2rem;
    height: 2rem;
    background-repeat: no-repeat;
    background-size: contain;
    background-position: center;
    margin-bottom: 0.3rem;
  }
  
  .payment-name {
    font-size: 15px;
    color: @gray-text;
    margin: 0;
  }
  
  .price-info {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    gap: 1rem;
    margin-bottom: 1rem;
    
    .qr-code img {
      width: 3rem;
      height: 3rem;
      border-radius: 0.25rem;
      background-color: #fff;
      padding: 0.5rem;
    }
    
    .price-info-detail {
      text-align: right;
      
      .price-label {
        font-size: 0.6rem;
        color: @gray-light-text;
        margin: 0 0 0.25rem 0;
      }
      
      .price-amount {
        font-size: 1rem;
        font-weight: bold;
        color: @primary;
        margin: 0;
      }
    }
  }
}

.pay-btn {
  width: 100%;
  background-color: @primary;
  color: white;
  border: none;
  border-radius: 0.5rem;
  padding: 0.6rem;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background-color 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;
  
  &:hover {
    background-color: darken(@primary, 10%);
  }
  
  &:disabled {
    background-color: #ffb899;
    cursor: not-allowed;
  }
}
</style>