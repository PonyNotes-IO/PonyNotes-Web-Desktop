<script setup>
import { ref, onMounted } from 'vue'
import AOS from 'aos'
import 'aos/dist/aos.css'
import VerifyIdentityModal from '~/components/VerifyIdentityModal.vue'
import BindEmailModal from '~/components/BindEmailModal.vue'

useSeoMeta({
    title: '账号绑定 - 小马笔记',
})

onMounted(() => {
    AOS.init({
        duration: 1000,
        easing: 'ease-out-quint',
        once: false,
        offset: 50,
        anchorPlacement: 'top-bottom',
    })
})

// 弹窗显示状态
const showVerifyModal = ref(false)
const showBindEmailModal = ref(false)
const currentAction = ref('')

const bindingItems = ref([
    {
        id: 'phone',
        title: '手机号',
        status: '196****1345',
        isBound: true,
        icon: 'check',
        actionText: '更改'
    },
    {
        id: 'email',
        title: '邮箱',
        status: '未绑定，绑定后当你手机号不可用时，可通过邮箱验证更换手机号',
        isBound: false,
        icon: 'warning',
        actionText: '绑定'
    },
    {
        id: 'password',
        title: '账户密码',
        status: '已设置，可通过账户密码登录',
        isBound: true,
        icon: 'check',
        actionText: '更改'
    }
])

// 处理按钮点击
const handleAction = (item) => {
    currentAction.value = item.id

    if (item.id === 'email' && !item.isBound) {
        // 同时显示两个弹窗，左侧是身份验证，右侧是绑定邮箱
        showVerifyModal.value = true
        showBindEmailModal.value = true
    } else {
        // 同时显示两个弹窗，左侧是身份验证，右侧是绑定邮箱
        showVerifyModal.value = true
        showBindEmailModal.value = true
    }
}

// 身份验证成功后的处理
const handleVerifySuccess = (code) => {
    console.log('验证成功，验证码:', code)
    showVerifyModal.value = false
    // 这里可以根据 currentAction.value 执行不同的后续操作
    // 例如：如果是更改手机号，可以打开一个新的弹窗输入新手机号
}

// 绑定邮箱成功后的处理
const handleBindEmailSuccess = (data) => {
    console.log('绑定邮箱成功:', data)
    showBindEmailModal.value = false
    // 更新邮箱绑定状态
    const emailItem = bindingItems.value.find(item => item.id === 'email')
    if (emailItem) {
        emailItem.isBound = true
        emailItem.status = data.email
        emailItem.actionText = '更改'
    }
}
</script>

<template>
    <div class="w-full bg-white min-h-screen font-sans overflow-x-hidden selection:bg-[#FF4D00] selection:text-white">
        <!-- 顶部区域 -->
        <div class="relative w-full bg-no-repeat bg-cover bg-top pb-12 header-bg-mask"
            style="background-image: url('/images/topbg.png');">

            <div
                class="absolute bottom-0 left-0 w-full h-32 bg-gradient-to-t from-white via-white/40 to-transparent pointer-events-none">
            </div>

            <div class="relative z-10">
                <!-- 导航栏 -->
                <nav class="max-w-[1100px] mx-auto px-6 h-20 flex items-center justify-between font-bold">
                    <AppHeader />
                </nav>

                <!-- 标题 -->
                <header class="max-w-[1140px] mx-auto px-6 pt-4">
                    <h1 class="text-[18px] font-bold text-gray-900 tracking-tight max-w-[800px] ml-10 mb-0"
                        data-aos="fade-right">
                        账号绑定
                    </h1>
                </header>
            </div>
        </div>

        <!-- 主要内容区域 -->
        <main class="max-w-[1140px] mx-auto px-6 pb-24 relative z-10">
            <div class="max-w-[800px] ml-10 -mt-4">
                <!-- 安全提示 -->
                <div class="flex items-center gap-3 px-1 mb-6 py-1" data-aos="fade-up">
                    <div class="w-5 h-5 flex items-center justify-center bg-[#FF4D00] rounded-full">
                        <span class="text-white text-[12px] font-bold">!</span>
                    </div>
                    <p class="text-[#888] text-[14px]">你当前的账号安全系数较低，请补充邮箱</p>
                </div>

                <!-- 绑定列表 -->
                <div class="space-y-3">
                    <div v-for="(item, index) in bindingItems" :key="item.id"
                        class="bg-[#F8F8F8] rounded-[16px] px-6 py-4 flex items-center justify-between transition-all hover:shadow-sm"
                        data-aos="fade-up" :data-aos-delay="index * 100">

                        <div class="flex items-center gap-4">
                            <!-- 图标状态 -->
                            <div class="w-5 h-5 flex-shrink-0">
                                <img v-if="item.isBound" src="/images/login/right.png"
                                    class="w-full h-full object-contain" alt="checked" />
                                <div v-else
                                    class="w-full h-full flex items-center justify-center bg-[#FF4D00] rounded-full">
                                    <span class="text-white text-[12px] font-bold">!</span>
                                </div>
                            </div>

                            <!-- 文本内容 -->
                            <div>
                                <h3 class="text-[15px] font-bold text-gray-900 mb-0.5">{{ item.title }}</h3>
                                <p class="text-[13px]" :class="item.isBound ? 'text-gray-500' : 'text-[#AAAAAA]'">
                                    {{ item.status }}
                                </p>
                            </div>
                        </div>

                        <!-- 操作按钮 -->
                        <button @click="handleAction(item)"
                            class="px-6 py-1.5 rounded-lg font-bold text-[13px] transition-all"
                            :class="item.id === 'phone' ? 'bg-[#FF4D00] text-white shadow-sm' : 'bg-white text-gray-900 border border-gray-200'">
                            {{ item.actionText }}
                        </button>
                    </div>
                </div>
            </div>
        </main>

        <!-- 弹窗组件 -->
        <VerifyIdentityModal :isVisible="showVerifyModal" phoneNumber="185******70" @close="showVerifyModal = false"
            @verify="handleVerifySuccess" />

        <BindEmailModal :isVisible="showBindEmailModal" @close="showBindEmailModal = false"
            @bind="handleBindEmailSuccess" />
    </div>
</template>

<style scoped>
.header-bg-mask {
    -webkit-mask-image: linear-gradient(to bottom, #000 70%, transparent 100%);
    mask-image: linear-gradient(to bottom, #000 70%, transparent 100%);
}

/* 针对移动端的简单适配 */
@media (max-width: 640px) {
    .bg-\[#F8F8F8\] {
        flex-direction: column;
        align-items: flex-start;
        gap: 20px;
    }

    button {
        width: 100%;
    }
}
</style>
