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
    <div class="text-group_1 flex-col">
      <span class="text_5">绑定邮箱</span>
      <span class="text_6">请绑定您的邮箱</span>
    </div>
    <div class="input_1 flex-row">
      <span class="text_7">+86</span>
      <img
        class="icon_2"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPngdf83d4a3b10d5cfa0d03436d13aeb4a243747e4e62c0b3989a2c1e22d1c32f74.png"
      />
      <input class="text_8" placeholder="请输入新的手机号" style="width:96px;height:17px;" />
    </div>
    <div class="group_3 flex-row justify-between">
      <div class="input_2 flex-col"></div>
      <button class="button_1 flex-col" @click="onClick_1"><span class="text_9">获取验证码</span></button>
    </div>
    <span class="text_10">已发送短信验证码到绑定手机</span>
    <div class="group_4 flex-row justify-between">
      <button class="button_2 flex-col" @click="onClick_2"><span class="text_11">取消</span></button>
      <button class="button_3 flex-col" @click="onClick_3"><span class="text_12">完成</span></button>
    </div>
    <img
      class="image_2"
      referrerpolicy="no-referrer"
      src="./assets/img/SketchPnge6d918b7b08b0c49f7bbe81f62c278ef96fd1a7eef15d897c2004a8a671ec00c.png"
    />
    <div class="text-wrapper_1 flex-row justify-between">
      <span class="text_13">产品</span>
      <span class="text_14">资源</span>
      <span class="text_15">联系我们</span>
    </div>
    <div class="text-wrapper_2 flex-row">
      <span class="text_16">桌面端</span>
      <span class="text_17">使用教程</span>
      <span class="text_18">B站</span>
    </div>
    <div class="text-wrapper_3 flex-row">
      <span class="text_19">手机端</span>
      <span class="text_20">快速上手</span>
      <span class="text_21">抖音</span>
    </div>
    <div class="text-wrapper_4 flex-row justify-between">
      <span class="text_22">隐私协议</span>
      <span class="text_23">常见问题</span>
      <span class="text_24">小红书</span>
    </div>
    <span class="text_25">邮箱</span>
    <img
      class="image_3"
      referrerpolicy="no-referrer"
      src="./assets/img/SketchPnge6d918b7b08b0c49f7bbe81f62c278ef96fd1a7eef15d897c2004a8a671ec00c.png"
    />
    <div class="text-wrapper_5">
      <span class="text_26">小马笔记</span>
      <span class="text_27">&nbsp;|</span>
      <span class="text_28">&nbsp;京ICP备2025131868号</span>
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