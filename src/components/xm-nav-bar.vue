<template>
  <van-sticky>
    <div class="xm-index-nav-bar">
      <div class="content-bar">
        <div class="box_23 flex-row group_1">
          <img @click="navTo('/')" class="label_8" referrerpolicy="no-referrer"
            src="./img/logo.png" />
          <span class="text_65" @click="navTo('/')">小马笔记</span>
          <span class="text_66"></span>
          <span class="text_67" @click="navTo('/price')">价格</span>
          <span class="text_68" @click="navTo('/download')">下载</span>

          <div class="text-wrapper_1 flex-col"><span class="text_1">登录</span></div>
          <div class="text-wrapper_2 flex-col"><span class="text_2">注册</span></div>
        </div>
      </div>

    </div>
  </van-sticky>

</template>
<script>
export default {
  methods: {
    navTo(url) {
      this.$router.push(url);
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
  border-bottom: 1px solid #999;

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
      margin-left: 677px;
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
      margin-right: 200px;
      margin-top: 4px;
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
      width: 70px;
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
  }
}
</style>
