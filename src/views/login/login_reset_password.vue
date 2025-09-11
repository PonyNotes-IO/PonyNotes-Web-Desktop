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
    
    <div class="box_2 flex-row justify-center">
      <div class="text-group_1 flex-col">
        <span class="text_4">重置密码</span>
        <span class="text_5">请输入8位以上的密码，需包含大小写字母、数字和特殊字符</span>
      </div>
    </div>
    
    <!-- 账号显示（不可编辑） -->
    <div class="box_3 flex-row justify-center">
      <div class="input_1 flex-col">
        <input 
          class="text_6 text-wrapper_1" 
          :value="displayAccount" 
          style="width:480px;height:28px;" 
          disabled
        />
      </div>
    </div>
    
    <!-- 新密码输入 -->
    <div class="box_4 flex-row justify-center">
      <!-- <div class="section_1 flex-row justify-between">
        <span class="text_7">请输入新密码</span>
        <img
          class="icon_1"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPngfccf3b734f0cce2a77ddd61b61628bc68ff58767b7f862291556c318a7317c89.png"
          @click="togglePasswordVisibility('newPassword')"
          alt="切换密码可见性"
        />
      </div> -->
      <div class="input_1 flex-col justify-center">
        <input 
          class="text_6 " 
          :type="newPasswordVisible ? 'text' : 'password'"
          v-model="newPassword"

          placeholder="请输入新密码"
          style="width:480px;height:28px;"
        />
                <!-- 眼睛图标（睁眼/闭眼切换） -->
        <img
          class="icon_1 eye-icon"
          referrerpolicy="no-referrer"
           :src="newPasswordVisible ? visibleIcon : hiddenIcon"
          @click="togglePasswordVisibility('newPassword')"
          alt="切换密码可见性"
        />
      </div>
      <!-- 密码规则错误提示 -->
      <div v-if="showPasswordRuleError" class="error-text">
        {{ passwordRuleError }}
      </div>
    </div>
    
    <!-- 确认密码输入 -->
    <div class="box_5 flex-row justify-center">
      <!-- <div class="block_1 flex-row justify-between">
        <span class="text_8">再次输入你的新密码</span>
        <img
          class="icon_2"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPngb9722adfebe3496ec3901d85de34cb4a8bd1ae8e5ad2a88eeea8688d98177657.png"
          @click="togglePasswordVisibility('confirmPassword')"
          alt="切换密码可见性"
        />
      </div> -->
      <div class="input_1 flex-col justify-center">
        <input 
          class="text_6" 
          :type="confirmPasswordVisible ? 'text' : 'password'"
          v-model="confirmPassword"
          placeholder="再次输入新密码"
          style="width:480px;height:28px;"
        />
        <img
          class="icon_2 eye-icon"
          referrerpolicy="no-referrer"
           :src="confirmPasswordVisible ? visibleIcon : hiddenIcon"
          @click="togglePasswordVisibility('confirmPassword')"
          alt="切换密码可见性"
        />
      </div>
      <!-- 密码不一致提示 -->
      <div v-if="showPasswordMismatch" class="error-text">
        两次输入的密码不一致
      </div>
    </div>
    
    <!-- 确定按钮 -->
    <div class="box_6 flex-row">
      <button 
        class="button_1 flex-col" 
        @click="handleResetPassword"
        :disabled="isLoading"
      >
        <span class="text_9">{{ isLoading ? '提交中...' : '确定' }}</span>
      </button>
    </div>
  </div>
</template>

<script>
import { Toast } from 'vant';
// 导入睁眼和闭眼图标
import hiddenIcon  from './assets/img/SketchPngb9722adfebe3496ec3901d85de34cb4a8bd1ae8e5ad2a88eeea8688d98177657.png';
import visibleIcon from './assets/img/SketchPngfccf3b734f0cce2a77ddd61b61628bc68ff58767b7f862291556c318a7317c89.png';


// // 创建axios实例
// const axiosInstance = this.$axios.create({
//   baseURL: process.env.VUE_APP_API_BASE_URL,
//   timeout: 5000
// });

