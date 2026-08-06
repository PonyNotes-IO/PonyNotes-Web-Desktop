<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import AOS from 'aos'
import 'aos/dist/aos.css'
import { downloadApi, type DownloadLinkItem } from '~/api/download'

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

// 后端返回的全部启用链接
const downloadLinks = ref<DownloadLinkItem[]>([])

// 加载状态
const loading = ref(true)

// 按平台分组：macOS 可能有多个架构，其他平台取第一条
const macArchLinks = computed(() => {
    return downloadLinks.value
        .filter(item => item.platform === 'macOS')
        .sort((a, b) => (a.sortOrder ?? 0) - (b.sortOrder ?? 0))
})

const platformLinkMap = computed<Record<string, DownloadLinkItem | undefined>>(() => {
    const map: Record<string, DownloadLinkItem | undefined> = {}
    for (const item of downloadLinks.value) {
        // 非 macOS 平台只取排序最前的一条
        if (item.platform !== 'macOS' && !map[item.platform]) {
            map[item.platform] = item
        }
    }
    return map
})

/** 获取某个非 macOS 平台的下载链接 */
const getPlatformLink = (platformName: string): DownloadLinkItem | undefined => {
    return platformLinkMap.value[platformName]
}

/** 是否有可用下载链接 */
const hasPlatformLink = (platformName: string): boolean => {
    if (platformName === 'macOS') {
        return macArchLinks.value.length > 0
    }
    const link = getPlatformLink(platformName)
    return !!link && !!link.downloadUrl && link.downloadUrl !== '#'
}

/** macOS 下载 */
const handleMacDownload = (arch: DownloadLinkItem) => {
    if (!arch.downloadUrl || arch.downloadUrl === '#') {
        return
    }
    window.open(arch.downloadUrl, '_blank')
}

/** 其他平台下载 */
const handlePlatformDownload = (platformName: string) => {
    const link = getPlatformLink(platformName)
    if (!link || !link.downloadUrl || link.downloadUrl === '#') {
        return
    }
    window.open(link.downloadUrl, '_blank')
}

/** 加载下载链接 */
const loadDownloadLinks = async () => {
    loading.value = true
    try {
        const res = await downloadApi.list()
        if (res && res.code === 200 && Array.isArray(res.data)) {
            downloadLinks.value = res.data
        }
    } catch (error) {
        console.error('加载下载链接失败:', error)
    } finally {
        loading.value = false
    }
}

/** 判断平台按钮文案 */
const getPlatformButtonText = (platformName: string): string => {
    if (['Android/Pad', 'iPhone/iPad'].includes(platformName)) {
        // 若后端配置了真实链接，则显示立即下载
        if (hasPlatformLink(platformName)) {
            return '立即下载'
        }
        return '敬请期待'
    }
    return '立即下载'
}

onMounted(() => {
    AOS.init({
        duration: 800,
        easing: 'ease-out-quart',
        once: true,
        offset: 50,
        anchorPlacement: 'top-bottom',
    })
    loadDownloadLinks()
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

                <!-- 芯片版本选择按钮 (已注释)
                <div class="flex justify-center gap-6 mb-24" data-aos="zoom-in" data-aos-delay="100">
                    <button class="bg-gradient-to-r from-[#FF8F00] to-[#FF4D00] text-white px-12 py-4 rounded-xl font-bold text-[18px] transition-transform hover:scale-105 active:scale-95 border-none shadow-none">
                        Intel芯片版
                    </button>
                    <button class="bg-gradient-to-r from-[#FF8F00] to-[#FF4D00] text-white px-12 py-4 rounded-xl font-bold text-[18px] transition-transform hover:scale-105 active:scale-95 border-none shadow-none">
                        Apple芯片版
                    </button>
                </div>
                -->
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

                        <!-- macOS: 区分 Intel (x86) / Apple (ARM) 芯片架构 -->
                        <div v-if="item.name === 'macOS'" class="w-full flex flex-col gap-3 mt-2">
                            <template v-if="macArchLinks.length > 0">
                                <button 
                                    v-for="arch in macArchLinks" 
                                    :key="arch.id"
                                    @click.stop="handleMacDownload(arch)"
                                    class="w-full bg-[#FF4D00] text-white px-6 py-2.5 rounded-[14px] text-[15px] font-bold hover:bg-[#E64500] transition-colors flex flex-col items-center gap-0.5"
                                >
                                    <span>{{ arch.archLabel || `版本 ${arch.version || ''}` }}</span>
                                    <span v-if="arch.archDesc" class="text-[11px] font-normal opacity-80">{{ arch.archDesc }}</span>
                                </button>
                            </template>
                            <template v-else>
                                <div class="h-14 flex items-center justify-center text-gray-400 text-sm">
                                    暂未提供下载
                                </div>
                            </template>
                        </div>

                        <!-- 其他平台 -->
                        <div v-else class="h-14 flex items-center justify-center">
                            <button 
                                @click.stop="handlePlatformDownload(item.name)"
                                class="font-bold border-none transition-none"
                                :class="activePlatform === item.name 
                                        ? 'bg-[#FF4D00] text-white px-12 py-3 rounded-[18px] text-[18px]' 
                                        : 'bg-transparent text-[#FF4D00] text-[20px] hover:underline p-0'"
                            >
                                {{ getPlatformButtonText(item.name) }}
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
