<template>
  <div class="page flex-col">
      <MobileNavBar 
        :show-logo="true"
        :show-title="true"
        :show-login="true"
        :show-register="true"
        :show-more="true"
      />
    <div class="text-group_1 flex-col">
      <span class="text_5">更改密码</span>
      <span class="text_6">请选择新密码进行设置</span>
    </div>
    <div class="text-wrapper_1 flex-row">
      <input 
        class="text_7 " 
        :type="newPasswordVisible ? 'text' : 'password'"
        v-model="newPassword"
        placeholder="请输入新密码"
        @input="checkPasswordRule"
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
    <div class="text-wrapper_1 flex-row">
      <input 
        class="text_7" 
        :type="confirmPasswordVisible ? 'text' : 'password'"
        v-model="confirmPassword"
        placeholder="再次输入新密码"
        @input="checkPasswordMatchRealTime"
      />
      <img
        class="icon_2 eye-icon"
        referrerpolicy="no-referrer"
          :src="confirmPasswordVisible ? visibleIcon : hiddenIcon"
        @click="togglePasswordVisibility('confirmPassword')"
        alt="切换密码可见性"
      />
    </div>
    <div class="box_5 flex-row justify-center">
    <!-- 密码设置提示 -->
        <span v-show="showPasswordMismatch" class="error-text" style="color: rgba(248, 149, 117, 1);" > 两次输入的密码不一致</span>
        <span v-show="showPasswordRuleError" class="error-text" style="color: rgba(248, 149, 117, 1);" > {{ passwordRuleError }}</span>
    </div>

    <div class="box_3 flex-row justify-between">
      <button class="button_1 flex-col" @click="cancel"><span class="text_10">取消</span></button>
      <button class="button_2 flex-col" @click="handleResetPassword">
        <span class="text_11">更改密码</span>
      </button>
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
import { updatePassword,validatePassword } from '../../../api/ponynote_login.js'; 
import hiddenIcon  from './assets/img/SketchPngb9722adfebe3496ec3901d85de34cb4a8bd1ae8e5ad2a88eeea8688d98177657.png';
import visibleIcon from './assets/img/SketchPngfccf3b734f0cce2a77ddd61b61628bc68ff58767b7f862291556c318a7317c89.png';

export default {
  data() {
    return {
      accountType: this.$route.query.accountType ||'',
      account: this.$route.query.account ||'',
      userName: this.$route.query.userName || '',
      phoneNumber: '',
      userId: '',
      phone: '',
      email: '',
      nickName: '',
      // 页面状态
      newPassword: '',
      confirmPassword: '',
      newPasswordVisible: false,
      confirmPasswordVisible: false,
      showPasswordRuleError: false,
      passwordRuleError: '',
      passwordMismatchText: '两次输入的密码不一致', // 仅存“不一致”信息
      showPasswordMismatch: false,
      isLoading: false,
      visibleIcon: visibleIcon,  // 睁眼图标-显示密码
      hiddenIcon: hiddenIcon     // 闭眼图标-隐藏密码
    };
  },
  methods: {
    parseBoolean(value, defaultValue = false) {
      if (value === undefined || value === null) return defaultValue;
      if (typeof value === 'boolean') return value;
      return String(value).toLowerCase() === 'true';
    },
    // 切换密码可见性
    togglePasswordVisibility(type) {
      if (type === 'newPassword') {
        this.newPasswordVisible = !this.newPasswordVisible;
      } else {
        this.confirmPasswordVisible = !this.confirmPasswordVisible;
      }
    },
    // 检查两次密码是否一致
    checkPasswordMatch() {
      return this.newPassword === this.confirmPassword;
    },
    // 实时校验密码规则（新密码输入时）
    checkPasswordRule() {
      this.showPasswordRuleError = false; // 先重置
      if (!this.newPassword) return; // 空值不校验
      const ruleResult = validatePassword(this.newPassword);
      if (ruleResult !== '200') {
        this.showPasswordRuleError = true;
        this.passwordRuleError = ruleResult;
      }
    },

    // 实时校验密码一致性（确认密码输入时）
    checkPasswordMatchRealTime() {
      this.showPasswordMismatch = false; // 先重置
      if (!this.newPassword || !this.confirmPassword) return; // 任一为空不校验
      this.showPasswordMismatch = !this.checkPasswordMatch();
    },

    cancel() {
      this.$router.go(-1);
    },
    // 处理重置密码
    async handleResetPassword() {
      this.showPasswordRuleError = false;
      this.showPasswordMismatch = false;
      this.passwordRuleError = '';
      this.isLoading = false;
      if (!this.newPassword) {
        this.showPasswordRuleError = true;
        this.passwordRuleError = '请输入新密码';
        return;
      }
      if (!this.confirmPassword) {
        this.showPasswordRuleError = true;
        this.passwordRuleError = '请再次输入新密码';
        return;
      }
      this.passwordRuleError = validatePassword(this.newPassword);
      // 验证密码规则
      if (this.passwordRuleError!== '200') {
        this.showPasswordRuleError = true;
        this.passwordRuleError = '密码不符合规则，请重新输入';
        return;
      }
      // 验证密码一致性
      if (!this.checkPasswordMatch()) {
        this.$toast.fail(this.passwordMismatchText);
        this.passwordRuleError = '两次输入的密码不一致';
        this.showPasswordMismatch = true;
        this.showPasswordRuleError = false; // 隐藏规则错误（防止同时显示）
        return;
      }
      if (this.userName === '') {
        this.$toast.fail('用户名不能为空，请返回上一页重新操作');
        this.isLoading = false; // 确保状态重置
        return;
      }
      // 开始提交
      this.isLoading = true;
      const response = await updatePassword({
        username: this.userName,
        password: this.newPassword,
      });
      if(response.status !== 200){
        this.isLoading = false; // 重置加载状态
        this.$toast.fail('请求失败，请重试')
        return
      }
      if(response.data.code !== 200){
        this.isLoading = false; // 重置加载状态
        this.$toast.fail('密码重置失败，请重试')
        return
      }
      let userInfo = response.data.data;
      if (typeof userInfo === 'string') {
        try {
          userInfo = JSON.parse(userInfo);
        } catch (e) {
          this.isLoading = false; // 重置加载状态
          this.$toast.error('数据解析错误');
          return;
        }
      }
      this.$toast.success('密码修改成功')
      const query= {
        accountType: 'phone',  // 账号类型
        account: this.account,         // 账号（手机号/邮箱）
        phone: userInfo.phonenumber,       // 手机号
        email: userInfo.email,       // 邮箱
        userName: userInfo.userName, // 用户名
        nickname: userInfo.nickName, // 昵称
        userId : userInfo.userId, // 用户ID  
        passwordSet:(userInfo.password? true : false),  
        emailBind:( userInfo.email? true : false),  
        phoneBind:( userInfo.phonenumber? true : false),  
      }
      console.log('跳转至account的参数：', query); 
      this.$router.replace({
        path:'/mobile/account',query:query
      }) 
    
    }
  }
};
</script>
<style scoped lang="less" src="./assets/change_password.response.less" />