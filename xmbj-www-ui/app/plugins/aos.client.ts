import AOS from 'aos'
import 'aos/dist/aos.css'

export default defineNuxtPlugin((nuxtApp) => {
    if (process.client) {
        nuxtApp.hook('app:mounted', () => {
            AOS.init({
                // 全局配置
                duration: 800, // 动画持续时间
                easing: 'ease-in-out', // 动画缓动函数
                once: true, // 是否只触发一次
                offset: 50, // 距离可视区域多远开始触发
            })
        })
    }
})
