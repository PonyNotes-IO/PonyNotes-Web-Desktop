<template>
      <div class="index-bottom-pc ">
          <div class="text-wrapper_17 flex-row">
              <span class="text_50">产品</span>
              <span class="text_51">资源</span>
              <span class="text_52">联系我们</span>
          </div>
          <div class="section_4 flex-row">
              <div class="image-text_9 flex-row justify-between">
                  <img class="image_4" referrerpolicy="no-referrer"
                      src="./img/bottom-logo.png" />
                  <span class="text-group_12">小马笔记</span>
              </div>
              <div class="text-wrapper_18 flex-col justify-between">
                  <span class="text_53" @click="handleClick('download')" >桌面端</span>
                  <span class="text_54" @click="handleClick('download')" >手机端</span>
              </div>
              <div class="text-wrapper_19 flex-col justify-between">
                  <span class="text_55" @click="handleClick('price')">使用教程</span>
                  <span class="text_56" @click="handleClick('price')">快速上手</span>
              </div>
              <div class="text-wrapper_20 flex-col justify-between">
                  <span class="text_57" @click="handleClick('bilibili')">B站</span>
                  <span class="text_58" @click="handleClick('douyin')">抖音</span>
              </div>
          </div>
          <div class="text-wrapper_21 flex-row">
              <span class="text_59">京ICP备2025131868号</span>
              <span class="text_60">隐私协议</span>
              <span class="text_61"  @click="handleClick('price')">常见问题</span>
              <span class="text_62"  @click="handleClick('xiaohongshu')">小红书</span>
          </div>
          <div class="text-wrapper_22 flex-row justify-between">
              <span class="text_63">用户协议</span>
              <span class="text_64"  @mouseenter="handleMouseEnter" 
                @mouseleave="handleMouseLeave">邮箱</span>
                <!-- 邮箱悬浮提示（条件渲染） -->
                <span 
                  v-show="showEmail" 
                  class="email-tooltip"
                >{{ email }}</span>
          </div>
      </div>
