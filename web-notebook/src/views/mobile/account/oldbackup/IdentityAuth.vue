<template>
  <div class="identity-auth-page">
    <MobileNavBar 
      showClose
      showTitle
      title="身份验证"
      @close="handleClose"
    />
    
    <div class="content">
      <p class="subtitle">为了你的账户安全，请先验证身份</p>
      
      <div class="auth-method">
        <div class="method-info">
          <p>使用手机185******70验证</p>
          <router-link to="/mobile/account/change-phone" class="switch-method">切换验证</router-link>
        </div>
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
          :disabled="!verificationCode"
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
import { countDown } from '@/util/mobile'

export default {
  name: 'IdentityAuth',
  components: {
    MobileNavBar,
    MobileBottom,
    MobileInput,
    MobileButton
  },
  data() {
    return {
      verificationCode: '',
      codeError: '',
      countdown: 0,
      timer: null
    }
  },
  methods: {
    handleClose() {
      this.$router.go(-1)
    },
    handleCancel() {
      this.$router.go(-1)
    },
    getVerificationCode() {
      if (this.countdown > 0) return
      
      // 模拟发送验证码
      this.$store.dispatch('user/sendVerificationCode', {
        phone: this.$store.getters['user/phone']
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
      if (!this.verificationCode) {
        this.codeError = '请输入验证码'
        return
      }
      
      // 提交验证逻辑
      this.$store.dispatch('user/verifyIdentity', {
        code: this.verificationCode
      }).then(() => {
        this.$toast.success('身份验证成功')
        this.$router.push(this.$route.query.redirect || '/mobile/account/binding')
      }).catch(error => {
        this.$toast.error(error.message || '身份验证失败')
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
.identity-auth-page {
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

    .auth-method {
      margin-bottom: 24px;
      padding: 16px;
      background-color: #222;
      border-radius: 8px;

      .method-info {
        display: flex;
        justify-content: space-between;
        align-items: center;

        p {
          font-size: 16px;
        }

        .switch-method {
          color: #ff6b00;
          font-size: 14px;
          text-decoration: none;
        }
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
