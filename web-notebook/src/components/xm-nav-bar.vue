<template>
    <div class="xm-index-nav-bar" :class="{ 'is-sticky': isSticky }">
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
          <div class="text-wrapper_1 flex-col justify-content: center" v-if="showLoginButton">
            <span class="text_1" @click="navTo('/login')">注册/登录</span>
          </div>

          <!-- <div class="text-wrapper_2 flex-col"><span class="text_2" @click="navTo('/register')">注册</span></div> -->
        </div>
        <!-- 新增：底部80%白色线 -->
      </div>
    <!-- <div class="bottom-line"></div> -->
    </div>

</template>
<script>
export default {
  data() {
    return {
      isSticky: false, // 控制吸顶状态的变量
      navHeight: 0, // 导航栏自身高度（用于计算滚动阈值）
    };
  },
  // 在 xm-nav-bar.vue 的 mounted 钩子中修改滚动监听逻辑
  mounted() {
    this.$nextTick(() => {
      // 1. 获取导航栏自身高度
      this.navHeight = this.$el.offsetHeight;
      // 2. 直接监听 window 滚动（而非 .scale-root）
      window.addEventListener('scroll', this.handleScroll);
    });
  },
  beforeDestroy() {
    // 移除 window 滚动监听
    window.removeEventListener('scroll', this.handleScroll);
    window.removeEventListener('resize', this.handleResize);
  },
  methods: {
    handleScroll() {
      // 获取页面滚动距离（顶部滚动距离）
      const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
      this.isSticky = scrollTop > this.navHeight; // 判断是否达到吸顶条件  
    },
    handleResize() {
      // 窗口大小改变时重新计算导航栏高度
      this.navHeight = this.$el.offsetHeight;
      // 重新检查滚动状态
      this.handleScroll();
    },
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
    userInfo() {
      return localStorage.getItem('user-info') || '';
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
<!-- <style lang="less" scoped>

.xm-index-nav-bar {

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
      margin-left: 12%;
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
      // margin-left: 650px;
      margin-left: 30%;
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
      margin-right: 8%;

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
/* 吸顶状态核心样式（强制生效） */
.xm-index-nav-bar.is-sticky {
  position: fixed !important; /* 强制固定定位 */
  top: 0 !important;         /* 固定在顶部 */
  left: 0 !important;
  right: 0 !important;

  z-index: 9999 !important;  /* 避免被其他元素遮挡 */
  background-color: #000000dd;
  /* 解决父容器 transform 影响 fixed 定位的问题 */
  transform: none !important;
  /* 解决可能的布局偏移 */
  box-sizing: border-box;
}
</style> -->


<style lang="less" scoped>
.xm-index-nav-bar {
  height: 6.5vw; /* 85px 转换为vw（基于1300px基准视口） */
  padding-top: 1.9vw; /* 25px 转换为vw */
  background-color:#030300;
  width: 90%;
  margin: auto;
  border-bottom: 0.01vw solid #585858; /* 0.3px 转换为vw */
  z-index: 999;

  p, h1, h2, h3, h4, h5, h6, span, a {
    cursor: pointer !important;
  }
  // 底部80%白色线样式
  .bottom-line {
    z-index: 999;
    width: 92%; // 线的长度为80%
    height: 0.02vw; // 线的厚度（与原边框一致）
    // background-color: #fff; // 白色线
    border-bottom: 0.01vw solid #999;
    margin: 0 auto; // 水平居中（左右各留10%空白）
    position: absolute; // 绝对定位，不占用布局空间
    bottom: 0; // 固定在导航栏底部
    left: 0;
    right: 0; // 配合margin:0 auto实现居中
  }
  .content-bar {
    width: 100%; /* 1480px 转换为vw */
    line-height: 3.8vw; /* 50px 转换为vw */
    margin: 0 auto;
  }

  .box_23 {
    height: 8.8vw; /* 114px 转换为vw */
    
    .label_8 {
      width: 3.2vw; /* 44px 转换为vw */
      height: 3.2vw; /* 44px 转换为vw */
      margin-top: -0.3vw; /* -4px 转换为vw */
      margin-left: 3vw; /* 4px 转换为vw */
    }
    
    .text_65 {
      width: 10.8vw; /* 140px 转换为vw */
      height: 2.7vw; /* 35px 转换为vw */
      color: rgba(255, 255, 255, 1);
      font-size: 2.5vw; /* 35px 转换为vw */
      font-family: AlimamaFangYuanTiVF-SemiBold-Round;
      font-weight: NaN;
      text-align: left;
      white-space: nowrap;
      line-height: 3.2vw; /* 42px 转换为vw */
      margin-left: 0.9vw; /* 12px 转换为vw */
    }
    
    .text_66 {
      width: 2.7vw; /* 35px 转换为vw */
      height: 1.9vw; /* 25px 转换为vw */
      color: rgba(255, 255, 255, 1);
      font-size: 1.3vw; /* 17px 转换为vw */
      font-family: AlibabaPuHuiTi_3_65_Medium;
      font-weight: NaN;
      text-align: left;
      white-space: nowrap;
      line-height: 1.9vw; /* 25px 转换为vw */
      // margin-left: 50vw; /* 650px 转换为vw */
      margin-left:35%;
      margin-right: 2.3vw; /* 30px 转换为vw */
      margin-top: 0.3vw; /* 4px 转换为vw */
      
      &.active {
        color: #f89575;
        text-decoration: underline;
        font-weight: bold;
      }
    }
    
    .text_67 {
      width: 2.7vw; /* 35px 转换为vw */
      height: 1.9vw; /* 25px 转换为vw */
      color: rgba(255, 255, 255, 1);
      font-size: 1.3vw; /* 17px 转换为vw */
      font-family: AlibabaPuHuiTi_3_65_Medium;
      font-weight: NaN;
      text-align: left;
      white-space: nowrap;
      line-height: 1.9vw; /* 25px 转换为vw */
      margin-left: 0.2vw; /* 3px 转换为vw */
      margin-top: 0.3vw; /* 4px 转换为vw */
      
      &.active {
        color: #f89575;
        text-decoration: underline;
        font-weight: bold;
      }
    }
    
    .text_68 {
      width: 2.7vw; /* 35px 转换为vw */
      height: 1.9vw; /* 25px 转换为vw */
      color: rgba(255, 255, 255, 1);
      font-size: 1.3vw; /* 17px 转换为vw */
      font-family: AlibabaPuHuiTi_3_65_Medium;
      font-weight: NaN;
      text-align: left;
      white-space: nowrap;
      line-height: 1.9vw; /* 25px 转换为vw */
      margin-left: 2.7vw; /* 35px 转换为vw */
      margin-right: 14.6vw; /* 190px 转换为vw */
      margin-top: 0.3vw; /* 4px 转换为vw */
      
      &.active {
        color: #f89575;
        text-decoration: underline;
        font-weight: bold;
      }
    }
  }

  .group_1 {
    .text-wrapper_1 {
      background-color: rgba(248, 149, 117, 1);
      border-radius: 0.08vw; /* 1px 转换为vw */
      height: 2.7vw; /* 35px 转换为vw */
      // width: 8.4vw; /* 110px 转换为vw */
      width: 8.5vw;
      margin-right: 3.8vw; /* 50px 转换为vw */
      
      .text_1 {
        width: 2.7vw; /* 35px 转换为vw */
        height: 1.9vw; /* 25px 转换为vw */
        color: rgba(255, 255, 255, 1);
        font-size: 1.3vw; /* 17px 转换为vw */
        font-family: AlibabaPuHuiTi_3_65_Medium;
        font-weight: NaN;
        // text-align: left;
        text-align: center;
        white-space: nowrap;
        line-height: 1.9vw; /* 25px 转换为vw */
        margin: 0.4vw 0 0 1.4vw; /* 5px 0 0 18px 转换为vw */
      }
    }
    
    .text-wrapper_2 {
      border-radius: 0.08vw; /* 1px 转换为vw */
      height: 2.7vw; /* 35px 转换为vw */
      border: 0.07vw solid rgba(255, 255, 255, 1); /* 0.875px 转换为vw */
      width: 5.4vw; /* 70px 转换为vw */
      
      .text_2 {
        width: 2.7vw; /* 35px 转换为vw */
        height: 1.9vw; /* 25px 转换为vw */
        color: rgba(255, 255, 255, 1);
        font-size: 1.3vw; /* 17px 转换为vw */
        font-family: AlibabaPuHuiTi_3_65_Medium;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 1.9vw; /* 25px 转换为vw */
        margin: 0.4vw 0 0 1.3vw; /* 5px 0 0 17px 转换为vw */
      }
    }
    
    .user-info-wrapper {
      display: flex;
      align-items: center;
      
      .user-avatar {
        width: 3.1vw; /* 40px 转换为vw */
        height: 3.1vw; /* 40px 转换为vw */
        border-radius: 50%;
        background-color: #f89575;
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        font-size: 1.1vw; /* 14px 转换为vw */
        cursor: pointer;
        margin-right: 3.8vw; /* 50px 转换为vw */
      }
    }
  }
}

.xm-index-nav-bar.is-sticky {
  position: fixed !important;
  top: 0 !important;
  left: 0 !important;
  right: 0 !important;
  z-index: 9999 !important;
  background-color: #030300 !important; /* 修改为不透明颜色 */
  transform: none !important;
  box-sizing: border-box;
}
// 吸顶状态下保持线的样式一致
.xm-index-nav-bar.is-sticky .bottom-line {
  z-index: 2; // 吸顶时保持层级
}
</style>
