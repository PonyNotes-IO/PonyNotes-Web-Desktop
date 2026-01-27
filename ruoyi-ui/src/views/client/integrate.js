/**
 * 客户端模块路由集成配置
 * 用于将client模块集成到主路由系统中
 */

import Layout from '@/layout'

// 客户端模块路由配置
const clientRoutes = {
  path: '/client',
  component: Layout,
  hidden: false,
  redirect: '/client/user',
  name: 'Client',
  meta: {
    title: '客户端管理',
    icon: 'user'
  },
  children: [
    {
      path: 'user',
      component: () => import('./UserManagement.vue'),
      name: 'ClientUser',
      meta: { title: '用户管理', icon: 'user' }
    },
    {
      path: 'ai-model-config',
      component: () => import('./aiModelConfig/index.vue'),
      name: 'AiModelConfig',
      meta: { title: 'AI模型配置', icon: 'ai' }
    },
    {
      path: 'app-version',
      component: () => import('./appVersion/index.vue'),
      name: 'AppVersion',
      meta: { title: '应用版本', icon: 'version' }
    },
    {
      path: 'payment',
      component: () => import('./payment/index.vue'),
      name: 'Payment',
      meta: { title: '支付管理', icon: 'money' }
    },
    {
      path: 'sign-in-log',
      component: () => import('./signInLog/index.vue'),
      name: 'SignInLog',
      meta: { title: '登录日志', icon: 'log' }
    },
    {
      path: 'subscripts',
      component: () => import('./subscripts/index.vue'),
      name: 'Subscripts',
      meta: { title: '订阅套餐', icon: 'subscription' }
    },
    {
      path: 'order',
      component: () => import('./OrderManagement.vue'),
      name: 'OrderManagement',
      meta: { title: '订单管理', icon: 'order' }
    },
    {
      path: 'workspace',
      component: () => import('./WorkspaceManagement.vue'),
      name: 'WorkspaceManagement',
      meta: { title: '工作区管理', icon: 'workspace' }
    },
    {
      path: 'addon',
      component: () => import('./AddonManagement.vue'),
      name: 'AddonManagement',
      meta: { title: '补充包管理', icon: 'addon' }
    },
    {
      path: 'login-profiles',
      component: () => import('./login_profiles/index.vue'),
      name: 'LoginProfiles',
      meta: { title: '登录配置', icon: 'profile' },
      redirect: '/client/login-profiles/bind',
      children: [
        {
          path: 'bind',
          component: () => import('./login_profiles/bind/index.vue'),
          name: 'ThirdPartyBind',
          meta: { title: '第三方绑定', icon: 'link' }
        },
        {
          path: 'record',
          component: () => import('./login_profiles/record/index.vue'),
          name: 'LoginRecord',
          meta: { title: '登录记录', icon: 'history' }
        }
      ]
    }
  ]
}

export default clientRoutes

/**
 * 集成到主路由的方法：
 * 
 * 在 src/router/index.js 中添加：
 * 
 * import clientRoutes from '@/views/client/integrate'
 * 
 * // 在路由配置中添加
 * export const constantRoutes = [
 *   // ...其他路由
 *   clientRoutes
 * ]
 */