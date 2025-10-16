<template>
  <div class="scale-root">
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
        class="box_1 code-input" 
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
  </div>
</template>

<script>
import scaleMixin  from '../../utils/scale';
import { verifyCaptcha,sendCaptcha, loginWithCode,registerUser} from '../../api/ponynote_login.js'; 
export default {
  mixins: [scaleMixin], // 使用混入
  data() {
    return {
      // 从父页面接收的参数
      accountType: '',
      isFirstLogin: false,
      isFirstEmailLogin: false,
      account: '',
      phone: '',
      lastPageAction: '',
      
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
    this.isFirstEmailLogin = this.$route.query.isFirstEmailLogin === 'true';
    this.account = this.$route.query.account || '';
    this.phone = this.$route.query.phone || '';
    this.lastPageAction = this.$route.query.lastPageAction || '';
    console.log('$refs:', this.$refs);
    console.log('获取到的账号类型：', this.accountType);
    console.log('是否首次登录：', this.isFirstLogin);
    console.log('是否首次邮箱登录：', this.isFirstEmailLogin);
    console.log('账号：', this.account);
    console.log('手机号：', this.phone);
    console.log('路由参数全貌：', this.$route.query); // 查看是否有 account 和 accountType
    console.log('初始 account：', this.account);
    console.log('上一个页面动作：', this.lastPageAction);
    // 自动聚焦第一个输入框
    this.$nextTick(() => {
      // 确保ref存在再调用focus
      if (this.$refs.codeInput0) {
        this.$refs.codeInput0[0]?.focus();
      }
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
    
    handleInput(index) {
      this.codeArray[index] = this.codeArray[index].replace(/[^0-9]/g, '');
      if (this.codeArray[index] && index < 5) {
        this.focusedIndex = index + 1;
        const nextRef = `codeInput${index + 1}`;
        // // 检查：$refs存在 + 是DOM元素 + 有focus方法
        // if (this.$refs[nextRef] && this.$refs[nextRef].focus) {
        //   this.$refs[nextRef].focus();
        // }
        const nextInput = this.$refs[nextRef]?.[0];
        if (nextInput?.focus) {
          nextInput.focus();
        }        
      }
    },

    handleKeydown(event, index) {
      if (event.key === 'Backspace' && !this.codeArray[index] && index > 0) {
        this.focusedIndex = index - 1;
        const prevRef = `codeInput${index - 1}`;
        // if (this.$refs[prevRef] && this.$refs[prevRef].focus) {
        //   this.$refs[prevRef].focus();
        // }
        const prevInput = this.$refs[prevRef]?.[0];
        if (prevInput?.focus) {
          prevInput.focus();
        }
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
    async resendCode() {
      if (!this.isCountingDown) {
        // 模拟重新发送验证码的API请求
        this.isVerifying = true;
        const captchaResponse  = await  sendCaptcha({
            accountType: this.accountType, 
            account:  this.account
          });
        console.log("验证码发送结果"+captchaResponse)
        
        if (captchaResponse.status !== 200) {
          this.$toast.fail('验证码发送失败，请重试')
          console.log("验证码发送失败，请重试"+captchaResponse.data)
          console.log(captchaResponse )
          return;
        }else if(captchaResponse.data.code !== 200){
          this.$toast.fail(captchaResponse.data.msg)
          return;
        }else{
          this.$toast.success('验证码发送成功')
          this.setIsSendingCaptcha  =true;
          this.startCountdown();
        }
        // 模拟网络请求延迟
        setTimeout(() => {
          this.isVerifying = false;
          
          // 重置输入框
          this.codeArray = ['', '', '', '', '', ''];
          this.focusedIndex = 0;
          this.showError = false;
          if (this.$refs.codeInput0) {
            this.$refs.codeInput0?.focus();
          }
          // 重新开始倒计时
          this.startCountdown();
          // 这里可以添加提示：验证码已重新发送
        }, 800);
      }
    },
    
    // 验证验证码
    async verifyCode() {
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
      let isCodeValid =await this.validateCode(this.account,code,this.accountType);
      console.log("验证码验证结果"+isCodeValid)
      if (isCodeValid) {
        var nextPath = '/index'; 
        if (this.lastPageAction==='index_login'){
          if (this.accountType === 'phone' &&this.isFirstLogin) {
            nextPath = '/login/login_set_password';
            this.lastPageAction = 'first_phone_login_validate_captcha';
          }else if (this.accountType === 'email' && this.isFirstEmailLogin) {
            nextPath = '/login/login_bind_phone';
            // nextPath = '/login/login_set_password';
            console.log('绑定手机' + this.accountType + ' ' + this.account)
            this.lastPageAction = 'first_email_login_validate_captcha';
          }else{
            nextPath = '/account';
            this.lastPageAction = 'login_validate_captcha';
          }
        }else if (this.lastPageAction==='login_with_password_forget_password'){
            nextPath = '/login/login_reset_password';
            this.lastPageAction = 'login_validate_captcha';
        }else if(this.lastPageAction==='login_with_password_switch_to_captcha_login'){
            nextPath = '/account';
            this.lastPageAction = 'login_validate_captcha';
        }else if(this.lastPageAction==='login_with_password_bind_phone'){
            nextPath = '/account';
            this.lastPageAction = 'login_validate_captcha';
        }else{
            nextPath = '/account';
            this.lastPageAction = 'login_validate_captcha';
        }
        if (nextPath === '/account') {//loginwithcode登录成功，跳转到首页
          let loginResult = this.loginWithCode(this.inputValue,code,this.accountType);
          if (!loginResult) {//登录成功，跳转到首页
            return false;
          }
        }
        if (nextPath === '/login/login_set_password') {//登录成功，跳转到设置密码页面l
          console.log("registerUserAction"+this.account + this.accountType)
          let register = this.registerUserAction(this.account,this.accountType);
          if (!register) {//注册成功
            return false;
          }
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
        this.showError = true;
        this.errorMessage = '验证码错误，请重新输入';
        // 模拟网络请求延迟
        setTimeout(() => {
          this.isVerifying = false;
          
          // 重置输入框
          this.codeArray = ['', '', '', '', '', ''];
          this.focusedIndex = 0;
          this.showError = false;
          this.$refs.codeInput0?.focus();
          // if (this.$refs.codeInput0) {
          //   this.$refs.codeInput0.focus();
          // }
          // 重新开始倒计时
          this.startCountdown();
          
          // 这里可以添加提示：验证码已重新发送
        }, 800);
        return false; // 验证失败，不跳转页面
      }
    },
    
  
    async validateCode(inputValue,code,accountType) {
      const validateCodeResponse  = await verifyCaptcha({
            accountType: accountType, 
            inputValue: inputValue,
            code:code
      });
      let result = false
      if (validateCodeResponse.status !== 200) {
          console.log("验证码发送失败，请重试"+validateCodeResponse.data)
          console.log(validateCodeResponse )
      } 
      if(validateCodeResponse.data.code !== 200){
        this.$toast.fail(validateCodeResponse.data.msg)
      }else{
        result = true
        this.$toast.success('验证码发送成功')
      }

      return result;
    },

    async loginWithCode(inputValue,code,accountType) {
      const loginWithCodeResponse  = await loginWithCode({
            accountType: accountType, 
            inputValue: inputValue,
            code:code
      });
      let result = false
      if (loginWithCodeResponse.status !== 200) {
          this.$toast.fail('登录失败，请重试')
          console.log("登录发送失败，请重试"+loginWithCodeResponse.data)
          console.log(loginWithCodeResponse )
      }else if(loginWithCodeResponse.data.code !== 200){
        this.$toast.fail(loginWithCodeResponse.data.msg)
      }else{
        result = true
        // 登录成功，存入token到localStorage
        const token = loginWithCodeResponse.data.token;
        localStorage.setItem('token', token); 
        localStorage.setItem('userInfo', inputValue);
        // 同时存入authToken（可能为兼容其他逻辑）
        localStorage.setItem('authToken',token);

        this.$toast.success('登录成功')

      }
      return result;
    },

    async registerUserAction(account,accountType) {
      const registerUserResponse  = await  registerUser ({
        account: account,
        accountType:accountType, // 账号类型，手机号或邮箱
      });
      let result = false
      if (registerUserResponse.status !== 200) {
          this.$toast.fail('注册失败，请重试')
          console.log("登录发送失败，请重试"+registerUserResponse.data)
          console.log(registerUserResponse )
      }else if(registerUserResponse.data.code !== 200){
        this.$toast.fail(registerUserResponse.data.msg)
      }else{
        result = true
        this.$toast.success('注册成功')
      }
      return result;
    },
    
    // 重新获取验证码的倒计时逻辑（此处仅为示意代码）
    // startCountdown() {
    //   let countdownTime = 60; // 初始时间设置为60秒
    //   this.resendBtnText = `${countdownTime}s后重新获取`;
    //   this.isResendDisabled = true; // 禁用按钮
    // }
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
<style scoped>
.scale-root {
  overflow: hidden;
}
</style>