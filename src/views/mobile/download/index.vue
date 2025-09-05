<template>
  <div class="page flex-col">
    <div class="box_1 flex-col">
      <van-nav-bar class="nav-bar_1 NavBar">
        <template #left>
          <div class="box_2 flex-col">
            <div class="group_1 flex-row justify-between">
              <img
                class="icon_1"
                referrerpolicy="no-referrer"
                src="./assets/img/SketchPng50df99f41c5b411f42c7412ae774fd2bb2488d446ca2d01a637b7c54af62d535.png"
              />
              <span class="text_2">小马笔记</span>
            </div>
          </div>
        </template>
        <template #title></template>
        <template #right>
          <div class="box_3 flex-col">
            <div class="group_2 flex-row justify-between">
              <span class="text_3" @click="handleLoginClick">登录</span>
              <span class="text_4" @click="handleRegisterClick">注册</span>
              <img
                class="thumbnail_3"
                referrerpolicy="no-referrer"
                src="./assets/img/SketchPng0fa58b251acd9fdd3cf775e74f4ab4baf08feb1e112b74c5ac39d12508ef9ff7.png"
              />
            </div>
          </div>
        </template>
      </van-nav-bar>
      <div class="text-group_1 flex-col">
        <div class="text-wrapper_1">
          <span class="text_5">AI工具</span>
          <span class="text_6">赋能内容创作</span>
        </div>
        <span class="text_7">简化工作流程，做事效率倍增</span>
      </div>
    </div>
    <div class="box_4 flex-col"></div>
    <div class="box_5 flex-col">
      <span class="text_8">桌面端</span>
      <div class="group_3 flex-col">
        <div class="image-text_1 flex-col">
          <img
            class="label_1"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng98fe94989f78c35872de1410f2201ead031998f31f9330d87968165e0fa19941.png"
          />
          <div class="text-group_2 flex-col">
            <span class="text_9">小马笔记window</span>
            <span class="text_10">仅支持64位系统</span>
          </div>
        </div>
        <div class="image-text_2 flex-row justify-between">
          <img
            class="icon_2"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng8e5681454a76727521be356c840fb320386ab5bb368d91aa9f25959c990b0aad.png"
          />
          <span class="text-group_3">立即下载</span>
        </div>
      </div>
      <div class="group_4 flex-col">
        <div class="image-text_3 flex-col">
          <img
            class="label_2"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng3c4acb540fa3f0ce82a8df484cc2ddf27d55e902e338cd137785bad2e12f9920.png"
          />
          <div class="text-group_4 flex-col">
            <span class="text_11">小马笔记window</span>
            <span class="text_12">仅支持64位系统</span>
          </div>
        </div>
        <div class="image-text_4 flex-row justify-between">
          <img
            class="icon_3"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng8e5681454a76727521be356c840fb320386ab5bb368d91aa9f25959c990b0aad.png"
          />
          <span class="text-group_5">立即下载</span>
        </div>
      </div>
    </div>
    <div class="box_6 flex-col">
      <span class="text_13">移动端</span>
      <div class="section_1 flex-col">
        <div class="image-text_5 flex-col">
          <img
            class="icon_4"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng2f3554e3079a3b440d4efe79ed6d2e862bebce42cd1e118718c27d94adeae1c1.png"
          />
          <div class="text-group_6 flex-col">
            <span class="text_14">小马笔记安卓端</span>
            <span class="text_15">仅支持64位系统</span>
          </div>
        </div>
        <div class="image-text_6 flex-row justify-between">
          <img
            class="icon_5"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng8e5681454a76727521be356c840fb320386ab5bb368d91aa9f25959c990b0aad.png"
          />
          <span class="text-group_7">立即下载</span>
        </div>
      </div>
      <div class="section_2 flex-col">
        <div class="image-text_7 flex-col">
          <img
            class="label_3"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPngba0e3c38ebc4ed5b149338e620cb76c65d83d5ae2f6adc1d00aab1808b2e3209.png"
          />
          <div class="text-group_8 flex-col">
            <span class="text_16">小马笔记ios版</span>
            <span class="text_17">仅支持64位系统</span>
          </div>
        </div>
        <div class="image-text_8 flex-row justify-between">
          <img
            class="icon_6"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng8e5681454a76727521be356c840fb320386ab5bb368d91aa9f25959c990b0aad.png"
          />
          <span class="text-group_9">立即下载</span>
        </div>
      </div>
    </div>
    <div class="box_7 flex-col">
      <span class="text_18">桌面端</span>
      <div class="box_8 flex-col">
        <div class="image-text_9 flex-col">
          <img
            class="label_4"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng2f3554e3079a3b440d4efe79ed6d2e862bebce42cd1e118718c27d94adeae1c1.png"
          />
          <div class="text-group_10 flex-col">
            <span class="text_19">小马笔记安卓端</span>
            <span class="text_20">仅支持64位系统</span>
          </div>
        </div>
        <div class="image-text_10 flex-row justify-between">
          <img
            class="icon_7"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng8e5681454a76727521be356c840fb320386ab5bb368d91aa9f25959c990b0aad.png"
          />
          <span class="text-group_11">立即下载</span>
        </div>
      </div>
      <div class="box_9 flex-col">
        <div class="image-text_11 flex-col">
          <img
            class="label_5"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng681667a057d2b1fd4a1a84e80d50f65f08df3e8a5e9ac5193373432974e06720.png"
          />
          <div class="text-group_12 flex-col">
            <span class="text_21">小马笔记ios版</span>
            <span class="text_22">仅支持64位系统</span>
          </div>
        </div>
        <div class="image-text_12 flex-row justify-between">
          <img
            class="icon_8"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng8e5681454a76727521be356c840fb320386ab5bb368d91aa9f25959c990b0aad.png"
          />
          <span class="text-group_13">立即下载</span>
        </div>
      </div>
      <img
        class="image_2"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPnge6d918b7b08b0c49f7bbe81f62c278ef96fd1a7eef15d897c2004a8a671ec00c.png"
      />
      <div class="text-wrapper_2 flex-row justify-between">
        <span class="text_23">产品</span>
        <span class="text_24">资源</span>
        <span class="text_25">联系我们</span>
      </div>
      <div class="text-wrapper_3 flex-row">
        <span class="text_26">桌面端</span>
        <span class="text_27">使用教程</span>
        <span class="text_28">B站</span>
      </div>
      <div class="text-wrapper_4 flex-row">
        <span class="text_29">手机端</span>
        <span class="text_30">快速上手</span>
        <span class="text_31">抖音</span>
      </div>
      <div class="text-wrapper_5 flex-row justify-between">
        <span class="text_32">隐私协议</span>
        <span class="text_33">常见问题</span>
        <span class="text_34">小红书</span>
      </div>
      <span class="text_35">邮箱</span>
      <img
        class="image_3"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPnge6d918b7b08b0c49f7bbe81f62c278ef96fd1a7eef15d897c2004a8a671ec00c.png"
      />
      <div class="text-wrapper_6">
        <span class="text_36">小马笔记</span>
        <span class="text_37">&nbsp;|</span>
        <span class="text_38">&nbsp;京ICP备2025131868号</span>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      constants: {}
    };
  },
  methods: {}
};
</script>
<style scoped lang="less" src="./assets/index.less" />