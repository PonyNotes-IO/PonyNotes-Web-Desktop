<template>
  <div class="mobile-home">
    <MobileNavBar 
      showLogo 
      showTitle 
      showLogin 
      showRegister 
      @login="handleLogin"
      @register="handleRegister"
    />
    
    <div class="main-content">
      <h1>小马笔记</h1>
      <p>高效记录，轻松管理</p>
      
      <div class="menu">
        <router-link to="/mobile/features" class="menu-item">
          <span>功能</span>
        </router-link>
        <router-link to="/mobile/pricing" class="menu-item">
          <span>价格</span>
        </router-link>
        <router-link to="/mobile/download" class="menu-item">
          <span>下载</span>
        </router-link>
      </div>
    </div>
    
    <MobileBottom />
  </div>
</template>

<script>
import MobileNavBar from '@/components/mobile/MobileNavBar.vue'
import MobileBottom from '@/components/mobile/MobileBottom.vue'

export default {
  name: 'MobileHome',
  components: {
    MobileNavBar,
    MobileBottom
  },
  methods: {
    handleLogin() {
      this.$router.push('/login')
    },
    handleRegister() {
      this.$router.push('/register')
    }
  }
}
</script>

<style lang="less" scoped>
.mobile-home {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: #000;
  color: #fff;
  padding-top: 56px; /* 导航栏高度 */
  padding-bottom: 60px; /* 底部导航高度 */

  .main-content {
    flex: 1;
    padding: 24px 16px;
    text-align: center;

    h1 {
      font-size: 32px;
      margin-bottom: 12px;
    }

    p {
      font-size: 16px;
      color: #aaa;
      margin-bottom: 32px;
    }

    .menu {
      display: flex;
      flex-direction: column;
      gap: 16px;
      max-width: 300px;
      margin: 0 auto;

      .menu-item {
        display: block;
        padding: 16px;
        background-color: #222;
        color: #fff;
        text-decoration: none;
        border-radius: 8px;
        font-size: 18px;
        transition: background-color 0.3s;

        &:hover {
          background-color: #333;
        }
      }
    }
  }
}
</style>
