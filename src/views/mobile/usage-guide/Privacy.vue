<template>
  <div class="privacy-page">
    <MobileNavBar 
      showClose
      showTitle
      title="隐私协议"
      @close="handleClose"
    />
    
    <div class="content">
      <div class="section">
        <h3>隐私政策</h3>
        <p>小马笔记（以下简称"我们"）非常重视用户的隐私保护。在使用我们的服务时，我们可能会收集和使用您的相关信息。我们希望通过本《隐私政策》向您说明，在使用我们的服务时，我们如何收集、使用、储存和分享这些信息，以及我们为您提供的访问、更新、控制和保护这些信息的方式。</p>
      </div>
      
      <div class="section">
        <h3>我们收集的信息</h3>
        <p>我们提供服务时，可能会收集、储存和使用下列与您有关的信息：</p>
        <ul>
          <li>您提供的信息：包括但不限于您的姓名、手机号码、电子邮件地址等。</li>
          <li>设备信息：我们可能会接收并记录您所使用的设备相关信息。</li>
          <li>日志信息：当您使用我们的服务时，我们可能会自动收集相关信息并存储为服务日志信息。</li>
        </ul>
      </div>
      
      <div class="section">
        <h3>信息的使用</h3>
        <p>我们可能将在向您提供服务的过程之中所收集的信息用作下列用途：</p>
        <ul>
          <li>向您提供服务</li>
          <li>帮助我们设计新服务，改善现有服务</li>
          <li>软件认证或管理软件升级</li>
          <li>让您参与有关我们产品和服务的调查</li>
        </ul>
      </div>
      
      <div class="section">
        <h3>信息的分享</h3>
        <p>我们不会将您的个人信息转让给任何公司、组织和个人，但以下情况除外：</p>
        <ul>
          <li>事先获得您的明确同意或授权</li>
          <li>根据适用的法律法规、法律程序的要求、强制性的行政或司法要求所必须的情况</li>
          <li>在涉及合并、收购、资产转让或类似的交易时</li>
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
  name: 'Privacy',
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
.privacy-page {
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

    .section {
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

      ul {
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
