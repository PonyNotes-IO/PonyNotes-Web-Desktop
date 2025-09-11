<template>
  <div class="page flex-col">
    <!-- 返回按钮 -->
    <div class="image-wrapper_1 flex-row">
      <img
        class="image_3"
        referrerpolicy="no-referrer"
        src="./assets/img/reback.png"
        @click="$router.go(-1)"
        alt="返回"
      />
    </div>
    
    <!-- 标题区域 -->
    <div class="section_2  flex-col  justify-between">
      <div class="text-group_1 flex-row">
        <span class="text_4">请输入验证码</span>
      </div>
      <div class="text-wrapper_1 flex-row">
        <span class="text_5">6位验证码已发送至</span>
        <span class="text_6"></span>
        <span class="text_7">{{ maskedContact }}</span>
        <span class="text_8">，有效期15分钟。</span>
      </div>
    </div>


    <!-- 验证码输入框 - 居中显示 -->
    <div class="verification-code flex-row justify-center gap-2">
      <input 
        type="text" 
        maxlength="1" 
        class="box_1" 
        v-for="(_, index) in 6" 
        :key="index"
        v-model="codeArray[index]"
        @input="handleInput(index)"
        @keydown="handleKeydown($event, index)"
        @focus="handleFocus(index)"
        :class="{ 'focused': focusedIndex === index }"
        :ref="`codeInput${index}`"
      >
    </div>
    
    <!-- 验证码输入和倒计时区域 -->
    <div class="section_3 flex-row justify-between">
      <span class="text-group_2 flex-col">收不到验证码？</span>
      <!-- 倒计时区域 -->
      <div class="image-text_1 flex-col">
        <div class="image-text_2 ">
          <div class="text-wrapper_2">
            <span v-if="isCountingDown" class="text_9">{{ countdownSeconds }}</span>
            <span v-if="isCountingDown" class="text_10">秒后重新获取验证码</span>
            <button 
              v-if="!isCountingDown" 
              class="resend-btn"
              @click="resendCode"
            >
              重新获取验证码
            </button>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 错误提示 - 红色字体 -->
    <div v-if="showError" class="text-wrapper_4 flex-row error-message">
      <span class="text_13">验证码错误，请重新输入</span>
    </div>
    
    <!-- 下一步按钮 -->
    <div class="section_4 flex-row justify-center">
      <button 
        class="button_1 flex-col" 
        @click="verifyCode"
        :disabled="isVerifying"
      >
        <span v-if="!isVerifying" class="text_14">下一步</span>
        <span v-if="isVerifying" class="text_14">验证中...</span>
      </button>
    </div>
  </div>
</template>

