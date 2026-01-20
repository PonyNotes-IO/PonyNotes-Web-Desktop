<template>
  <div class="page flex-col">
    <div class="box_2 flex-row">
      <MobileNavBar 
        :show-logo="true"
        :show-title="true"
        :show-login="true"
        :show-register="true"
        :show-more="true"
      />
    </div>
    <div class="text-group_1 flex-col">
      <span class="text_5">身份验证</span>
      <span class="text_6">为了你的账户安全，请先验证身份</span>
    </div>

      <!-- 手机号验证表单 -->
      <div v-if="verificationMode === 'phone' && !showCodeInput" class="space-y-4">
        <div class="relative text-wrapper_1 flex-row justify-between">
          <input type="tel" placeholder="请输入手机号" 
                 class="w-full bg-secondary border border-gray-dark rounded-lg px-4 py-3 text-white input-focus"
                 v-model="phoneNumber">
        </div>
        <div class="relative text-wrapper_style flex-row justify-between">
          <span class="w-full text-primary text-sm" @click="switchToEmail">切换验证方式</span>
        </div>
        
        <div class="relative text-wrapper_botton flex-row justify-between justify-center">
          <button class="w-full text_botton text-primary text-sm" @click="getVerificationCode">获取验证码</button>
        </div>
      </div>

      <!-- 邮箱验证表单 -->
      <div v-if="verificationMode === 'email' && !showCodeInput" class="space-y-4">
        <div class="relative text-wrapper_1 flex-row justify-between">
          <input type="email" placeholder="请输入邮箱地址" 
                 class="w-full bg-secondary border border-gray-dark rounded-lg px-4 py-3 text-white input-focus"
                 v-model="emailAddress">
        </div>
        
        <div class="relative text-wrapper_style flex-row justify-between">
          <span @click="switchToPhone" class="w-full text-primary text-sm">切换验证方式</span>
        </div>
        <div class="relative text-wrapper_botton flex-row justify-between justify-center">
          <button class="w-full text_botton text-primary text-sm" @click="getVerificationCode">获取验证码</button>
        </div>
      </div>

      <!-- 验证码输入区域 -->
      <div v-if="showCodeInput" class="space-y-4">
        <div class="relative text-wrapper_verify flex-row  items-center justify-between">
          <span class="text_7">使用{{ verificationMode === 'phone' ? '手机' : '邮箱' }} 
           {{ maskedContact }} 验证
          </span>
          <span @click="changeVerificationMethod" class="text-primary text_8 text-sm">切换验证</span>
        </div>
  
        <div class="relative text-wrapper_verify flex-row  items-center justify-between">
            <input type="number" placeholder="请输入验证码" 
                   class="w-full  input_1 flex-col Input InputArea"
                   v-model="verificationCode">
             <span class="text_botton"
                    :disabled="countdown > 0"
                    @click="resendCode">
              {{ countdown > 0 ? `${countdown}s后重发` : '重新获取' }}
              </span>       
        </div>

        <div class="text-wrapper_verify_msg text-gray-light text-sm text-center mt-2">
          <span class="text_11">已发送{{ verificationMode === 'phone' ? '短信' : '邮件' }}验证码到{{ verificationMode === 'phone' ? '绑定手机' : '邮箱' }}</span>
        </div>
        <div class="relative text-wrapper_verify_button flex-row  items-center justify-between">
          <button @click="cancelVerification" 
                  class="button_2 flex-col">
            取消
          </button>
          <button @click="completeVerification" 
                  class="button_3 flex-col">
            完成
          </button>
        </div>
      </div>
    <img
      class="image_2"
      referrerpolicy="no-referrer"
      src="./assets/img/SketchPnge6d918b7b08b0c49f7bbe81f62c278ef96fd1a7eef15d897c2004a8a671ec00c.png"
    />
    <MobileBottom />
  </div>
</template>
<script>
import { getuserinfo,sendCaptcha,loginWithCode,validateInput } from '../../../api/ponynote_login.js'; 

