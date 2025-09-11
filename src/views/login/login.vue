<template>
  <div class="page flex-col">
    <!-- 页面图片 -->
    <div class="image-wrapper_3 flex-row">
      <img
        class="image_3"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPngc83012784b4d01cca040c34c120c4e75c61902c8d48246217777999d0463a686.png"
        alt="小马笔记logo"
      />
    </div>

    <!-- 欢迎文本 -->
    <div class="text-wrapper_3 flex-row">
      <span class="text_4">欢迎使用小马笔记</span>
    </div>

    <!-- 输入框区域  :type="isPhoneNumber ? 'number' : 'text'"-->
    <div class="box_2 flex-row">
      <div class="input_1 flex-col Input" >
        <van-field
          class="text_7 InputArea"
          placeholder="输入邮箱或者手机号"
          v-model="inputValue"
          type="text"
          @input="validateInput"
        ></van-field>
      </div>
    </div>

    <!-- 登录/注册按钮 -->
    <div class="box_3 flex-row">
      <button 
        class="button_1 flex-col" 
        @click="handleLoginRegister"
      >
        <span class="text_8">登录/注册</span>
      </button>
    </div>

    <!-- 用户协议勾选区域 -->
    <div class="box_4 flex-row">
      <div class="image-text_2 flex-row justify-between items-center">
        <van-checkbox
          name="agreement"
          v-model="agreementChecked"
          icon-size="20"
          class="checkbox_1 Checkbox"
        ></van-checkbox>
        <div class="text-group_1 flex-row">
          <span class="text_9">我已阅读并同意</span> 
          <span class="text_10" @click="openAgreement">《用户协议》</span>
          <span class="text_11">与</span>
          <span class="text_12" @click="openPrivacyPolicy">《隐私政策》</span>
        </div>
      </div>
    </div>

     <LoginPopupBox
      v-if="showPopup"
      @agree="handleAgreeFromChild"
      @closePopup="handleClosePopup"
    />
    <!-- 用户协议弹窗、隐私政策弹窗、第三方扫码登录弹窗等原有内容 -->

    <!-- 其他登录方式 -->
    <div class="box_5 flex-row">
      <img
        class="image_6"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPngea302f0b17509638bf635ff6e8de579a5b8fe081c2784376be35b4033aecb704.png"
        alt="分隔线"
      />
      <span class="text_13">其他登录方式</span>
      <img
        class="image_7"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPng6c680d45766d384a7d4b1cabac31ce18cfc2adc9f19caa4b2e38355a060c18b1.png"
        alt="分隔线"
      />
    </div>

    <!-- 第三方登录图标 -->
    <div class="box_6 flex-row">
      <div class="image-wrapper_4 flex-row">
        <img
          class="icon_1"
          referrerpolicy="no-referrer"
          :src="item.imageUrl"
          :alt="item.name + '登录'"
          v-for="(item, index) in thirdPartyLogins"
          :key="index"
          @click="openThirdPartyLogin(index)"
        />
      </div>
    </div>

    <!-- 用户协议弹窗 -->
    <van-popup 
      v-model="showAgreementPopup" 
      :style="{ height: '80%' }" 
      @close="closeAgreementPopup"
    >
      <div class="popup-header">
        <span>用户协议</span>
        <van-icon name="close" @click="closeAgreementPopup" />
      </div>
      <user-agreement />
    </van-popup>

    <!-- 隐私政策弹窗 -->
    <van-popup 
      v-model="showPrivacyPopup" 
      :style="{ height: '80%' }" 
      @close="closePrivacyPopup"
    >
      <div class="popup-header">
        <span>隐私政策</span>
        <van-icon name="close" @click="closePrivacyPopup" />
      </div>
      <privacy-policy />
    </van-popup>

    <!-- 第三方扫码登录弹窗 -->
    <van-popup 
      v-model="showQrPopup" 
      position="center" 
      :style="{ width: '320px', padding: '16px' }"
      @close="closeQrPopup"
    >
      <div class="qr-popup-header">
        <span>{{ qrPlatformName }}扫码登录</span>
        <van-icon name="close" @click="closeQrPopup" />
      </div>
      <div class="flex-col" style="align-items:center;">
        <img 
          :src="qrImageUrl" 
          :alt="qrPlatformName + '登录二维码'" 
          style="width:260px;height:260px;margin:16px 0;" 
        />
        <p style="font-size:14px;color:#666;">请使用{{ qrPlatformName }}扫描二维码登录</p>
      </div>
    </van-popup>
  </div>
