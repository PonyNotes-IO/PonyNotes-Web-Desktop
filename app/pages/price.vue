<script setup>
import { ref, onMounted } from 'vue'
// 引入 AOS 动画库
import AOS from 'aos'
import 'aos/dist/aos.css'

useSeoMeta({
    title: '价格方案 - 小马笔记',
})

// 计费周期切换：0为月付，1为年付
const billingCycle = ref(1)

// 选中的计划索引：0免费, 1学生, 2标准, 3团队
const selectedPlan = ref(0)

onMounted(() => {
    // 初始化 AOS 配置
    AOS.init({
        duration: 1000,
        easing: 'ease-out-quint',
        once: false,
        offset: 50,
        anchorPlacement: 'top-bottom',
    })
})
</script>

<template>
    <!-- 全局容器 -->
    <div class="w-full bg-white min-h-screen font-sans overflow-x-hidden selection:bg-[#FF4D00] selection:text-white">

        <!-- =========================================================
             顶部区域 (底部边缘自然淡出背景)
        ========================================================= -->
        <div class="relative w-full bg-no-repeat bg-cover bg-top pb-64 header-bg-mask"
            style="background-image: url('/images/topbg.png');">

            <div
                class="absolute bottom-0 left-0 w-full h-32 bg-gradient-to-t from-white via-white/40 to-transparent pointer-events-none">
            </div>

            <div class="relative z-10">
                <!-- 导航栏 -->
                <nav class="max-w-[1100px] mx-auto px-6 h-20 flex items-center justify-between">
                    <AppHeader />
                </nav>

                <!-- 主标题区域 (主标题居中) -->
                <header class="max-w-[1140px] mx-auto px-6 pt-12">
                    <h1 class="text-[56px] font-extrabold text-gray-900 tracking-tight leading-tight text-center"
                        data-aos="fade-up">
                        新一代<span class="text-[#FF4D00]">AI</span>本地存储小马笔记
                    </h1>

                    <!-- 计费切换按钮 (靠左对齐) -->
                    <div class="mt-12 flex justify-start" data-aos="fade-up" data-aos-delay="100">
                        <div
                            class="inline-flex items-center p-1 bg-white/40 backdrop-blur-md border border-gray-100 rounded-full cursor-pointer">
                            <button @click="billingCycle = 0"
                                :class="[billingCycle === 0 ? 'bg-[#FF4D00] text-white shadow-none' : 'text-gray-500 hover:text-gray-700']"
                                class="px-8 py-1.5 rounded-full text-sm font-bold transition-all duration-300">月付</button>
                            <button @click="billingCycle = 1"
                                :class="[billingCycle === 1 ? 'bg-[#FF4D00] text-white shadow-none' : 'text-gray-500 hover:text-gray-700']"
                                class="px-8 py-1.5 rounded-full text-sm font-bold transition-all duration-300">年付</button>
                        </div>
                    </div>
                </header>
            </div>
        </div>

        <!-- ================= 核心价格卡片区域 (手动展开所有卡片) ================= -->
        <!-- ================= 核心价格卡片区域 (手动展开所有卡片) ================= -->
        <section class="relative z-10 max-w-[1140px] mx-auto px-6" data-aos="fade-up" data-aos-delay="200">
            <div class="grid grid-cols-1 md:grid-cols-4 gap-6 items-stretch -mt-56">

                <!-- 免费版 -->
                <div @click="selectedPlan = 0"
                    :class="[selectedPlan === 0 ? 'border-2 border-[#FF4D00]' : 'border border-[#FFD9C6]']"
                    class="bg-white rounded-2xl p-5 pb-6 flex flex-col h-full shadow-none cursor-pointer transition-all duration-300">
                    <div class="h-24 mt-1">
                        <h3 class="text-[16px] font-medium text-gray-900 mb-1">免费</h3>
                        <div class="flex items-baseline gap-0.5">
                            <span class="text-[14px] font-bold">¥</span>
                            <span class="text-[34px] font-bold leading-none text-gray-900">0</span>
                        </div>
                    </div>
                    <button
                        class="w-full bg-[#FF4D00] text-white py-2.5 rounded-xl font-bold mb-6 border-none shadow-none text-[14px]">立即开始</button>
                    <ul :class="[selectedPlan === 0 ? 'text-gray-950 font-bold' : 'text-gray-400 font-medium']"
                        class="space-y-2.5 text-[13px] px-1 transition-colors duration-300">
                        <li>本地免费使用</li>
                        <li>你的随身知识库系统</li>
                        <li>AI图文生成 + 云存储</li>
                        <li>开启智能学习体验</li>
                    </ul>
                </div>

                <!-- 学生版 -->
                <div @click="selectedPlan = 1"
                    :class="[selectedPlan === 1 ? 'border-2 border-[#FF4D00]' : 'border border-[#FFD9C6]']"
                    class="bg-white rounded-2xl p-5 pb-6 flex flex-col h-full shadow-none cursor-pointer transition-all duration-300">
                    <div class="h-24 mt-1">
                        <h3 class="text-[16px] font-medium text-gray-900 mb-1">学生版</h3>
                        <div class="flex items-baseline gap-0.5">
                            <span class="text-[14px] font-bold">¥</span>
                            <span class="text-[34px] font-bold leading-none text-gray-900">3</span>
                            <span class="text-[#FF4D00] text-[12px] font-bold ml-1">每月2G存储空间</span>
                        </div>
                    </div>
                    <button
                        class="w-full bg-[#E5E7EB] text-gray-900 py-2.5 rounded-xl font-bold mb-6 border-none shadow-none text-[14px]">去升级</button>
                    <ul :class="[selectedPlan === 1 ? 'text-gray-950 font-bold' : 'text-gray-400 font-medium']"
                        class="space-y-2.5 text-[13px] px-1 leading-snug transition-colors duration-300">
                        <li>轻装上阵，AI助学</li>
                        <li>从学生版开始小投入</li>
                        <li>大升级学习效率跃升一步!</li>
                        <li>AI图文生成 + 云存储</li>
                        <li>开启智能学习体验</li>
                    </ul>
                </div>

                <!-- 标准版 -->
                <div @click="selectedPlan = 2"
                    :class="[selectedPlan === 2 ? 'border-2 border-[#FF4D00]' : 'border border-[#FFD9C6]']"
                    class="bg-white rounded-2xl p-5 pb-6 flex flex-col h-full shadow-none cursor-pointer transition-all duration-300">
                    <div class="h-24 mt-1">
                        <h3 class="text-[16px] font-medium text-gray-900 mb-1">标准版</h3>
                        <div class="flex items-baseline gap-0.5">
                            <span class="text-[14px] font-bold">¥</span>
                            <span class="text-[34px] font-bold leading-none text-gray-900">8</span>
                            <span class="text-[#FF4D00] text-[12px] font-bold ml-1">每月10G存储空间</span>
                        </div>
                    </div>
                    <button
                        class="w-full bg-[#E5E7EB] text-gray-900 py-2.5 rounded-xl font-bold mb-6 border-none shadow-none text-[14px]">去升级</button>
                    <ul :class="[selectedPlan === 2 ? 'text-gray-950 font-bold' : 'text-gray-400 font-medium']"
                        class="space-y-2.5 text-[13px] px-1 leading-snug transition-colors duration-300">
                        <li>面向内容创作者</li>
                        <li>与高效工作者的智能笔记</li>
                        <li>空间丰富AI+10GB云同步</li>
                        <li>助你随时记录、随处创作</li>
                        <li>一站整合写作、整理、归档</li>
                    </ul>
                </div>

                <!-- 团队版 -->
                <div @click="selectedPlan = 3"
                    :class="[selectedPlan === 3 ? 'border-2 border-[#FF4D00]' : 'border border-[#FFD9C6]']"
                    class="bg-white rounded-2xl p-5 pb-6 flex flex-col h-full shadow-none cursor-pointer transition-all duration-300">
                    <div class="h-24 mt-1">
                        <h3 class="text-[16px] font-medium text-gray-900 mb-1">团队版</h3>
                        <div class="flex items-baseline gap-0.5">
                            <span class="text-[14px] font-bold">¥</span>
                            <span class="text-[34px] font-bold leading-none text-gray-900">18</span>
                            <span class="text-[#FF4D00] text-[12px] font-bold ml-1">每月20G存储空间</span>
                        </div>
                    </div>
                    <button
                        class="w-full bg-[#E5E7EB] text-gray-900 py-2.5 rounded-xl font-bold mb-6 border-none shadow-none text-[14px]">去升级</button>
                    <ul :class="[selectedPlan === 3 ? 'text-gray-950 font-bold' : 'text-gray-400 font-medium']"
                        class="space-y-2.5 text-[13px] px-1 leading-snug transition-colors duration-300">
                        <li>团队协作不止是共享</li>
                        <li>更是高效共创</li>
                        <li>专属权限+共享工作区</li>
                        <li>大容量云空间轻松协同办公</li>
                        <li>配套AI能力为团队注入内容</li>
                    </ul>
                </div>
            </div>
        </section>





        <!-- ================= 空间补充包 (调整为扁平化比例) ================= -->
        <section class="max-w-[1140px] mx-auto px-6 mt-16 text-left">
            <div class="max-w-[950px] ml-0">
                <div class="mb-6" data-aos="fade-right">
                    <span
                        class="border border-[#FFD9C6] text-[#FF4D00] px-5 py-1 rounded-full font-medium text-[13px]">空间补充包</span>
                </div>
                <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
                    <!-- 调整 py-6 缩短高度，mb 缩短间距 -->
                    <div class="bg-white rounded-2xl py-6 px-8 border border-[#FFD9C6] text-center shadow-none"
                        data-aos="fade-up" data-aos-delay="100">
                        <p class="text-[15px] font-bold text-gray-900 mb-2">云存储空间<span class="text-[#FF4D00]">
                                5GB</span></p>
                        <div class="text-[28px] font-bold mb-5 text-gray-900 leading-none">¥ 3</div>
                        <button
                            class="w-full bg-[#E5E7EB] text-gray-900 py-2.5 rounded-xl font-bold border-none shadow-none text-sm">购买</button>
                    </div>
                    <div class="bg-white rounded-2xl py-6 px-8 border border-[#FFD9C6] text-center shadow-none"
                        data-aos="fade-up" data-aos-delay="200">
                        <p class="text-[15px] font-bold text-gray-900 mb-2">云存储空间<span class="text-[#FF4D00]">
                                20GB</span></p>
                        <div class="text-[28px] font-bold mb-5 text-gray-900 leading-none">¥ 15</div>
                        <button
                            class="w-full bg-[#E5E7EB] text-gray-900 py-2.5 rounded-xl font-bold border-none shadow-none text-sm">购买</button>
                    </div>
                    <div class="bg-white rounded-2xl py-6 px-8 border border-[#FFD9C6] text-center shadow-none"
                        data-aos="fade-up" data-aos-delay="300">
                        <p class="text-[15px] font-bold text-gray-900 mb-2">云存储空间<span class="text-[#FF4D00]">
                                50GB</span></p>
                        <div class="text-[28px] font-bold mb-5 text-gray-900 leading-none">¥ 35</div>
                        <button
                            class="w-full bg-[#E5E7EB] text-gray-900 py-2.5 rounded-xl font-bold border-none shadow-none text-sm">购买</button>
                    </div>
                </div>
            </div>
        </section>

        <!-- ================= 小马AI对话 (调整为扁平化比例) ================= -->
        <section class="max-w-[1140px] mx-auto px-6 mt-16 text-left">
            <div class="max-w-[950px] ml-0">
                <div class="mb-6" data-aos="fade-right">
                    <span
                        class="border border-[#FFD9C6] text-[#FF4D00] px-5 py-1 rounded-full font-medium text-[13px]">小马AI对话</span>
                </div>
                <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
                    <!-- 调整 py-6 缩短高度，mb 缩短间距 -->
                    <div class="bg-white rounded-2xl py-6 px-8 border border-[#FFD9C6] text-center shadow-none"
                        data-aos="fade-up" data-aos-delay="100">
                        <p class="text-[15px] font-bold text-gray-900 mb-2">100次对话/10张图片</p>
                        <div class="text-[28px] font-bold mb-5 text-gray-900 leading-none">¥ 10</div>
                        <button
                            class="w-full bg-[#E5E7EB] text-gray-900 py-2.5 rounded-xl font-bold border-none shadow-none text-sm">购买</button>
                    </div>
                    <div class="bg-white rounded-2xl py-6 px-8 border border-[#FFD9C6] text-center shadow-none"
                        data-aos="fade-up" data-aos-delay="200">
                        <p class="text-[15px] font-bold text-gray-900 mb-2">400次对话/20张图片</p>
                        <div class="text-[28px] font-bold mb-5 text-gray-900 leading-none">¥ 30</div>
                        <button
                            class="w-full bg-[#E5E7EB] text-gray-900 py-2.5 rounded-xl font-bold border-none shadow-none text-sm">购买</button>
                    </div>
                    <div class="bg-white rounded-2xl py-6 px-8 border border-[#FFD9C6] text-center shadow-none"
                        data-aos="fade-up" data-aos-delay="300">
                        <p class="text-[15px] font-bold text-gray-900 mb-2">1000次对话/50张图片</p>
                        <div class="text-[28px] font-bold mb-5 text-gray-900 leading-none">¥ 50</div>
                        <button
                            class="w-full bg-[#E5E7EB] text-gray-900 py-2.5 rounded-xl font-bold border-none shadow-none text-sm">购买</button>
                    </div>
                </div>
            </div>
        </section>

        <!-- ================= 方案与功能详细对比 (全量手动展开) ================= -->
        <section class="max-w-[1100px] mx-auto px-6 mt-32">
            <h2 class="text-[42px] font-bold text-gray-900 mb-12" data-aos="fade-right">方案与功能</h2>

            <div class="flex flex-col gap-1.5" data-aos="fade-up">
                <!-- 头部 -->
                <div
                    class="grid grid-cols-5 bg-[#F5F5F7] py-4 px-6 rounded-2xl items-center text-[16px] font-bold text-gray-900">
                    <div></div>
                    <div class="text-center">免费版(本地)</div>
                    <div class="text-center">学生版</div>
                    <div class="text-center">标准版</div>
                    <div class="text-center">团队版</div>
                </div>
                <!-- 基础项 -->
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">页面/块数量</div>
                    <div class="text-center text-gray-500">无限制</div>
                    <div class="text-center text-gray-500">无限制</div>
                    <div class="text-center text-gray-500">无限制</div>
                    <div class="text-center text-gray-500">无限制</div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">云存储空间</div>
                    <div class="text-center text-gray-500">—</div>
                    <div class="text-center text-gray-900">2GB</div>
                    <div class="text-center text-gray-900">10GB</div>
                    <div class="text-center text-gray-900">20GB</div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">导入与导出</div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">收件箱</div>
                    <div class="text-center text-gray-500">—</div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">多端同步(iPad、Mac、Windows、Web)</div>
                    <div class="text-center text-gray-500">—</div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">支持 API</div>
                    <div class="text-center text-gray-500">—</div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">版本历史</div>
                    <div class="text-center text-gray-500">—</div>
                    <div class="text-center text-gray-900">7天</div>
                    <div class="text-center text-gray-900">7天</div>
                    <div class="text-center text-gray-900">30天</div>
                </div>

                <!-- AI 分类 -->
                <div class="mt-6 mb-2 px-2 text-[#FF4D00] font-bold text-[16px]">AI功能</div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">AI对话</div>
                    <div class="text-center text-gray-500">—</div>
                    <div class="text-center text-gray-900">10次/月</div>
                    <div class="text-center text-gray-900">40次/月</div>
                    <div class="text-center text-gray-900">120次/月</div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">图片生成</div>
                    <div class="text-center text-gray-500">—</div>
                    <div class="text-center text-gray-500">—</div>
                    <div class="text-center text-gray-900">10张/月</div>
                    <div class="text-center text-gray-900">20张/月</div>
                </div>

                <!-- 协作 分类 -->
                <div class="mt-6 mb-2 px-2 text-[#FF4D00] font-bold text-[16px]">共享和协作</div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">分享链接</div>
                    <div class="text-center text-gray-500">—</div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">发布</div>
                    <div class="text-center text-gray-500">—</div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">工作区成员</div>
                    <div class="text-center text-gray-500">—</div>
                    <div class="text-center text-gray-900">2</div>
                    <div class="text-center text-gray-900">5</div>
                    <div class="text-center text-gray-900">10</div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">协作工作区</div>
                    <div class="text-center text-gray-500">—</div>
                    <div class="text-center text-gray-900 font-medium text-[13px]">仅限1</div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                </div>

                <!-- 权限 分类 -->
                <div class="mt-6 mb-2 px-2 text-[#FF4D00] font-bold text-[16px]">安全与权限管理</div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">页面权限管理</div>
                    <div class="text-center text-gray-500">—</div>
                    <div class="text-center text-gray-900 font-medium text-[13px]">仅查看</div>
                    <div class="text-center text-gray-900 text-[13px]">10个访客编辑</div>
                    <div class="text-center text-gray-900 text-[13px]">50个访客编辑</div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">空间成员管理</div>
                    <div class="text-center text-gray-500">—</div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">空间成员分组</div>
                    <div class="text-center text-gray-500">—</div>
                    <div class="text-center text-gray-500">—</div>
                    <div class="text-center text-gray-500">—</div>
                    <div class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                </div>
            </div>
        </section>

        <!-- ================= 常见问题 (全量手动展开) ================= -->
        <section class="py-24 bg-white w-full flex justify-center">
            <div class="w-[96%] max-w-[1240px] bg-black text-white rounded-[3.5rem] py-24 overflow-hidden relative shadow-none"
                data-aos="zoom-in-up">

                <!-- 内部装饰 -->
                <div
                    class="absolute top-[-20%] right-[-10%] w-[600px] h-[600px] bg-blue-600 opacity-10 blur-[150px] rounded-full animate-pulse-slow">
                </div>
                <div class="absolute bottom-[-20%] left-[-10%] w-[600px] h-[600px] bg-purple-600 opacity-10 blur-[150px] rounded-full animate-pulse-slow"
                    style="animation-delay: 2s;"></div>

                <div class="max-w-[1000px] mx-auto px-8 relative z-10 text-left">
                    <h2 class="text-[42px] font-bold mb-16 text-white" data-aos="fade-right">常见问题</h2>

                    <div class="space-y-12">
                        <!-- 1 -->
                        <div class="space-y-3" data-aos="fade-up">
                            <h4 class="text-[18px] font-bold text-white">1、我需要更多的云存储空间怎么办？</h4>
                            <div class="text-[15px] text-white/70 leading-relaxed font-medium">
                                <p>你可以随时通过「账户设置 → 存储扩容」购买额外空间。我们提供：5GB ¥5/月、20GB ¥15/月、50GB ¥35/月。空间立即生效，叠加原有容量，无需等待。
                                </p>
                            </div>
                        </div>
                        <!-- 2 -->
                        <div class="space-y-3" data-aos="fade-up">
                            <h4 class="text-[18px] font-bold text-white">2、AI 对话/图片额度用完了怎么办？</h4>
                            <div class="text-[15px] text-white/70 leading-relaxed font-medium">
                                <p>你可以购买 AI 增值包扩容，额度实时到账，不限使用期限。我们提供多种不同规格的额度包以供选择。</p>
                            </div>
                        </div>
                        <!-- 3 -->
                        <div class="space-y-3" data-aos="fade-up">
                            <h4 class="text-[18px] font-bold text-white">3、可以随时升级或降级套餐吗？</h4>
                            <div class="text-[15px] text-white/70 leading-relaxed font-medium">
                                <p>您可以随时升级套餐，系统将按比例扣除剩余费用，补差价即可完成升级。但当前暂不支持主动降级，以保障数据完整性。</p>
                            </div>
                        </div>
                        <!-- 4 -->
                        <div class="space-y-3" data-aos="fade-up">
                            <h4 class="text-[18px] font-bold text-white">4、学生版只有学生可以买吗？</h4>
                            <div class="text-[15px] text-white/70 leading-relaxed font-medium">
                                <p>不是的，所有用户都可以购买学生版，无需学生认证。初衷是提供低门槛入门体验，帮助更多人尝试 AI 驱动的笔记。</p>
                            </div>
                        </div>
                        <!-- 5 -->
                        <div class="space-y-3" data-aos="fade-up">
                            <h4 class="text-[18px] font-bold text-white">5、我的数据会因未续费而丢失吗？</h4>
                            <div class="text-[15px] text-white/70 leading-relaxed font-medium">
                                <p>即使取消订阅，数据仍保留在本地。云同步和 AI 等功能将暂停。超过 30 天未续费的账号，云端数据可能会被清理，请务必提前备份。</p>
                            </div>
                        </div>
                        <!-- 6 -->
                        <div class="space-y-3" data-aos="fade-up">
                            <h4 class="text-[18px] font-bold text-white">6、付费订阅后是否支持退款？</h4>
                            <div class="text-[15px] text-white/70 leading-relaxed font-medium">
                                <p>目前暂不支持退款，请在购买前了解产品。AI 额度和存储空间启用即产生成本。</p>
                            </div>
                        </div>
                        <!-- 7 -->
                        <div class="space-y-3" data-aos="fade-up">
                            <h4 class="text-[18px] font-bold text-white">7、可以通过第三方工具同步或备份数据吗？</h4>
                            <div class="text-[15px] text-white/70 leading-relaxed font-medium">
                                <p>不建议。这类工具非为笔记实时文件设计，可能导致数据损坏或锁定异常。推荐使用软件内置的加密云同步功能。</p>
                            </div>
                        </div>
                        <!-- 8 -->
                        <div class="space-y-3" data-aos="fade-up">
                            <h4 class="text-[18px] font-bold text-white">8、我在电脑端购买了订阅，如何在 iPad 上恢复使用？</h4>
                            <div class="text-[15px] text-white/70 leading-relaxed font-medium">
                                <p>使用相同账号登录，系统将自动同步权益。Apple 内购用户如果未自动恢复，可点击「恢复购买」按钮进行手动同步。</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>



    </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700;800&display=swap');

h1,
h2,
h3 {
    letter-spacing: -0.02em;
}

.header-bg-mask {
    -webkit-mask-image: linear-gradient(to bottom, #000 85%, transparent 100%);
    mask-image: linear-gradient(to bottom, #000 85%, transparent 100%);
}

@media (max-width: 768px) {
    h1 {
        font-size: 28px !important;
    }
}
</style>