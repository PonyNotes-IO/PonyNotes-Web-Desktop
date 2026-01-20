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
        <span class="text_4">请验证手机号</span>
        </div>
        <!-- 输入框区域  :type="isPhoneNumber ? 'number' : 'text'"-->
        <div class="box_2 flex-row">
            <div class="input_1 flex-col Input" >
            <van-field
                class="text_7 InputArea"
                placeholder="输入邮箱或者手机号"
                v-model="phoneNumber"
                type="text"
                @input="validateInput"
            ></van-field>
            </div>
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
               <span v-if="hasSentCode">重新获取验证码</span><span v-else>发送验证码</span>
            </button>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 错误提示 - 红色字体 -->
    <div v-if="showError" class="text-wrapper_4 flex-row error-message">
      <span class="text_13">验证码错误，请重新输入</span>
    </div>
    
    <!-- 下一步按钮        :disabled="isVerifying"-->
    <div class="section_4 flex-row justify-center">
      <button 
        class="button_1 flex-col" 
        @click="verifyCode"
      >
        <span v-if="!isVerifying" class="text_14">下一步</span>
        <span v-if="isVerifying" class="text_14">验证中...</span>
      </button>
    </div>
  </div>
  </div>
</template>

<script>
// import { sendCaptcha, verifyCaptcha } from '../../utils/captchaUtil.js';
import scaleMixin  from '../../utils/scale';
import { verifyCaptcha,sendCaptcha, bindPhone } from '../../api/ponynote_login.js';

