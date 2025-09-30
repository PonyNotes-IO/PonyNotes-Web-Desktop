<template>
  <div class="scale-root">
    <div class="page flex-col">
      <div class="block_1 flex-row">
        <div class="box_1 flex-col">
          <xm-nav-bar></xm-nav-bar>
          <div class="block_3 flex-row">
            <span class="text_3" @click="handleAccountManageClick">账号管理</span>
            <div class="group_1 flex-col"></div>
            <div class="group_2 flex-col">
              <span class="text_4">账号绑定</span>
              <div class="box_2 flex-row justify-between">
                <img
                  class="label_1"
                  referrerpolicy="no-referrer"
                  :src="(phoneBind && emailBind && passwordSet) ? require('./assets/img/yes_icon.png') : require('./assets/img/warn_icon.png')"
                />
                <span class="text_5">{{ securityMessage }}</span>
              </div>
              <div class="box_3 flex-row">
                <img
                  class="label_2"
                  referrerpolicy="no-referrer"
                  :src="phoneBind ? require('./assets/img/yes_icon.png') : require('./assets/img/warn_icon.png')"
                />
                <div class="text-group_1 flex-col justify-between">
                  <span class="text_6">{{ phone ? (phone || '手机号') : '手机号未绑定' }}</span>
                  <span class="text_7">{{ phone ? phone : '未绑定' }}</span>
                </div>
                <div class="text-wrapper_3 flex-col">
                  <span class="text_8" @click="showChangePhoneModal = true">更改</span>
                </div>
              </div>
              <div class="box_4 flex-row">
                <img
                  class="label_3"
                  referrerpolicy="no-referrer"
                  :src="email ? require('./assets/img/yes_icon.png') : require('./assets/img/warn_icon.png')"
                />
                <div class="text-wrapper_4 flex-col justify-between">
                  <span class="text_9">{{ email ? (email || '邮箱') : '邮箱未绑定' }}</span>
                  <span class="text_10">{{ email ? '邮箱已经绑定' : '未绑定，绑定后当你手机号不可用时，可通过邮箱验证更换手机号' }}</span>
                </div>
                <div class="text-wrapper_5 flex-col">
                  <span class="text_11" @click="showBindEmailModal = true">更改</span>
                </div>
              </div>
              <div class="box_5 flex-row">
                <img
                  class="label_4"
                  referrerpolicy="no-referrer"
                  :src="passwordSet ? require('./assets/img/yes_icon.png') : require('./assets/img/warn_icon.png')"
                />
                <div class="text-group_2 flex-col justify-between">
                  <span class="text_12">账户密码</span>
                  <span class="text_13">{{ passwordSet ? '已设置，可通过账户密码登录' : '未设置，请设置密码' }}</span>
                </div>
                <div class="text-wrapper_6 flex-col">
                  <span class="text_14" @click="showChangePasswordModal = true">更改</span>
                </div>
              </div>
            </div>
          
          
          
          
            <!-- 更改手机号弹窗 -->
            <ChangePhoneModal 
              v-if="showChangePhoneModal"
              :userName="userName"
              :account="account"
              @close="showChangePhoneModal = false"
              @success="handlePhoneChangeSuccess"
              @show-notification="showNotification"
            />
            
            <!-- 绑定邮箱弹窗 -->
            <BindEmailModal 
              v-if="showBindEmailModal"
              :userName="userName"
              :phoneNumber="phone"
              @close="showBindEmailModal = false"
              @success="handleEmailBindSuccess"
              @show-notification="showNotification"
            />
            
            <!-- 更改密码弹窗 -->
            <ChangePasswordModal 
              v-if="showChangePasswordModal"
              :userName="userName"
              :account="account"
              @close="showChangePasswordModal = false"
              @success="handlePasswordChangeSuccess"
              @show-notification="showNotification"
            />
          
          </div>
        </div>
        <div class="box_6 flex-col">
          <index-bottom-pc></index-bottom-pc> <!-- 底部 -->
        </div>
      </div>
    </div>
  </div>

