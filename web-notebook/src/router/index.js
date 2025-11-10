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
import accunt_manage from '../views/account/account_manage.vue'
import noteshare from '../views/noteshare/noteshare.vue'

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
      } else {
        next('/index');
      }
    }
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
  {
    path: '/account',
    name: 'account',
    component: accunt_manage
  },
  {
    path: '/noteshare',
    name: 'noteshare',
    component: noteshare
  },

]


const router = new VueRouter({
  // mode: 'history',
  base: process.env.BASE_URL,
  routes
})
const isNoteSharePath = (path) => {
  return path.replace(/\?.*/, '').endsWith('/noteshare') 
      || path.replace(/\?.*/, '').includes('/noteshare/');
};
router.beforeEach(async (to, from, next) => {
  // 设置页面标题
  document.title = '小马笔记';
  
  // 检测设备类型
  const deviceType = await deviceDetector.detect();
  const isMobilePath = to.path.startsWith('/mobile');
  
  if (isNoteSharePath(to.path)) {
    next();
    return; 
  }
  
  // 如果是移动设备访问PC端页面，重定向到移动端首页
  if (deviceType === 'mobile' && !isMobilePath && to.path !== '/') {
    // 重定向到移动端HTML文件
    window.location.href = '/mobile.html#/mobile/home';
  } else if (deviceType === 'pc' && isMobilePath) {
    next('/index');
  } else {
    next();
  }
});

export default router
