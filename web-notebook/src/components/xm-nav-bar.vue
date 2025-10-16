<template>
    <div class="xm-index-nav-bar">
      <div class="content-bar">
        <div class="box_23 flex-row group_1">
          <img @click="navTo('/')" class="label_8" referrerpolicy="no-referrer"
            src="./img/logo.png" />
          <span class="text_65" @click="navTo('/')">小马笔记</span>
          <span class="text_66" :class="{ active: isIndex }"  @click="navTo('/index')">首页</span>
          <span class="text_67" :class="{ active: isPriceActive }"  @click="navTo('/price')">价格</span>
          <span class="text_68" :class="{ active: isDownloadActive }" @click="navTo('/download')">下载</span>
          <!-- 登录状态：显示用户信息图标（点击退出） -->
          <div class="user-info-wrapper flex-col" v-if="hasToken">
            <div class="user-avatar" @click="logout">
              <span class="user-info">{{ truncateUserInfo() }}</span> <!-- 示例：显示手机号前几位，实际可从接口获取 -->
            </div>
          </div>
          <div class="text-wrapper_1 flex-col" v-if="showLoginButton">
            <span class="text_1" @click="navTo('/login')">注册/登录</span>
          </div>

          <!-- <div class="text-wrapper_2 flex-col"><span class="text_2" @click="navTo('/register')">注册</span></div> -->
        </div>
      </div>

    </div>

</template>
<script>
export default {
  methods: {
    navTo(url) {
      this.$router.push(url);
    },
    // 新增：退出登录逻辑
    logout() {
      localStorage.removeItem('token'); // 清除登录态 Token
      localStorage.removeItem('user-info');
      this.$router.push('/index'); // 
      // alert('退出登录成功');
    },
    truncateUserInfo() {
      if (!this.userInfo) return '';
      if (this.userInfo.length <= 3) {
        return this.userInfo; // 若长度≤3，直接显示
      }
      return this.userInfo.slice(0, 3) + '***'; // 截取前3位，后面加***
    }
  },
  computed: {
    hasToken() {
      return !!localStorage.getItem('token');
    },

    // 新增计算属性，判断是否显示登录按钮
    showLoginButton() {
      const excludedRoutes = ['/account'];
      const isExcludedPage = excludedRoutes.includes(this.$route.path);
      return !(isExcludedPage && this.hasToken);
    },
    isIndex() {
      return this.$route.path === '/index'; // 匹配价格页路由
    },
    isPriceActive() {
      return this.$route.path === '/price'; // 匹配价格页路由
    },
    isDownloadActive() {
      return this.$route.path === '/download'; // 匹配下载页路由
    }
  }
}
</script>
<style lang="less" scoped>

.xm-index-nav-bar {
  // height: 95px;
  // padding-top: 30px;
  height: 85px;
  padding-top: 25px;
  background-color: #000000dd;
  border-bottom: 0.3px solid #999;
  // box-shadow: 0px 1px 0px 0px rgba(255, 255, 255, 0.5);
  z-index: 999;

      /* 全局覆盖所有文本元素（p、h1~h6、span 等） */
  p, h1, h2, h3, h4, h5, h6, span, a {
    cursor: pointer !important; /* 强制覆盖默认样式 */
  }
  .content-bar {
    width: 1480px;
    line-height: 50px;
    margin: 0 auto;
  }

  .box_23 {
    // position: absolute;
    // left: 0;
    // top: 0;
    // width: 1680px;
    height: 114px;
    .label_8 {
      width: 44px;
      height: 44px;
      margin-top: -4px;
    }
    .text_65 {
      width: 140px;
      height: 35px;
      overflow-wrap: break-word;
      color: rgba(255, 255, 255, 1);
      font-size: 35px;
      font-family: AlimamaFangYuanTiVF-SemiBold-Round;
      font-weight: NaN;
      text-align: left;
      white-space: nowrap;
      line-height: 42px;
      margin-left: 12px;

    }
    .text_66 {
      width: 35px;
      height: 25px;
      overflow-wrap: break-word;
      color: rgba(255, 255, 255, 1);
      font-size: 17px;
      font-family: AlibabaPuHuiTi_3_65_Medium;
      font-weight: NaN;
      text-align: left;
      white-space: nowrap;
      line-height: 25px;
      margin-left: 650px;
      margin-right: 30px;
      margin-top: 4px;
      &.active {  // 当添加active类时生效
        color: #f89575; // 示例：改为按钮的橙色，与注册/登录按钮呼应
        // 可选：添加下划线
        text-decoration: underline;
        // 可选：加粗
        font-weight: bold;
      }
    }
    .text_67 {
      width: 35px;
      height: 25px;
      overflow-wrap: break-word;
      color: rgba(255, 255, 255, 1);
      font-size: 17px;
      font-family: AlibabaPuHuiTi_3_65_Medium;
      font-weight: NaN;
      text-align: left;
      white-space: nowrap;
      line-height: 25px;
      margin-left: 3px;
      margin-top: 4px;
      &.active {  // 当添加active类时生效
        color: #f89575; // 示例：改为按钮的橙色，与注册/登录按钮呼应
        // 可选：添加下划线
        text-decoration: underline;
        // 可选：加粗
        font-weight: bold;
      }
    }
    .text_68 {
      width: 35px;
      height: 25px;
      overflow-wrap: break-word;
      color: rgba(255, 255, 255, 1);
      font-size: 17px;
      font-family: AlibabaPuHuiTi_3_65_Medium;
      font-weight: NaN;
      text-align: left;
      white-space: nowrap;
      line-height: 25px;
      margin-left: 35px;
      margin-right: 190px;
      margin-top: 4px;
      &.active {  // 当添加active类时生效
        color: #f89575; // 示例：改为按钮的橙色，与注册/登录按钮呼应
        // 可选：添加下划线
        text-decoration: underline;
        // 可选：加粗
        font-weight: bold;
      }
    }
  }

  .group_1 {
    // width: 165px;
    // height: 35px;
    // float: right;

    //   margin: 39px 100px 0 0;
    .text-wrapper_1 {
      background-color: rgba(248, 149, 117, 1);
      border-radius: 1px;
      height: 35px;
      width: 110px;
      margin-right: 50px;

      .text_1 {
        width: 35px;
        height: 25px;
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 17px;
        font-family: AlibabaPuHuiTi_3_65_Medium;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 25px;
        margin: 5px 0 0 18px;
      }
    }

    .text-wrapper_2 {
      border-radius: 1px;
      height: 35px;
      border: 0.875px solid rgba(255, 255, 255, 1);
      width: 70px;

      .text_2 {
        width: 35px;
        height: 25px;
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 17px;
        font-family: AlibabaPuHuiTi_3_65_Medium;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 25px;
        margin: 5px 0 0 17px;
      }
    }

    // 新增：用户信息图标样式
    .user-info-wrapper {
      display: flex;
      align-items: center;
      .user-avatar {
        width: 40px;
        height: 40px;
        border-radius: 50%; // 圆形
        background-color: #f89575; // 与“注册/登录”按钮同色
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        font-size: 14px;
        cursor: pointer;
        margin-right: 50px;
      }
    }
  }
}
</style>
