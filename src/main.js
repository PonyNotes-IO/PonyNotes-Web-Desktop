import 'vant/lib/index.css';
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import {Button,DropdownMenu,DropdownItem,Cell,Switch,Field} from 'vant'
import { RadioGroup, Radio } from 'vant';
import { CheckboxGroup, Checkbox } from 'vant';
import { Tab, Tabs } from 'vant';
import { Tabbar, TabbarItem } from 'vant';
import { NavBar, Icon, Tag,  Sticky, Swipe , SwipeItem } from 'vant';
import { Popup } from 'vant' 
import { Toast } from 'vant' 

Vue.component('van-field', Field)
Vue.component('van-button', Button)
Vue.component('van-dropdown-menu', DropdownMenu)
Vue.component('van-dropdown-item', DropdownItem)
Vue.component('van-cell', Cell)
Vue.component('van-switch', Switch)
Vue.component('van-checkbox-group', CheckboxGroup)
Vue.component('van-checkbox', Checkbox)
Vue.component('van-radio-group', RadioGroup)
Vue.component('van-radio', Radio)
Vue.component('van-tab', Tab)
Vue.component('van-tabs', Tabs)
Vue.component('van-tabbar', Tabbar)
Vue.component('van-tabbar-item', TabbarItem)
Vue.component('van-nav-bar', NavBar)
Vue.component('van-icon', Icon)
Vue.component('van-tag', Tag)
Vue.component('van-sticky',  Sticky)
Vue.component('van-swipe',  Swipe)
Vue.component('van-swipe-item',  SwipeItem)
Vue.component('van-popup', Popup)
Vue.use(Toast)
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