export default {
  data() {
    return {
      // 验证方式：'phone' 或 'email'
      verificationMode: 'phone',
      // 是否显示验证码输入区域
      showCodeInput: false,
      // 手机号
      phoneNumber: '',
      // 邮箱地址
      emailAddress: '',
      // 验证码
      verificationCode: '',
      // 倒计时
      countdown: 0,
      // 存储原始联系方式用于显示掩码
      originalContact: ''
    };
  },
  computed: {
    // 显示掩码处理后的联系方式
    maskedContact() {
      if (!this.originalContact) return '';
      
      if (this.verificationMode === 'phone') {
        // 手机号掩码处理：185****770
        return this.originalContact.replace(/(\d{3})\d{4}(\d{3})/, '$1****$2');
      } else {
        // 邮箱掩码处理：example****@domain.com
        const [localPart, domain] = this.originalContact.split('@');
        if (localPart.length > 4) {
          return `${localPart.substring(0, 4)}****@${domain}`;
        }
        return `${localPart}****@${domain}`;
      }
    }
  },
  methods: {
    // 切换到邮箱验证
    switchToEmail() {
      this.verificationMode = 'email';
    },
    
    // 切换到手机号验证
    switchToPhone() {
      this.verificationMode = 'phone';
    },
    
    // 获取验证码
    async getVerificationCode() {
      // 验证输入
      if (this.verificationMode === 'phone' && !this.phoneNumber) {
        this.$toast.fail('请输入手机号');
        return;
      }
      
      if (this.verificationMode === 'email' && !this.emailAddress) {
        this.$toast.fail('请输入邮箱地址');
        return;
      }
      if (this.verificationMode === 'phone' && !validateInput(this.phoneNumber, 'phone')) {
        this.$toast.fail('请输入正确的手机号格式');
        return;
      }
      if (this.verificationMode === 'email' && !validateInput(this.emailAddress, 'email')) {
        this.$toast.fail('请输入正确的邮箱地址格式');
        return;
      }
      
      // 保存原始联系方式
      this.originalContact = this.verificationMode === 'phone' 
        ? this.phoneNumber 
        : this.emailAddress; 
      
      // 显示验证码输入区域
      this.showCodeInput = true;
      const accountType = this.verificationMode === 'phone' ? 'phone' : 'email';
      const captchaResponse  = await  sendCaptcha({
          accountType: accountType, 
          account:  this.originalContact
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
        this.setIsSendingCaptcha  =true;
        this.startCountdown();
      }      
    },
    
    // 开始倒计时
    startCountdown() {
      this.countdown = 60;
      const timer = setInterval(() => {
        this.countdown--;
        if (this.countdown <= 0) {
          clearInterval(timer);
        }
      }, 1000);
    },
    
    // 重新发送验证码
    async resendCode() {
      const accountType = this.verificationMode === 'phone' ? 'phone' : 'email';
      const account = this.verificationMode === 'phone' ? this.phoneNumber : this.emailAddress;
      const captchaResponse  = await  sendCaptcha({
          accountType: accountType, 
          account:  account
        });
      console.log("验证码发送结果"+captchaResponse)
      
      if (captchaResponse.status !== 200) {
        this.$toast.fail('验证码发送失败，请重试')
        console.log("验证码发送失败，请重试"+captchaResponse.data)
        return;
      }else if(captchaResponse.data.code !== 200){
        this.$toast.fail(captchaResponse.data.msg)
        return;
      }else{
        this.$toast.success('验证码发送成功')
        this.setIsSendingCaptcha  =true;
        this.startCountdown();
      }
      if (this.countdown <= 0) {
        this.startCountdown();
        // 这里可以添加重新发送验证码的逻辑
        console.log('重新发送验证码');
      }
    },
    
    // 切换验证方式
    changeVerificationMethod() {
      this.showCodeInput = false;
      this.verificationCode = '';
      this.countdown = 0;
      // 切换到另一种验证方式
      this.verificationMode = this.verificationMode === 'phone' ? 'email' : 'phone';
    },
    
    // 取消验证
    cancelVerification() {
      this.showCodeInput = false;
      this.verificationCode = '';
      this.countdown = 0;
    },
    
    // 完成验证
    async completeVerification() {
      if (!this.verificationCode) {
        alert('请输入验证码');
        return;
      }
      const accountType = this.verificationMode === 'phone' ? 'phone' : 'email';
      const inputValue = this.verificationMode === 'phone' ? this.phoneNumber : this.emailAddress;
      if (!inputValue) {
        alert('请输入正确的账户');
        return;
      }
    
      const loginWithCodeResponse  = await loginWithCode({
        accountType: accountType, 
        inputValue: inputValue,
        code: this.verificationCode
      });
      let result = false
      if (loginWithCodeResponse.status !== 200) {
          console.log("验证码发送失败，请重试"+loginWithCodeResponse.data)
          console.log(loginWithCodeResponse )
      } 
      if(loginWithCodeResponse.data.code !== 200){
        this.$toast.fail(loginWithCodeResponse.data.msg)
        return;
      }else{
        result = true
        this.$toast.success('验证码登录成功')
      }
      const getuserinfoResp = await getuserinfo({
        account: inputValue, // 账号（手机号/邮箱）
        accountType: accountType // 账号类型
      });
      let infoResp = false    
      if(getuserinfoResp.data.code != 200 || getuserinfoResp.status != 200){
        this.$toast.success('用户信息获取失败')
        return;
      }else{
        infoResp = true
      }
      let userInfo = getuserinfoResp.data.data;
      if (typeof userInfo === 'string') {
        try {
          userInfo = JSON.parse(userInfo);
        } catch (e) {
          this.$toast.error('数据解析错误');
          return;
        }
      }
      const query= {
        accountType: accountType,  // 账号类型
        account: inputValue,         // 账号（手机号/邮箱）
        phone: userInfo.phonenumber,       // 手机号
        email: userInfo.email,       // 邮箱
        userName: userInfo.userName, // 用户名
        nickname: userInfo.nickName, // 昵称
        userId : userInfo.userId, // 用户ID  
        passwordSet:( userInfo.password? true : false),  
        emailBind:( userInfo.email? true : false),  
        phoneBind:( userInfo.phonenumber? true : false),  
      }
          
      console.log("用户信息",query)
      if(result && infoResp){
        var nextPath = '/mobile/account';
        console.log("用户信息"+query)
        this.$router.push({
            path:nextPath,query:query
        }) 
      }else{  
        this.$toast.fail('登录失败')
      }
     
    }
  }
};
</script>

<style scoped lang="less" src="./assets/login.response.less" />

<style scoped>

.w-full {
    display: block;
    box-sizing: border-box;
    width: 85%;
    min-width: 0;
    margin: 0;
    padding: 0;
    color: #f9f9f9;
    line-height: inherit;
    text-align: left;
    background-color: transparent;
    border: 0;
    resize: none;
    font-size: 3.2vw;
}
/* 自定义工具类 */
.input-focus:focus {
  border-color: #FF7D00;
  ring: 1px solid #FF7D00;
  outline: none;
}

/* 颜色变量 */
:root {
  --primary: #FF7D00;
  --secondary: #161616;
  --dark: #121212;
  --gray-dark: #2C2C2C;
  --gray-light: #8A8A8A;
}

/* 基础样式 */
.bg-dark {
  background-color: var(--dark);
}

.bg-secondary {
  background-color: var(--secondary);
}

.border-gray-dark {
  border-color: var(--gray-dark);
}

.text-primary {
  color: var(--primary);
}

.text-gray-light {
  color: var(--gray-light);
}

/* 动画效果 */
.transition {
  transition: all 0.3s ease;
}

.hover\:bg-opacity-90:hover {
  background-opacity: 0.9;
}

.hover\:text-primary:hover {
  color: var(--primary);
}
</style>