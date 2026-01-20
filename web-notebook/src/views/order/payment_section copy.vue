<template>
  <div>
    <!-- 协议确认 -->
    <div class="agreement">
      <label class="agreement-label">
        <input 
          type="radio" 
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
        <div class="payment-icons flex-row">
          <span>选择支付方式</span>
        </div>

        <!-- 支付方式卡片（微信 + 支付宝 并排） -->
        <div class="payment-grid flex-row">
          <div  class="payment-card flex-col" 
            @click="selectPayment('wechat')" 
            :class="{ 'selected': selectedPayment === 'wechat' }"
          >
            <div class="payment-icon" :style="{ backgroundImage: `url(${require('./assets/img/wx_icon.png')})` }"></div>
            <p class="payment-name">微信支付</p>
          </div>
          <div 
            class="payment-card flex-col" 
            @click="selectPayment('alipay')" 
            :class="{ 'selected': selectedPayment === 'alipay' }"
          >
            <div class="payment-icon" :style="{ backgroundImage: `url(${require('./assets/img/alipay_icon.png')})` }"></div>
            <p class="payment-name">支付宝</p>
          </div>
        </div>

        <div class="price-info  flex-row">
          <div class="qr-code flex-col">
            <img src="./assets/img/pay_code_demo.png" alt="二维码">
          </div>
          <div class="price-info-detail flex-col">
            <p class="price-label">应付金额</p>
            <p class="price-amount">¥{{ amount }}</p>
          </div>
        </div>
      </div>
      <button class="pay-btn">
        <i class="fa fa-weixin mr-2"></i>
        <i class="fa fa-credit-card mr-2"></i>
        使用微信或者支付宝支付
      </button>
    </div>
  </div>
</template>

<script>
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
  data() {
    return {
      isAgreed: this.checked // 初始化协议同意状态
    };
  },
  computed: {
    formattedAmount() {
      // 格式化金额为两位小数
      return this.amount.toFixed(2);
    }
  },
  methods: {
    handlePayment() {
      if (this.isAgreed) {
        // 触发支付事件，将支付金额和同意状态传递给父组件
        this.$emit('payment', {
          amount: this.amount,
          agreed: this.isAgreed
        });
      } else {
        alert('请先确认《会员服务》和《隐私协议》');
      }
    }
  }
};
</script>

<style lang="less">
@primary: #FF7A45;
@neutral: #F5F5F5;
@gray-text: #666666;
@gray-light-text: #999999;

.agreement {
  margin-bottom: 0.5rem;
  
  .agreement-label {
    display: flex;
    align-items: center;
  }
  
  // .agreement-checkbox {
  //   width: 0.6rem;
  //   height: 0.6rem;
  //   color: @primary;
  //   border-radius: 0.5rem;
  //   border-color: #ccc;
  // }
  .agreement-checkbox {
    // 隐藏原生radio样式
    appearance: none;
    -webkit-appearance: none;
    width: 0.8rem;  // 适当放大，提升点击体验
    height: 0.8rem;
    border-radius: 50%;  // 圆形
    border: 1.5px solid #ccc;  // 未选中时灰色边框
    position: relative;  // 用于定位选中圆点
    outline: none;
    transition: all 0.2s;  // 状态切换动画
    
    // 选中状态样式
    &:checked {
      border-color: @primary;  // 选中时边框为橙色
      
      // 选中时的橙色圆点（通过伪元素实现）
      &::after {
        content: '';
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);  // 居中
        width: 0.4rem;  // 圆点大小（约为radio的1/2）
        height: 0.4rem;
        border-radius: 50%;
        background-color: @primary;  // 圆点为橙色
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
    // display: inline-flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 0.5rem;
  }

  
  // 支付方式卡片容器（flex 实现并排）
  .payment-grid {
    display: flex;
    gap: 1rem; // 卡片之间的间距
    margin-bottom: 1rem;
      // 单个支付卡片样式
    .payment-card {
      flex: 1; // 两个卡片等分宽度
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 0.3rem;
      background-color: #fff;
      border-radius: 0.5rem;
      border: 2px solid #d0cfcf;
      cursor: pointer;
      transition: all 0.2s;
      width: 150px; // 确保卡片宽度撑满容器
      height: 120px; // 确保卡片高度一致
      // 支付图标样式
      .payment-icon {
        width: 2rem;
        height: 2rem;
        background-repeat: no-repeat;
        background-size: contain;
        background-position: center;
        margin-bottom: 0.3rem;
      }
      // 支付方式名称
      .payment-name {
        font-size: 15px;
        color: @gray-text;
        margin-top: 3px;
      }

      &:hover {
        box-shadow: 0 0 5px rgba(0,0,0,0.1);
      }
      
      // 选中状态高亮（橙色边框）
      &.selected {
        border: 2px solid @primary;
        box-shadow: 0 0 5px rgba(255, 122, 69, 0.2);
      }
    }
  }
  

  .qr-code img {
    width: 3rem;
    height: 3rem;
    border-radius: 0.25rem;
    margin-left: auto;
  }
  .qr-code {
    margin-left: auto;
  }
  .price-info-detail {
    text-align: right;
    .payment-icons {
      font-size: 0.8rem;
      margin-bottom: 0.5rem;
    }
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

// // 支付区域样式
// .payment-area {
//   background-color: @neutral;
//   border-radius: 0.5rem;
//   padding: 0.8rem;
  
//   .payment-info {
//     margin-bottom: 0.5rem;
//   }
  
//   .payment-icons {
//     font-size: 0.8rem;
//     margin-bottom: 0.5rem;
//   }
  
//   // 支付方式卡片容器（flex 实现并排）
//   .payment-grid {
//     display: flex;
//     gap: 1rem; // 卡片之间的间距
//     margin-bottom: 1rem;
//   }
  
//   // 单个支付卡片样式
//   .payment-card {
//     flex: 1; // 两个卡片等分宽度
//     display: flex;
//     flex-direction: column;
//     align-items: center;
//     padding: 0.8rem;
//     background-color: #fff;
//     border-radius: 0.5rem;
//     border: 1px solid #eee;
//     cursor: pointer;
//     transition: all 0.2s;
    
//     &:hover {
//       box-shadow: 0 0 5px rgba(0,0,0,0.1);
//     }
    
//     // 选中状态高亮（橙色边框）
//     &.selected {
//       border: 1px solid @border-color;
//       box-shadow: 0 0 5px rgba(255, 122, 69, 0.2);
//     }
//   }
  
//   // 支付图标样式
//   .payment-icon {
//     width: 2rem;
//     height: 2rem;
//     background-repeat: no-repeat;
//     background-size: contain;
//     background-position: center;
//     margin-bottom: 0.5rem;
//   }
  
//   // 支付方式名称
//   .payment-name {
//     font-size: 0.7rem;
//     color: @gray-text;
//   }
  
//   // 金额信息
//   .price-info {
//     text-align: right;
    
//     .price-label {
//       font-size: 0.6rem;
//       color: @gray-light-text;
//       margin: 0 0 0.25rem 0;
//     }
    
//     .price-amount {
//       font-size: 1rem;
//       font-weight: bold;
//       color: @primary;
//       margin: 0;
//     }
//   }
// }
.pay-btn {
  width: 100%;
  background-color: @primary;
  color: white;
  border: none;
  border-radius: 0.5rem;
  padding: 0.5rem;
  font-size: 0.8rem;
  cursor: pointer;
  transition: background-color 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;
  
  &:hover {
    background-color: darken(@primary, 10%);
  }
}
</style>