<script setup>
import { ref, onMounted, onUnmounted, computed } from 'vue'
// 引入 AOS 动画库
import AOS from 'aos'
import 'aos/dist/aos.css'

useSeoMeta({
  title: '小马笔记 - 让记录更简单，让知识更加智慧',
})

// --- 1. 原始数据 (用于轮播图逻辑) ---
const rawSlides = [
  { id: 1, title: '导入或者迁移', desc: '从其他应用 and 文件导入数据到 小马笔记', type: 'import' },
  { id: 2, title: '无限画布', desc: '打破页面限制，随心所欲布局你的灵感', type: 'canvas' },
  { id: 3, title: '多维表数据库', desc: '用表格、看板、日历管理你的结构化数据', type: 'table' },
  { id: 4, title: 'AI 智能助手', desc: '利用大模型能力，一键总结、润色与续写', type: 'ai' },
  { id: 5, title: '全平台同步', desc: '实时同步，随时随地记录你的想法', type: 'sync' }
]

// --- 2. 构造无限视觉数据 ---
const slides = [...rawSlides, ...rawSlides, ...rawSlides]

// --- 3. 轮播逻辑 ---
const currentIndex = ref(5)
const totalRaw = rawSlides.length
const realIndex = computed(() => currentIndex.value % totalRaw)
let timer = null

const next = () => { currentIndex.value++ }
const goTo = (index) => { currentIndex.value = totalRaw + index }

// --- 4. 移动端适配：动态宽度计算 ---
const slideWidth = ref(1000)
const updateSlideWidth = () => {
  if (typeof window !== 'undefined') {
    // 移动端使用 90vw，桌面端上限 1000px
    slideWidth.value = window.innerWidth < 768 ? window.innerWidth * 0.9 : 1000
  }
}

// --- 5. 打字机效果逻辑 ---
const typewriterText = ref('')
const fullText = '让记录更简单，让知识更加智慧'
let typeIndex = 0

const typeWriter = () => {
  if (typeIndex < fullText.length) {
    typewriterText.value += fullText.charAt(typeIndex)
    typeIndex++
    setTimeout(typeWriter, 100)
  }
}

onMounted(() => {
  const isMobile = typeof window !== 'undefined' && window.innerWidth < 768

  // 初始化 AOS 配置
  AOS.init({
    duration: 1000,
    easing: 'ease-out-quint',
    once: isMobile, // 移动端只触发一次，防止往复滚动时消失
    offset: isMobile ? 20 : 120, // 移动端触发偏移量调小，让内容出现得更早
    delay: 0,
    anchorPlacement: 'top-bottom',
  })

  // 初始化宽度并监听窗口缩放
  updateSlideWidth()
  window.addEventListener('resize', updateSlideWidth)

  timer = setInterval(next, 5000)
  typeWriter()
})

onUnmounted(() => {
  clearInterval(timer)
  window.removeEventListener('resize', updateSlideWidth)
})
</script>

