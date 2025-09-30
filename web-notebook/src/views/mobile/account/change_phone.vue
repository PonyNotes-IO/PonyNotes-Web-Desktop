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
      <span class="text_5">更改手机号</span>
      <span class="text_6">请选择新手机号进行绑定</span>
    </div>
    <!-- 手机号输入框 包含国家区号选择  -->
    <div class="input_1 flex-row">
      <div class="country-code" @click="showAreaCodeList = !showAreaCodeList">
        <span class="text_7">{{ selectedAreaCode }}</span>
        <!-- <img
          class="icon_2"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPngdf83d4a3b10d5cfa0d03436d13aeb4a243747e4e62c0b3989a2c1e22d1c32f74.png"
          alt="展开区号列表"
        /> -->
        
        <!-- 国家区号下拉列表 -->
        <div class="area-code-list" v-if="showAreaCodeList">
          <div 
            v-for="code in areaCodes" 
            :key="code.value" 
            class="code-item"
            @click="selectAreaCode(code)"
          >
            {{ code.name }} {{ code.value }}
          </div>
        </div>
      </div>
      <input class="text_8" 
      placeholder="请输入新的手机号" 
      style="width:96px;height:17px;" 
      v-model="phoneNumber"
      @input="validatePhone"
      @blur="validatePhone"/>
    </div>
    <!-- 验证状态显示 -->
    <div class="button_1 flex-col">
       <span :class="validationPassed ? 'valid' : 'invalid'">{{ validationText }}</span>
      <img
        class="icon_3"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPng00f8d3f3717ba3b0843bc30123fc429b74b26954beedc3c643bcad90c0457a10.png"
      />
    </div>
    <!-- 验证码输入区域 -->
    <div class="box_3 flex-row justify-between">
      <input 
        class="input_2 flex-col" 
        type="number"
        placeholder="请输入验证码"
        v-model="verificationCode"
      />
      <button  
      class="button_2 flex-col" 
       @click="getVerificationCode"
       >
        <span class="text_10" v-if="isCountingDown" >
          {{  `${countdown}s后重新获取` }}
        </span>
        <span class="text_10"  v-else >
          {{ (hasSentVerification ? '重新获取' : '获取验证码') }}
        </span>
      </button>
    </div>
    <span class="text_11" v-if="showSmsSent">已发送短信验证码到新手机</span>
    <div class="box_4 flex-row justify-between">
      <button class="button_3 flex-col" @click="cancel"><span class="text_12">取消</span></button>
      <button class="button_4 flex-col" @click="complete"><span class="text_13">完成</span></button>
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
import { sendCaptcha,changePhoneByCode,validateInput } from '../../../api/ponynote_login.js'; 
export default {
  data() {
    return {
      userName: this.$route.query.userName || '',
      // 国家区号数据
      areaCodes: [
        { name: '中国', value: '+86' },
        { name: '中国香港', value: '+852' },
        { name: '中国台湾', value: '+886' },
        { name: '中国澳门', value: '+853' },
        { name: '美国', value: '+1' },
        { name: '日本', value: '+81' },
      ],
      selectedAreaCode: '+86',
      showAreaCodeList: false,
      
      // 手机号和验证码
      phoneNumber: '',
      verificationCode: '',
      hasSentVerification: false, // 新增：标记是否已发送过验证码
      // 状态控制
      isPhoneValid: false,
      showValidation: false,
      validationPassed: false,
      validationText: '请输入手机号',
      
      // 验证码倒计时
      isCountingDown: false,
      countdown: 60,
      countdownTimer: null, // 保存定时器引用
      showSmsSent: false,
    };
  },
  
  methods: {
    // 选择国家区号
    selectAreaCode(code) {
      this.selectedAreaCode = code.value;
      this.showAreaCodeList = false;
      validateInput(this.phoneNumber,"phone"); // 重新验证手机号
    },
    // 开始倒计时
    startCountdown() {
      // 清除可能存在的旧定时器
      if (this.countdownTimer) {
        clearInterval(this.countdownTimer);
      }
      this.isCountingDown = true;
      this.countdown = 60;
      this.countdownTimer = setInterval(() => {
        this.countdown--;
        if (this.countdown <= 0) {
          clearInterval(this.countdownTimer);  // 修复：添加 this
          this.isCountingDown = false; 
          this.countdownTimer = null;
        }
      }, 1000);  // 修复：改为 1000ms（1秒）
    },
    // 验证手机号格式
    validatePhone() {
      if (!this.phoneNumber) {
        this.showValidation = true;
        this.isPhoneValid = false;
        return;
      }
      
     let validResult = validateInput(this.phoneNumber,"phone");
      
      this.showValidation = true;
      this.isPhoneValid = validResult;
      this.validationPassed = validResult;
      this.validationText = validResult ? '验证通过' : '请输入正确的手机号';
      return validResult;
    },
    
    // 获取验证码
    async getVerificationCode() {
      let validResult = validateInput(this.phoneNumber,"phone");
      if (!validResult) {
        this.$toast.fail('请输入正确的手机号');
        return;
      }
      
      
      // 模拟调用获取验证码API
      console.log(`调用获取验证码API: ${this.selectedAreaCode}${this.phoneNumber}`);
      const captchaResponse  = await  sendCaptcha({
        accountType: 'phone', 
        account:  this.phoneNumber
      });
      if (captchaResponse.status !== 200 || captchaResponse.data.code !== 200) {
        this.$toast.fail(captchaResponse.data?.msg || '验证码发送失败，请重试');
        return;
      }
      this.$toast.success('验证码发送成功')
      this.showSmsSent = true;
      this.hasSentVerification = true; // 标记已发送过验证码
      this.isCountingDown = true;
      this.startCountdown(); 
      
    },
    

    // 取消操作，返回上一级
    cancel() {
      console.log('返回上一级页面');
      this.$router.go(-1);
    },
    
    // 完成操作，验证验证码
    async complete() {
      if (!this.verificationCode) {
        this.$toast.fail('请输入验证码');
        return;
      }
      

      const changePhoneResponse = await changePhoneByCode({
        username : this.userName,
        account:  this.phoneNumber,
        code: this.verificationCode,
        phone: this.phoneNumber,
      });

      if (changePhoneResponse.status !== 200) {
        this.$toast.fail('验证码验证失败，请重试')
        this.validationPassed = false;
        this.validationText = '验证码验证失败，请重试';
        this.showValidation = true;
        return;
      }
      if(changePhoneResponse.data.code !== 200){
        this.$toast.fail(changePhoneResponse.data.msg)
        this.validationPassed = false;
        this.validationText = "验证失败，验证码过期或验证码错误";
        this.showValidation = true;
        return;
      }
      let userInfo = changePhoneResponse.data.data;
      if (typeof userInfo === 'string') {
        try {
          userInfo = JSON.parse(userInfo);
        } catch (e) {
          this.$toast.error('数据解析错误');
          return;
        }
      }
      this.$toast.success('手机号修改成功')
      this.validationPassed = true;
      this.validationText = '验证通过';
      this.showValidation = true;
      var nextPath = '/mobile/account';

      const query= {
        accountType: 'phone',  // 账号类型
        account: this.phoneNumber,         // 账号（手机号/邮箱）
        phone: userInfo.phonenumber,       // 手机号
        email: userInfo.email,       // 邮箱
        userName: userInfo.userName, // 用户名
        nickname: userInfo.nickName, // 昵称
        userId : userInfo.userId, // 用户ID  
        passwordSet:( userInfo.password? true : false),  
        emailBind:( userInfo.email? true : false),  
        phoneBind:( userInfo.phonenumber? true : false),  
      }
      this.$router.push({
          path:nextPath,
          query: query
      }) 
    }
  },
  
  beforeDestroy() {
    // 清除定时器
    if (this.countdownTimer) {
      clearInterval(this.countdownTimer);
    }
  }
};
</script>
<style scoped lang="less" src="./assets/change_phone.response.less" />