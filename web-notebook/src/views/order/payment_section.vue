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
<script src="https://res.wx.qq.com/open/js/jweixin-1.6.0.js"></script>
<script>
import { ref, computed, getCurrentInstance, onUnmounted,onMounted } from 'vue'
import { createPayment, pollPaymentStatus,getWxConfig } from '@/api/payment'
import { initWxConfig } from '@/utils/wechat'; // 你的 JS-SDK 初始化函数
import { UserInfoKey } from '@/utils/auth';
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
    // 微信支付流程：用户进入支付页 → 前端判断无 OpenID 
    // →跳转微信授权页 → 用户授权后回调 → 前端获取 code 
    // → 前端用 code 调用后端接口 → 后端用 code 换 OpenID → 后端缓存 OpenID（可选）
    // → 前端调用支付接口时传入 OpenID → 后端 `createWechatJsapiPay` 接收 OpenID 生成支付参数 
    // → 前端用参数调起微信支付

    // 微信openId
    const openId = ref('');
    // 获取当前实例用于emit
    const instance = getCurrentInstance();
    
    // 状态管理
    const isAgreed = ref(props.checked);
    const selectedPayment = ref(''); // 选中的支付方式：wechat/alipay
    const wechatQrCode = ref(''); // 微信支付二维码
    const alipayQrCode = ref(''); // 支付宝支付二维码
    const isLoading = ref(false); // 支付加载状态
    const showPaymentToast = ref(false); // 支付提示弹窗
    const paymentToastText = ref(''); // 支付提示文本
    const currentOrderNo = ref(''); // 当前订单号
    // 轮询定时器ID
    const pollTimer = ref(null);


    // 2. 从 URL 中提取微信回调的 code（关键：授权后微信会把 code 拼在 URL 上）
    const getCodeFromUrl = () => {
      const searchParams = new URLSearchParams(window.location.search);
      return searchParams.get('code'); // 提取 ?code=xxx 中的 code
    };


    // 3. 跳转微信授权页（获取 code）- 静默授权，用户无感知
    const redirectToWechatAuth = () => {
      const appId = 'wx09097cecc59d8571'; // 公众号 AppID（建议从后端接口获取，避免硬编码）
      const redirectUri = encodeURIComponent(window.location.href); // 授权后回调当前支付页（必须编码）
      const scope = 'snsapi_base'; // 静默授权（不弹授权弹窗，只能获取 OpenID）；若需用户信息用 snsapi_userinfo
      const state = 'jsapi_pay'; // 自定义状态值，回调时会原样返回（可选）

      // 微信授权接口地址（固定格式）
      const authUrl = `https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appId}&redirect_uri=${redirectUri}&response_type=code&scope=${scope}&state=${state}#wechat_redirect`;

      // 跳转授权页（网页端直接重定向）
      // window.location.href = authUrl;
    };
    // 格式化金额
    const formattedAmount = computed(() => {
      return props.amount.toFixed(2);
    });

    // 选择支付方式
    const selectPayment = (type) => {
      if (isLoading.value) return;
      selectedPayment.value = type;
    };

    //是否在微信环境中
    const isWeChatEnv = () => {
      const ua = window.navigator.userAgent.toLowerCase();
      const isWechat = ua.includes('micromessenger'); // 正确判断微信浏览器
      if (!isWechat) {
        alert('请在微信浏览器中打开以使用微信支付');
        return false;
      }
      return true;
    };
    // 获取openId并存储到本地
    const getOpenId = async () => {
      // 从URL中获取微信授权返回的code
      const code = new URLSearchParams(window.location.search).get('code');
      isLoading.value = true;
      if (code) {
        // 调用后端接口用code换openid（后端已有/getWechat/openid接口）
        const res = await getWechatOpenid(code); // 需新增api封装
        openId.value = res.data.data.openid;
      } else if (!openId.value) {
        // 未获取到code，跳转微信授权页面
        const appId = 'wx09097cecc59d8571'; // 从后端配置获取
        const redirectUri = encodeURIComponent(window.location.href); // 授权后回调当前页
        const authUrl = `https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appId}&redirect_uri=${redirectUri}&response_type=code&scope=snsapi_base&state=123#wechat_redirect`;
        
        // window.location.href = authUrl;
      }
    };
    // 4. 调用后端接口，用 code 换 OpenID
    const fetchOpenId = async (code) => {
      try {
        isLoading.value = true;
        const res = await getWechatOpenid(code); // 后端新增接口，下文会实现
        if (res.status === 200 && res.data?.data.openid) {
          // 缓存 OpenID 到本地（避免用户刷新页面后重复授权，有效期7天）
          localStorage.setItem('wx_openid', res.data.data.openid);
          localStorage.setItem('wx_openid_expire', Date.now() + 7 * 24 * 60 * 60 * 1000);
          openId.value = res.data.data.openid;
          return true;
        } else {
          throw new Error('获取用户信息失败');
        }
      } catch (err) {
        console.error('OpenID 兑换失败：', err);
        alert('微信授权失败，请刷新页面重试');
        return false;
      } finally {
        isLoading.value = false;
      }
    };

    // 5. 组件挂载时初始化：获取 OpenID（核心入口）
    onMounted(async () => {
      // 第一步：检查本地是否有缓存的 OpenID（且未过期）
      const cachedOpenId = localStorage.getItem('wx_openid');
      const expireTime = localStorage.getItem('wx_openid_expire');
      if (cachedOpenId && expireTime && Date.now() < Number(expireTime)) {
        openId.value = cachedOpenId;
        // 初始化 JS-SDK（已有逻辑）
        // await initWxConfig(window.location.href.split('#')[0]);
        // return;
      }

      // 第二步：本地无有效缓存，检查 URL 中是否有微信返回的 code
      const code = getCodeFromUrl();
      console.log('code:', code);
      // if (code) {
      //   // 有 code → 调用后端换 OpenID
      //   const success = await fetchOpenId(code);
      //   if (success) {
      //     await initWxConfig(window.location.href.split('#')[0]);
      //   } else {
      //     redirectToWechatAuth();
      //   }
      // } else {
      //   // 无 code → 跳转微信授权页获取 code
      //   redirectToWechatAuth();
      // }
    });


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
    const initWxConfig = async () => {
      const currentUrl = window.location.href.split('#')[0]; // 去除hash部分（微信签名要求）
      const wxConfig = await getWxConfig(currentUrl);
      const config = wxConfig.data.data;
      // 2. 配置微信JS-SDK
      wx.config({
        debug: false, // 调试模式
        appId: config.appId, // 公众号的唯一标识
        timestamp: config.timestamp, // 生成签名的时间戳
        nonceStr: config.nonceStr, // 生成签名的随机串
        signature: config.signature, // 签名
        jsApiList: ['chooseWXPay'] // 需要使用的JS接口列表
      })

      // 3. 配置成功回调
      wx.ready(() => {
        console.log('微信JS-SDK配置成功')
      })

      // 4. 配置失败回调
      wx.error((res) => {
        console.error('微信JS-SDK配置失败:', res)
      })
    };
    //  6. 处理支付
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
      console.log(props.productName)
      const paymentType = selectedPayment.value;
      const openId = ''; // 微信支付时需要传入openId，移动端可通过微信授权获取
      if (!paymentType) {
        alert('请选择支付方式');
        isLoading.value = false;
        return;
      }
      if (paymentType === 'wechhat') {
        console.log('发起微信支付');
        const code = new URLSearchParams(window.location.search).get('code');
        openId = getOpenId(); // 取openId
        console.log('openId:', openId);
        console.log('code:', code);
        
      } 
      const userInfo = localStorage.getItem(UserInfoKey);
      if (userInfo) {
        console.log('用户信息:', userInfo);
      } else {
        console.warn('未找到用户信息');
        // 返回登录页面
        alert('请先登录后再进行支付');
        this.$router.push('/login');
      }
      if (paymentType === 'wechat') {
        // 判断是否在微信环境中
        if (!isWeChatEnv()) {
          isLoading.value = false;
          //
          return;
        }
        // 步骤1：检查是否已有 OpenID（本地缓存）
        const cachedOpenId = localStorage.getItem('wx_openid');
        const expireTime = localStorage.getItem('wx_openid_expire');
        if (cachedOpenId && expireTime && Date.now() < Number(expireTime)) {
          openId.value = cachedOpenId; // 使用缓存的 OpenID
        } else {
          // 步骤2：无缓存，检查 URL 中是否有微信返回的 code
          const code = getCodeFromUrl();
          if (code) {
            // 有 code → 调用后端接口换 OpenID
            const success = await fetchOpenId(code);
            if (!success) {
              throw new Error('获取用户信息失败');
            }
          } else {
            // 无 code → 跳转微信授权页（仅此时才跳转）
            redirectToWechatAuth();
            isLoading.value = false; // 跳转前重置加载状态
            return; // 跳转后终止后续流程
          }
        }
      }
      // 调用后端接口获取支付链接
      const response = await createPayment({
        // amount: props.amount,
        amount: 0.01, // 测试金额0.01元
        paymentType: paymentType,
        productName: props.productName || '会员服务',
        userInfo: userInfo,
        openId: openId, // 微信支付时需要传入openId，
        url: window.location.href.split('#')[0] // 用于后端 JS-SDK 签名（可选）
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
        } else if (paymentType === 'wechat') {
            // 微信支付使用jsapi  代替h5支付方式，直接打开链接
            // 判断是否在微信环境中
            if (!isWeChatEnv()) {
              isLoading.value = false;
              return;
            }
            // 步骤1：检查是否已有 OpenID（本地缓存）
            const cachedOpenId = localStorage.getItem('wx_openid');
            const expireTime = localStorage.getItem('wx_openid_expire');
            if (cachedOpenId && expireTime && Date.now() < Number(expireTime)) {
              openId.value = cachedOpenId; // 使用缓存的 OpenID
            } else {
              // 步骤2：无缓存，检查 URL 中是否有微信返回的 code
              const code = getCodeFromUrl();
              if (code) {
                // 有 code → 调用后端接口换 OpenID
                const success = await fetchOpenId(code);
                if (!success) {
                  throw new Error('获取用户信息失败');
                }
              } else {
                // 无 code → 跳转微信授权页（仅此时才跳转）
                redirectToWechatAuth();
                isLoading.value = false; // 跳转前重置加载状态
                return; // 跳转后终止后续流程
              }
            }

            const payParams = JSON.parse(response.data.data); // 解析后端返回的参数
            // 调起微信支付
            wx.chooseWXPay({
              appId: payParams.appId,
              timestamp: payParams.timeStamp,
              nonceStr: payParams.nonceStr,
              package: payParams.package,
              signType: payParams.signType,
              paySign: payParams.paySign,
              success: (res) => {
                if (res.errMsg === 'chooseWXPay:ok') {
                  // 支付成功，开始轮询确认
                  startPolling(orderNo);
                }
              },
              fail: (err) => {
                console.error('支付调起失败：', err);
                alert('支付未完成，请重试');
              }
            });
        } else if (paymentType === 'wechat_native') {
            // 微信支付使用native  代替h5支付方式，直接打开链接
              const qrCodeUrl = res.data.qrCodeUrl;
              
              window.open(payUrl, '_blank');
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
        alert(response.msg || '创建支付订单失败微信支付需要openid');
      }
    };

    // 组件卸载时清除轮询
    onUnmounted(() => {
      clearPolling();
    });
    
    return {
      openId,
      isAgreed,
      selectedPayment,
      wechatQrCode,
      alipayQrCode,
      formattedAmount,
      selectPayment,
      handlePayment,
      isLoading,
      showPaymentToast,
      paymentToastText,
      checkPaymentStatus,
      closePaymentToast
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