<template>
  <!-- 全局容器 -->
  <div class="w-full bg-white min-h-screen font-sans overflow-x-hidden selection:bg-[#FF4D00] selection:text-white">

    <!-- =========================================================
         Hero 区域
    ========================================================= -->
    <div class="relative w-full bg-no-repeat bg-cover bg-top" style="background-image: url('/images/topbg.png');">
      
      <!-- 柔化遮罩：使用多重渐变层叠实现自然淡出，性能极高且无闪烁 -->
      <div class="absolute bottom-0 left-0 w-full h-40 bg-gradient-to-t from-white via-white/60 to-transparent pointer-events-none z-10"></div>

      <div class="relative z-10">
        <!-- 导航栏 (已移至 Layout) -->

        <!-- Hero 内容 -->
        <section class="max-w-[1100px] mx-auto px-6 pt-32 md:pt-40 pb-32">
          <div class="flex flex-col lg:flex-row items-center gap-10 lg:gap-8">
            <div class="flex-1 space-y-6 text-center lg:text-left">
              <h1 class="text-4xl md:text-[54px] font-extrabold leading-[1.15] tracking-tight text-gray-900">
                <span class="block" data-aos="fade-up" data-aos-delay="200">让<span
                    class="text-[#FF4D00]">笔记</span>更简单</span>
                <span class="block" data-aos="fade-up" data-aos-delay="400">让知识更加<span class="text-[#FF4D00]">智慧</span></span>
              </h1>
              <p class="text-[#4E403B] text-[16px] leading-relaxed max-w-md mx-auto lg:mx-0 font-medium"
                data-aos="fade-up" data-aos-delay="600">
                让笔记更简单、让知识更智慧，开源、本地优先、云同步，多模态记录与 AI 并存，助你高效构建个人与团队的知识系统
              </p>
              <div class="pt-4" data-aos="zoom-in-up" data-aos-delay="800">
                <NuxtLink to="/download"
                  class="inline-block bg-[#FF4D00] text-white px-16 py-2.5 rounded-xl text-[15px] font-bold transition-all duration-500 hover:translate-y-[-4px] border-none shadow-none">
                  免费下载使用
                </NuxtLink>
              </div>
            </div>
            <div class="flex-1 relative w-full max-w-[650px]" data-aos="fade-left" data-aos-duration="1200"
              data-aos-delay="500">
              <div class="relative animate-float-slow">
                <img src="/images/index_right2.png" class="w-full h-auto block border-none shadow-none"
                  alt="Hero Preview" />
              </div>
            </div>
          </div>
        </section>
      </div>
    </div>

    <!-- =========================================================
         轮播 Section
    ========================================================= -->
    <section class="py-24 bg-white overflow-hidden w-full relative z-20">
      <div class="max-w-[1200px] mx-auto px-6 text-center mb-16" data-aos="fade-up">
        <h2 class="text-3xl md:text-[42px] font-bold text-gray-900 tracking-tight">易于使用且功能强大</h2>
      </div>

      <!-- 设置幻灯片宽度 CSS 变量 -->
      <div class="relative w-full" data-aos="fade-up" data-aos-delay="200" :style="{ '--sw': slideWidth + 'px' }">
        <div class="flex transition-transform duration-700 ease-[cubic-bezier(0.25,1,0.5,1)]"
          :style="{ transform: `translateX(calc(50% - (var(--sw) * ${currentIndex} + var(--sw) / 2)))` }">

          <div v-for="(slide, index) in slides" :key="index" class="shrink-0 px-2 md:px-5 transition-all duration-700"
            :style="{ width: 'var(--sw)' }"
            :class="[index === currentIndex ? 'opacity-100 scale-100 z-10' : 'opacity-40 scale-[0.93] blur-[4px]']"
            @click="currentIndex = index">
            <div class="rounded-[1rem] md:rounded-[1.5rem] overflow-hidden bg-transparent border-none shadow-none">
              <img src="/images/intro1.png" class="w-full h-full object-cover object-top border-none shadow-none"
                alt="Feature Preview" />
            </div>
          </div>

        </div>
      </div>

      <div class="flex justify-center mt-12" data-aos="fade-up">
        <div class="bg-[#F5F5F5] rounded-full px-4 py-2 flex items-center gap-3 border-none shadow-none">
          <button v-for="(item, index) in rawSlides" :key="item.id" @click="goTo(index)"
            class="w-8 h-1.5 rounded-full transition-all duration-300"
            :class="[index === realIndex ? 'bg-[#FF4D00] w-12' : 'bg-[#E0E0E0] hover:bg-gray-300']"></button>
        </div>
      </div>
    </section>

    <!-- ================= 黑色背景功能板块 (模块化/多维表/任务/站点) ================= -->

    <!-- 1. 模块化笔记 -->
    <section class="pt-12 pb-20 bg-black text-white w-full">
      <div class="max-w-[1100px] mx-auto px-6">
        <div class="mb-12 text-left" data-aos="fade-right">
          <img src="/images/index/biji.png" alt="模块化笔记" class="h-[38px] md:h-[52px] w-auto mb-6 ml-0" />
          <div class="space-y-1 font-medium leading-snug text-left">
            <p class="text-[16px] md:text-[20px] text-gray-400 mb-2">像搭积木一样编辑笔记</p>
            <p class="text-[26px] text-white font-bold mb-1 hidden md:block">一键插入文字、表格、代码、音视频、网页等</p>
            <p class="text-[26px] text-white font-bold hidden md:block">内容，自由组合，灵活创作</p>
            <!-- 移动端专用的紧凑换行排版 -->
            <p class="text-[18px] text-white font-bold md:hidden leading-tight">一键插入文字、表格、代码、</p>
            <p class="text-[18px] text-white font-bold md:hidden leading-tight">音视频、网页等内容，</p>
            <p class="text-[18px] text-white font-bold md:hidden leading-tight">自由组合，灵活创作</p>
          </div>
        </div>
        <div class="flex justify-center" data-aos="fade-up">
          <img src="/images/index/main/index_main2.png" class="w-full max-w-[720px] h-auto block rounded-xl border-none shadow-none" />
        </div>
      </div>
    </section>

    <!-- 2. 多维表 -->
    <section class="py-16 bg-black text-white w-full">
      <div class="max-w-[1100px] mx-auto px-6">
        <div class="mb-10 text-left" data-aos="fade-right">
          <img src="/images/index/duowei.png" alt="多维表" class="h-[36px] md:h-[48px] w-auto mb-4 ml-0" />
          <div class="space-y-1 font-medium leading-tight text-left">
            <p class="text-[15px] md:text-[18px] text-gray-400 mb-2">支持类似 Notion 的多视图数据库</p>
            <p class="text-[24px] text-white font-bold hidden md:block">用表格、看板、日历等方式查看同一组数据</p>
            <!-- 移动端专用的紧凑换行排版 -->
            <p class="text-[18px] text-white font-bold md:hidden leading-tight">用表格、看板、日历等方式</p>
            <p class="text-[18px] text-white font-bold md:hidden leading-tight">查看同一组数据</p>
          </div>
        </div>
        <div class="flex justify-center" data-aos="fade-up">
          <img src="/images/index/main/index_main3.png" class="w-full max-w-[720px] h-auto block rounded-xl border-none shadow-none" />
        </div>
      </div>
    </section>

    <!-- 3. 任务系统 -->
    <section class="py-16 bg-black text-white w-full">
      <div class="max-w-[1100px] mx-auto px-6 flex flex-col items-center md:items-end">
        <div class="mb-10 text-left md:text-right w-full md:w-auto" data-aos="fade-left">
          <img src="/images/index/mission.png" alt="任务系统" class="h-[38px] md:h-[52px] w-auto mb-6 ml-0 md:ml-auto md:mr-0" />
          <div class="space-y-1 font-medium leading-snug text-left md:text-right">
            <p class="text-[16px] md:text-[20px] text-gray-400 mb-2">内置任务管理功能支持待办事项、多状态、优</p>
            <p class="text-[26px] text-white font-bold hidden md:block">先级、截止日期笔记即项目管理</p>
            <!-- 移动端专用的紧凑换行排版 -->
            <p class="text-[18px] text-white font-bold md:hidden leading-tight">先级、截止日期笔记即项目管理</p>
          </div>
        </div>
        <div class="flex justify-center md:justify-start w-full" data-aos="fade-up">
          <img src="/images/index/main/index_main4.png" class="w-full max-w-[720px] h-auto block rounded-xl border-none shadow-none" />
        </div>
      </div>
    </section>

    <!-- 4. 知识站点 -->
    <section class="py-16 bg-black text-white w-full">
      <div class="max-w-[1100px] mx-auto px-6 flex flex-col items-center md:items-end">
        <div class="mb-10 text-left md:text-right w-full md:w-auto" data-aos="fade-left">
          <img src="/images/index/knowledge.png" alt="知识站点" class="h-[38px] md:h-[52px] w-auto mb-6 ml-0 md:ml-auto md:mr-0" />
          <div class="space-y-1 font-medium leading-snug text-left md:text-right">
            <p class="text-[16px] md:text-[20px] text-gray-400 mb-2">将笔记一键发布为静态网站打造专属知识库或</p>
            <p class="text-[26px] text-white font-bold hidden md:block">项目主页，支持自定义域名和样式</p>
            <!-- 移动端专用的紧凑换行排版 -->
            <p class="text-[18px] text-white font-bold md:hidden leading-tight">项目主页，支持自定义域名和样式</p>
          </div>
        </div>
        <div class="flex justify-center md:justify-start w-full" data-aos="fade-up">
          <img src="/images/intro1.png" class="w-full max-w-[720px] h-auto block rounded-xl border-none shadow-none" />
        </div>
      </div>
    </section>

    <!-- ================= 白色背景手写功能板块 ================= -->
    <section class="py-16 bg-white text-gray-900">
      <div class="max-w-[1220px] mx-auto px-10">
        <div class="flex justify-center items-center gap-0 mb-12" data-aos="flip-up">
          <span
            class="bg-[#FF4D00] text-white px-4 py-1.5 text-[22px] md:text-[28px] font-bold tracking-tight transform hover:rotate-2">独一无二</span>
          <span class="text-[24px] md:text-[32px] font-bold text-gray-900 tracking-tight ml-3">的手写体验</span>
        </div>
        <div class="mb-8" data-aos="fade-up">
          <h2 class="text-[32px] md:text-[48px] font-bold mb-4 text-[#4facfe] tracking-tight">手写笔记</h2>
          <p class="text-[16px] md:text-[20px] text-gray-900 font-bold leading-tight">支持流畅书写与自然笔迹回放，打破键盘限制，模拟纸笔体验</p>
        </div>
        <div class="flex justify-center" data-aos="zoom-in">
          <img src="/images/index/main/index_main5.png" class="w-full max-w-[640px] h-auto block rounded-xl border-none shadow-none" />
        </div>
      </div>
    </section>

    <!-- ================= 一起思维发散 (黑色大圆角) ================= -->
    <section class="py-16 bg-white w-full flex justify-center">
      <div
        class="w-[96%] max-w-[1240px] bg-black text-white rounded-[3.5rem] py-24 overflow-hidden relative shadow-none border-none"
        data-aos="zoom-in-up">
        <div
          class="absolute top-[-20%] right-[-10%] w-[600px] h-[600px] bg-blue-600 opacity-20 blur-[150px] rounded-full animate-pulse-slow">
        </div>
        <div
          class="absolute bottom-[-20%] left-[-10%] w-[600px] h-[600px] bg-purple-600 opacity-20 blur-[150px] rounded-full animate-pulse-slow"
          style="animation-delay: 2s;"></div>
        <div class="max-w-[1000px] mx-auto px-6 relative z-10">
          <div class="text-center mb-28">
            <h2 class="text-[36px] md:text-[56px] font-bold mb-2 md:mb-6 text-white tracking-tight">一起思维发散</h2>
            <p
              class="text-[36px] md:text-[56px] font-bold bg-gradient-to-r from-[#FF8F00] to-[#FF4D00] bg-clip-text text-transparent animate-gradient-x">
              打破页面限制</p>
          </div>
          <!-- 子项 1 -->
          <div class="mb-32 flex flex-col items-start text-left">
            <h3
              class="text-[28px] md:text-[42px] font-bold mb-6 bg-gradient-to-r from-[#3B82F6] to-[#06B6D4] bg-clip-text text-transparent animate-gradient-x"
              data-aos="fade-right">无限白板</h3>
            <div class="text-[16px] md:text-[22px] text-white/90 font-medium mb-10 max-w-2xl" data-aos="fade-right">
              <p>支持手绘直线、箭头、图形，自动矫正为标准图形，适合绘制流程图、思维导图、界面草图</p>
            </div>
            <img src="/images/index/main/index_main6.png" class="w-full h-auto block rounded-xl border-none shadow-none"
              data-aos="fade-up" />
          </div>
          <!-- 子项 2 -->
          <div class="mb-32 flex flex-col items-start text-left">
            <h3
              class="text-[28px] md:text-[42px] font-bold mb-5 bg-gradient-to-r from-[#84CC16] to-[#22C55E] bg-clip-text text-transparent animate-gradient-x"
              data-aos="fade-right">自然书写</h3>
            <div class="text-[16px] md:text-[22px] text-white/90 font-medium mb-10 max-w-xl" data-aos="fade-right">
              <p>兼容 Apple Pencil 和各类手写笔，支持压感与笔迹粗细调节，体验接近真实书写</p>
            </div>
            <img src="/images/index/main/index_main5.png" class="w-full h-auto block rounded-xl border-none shadow-none"
              data-aos="fade-up" />
          </div>
          <!-- 子项 3 -->
          <div class="mb-32 flex flex-col items-end text-right">
            <h3
              class="text-[28px] md:text-[42px] font-bold mb-5 bg-gradient-to-r from-[#F97316] to-[#EF4444] bg-clip-text text-transparent animate-gradient-x"
              data-aos="fade-left">文字编辑</h3>
            <div class="text-[16px] md:text-[22px] text-white/90 font-medium mb-10 max-w-xl" data-aos="fade-left">
              <p>插入文本框，支持富文本格式搭配手绘内容轻松表达逻辑</p>
            </div>
            <img src="/images/index/main/index_main8.png" class="w-full h-auto block rounded-xl border-none shadow-none"
              data-aos="fade-up" />
          </div>
        </div>
      </div>
    </section>

    <!-- ================= AI功能 (白色背景) ================= -->
    <section class="py-24 bg-white w-full overflow-hidden">
      <div class="max-w-[1100px] mx-auto px-6">
        <div class="text-center mb-24" data-aos="fade-up">
          <h2 class="text-[36px] md:text-[54px] font-extrabold text-gray-900 mb-2">AI功能</h2>
          <div class="flex justify-center items-center gap-4 text-[36px] md:text-[54px] font-extrabold">
            <span class="text-gray-900">自选模型</span>
            <span class="bg-[#FF4D00] text-white px-6 py-1">更懂你</span>
          </div>
        </div>
        <div class="grid md:grid-cols-2 gap-20 items-center mb-32">
          <div data-aos="fade-right">
            <h3 class="text-[26px] md:text-[36px] font-bold text-[#3B82F6] mb-6">自由选择大模型</h3>
            <p class="text-[15px] md:text-[18px] text-gray-900 font-bold leading-relaxed">小马笔记支持多种主流大语言模型，按需切换，满足不同场景需求</p>
          </div>
          <div data-aos="fade-left"><img src="/images/index/main/index_main7.png" class="w-full rounded-xl border-none shadow-none" />
          </div>
        </div>
        <div class="grid md:grid-cols-2 gap-20 items-center mb-32">
          <div class="order-2 md:order-1" data-aos="fade-right"><img src="/images/index/main/index_main1.png"
              class="w-full h-auto block rounded-xl border-none shadow-none" /></div>
          <div class="order-1 md:order-2 text-right" data-aos="fade-left">
            <h3 class="text-[26px] md:text-[36px] font-bold text-[#4ADE80] mb-6">AI总结与问答</h3>
            <p class="text-[15px] md:text-[18px] text-gray-900 font-bold leading-relaxed">选中任意笔记内容，一键生成摘要、提炼重点，支持基于上下文的智能问答</p>
          </div>
        </div>
        <div class="grid md:grid-cols-2 gap-20 items-center">
          <div data-aos="fade-right">
            <h3 class="text-[26px] md:text-[36px] font-bold text-[#F97316] mb-6">AI写作助手</h3>
            <p class="text-[15px] md:text-[18px] text-gray-900 font-bold leading-relaxed">输入大纲或灵感碎片，AI 帮你扩写文章、润色表达、生成标题，提升创作效率</p>
          </div>
          <div data-aos="fade-left"><img src="/images/intro1.png" class="w-full rounded-xl border-none shadow-none" />
          </div>
        </div>
      </div>
    </section>

    <!-- ================= 隐私保护 (黑色圆角卡片) ================= -->
    <section class="py-24 bg-white w-full flex justify-center">
      <div
        class="w-[96%] max-w-[1240px] bg-black text-white rounded-[3.5rem] py-32 relative border-none custom-floating-shadow z-[110]"
        data-aos="zoom-in-up">
        <div
          class="absolute top-[-20%] right-[-10%] w-[600px] h-[600px] bg-blue-600 opacity-10 blur-[150px] rounded-full animate-pulse-slow">
        </div>
        <div
          class="absolute bottom-[-20%] left-[-10%] w-[600px] h-[600px] bg-cyan-500 opacity-10 blur-[150px] rounded-full animate-pulse-slow"
          style="animation-delay: 3s;"></div>
        <div class="max-w-[1000px] mx-auto px-6 relative z-10">
          <div class="text-center mb-32">
            <h2 data-aos="fade-up"
              class="text-[36px] md:text-[56px] font-bold bg-gradient-to-r from-[#6EE7B7] via-[#3B82F6] to-[#9333EA] bg-clip-text text-transparent tracking-tight animate-gradient-x">
              专为注重隐私的人打造</h2>
          </div>
          <div class="space-y-40">
            <div class="grid md:grid-cols-2 gap-16 items-center">
              <div data-aos="fade-right">
                <h3 class="text-[26px] md:text-[36px] font-bold mb-6 text-white">本地优先 数据自有</h3>
                <p class="text-[15px] md:text-[18px] text-white/70 font-medium leading-relaxed">默认本地保存，用户完全掌控数据存储位置，无需依赖云端即可使用全部核心功能
                </p>
              </div>
              <div data-aos="fade-left"><img src="/images/index/main/index_main9.png"
                  class="w-full h-auto block rounded-xl border-none shadow-none" /></div>
            </div>
            <div class="grid md:grid-cols-2 gap-16 items-center">
              <div class="order-2 md:order-1" data-aos="fade-right"><img src="/images/index/main/index_main9.png"
                  class="w-full h-auto block rounded-xl border-none shadow-none" /></div>
              <div class="order-1 md:order-2 text-right">
                <h3 data-aos="fade-left" class="text-[26px] md:text-[36px] font-bold mb-6 text-white">全程加密传输</h3>
                <p class="text-[15px] md:text-[18px] text-white/70 font-medium leading-relaxed">采用行业标准 HTTPS + 加密协议，保障笔记在云同步过程中的数据安全
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- ================= API联动 ================= -->
    <!-- <section class="py-24 bg-white w-full overflow-hidden">
      <div class="max-w-[1100px] mx-auto px-6">
        <div class="text-center mb-16" data-aos="fade-up">
          <h2 class="text-[36px] md:text-[56px] font-bold text-[#FF4D00] tracking-tight">利用API让笔记进行联动</h2>
        </div>
        <div class="flex justify-center" data-aos="zoom-in" data-aos-duration="1200">
          <img src="/images/programm.png" class="w-full max-w-[1000px] h-auto block rounded-xl border-none shadow-none"
            alt="API Linkage" />
        </div>
      </div>
    </section> -->

  </div>
