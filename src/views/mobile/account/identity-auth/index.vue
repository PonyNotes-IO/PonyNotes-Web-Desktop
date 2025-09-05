<template>
  <div class="page flex-col">
    <div class="box_2 flex-row">
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
    <div class="text-group_1 flex-col">
      <span class="text_5">身份验证</span>
      <span class="text_6">为了你的账户安全，请先验证身份</span>
    </div>
    <div class="text-wrapper_1 flex-row justify-between">
      <span class="text_7">使用手机185******70验证</span>
      <span class="text_8">切换验证</span>
    </div>
    <div class="box_3 flex-row justify-between">
      <span class="text_9">验证通过</span>
      <img
        class="thumbnail_4"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPng00f8d3f3717ba3b0843bc30123fc429b74b26954beedc3c643bcad90c0457a10.png"
      />
    </div>
    <div class="box_4 flex-row justify-between">
      <div class="input_1 flex-col"></div>
      <button class="button_1 flex-col" @click="onClick_1"><span class="text_10">重新获取</span></button>
    </div>
    <span class="text_11">已发送短信验证码到绑定手机</span>
    <div class="box_5 flex-row justify-between">
      <button class="button_2 flex-col" @click="onClick_2"><span class="text_12">取消</span></button>
      <button class="button_3 flex-col" @click="onClick_3"><span class="text_13">完成</span></button>
    </div>
    <img
      class="image_2"
      referrerpolicy="no-referrer"
      src="./assets/img/SketchPnge6d918b7b08b0c49f7bbe81f62c278ef96fd1a7eef15d897c2004a8a671ec00c.png"
    />
    <div class="text-wrapper_2 flex-row justify-between">
      <span class="text_14">产品</span>
      <span class="text_15">资源</span>
      <span class="text_16">联系我们</span>
    </div>
    <div class="text-wrapper_3 flex-row">
      <span class="text_17">桌面端</span>
      <span class="text_18">使用教程</span>
      <span class="text_19">B站</span>
    </div>
    <div class="text-wrapper_4 flex-row">
      <span class="text_20">手机端</span>
      <span class="text_21">快速上手</span>
      <span class="text_22">抖音</span>
    </div>
    <div class="text-wrapper_5 flex-row justify-between">
      <span class="text_23">隐私协议</span>
      <span class="text_24">常见问题</span>
      <span class="text_25">小红书</span>
    </div>
    <span class="text_26">邮箱</span>
    <img
      class="image_3"
      referrerpolicy="no-referrer"
      src="./assets/img/SketchPnge6d918b7b08b0c49f7bbe81f62c278ef96fd1a7eef15d897c2004a8a671ec00c.png"
    />
    <div class="text-wrapper_6">
      <span class="text_27">小马笔记</span>
      <span class="text_28">&nbsp;|</span>
      <span class="text_29">&nbsp;京ICP备2025131868号</span>
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