<template>
  <div class="page flex-col">
    <!-- 返回按钮 -->
    <div class="image-wrapper_1 flex-row" @click="goBack">
      <img
        class="image_3"
        referrerpolicy="no-referrer"
        src="./assets/img/reback.png"
        alt="返回"
      />
    </div>
    
    <div class="section_2 flex-row justify-center">
      <div class="text-group_1 flex-col">
        <span class="text_4">设置密码</span>
        <span class="text_5">请输入8位以上的密码，需包含大小写字母、数字和特殊字符。</span>
      </div>
    </div>
    
    <!-- 密码输入框 -->
    <div class="section_3 flex-row">
      <!-- <div class="text-wrapper_1 flex-col"><span class="text_6">输入设置的密码</span></div> -->
      <div class="input-wrapper flex-col">
        <input 
          type="password"
          v-model="password"
          placeholder="请设置密码"
          class="password-input text-wrapper_1"
          @input="validatePasswordOnInput"
        />
      </div>
    </div>
    
    <!-- 确认密码输入框 -->
    <div class="section_3 flex-row">
      <!-- <div class="text-wrapper_1 flex-col"><span class="text_6">再次输入密码</span></div> -->
      <div class="input-wrapper flex-col">
        <input 
          type="password"
          v-model="confirmPassword"
          placeholder="请确认密码"
          class="password-input text-wrapper_1"
          @input="checkPasswordMatch"
        />
      </div>
    </div>
    
    <!-- 密码规则提示和错误信息 -->
    <div class="text-wrapper_2 flex-row justify-center">
      <span class="paragraph_1" :class="{ error: showError }">
        <template v-if="showError">
          <template v-if="passwordError">{{ passwordError }}</template>
          <template v-else-if="passwordMismatch">两次输入的密码不一致</template>
        </template>
        <template v-else>
          设置一个密码才可以继续
          <br />
          密码长度太短，至少需8个字符
          <br />
          密码需包含数字、字母、英文字符中任意两种
        </template>
      </span>
    </div>
    
    <!-- 确定按钮 -->
    <div class="section_4 flex-row">
      <div 
        class="text-wrapper_3 flex-col" 
        @click="handleSetPassword"
        :class="{ disabled: isLoading || !canSubmit }"
      >
        <span class="text_7">{{ isLoading ? '设置中...' : '确定' }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import { Toast } from 'vant';
// import axios from 'axios';
// 导入睁眼和闭眼图标
// import hiddenIcon  from './assets/img/SketchPngb9722adfebe3496ec3901d85de34cb4a8bd1ae8e5ad2a88eeea8688d98177657.png';
// import visibleIcon from './assets/img/SketchPngfccf3b734f0cce2a77ddd61b61628bc68ff58767b7f862291556c318a7317c89.png';


export default {
  data() {
    return {
      // 接收父页面参数
      accountType: '',
      account: '',
      phone: '',
      isFirstLogin: false,
      isFirstEmailLogin: false,
      lastPageAction: '',
      
      // 页面状态
      password: '',
      confirmPassword: '',
      passwordError: '',
      passwordMismatch: false,
      showError: false,
      isLoading: false
    };
  },
  
  computed: {
    // 判断是否可以提交
    canSubmit() {
      return this.password && 
             this.confirmPassword && 
             !this.passwordError && 
             !this.passwordMismatch;
    }
  },
  
  created() {
    // 从路由参数获取数据
    this.initParams();
  },
  
  methods: {
    // 初始化接收参数
    initParams() {
      const { 
        accountType, 
        account, 
        phone, 
        isFirstLogin, 
        isFirstEmailLogin,
        lastPageAction
      } = this.$route.query;
      
      this.accountType = accountType || '';
      this.account = account || phone || '';
      this.phone = phone || '';
      this.isFirstLogin = JSON.parse(isFirstLogin) || false;
      this.isFirstEmailLogin = JSON.parse(isFirstEmailLogin) || false;
      this.lastPageAction = lastPageAction || '';
    },
    
    // 返回上一页
    goBack() {
      this.$router.go(-1);
    },
    
    // 验证密码规则
    validatePassword(password) {
      // 密码规则：8位以上，包含数字、字母中至少两种
      const minLength = 8;
      const hasNumber = /\d/.test(password);
      const hasLetter = /[a-zA-Z]/.test(password);
      
      if (password.length < minLength) {
        return '密码长度不能少于8位';
      }
      
      if (!hasNumber && !hasLetter) {
        return '密码需包含数字或字母';
      }
      
      if (!(hasNumber || hasLetter)) {
        return '密码需包含数字或字母';
      }
      
      return ''; // 验证通过
    },
    
    // 输入时验证密码
    validatePasswordOnInput() {
      this.passwordError = this.validatePassword(this.password);
      this.showError = !!this.passwordError;
      this.checkPasswordMatch(); // 同时检查密码一致性
    },
    
    // 检查两次密码是否一致
    checkPasswordMatch() {
      this.passwordMismatch = this.password !== this.confirmPassword;
      this.showError = this.passwordMismatch || !!this.passwordError;
    },
    
    // 处理设置密码
    async handleSetPassword() {
      // 验证密码
      this.passwordError = this.validatePassword(this.password);
      this.checkPasswordMatch();
      
      if (this.passwordError || this.passwordMismatch) {
        this.showError = true;
        return;
      }
      
      // 开始提交
      this.isLoading = true;
      
      try {
        // 调用设置密码接口
        const response = await this.$axios.post('/api/auth/set-password', {
          account: this.account,
          accountType: this.accountType,
          password: this.password,
          isFirstLogin: this.isFirstLogin,
          isFirstEmailLogin: this.isFirstEmailLogin
        });
        
        const { code, message } = response.data;
        
        if (code === 200) {
          Toast.success('密码设置成功');
          // 设置成功后跳转到账户管理界面，携带参数
          this.$router.push({
            path: '/account/manage',
            query: {
              accountType: this.accountType,
              account: this.account,
              isFirstLogin: this.isFirstLogin
            }
          });
        } else {
          Toast.fail(message || '密码设置失败，请重试');
        }
      } catch (error) {
        console.error('设置密码请求失败:', error);
        Toast.fail('网络异常，请稍后重试');
      } finally {
        this.isLoading = false;
      }
    }
  }
};
</script>
<style scoped lang="css" src="./assets/login_set_password.css" />
