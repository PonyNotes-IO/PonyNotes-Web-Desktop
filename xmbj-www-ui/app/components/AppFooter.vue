<template>
    <!-- 移除所有动画，确保内容即时显示，z-50 确保层级在上 -->
    <footer class="footer-wrapper w-full bg-black text-white pt-8 pb-6 relative z-50">
        <div class="max-w-[1100px] mx-auto px-6">

            <!-- 主布局：在移动端纵向排列，桌面端横向分布 -->
            <div class="flex flex-col md:flex-row justify-between items-start">

                <!-- 左侧：品牌展示 (Logo + ICP) -->
                <div class="mb-8 md:mb-0 w-full md:w-auto text-left">
                    <div class="flex items-center gap-3 mb-1">
                        <img src="/images/foot_ico.png" class="w-10 h-10 object-contain shadow-none border-none"
                            alt="小马笔记 Logo" />
                        <span class="text-[26px] font-bold tracking-tight">小马笔记</span>
                    </div>
                    <p class="text-gray-600 text-[13px] tracking-wide ml-1 mb-6">
                        京ICP备2025131868号
                    </p>
                    <div class="flex items-center gap-2 ml-1">
                        <img src="/images/ioi.png" class="w-5 h-auto object-contain opacity-80" alt="Company Logo" />
                        <span class="text-gray-500 text-[14px] font-medium">北京爱欧爱科技有限公司</span>
                    </div>
                </div>

                <!-- 右侧：三个列。移动端使用 grid 布局，确保在窄屏下也能整齐排列 -->
                <div class="grid grid-cols-2 sm:grid-cols-3 gap-x-8 gap-y-8 w-full md:w-auto md:flex md:gap-x-16">

                    <!-- 产品列 -->
                    <div class="flex flex-col items-start">
                        <h4 class="text-[16px] font-bold mb-3 text-white">产品</h4>
                        <ul class="space-y-2 text-gray-400 font-medium text-[14px]">
                            <li><a href="#" class="hover:text-[#FF4D00] transition-colors duration-300">桌面端</a></li>
                            <li><a href="#" class="hover:text-[#FF4D00] transition-colors duration-300">手机端</a></li>
                            <li><a href="#" class="hover:text-[#FF4D00] transition-colors duration-300">隐私协议</a></li>
                            <li><a href="#" class="hover:text-[#FF4D00] transition-colors duration-300">用户协议</a></li>
                        </ul>
                    </div>

                    <!-- 资源列 -->
                    <div class="flex flex-col items-start">
                        <h4 class="text-[16px] font-bold mb-3 text-white">资源</h4>
                        <ul class="space-y-2 text-gray-400 font-medium text-[14px]">
                            <li><a href="#" class="hover:text-[#FF4D00] transition-colors duration-300">使用教程</a></li>
                            <li><a href="#" class="hover:text-[#FF4D00] transition-colors duration-300">快速上手</a></li>
                            <li><a href="#" class="hover:text-[#FF4D00] transition-colors duration-300">常见问题</a></li>
                        </ul>
                    </div>

                    <!-- 联系我们列 -->
                    <div class="flex flex-col items-start">
                        <h4 class="text-[16px] font-bold mb-3 text-white">联系我们</h4>
                        <ul class="space-y-2 text-gray-400 font-medium text-[14px]">
                            <li><a href="#" class="hover:text-[#FF4D00] transition-colors duration-300">B站</a></li>
                            <li><a href="#" class="hover:text-[#FF4D00] transition-colors duration-300">抖音</a></li>
                            <li><a href="#" class="hover:text-[#FF4D00] transition-colors duration-300">小红书</a></li>
                            <li><a href="#" class="hover:text-[#FF4D00] transition-colors duration-300">邮箱</a></li>
                        </ul>
                    </div>

                </div>
            </div>

            <!-- 底部版权声明 -->
            <div class="border-t border-white/5 mt-8 pt-6 text-center">
                <p class="text-gray-600 text-[12px] tracking-wide">
                    © 2026 小马笔记 PonyNotes. 让记录更简单。
                </p>
            </div>

        </div>
    </footer>
</template>

<script setup>
// 移除所有逻辑，仅作为静态组件展示，提高移动端稳定性
</script>

<style scoped>
footer {
    font-family: 'Inter', -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
    -webkit-font-smoothing: antialiased;
    /* 禁止页面底部的橡皮筋回弹效果 */
    overscroll-behavior-y: none;
}

/* 使用背景填充技术，防止滚动到底部时看到白色背景 */
.footer-wrapper {
    /* 在底部通过大尺寸 box-shadow 填充黑色 */
    box-shadow: 0 500px 0 0 #000;
}

h4 {
    letter-spacing: 0.02em;
}

ul li a {
    display: inline-block;
    white-space: nowrap;
}

/* 移动端间距微调 */
@media (max-width: 768px) {
    .footer-wrapper {
        padding-top: 2rem;
        padding-bottom: 1.5rem;
    }
}
</style>