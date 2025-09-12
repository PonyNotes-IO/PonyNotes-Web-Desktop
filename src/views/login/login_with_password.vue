<template>
  <div class="scale-root">
    <div class="page flex-col">
      <div class="box_2 flex-row justify-between">
        <div class="text-group_1 flex-col">
          <span class="text_4">账号密码登录</span>
          <span class="text_5">使用已经注册过的{{ accountType === 'phone' ? '手机号' : '邮箱' }}登录</span>
        </div>
        <button class="button_1 flex-col" @click="switchToCaptchaLogin"><span class="text_6">验证码登录</span></button>
      </div>

      <div class="box_3 flex-row justify-center">
        <div class="input_1 flex-col">
          <input 
            class="text_6" 
            :placeholder="accountType === 'phone' ? '15871231937' : 'example@email.com'" 
            style="width:132px;height:28px;" 
            :value="account"
            :disabled="true"
          />
        </div>
      </div>

      <div class="box_4 flex-row justify-center">
        <div class="input_1 flex-col">
          <input 
            class="text_6" 
            type="password" 
            placeholder="请输入密码" 
            style="width:132px;height:28px;" 
            v-model="password"
          />
        </div>
      </div>
      <!-- 只有校验有问题才显示红色错误信息 -->
      <div class="text-wrapper_2 flex-row justify-between" >
        <span class="text_9" v-if="showError">账号和密码不匹配，请重新输入</span>
        <span class="text_10" @click="goToResetPassword">忘记密码？重置密码</span>
      </div>

      <div class="box_5 flex-row">
        <button 
          class="button_2 flex-col" 
          @click="handleLogin"
          :disabled="isLoading"
        >
          <span class="text_11">{{ isLoading ? '登录中...' : '确定' }}</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { Toast } from 'vant';
import scaleMixin from '../../utils/scale';

export default {
  mixins: [scaleMixin],
  data() {
    return {
      // 从URL参数获取的信息
      accountType: '',       // 账号类型：phone 或 email
      isFirstLogin: false,   // 是否首次登录
      isFirstEmailLogin: false, // 是否首次登录邮箱
      account: '',           // 账号（手机号/邮箱）
      phone: '',             // 手机号
      lastPageAction: '',    // 标识从哪里跳转过来

      // 页面状态
      password: '',          // 密码
      showError: false,      // 是否显示错误信息
      isLoading: false       // 登录按钮加载状态
    };
  },

  created() {
    // 从路由参数中获取父页面传递的数据
    this.initDataFromQuery();
  },

  methods: {
    // 从URL参数初始化数据
    initDataFromQuery() {
      const { accountType, isFirstLogin, isFirstEmailLogin, account, phone, lastPageAction } = this.$route.query;

      if (accountType) {
        this.accountType = accountType;
      }

      if (isFirstLogin !== undefined) {
        this.isFirstLogin = JSON.parse(isFirstLogin);
      }

      if (isFirstEmailLogin !== undefined) {
        this.isFirstEmailLogin = JSON.parse(isFirstEmailLogin);
      }

      if (account) {
        this.account = account;
      }

      if (phone) {
        this.phone = phone;
      }

      if (lastPageAction) {
        this.lastPageAction = lastPageAction;
      }
    },

    // 获取需要携带的公共参数
    getCommonParams(lastPageActionDetaile) {
      return {
        accountType: this.accountType,
        isFirstLogin: this.isFirstLogin,
        isFirstEmailLogin: this.isFirstEmailLogin,
        account: this.account,
        phone: this.phone,
        lastPageAction: lastPageActionDetaile
      };
    },

    // 切换到验证码登录
    switchToCaptchaLogin() {
      // 跳转到验证码登录页面，携带必要参数
      this.$router.push({
        path: '/login/login_verify_code',
        query: this.getCommonParams("login_with_password_switch_to_captcha_login")
      });
    },

    // 验证密码格式
    validatePassword() {
      // 密码验证规则：至少8位，包含字母和数字
      const passwordReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;

      if (!this.password) {
        Toast('请输入密码');
        return false;
      }

      if (!passwordReg.test(this.password)) {
        Toast('密码至少8位，且包含字母和数字');
        return false;
      }

      return true;
    },

    // 处理登录逻辑
    async handleLogin() {
      // 隐藏之前的错误信息
      this.showError = false;

      // // 验证密码
      // if (!this.validatePassword()) {
      //   return;
      // }

      // 设置加载状态
      this.isLoading = true;

      try {
        // 调用登录接口
        const response = await this.$axios.post('/api/auth/login-with-password', {
          account: this.account,
          accountType: this.accountType,
          password: this.password,
          isFirstLogin: this.isFirstLogin,
          isFirstEmailLogin: this.isFirstEmailLogin
        });

        const { code, message, data } = response.data;

        if (code === 200) {
          // 登录成功
          Toast.success('登录成功');

          // 保存登录状态和用户信息
          localStorage.setItem('token', data.token);
          localStorage.setItem('userInfo', JSON.stringify(data.userInfo));

          // 根据是否首次登录决定跳转页面，均携带参数
          let targetPath = '/account';
          this.$router.push({
            path: targetPath,
            query: this.getCommonParams("login_with_password_successful")
          });
        } else {
          // 登录失败，显示错误信息
          Toast.fail(message || '登录失败，请重试');
          this.showError = true;
        }
      } catch (error) {
        console.error('登录请求失败:', error);
        Toast.fail('网络异常，请稍后重试');
        this.showError = true;
      } finally {
        // 取消加载状态
        this.isLoading = false;
      }
    },

    // 跳转到验证码重置密码页面
    goToResetPassword() {
      this.$router.push({
        path: '/login/login_verify_code',
        query: this.getCommonParams("login_with_password_forget_password")
      });
    }
  }
};
</script>
<style scoped lang="css" src="./assets/login_with_password.css" />
<style scoped>
.scale-root {
  overflow: hidden;
}
</style>