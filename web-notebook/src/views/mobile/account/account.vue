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
        :src="(phoneBind && emailBind && passwordSet) ? require('./assets/img/yes_icon.png') : require('./assets/img/warn_icon.png')"
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
        :src="phoneBind ? require('./assets/img/yes_icon.png') : require('./assets/img/warn_icon.png')"
      />
      <div class="text-group_3 flex-col">
        <span class="text_10">{{ phone ? (phone || '手机号') : '手机号未绑定' }}</span>
        <span class="paragraph_1">{{ phone ? '已绑定' : '未绑定' }}</span>
      </div>
      <button class="button_2 flex-col" @click="handleChange('phone')"><span class="text_11">更改</span></button>
    </div>
    <!-- 邮箱 -->
    <div class="group_4 flex-row">
      <img
        class="icon_4"
        referrerpolicy="no-referrer"
        :src="email ? require('./assets/img/yes_icon.png') : require('./assets/img/warn_icon.png')"
      />
      <div class="text-group_3 flex-col">
        <span class="text_10">{{ email ? (email || '邮箱') : '邮箱未绑定' }}</span>
        <span class="paragraph_1">
          {{ email ? '邮箱已经绑定' : '未绑定，绑定后当你手机号不可用时，可通过邮箱验证更换手机号' }}
        </span>
      </div>
      <button class="button_2 flex-col" @click="handleChange('email')"><span class="text_11">更改</span></button>
    </div>

    <!-- 密码 -->
    <div class="group_4 flex-row">
      <img
        class="icon_4"
        referrerpolicy="no-referrer"
        :src="passwordSet ? require('./assets/img/yes_icon.png') : require('./assets/img/warn_icon.png')"
      />
      <div class="text-group_3 flex-col">
        <span class="text_10">账户密码</span>
        <span class="paragraph_1">{{ passwordSet ? '已设置，可通过账户密码登录' : '未设置，请设置密码' }}</span>
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
import { getuserinfo } from '../../../api/ponynote_login.js'; 

// 常量定义集中管理
const SECURITY_MESSAGES = {
  missingPhone: '你当前的账号安全系数较低，请补充手机号',
  missingEmail: '你当前的账号安全系数较低，请补充邮箱',
  missingPassword: '你当前的账号安全系数较低，请设置密码',
  complete: '当前账户信息完整，安全系数达标'
};

const ROUTE_MAP = {
  phone: '/mobile/account/change_phone',
  email: '/mobile/account/bind_email',
  password: '/mobile/account/change_password',
};


export default {
  name: 'AccountBinding',
  data() {
    return {
      // 从路由参数获取用户信息
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
      loading: false // 加载状态
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
  created() {
    this.accountType = this.$route.query.accountType || '';
    this.account = this.$route.query.account || '';
    this.phone = this.$route.query.phone || '';
    this.email = this.$route.query.email || '';
    this.userId = this.$route.query.userId || '';
    this.userName = this.$route.query.userName || '';
    this.emailBind = this.parseBoolean(this.$route.query.emailBind);
    this.phoneBind = this.parseBoolean(this.$route.query.phoneBind);
    this.passwordSet = this.parseBoolean(this.$route.query.passwordSet);
    // this.fetchUserInfo();
  },
  methods: {
    parseBoolean(value, defaultValue = false) {
      if (value === undefined || value === null) return defaultValue;
      if (typeof value === 'boolean') return value;
      return String(value).toLowerCase() === 'true';
    },
    /**
     * 获取用户信息API调用
     */
    async fetchUserInfo() {
      // 无账号信息时不发起请求
      if (!this.phone && !this.email) return;
      
      try {
        this.loading = true;
        
        // 确定账号和账号类型
        const account = this.phone || this.email;
        const accountType = this.phone ? 'phone' : 'email';
        
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
        this.phone = userInfo.phone || '';
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
    // 处理更改操作
    handleChange(type) {
      // 携带当前数据到目标页面
      const query = {
        userId: this.userId,
        accountType: this.accountType,
        account: this.account,
        phone: this.phone,
        email: this.email,
        userName: this.userName,
        nickname: this.nickname,
      };
      const path = ROUTE_MAP[type];
      if (path) {
        this.$router.push({ path, query });
      } else {
        this.$toast.warning('请先绑定或设置该项信息');
      }
    }
  }
};
</script>
<style scoped lang="less" src="./assets/account.response.less" />