</template>

<style>
html {
  scroll-behavior: smooth;
}

body {
  margin: 0;
  padding: 0;
  background: #fff;
  width: 100%;
  overflow-x: hidden;
}
</style>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700;800&display=swap');

h1,
h2,
h3 {
  letter-spacing: -0.03em;
}

.bg-clip-text {
  -webkit-background-clip: text;
  background-clip: text;
}

@keyframes blink {

  0%,
  100% {
    opacity: 1;
  }

  50% {
    opacity: 0;
  }
}

.animate-blink {
  animation: blink 1s step-end infinite;
}

@keyframes float {

  0%,
  100% {
    transform: translateY(0);
  }

  50% {
    transform: translateY(-12px);
  }
}

.animate-float-slow {
  animation: float 6s ease-in-out infinite;
}

@keyframes gradient-x {
  0% {
    background-position: 0% 50%;
  }

  50% {
    background-position: 100% 50%;
  }

  100% {
    background-position: 0% 50%;
  }
}

.animate-gradient-x {
  background-size: 200% 200%;
  animation: gradient-x 6s ease infinite;
}

@keyframes pulse-slow {

  0%,
  100% {
    opacity: 0.2;
    transform: scale(1);
  }

  50% {
    opacity: 0.3;
    transform: scale(1.1);
  }
}

.animate-pulse-slow {
  animation: pulse-slow 8s ease-in-out infinite;
}

.custom-floating-shadow {
  box-shadow: 0 -20px 50px -10px rgba(0, 0, 0, 0.5);
}
</style>