<script setup>
import { ref, onMounted } from 'vue'
import AOS from 'aos'
import 'aos/dist/aos.css'

useSeoMeta({
    title: '邀请 - 小马笔记',
})

const route = useRoute();

const toastVisible = ref(false);
const toastMessage = ref('');
const toastTimer = ref(null);

const toast = (message, duration = 3000) => {
    toastMessage.value = message;
    toastVisible.value = true;
    
    if (toastTimer.value) {
        clearTimeout(toastTimer.value);
    }
    
    toastTimer.value = setTimeout(() => {
        toastVisible.value = false;
    }, duration);
};

const openApp = (ws, q)=> {

    var iframe = document.createElement('iframe');
    const appUrl = `ponynotes://invite?code=${q}&workspaceId=${ws}`;
    iframe.style.display = 'none';
    iframe.src = appUrl;
    document.body.appendChild(iframe);

    // 尝试唤起应用
    // window.location.href = appUrl;
    // 设置超时时间（例如500ms）
    const timeout = 1000;
    const downloadUrl = location.origin + '/download';
    const timer = setTimeout(() => {
        // 超时后执行回退逻辑（如跳转到下载页）
        toast('打开App超时，请检查是否已安装PonyNotes');
        setTimeout(() => {
            window.location.href = downloadUrl;
        }, 900);
    }, timeout);

    // 如果页面失去焦点（应用成功打开），则清除超时
    window.onblur = function() {
        clearTimeout(timer);
    };
};

onMounted(() => {
    AOS.init({
        duration: 800,
        easing: 'ease-out-quint',
        once: true,
        offset: 50,
        anchorPlacement: 'top-bottom',
    })
    if(route.query.q && route.query.ws) {
        openApp(route.query.ws, route.query.q);
    }
})
</script>

<template>
    <!-- 基础结构和全局样式 -->
    <div class="w-full bg-white font-sans overflow-x-hidden selection:bg-[#FF4D00] selection:text-white relative">

        <!-- Toast 轻提示 -->
        <Transition name="toast">
            <div v-if="toastVisible" 
                 class="fixed top-8 left-1/2 transform -translate-x-1/2 z-[9999] 
                        bg-gray-900 text-white px-6 py-3 rounded-lg shadow-lg 
                        flex items-center gap-3 min-w-[200px] justify-center">
                <svg class="w-5 h-5 text-[#FF4D00]" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clip-rule="evenodd"/>
                </svg>
                <span class="text-sm font-medium">{{ toastMessage }}</span>
            </div>
        </Transition>

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
                    邀请协作
                </h1>

                
            </div>
        </section>


        <!-- =========================================================
             SECTION 2: 下载卡片列表区域
             修改点：-mt-[600px] 配合上面的 pb-[650px] 重新计算负边距
        ========================================================= -->
        <section class="w-full bg-white relative z-30 pt-10 pb-20">
            <div class="max-w-[880px] mx-auto px-6 -mt-[690px]" data-aos="zoom-in">
                <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
                    
                    

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

/* Toast 过渡动画 */
.toast-enter-active,
.toast-leave-active {
    transition: all 0.3s ease;
}

.toast-enter-from {
    opacity: 0;
    transform: translate(-50%, -20px);
}

.toast-leave-to {
    opacity: 0;
    transform: translate(-50%, -20px);
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