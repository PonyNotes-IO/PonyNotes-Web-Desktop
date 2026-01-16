<template>
    <!-- 统一的顶部导航组件：背景完全透明，z-50 确保在最上层 -->
    <nav class="w-full bg-transparent h-20 relative z-50">
        <div class="max-w-[1140px] mx-auto px-6 h-full flex items-center justify-between">

            <!-- 左侧 Logo 区域：点击回首页 -->
            <NuxtLink to="/" class="flex items-center gap-2 group cursor-pointer">
                <img src="/images/ico.png"
                    class="w-8 h-8 object-contain transition-transform duration-1000 group-hover:rotate-[360deg] border-none shadow-none"
                    alt="小马笔记 Logo" />
                <span class="text-xl font-bold tracking-tight text-gray-900">小马笔记</span>
            </NuxtLink>

            <!-- 右侧 导航链接与按钮 -->
            <div class="hidden md:flex items-center gap-10 text-[14px] font-semibold text-gray-600">

                <!-- 功能链接 -->
                <NuxtLink to="/" class="nav-item">
                    功能
                    <span class="active-line"></span>
                </NuxtLink>

                <!-- 价格链接 -->
                <NuxtLink to="/price" class="nav-item">
                    价格
                    <span class="active-line"></span>
                </NuxtLink>

                <!-- 下载链接 -->
                <NuxtLink to="/download" class="nav-item">
                    下载
                    <span class="active-line"></span>
                </NuxtLink>

                <!-- 注册登录按钮：保持品牌色，无阴影 -->
                <button
                    class="bg-[#FF4D00] text-white px-5 py-2 rounded-lg font-bold transition-all duration-300 hover:scale-105 active:scale-95 border-none shadow-none">
                    注册/登录
                </button>
            </div>
        </div>
    </nav>
</template>

<script setup>
/** 
 * 组件逻辑：
 * 采用 NuxtLink 内置路由匹配
 * 透明背景以便复用在带有背景图的 Section 之上
 */
</script>

<style scoped>
/* 导航项基础样式 */
.nav-item {
    position: relative;
    transition: color 0.3s ease;
    display: block;
    padding: 4px 0;
}

/* 悬停状态 */
.nav-item:hover {
    color: #FF4D00;
}

/* 下划线基础动画样式 */
.active-line {
    position: absolute;
    bottom: -4px;
    left: 0;
    width: 0;
    height: 2px;
    background-color: #FF4D00;
    transition: width 0.3s ease;
}

/* 鼠标悬停显示线条 */
.nav-item:hover .active-line {
    width: 100%;
}

/* NuxtLink 匹配当前路由时自动应用的类 */
.router-link-active.nav-item {
    color: #FF4D00 !important;
}

/* 匹配当前路由时强制显示线条 */
.router-link-active.nav-item .active-line {
    width: 100% !important;
}

/* 字体统一渲染 */
nav {
    font-family: 'Inter', -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
    -webkit-font-smoothing: antialiased;
}
</style>