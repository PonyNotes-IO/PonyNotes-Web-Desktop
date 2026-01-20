import Vue from 'vue'
import VueRouter from 'vue-router'
import MobileHome from '@/views/mobile/home/Home.vue'
import MobilePrice from '@/views/mobile/price/Price.vue'
import MobileFeatures from '@/views/mobile/Features.vue'
import MobilePricingYear from '@/views/mobile/price-year/index.vue'
import MobilePricingMonth from '@/views/mobile/price-month/index.vue'
import MobileDownload from '@/views/mobile/download/index.vue'
import Tutorial from '@/views/mobile/usage-guide/Tutorial.vue'
import QuickStart from '@/views/mobile/usage-guide/QuickStart.vue'
import FAQ from '@/views/mobile/usage-guide/FAQ.vue'
import Privacy from '@/views/mobile/usage-guide/Privacy.vue'
import Login from '@/views/mobile/login/login.vue'
import Account from '@/views/mobile/account/account.vue'
import ChangePhone from '@/views/mobile/account/change_phone.vue'
import ChangePassword from '@/views/mobile/account/change_password.vue'
import BindEmail from '@/views/mobile/account/bind_email.vue'
import noteshare from '@/views/noteshare/noteshare.vue'
Vue.use(VueRouter)
// 导入设备检测工具
import { deviceDetector } from '../util/device-utils'
const routes = [
  {
    path: '/',
    beforeEnter: (to, from, next) => {
      // 如果目标 URL 包含 /noteshare，则不重定向到 /index
      if (to.fullPath.includes('/noteshare')) {
        next();
      }
    }
  },
  {
    path: '/mobile/home',
    name: 'MobileHome',
    component: MobileHome,
    meta: { title: '小马笔记 - 首页' }
  },
  {
    path: '/mobile/price',
    name: 'MobilePrice',
    component: MobilePrice,
    meta: { title: '小马笔记 - 价格' }
  },
  {
    path: '/mobile/features',
    name: 'MobileFeatures',
    component: MobileFeatures,
    meta: { title: '小马笔记 - 功能' }
  },
  {
    path: '/mobile/price-year',
    name: 'MobilePriceYear',
    component: MobilePricingYear,
    meta: { title: '小马笔记 - 价格年付' }
  },
  {
    path: '/mobile/price-month',
    name: 'MobilePriceMonth',
    component: MobilePricingMonth,
    meta: { title: '小马笔记 - 价格月付' }
  },
  {
    path: '/mobile/download',
    name: 'MobileDownload',
    component: MobileDownload,
    meta: { title: '小马笔记 - 下载' }
  },
  {
    path: '/mobile/usage-guide/tutorial',
    name: 'Tutorial',
    component: Tutorial,
    meta: { title: '小马笔记 - 使用教程' }
  },
  {
    path: '/mobile/usage-guide/quick-start',
    name: 'QuickStart',
    component: QuickStart,
    meta: { title: '小马笔记 - 快速上手' }
  },
  {
    path: '/mobile/usage-guide/faq',
    name: 'FAQ',
    component: FAQ,
    meta: { title: '小马笔记 - 常见问题' }
  },
  {
    path: '/mobile/usage-guide/privacy',
    name: 'Privacy',
    component: Privacy,
    meta: { title: '小马笔记 - 隐私协议' }
  },
  {
    path: '/mobile/login',
    name: 'Login',
    component: Login,
    meta: { title: '小马笔记 - 登录注册' }
  },
  {
    path: '/mobile/account',
    name: 'Account',
    component: Account,
    meta: { title: '小马笔记 - 账户管理' }
  },
  {
    path: '/mobile/account/change_phone',
    name: 'ChangePhone',
    component: ChangePhone,
    meta: { title: '小马笔记 - 更改手机号' }
  },
  {
    path: '/mobile/account/change_password',
    name: 'ChangePassword',
    component: ChangePassword,
    meta: { title: '小马笔记 - 更改密码' }
  },
  {
    path: '/mobile/account/bind_email',
    name: 'BindEmail',
    component: BindEmail,
    meta: { title: '小马笔记 - 绑定邮箱' }
  },
  {
    path: '/noteshare',
    name: 'mobile-noteshare',
    component: noteshare,
    meta: { title: '小马笔记 - 共享连接' }
  } 
]

const mobileRouter = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes
})
// mobileRouter.beforeEach(async (to, from, next) => {
//   // 设置页面标题
//   document.title = '小马笔记';

//   // 检测设备类型
//   const deviceType = await deviceDetector.detect();
//   const isMobilePath = to.path.startsWith('/mobile');
  
//   // 根据设备类型决定重定向
//   if (deviceType === 'mobile' && !isMobilePath && to.path !== '/'&& !to.path.startsWith('/noteshare')) {
//     next('/mobile/home');
//   } else if (deviceType === 'pc' && isMobilePath  && !to.path.startsWith('/noteshare')) {
//     next('/index');
//   } else {
//     next();
//   }
// });
const isNoteSharePath = (path) => {
  return path.replace(/\?.*/, '').endsWith('/noteshare') 
      || path.replace(/\?.*/, '').includes('/noteshare/');
};
mobileRouter.beforeEach(async (to, from, next) => {
  document.title = '小马笔记';
  const deviceType = await deviceDetector.detect();
  const isMobilePath = to.path.startsWith('/mobile');
  const noteshare = to.path.includes('noteshare');
  console.log('noteshare path check:', noteshare);
  if (isNoteSharePath(to.path)) {
    next();
    return; // 直接返回，不执行后续设备检测逻辑
  }
  if (deviceType === 'mobile' && !isMobilePath && to.path !== '/' && !noteshare) {
    next('/mobile/home');
  } else if (deviceType === 'pc' && isMobilePath ) {
    next('/index');
  } else {
    next();
  }
});

export default mobileRouter