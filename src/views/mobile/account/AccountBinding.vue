<template>
  <div class="account-binding-page">
    <MobileNavBar 
      showClose
      showTitle
      title="账号绑定"
      @close="handleClose"
    />
    
    <div class="content">
      <p class="warning" v-if="securityLevel < 3">
        你当前的账号安全系数较低，请补充邮箱
      </p>
      
      <div class="binding-list">
        <div class="binding-item">
          <div class="info">
            <h3>手机号</h3>
            <p>{{ formatPhone(user.phone) }}</p>
          </div>
          <MobileButton 
            type="text"
            @click="goToChangePhone"
          >
            更改
          </MobileButton>
        </div>
        
        <div class="binding-item">
          <div class="info">
            <h3>邮箱</h3>
            <p v-if="user.email">已绑定：{{ user.email }}</p>
            <p v-else class="unbound">未绑定</p>
            <p class="hint" v-if="!user.email">
              绑定后当你手机号不可用时，可通过邮箱验证更换手机号
            </p>
          </div>
          <MobileButton 
            type="text"
            @click="goToBindEmail"
          >
            {{ user.email ? '更改' : '绑定' }}
          </MobileButton>
        </div>
        
        <div class="binding-item">
          <div class="info">
            <h3>账户密码</h3>
            <p>已设置，可通过账户密码登录</p>
          </div>
          <MobileButton 
            type="text"
            @click="goToChangePassword"
          >
            更改
          </MobileButton>
        </div>
      </div>
    </div>
    
    <MobileFooter />
  </div>
</template>

<script>
import MobileNavBar from '@/components/mobile/MobileNavBar.vue'
import MobileFooter from '@/components/mobile/MobileFooter.vue'
import MobileButton from '@/components/mobile/MobileButton.vue'
import { formatPhoneNumber } from '@/util/mobile'

export default {
  name: 'AccountBinding',
  components: {
    MobileNavBar,
    MobileFooter,
    MobileButton
  },
  data() {
    return {
      securityLevel: 2 // 1-3级别，3为最高
    }
  },
  computed: {
    user() {
      return this.$store.getters['user/info'] || {}
    }
  },
  methods: {
    handleClose() {
      this.$router.go(-1)
    },
    formatPhone(phone) {
      return formatPhoneNumber(phone)
    },
    goToChangePhone() {
      this.$router.push('/mobile/account/change-phone')
    },
    goToBindEmail() {
      this.$router.push('/mobile/account/bind-email')
    },
    goToChangePassword() {
      this.$router.push('/mobile/account/change-password')
    }
  }
}
</script>

<style lang="less" scoped>
.account-binding-page {
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

    .warning {
      color: #ff6b00;
      font-size: 14px;
      margin-bottom: 24px;
    }

    .binding-list {
      .binding-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 16px 0;
        border-bottom: 1px solid #333;

        .info {
          flex: 1;

          h3 {
            font-size: 16px;
            margin-bottom: 4px;
          }

          p {
            font-size: 14px;
            color: #aaa;
            margin-bottom: 4px;

            &.unbound {
              color: #ff6b00;
            }

            &.hint {
              font-size: 12px;
              color: #666;
            }
          }
        }

        .mobile-button {
          width: auto;
          padding: 0 8px;
        }
      }
    }
  }
}
</style>