</template>
<script lang="js">
  export default {
    name: 'IndexBottomPc',
    data() {
      return {
        showEmail: false, // 控制邮箱悬浮提示的显示/隐藏
        email: 'your-email@example.com' // 替换为实际邮箱地址
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
            this.$router.push('/price');
            break;
          case 'home': // 产品、资源、联系我们、使用教程、快速上手  跳转「价格页面」
            this.$router.push('/index'); 
            break;
          case 'download': // 产品、资源、联系我们、使用教程、快速上手  跳转「价格页面」
            // window.location.href = '/download';
            this.$router.push('/download');  
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
  .index-bottom-pc {
    /* 全局覆盖所有文本元素（p、h1~h6、span 等） */
    p, h1, h2, h3, h4, h5, h6, span, a {
        cursor: pointer !important; /* 强制覆盖默认样式 */
    }
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

    /* 父元素需设为 relative，使 tooltip 绝对定位生效 */
    .text-wrapper_21 {
      position: relative;
    }
    box-shadow: 0px -1px 2px -1px rgba(255, 255, 255, 0.5);
    background-color: rgba(0, 0, 0, 1);
    height: 312px;
    width: 1680px;
    margin-top: 80px;
    .text-wrapper_17 {
      width: 585px;
      height: 37px;
      // margin: 52px 0 0 867px;
      margin: 52px 0 0 867px;
      .text_50 {
        width: 52px;
        height: 37px;
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 26px;
        font-family: AlibabaPuHuiTi_3_55_Regular;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 37px;
      }
      .text_51 {
        width: 52px;
        height: 37px;
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 26px;
        font-family: AlibabaPuHuiTi_3_55_Regular;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 37px;
        margin-left: 200px;
      }
      .text_52 {
        width: 104px;
        height: 37px;
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 26px;
        font-family: AlibabaPuHuiTi_3_55_Regular;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 37px;
        margin-left: 200px;
      }
    }
    .section_4 {
      width: 1156px;
      height: 83px;
      margin: 3px 0 0 228px;
      .image-text_9 {
        width: 294px;
        height: 67px;
        .image_4 {
          // width: 67px;
          // height: 67px;
          margin-top: 8px;
          width: 60px;
          height: 60px;

        }
        .text-group_12 {
          width: 210px;
          height: 53px;
          overflow-wrap: break-word;
          color: rgba(255, 255, 255, 1);
          font-size: 45px;
          font-family: AlimamaFangYuanTiVF-SemiBold-Round;
          font-weight: NaN;
          text-align: left;
          white-space: nowrap;
          line-height: 63px;
          margin-top: 8px;
        }
      }
      .text-wrapper_18 {
        width: 54px;
        height: 65px;
        margin: 18px 0 0 345px;
        .text_53 {
          width: 54px;
          height: 25px;
          overflow-wrap: break-word;
          color: rgba(255, 255, 255, 1);
          font-size: 18px;
          font-family: AlibabaPuHuiTi_3_55_Regular;
          font-weight: NaN;
          text-align: left;
          white-space: nowrap;
          line-height: 25px;
        }
        .text_54 {
          width: 54px;
          height: 25px;
          overflow-wrap: break-word;
          color: rgba(255, 255, 255, 1);
          font-size: 18px;
          font-family: AlibabaPuHuiTi_3_55_Regular;
          font-weight: NaN;
          text-align: left;
          white-space: nowrap;
          line-height: 25px;
          margin-top: 15px;
        }
      }
      .text-wrapper_19 {
        width: 71px;
        height: 65px;
        margin: 18px 50px 0 200px;
        .text_55 {
          width: 71px;
          height: 25px;
          overflow-wrap: break-word;
          color: rgba(255, 255, 255, 1);
          font-size: 18px;
          font-family: AlibabaPuHuiTi_3_55_Regular;
          font-weight: NaN;
          text-align: left;
          white-space: nowrap;
          line-height: 25px;
        }
        .text_56 {
          width: 71px;
          height: 25px;
          overflow-wrap: break-word;
          color: rgba(255, 255, 255, 1);
          font-size: 18px;
          font-family: AlibabaPuHuiTi_3_55_Regular;
          font-weight: NaN;
          text-align: left;
          white-space: nowrap;
          line-height: 25px;
          margin-top: 15px;
        }
      }
      .text-wrapper_20 {
        width: 36px;
        height: 65px;
        margin: 18px 0 0 129px;
        .text_57 {
          width: 29px;
          height: 25px;
          overflow-wrap: break-word;
          color: rgba(255, 255, 255, 1);
          font-size: 18px;
          font-family: AlibabaPuHuiTi_3_55_Regular;
          font-weight: NaN;
          text-align: left;
          white-space: nowrap;
          line-height: 25px;
        }
        .text_58 {
          width: 36px;
          height: 25px;
          overflow-wrap: break-word;
          color: rgba(255, 255, 255, 1);
          font-size: 18px;
          font-family: AlibabaPuHuiTi_3_55_Regular;
          font-weight: NaN;
          text-align: left;
          white-space: nowrap;
          line-height: 25px;
          margin-top: 15px;
        }
      }
    }
    .text-wrapper_21 {
      width: 1174px;
      height: 30px;
      margin: 11px 0 0 228px;
      .text_59 {
        width: 223px;
        height: 30px;
        overflow-wrap: break-word;
        color: rgb(198, 195, 195);
        font-size: 18px;
        font-family: AlibabaPuHuiTi_3_55_Regular;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 30px;
      }
      .text_60 {
        width: 71px;
        height: 25px;
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 18px;
        font-family: AlibabaPuHuiTi_3_55_Regular;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 25px;
        margin: 4px 0 0 416px;
      }
      .text_61 {
        width: 71px;
        height: 25px;
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 18px;
        font-family: AlibabaPuHuiTi_3_55_Regular;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 25px;
        margin: 4px 0 0 184px;
      }
      .text_62 {
        width: 54px;
        height: 25px;
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 18px;
        font-family: AlibabaPuHuiTi_3_55_Regular;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 25px;
        margin: 4px 0 0 179px;
      }
    }
    .text-wrapper_22 {
      width: 540px;
      // width: 517px;
      height: 25px;
      margin: 14px 0 57px 868px;
      .text_63 {
        width: 71px;
        height: 25px;
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 18px;
        font-family: AlibabaPuHuiTi_3_55_Regular;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 25px;
      }
      .text_64 {
        width: 36px;
        height: 25px;
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 18px;
        font-family: AlibabaPuHuiTi_3_55_Regular;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 25px;
      }
    }
  }
</style>
