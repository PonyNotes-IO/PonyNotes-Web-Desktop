<script setup>
import { ref, computed } from 'vue'

const props = defineProps({
    isVisible: {
        type: Boolean,
        default: false
    }
})

const emit = defineEmits(['close', 'bind'])

const api = useApi()

const email = ref('')
const verificationCode = ref('')
const countdown = ref(0)
const isSending = ref(false)
const loading = ref(false)

const isValidEmail = computed(() => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    return emailRegex.test(email.value)
})

const startCountdown = async () => {
    if (countdown.value > 0 || !isValidEmail.value) return

    try {
        isSending.value = true
        
        await api.user.bindEmail({
            email: email.value.trim(),
            loginType: 'code'
        })

        countdown.value = 60

        const timer = setInterval(() => {
            countdown.value--
            if (countdown.value <= 0) {
                clearInterval(timer)
                isSending.value = false
            }
        }, 1000)
    } catch (error) {
        console.error('发送验证码失败:', error)
        alert('发送验证码失败，请稍后重试')
        isSending.value = false
    }
}

const countdownText = computed(() => {
    return countdown.value > 0 ? `${countdown.value}s` : '重新获取'
})

const handleBind = async () => {
    if (!isValidEmail.value) {
        alert('请输入有效的邮箱地址')
        return
    }
    if (verificationCode.value.trim() === '') {
        alert('请输入验证码')
        return
    }

    try {
        loading.value = true
        
        const res = await api.user.bindEmail({
            email: email.value.trim(),
            code: verificationCode.value.trim(),
            loginType: 'code'
        })

        if (res.code === 200) {
            alert('绑定邮箱成功!')
            emit('bind', { email: email.value })
            handleClose()
        } else {
            alert(res.msg || '绑定失败，请重试')
        }
    } catch (error) {
        console.error('绑定邮箱失败:', error)
        alert('绑定失败，请检查验证码是否正确')
    } finally {
        loading.value = false
    }
}

const handleClose = () => {
    email.value = ''
    verificationCode.value = ''
    countdown.value = 0
    emit('close')
}
</script>

<template>
    <!-- 模态遮罩 -->
    <Transition name="modal">
        <div v-if="isVisible" class="fixed inset-0 z-50 flex items-center justify-center" @click.self="handleClose">

            <!-- 弹窗容器 - 居中定位 -->
            <div class="bg-white rounded-[24px] w-full max-w-[500px] mx-4 p-8 shadow-2xl relative"
                @click.stop>

                <!-- 关闭按钮 -->
                <button @click="handleClose"
                    class="absolute top-6 right-6 w-6 h-6 flex items-center justify-center transition-opacity hover:opacity-70">
                    <img src="/images/login/close.png" class="w-full h-full object-contain" alt="关闭" />
                </button>

                <!-- 标题 -->
                <div class="mb-6">
                    <h2 class="text-[16px] font-bold text-gray-900 mb-4">绑定邮箱</h2>
                    <!-- 灰色分隔线 -->
                    <div class="border-b border-gray-200 -mx-8"></div>
                </div>

                <!-- 邮箱输入 -->
                <div class="mb-4">
                    <input v-model="email" type="email" placeholder="请输入新的邮箱"
                        class="w-full px-4 py-3 text-[15px] rounded-lg bg-gray-50 border border-gray-200 focus:border-[#FF4D00] focus:ring-1 focus:ring-[#FF4D00] outline-none transition-all" />
                </div>

                <!-- 验证码输入 -->
                <div class="mb-6">
                    <div class="flex items-center gap-3">
                        <input v-model="verificationCode" type="text" placeholder="6位短信验证码" maxlength="6"
                            class="flex-1 px-4 py-3 text-[15px] rounded-lg bg-gray-50 border border-gray-200 focus:border-[#FF4D00] focus:ring-1 focus:ring-[#FF4D00] outline-none transition-all" />
                        <button @click="startCountdown" :disabled="countdown > 0 || !isValidEmail"
                            class="px-8 py-3 text-[15px] font-medium rounded-lg transition-all whitespace-nowrap border"
                            :class="countdown > 0 || !isValidEmail
                                ? 'text-gray-400 bg-gray-100 border-gray-200 cursor-not-allowed'
                                : 'text-gray-900 bg-white border-gray-300 hover:bg-gray-50'">
                            {{ countdownText }}
                        </button>
                    </div>
                </div>

                <!-- 底部按钮 -->
                <div class="flex items-center justify-center gap-3">
                    <button @click="handleClose" :disabled="loading"
                        class="px-6 py-2 text-[15px] font-medium text-gray-700 bg-[#F5F5F5] rounded-lg hover:bg-gray-200 transition-all">
                        取消
                    </button>
                    <button @click="handleBind" :disabled="loading"
                        class="px-6 py-2 text-[15px] font-medium text-white bg-[#FF4D00] rounded-lg hover:bg-[#FF4D00]/90 transition-all">
                        {{ loading ? '绑定中...' : '完成' }}
                    </button>
                </div>
            </div>
        </div>
    </Transition>
</template>

<style scoped>
.modal-enter-active,
.modal-leave-active {
    transition: opacity 0.3s ease;
}

.modal-enter-from,
.modal-leave-to {
    opacity: 0;
}

.modal-enter-active .bg-white,
.modal-leave-active .bg-white {
    transition: transform 0.3s ease;
}

.modal-enter-from .bg-white,
.modal-leave-to .bg-white {
    transform: scale(0.9);
}
</style>
