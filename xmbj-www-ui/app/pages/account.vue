<script setup>
import { ref, onMounted } from 'vue'
import AOS from 'aos'
import 'aos/dist/aos.css'
import VerifyIdentityModal from '~/components/VerifyIdentityModal.vue'
import BindEmailModal from '~/components/BindEmailModal.vue'

useSeoMeta({
    title: '账号绑定 - 小马笔记',
})

const api = useApi()
const userStore = useUserStore()

onMounted(() => {
    AOS.init({
        duration: 1000,
        easing: 'ease-out-quint',
        once: false,
        offset: 50,
        anchorPlacement: 'top-bottom',
    })
    
    fetchUserInfo()
})

const showVerifyModal = ref(false)
const showBindEmailModal = ref(false)
const currentAction = ref('')
const loading = ref(false)

const bindingItems = ref([
    {
        id: 'phone',
        title: '手机号',
        status: '未绑定',
        isBound: false,
        icon: 'warning',
        actionText: '绑定'
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
        status: '未设置',
        isBound: false,
        icon: 'warning',
        actionText: '设置'
    }
])

const fetchUserInfo = async () => {
    try {
        console.log(userStore.userInfo.value,'userStore.userInfo')
        loading.value = true
        const isPhone = !!userStore.userInfo.value.phonenumber;
        const res = await api.user.getuserinfo({
            account: userStore.userInfo.value.phonenumber || userStore.userInfo.value.email,
            accountType: isPhone ? 'phone' : 'email'
        })  
        console.log(res,'获取用户信息')
        if (res.code === 200 && res.data) {
            const userData = res.data
            
            if (userData.phonenumber) {
                const phoneItem = bindingItems.value.find(item => item.id === 'phone')
                if (phoneItem) {
                    phoneItem.isBound = true
                    phoneItem.status = userData.phonenumber.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
                    phoneItem.actionText = '更改'
                }
            }
            
            if (userData.email) {
                const emailItem = bindingItems.value.find(item => item.id === 'email')
                if (emailItem) {
                    emailItem.isBound = true
                    emailItem.status = userData.email
                    emailItem.actionText = '更改'
                }
            }
            
            if (userData.password) {
                const passwordItem = bindingItems.value.find(item => item.id === 'password')
                if (passwordItem) {
                    passwordItem.isBound = true
                    passwordItem.status = '已设置，可通过账户密码登录'
                    passwordItem.actionText = '更改'
                }
            }
        }
    } catch (error) {
        console.error('获取用户信息失败:', error)
    } finally {
        loading.value = false
    }
}

const handleAction = (item) => {
    currentAction.value = item.id

    if (item.id === 'phone') {
        showVerifyModal.value = true
        showBindEmailModal.value = false
    } else if (item.id === 'email') {
        showVerifyModal.value = false
        showBindEmailModal.value = true
    }
}

const handleVerifySuccess = (code) => {
    console.log('验证成功，验证码:', code)
    showVerifyModal.value = false
}

const handleBindEmailSuccess = (data) => {
    console.log('绑定邮箱成功:', data)
    showBindEmailModal.value = false
    fetchUserInfo()
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
                <!-- 导航栏 (已移至 Layout) -->

                <!-- 标题 -->
                <header class="max-w-[1140px] mx-auto px-6 pt-24">
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