<script>
import { sendCaptcha, verifyCaptcha } from '../../utils/captchaUtil.js';
export default {
  data() {
    return {
      // 从父页面接收的参数
      accountType: '',
      isFirstLogin: false,
      account: '',
      phone: '',
      
      // 验证码相关
      codeArray: ['', '', '', '', '', ''],
      focusedIndex: 0,
      showError: false,
      isCountingDown: true,
      countdownSeconds: 60,
      countdownInterval: null,
      isVerifying: false
    };
  },
  computed: {
    // 联系方式（优先使用phone，没有则使用account）
    contact() {
      return this.phone || this.account;
    },
    // 脱敏处理的联系方式
    maskedContact() {
      if (!this.contact) return '';
      
      // 手机号脱敏
      if (this.contact.length === 11 && /^\d+$/.test(this.contact)) {
        return `+86 ${this.contact.substr(0, 3)} **** ${this.contact.substr(7)}`;
      }
      // 邮箱脱敏
      else if (this.contact.includes('@')) {
        const [localPart, domain] = this.contact.split('@');
        if (localPart.length > 2) {
          return localPart[0] + '*'.repeat(localPart.length - 2) + '@' + domain;
        }
        return this.contact;
      }
      return this.contact;
    }
  },
  mounted() {
    // 从路由参数获取数据
    this.accountType = this.$route.query.accountType || '';
    this.isFirstLogin = this.$route.query.isFirstLogin === 'true';
    this.account = this.$route.query.account || '';
    this.phone = this.$route.query.phone || '';
    this.lastPageAction = this.$route.query.lastPageAction || '';
    
    // 自动聚焦第一个输入框
    this.$nextTick(() => {
      this.$refs.codeInput0?.focus();
    });
    
    // 启动倒计时
    this.startCountdown();
  },
  beforeUnmount() {
    // 清除定时器
    if (this.countdownInterval) {
      clearInterval(this.countdownInterval);
    }
  },
  methods: {
    // 处理输入事件
    handleInput(index) {
      // 只允许输入数字
      this.codeArray[index] = this.codeArray[index].replace(/[^0-9]/g, '');
      
      // 自动跳到下一个输入框
      if (this.codeArray[index] && index < 5) {
        this.focusedIndex = index + 1;
        this.$refs[`codeInput${index + 1}`]?.focus();
      }
    },
    
    // 处理键盘事件
    handleKeydown(event, index) {
      // 支持回退键
      if (event.key === 'Backspace' && !this.codeArray[index] && index > 0) {
        this.focusedIndex = index - 1;
        this.$refs[`codeInput${index - 1}`]?.focus();
      }
    },
    
    // 处理聚焦事件
    handleFocus(index) {
      this.focusedIndex = index;
    },
    
    // 开始倒计时
    startCountdown() {
      this.isCountingDown = true;
      this.countdownSeconds = 60;
      
      this.countdownInterval = setInterval(() => {
        this.countdownSeconds--;
        
        if (this.countdownSeconds <= 0) {
          clearInterval(this.countdownInterval);
          this.isCountingDown = false;
        }
      }, 1000);
    },
    
    // 重新发送验证码
    resendCode() {
      if (!this.isCountingDown) {
        // 模拟重新发送验证码的API请求
        this.isVerifying = true;

        sendCaptcha({
          axiosInstance: this.$axios,
          inputValue: this.contact,
          isPhoneNumber: this.accountType === 'phone',
          isFirstLogin: this.isFirstLogin,
          isFirstEmailLogin: this.accountType === 'email' && this.isFirstLogin,
          setIsSendingCaptcha: (isSending) => {
            this.isVerifying = isSending;
          },
          startCountdown: () => {}
        })
        
        // 模拟网络请求延迟
        setTimeout(() => {
          this.isVerifying = false;
          
          // 重置输入框
          this.codeArray = ['', '', '', '', '', ''];
          this.focusedIndex = 0;
          this.showError = false;
          this.$refs.codeInput0?.focus();
          
          // 重新开始倒计时
          this.startCountdown();
          
          // 这里可以添加提示：验证码已重新发送
        }, 800);
      }
    },
    
    // 验证验证码
    verifyCode() {
      // 拼接验证码
      const code = this.codeArray.join('');
      
      // 验证验证码是否完整
      if (code.length !== 6) {
        this.showError = true;
        this.errorMessage = '请输入完整的6位验证码';
        return;
      }
      
      // 开始验证
      this.isVerifying = true;
      this.showError = false;
      
      // 模拟API请求验证验证码
      setTimeout(() => {
        // 实际项目中应替换为真实的API验证
        const isCodeValid = this.validateCode(code);
        
        if (isCodeValid) {
          // 验证成功，根据条件进入下一个页面
          if (this.isFirstLogin) {
            // 首次登录，跳转到完善信息页面
            this.$router.push({
              path: '/complete-info',
              query: {
                accountType: this.accountType,
                account: this.account,
                phone: this.phone
              }
            });
          } else {
            // 非首次登录，跳转到首页或其他页面
            this.$router.push('/home');
          }
        } else {
          // 验证失败，显示红色错误提示
          this.isVerifying = false;
          this.showError = true;
          
          // 添加输入框震动效果
          const inputs = document.querySelectorAll('.code-input');
          inputs.forEach(input => {
            input.classList.add('error-shake');
            
            setTimeout(() => {
              input.classList.remove('error-shake');
            }, 1000);
          });
        }
      }, 1000);
    },
    
  
    validateCode(code) {
      // 实际项目中应该调用后端API验证
      // 这里仅作演示，正确验证码为123456
      const verfityCaptcha = () => {
            verifyCaptcha({
              axiosInstance: this.$axios,
              captchaValue: code,
              captchaType: 'login',
            }).then(result => {
              // 这里的 result 是 verifyCaptcha 的返回值
              console.log('验证验证码的结果:', result);
            }).catch(error => {
              // 捕获可能的错误（虽然函数内部已处理，但可额外处理）
              console.error('验证失败:', error);
            });
      };

      if (verfityCaptcha) {
        var nextPath = '/index'; // 默认跳转路径
        
        // 验证码验证成功，根据条件跳转页面
       if (this.accountType === 'phone' &&this.isFirstLogin) {
          nextPath = '/login/login_set_password';
          this.lastPageAction = 'first_phone_login_validate_captcha';
        } else if (this.accountType === 'email' && this.isFirstEmailLogin) {
          // nextPath = '/login/login_bind_phone';
           nextPath = '/login/login_set_password';
          this.lastPageAction = 'first_email_login_validate_captcha';
        }else if (this.lastPageAction && this.lastPageAction == 'forget_password') {
          nextPath = '/login/login_reset_password';
          this.lastPageAction = 'forget_paasword_validate_captcha';
        }else{
          nextPath = '/account';
          this.lastPageAction = 'login_validate_captcha';
        }
        this.$router.push({
          path: nextPath,
          query: {
            accountType: this.accountType,
            account: this.account,
            phone: this.phone,
            isFirstLogin: this.isFirstLogin,
            isFirstEmailLogin: this.accountType === 'email' && this.isFirstLogin,
            lastPageAction: this.lastPageAction
          }
        });
        //   // 手机验证码验证逻辑
        return true; // 假设验证通过跳转页面，否则返回false
      }else{
        return false;
      }
      // return code === '123456';
    }
  }
};
</script>
<style scoped lang="css" src="./assets/login_verify_code.css" />
<style scoped>
/* 验证码输入框样式 */
.verification-code {
  margin-top: 80px;
  width: 100%;
}

.code-input {
  width: 50px;
  height: 50px;
  text-align: center;
  font-size: 24px;
  border: 1px solid #ccc;
  border-radius: 8px;
}

.code-input.focused {
  border-color: #4285f4;
  outline: none;
  box-shadow: 0 0 0 2px rgba(66, 133, 244, 0.2);
}

/* 错误提示样式 */
.error-message {
  color: #ff4d4f;
  margin: 10px 0;
  height: 20px;
}

/* 错误震动动画 */
@keyframes shake {
  0%, 100% { transform: translateX(0); }
  20%, 60% { transform: translateX(-5px); }
  40%, 80% { transform: translateX(5px); }
}

.error-shake {
  animation: shake 0.5s ease-in-out;
  border-color: #ff4d4f !important;
}

/* 重新获取按钮样式 */
.resend-btn {
  color: #4285f4;
  background: none;
  border: none;
  padding: 0;
  cursor: pointer;
  font-size: 20px;
}

.resend-btn:hover {
  text-decoration: underline;
}

/* 按钮禁用样式 */
.button_1:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}
</style>
