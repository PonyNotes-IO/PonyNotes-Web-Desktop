<template>
  <div class="change-password-page">
    <MobileNavBar 
      showClose
      showTitle
      title="更改密码"
      @close="handleClose"
    />
    
    <div class="content">
      <p class="subtitle">请选择新密码进行设置</p>
      
      <MobileInput 
        v-model="newPassword"
        type="password"
        placeholder="请输入新的密码"
        :error="passwordError"
        @blur="validatePassword"
      />
      
      <MobileInput 
        v-model="confirmPassword"
        type="password"
        placeholder="请重新输入新的密码"
        :error="confirmError"
        @blur="validateConfirm"
      />
      
      <p class="hint">已发送短信验证码到绑定手机</p>
      
      <div class="buttons">
        <MobileButton 
          type="secondary"
          @click="handleCancel"
        >
          取消
        </MobileButton>
        <MobileButton 
          type="primary"
          @click="handleSubmit"
          :disabled="!isFormValid"
        >
          更改密码
        </MobileButton>
      </div>
    </div>
    
    <MobileFooter />
  </div>
</template>

<script>
import MobileNavBar from '@/components/mobile/MobileNavBar.vue'
import MobileFooter from '@/components/mobile/MobileFooter.vue'
import MobileInput from '@/components/mobile/MobileInput.vue'
import MobileButton from '@/components/mobile/MobileButton.vue'
import { validatePassword } from '@/util/mobile'

export default {
  name: 'ChangePassword',
  components: {
    MobileNavBar,
    MobileFooter,
    MobileInput,
    MobileButton
  },
  data() {
    return {
      newPassword: '',
      confirmPassword: '',
      passwordError: '',
      confirmError: ''
    }
  },
  computed: {
    isFormValid() {
      return this.newPassword && 
             this.confirmPassword && 
             !this.passwordError && 
             !this.confirmError
    }
  },
  methods: {
    handleClose() {
      this.$router.go(-1)
    },
    handleCancel() {
      this.$router.go(-1)
    },
    validatePassword() {
      if (!this.newPassword) {
        this.passwordError = '请输入密码'
        return false
      }
      if (!validatePassword(this.newPassword)) {
        this.passwordError = '密码至少8位字符'
        return false
      }
      this.passwordError = ''
      return true
    },
    validateConfirm() {
      if (!this.confirmPassword) {
        this.confirmError = '请确认密码'
        return false
      }
      if (this.confirmPassword !== this.newPassword) {
        this.confirmError = '两次输入的密码不一致'
        return false
      }
      this.confirmError = ''
      return true
    },
    handleSubmit() {
      if (!this.validatePassword() || !this.validateConfirm()) {
        return
      }
      
      // 提交更改逻辑
      this.$store.dispatch('user/changePassword', {
        newPassword: this.newPassword
      }).then(() => {
        this.$toast.success('密码更改成功')
        this.$router.push('/mobile/account/binding')
      }).catch(error => {
        this.$toast.error(error.message || '密码更改失败')
      })
    }
  }
}
</script>

<style lang="less" scoped>
.change-password-page {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: #000;
  color: #fff;
  padding-top: 56px;
  padding-bottom: 60px;

  .content {
    flex: 1;
    padding: 24px 16px;

    .subtitle {
      font-size: 14px;
      color: #aaa;
      margin-bottom: 24px;
    }

    .hint {
      font-size: 12px;
      color: #666;
      margin: 8px 0 24px;
    }

    .buttons {
      display: flex;
      gap: 16px;
      margin-top: 24px;

      button {
        flex: 1;
      }
    }
  }
}
</style>
