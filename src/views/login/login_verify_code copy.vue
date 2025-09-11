<template>
  <div class="page flex-col">
    <div class="image-wrapper_1 flex-row">
      <img
        class="image_3"
        referrerpolicy="no-referrer"
        src="./assets/img/reback.png"
      />
    </div>
    <div class="section_2 flex-row">
      <div class="text-group_1 flex-col">
        <span class="text_4">请输入验证码</span>
        <div class="text-wrapper_1">
          <span class="text_5">6位验证码已发送至</span>
          <span class="text_6"></span>
          <span class="text_7">+86&nbsp;133123455678</span>
          <span class="text_8">，有效期15分钟。</span>
        </div>
      </div>
    </div>
    <div class="section_3 flex-row justify-between">
      <span class="text-group_2">收不到验证码？</span>
      <div class="image-text_1 flex-row">
        <div class="image-text_2 flex-col">
          <div class="box_1 flex-col"></div>
          <div class="text-wrapper_2">
            <span class="text_9">56</span>
            <span class="text_10">秒后重新获取验证码</span>
          </div>
        </div>
        <div class="block_1 flex-col"></div>
      </div>
      <div class="block_2 flex-row justify-between">
        <div class="image-text_3 flex-col"><span class="text_11">5</span></div>
        <div class="text-wrapper_3 flex-col"><span class="text_12">5</span></div>
        <div class="input_1 flex-col"></div>
        <div class="block_3 flex-col"></div>
        <div class="box_1 flex-col"></div>
        <div class="block_1 flex-col"></div>
      </div>
    </div>
    <div class="text-wrapper_4 flex-row"><span class="text_13">验证码错误，请重新输入</span></div>
    <div class="section_4 flex-row">
      <button class="button_1 flex-col" @click="onClick_1"><span class="text_14">下一步</span></button>
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
    }
  }
};
</script>
<style scoped lang="css" src="./assets/login_verify_code.css" />