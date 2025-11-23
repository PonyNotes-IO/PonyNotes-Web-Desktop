<template>
  <div>
    <!-- 协议确认 -->
    <div class="agreement">
      <label class="agreement-label">
        <input 
          type="checkbox" 
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
            <!-- 微信内支付（JSAPI） -->
          <div
            v-if="isWechat"
            class="payment-card"
            @click="selectPayment('wechat_jsapi')"
            :class="{ 'selected': selectedPayment === 'wechat_jsapi' }"
          >
            <div class="payment-icon" :style="{ backgroundImage: `url(${require('./assets/img/wx_icon.png')})` }"></div>
            <p class="payment-name">微信内支付</p>
          </div>
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
          <!-- 修改成了 网页跳转移除二维码-->
          <!-- <div class="qr-code">
            <img 
              :src="selectedPayment === 'wechat' ? wechatQrCode : alipayQrCode" 
              alt="支付二维码"
            >
          </div> -->
          <div class="price-info-detail">
            <p class="price-label">应付金额</p>
            <p class="price-amount">¥{{ formattedAmount }}</p>
          </div>
        </div>
      </div>
      
      <!-- 支付按钮 -->
      <!-- <button class="pay-btn" @click="handlePayment"  :disabled="isLoading || !isAgreed || !selectedPayment">
        使用{{ selectedPayment === 'wechat' ? '微信' : selectedPayment === 'alipay' ? '支付宝' : '' }}支付
      </button> -->
      <!-- 支付按钮 -->
      <button 
        class="pay-btn" 
        @click="handlePayment"
        :disabled="isLoading || !isAgreed || !selectedPayment"
      >
        <template v-if="isLoading">
          <span class="loading-spinner"></span>
          <span>处理中...</span>
        </template>
        <template v-else>
          使用{{ selectedPayment === 'wechat' ? '微信' : '支付宝' }}支付
        </template>
      </button>
      
      <!-- 支付提示弹窗 -->
      <div class="payment-toast" v-if="showPaymentToast">
        <div class="toast-content">
          <button class="toast-close" @click="closePaymentToast">×</button>
          <div class="toast-icon">
            <span class="icon-info"></span>
          </div>
          <p class="toast-text">
            {{ paymentToastText }}
          </p>
          <button class="toast-btn" @click="checkPaymentStatus">
            已完成支付
          </button>
        </div>
      </div>
    
    </div>
  </div>
</template>

