import Vue from 'vue'
import VueRouter from 'vue-router'
import lanhu_dingjiabeifen from '../views/lanhu_dingjiabeifen/index.vue'
import lanhu_zhanghaoguanli from '../views/lanhu_zhanghaoguanli/index.vue'
import lanhu_shouye1 from '../views/lanhu_shouye1/index.vue'
import lanhu_shouye2 from '../views/lanhu_shouye2/index.vue'
import lanhu_xiazai from '../views/lanhu_xiazai/index.vue'
import lanhu_dingjia from '../views/lanhu_dingjia/index.vue'
import lanhu_shouye5 from '../views/lanhu_shouye5/index.vue'
import lanhu_zhanghaoguanlidanchuang from '../views/lanhu_zhanghaoguanlidanchuang/index.vue'
import lanhu_shouye4 from '../views/lanhu_shouye4/index.vue'
import lanhu_shouye3 from '../views/lanhu_shouye3/index.vue'
import Index from '../views/index/index.vue'
import Download from '../views/download/index.vue'
import Price from '../views/price/index.vue'
import Login from '../views/login/login.vue'
import login_set_password from '../views/login/login_set_password.vue'
import login_verify_code from '../views/login/login_verify_code.vue'
import login_with_password from '../views/login/login_with_password.vue'
import login_reset_password from '../views/login/login_reset_password.vue'
import login_bind_phone from '../views/login/login_bind_phone.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: "/index"
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/login/login_set_password',
    name: 'login_set_password',
    component: login_set_password
  },
  {
    path: '/login/login_reset_password',
    name: 'login_reset_password',
    component: login_reset_password
  },
  {
    path: '/login/login_verify_code',
    name: 'login_verify_code',
    component: login_verify_code
  },
 {
    path: '/login/login_with_password',
    name: 'login_with_password',
    component: login_with_password
  },
   {
    path: '/login/login_bind_phone',
    name: 'login_bind_phone',
    component: login_bind_phone
  },
  {
    path: '/lanhu_dingjiabeifen',
    name: 'lanhu_dingjiabeifen',
    component: lanhu_dingjiabeifen
  },
  {
    path: '/lanhu_zhanghaoguanli',
    name: 'lanhu_zhanghaoguanli',
    component: lanhu_zhanghaoguanli
  },
  {
    path: '/lanhu_shouye1',
    name: 'lanhu_shouye1',
    component: lanhu_shouye1
  },
  {
    path: '/lanhu_shouye2',
    name: 'lanhu_shouye2',
    component: lanhu_shouye2
  },
  {
    path: '/lanhu_xiazai',
    name: 'lanhu_xiazai',
    component: lanhu_xiazai
  },
  {
    path: '/lanhu_dingjia',
    name: 'lanhu_dingjia',
    component: lanhu_dingjia
  },
  {
    path: '/lanhu_shouye5',
    name: 'lanhu_shouye5',
    component: lanhu_shouye5
  },
  {
    path: '/lanhu_zhanghaoguanlidanchuang',
    name: 'lanhu_zhanghaoguanlidanchuang',
    component: lanhu_zhanghaoguanlidanchuang
  },
  {
    path: '/lanhu_shouye4',
    name: 'lanhu_shouye4',
    component: lanhu_shouye4
  },
  {
    path: '/lanhu_shouye3',
    name: 'lanhu_shouye3',
    component: lanhu_shouye3
  },
  {
    path: '/index',
    name: 'index',
    component: Index
  },
  {
    path: '/download',
    name: 'download',
    component: Download
  },
  {
    path: '/price',
    name: 'price',
    component: Price
  },
]

const router = new VueRouter({
  // mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
