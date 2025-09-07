<template>
  <div class="mobile-features">
    <MobileNavBar 
      showClose
      showTitle
      title="功能"
      @close="handleClose"
    />
    
    <div class="content">
      <div class="feature-item">
        <h3>AI工具赋能内容创作</h3>
        <p>利用先进的人工智能技术，帮助您更高效地创作内容</p>
      </div>
      
      <div class="feature-item">
        <h3>自动化创作流程</h3>
        <p>简化创作过程，让您的效率倍增</p>
      </div>
      
      <div class="feature-item">
        <h3>多平台同步</h3>
        <p>支持桌面端和移动端无缝切换</p>
      </div>
    </div>
    
    <MobileBottom />
  </div>
</template>

<script>
import MobileNavBar from '@/components/mobile/MobileNavBar.vue'
import MobileBottom from '@/components/mobile/MobileBottom.vue'

export default {
  name: 'MobileFeatures',
  components: {
    MobileNavBar,
    MobileBottom
  },
  methods: {
    handleClose() {
      this.$router.go(-1)
    }
  }
}
</script>

<style lang="less" scoped>
.mobile-features {
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

    .feature-item {
      margin-bottom: 24px;
      padding: 16px;
      background-color: #222;
      border-radius: 8px;

      h3 {
        font-size: 18px;
        margin-bottom: 8px;
        color: #ff6b00;
      }

      p {
        font-size: 14px;
        color: #aaa;
      }
    }
  }
}
</style>
