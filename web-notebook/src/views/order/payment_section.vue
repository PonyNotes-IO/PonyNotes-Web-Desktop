<template>
  <div>
    <!-- 协议确认 -->
    <div class="agreement">
      <label class="agreement-label">
        <input 
          type="checkbox" 
          class="agreement-checkbox"
          :checked="checked"
        >
        <span class="agreement-text">确认《会员服务》《隐私协议》</span>
      </label>
    </div>
    
    <!-- 支付区域 -->
    <div class="payment-area">
      <div class="payment-info">
        <div class="qr-code">
          <img src="https://picsum.photos/40/40" alt="二维码">
        </div>
        <div class="price-info">
          <p class="price-label">应付金额</p>
          <p class="price-amount">¥{{ amount }}</p>
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
  }
};
</script>

<style lang="less">
@primary: #FF7A45;
@neutral: #F5F5F5;
@gray-text: #666666;
@gray-light-text: #999999;

.agreement {
  margin-bottom: 1.5rem;
  
  .agreement-label {
    display: flex;
    align-items: center;
  }
  
  .agreement-checkbox {
    width: 1rem;
    height: 1rem;
    color: @primary;
    border-radius: 0.25rem;
    border-color: #ccc;
  }
  
  .agreement-text {
    margin-left: 0.5rem;
    font-size: 0.875rem;
    color: @gray-text;
  }
}

.payment-area {
  background-color: @neutral;
  border-radius: 0.5rem;
  padding: 1rem;
  
  .payment-info {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1rem;
  }
  
  .qr-code img {
    width: 4rem;
    height: 4rem;
    border-radius: 0.25rem;
  }
  
  .price-info {
    text-align: right;
    
    .price-label {
      font-size: 0.875rem;
      color: @gray-light-text;
      margin: 0 0 0.25rem 0;
    }
    
    .price-amount {
      font-size: 1.5rem;
      font-weight: bold;
      color: @primary;
      margin: 0;
    }
  }
  
  .pay-btn {
    width: 100%;
    background-color: @primary;
    color: white;
    border: none;
    border-radius: 0.5rem;
    padding: 0.625rem;
    font-size: 1rem;
    cursor: pointer;
    transition: background-color 0.2s;
    display: flex;
    align-items: center;
    justify-content: center;
    
    &:hover {
      background-color: darken(@primary, 10%);
    }
  }
}
</style>