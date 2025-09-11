<template>
    <div class="login_popup_box flex-col"> 
        <div class="group_4 flex-col">
            <div class="box_8 flex-col">
            <div class="text-group_2 flex-col">
                <span class="paragraph_1">
                为了更好的使用服务
                <br />
                登录前请阅读并同意以下协议
                </span>
                <div class="text-wrapper_5">
                <span class="text_14">小马笔记</span>
                <span class="text_15" @click="openAgreement">《用户协议》</span>
                <span class="text_16">和</span>
                <span class="text_17" @click="openPrivacyPolicy">《隐私政策》</span>
                </div>
            </div>
            <button class="button_1 flex-col" @click="handleAgree">
                <span class="text_18">同意并继续</span>
            </button>
            <span class="text_19" @click="handleDisagree">不同意</span>
            </div>
        </div>

        <!-- 用户协议弹窗 -->
        <van-popup 
            v-model="showAgreementPopup" 
            :style="{ height: '80%' }" 
            @close="closeAgreementPopup"
        >
            <div class="popup-header">
            <span>用户协议</span>
            <van-icon name="close" @click="closeAgreementPopup" />
            </div>
            <user-agreement />
        </van-popup>

        <!-- 隐私政策弹窗 -->
        <van-popup 
            v-model="showPrivacyPopup" 
            :style="{ height: '80%' }" 
            @close="closePrivacyPopup"
        >
            <div class="popup-header">
            <span>隐私政策</span>
            <van-icon name="close" @click="closePrivacyPopup" />
            </div>
            <privacy-policy />
        </van-popup>
    </div>

</template>

<script>
import { Popup, Icon } from 'vant';
import UserAgreement from './user_agreement.vue';
import PrivacyPolicy from './privacy_policy.vue';

export default {
  name: 'LoginPopupBox',
  components: {
    vanPopup: Popup,
    vanIcon: Icon,
    UserAgreement,
    PrivacyPolicy
  },
  data() {
    return {
      showAgreementPopup: false,
      showPrivacyPopup: false
    };
  },
  methods: {
    handleAgree() {
      this.$emit('agree'); // 触发父组件agree事件
    },
    handleDisagree() {
      this.$emit('closePopup'); // 触发父组件closePopup事件
    },
    openAgreement() {
      this.showAgreementPopup = true;
    },
    closeAgreementPopup() {
      this.showAgreementPopup = false;
    },
    openPrivacyPolicy() {
      this.showPrivacyPopup = true;
    },
    closePrivacyPopup() {
      this.showPrivacyPopup = false;
    }
  }
};
</script>

<style scoped lang="css">
.group_4 {
  background-color: rgba(244, 242, 242, 0.5);
  color: rgb(255, 252, 252);
  height: 100vh;
  width: 100vw;
  position: fixed;
  left: 0;
  top: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}
.box_8 {
  background-color: rgba(35, 35, 35, 1);
  border-radius: 10px;
  padding: 40px 45px;
  width: 100%;
  max-width: 500px;
  box-sizing: border-box;
}

.text-group_2 {
  width: 340px;
  align-self: center;
}
.paragraph_1 {
  width: 286px;
  height: 60px;
  overflow-wrap: break-word;
  color: rgba(255, 255, 255, 1);
  font-size: 22px;
  font-family: PingFangSC-Medium;
  font-weight: 500;
  text-align: center;
  line-height: 30px;
  margin: 0 28px 0 26px;
}

.text-wrapper_5 {
  width: 340px;
  height: 28px;
  overflow-wrap: break-word;
  font-size: 0;
  font-weight: NaN;
  text-align: center;
  white-space: nowrap;
  line-height: 28px;
  margin-top: 20px;
}

.text_14 {
  overflow-wrap: break-word;
  color: rgba(255, 255, 255, 1);
  font-size: 20px;
  font-weight: NaN;
  text-align: left;
  white-space: nowrap;
  line-height: 28px;
}

.text_15 {
  overflow-wrap: break-word;
  color: rgba(248, 149, 117, 1);
  font-size: 20px;
  font-weight: NaN;
  text-align: left;
  white-space: nowrap;
  line-height: 28px;
}

.text_16 {
  overflow-wrap: break-word;
  color: rgba(255, 255, 255, 1);
  font-size: 20px;
  font-weight: NaN;
  text-align: left;
  white-space: nowrap;
  line-height: 28px;
}

.text_17 {
  overflow-wrap: break-word;
  color: rgba(248, 149, 117, 1);
  font-size: 20px;
  font-weight: NaN;
  text-align: left;
  white-space: nowrap;
  line-height: 28px;
}

.button_1 {
  background-color: rgba(248, 149, 117, 1);
  border-radius: 10px;
  margin-top: 30px;
  padding: 15px 158px 15px 158px;
}

.text_18 {
  overflow-wrap: break-word;
  color: rgba(255, 255, 255, 1);
  font-size: 20px;
  font-family: PingFangSC-Medium;
  font-weight: 500;
  text-align: center;
  white-space: nowrap;
  line-height: 28px;
}

.text_19 {
  overflow-wrap: break-word;
  color: rgba(153, 153, 153, 1);
  font-size: 20px;
  font-family: PingFangSC-Medium;
  font-weight: 500;
  text-align: center;
  white-space: nowrap;
  line-height: 28px;
  align-self: center;
  margin-top: 26px;
}

/* 其他样式补充 */
</style>