<template>
  <div class="faq-page">
    <MobileNavBar 
      showClose
      showTitle
      title="常见问题"
      @close="handleClose"
    />
    
    <div class="content">
      <div class="question">
        <h3>如何注册小马笔记账号？</h3>
        <p>您可以通过手机号注册小马笔记账号。打开应用后，点击"注册"按钮，输入手机号并获取验证码，设置密码后即可完成注册。</p>
      </div>
      
      <div class="question">
        <h3>忘记密码怎么办？</h3>
        <p>在登录页面点击"忘记密码"，按照提示输入手机号获取验证码，然后设置新密码即可。</p>
      </div>
      
      <div class="question">
        <h3>如何升级到专业版？</h3>
        <p>前往"价格"页面，选择专业版套餐，按照提示完成支付即可升级。</p>
      </div>
      
      <div class="question">
        <h3>笔记数据会丢失吗？</h3>
        <p>小马笔记采用云端存储技术，您的笔记数据会自动同步到服务器，即使更换设备也不会丢失。</p>
      </div>
      
      <div class="question">
        <h3>如何联系客服？</h3>
        <p>您可以通过应用内的"联系我们"页面找到客服邮箱，或者通过B站、抖音等社交媒体平台联系我们。</p>
      </div>
    </div>
    
    <MobileBottom />
  </div>
</template>

<script>
import MobileNavBar from '@/components/mobile/MobileNavBar.vue'
import MobileBottom from '@/components/mobile/MobileBottom.vue'

export default {
  name: 'FAQ',
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
.faq-page {
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

    .question {
      margin-bottom: 24px;
      padding-bottom: 16px;
      border-bottom: 1px solid #333;

      h3 {
        font-size: 16px;
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