export default {
  data() {
    return {
      // 接收父页面传递的参数
      accountType: '',
      account: '',
      phone: '',
      isFirstLogin: false,
      isFirstEmailLogin: false,
      lastPageAction: '',
      
      // 页面状态
      newPassword: '',
      confirmPassword: '',
      newPasswordVisible: false,
      confirmPasswordVisible: false,
      showPasswordRuleError: false,
      passwordRuleError: '',
      showPasswordMismatch: false,
      isLoading: false,
      visibleIcon: visibleIcon,  // 睁眼图标-显示密码
      hiddenIcon: hiddenIcon     // 闭眼图标-隐藏密码
    };
  },
  
  computed: {
    // 格式化显示账号（手机号中间加*）
    displayAccount() {
      if (this.accountType === 'phone' && this.account) {
        return this.account.replace(/^(\d{3})(\d{4})(\d{4})$/, '$1****$3');
      }
      return this.account || '';
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
      this.account = account || phone || ''; // 优先使用account，没有则用phone
      this.phone = phone || '';
      this.isFirstLogin = JSON.parse(isFirstLogin) || false;
      this.isFirstEmailLogin = JSON.parse(isFirstEmailLogin) || false;
      this.lastPageAction = lastPageAction || '';
    },
    
    // 返回上一页
    goBack() {
      this.$router.go(-1);
    },
    
    // 切换密码可见性
    togglePasswordVisibility(type) {
      if (type === 'newPassword') {
        this.newPasswordVisible = !this.newPasswordVisible;
      } else {
        this.confirmPasswordVisible = !this.confirmPasswordVisible;
      }
    },
    
    // 验证密码规则
    validatePassword(password) {
      // 密码规则：8位以上，包含大小写字母、数字和特殊字符
      const minLength = 8;
      const hasUpper = /[A-Z]/.test(password);
      const hasLower = /[a-z]/.test(password);
      const hasNumber = /\d/.test(password);
      const hasSpecial = /[!@#$%^&*(),.?":{}|<>]/.test(password);
      
      if (password.length < minLength) {
        this.passwordRuleError = '密码长度不能少于8位';
        return false;
      }
      
      if (!hasUpper) {
        this.passwordRuleError = '密码需包含大写字母';
        return false;
      }
      
      if (!hasLower) {
        this.passwordRuleError = '密码需包含小写字母';
        return false;
      }
      
      if (!hasNumber) {
        this.passwordRuleError = '密码需包含数字';
        return false;
      }
      
      if (!hasSpecial) {
        this.passwordRuleError = '密码需包含特殊字符';
        return false;
      }
      
      return true;
    },
    
    // 检查两次密码是否一致
    checkPasswordMatch() {
      return this.newPassword === this.confirmPassword;
    },
    
    // 处理重置密码
    async handleResetPassword() {
      // 重置错误提示
      this.showPasswordRuleError = false;
      this.showPasswordMismatch = false;
      
      // 验证密码规则
      if (!this.validatePassword(this.newPassword)) {
        this.showPasswordRuleError = true;
        return;
      }
      
      // 验证密码一致性
      if (!this.checkPasswordMatch()) {
        this.showPasswordMismatch = true;
        return;
      }
      
      // 开始提交
      this.isLoading = true;
      
      try {
        // 调用重置密码接口
        const response = await this.$axios.post('/api/auth/reset-password', {
          account: this.account,
          accountType: this.accountType,
          newPassword: this.newPassword,
          isFirstLogin: this.isFirstLogin
        });
        
        const { code, message } = response.data;
        
        if (code === 200) {
          Toast.success('密码重置成功');
          // 重置成功后跳转到登录页，携带参数
          this.$router.push({
            path: '/login/login_with_password',
            query: {
              accountType: this.accountType,
              account: this.account,
              isFirstLogin: this.isFirstLogin
            }
          });
        } else {
          Toast.fail(message || '密码重置失败，请重试');
        }
      } catch (error) {
        console.error('重置密码请求失败:', error);
        Toast.fail('网络异常，请稍后重试');
      } finally {
        this.isLoading = false;
      }
    }
  }
};
</script>
<style scoped lang="css" src="./assets/login_reset_password.css" />
