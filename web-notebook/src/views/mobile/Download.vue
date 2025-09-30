<template>
  <div class="mobile-download">
    <MobileNavBar 
      showClose
      showTitle
      title="下载"
      @close="handleClose"
    />
    
    <div class="content">
      <div class="download-section">
        <h3>小马笔记安卓版</h3>
        <p class="support">支持Android 4.4及以上系统</p>
        <MobileButton type="primary" class="download-btn">立即下载</MobileButton>
      </div>
      
      <div class="download-section">
        <h3>小马笔记iOS版</h3>
        <p class="support">支持iOS 9.0及以上系统</p>
        <MobileButton type="primary" class="download-btn">立即下载</MobileButton>
      </div>
    </div>
    
    <MobileBottom />
  </div>
</template>

<script>
import MobileNavBar from '@/components/mobile/MobileNavBar.vue'
import MobileBottom from '@/components/mobile/MobileBottom.vue'
import MobileButton from '@/components/mobile/MobileButton.vue'

export default {
  name: 'MobileDownload',
  components: {
    MobileNavBar,
    MobileBottom,
    MobileButton
  },
  methods: {
    handleClose() {
      this.$router.go(-1)
    }
  }
}
</script>

<style lang="less" scoped>
.mobile-download {
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

    .download-section {
      margin-bottom: 32px;
      padding: 16px;
      background-color: #222;
      border-radius: 8px;
      text-align: center;

      h3 {
        font-size: 20px;
        margin-bottom: 8px;
      }

      .support {
        font-size: 14px;
        color: #aaa;
        margin-bottom: 16px;
      }

      .download-btn {
        max-width: 200px;
        margin: 0 auto;
      }
    }
  }
}
</style>
