<template>
  <!-- <div class="mobile-nav-bar">
    <div class="left">
      <button v-if="showClose" class="close-btn" @click="handleClose">×</button>
      <img v-if="showLogo" src="@/assets/mobile/logo.png" alt="小马笔记" class="logo">
      <span v-if="showTitle" class="title">{{ title }}</span>
    </div>
    <div class="right">
      <button v-if="showLogin" class="login-btn" @click="handleLogin">登录</button>
      <button v-if="showRegister" class="register-btn" @click="handleRegister">注册</button>
      <button v-if="showMore" class="more-btn" @click="handleMore">...</button>
    </div>
  </div> -->
  <div class="mobile-nav-bar flex-row">
    <img
      class="thumbnail_3"
      referrerpolicy="no-referrer"
      :src="logoImage"
      v-if="showLogo"
    />
    <span class="text_2" v-if="showTitle">小马笔记</span>
    <button class="text_3" v-if="showLogin" @click="handleLogin">登录</button>
    <button class="text_4" v-if="showRegister" @click="handleRegister">注册</button>
    <!-- <button class="text_5" v-if="showMore" @click="handleMore">...</button> -->
     <img
          class="icon_1"
          referrerpolicy="no-referrer"
          :src="moreImage"
        />
  </div>
</template>

<script>
import mobileRouter from '@/router/mobile-router.js'
export default {
  name: 'MobileNavBar',
  props: {
    showClose: {
      type: Boolean,
      default: false
    },
    showLogo: {
      type: Boolean,
      default: true
    },
    showTitle: {
      type: Boolean,
      default: true
    },
    title: {
      type: String,
      default: '小马笔记'
    },
    showLogin: {
      type: Boolean,
      default: true
    },
    showRegister: {
      type: Boolean,
      default: true
    },
    showMore: {
      type: Boolean,
      default: true
    }
  },
  computed: {
    logoImage() {
      // 使用项目中已有的图片
      try {
        // 尝试使用下载页面中的 logo src\components\img\logo.png
        return require('@/components/img/logo.png');
      } catch (e) {
        // 如果找不到文件，返回一个空字符串
        return '';
      }
    },
    moreImage() {
      // 使用项目中已有的图片
      try {
        // 尝试使用下载页面中的 logo src\components\img\logo.png
        return require('@/components/img/more.png');
      } catch (e) {
        // 如果找不到文件，返回一个空字符串
        return '';
      }
    }

  },
  methods: {
    getRoutePath(name) {
      const route = mobileRouter.options.routes.find(r => r.name === name)
      return route ? route.path : '/'
    },
    handleClose() {
      this.$emit('close');
    },
    handleLogin() {
      alert(1); // 添加弹窗
      console.log('Login clicked'); // 添加日志
      this.$emit('login');
      if (this.$router) {
        try {
          this.$router.push(this.getRoutePath('MobileDownload'))
          this.$router.push('/mobile/account/account-detail');
        } catch (e) {
          console.log(e);
          window.location.href = '/mobile/account/account-detail';
        }
      } else {
        console.error('Router not available');
        window.location.href = '/mobile/account/account-detail';
      }
    },
    handleRegister() {
      alert(2); // 添加弹窗
      console.log('Register clicked'); // 添加日志
      this.$emit('register');
      if (this.$router) {
        try {
          this.$router.push('/mobile/account/account-detail');
        } catch (e) {
          console.log(e);
          window.location.href = '/mobile/account/account-detail';
        }
      } else {
        console.error('Router not available');
        window.location.href = '/mobile/account/account-detail';
      }
    },
    handleMore() {
      alert(3); // 添加弹窗
      console.log('More clicked'); // 添加日志
      this.$emit('more');
      if (this.$router) {
        try {
          this.$router.push('/mobile/account/account-detail');
        } catch (e) {
          console.log(e);
          window.location.href = '/mobile/account/account-detail';
        }
      } else {
        console.error('Router not available');
        window.location.href = '/mobile/account/account-detail';
      }
    }
  }
};
</script>

<style lang="less" scoped>
// .mobile-nav-bar {
//   display: flex;
//   justify-content: space-between;
//   align-items: center;
//   padding: 12px 16px;
//   background-color: #000;
//   color: #fff;
//   height: 56px;
//   box-sizing: border-box;
//   position: fixed;
//   top: 0;
//   left: 0;
//   right: 0;
//   z-index: 100;

//   .left {
//     display: flex;
//     align-items: center;

//     .close-btn {
//       background: none;
//       border: none;
//       color: #fff;
//       font-size: 24px;
//       margin-right: 10px;
//       padding: 0;
//       cursor: pointer;
//     }

//     .logo {
//       height: 24px;
//       margin-right: 8px;
//     }

//     .title {
//       font-size: 18px;
//       font-weight: bold;
//     }
//   }

//   .right {
//     display: flex;
//     align-items: center;

//     button {
//       background: none;
//       border: none;
//       color: #fff;
//       font-size: 14px;
//       margin-left: 12px;
//       padding: 4px 8px;
//       cursor: pointer;

//       &.login-btn {
//         border: 1px solid #fff;
//         border-radius: 4px;
//       }

//       &.register-btn {
//         background-color: #ff6b00;
//         border-radius: 4px;
//       }

//       &.more-btn {
//         font-size: 18px;
//         line-height: 1;
//       }
//     }
//   }
// }

.mobile-nav-bar {
  margin: 26px 15px 0 14px;
  .thumbnail_3 {
    width: 16px;
    height: 16px;
    margin-bottom: 1px;
  }
  .text_2 {
    height: 16px;
    overflow-wrap: break-word;
    color: rgba(255, 255, 255, 1);
    font-size: 16px;
    font-family: AlimamaFangYuanTiVF-SemiBold-Round;
    font-weight: NaN;
    text-align: left;
    white-space: nowrap;
    line-height: 19px;
    margin-left: 6px;
  }
  .text_3 {
    overflow-wrap: break-word;
    color: rgba(248, 149, 117, 1);
    font-size: 12px;
    font-family: AlibabaPuHuiTi_3_65_Medium;
    font-weight: NaN;
    text-align: left;
    white-space: nowrap;
    line-height: 17px;
    margin-left: 166px;
  }
  .text_4 {
    overflow-wrap: break-word;
    color: rgba(212, 212, 212, 1);
    font-size: 12px;
    font-family: AlibabaPuHuiTi_3_65_Medium;
    font-weight: NaN;
    text-align: left;
    white-space: nowrap;
    line-height: 17px;
    margin-left: 14px;
  }
  .text_5 {
    font-size: 18px;
    line-height: 1;
    background: none;
    border: none;
    cursor: pointer;
    color: #fff;
  }
  .thumbnail_4 {
        width: 18px;
        height: 4px;
        margin: 4px 0 9px 14px;
   }
  // .icon_1 {
  //   width: 16px;
  //   height: 4px;
  // }
  .icon_1 {
    width: 18px;
    height: 4px;
    margin: 6px 0 6px 6px;
  }
}
</style>
