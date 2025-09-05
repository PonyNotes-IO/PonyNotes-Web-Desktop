<template>
  <div class="page flex-col">
    <div class="group_2 flex-row">
      <img
        class="thumbnail_3"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPng50df99f41c5b411f42c7412ae774fd2bb2488d446ca2d01a637b7c54af62d535.png"
      />
      <span class="text_2">小马笔记</span>
      <span class="text_3">登录</span>
      <span class="text_4">注册</span>
      <img
        class="icon_1"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPng0fa58b251acd9fdd3cf775e74f4ab4baf08feb1e112b74c5ac39d12508ef9ff7.png"
      />
    </div>
    <div class="image-text_1 flex-row justify-between">
      <img
        class="icon_2"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPng40a35c524a585af118d4e06dc265bb2ba0a5e994484a1eea31bc33df697bce64.png"
      />
      <div class="text-group_1 flex-col">
        <span class="text_5">账号绑定</span>
        <span class="text_6">你当前的账号安全系数较低，请补充邮箱</span>
      </div>
    </div>
    <div class="group_3 flex-row">
      <img
        class="icon_3"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPng9036f48540277503de9d53b0f120ddcbcae174d98e4b45e03a2893f6be118d7a.png"
      />
      <div class="text-group_2 flex-col">
        <span class="text_7">手机号</span>
        <span class="text_8">196****1345</span>
      </div>
      <button class="button_1 flex-col" @click="onClick_1"><span class="text_9">更改</span></button>
    </div>
    <div class="group_4 flex-row">
      <img
        class="icon_4"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPng40a35c524a585af118d4e06dc265bb2ba0a5e994484a1eea31bc33df697bce64.png"
      />
      <div class="text-group_3 flex-col">
        <span class="text_10">邮箱未绑定</span>
        <span class="paragraph_1">
          绑定后当你手机号不可用时，可通
          <br />
          过邮箱验证更换手机号
        </span>
      </div>
      <button class="button_2 flex-col" @click="onClick_2"><span class="text_11">更改</span></button>
    </div>
    <div class="group_5 flex-row">
      <img
        class="icon_5"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPng9036f48540277503de9d53b0f120ddcbcae174d98e4b45e03a2893f6be118d7a.png"
      />
      <div class="text-group_4 flex-col">
        <span class="text_12">账户密码</span>
        <span class="text_13">已设置，可通过账户密码登录</span>
      </div>
      <button class="button_3 flex-col" @click="onClick_3"><span class="text_14">更改</span></button>
    </div>
    <img
      class="image_2"
      referrerpolicy="no-referrer"
      src="./assets/img/SketchPnge6d918b7b08b0c49f7bbe81f62c278ef96fd1a7eef15d897c2004a8a671ec00c.png"
    />
    <div class="text-wrapper_1 flex-row justify-between">
      <span class="text_15">产品</span>
      <span class="text_16">资源</span>
      <span class="text_17">联系我们</span>
    </div>
    <div class="text-wrapper_2 flex-row">
      <span class="text_18">桌面端</span>
      <span class="text_19">使用教程</span>
      <span class="text_20">B站</span>
    </div>
    <div class="text-wrapper_3 flex-row">
      <span class="text_21">手机端</span>
      <span class="text_22">快速上手</span>
      <span class="text_23">抖音</span>
    </div>
    <div class="text-wrapper_4 flex-row justify-between">
      <span class="text_24">隐私协议</span>
      <span class="text_25">常见问题</span>
      <span class="text_26">小红书</span>
    </div>
    <span class="text_27">邮箱</span>
    <img
      class="image_3"
      referrerpolicy="no-referrer"
      src="./assets/img/SketchPnge6d918b7b08b0c49f7bbe81f62c278ef96fd1a7eef15d897c2004a8a671ec00c.png"
    />
    <div class="text-wrapper_5">
      <span class="text_28">小马笔记</span>
      <span class="text_29">&nbsp;|</span>
      <span class="text_30">&nbsp;京ICP备2025131868号</span>
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
  methods: {
    onClick_1() {
      alert(1);
    },
    onClick_2() {
      alert(1);
    },
    onClick_3() {
      alert(1);
    }
  }
};
</script>
<style scoped lang="less" src="./assets/index.less" />