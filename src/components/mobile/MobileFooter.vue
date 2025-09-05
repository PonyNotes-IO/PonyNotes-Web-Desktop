<template>
  <div class="mobile-footer">
    <div class="footer-links">
      <div class="link-group">
        <h4>产品</h4>
        <router-link to="/mobile">桌面端</router-link>
        <router-link to="/mobile">手机端</router-link>
        <router-link to="/mobile/docs/privacy">隐私协议</router-link>
      </div>
      <div class="link-group">
        <h4>资源</h4>
        <router-link to="/mobile/docs/tutorial">使用教程</router-link>
        <router-link to="/mobile/docs/quick-start">快速上手</router-link>
        <router-link to="/mobile/docs/faq">常见问题</router-link>
      </div>
      <div class="link-group">
        <h4>联系我们</h4>
        <a href=" https://www.bilibili.com " target="_blank">B站</a>
        <a href=" https://www.douyin.com " target="_blank">抖音</a>
        <a href=" https://www.xiaohongshu.com " target="_blank">小红书</a>
        <a href="mailto:support@ponynotes.com">邮箱</a>
      </div>
    </div>
    <div class="copyright">
      小马笔记 | ICP备2025131868号
    </div>
  </div>
</template>

<script>
export default {
  name: 'MobileFooter'
}
</script>

<style lang="less" scoped>
.mobile-footer {
  background-color: #000;
  color: #fff;
  padding: 24px 16px;
  font-size: 14px;

  .footer-links {
    display: flex;
    justify-content: space-between;
    margin-bottom: 20px;

    .link-group {
      flex: 1;
      padding: 0 8px;

      h4 {
        margin-bottom: 12px;
        font-size: 16px;
        font-weight: bold;
      }

      a {
        display: block;
        color: #aaa;
        margin-bottom: 8px;
        text-decoration: none;

        &:hover {
          color: #fff;
        }
      }
    }
  }

  .copyright {
    text-align: center;
    color: #666;
    padding-top: 16px;
    border-top: 1px solid #333;
  }
}
</style>