</template>
<script>
{/* <xm-nav-bar></xm-nav-bar> */}
import XmNavBar from '@/components/xm-nav-bar.vue';
import IndexBottomPC from '@/components/index-bottom-pc.vue';
import ChangePhoneModal from './ChangePhoneModal.vue';
import BindEmailModal from './BindEmailModal.vue';
import ChangePasswordModal from './ChangePasswordModal.vue';
import { getuserinfo } from '../../api/ponynote_login';
// 常量定义集中管理
const SECURITY_MESSAGES = {
  missingPhone: '你当前的账号安全系数较低，请补充手机号',
  missingEmail: '你当前的账号安全系数较低，请补充邮箱',
  missingPassword: '你当前的账号安全系数较低，请设置密码',
  complete: '当前账户信息完整，安全系数达标'
};
export default {
  components: {
      'xm-nav-bar':XmNavBar,
      'index-bottom-pc': IndexBottomPC,
      ChangePhoneModal,
      BindEmailModal,
      ChangePasswordModal
  },
  data() {
    return {
      // 弹窗控制状态
      showChangePhoneModal: false,
      showBindEmailModal: false,
      showChangePasswordModal: false,

      // 路由获取数据
      accountType: '',
      account: '',
      phone: '',
      email: '',
      userName: '',
      nickname: '',
      newAccount: false,
      firstEmailLogin: false,
      userId: '',
      phoneBind: false,
      emailBind: false,
      passwordSet: false,
    };
  },
  computed: {
    // 安全状态判断
    isAllSecure() {
      return this.phoneBind && this.emailBind && this.passwordSet;
    },
    // 计算安全等级
    securityLevel() {
      if (!this.phoneBind) return 'missingPhone';
      if (!this.emailBind) return 'missingEmail';
      if (!this.passwordSet) return 'missingPassword';
      return 'complete';
    },
    // 安全提示信息（使用对象映射替代switch）
    securityMessage() {
      return SECURITY_MESSAGES[this.securityLevel] || '';
    }
  },
  mounted() {
      this.handleResize();
      window.addEventListener('resize', this.handleResize);
      this.initIntersectionObserver(); // 调用方法
      this.fetchUserInfo(); // 页面加载时立即调用接口获取用户信息
  },
  beforeDestroy() {
      window.removeEventListener('resize', this.handleResize);
      if (this.observer) this.observer.disconnect();
  },
  methods: {
      // 新增：点击“账号管理”时刷新数据
    handleAccountManageClick() {
      // 调用获取用户信息的方法（刷新数据）
      this.fetchUserInfo(); 

    },
    handleResize() {
    // 以1920为设计稿宽度
    const baseWidth = 1920;
    const scale = window.innerWidth / baseWidth;
    document.querySelector('.scale-root').style.transform = `scale(${scale})`;
    document.querySelector('.scale-root').style.transformOrigin = 'top left';
    // 可选：设置根容器宽高，避免溢出
    document.querySelector('.scale-root').style.width = baseWidth + 'px';
    document.querySelector('.scale-root').style.height = '8449px';
    },
    initIntersectionObserver() {
        const items = document.querySelectorAll('.list-items_1');
        const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
            entry.target.style.opacity = '1';
            observer.unobserve(entry.target);
            }
        });
        }, { threshold: 0.5 });
        items.forEach(item => observer.observe(item));
    },
    /**
     * 获取用户信息API调用
     */
    async fetchUserInfo() {
      // 无账号信息时不发起请求
      this.account = this.$route.query.account || '';
      this.accountType = this.$route.query.accountType || '';
      // 2. 正确前置判断：参数缺失时不发请求，同时提示用户
      if (!this.account || !this.accountType) {
        this.showNotification('账号信息缺失，无法获取用户数据', 'error');
        return;
      }
      try {
        this.loading = true;
        
        // 确定账号和账号类型
        const account = this.account;
        const accountType = this.accountType;
        
        const response = await getuserinfo({ account, accountType });
        
        // 验证请求状态
        if (response.data.code !== 200 || response.status !== 200) {
          this.$toast.error('用户信息获取失败');
          return;
        }
        
        // 处理响应数据（兼容字符串和对象）
        let userInfo = response.data.data;
        if (typeof userInfo === 'string') {
          try {
            userInfo = JSON.parse(userInfo);
          } catch (e) {
            console.error('用户信息解析失败:', e);
            this.$toast.error('数据解析错误');
            return;
          }
        }
        
        // 更新用户信息
        this.phone = userInfo.phonenumber || '';
        this.email = userInfo.email || '';
        this.userName = userInfo.userName || '';
        this.nickname = userInfo.nickName || '';
        this.sex = userInfo.sex || '';
        // 更新绑定状态
        this.phoneBind = !!this.phone;
        this.emailBind = !!this.email;
        this.passwordSet = !!userInfo.password;
        
      } catch (error) {
        console.error('获取用户信息异常:', error);
        this.$toast.error('获取用户信息失败，请稍后重试');
      } finally {
        this.loading = false;
      }
    },
    // 手机号更改成功处理
    handlePhoneChangeSuccess(newPhone) {
      this.currentPhone = newPhone;
      this.phoneBind = !!newPhone; // 更新绑定状态
      this.fetchUserInfo(); 
      this.showNotification('手机号已成功更新', 'success');
    },
    // 邮箱绑定/更改成功处理
    handleEmailBindSuccess(newEmail) {
      this.email = newEmail;
      this.emailBind = !!newEmail;
      this.fetchUserInfo();
      this.showNotification(`邮箱已成功${this.email ? '更新' : '绑定'}`, 'success');
    },
    // 密码更改成功处理
    handlePasswordChangeSuccess() {
      this.passwordSet = true;
      this.showNotification('密码已成功修改', 'success');
    },
    // 统一通知显示方法
    showNotification(message, type) {
      // 这里可以替换为实际的通知组件调用
      alert(`[${type === 'error' ? '错误' : '成功'}] ${message}`);
    }
  }
};
</script>
<style scoped lang="less" src="./assets/account.less" />
<style scoped>
/* 全局覆盖所有文本元素（p、h1~h6、span 等） */
p, h1, h2, h3, h4, h5, h6, span, a {
    cursor: pointer !important; /* 强制覆盖默认样式 */
}
.scale-root {
  width: 100%; 
  height: 8449px;
  overflow: hidden;
}
.group_13 {
  position: relative;
  bottom: 0;
  width:  100%;
  display: flex;            
  justify-content: center;  /* 水平居中 */
  align-items: center;      /* 垂直居中（若导航栏高度>内容高度时生效） */

}
.index-bottom-pc{
    box-shadow: none;
    background-color: #000000;
    height: 312px;
    width: 1680px;
    margin-top: 80px;
}
</style>