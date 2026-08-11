<script setup>
import { onMounted } from 'vue'
// 引入 AOS 动画库
import AOS from 'aos'
import 'aos/dist/aos.css'

useSeoMeta({
  title: '小马笔记 - 让笔记更简单，让知识更智能',
})

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
                <span class="block" data-aos="fade-up" data-aos-delay="400">让知识更<span class="text-[#FF4D00]">智能</span></span>
              </h1>
              <p class="text-[#4E403B] text-[16px] leading-relaxed max-w-md mx-auto lg:mx-0 font-medium"
                data-aos="fade-up" data-aos-delay="600">
                开源、本地优先、自由云同步，多模态记录融合 AI，高效搭建个人与团队知识系统免费下载使用
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

    <!-- ================= 黑色背景功能板块 (模块化/多维表/任务/站点) ================= -->

    <!-- 1. 模块化笔记 -->
    <section class="pt-12 pb-20 bg-black text-white w-full">
      <div class="max-w-[1100px] mx-auto px-6">
        <div class="mb-12 text-left" data-aos="fade-right">
          <img src="/images/index/biji.png" alt="模块化笔记" class="h-[38px] md:h-[52px] w-auto mb-6 ml-0" />
          <div class="space-y-1 font-medium leading-snug text-left">
            <p class="text-[16px] md:text-[20px] text-gray-400 mb-2">像搭积木一样自由编辑笔记</p>
            <p class="text-[26px] text-white font-bold mb-1">一键插入文字、表格、代码、</p>
            <p class="text-[26px] text-white font-bold">音视频、网页素材，</p>
            <p class="text-[26px] text-white font-bold">自由拼接组合，随心灵活创作</p>
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
            <p class="text-[16px] md:text-[20px] text-gray-400 mb-2">内置完整任务管理体系</p>
            <p class="text-[26px] text-white font-bold">笔记联动日程，搞定个人与团队全流程项目管理</p>
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
            <p class="text-[16px] md:text-[20px] text-gray-400 mb-2">多人实时协同</p>
            <p class="text-[16px] md:text-[20px] text-gray-400 mb-2">将笔记一键发布为静态网站打造专属知识库或</p>
            <p class="text-[26px] text-white font-bold hidden md:block">权限共享体系，支持笔记、多维表格一键分发
多人实时在线协同编辑，团队共建专属知识库。</p>
            <!-- 移动端专用的紧凑换行排版 -->
            <p class="text-[18px] text-white font-bold md:hidden leading-tight">权限共享体系，支持笔记、多维表格一键分发
多人实时在线协同编辑，团队共建专属知识库。</p>
          </div>
        </div>
        <div class="flex justify-center md:justify-start w-full" data-aos="fade-up">
          <img src="/images/index/main/index_knowledge.png" class="w-full max-w-[720px] h-auto block rounded-xl border-none shadow-none" />
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
          <div class="flex flex-col items-start text-left">
            <h3
              class="text-[28px] md:text-[42px] font-bold mb-5 bg-gradient-to-r from-[#84CC16] to-[#22C55E] bg-clip-text text-transparent animate-gradient-x"
              data-aos="fade-right">自然书写</h3>
            <div class="text-[16px] md:text-[22px] text-white/90 font-medium mb-10 max-w-xl" data-aos="fade-right">
              <p>兼容 Apple Pencil 和各类手写笔，支持压感与笔迹粗细调节，体验接近真实书写</p>
            </div>
            <img src="/images/index/main/index_main5.png" class="w-full h-auto block rounded-xl border-none shadow-none"
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