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
import { ref, computed } from 'vue'
import { createPayment, pollPaymentStatus } from '@/api/payment'
export default {
  props: {
    amount: { type: Number, required: true },
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

    // 生成支付二维码
    const generatePaymentQrCode = async (type) => {
      try {
        const response = await createPayment({
          amount: props.amount,
          paymentType: type
        });
        
        if (response.code === 200) {
          const qrCodeUrl = response.data.qrCodeUrl;
          if (type === 'wechat') {
            wechatQrCode.value = qrCodeUrl;
          } else {
            alipayQrCode.value = qrCodeUrl;
          }
          
          // 开始轮询支付状态
          pollPaymentStatus(
            response.data.orderNo,
            () => {
              // 支付成功
              alert('支付成功！');
              this.$emit('paymentSuccess', {
                orderNo: response.data.orderNo,
                amount: props.amount,
                paymentType: type
              });
            },
            (error) => {
              // 支付失败
              if (error === 'expired') {
                alert('支付已超时，请重新发起支付');
              } else {
                alert('支付失败，请重试');
              }
            },
            () => {
              // 轮询超时
              alert('支付超时，请重新发起支付');
            }
          );
        } else {
          alert(response.msg || '创建支付订单失败');
        }
      } catch (err) {
        console.error('生成支付二维码失败:', err);
        alert('支付方式加载失败，请重试');
      }
    };

    // 处理支付
    const handlePayment = () => {
      // 验证：必须同意协议且选择支付方式
      if (!isAgreed.value) {
        alert('请先确认《会员服务》和《隐私协议》');
        return;
      }
      if (!selectedPayment.value) {
        alert('请选择支付方式');
        return;
      }

      // 开始支付流程
      isLoading.value = true;
      // 生成二维码
      generatePaymentQrCode(selectedPayment.value);
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