<template>
  <div class="change-phone-page">
    <MobileNavBar 
      showClose
      showTitle
      title="更改手机号"
      @close="handleClose"
    />
    
    <div class="content">
      <p class="subtitle">请选择新手机号进行绑定</p>
      
      <div class="input-group">
        <div class="country-code">
          <span>+86</span>
          <span class="dropdown-icon">▼</span>
        </div>
        <MobileInput 
          v-model="newPhone"
          type="tel"
          placeholder="请输入新的手机号"
          :error="phoneError"
          @blur="validatePhone"
        />
      </div>
      
      <div class="input-with-button">
        <MobileInput 
          v-model="verificationCode"
          type="text"
          placeholder="请输入验证码"
          :error="codeError"
        />
        <MobileButton 
          type="secondary"
          small
          @click="getVerificationCode"
          :disabled="countdown > 0"
        >
          {{ countdown > 0 ? `${countdown}s后重新获取` : '获取验证码' }}
        </MobileButton>
      </div>
      
      <p class="hint success">验证通过</p>
      
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
          完成
        </MobileButton>
      </div>
    </div>
    
    <MobileBottom />
  </div>
</template>

<script>
import MobileNavBar from '@/components/mobile/MobileNavBar.vue'
import MobileBottom from '@/components/mobile/MobileBottom.vue'
import MobileInput from '@/components/mobile/MobileInput.vue'
import MobileButton from '@/components/mobile/MobileButton.vue'
import { validatePhone, countDown } from '@/util/mobile'

export default {
  name: 'ChangePhone',
  components: {
    MobileNavBar,
    MobileBottom,
    MobileInput,
    MobileButton
  },
  data() {
    return {
      newPhone: '',
      verificationCode: '',
      phoneError: '',
      codeError: '',
      countdown: 0,
      timer: null
    }
  },
  computed: {
    isFormValid() {
      return this.newPhone && 
             this.verificationCode && 
             !this.phoneError
    }
  },
  methods: {
    handleClose() {
      this.$router.go(-1)
    },
    handleCancel() {
      this.$router.go(-1)
    },
    validatePhone() {
      if (!this.newPhone) {
        this.phoneError = '请输入手机号'
        return false
      }
      if (!validatePhone(this.newPhone)) {
        this.phoneError = '请输入正确的手机号'
        return false
      }
      this.phoneError = ''
      return true
    },
    getVerificationCode() {
      if (!this.validatePhone()) return
      
      if (this.countdown > 0) return
      
      // 模拟发送验证码
      this.$store.dispatch('user/sendVerificationCode', {
        phone: this.newPhone
      }).then(() => {
        this.$toast.success('验证码已发送')
        this.countdown = 60
        this.timer = countDown(this.countdown, (seconds) => {
          this.countdown = seconds
        })
      }).catch(error => {
        this.$toast.error(error.message || '验证码发送失败')
      })
    },
    handleSubmit() {
      if (!this.validatePhone()) return
      if (!this.verificationCode) {
        this.codeError = '请输入验证码'
        return
      }
      
      // 提交更改逻辑
      this.$store.dispatch('user/changePhone', {
        newPhone: this.newPhone,
        code: this.verificationCode
      }).then(() => {
        this.$toast.success('手机号更改成功')
        this.$router.push('/mobile/account/binding')
      }).catch(error => {
        this.$toast.error(error.message || '手机号更改失败')
      })
    }
  },
  beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer)
    }
  }
}
</script>

<style lang="less" scoped>
.change-phone-page {
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

    .input-group {
      display: flex;
      align-items: center;
      margin-bottom: 16px;

      .country-code {
        display: flex;
        align-items: center;
        padding: 0 12px;
        background-color: #222;
        height: 48px;
        border-radius: 8px 0 0 8px;
        border-right: 1px solid #333;

        .dropdown-icon {
          margin-left: 4px;
          font-size: 12px;
          color: #666;
        }
      }

      .mobile-input {
        flex: 1;
        border-radius: 0 8px 8px 0;
      }
    }

    .input-with-button {
      display: flex;
      gap: 8px;
      margin-bottom: 8px;

      .mobile-input {
        flex: 1;
      }

      .mobile-button {
        width: 120px;
      }
    }

    .hint {
      font-size: 12px;
      color: #666;
      margin: 8px 0 24px;

      &.success {
        color: #4cd964;
      }
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
