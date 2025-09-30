<template>
  <div class="mobile-bottom flex-col">
    <div class="text_row_title flex-row justify-between">
      <span class="text_titile">产品</span>
      <span class="text_titile">资源</span>
      <span class="text_titile">联系我们</span>
    </div>
    <div class="text_row flex-row">
      <span class="text_col_1"  @click="handleClick('download')">桌面端</span>
      <span class="text_col_2"  @click="handleClick('price')">使用教程</span>
      <span class="text_col_3"  @click="handleClick('bilibili')">B站</span>
    </div>
    <div class="text_row flex-row">
      <span class="text_col_1"  @click="handleClick('download')">手机端</span>
      <span class="text_col_2"  @click="handleClick('price')">快速上手</span>
      <span class="text_col_3"  @click="handleClick('douyin')">抖音</span>
    </div>
    <div class="text_row flex-row justify-between">
      <span class="text_col_1" @click="handleClick('price')">隐私协议</span>
      <span class="text_col_2 text_67" @click="handleClick('price')">常见问题</span>
      <span class="text_col_3 " @click="handleClick('xiaohongshu')">小红书</span>
    </div>
    <div class="text_row flex-row justify-between">
      <span class="text_col_3 text_68">邮箱</span>
    </div>
      
    <img
      class="image_4"
      referrerpolicy="no-referrer"
      :src="mobile_bottom"
    />
    <div class="text-wrapper_27">
      <span class="text_70">小马笔记</span>
      <span class="text_71">&nbsp;|</span>
      <span class="text_72">&nbsp;京ICP备2025131868号</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MobileBottom',
  data() {
    return {
      showEmail: false, // 控制邮箱悬浮提示的显示/隐藏
      email: 'ponynotes-service@ponynotes.com' // 替换为实际邮箱地址
    }
  },
  computed: {
      mobile_bottom() {
        try {
          return require('@/components/img/mobile_bottom.png');
        } catch (e) {
          // 如果找不到文件，返回一个空字符串
          return '';
        }
      }
    },
  methods: {
    handleClick(target) {
      switch (target) {
        case 'refresh':
          window.location.reload();
          break;
        case 'price': // 产品、资源、联系我们、使用教程、快速上手  跳转「价格页面」
          // window.location.href = '/price'; 
          // alert(this.$router)
          this.$router.push('/mobile/price');
          break;
        case 'home': // 产品、资源、联系我们、使用教程、快速上手  跳转「价格页面」
          this.$router.push('/mobile/home'); 
          break;
        case 'download': // 产品、资源、联系我们、使用教程、快速上手  跳转「价格页面」
          // window.location.href = '/download';
          this.$router.push('/mobile/download');  
          break;    
        case 'bilibili': // B站 → 跳转官网
          window.location.href = 'https://www.bilibili.com/';
          break;
        case 'douyin': // 抖音 → 跳转官网
          window.location.href = 'https://www.douyin.com/';
          break;
        case 'xiaohongshu': // 小红书 → 跳转官网
          window.location.href = 'https://www.xiaohongshu.com/';
          break;
        default:
          break;
      }
    },
    /**
     * 鼠标悬浮邮箱时显示提示
     */
    handleMouseEnter() {
      this.showEmail = true;
    },
    /**
     * 鼠标离开邮箱时隐藏提示
     */
    handleMouseLeave() {
      this.showEmail = false;
    }
  }
}
</script>

<style lang="less" scoped>
  .mobile-bottom {
    padding: 1.33vw 0 2.13vw 0;
    /* 邮箱悬浮提示样式 */
    .email-tooltip {
      position: absolute;        /* 绝对定位，相对于父元素（text-wrapper_21） */
      background-color: #333;    /* 背景色 */
      color: #fff;               /* 文字色 */
      padding: 4px 8px;          /* 内边距 */
      border-radius: 4px;        /* 圆角 */
      font-size: 14px;           /* 字号 */
      top: 85%;                 /* 在邮箱下方显示 */
      left: 85%;                 /* 水平居中 */
      transform: translateX(-50%); /* 精准居中 */
      white-space: nowrap;       /* 防止文字换行 */
      z-index: 999;              /* 保证层级最高 */
    }
    .text_row_title {
      width: 82.14vw;
      margin: 0 8vw 0 9.86vw;
      .text_titile {
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 3.73vw;
        font-family: AlibabaPuHuiTi_3_55_Regular;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 5.34vw;
      }
    }
    .text_row {
      margin: 2.66vw 17.6vw 0 9.86vw;
      .text_67 {
        margin-left: 21vw !important;
      }
      .text_68 {
        margin-left: 67.2vw !important;
      }
      .text_col_1 {
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 3.2vw;
        font-family: AlibabaPuHuiTi_3_55_Regular;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 4.54vw;
      }
      .text_col_2 {
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 3.2vw;
        font-family: AlibabaPuHuiTi_3_55_Regular;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 4.54vw;
        margin-left: 24vw;
      }
      .text_col_3 {
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 3.2vw;
        font-family: AlibabaPuHuiTi_3_55_Regular;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 4.54vw;
        margin-left: 20.8vw;
      }
    }
    .image_4 {
      width: 100vw;
      height: 0.27vw;
      margin-top: 4vw;
    }
    .text-wrapper_27 {
      width: 48.27vw;
      height: 4.54vw;
      overflow-wrap: break-word;
      font-size: 0;
      font-family: PingFangSC-Medium;
      font-weight: 500;
      text-align: left;
      white-space: nowrap;
      line-height: 4.54vw;
      margin: 1.86vw 25.06vw 0 26.66vw;
      .text_70 {
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 3.2vw;
        font-family: PingFangSC-Medium;
        font-weight: 500;
        text-align: left;
        white-space: nowrap;
        line-height: 4.54vw;
      }
      .text_71 {
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 3.2vw;
        font-family: PingFangSC-Ultralight;
        font-weight: 200;
        text-align: left;
        white-space: nowrap;
        line-height: 4.54vw;
      }
      .text_72 {
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 3.2vw;
        font-family: PingFangSC-Medium;
        font-weight: 500;
        text-align: left;
        white-space: nowrap;
        line-height: 4.54vw;
      }
    }
  }
</style>