</template>

<script>
import UserAgreement from './user_agreement.vue'
import PrivacyPolicy from './privacy_policy.vue'
import LoginPopupBox from './login_popup_box.vue'; 
import { Icon } from 'vant'

export default {  
  name: 'Login',
  components: {
    'user-agreement': UserAgreement,
    'privacy-policy': PrivacyPolicy,
    'LoginPopupBox': LoginPopupBox, // 注册弹框组件,
    [Icon.name]: Icon
  },

  data() {
    return {
      
      inputValue: '',// 输入框值
      agreementChecked: false,// 协议勾选状态
      isInputValid: false,//输入是否有效（手机号/邮箱格式）
      isPhoneNumber: false, // 是否为手机号（控制输入框类型）

     // 加载与弹窗状态
      isLoading: false, // 全局加载状态
      showPopup: false, // 协议提示弹窗
      showAgreementPopup: false, // 用户协议弹窗
      showPrivacyPopup: false, // 隐私政策弹窗
      showQrPopup: false, // 第三方扫码弹窗
      showCaptchaPopup: false, // 验证码弹窗
      showPasswordLoginPopup: false, // 密码登录弹窗
      showSetPasswordPopup: false, // 设置密码弹窗
      showResetPasswordPopup: false, // 重置密码弹窗
      showBindPhonePopup: false, // 绑定手机号弹窗

      // 验证码相关状态
      captchaValue: '', // 验证码输入值
      isCaptchaValid: false, // 验证码是否有效（6位数字）
      isSendingCaptcha: false, // 是否正在发送验证码
      countdown: 60, // 验证码重发倒计时
      captchaTimer: null, // 倒计时定时器
      captchaType: '', // 验证码类型: register, login, reset
      
      // 业务分支标识
      isNewAccount: false, // 是否为新账号（后端返回）
      isForgetPwd: false, // 是否为忘记密码流程
      isFirstEmailLogin: false, // 邮箱是否首次登录（需绑定手机号）  

      // 二维码相关信息
      qrPlatformName: '',
      qrImageUrl: '',

      captchaValid: false,
      // 重置密码相关
      passwordValue: '', // 密码输入值
      newPwdValue: '',
      confirmPwdValue: '',
      // 登录注册切换标识
      isLogin: true,
      // 第三方登录配置
      thirdPartyLogins: [
        {
          name: '微信',
          imageUrl: 'https://lanhu-oss-proxy.lanhuapp.com/SketchPng5cc925f16ebbe5c7863f2fdb603969442e27dded944355099a7bd0f040a9ad6f'
        },
        {
          name: '抖音',
          imageUrl: 'https://lanhu-oss-proxy.lanhuapp.com/SketchPng25089a1ed73bd0167722826e1e709f02fff64e82ed9f5dc8f2cc597116f57bd5'
        },
        {
          name: 'QQ',
          imageUrl: 'https://lanhu-oss-proxy.lanhuapp.com/SketchPng567c62dfd9b1dd45bce50a42ca513e529a7150c3a1d9f989869889cc73bcc032'
        }
      ]
    };
  },
  beforeUnmount() {
    // 页面销毁前清除定时器
    if (this.captchaTimer) clearInterval(this.captchaTimer)
  },
  methods: {
    // 处理登录/注册按钮点击
    async handleLoginRegister(){
     
      // 检查协议是否勾选 弹框提示 若未勾选则不执行登录/注册逻辑
      if (!this.agreementChecked) {
        this.$toast('请先勾选“我已阅读并同意”');
        // 打开弹窗login_popup_box.vue
        this.showPopup = true; // 显示弹框
        return;
      }
      // 验证输入有效性
      if (!this.isInputValid) {
        this.$toast('请输入有效的邮箱或手机号');
        return;
      }
      // 获取用户信息请求后端接口information -getuserinfo
      try {
        this.isLoading = true
        // 1. 请求后端接口：判断账号是否存在（手机号/邮箱）
        const response = await this.$axios.post('/api/information/getuserinfo', {
          account: this.inputValue, // 账号（手机号/邮箱）
          accountType: this.isPhoneNumber ? 'phone' : 'email' // 账号类型
        })

        const { code, data, message } = response.data
        if (code !== 200) {
          this.$toast.fail(message || '请求失败，请重试')
          return
        }

        // 2. 根据后端返回的账号状态分支处理
        this.isNewAccount = data.isNewAccount // 后端返回：true=新账号，false=已有账号
        this.isFirstEmailLogin = data.isFirstEmailLogin || false // 邮箱首次登录标识（仅邮箱登录返回）
        var nextPath = '/index' // 默认跳转路径
        if (this.isPhoneNumber) {
          if (this.isNewAccount) {
            nextPath = '/login/login_verify_code'
          } else {
            nextPath = '/login/login_with_password'
          }
        } else if(this.isFirstEmailLogin) {
           nextPath = '/login/login_verify_code'
        }else{
           nextPath = '/login/login_with_password'
        }
        var accountType = this.isPhoneNumber ? 'phone' : 'email';
        this.$router.push({
            path:nextPath,
            query: {
            accountType: accountType,  // 账号类型
            isFirstLogin: this.isFirstLogin, // 是否首次登录
            account: this.inputValue,           // 账号（手机号/邮箱）
            phone: this.phone,                // 手机号
          }
        })
      } catch (error) {
        // console.error('账号状态查询失败：', error)
        // this.$toast.fail('网络异常，请检查网络后重试')
        // 后端没通先跳转

        this.isNewAccount = true // 后端返回：true=新账号，false=已有账号
        this.isFirstEmailLogin = false // 邮箱首次登录标识（仅邮箱登录返回）
        if(this.isPhoneNumber){
          if (this.isNewAccount) {
            // 把参数给到子页面 ：账号类型，是否首次登录，账号，手机号/邮箱，
            alert('isPhoneNumber /isNewAccount /login-verify-code')
            // 访问后端api 发送验证码
            this.sendCaptcha(this.isNewAccount, this.isFirstEmailLogin)
            console.log('isPhoneNumber /isNewAccount /login-verify-code')
            this.$router.push({
                path:'/login/login_verify_code',
                query: {
                accountType: this.accountType,  // 账号类型
                isFirstLogin: this.isFirstLogin, // 是否首次登录
                account: this.inputValue,           // 账号（手机号/邮箱）
                phone: this.phone,                // 手机号
              }
            })

          }else{
            alert('isPhoneNumber /not new  //login-with-password')
            this.$router.push({
                path:'/login/login_with_password',
                query: {
                accountType: this.accountType,  // 账号类型
                isFirstLogin: this.isFirstLogin, // 是否首次登录
                account: this.inputValue,        // 账号（手机号/邮箱）
                lastPageAction: 'index_login', // 标识从验证码登录跳转过来
              }
            })
          }
        }else{ //邮箱
          if (this.isFirstEmailLogin) {// 邮箱首次登录
            this.$router.push('/login/login_verify_code')
          }else{//邮箱非首次登录
            this.$router.push('/login/login_verify_code')
          }
        }
      } finally {
        this.isLoading = false
      }
    },
    login() {
      // 模拟登录请求
      this.$toast.loading({
        message: '登录中...',
        forbidClick: true,
        duration: 1000
      });
      setTimeout(() => {
        this.$toast.success('登录成功');
        // 可进行路由跳转等操作，如 this.$router.push('/home')
      }, 1000);
    },
    register() {
      // 显示验证码弹窗
      this.showCaptchaPopup = true;
    },
    // verifyCaptcha() {
    //   // 简单模拟验证码验证
    //   if (this.captchaValue === '123456') {
    //     this.captchaValid = true;
    //     this.$toast.success('验证码验证成功');
    //     this.closeCaptchaPopup();
    //     // 模拟注册请求
    //     this.$toast.loading({
    //       message: '注册中...',
    //       forbidClick: true,
    //       duration: 1000
    //     });
    //     setTimeout(() => {
    //       this.$toast.success('注册成功');
    //       // 注册成功后切换为登录状态
    //       this.isLogin = true;
    //     }, 1000);
    //   } else {
    //     this.$toast.fail('验证码错误');
    //   }
    // },
    confirmResetPwd() {
      if (this.newPwdValue !== this.confirmPwdValue) {
        this.$toast.fail('两次密码输入不一致');
        return;
      }
      if (this.newPwdValue.length < 6) {
        this.$toast.fail('密码长度不能少于6位');
        return;
      }
      // 模拟重置密码请求
      this.$toast.loading({
        message: '重置中...',
        forbidClick: true,
        duration: 1000
      });
      setTimeout(() => {
        this.$toast.success('密码重置成功');
        this.closeResetPwdPopup();
      }, 1000);
    },

   // 检查用户信息，判断是登录还是注册流程
    async checkUserInfo() {
      try {
        this.isLoading = true;
        
        // 请求后端接口：判断账号是否存在
        const response = await this.$axios.post('/api/information/getuserinfo', {
          account: this.inputValue,
          accountType: this.isPhoneNumber ? 'phone' : 'email'
        });

        const { code, data, message } = response.data;
        
        if (code !== 200) {
          this.$toast.fail(message || '请求失败，请重试');
          return;
        }
        
        // 存储后端返回的账号状态
        this.isNewAccount = data.isNewAccount;
        this.isFirstEmailLogin = data.isFirstEmailLogin || false;
        
        // 根据账号状态处理不同流程
        if (this.isNewAccount) {
          // 新账号 - 走注册流程
          this.handleNewAccountRegistration();
        } else {
          // 已有账号 - 走登录流程
          this.handleExistingAccountLogin();
        }
      } catch (error) {
        console.error('账号状态查询失败：', error);
        this.$toast.fail('网络异常，请检查网络后重试');
      } finally {
        this.isLoading = false;
      }
    },
    
    // 处理新账号注册流程
    handleNewAccountRegistration() {
      // 设置验证码类型为注册
      this.captchaType = 'register';
      // 打开验证码弹窗
      this.showCaptchaPopup = true;
      // 自动发送验证码
      this.sendCaptcha();
    },
    
    // 处理已有账号登录流程
    handleExistingAccountLogin() {
      // 打开密码登录弹窗
      this.showPasswordLoginPopup = true;
    },
    
    // 发送验证码入参口
        // accountType: this.accountType,  // 账号类型
        //         isFirstLogin: this.isFirstLogin, // 是否首次登录
        //         account: this.inputValue,           // 账号（手机号/邮箱）
        //         phone: this.phone,                // 手机号
    async sendCaptcha(isFirstLogin, isFirstEmailLogin) {
      if (this.isSendingCaptcha) return;
      
      try {
        this.isSendingCaptcha = true;
        // 请求后端发送验证码
        // Request the back-end to send the verification code
        const response = await this.$axios.post('/api/auth/send-captcha', {
          account: this.inputValue,
          accountType: this.isPhoneNumber ? 'phone' : 'email',
          isFirstLogin: this.isPhoneNumber ? isFirstLogin : isFirstEmailLogin
        });
        
        const { code, message } = response.data;
        
        if (code === 200) {
          this.$toast.success('验证码发送成功');
          this.startCountdown();
        } else {
          this.$toast.fail(message || '验证码发送失败');
          this.isSendingCaptcha = false;
        }
      } catch (error) {
        console.error('发送验证码失败：', error);
        this.$toast.fail('发送验证码失败，请重试');
        this.isSendingCaptcha = false;
      }
    },
    
    // 开始倒计时
    startCountdown() {
      this.countdown = 60;
      this.captchaTimer = setInterval(() => {
        this.countdown--;
        if (this.countdown <= 0) {
          clearInterval(this.captchaTimer);
          this.isSendingCaptcha = false;
        }
      }, 1000);
    },
    
    // 验证验证码
    async verifyCaptcha() {
      if (!this.captchaValue) {
        this.$toast('请输入验证码');
        return;
      }
      
      try {
        this.isLoading = true;
        
        // 请求后端验证验证码
        const response = await this.$axios.post('/api/auth/verify-captcha', {
          account: this.inputValue,
          accountType: this.isPhoneNumber ? 'phone' : 'email',
          captcha: this.captchaValue,
          type: this.captchaType
        });
        
        const { code, message } = response.data;
        
        if (code !== 200) {
          this.$toast.fail(message || '验证码错误');
          return;
        }
        
        // 验证码验证成功，根据不同类型处理
        if (this.captchaType === 'register') {
          // 注册流程：验证码正确，跳转设置密码
          this.showCaptchaPopup = false;
          this.showSetPasswordPopup = true;
        } else if (this.captchaType === 'login') {
          // 登录流程：验证码正确，直接登录
          this.handleLoginSuccess();
        } else if (this.captchaType === 'reset') {
          // 重置密码流程：验证码正确，跳转重置密码
          this.showCaptchaPopup = false;
          this.showResetPasswordPopup = true;
        }
      } catch (error) {
        console.error('验证验证码失败：', error);
        this.$toast.fail('验证失败，请重试');
      } finally {
        this.isLoading = false;
      }
    },
    
    // 设置密码
    async setPassword() {
      if (this.newPwdValue !== this.confirmPwdValue) {
        this.$toast.fail('两次密码输入不一致');
        return;
      }
      
      if (this.newPwdValue.length < 6) {
        this.$toast.fail('密码长度不能少于6位');
        return;
      }
      
      try {
        this.isLoading = true;
        
        // 请求后端设置密码
        const response = await this.$axios.post('/api/auth/set-password', {
          account: this.inputValue,
          accountType: this.isPhoneNumber ? 'phone' : 'email',
          password: this.newPwdValue
        });
        
        const { code, message } = response.data;
        
        if (code !== 200) {
          this.$toast.fail(message || '设置密码失败');
          return;
        }
        
        this.$toast.success('注册成功');
        this.showSetPasswordPopup = false;
        
        // 注册成功后自动登录
        this.handleLoginSuccess();
      } catch (error) {
        console.error('设置密码失败：', error);
        this.$toast.fail('设置密码失败，请重试');
      } finally {
        this.isLoading = false;
      }
    },
    
    // 密码登录
    async loginWithPassword() {
      if (!this.passwordValue) {
        this.$toast('请输入密码');
        return;
      }
      
      try {
        this.isLoading = true;
        
        // 请求后端密码登录
        const response = await this.$axios.post('/api/auth/login-with-password', {
          account: this.inputValue,
          accountType: this.isPhoneNumber ? 'phone' : 'email',
          password: this.passwordValue
        });
        
        const { code, message, data } = response.data;
        
        if (code !== 200) {
          this.$toast.fail(message || '登录失败');
          return;
        }
        
        // 存储登录凭证
        localStorage.setItem('token', data.token);
        localStorage.setItem('userInfo', JSON.stringify(data.userInfo));
        
        // 处理登录成功后的逻辑
        this.handleLoginSuccess();
      } catch (error) {
        console.error('密码登录失败：', error);
        this.$toast.fail('登录失败，请重试');
      } finally {
        this.isLoading = false;
      }
    },
    
    // 忘记密码流程
    forgetPassword() {
      this.isForgetPwd = true;
      this.captchaType = 'reset';
      this.showPasswordLoginPopup = false;
      this.showCaptchaPopup = true;
      this.sendCaptcha();
    },
    
    // 验证码登录
    useCaptchaLogin() {
      this.captchaType = 'login';
      this.showPasswordLoginPopup = false;
      this.showCaptchaPopup = true;
      this.sendCaptcha();
    },
    
    // 重置密码
    async resetPassword() {
      if (this.newPwdValue !== this.confirmPwdValue) {
        this.$toast.fail('两次密码输入不一致');
        return;
      }
      
      if (this.newPwdValue.length < 6) {
        this.$toast.fail('密码长度不能少于6位');
        return;
      }
      
      try {
        this.isLoading = true;
        
        // 请求后端重置密码
        const response = await this.$axios.post('/api/auth/reset-password', {
          account: this.inputValue,
          accountType: this.isPhoneNumber ? 'phone' : 'email',
          newPassword: this.newPwdValue
        });
        
        const { code, message } = response.data;
        
        if (code !== 200) {
          this.$toast.fail(message || '重置密码失败');
          return;
        }
        
        this.$toast.success('密码重置成功');
        this.showResetPasswordPopup = false;
        this.showPasswordLoginPopup = true;
      } catch (error) {
        console.error('重置密码失败：', error);
        this.$toast.fail('重置密码失败，请重试');
      } finally {
        this.isLoading = false;
      }
    },
    
    // 处理登录成功
    handleLoginSuccess() {
      // 检查是否需要绑定手机号（邮箱首次登录）
      if (this.isFirstEmailLogin) {
        this.showBindPhonePopup = true;
        return;
      }
      
      // 登录成功，跳转账户管理页面
      this.$toast.success('登录成功');
      setTimeout(() => {
        this.router.push('/account-management');
      }, 1000);
    },
    
    // 绑定手机号
    async bindPhoneNumber(phone, captcha) {
      try {
        this.isLoading = true;
        
        // 请求后端绑定手机号
        const response = await this.$axios.post('/api/user/bind-phone', {
          email: this.isPhoneNumber ? '' : this.inputValue,
          phone: phone,
          captcha: captcha
        });
        
        const { code, message } = response.data;
        
        if (code !== 200) {
          this.$toast.fail(message || '绑定手机号失败');
          return;
        }
        
        this.$toast.success('手机号绑定成功');
        this.showBindPhonePopup = false;
        
        // 绑定成功后跳转账户管理页面
        this.router.push('/account-management');
      } catch (error) {
        console.error('绑定手机号失败：', error);
        this.$toast.fail('绑定手机号失败，请重试');
      } finally {
        this.isLoading = false;
      }
    },
    
    // 其他已有方法

    handleAgreeFromChild(){
      this.agreementChecked = true; // 选中“我已阅读并同意”
      this.showPopup = false; // 关闭弹框
    },
    handleClosePopup(){
      this.showPopup = false; // 关闭弹框
    },
    // 打开用户协议
    openAgreement() {
      this.showAgreementPopup = true;
    },

    // 关闭用户协议弹窗
    closeAgreementPopup(){
      this.showAgreementPopup= false;
    },

    // 打开隐私政策
    openPrivacyPolicy() {
      this.showPrivacyPopup = true;
    },

    // 关闭隐私政策弹窗
    closePrivacyPopup() {
      this.showPrivacyPopup = false;
    },

    // 打开第三方登录二维码
    openThirdPartyLogin(index) {
      const platform = this.thirdPartyLogins[index];
      this.qrPlatformName = platform.name;
      
      // 根据不同平台设置不同的二维码
      switch(index) {
        case 0: // 微信
          this.qrImageUrl = 'https://api.qrserver.com/v1/create-qr-code/?size=260x260&data=WECHAT_LOGIN_' + Date.now();
          break;
        case 1: // 抖音
          this.qrImageUrl = 'https://api.qrserver.com/v1/create-qr-code/?size=260x260&data=DOUYIN_LOGIN_' + Date.now();
          break;
        case 2: // QQ
          this.qrImageUrl = 'https://api.qrserver.com/v1/create-qr-code/?size=260x260&data=QQ_LOGIN_' + Date.now();
          break;
        default:
          this.qrImageUrl = 'https://api.qrserver.com/v1/create-qr-code/?size=260x260&data=THIRD_PARTY_LOGIN_' + Date.now();
      }
      
      this.showQrPopup = true;
    },

    // 关闭二维码弹窗
    closeQrPopup() {
      this.showQrPopup = false;
    },
    // 关闭验证码弹窗
    closeCaptchaPopup() {
      this.showCaptchaPopup = false;
    },
    // 关闭重置密码弹窗
    closeResetPwdPopup() {
      this.showResetPwdPopup = false;
    },
    // 验证输入内容
    validateInput(value) {
        // 手机号校验
      const phone = value.trim();
      // const phoneReg = /^1[3-9]\d{9}$/;
      // const emailReg = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      const forbiddenPhones = ['00000000000', '12345678901'];
      // 主流号段正则（移动、联通、电信、虚拟运营商）
      const phoneReg = /^(1(3[0-9]|4[01478]|5[0-35-9]|6[2567]|7[013578]|8[0-9]|9[356789]))\d{8}$/;
      // 虚拟运营商号段
      const virtualReg = /^(165|167|170[0-2])\d{8}$/;
      // 号段有效性
      const validPhone = (phoneReg.test(phone) || virtualReg.test(phone)) &&
        phone.length === 11 &&
        /^\d{11}$/.test(phone) &&
        !forbiddenPhones.includes(phone);
        // 邮箱校验
        const email = value.trim();
        // const emailReg = /^([A-Za-z0-9]+([._-+][A-Za-z0-9]+)*)@([A-Za-z0-9]+([-][A-Za-z0-9]+)*\.)+[A-Za-z]{2,}$/;
        const emailReg = /^([A-Za-z0-9]+([._+][A-Za-z0-9]+)*)@([A-Za-z0-9]+([-][A-Za-z0-9]+)*\.)+[A-Za-z]{2,}$/;
        const atCount = (email.match(/@/g) || []).length;
        const localPart = email.split('@')[0];
        const domainPart = email.split('@')[1] || '';
        const tld = domainPart.split('.').pop() || '';
        const validEmail =
          email.length <= 254 &&
          localPart.length >= 1 &&
          localPart.length <= 64 &&
          atCount === 1 &&
          !email.startsWith('@') &&
          !email.endsWith('@') &&
          domainPart.includes('.') &&
          !domainPart.startsWith('.') &&
          !domainPart.endsWith('.') &&
          /^[A-Za-z]{2,}$/.test(tld) &&
          emailReg.test(email);
        if (validPhone) {
          this.isPhoneNumber = true;
        }else{
          this.isPhoneNumber = false;
        }
        // this.isInputValid = phoneReg.test(value) || emailReg.test(value);
        this.isInputValid = validPhone || validEmail;
    }
  }
};
</script>
<style scoped lang="css" src="./assets/login.css" />