<script>
/* global WeixinJSBridge */ 
import WeixinJSBridge from 'weixin-js-sdk'
import { ref, computed, getCurrentInstance, onUnmounted, onMounted } from 'vue'
import { createPayment, pollPaymentStatus } from '@/api/payment'
export default {
  props: {
    amount: { type: Number, required: true },
    checked: {
      type: Boolean,
      default: false
    },
    productName: { type: String, default: '会员服务' }
  },
  setup(props) {
    // 获取当前实例用于emit
    const instance = getCurrentInstance();
    
    // 状态管理
    const isAgreed = ref(props.checked);
    const selectedPayment = ref(''); // 选中的支付方式：wechat/alipay
    const isLoading = ref(false); // 支付加载状态
    const showPaymentToast = ref(false); // 支付提示弹窗
    const paymentToastText = ref(''); // 支付提示文本
    const currentOrderNo = ref(''); // 当前订单号
    const pollTimer = ref(null); // 轮询定时器ID
    const isWechat = ref(false); // 是否在微信内
    const openid = ref(''); // 微信用户 openid
    const formattedAmount = computed(() => {
      return props.amount.toFixed(2);
    });

    // 检查是否在微信浏览器内
    const checkEnv = () => {
      const ua = navigator.userAgent.toLowerCase();
      isWechat.value = ua.includes('micromessenger');
    };

    // 从 URL 中获取参数
    const getUrlParam = (name) => {
      const reg = new RegExp(`(^|&)${name}=([^&]*)(&|$)`);
      const r = window.location.search.substr(1).match(reg);
      return r ? decodeURIComponent(r[2]) : null;
    };

    // 通过 code 获取 openid
    const getOpenid = async () => {
      const code = getUrlParam('code');
      if (code) {
        try {
          // 调用后端接口换取 openid
          const response = await instance.proxy.$api.get('/wechat/openid', { code });
          if (response.code === 200) {
            openid.value = response.data.openid;
            localStorage.setItem('openid', openid.value); // 缓存 openid
          } else {
            alert('获取微信授权失败');
          }
        } catch (error) {
          console.error('获取 openid 失败:', error);
          alert('获取微信授权失败');
        }
      } else if (!localStorage.getItem('openid')) {
        // 没有 code 且没有缓存的 openid，跳转微信授权页面
        const appid = '你的公众号 AppID';
        const redirectUri = encodeURIComponent(window.location.href);
        window.location.href = `https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appid}&redirect_uri=${redirectUri}&response_type=code&scope=snsapi_base&state=pay#wechat_redirect`;
      } else {
        // 有缓存的 openid，直接使用
        openid.value = localStorage.getItem('openid');
      }
    };
    // 选择支付方式
    const selectPayment = (type) => {
      if (isLoading.value) return;
      selectedPayment.value = type;
    };

    // 清除轮询
    const clearPolling = () => {
      if (pollTimer.value) {
        clearInterval(pollTimer.value);
        pollTimer.value = null;
      }
    };

    // 关闭支付提示弹窗
    const closePaymentToast = () => {
      showPaymentToast.value = false;
      clearPolling(); // 关闭弹窗时停止轮询
    };

    // 检查支付状态
    const checkPaymentStatus = () => {
      if (!currentOrderNo.value) return;
      
      isLoading.value = true;
      pollPaymentStatus(
        currentOrderNo.value,
        () => {
          // 支付成功
          isLoading.value = false;
          showPaymentToast.value = false;
          clearPolling(); // 支付成功停止轮询
          instance.emit('paymentSuccess', {
            orderNo: currentOrderNo.value,
            amount: props.amount,
            paymentType: selectedPayment.value
          });
        },
        (error) => {
          // 支付失败
          isLoading.value = false;
          if (error === 'expired') {
            alert('支付已超时，请重新发起支付');
            closePaymentToast();
          } else {
            alert('尚未检测到支付成功，请确认是否已完成支付');
          }
        }
      );
    };

    // 调起微信 JSAPI 支付
    const invokeWechatPay = (payParams) => {
      if (typeof WeixinJSBridge === 'undefined') {
        if (document.addEventListener) {
          document.addEventListener('WeixinJSBridgeReady', () => onBridgeReady(payParams), false);
        } else if (document.attachEvent) {
          document.attachEvent('WeixinJSBridgeReady', () => onBridgeReady(payParams));
          document.attachEvent('onWeixinJSBridgeReady', () => onBridgeReady(payParams));
        }
      } else {
        onBridgeReady(payParams);
      }
    };

    const onBridgeReady = (payParams) => {
      WeixinJSBridge.invoke('getBrandWCPayRequest', {
        appId: payParams.appId,
        timeStamp: payParams.timeStamp,
        nonceStr: payParams.nonceStr,
        package: payParams.package,
        signType: payParams.signType,
        paySign: payParams.paySign
      }, (res) => {
        if (res.err_msg === 'get_brand_wcpay_request:ok') {
          // 支付成功
          instance.emit('paymentSuccess', {
            orderNo: payParams.outTradeNo,
            amount: props.amount,
            paymentType: selectedPayment.value
          });
        } else if (res.err_msg === 'get_brand_wcpay_request:cancel') {
          alert('用户取消支付');
        } else {
          alert('支付失败，请重试');
        }
        WeixinJSBridge.log(res.err_msg);
      });
    };

    // 处理支付
    const handlePayment = async () => {
      // 验证：必须同意协议且选择支付方式
      console.log('是否同意协议:', isAgreed.value);
      if (!isAgreed.value) {
        alert('请先确认《会员服务》和《隐私协议》');
        return;
      }
      if (!selectedPayment.value) {
        alert('请选择支付方式');
        return;
      }
      // 清除可能存在的旧轮询
      clearPolling();

      // 开始支付流程
      isLoading.value = true;
      
      try {
        console.log(props.productName)
        // 调用后端接口获取支付链接
        const response = await createPayment({
          // amount: props.amount,
          amount: 0.01, // 测试金额0.01元
          paymentType: selectedPayment.value,
          productName: props.productName || '会员服务'
        });
        
        if (response.status === 200 && response.data?.data) {
          console.log('支付链接获取成功:', response.data);
          const payUrl = response.data.data.payUrl;
          const orderNo = response.data.data.orderNo;
          
          if (!payUrl) {
          
            isLoading.value = false;
            return;
          }  
          
          // 保存当前订单号
          currentOrderNo.value = orderNo;
          
          // 设置支付提示文本
          paymentToastText.value = `已为您打开${selectedPayment.value === 'wechat' ? '微信' : '支付宝'}支付页面，请完成支付`;
          // 处理支付宝表单提交
          if (selectedPayment.value === 'alipay') {
            // 创建临时容器存放表单1
            const tempDiv = document.createElement('div');
            tempDiv.style.display = 'none';
            tempDiv.innerHTML = payUrl;
            document.body.appendChild(tempDiv);
            
            // 触发表单提交
            const form = tempDiv.querySelector('form');
            if (form) {
              form.target = '_blank'; // 在新窗口打开支付页面
              form.submit();
            }
            
            // 移除临时容器
            setTimeout(() => {
              document.body.removeChild(tempDiv);
            }, 1000);
          } else if (selectedPayment.value === 'wechat') {
              // 微信支付仍使用窗口打开方式
             
              if (isWechat.value && response.data.data) {
                // 微信内支付使用 JSAPI 调起
                invokeWechatPay(response.data.data);
              }else {
                window.open(payUrl, '_blank');
              }
          }
          // 延迟5秒再开始轮询（给支付宝足够时间创建订单）
          setTimeout(() => {
            pollTimer.value = setInterval(() => { //pollInterval
              pollPaymentStatus(
                orderNo,
                () => {
                  // clearInterval(pollInterval);
                  // 支付成功处理
                  clearPolling();
                  showPaymentToast.value = false;
                  instance.emit('paymentSuccess', {
                    orderNo,
                    amount: props.amount,
                    paymentType: selectedPayment.value
                  });
                },
                (error) => {
                  console.log('支付状态查询中...', error);
                },
                () => {
                  // clearInterval(pollInterval);
                  clearPolling();
                  alert('支付超时，请检查支付状态或重新发起支付');
                  showPaymentToast.value = false;
                }
              );
            }, 5000); // 每3秒查询一次
          }, 5000); // 延迟5秒开始轮询
          
        } else {
          alert(response.msg || '创建支付订单失败');
        }
      } catch (err) {
        console.error('支付请求失败:', err);
        alert('支付发起失败，请重试');
      } finally {
        isLoading.value = false;
      }
    };
    // 组件挂载时初始化环境判断
    onMounted(() => {
      checkEnv();
      if (isWechat.value) getOpenid(); // 微信内才获取openid
    });
    // 组件卸载时清除轮询
    onUnmounted(() => {
      clearPolling();
    });
    
    return {
      isAgreed,
      selectedPayment,
      formattedAmount,
      selectPayment,
      handlePayment,
      isLoading,
      showPaymentToast,
      paymentToastText,
      checkPaymentStatus,
      closePaymentToast,
      isWechat
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