export default {
  mixins: [scaleMixin], // 使用混入
  data() {
    return {
      // 从父页面接收的参数
      accountType: '',
      isFirstLogin: false,
      account: '',
      phone: '',
      
      // 手机号相关
      phoneNumber: '',
      phoneError: '',

      // 验证码相关
      codeArray: ['', '', '', '', '', ''],
      focusedIndex: 0,
      showError: false,
      isCountingDown: false,
      countdownSeconds: 60,
      countdownInterval: null,
      isVerifying: false,
      hasSentCode: false,  // 新增：标记是否已发送过验证码
    };
  },
  computed: {
    // 联系方式（优先使用phone，没有则使用account）
    contact() {
      if (this.accountType === 'email') {
        // return this.inputValue || this.account;
         return this.phoneNumber || this.phone || this.account;
      } else {
        // return this.inputValue || this.phone;
        return this.phoneNumber || this.phone || this.account;
      }
     
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
    
    // 如果有传入的手机号，自动填充
    if (this.phone) {
      this.phoneNumber = this.phone;
      this.validatePhoneNumber(this.phone);
    }

    // 自动聚焦第一个输入框
    this.$nextTick(() => {
      this.$refs.codeInput0?.focus();
    });
       
    // 启动倒计时
    // this.startCountdown();
  },
  beforeUnmount() {
    // 清除定时器
    if (this.countdownInterval) {
      clearInterval(this.countdownInterval);
    }
  },
  methods: {

    // 验证手机号格式
    // 验证手机号
    validatePhoneNumber(value) {
      // 清除非数字字符
      this.phoneNumber = value.replace(/\D/g, '');
      
      // 手机号验证规则：11位数字，以1开头
      const phoneReg = /^1[3-9]\d{9}$/;
      
      if (!this.phoneNumber) {
        this.phoneError = '请输入手机号';
      } else if (this.phoneNumber.length < 11) {
        this.phoneError = '请输入11位手机号';
      } else if (!phoneReg.test(this.phoneNumber)) {
        this.phoneError = '请输入有效的手机号';
      } else {
        this.phoneError = '';
      }
    },
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
      this.hasSentCode = true;  // 标记为已发送过
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
        if (this.contact === '' || this.contact === null){
          this.isVerifying = false;
          console.log("手机号为空")
          this.$toast.fail('手机号为空，请输入手机号')
          return;
        }
        console.log(this.contact + "accountType" + this.accountType +"account"+this.account)

        let sendCaptchaResult = this.sendCaptchaAction(this.contact,'phone'); // 假设的API调
        if (sendCaptchaResult !== '200') {
          this.isVerifying = false;
          console.log("验证码发送失败"+sendCaptchaResult)
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
          return;
        }
      }
    },
    
    // 验证验证码
    verifyCode() {
      // 先验证手机号
      this.validatePhoneNumber(this.phoneNumber);
      if (this.phoneError) {
        return;
      }

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
        const isCodeValid = this.validateCodeAction(this.phoneNumber,code,'phone');
        var nextPath = '/login/login_set_password'; // 绑定成功后设置密码
        if (isCodeValid) {
          // 验证码验证通过，触发绑定绑定成功登录
          let bindPhoneResult = this.bindPhoneAction(this.account,this.phoneNumber,code); // 假设的API调用
          if (bindPhoneResult ===false) {
            this.isVerifying = false;
            this.$toast.fail('绑定失败，请重试')
            console.log("绑定失败"+bindPhoneResult.data)
            return;
          }
          this.$router.push({
            path: nextPath,
            query: {
              accountType: this.accountType,
              account: this.account,
              phone: this.phoneNumber,
              isFirstLogin: this.isFirstLogin,
              isFirstEmailLogin: this.accountType === 'email' && this.isFirstLogin
            }
          });
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

    async sendCaptchaAction(inputValue,accountType) {
      const captchaResponse  = await  sendCaptcha({
            accountType: accountType, 
            account: inputValue
          });
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
          this.isVerifying = true;
          this.startCountdown();
          return true;
        }
    },

    async validateCodeAction(inputValue,code,accountType) {
      const validateCodeResponse  = await verifyCaptcha({
            accountType: accountType, 
            inputValue: inputValue,
            code:code
      });
      let result = false
      if (validateCodeResponse.status !== 200) {
          this.$toast.fail('验证码发送失败，请重试')
          console.log("验证码发送失败，请重试"+validateCodeResponse.data)
          console.log(validateCodeResponse )
      }else if(validateCodeResponse.data.code !== 200){
        this.$toast.fail(validateCodeResponse.data.msg)
      }else{
        result = true
        this.$toast.success('验证码发送成功')
      }

      return result;
    },  
    async bindPhoneAction(email,phone,code) {
      const bindPhoneResult  = await bindPhone({
            accountType: 'email',
            loginType: 'code',
            email: email,
            phone: phone,
            code:code
      });
      let result = false
      if (bindPhoneResult.status !== 200) {
          this.$toast.fail('验证码发送失败，请重试')
          console.log("验证码发送失败，请重试"+bindPhoneResult.data)
          console.log(bindPhoneResult )
      }else if(bindPhoneResult.data.code !== 200){
        this.$toast.fail(bindPhoneResult.data.msg)
      }else{
        result = true
        this.$toast.success('验证码发送成功')
      }

      return result;
    },      
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

.text_7 {
  overflow-wrap: break-word;
  background-color: darkgray;
  color: rgb(193, 188, 188);
  font-size: 20px;
  font-family: PingFangSC-Medium;
  font-weight: 500;
  text-align: left;
  white-space: nowrap;
  line-height: 28px;
  padding: 0 0 0 0px;
  background: none;
  border: 0;
  flex: auto;
}
.input_1 {
  background-color: rgba(35, 35, 35, 1);
  color: rgba(255, 255, 255, 1);
  border-radius: 8px;
  border: 1px solid rgba(69, 69, 69, 1);
  padding-left: 20px;
  width: 550px;
  height: 56px;
  padding-right: 20px;
}
.input_1>>>.van-field__body {
  height: 56px;
  color: white;
}
.input_1>>>.van-field__control {
  color: white;
}
.text-group_1 {
    margin: 80px 0 30px 0;
}

</style>
<style scoped>
.scale-root {
  overflow: hidden;
}
</style>