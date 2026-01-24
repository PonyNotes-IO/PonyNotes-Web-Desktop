<script setup>
import { ref, onMounted } from 'vue'
import AOS from 'aos'
import 'aos/dist/aos.css'

useSeoMeta({
    title: '下载 - 小马笔记',
})

const activePlatform = ref('Windows')

const platforms = [
    { name: 'Windows', icon: '/images/download/pcwin.png' },
    { name: 'macOS', icon: '/images/download/pcmacOS@2x.png' },
    { name: 'Android/Pad', icon: '/images/download/pcandroid.png' },
    { name: 'iPhone/iPad', icon: '/images/download/pcios@2x.png' }
]

onMounted(() => {
    AOS.init({
        duration: 800,
        easing: 'ease-out-quint',
        once: true,
        offset: 50,
        anchorPlacement: 'top-bottom',
    })
})
</script>

<template>
    <!-- 基础结构和全局样式 -->
    <div class="w-full bg-white font-sans overflow-x-hidden selection:bg-[#FF4D00] selection:text-white relative">

        <!-- =========================================================
             装饰性背景光晕
        ========================================================= -->
        <!-- 左侧蓝色/青色光晕 (优化性能) -->
        <div class="absolute left-[-100px] top-[40%] w-[300px] h-[300px] bg-gradient-to-r from-[#00FFFF] to-[#71F2B5] opacity-20 blur-[60px] rounded-full pointer-events-none z-0 will-change-transform"></div>
        
        <!-- 右侧粉色/橙色光晕 (优化性能) -->
        <div class="absolute right-[-100px] top-[60%] w-[300px] h-[300px] bg-gradient-to-l from-[#FF4081] to-[#FF99C2] opacity-20 blur-[60px] rounded-full pointer-events-none z-0 will-change-transform"></div>


        <!-- =========================================================
             SECTION 1: 顶部英雄区 (包含背景图)
             修改点：pb-[650px] 大幅增大底部填充，以拉长背景图
        ========================================================= -->
        <section class="relative w-full bg-no-repeat bg-top z-10"
            style="background-image: url('/images/download/bg@2x.png'); 
                   background-size: 100% auto;">

                <!-- 导航栏 (已移至 Layout) -->

            <div class="max-w-[1140px] mx-auto px-6 text-center pt-44 pb-[650px]"> <!-- 增大底部填充 -->
                <h1 class="text-[40px] md:text-[48px] font-bold text-gray-900 tracking-tight mb-10" data-aos="zoom-in">
                    下载
                </h1>

                <!-- 芯片版本选择按钮 -->
                <div class="flex justify-center gap-6 mb-24" data-aos="zoom-in" data-aos-delay="100">
                    <button class="bg-gradient-to-r from-[#FF8F00] to-[#FF4D00] text-white px-12 py-4 rounded-xl font-bold text-[18px] transition-transform hover:scale-105 active:scale-95 border-none shadow-none">
                        Intel芯片版
                    </button>
                    <button class="bg-gradient-to-r from-[#FF8F00] to-[#FF4D00] text-white px-12 py-4 rounded-xl font-bold text-[18px] transition-transform hover:scale-105 active:scale-95 border-none shadow-none">
                        Apple芯片版
                    </button>
                </div>
            </div>
        </section>


        <!-- =========================================================
             SECTION 2: 下载卡片列表区域
             修改点：-mt-[600px] 配合上面的 pb-[650px] 重新计算负边距
        ========================================================= -->
        <section class="w-full bg-white relative z-30 pt-10 pb-20">
            <div class="max-w-[880px] mx-auto px-6 -mt-[690px]" data-aos="zoom-in">
                <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
                    
                    <div 
                        v-for="item in platforms" 
                        :key="item.name"
                        @mouseenter="activePlatform = item.name"
                        class="bg-[#F8F9FA] rounded-[28px] py-10 px-8 text-center border-[1.5px] flex flex-col items-center cursor-pointer select-none"
                        :class="activePlatform === item.name ? 'border-[#FF4D00]' : 'border-transparent'"
                        style="transition: border-color 0.2s ease;"
                    >
                        <img :src="item.icon" :alt="item.name" class="h-14 mb-4 object-contain" />
                        <h3 class="text-[26px] font-semibold text-gray-900 mb-3 tracking-tight">
                            {{ item.name }}
                        </h3>

                        <div class="h-14 flex items-center justify-center">
                            <button 
                                class="font-bold border-none transition-none"
                                :class="activePlatform === item.name 
                                    ? 'bg-[#FF4D00] text-white px-12 py-3 rounded-[18px] text-[18px]' 
                                    : 'bg-transparent text-[#FF4D00] text-[20px] hover:underline p-0'"
                            >
                                立即下载
                            </button>
                        </div>
                    </div>

                </div>
            </div>
        </section>

    </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap');

h1, h3 {
    letter-spacing: -0.01em;
}

.cursor-pointer {
    transition: border-color 0.2s ease !important;
}

/* 优化性能：强制使用硬件加速 */
.will-change-transform {
    will-change: transform; 
}

@media (max-width: 768px) {
    /* 移动端修正顶部 section 的 padding */
    section:first-of-type > div {
        padding-top: 1.5rem !important;
        padding-bottom: 350px !important; 
    }
    /* 移动端修正卡片负边距 */
    .max-w-\[880px\] {
        margin-top: -150px !important; /* 增大负边距 */
    }
}
</style>