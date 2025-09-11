<template>
  <div class="page flex-col">

    <div class="image-wrapper_3 flex-row">
      <img
        class="image_3"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPngc83012784b4d01cca040c34c120c4e75c61902c8d48246217777999d0463a686.png"
      />
    </div>

    <div class="text-wrapper_3 flex-row">
      <span class="text_4">欢迎使用小马笔记</span></div>
    <div class="box_2 flex-row">
      <div class="input_1 flex-col Input">
        <van-field
          class="text_7 InputArea"
          placeholder="输入邮箱或者手机号"
          v-model="InputArea_text_7"
          type="number"
        ></van-field>
      </div>
    </div>
    <div class="box_3 flex-row">
      <button class="button_1 flex-col" @click="onClick_1"><span class="text_8">登录/注册</span></button>
    </div>
    <div class="box_4 flex-row">
      <div class="image-text_2 flex-row justify-between">
        <van-checkbox
          name="Checkbox_checkbox_1"
          v-model="Checkbox_checkbox_1"
          icon-size="20"
          checked-color=""
          class="checkbox_1 Checkbox"
        ></van-checkbox>
        <div class="text-group_1 flex-row">
          <span class="text_9">我已阅读并同意</span> 
          <span class="text_10"  @click="showAgreement">《用户协议》</span><span class="text_11">与</span>
          <span class="text_12"  @click="showPrivacy">《隐私政策》</span>
        </div>
      </div>
    </div>
    <div class="box_5 flex-row">
      <img
        class="image_6"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPngea302f0b17509638bf635ff6e8de579a5b8fe081c2784376be35b4033aecb704.png"
      />
      <span class="text_13">其他登录方式</span>
      <img
        class="image_7"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPng6c680d45766d384a7d4b1cabac31ce18cfc2adc9f19caa4b2e38355a060c18b1.png"
      />
    </div>
    <div class="box_6 flex-row">
      <div class="image-wrapper_4 flex-row">
        <img
          class="icon_1"
          referrerpolicy="no-referrer"
          :src="item.lanhuimage0"
          v-for="(item, index) in loopData0"
          :key="index"
          @click="openThirdParty(index)"
        />
      </div>
    </div>

    <!-- 用户协议弹窗 -->
    <van-popup v-model="showAgreementPopup" position="bottom" :style="{ height: '80%' }">
      <user-agreement />
    </van-popup>

    <!-- 隐私政策弹窗 -->
    <van-popup v-model="showPrivacyPopup" position="bottom" :style="{ height: '80%' }">
      <privacy-policy />
    </van-popup>

    <!-- 第三方扫码登录弹窗 -->
    <van-popup v-model="showQrPopup" position="center" :style="{ width: '320px', padding: '16px' }">
      <div class="flex-col" style="align-items:center;">
        <div style="font-size:16px;margin-bottom:8px;">{{ qrPlatformName }}扫码登录</div>
        <img :src="qrImageUrl" alt="qr" style="width:260px;height:260px;" />
      </div>
    </van-popup>
  </div>
</template>
<script>
import UserAgreement from './user_agreement.vue'
import PrivacyPolicy from './privacy_policy.vue'

export default {
  name: 'login',
  data() {
    return {
      InputArea_text_7: '',
      Checkbox_checkbox_1: false,
      showAgreementPopup: false,
      showPrivacyPopup: false,
      showQrPopup: false,
      qrPlatformName: '',
      qrImageUrl: '',
      loopData0: [
        {
          lanhuimage0:
            'https://lanhu-oss-proxy.lanhuapp.com/SketchPng5cc925f16ebbe5c7863f2fdb603969442e27dded944355099a7bd0f040a9ad6f'
        },
        {
          lanhuimage0:
            'https://lanhu-oss-proxy.lanhuapp.com/SketchPng25089a1ed73bd0167722826e1e709f02fff64e82ed9f5dc8f2cc597116f57bd5'
        },
        {
          lanhuimage0:
            'https://lanhu-oss-proxy.lanhuapp.com/SketchPng567c62dfd9b1dd45bce50a42ca513e529a7150c3a1d9f989869889cc73bcc032'
        }
      ],
      constants: {}
    };
  },
  components: {
    'user-agreement': UserAgreement,
    'privacy-policy': PrivacyPolicy
  },
  methods: {
    onClick_1() {
      if (!this.Checkbox_checkbox_1) {
        alert('请先勾选“我已阅读并同意”');
        return;
      }
      alert('登录/注册逻辑');
    },
    showAgreement() {
      this.showAgreementPopup = true;
    },
    showPrivacy() {
      this.showPrivacyPopup = true;
    },
    openThirdParty(index) {
      // 0: 微信, 1: 抖音, 2: QQ
      if (index === 0) {
        this.qrPlatformName = '微信';
        this.qrImageUrl = 'https://api.qrserver.com/v1/create-qr-code/?size=260x260&data=WECHAT_LOGIN_PLACEHOLDER';
      } else if (index === 1) {
        this.qrPlatformName = '抖音';
        this.qrImageUrl = 'https://api.qrserver.com/v1/create-qr-code/?size=260x260&data=DOUYIN_LOGIN_PLACEHOLDER';
      } else if (index === 2) {
        this.qrPlatformName = 'QQ';
        this.qrImageUrl = 'https://api.qrserver.com/v1/create-qr-code/?size=260x260&data=QQ_LOGIN_PLACEHOLDER';
      } else {
        this.qrPlatformName = '第三方';
        this.qrImageUrl = 'https://api.qrserver.com/v1/create-qr-code/?size=260x260&data=THIRD_PARTY_LOGIN';
      }
      this.showQrPopup = true;
    }
  }
};
</script>
<style scoped lang="css" src="./assets/login.css" />