import axios from 'axios'
// import auth from '@/utils/auth'
// 是否显示重新登录
export let isRelogin = { show: false }


axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: process.env.VUE_APP_BASE_API,
  // 超时
  timeout: 10000
})

// // 请求拦截器
// service.interceptors.request.use(
//   config => {
//     if (auth.hasToken()) {
//       // 从工具类获取 Token 并添加到请求头
//       config.headers['Authorization'] = `Bearer ${auth.getToken()}`
//     }
//     return config
//   },
//   error => {
//     return Promise.reject(error)
//   }
// )
export default service
