<template>
  <div class="quick-start-page">
    <MobileNavBar 
      showClose
      showTitle
      title="快速上手"
      @close="handleClose"
    />
    
    <div class="content">
      <div class="step">
        <h3>第一步：下载安装</h3>
        <p>根据您的设备类型下载并安装小马笔记应用：</p>
        <ul>
          <li>iOS用户：前往App Store搜索"小马笔记"</li>
          <li>安卓用户：前往应用商店或官网下载</li>
        </ul>
      </div>
      
      <div class="step">
        <h3>第二步：注册账号</h3>
        <p>打开应用后，按照引导完成账号注册：</p>
        <ol>
          <li>点击"注册"按钮</li>
          <li>输入手机号获取验证码</li>
          <li>设置密码完成注册</li>
        </ol>
      </div>
      
      <div class="step">
        <h3>第三步：开始使用</h3>
        <p>注册成功后，您可以立即开始使用：</p>
        <ul>
          <li>创建您的第一个笔记本</li>
          <li>添加笔记内容</li>
          <li>探索更多功能</li>
        </ul>
      </div>
    </div>
    
    <MobileFooter />
  </div>
</template>

<script>
import MobileNavBar from '@/components/mobile/MobileNavBar.vue'
import MobileFooter from '@/components/mobile/MobileFooter.vue'

export default {
  name: 'QuickStart',
  components: {
    MobileNavBar,
    MobileFooter
  },
  methods: {
    handleClose() {
      this.$router.go(-1)
    }
  }
}
</script>

<style lang="less" scoped>
.quick-start-page {
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

    .step {
      margin-bottom: 32px;

      h3 {
        font-size: 18px;
        margin-bottom: 12px;
        color: #ff6b00;
      }

      p {
        font-size: 14px;
        margin-bottom: 8px;
        color: #aaa;
      }

      ul, ol {
        padding-left: 20px;
        margin-bottom: 16px;

        li {
          font-size: 14px;
          color: #aaa;
          margin-bottom: 8px;
        }
      }
    }
  }
}
</style>
