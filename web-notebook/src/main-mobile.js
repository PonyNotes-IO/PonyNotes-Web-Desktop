import Vue from 'vue'
import App from './App.vue'
import mobileRouter from './router/mobile-router'
import store from './store'
import Vant from 'vant'
import 'vant/lib/index.css'
import './assets/common.less'
// src/main.js
import Carousel from '@/components/mobile/carousel/index.vue'
import MobileNavBar from '@/components/mobile/MobileNavBar.vue'
import MobileBottom from '@/components/mobile/MobileBottom.vue'
Vue.component('MobileCarousel', Carousel)
Vue.component('MobileNavBar', MobileNavBar)
Vue.component('MobileBottom', MobileBottom)
Vue.use(Vant)

Vue.config.productionTip = false

new Vue({
  router: mobileRouter,
  store,
  render: h => h(App)
}).$mount('#app')

// 打印移动端首页访问地址
const os = require('os');
function getLocalIP() {
  const interfaces = os.networkInterfaces();
  for (const name of Object.keys(interfaces)) {
    for (const iface of interfaces[name]) {
      if (iface.family === 'IPv4' && !iface.internal) {
        return iface.address;
      }
    }
  }
  return 'localhost';
}

const ip = getLocalIP();
console.log(`移动端首页访问地址: http://localhost:8083/ 或 http://${ip}:8083/`);