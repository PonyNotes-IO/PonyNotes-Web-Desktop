import Vue from 'vue'
import Vuex from 'vuex'
import scale from '../utils/scale'

Vue.use(Vuex)

// 检查scale是否为有效的Vuex插件
function is_valid_plugin(plugin) {
  return typeof plugin === 'function' || (plugin && typeof plugin.install === 'function')
}

// 仅在scale是有效插件时使用
const plugins = is_valid_plugin(scale) ? [scale] : []

export default new Vuex.Store({
  state: {},
  mutations: {},
  actions: {},
  modules: {},
  plugins: plugins  
})