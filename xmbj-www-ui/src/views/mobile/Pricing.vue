<template>
  <div class="mobile-pricing">
    <MobileNavBar 
      showClose
      showTitle
      title="价格"
      @close="handleClose"
    />
    
    <div class="content">
      <div class="pricing-plan">
        <h3>免费版</h3>
        <p class="price">¥0<span>/月</span></p>
        <ul class="features">
          <li>基础笔记功能</li>
          <li>3个笔记本</li>
          <li>10MB单个文件上传</li>
        </ul>
        <MobileButton type="secondary">当前使用</MobileButton>
      </div>
      
      <div class="pricing-plan highlight">
        <h3>专业版</h3>
        <p class="price">¥29<span>/月</span></p>
        <ul class="features">
          <li>全部笔记功能</li>
          <li>无限笔记本</li>
          <li>100MB单个文件上传</li>
          <li>AI辅助创作</li>
        </ul>
        <MobileButton type="primary">升级</MobileButton>
      </div>
      
      <div class="pricing-plan">
        <h3>团队版</h3>
        <p class="price">¥99<span>/月</span></p>
        <ul class="features">
          <li>专业版所有功能</li>
          <li>最多5人协作</li>
          <li>团队管理工具</li>
          <li>优先支持</li>
        </ul>
        <MobileButton type="secondary">联系销售</MobileButton>
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
  name: 'MobilePricing',
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
.mobile-pricing {
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

    .pricing-plan {
      margin-bottom: 24px;
      padding: 16px;
      background-color: #222;
      border-radius: 8px;
      text-align: center;

      &.highlight {
        border: 1px solid #ff6b00;
      }

      h3 {
        font-size: 20px;
        margin-bottom: 8px;
      }

      .price {
        font-size: 28px;
        font-weight: bold;
        margin-bottom: 16px;
        color: #ff6b00;

        span {
          font-size: 14px;
          color: #aaa;
        }
      }

      .features {
        list-style: none;
        margin-bottom: 16px;
        padding: 0;

        li {
          padding: 8px 0;
          font-size: 14px;
          color: #aaa;
        }
      }
    }
  }
}
</style>
