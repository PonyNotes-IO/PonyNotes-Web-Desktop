<template>
  <div class="page flex-col">
    <div class="group_2 flex-row">
      <MobileNavBar 
        :show-logo="true"
        :show-title="true"
        :show-login="true"
        :show-register="true"
        :show-more="true"
      />
    </div>
    <div class="image-text_1 flex-row justify-between">
      <img
        class="icon_2"
        referrerpolicy="no-referrer"
        :src="(phoneBind  &&  emailBind && passwordSet )? require('./assets/img/yes_icon.png') : require('./assets/img/warn_icon.png')"
      />
       <!-- 安全提示 -->
      <div class="text-group_1 flex-col">
        <span class="text_5">账号绑定</span>
        <span class="text_6">{{ securityMessage }}</span>
      </div>
    </div>
     <!-- 账号信息列表 -->
      <!-- 手机号 -->
    <div class="group_4 flex-row">
      <img
        class="icon_4"
        referrerpolicy="no-referrer"
        :src="phoneBind? require('./assets/img/yes_icon.png'): require('./assets/img/warn_icon.png')"
      />
      <div class="text-group_3 flex-col">
        <span class="text_10">{{ userInfo.phoneBind ? (userInfo.phone || '手机号') : '手机号未绑定' }}</span>
        <span class="paragraph_1">{{ userInfo.phoneBind ? (userInfo.phone || '已绑定') : '未绑定' }}</span>
      </div>
      <button class="button_2 flex-col button_align" @click="handleChange('phone')"><span class="text_11">更改</span></button>
    </div>
    <!-- 邮箱 -->
    <div class="group_4 flex-row">
      <img
        class="icon_4"
        referrerpolicy="no-referrer"
        :src="emailBind? require('./assets/img/yes_icon.png') : require('./assets/img/warn_icon.png')"
      />
      <div class="text-group_3 flex-col">
        <span class="text_10">{{ userInfo.emailBind ? (userInfo.email || '邮箱') : '邮箱未绑定' }}</span>
        <span class="paragraph_1">
          {{ userInfo.emailBind ? (userInfo.email || '邮箱已经绑定') : '绑定后当你手机号不可用时，可通过邮箱验证更换手机号' }}
        </span>
      </div>
      <button class="button_2 flex-col" @click="handleChange('email')"><span class="text_11">更改</span></button>
    </div>

    <!-- 密码 -->
    <div class="group_4 flex-row">
      <img
        class="icon_4"
        referrerpolicy="no-referrer"
        :src="passwordSet? require('./assets/img/yes_icon.png') : require('./assets/img/warn_icon.png')"
      />
      <div class="text-group_3 flex-col">
        <span class="text_10">账户密码</span>
        <span class="paragraph_1">{{ userInfo.password ? ('已设置，可通过账户密码登录') : '未设置，请设置密码' }}</span>
      </div>
      <button class="button_2 flex-col" @click="handleChange('password')"><span class="text_11">更改</span></button>
    </div>
    <img
      class="image_2"
      referrerpolicy="no-referrer"
      src="./assets/img/SketchPnge6d918b7b08b0c49f7bbe81f62c278ef96fd1a7eef15d897c2004a8a671ec00c.png"
    />
    <MobileBottom />
  </div>
</template>
<script>
// import { getuserinfo } from '@/api/ponynote_login.js';  // 引入API


export default {
  name: 'AccountBinding',
  props: {
    // 从父组件接收的用户信息JSON字符串
    userInfoStr: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      userInfo: {
        phone: '',
        email: '',
        password: '',
        phoneBind: this.phone? true : false,
        emailBind: this.email? true : false,
        passwordSet: this.password? true : false
      },
      loading: false // 加载状态
    };
  },
  computed: {
    // 安全状态判断
    isAllSecure() {
      return this.userInfo.phoneBind && this.userInfo.emailBind && this.userInfo.passwordSet;
    },
    // 计算安全等级和提示信息
    securityLevel() {
      const { phoneBind, emailBind, passwordSet } = this.userInfo;
      
      if (!phoneBind) return 'missingPhone';
      if (!emailBind) return 'missingEmail';
      if (!passwordSet) return 'missingPassword';
      return 'complete';
    },
    securityMessage() {
      switch (this.securityLevel) {
        case 'missingPhone':
          return '你当前的账号安全系数较低，请补充手机号';
        case 'missingEmail':
          return '你当前的账号安全系数较低，请补充邮箱';
        case 'missingPassword':
          return '你当前的账号安全系数较低，请设置密码';
        case 'complete':
          return '当前账户信息完整，安全系数达标';
        default:
          return '';
      }
    }
  },
  created() {
    // 组件创建时获取用户信息
    this.fetchUserInfo();
    
    // 如果有从父组件传递的用户信息，使用它
    if (this.userInfoStr) {
      try {
        const parsedInfo = JSON.parse(this.userInfoStr);
        this.userInfo = { ...this.userInfo, ...parsedInfo };
      } catch (e) {
        console.error('解析用户信息失败:', e);
      }
    }
  },
  watch: {
    // 监听props变化并更新本地数据
    userInfoStr(newVal) {
      if (newVal) {
        try {
          const parsedInfo = JSON.parse(newVal);
          this.userInfo = { ...this.userInfo, ...parsedInfo };
        } catch (e) {
          console.error('解析用户信息失败:', e);
        }
      }
    }
  },
  methods: {
    // 获取用户信息API
    async fetchUserInfo() {
      try {
        this.loading = true;
        // 实际项目中替换为真实API
        const response = await fetch('/api/user/info');
        
        // 检查响应状态
        if (!response.ok) {
          throw new Error(`HTTP错误，状态码: ${response.status}`);
        }
        
        // 尝试解析JSON
        const contentType = response.headers.get('content-type');
        if (!contentType || !contentType.includes('application/json')) {
          throw new Error('API返回不是JSON格式');
        }
        
        const data = await response.json();
        
        if (data.success) {
          this.userInfo = {
            phoneBind: data.phoneBind || false,
            phone: data.phone || '',
            emailBind: data.emailBind || false,
            email: data.email || '',
            passwordSet: data.passwordSet || false
          };
        }
      } catch (error) {
        console.error('获取用户信息失败:', error);
        // 可以显示错误提示或使用默认数据
      } finally {
        this.loading = false;
      }
    },
    
    // 处理更改操作
    handleChange(type) {
      // 携带当前数据到目标页面
      if (type === 'phone') {
          this.$router.push('/mobile/account/change_phone');  
      }else if(type === 'email') {
          this.$router.push('/mobile/account/bind_email');  
      }else if (type === 'password') {
          this.$router.push('/mobile/account/change_password');  
      }else{
          alert('请先绑定或设置该项信息');
      }
    }
  }
};
</script>
<style scoped lang="less" src="./assets/account.response.less" />
