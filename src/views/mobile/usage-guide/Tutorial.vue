<template>
  <div class="tutorial-page">
    <MobileNavBar 
      showClose
      showTitle
      title="使用教程"
      @close="handleClose"
    />
    
    <div class="content">
      <div class="section">
        <h3>基础功能介绍</h3>
        <p>小马笔记是一款专为内容创作者设计的笔记应用，提供以下基础功能：</p>
        <ul>
          <li>创建、编辑和组织笔记</li>
          <li>多级目录结构管理</li>
          <li>富文本编辑支持</li>
          <li>多设备同步</li>
        </ul>
      </div>
      
      <div class="section">
        <h3>高级功能</h3>
        <p>专业版用户还可以享受以下高级功能：</p>
        <ul>
          <li>AI辅助写作</li>
          <li>Markdown支持</li>
          <li>团队协作</li>
          <li>自定义模板</li>
        </ul>
      </div>
      
      <div class="section">
        <h3>方法与功能</h3>
        <p>以下是一些常用功能的操作方法：</p>
        <ol>
          <li>创建新笔记：点击底部"+"按钮</li>
          <li>整理笔记：长按笔记可拖动排序</li>
          <li>搜索笔记：顶部搜索框输入关键词</li>
          <li>导出笔记：在笔记详情页点击分享按钮</li>
        </ol>
      </div>
    </div>
    
    <MobileBottom />
  </div>
</template>

<script>
import MobileNavBar from '@/components/mobile/MobileNavBar.vue'
import MobileBottom from '@/components/mobile/MobileBottom.vue'

export default {
  name: 'Tutorial',
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
.tutorial-page {
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
