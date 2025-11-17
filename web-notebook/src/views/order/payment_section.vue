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

<!-- <script>
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
    const showPaymentToast = ref(false); // 支付提示弹窗
    const paymentToastText = ref(''); // 支付提示文本
    const currentOrderNo = ref(''); // 当前订单号
    // 格式化金额
    const formattedAmount = computed(() => {
      return props.amount.toFixed(2);
    });

    // 选择支付方式
    const selectPayment = (type) => {
      if (isLoading.value) return;
      selectedPayment.value = type;
      // 生成对应支付方式的二维码（调用后端接口）
      //generatePaymentQrCode(type);
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
          } else {
            alert('尚未检测到支付成功，请确认是否已完成支付');
          }
        }
      );
    };
    // 生成支付二维码
    // const generatePaymentQrCode = async (type) => {
    //   try {
    //     const response = await createPayment({
    //       amount: props.amount,
    //       paymentType: type
    //     });
        
    //     if (response.code === 200) {
    //       const qrCodeUrl = response.data.qrCodeUrl;
    //       if (type === 'wechat') {
    //         wechatQrCode.value = qrCodeUrl;
    //       } else {
    //         alipayQrCode.value = qrCodeUrl;
    //       }
          
    //       // 开始轮询支付状态
    //       pollPaymentStatus(
    //         response.data.orderNo,
    //         () => {
    //           // 支付成功
    //           alert('支付成功！');
    //           this.$emit('paymentSuccess', {
    //             orderNo: response.data.orderNo,
    //             amount: props.amount,
    //             paymentType: type
    //           });
    //         },
    //         (error) => {
    //           // 支付失败
    //           if (error === 'expired') {
    //             alert('支付已超时，请重新发起支付');
    //           } else {
    //             alert('支付失败，请重试');
    //           }
    //         },
    //         () => {
    //           // 轮询超时
    //           alert('支付超时，请重新发起支付');
    //         }
    //       );
    //     } else {
    //       alert(response.msg || '创建支付订单失败');
    //     }
    //   } catch (err) {
    //     console.error('生成支付二维码失败:', err);
    //     alert('支付方式加载失败，请重试');
    //   }
    // };

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

      // 开始支付流程
      isLoading.value = true;
      // 生成二维码
      // generatePaymentQrCode(selectedPayment.value);
      try {
        // 调用后端接口获取支付链接
        const response = await createPayment({
          amount: props.amount,
          paymentType: selectedPayment.value,
          productName: instance.parent?.productName || '会员服务'
        });
        if (response.status === 200 && response.data?.data) {
          console.log('支付链接获取成功:', response.data);
          const payUrl = response.data.data.payUrl;
          const orderNo = response.data.data.orderNo;
          console.log('支付链接:', payUrl);
          if (!payUrl) {
            alert('支付链接获取失败，请重试');
            return;
          }  
          // 保存当前订单号
          currentOrderNo.value = orderNo;

        // 设置支付提示文本
          paymentToastText.value = `已为您打开${selectedPayment.value === 'wechat' ? '微信' : '支付宝'}支付页面，请完成支付`;
          // 打开支付页面
          const payWindow = window.open(payUrl, '_blank');
        //  // 1. 跳转到支付页面
        //   if (selectedPayment.value === 'wechat') {
        //     window.location.href = payUrl; // 新页面打开支付链接
        //   } else if (selectedPayment.value === 'alipay') {
        //     window.location.href = payUrl; // 新页面打开支付链接
        //   } else {
        //     alert('请在新打开的支付页面完成支付');
        //   }

          // 显示支付提示弹窗
          showPaymentToast.value = true;
          // 开始轮询支付状态（后台检测）
          const pollInterval = setInterval(() => {
            pollPaymentStatus(
              orderNo,
              () => {
                clearInterval(pollInterval);
                // 支付成功
                showPaymentToast.value = false;
                instance.emit('paymentSuccess', {
                  orderNo,
                  amount: props.amount,
                  paymentType: selectedPayment.value
                });
                // 如果支付窗口还在打开，关闭它
                if (payWindow && !payWindow.closed) {
                  payWindow.close();
                }
              },
              (error) => {
                // 支付失败不清除定时器，继续轮询
                console.log('支付状态查询中...', error);
              },
              () => {
                // 轮询超时
                clearInterval(pollInterval);
                alert('支付超时，请检查支付状态或重新发起支付');
              }
            );
          }, 3000); // 每3秒查询一次支付状态
          

          // 2. 支付完成后需要轮询结果（需后端配合
          // pollPaymentStatus(
          //   orderNo,
          //   () => {
          //     this.$emit('paymentSuccess', {
          //       orderNo,
          //       amount: props.amount,
          //       paymentType: selectedPayment.value
          //     });
          //   },
          //   (error) => {
          //     console.error('支付失败:', error);
          //   }
          // );
        } else {
          alert(response.msg);
        }
      } catch (err) {
        console.error('支付请求失败:', err);
        alert('支付发起失败，请重试');
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
</script> -->

<script>
import { ref, computed, getCurrentInstance, onUnmounted } from 'vue'
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
    // 获取当前实例用于emit
    const instance = getCurrentInstance();
    
    // 状态管理
    const isAgreed = ref(props.checked);
    const selectedPayment = ref(''); // 选中的支付方式：wechat/alipay
    const isLoading = ref(false); // 支付加载状态
    const showPaymentToast = ref(false); // 支付提示弹窗
    const paymentToastText = ref(''); // 支付提示文本
    const currentOrderNo = ref(''); // 当前订单号
    // 轮询定时器ID
    const pollTimer = ref(null);

    // 格式化金额
    const formattedAmount = computed(() => {
      return props.amount.toFixed(2);
    });

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
        // 调用后端接口获取支付链接
        const response = await createPayment({
          // amount: props.amount,
          amount: 0.01, // 测试金额0.01元
          paymentType: selectedPayment.value
        });
        
        if (response.status === 200 && response.data?.data) {
          console.log('支付链接获取成功:', response.data);
          const payUrl = response.data.data.payUrl;
          const orderNo = response.data.data.orderNo;
          
          if (!payUrl) {
            alert('支付链接获取失败，请重试');
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
          alert(response.msg || '创建支付订单失败');
        }
      } catch (err) {
        console.error('支付请求失败:', err);
        alert('支付发起失败，请重试');
      } finally {
        isLoading.value = false;
      }
    };

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