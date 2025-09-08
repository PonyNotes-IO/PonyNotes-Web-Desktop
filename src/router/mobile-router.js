import Vue from 'vue'
import VueRouter from 'vue-router'
import MobileHome from '@/views/mobile/home/Home.vue'
import MobilePrice from '@/views/mobile/price/Price.vue'
import MobileFeatures from '@/views/mobile/Features.vue'
import MobilePricingYear from '@/views/mobile/price-year/index.vue'
import MobilePricingMonth from '@/views/mobile/price-month/index.vue'
import MobileDownload from '@/views/mobile/download/index.vue'
import ChangePassword from '@/views/mobile/account/change-password/index.vue'
import BindEmail from '@/views/mobile/account/bind-email/index.vue'
import ChangePhone from '@/views/mobile/account/chang-phone/index.vue'
import IdentityAuth from '@/views/mobile/account/identity-auth/index.vue'
import AccountBinding from '@/views/mobile/account/account-detail/index.vue'
import Tutorial from '@/views/mobile/usage-guide/Tutorial.vue'
import QuickStart from '@/views/mobile/usage-guide/QuickStart.vue'
import FAQ from '@/views/mobile/usage-guide/FAQ.vue'
import Privacy from '@/views/mobile/usage-guide/Privacy.vue'

Vue.use(VueRouter)

const routes = [
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
    meta: { title: '小马笔记 - 价格年付' }
  },
  {
    path: '/mobile/download',
    name: 'MobileDownload',
    component: MobileDownload,
    meta: { title: '小马笔记 - 下载' }
  },
  {
    path: '/mobile/account/change-password',
    name: 'ChangePassword',
    component: ChangePassword,
    meta: { title: '小马笔记 - 更改密码' }
  },
  {
    path: '/mobile/account/bind-email',
    name: 'BindEmail',
    component: BindEmail,
    meta: { title: '小马笔记 - 绑定邮箱' }
  },
  {
    path: '/mobile/account/change-phone',
    name: 'ChangePhone',
    component: ChangePhone,
    meta: { title: '小马笔记 - 更改手机号' }
  },
  {
    path: '/mobile/account/identity-auth',
    name: 'IdentityAuth',
    component: IdentityAuth,
    meta: { title: '小马笔记 - 身份验证' }
  },
  {
    path: '/mobile/account/account-detail',
    name: 'AccountBinding',
    component: AccountBinding,
    meta: { title: '小马笔记 - 账号绑定' }
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
  }
]

const mobileRouter = new VueRouter({
  mode: 'history',
  routes
})

export default mobileRouter