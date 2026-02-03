// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2025-07-15',
  devtools: { enabled: true },
  css: ['~/assets/css/main.css'],
  modules: ['@nuxtjs/tailwindcss'],
  tailwindcss: {
    config: {
      // 配置项
    }
  },
  app: {
    head: {
      title: '基础开发框架 - Vue3 + Nuxt3',
      meta: [
        { charset: 'utf-8' },
        { name: 'viewport', content: 'width=device-width, initial-scale=1' },
        { name: 'description', content: '基于 Vue3 + Nuxt3 的支持 PC 和移动端适配的基础开发框架' },
        { name: 'keywords', content: 'Vue3, Nuxt3, SEO, 响应式, 移动端适配' }
      ],
      link: [
        { rel: 'icon', type: 'image/png', href: '/images/ico.png' }
      ]
    }
  }
})
