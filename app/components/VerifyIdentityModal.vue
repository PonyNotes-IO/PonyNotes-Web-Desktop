<script setup>
import { ref, computed } from 'vue'

const props = defineProps({
    isVisible: {
        type: Boolean,
        default: false
    },
    phoneNumber: {
        type: String,
        default: '185******70'
    }
})

const emit = defineEmits(['close', 'verify'])

const verificationCode = ref('')
const countdown = ref(0)
const isSending = ref(false)

// 倒计时功能
const startCountdown = () => {
    if (countdown.value > 0) return

    countdown.value = 60
    isSending.value = true

    const timer = setInterval(() => {
        countdown.value--
        if (countdown.value <= 0) {
            clearInterval(timer)
            isSending.value = false
        }
    }, 1000)
}

const countdownText = computed(() => {
    return countdown.value > 0 ? `${countdown.value}s` : '重新获取'
})

const handleVerify = () => {
    if (verificationCode.value.trim() === '') {
        alert('请输入验证码')
        return
    }
    emit('verify', verificationCode.value)
}

const handleClose = () => {
    verificationCode.value = ''
    countdown.value = 0
    emit('close')
}
</script>

<template>
    <!-- 模态遮罩 -->
    <Transition name="modal">
        <div v-if="isVisible" class="fixed inset-0 z-50 flex items-center justify-center bg-black/50 backdrop-blur-sm"
            @click.self="handleClose">

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
                    <h2 class="text-[20px] font-bold text-gray-900 mb-2">身份验证</h2>
                    <p class="text-[14px] text-gray-500 mb-4">为了你的账户安全，请先验证身份</p>
                    <!-- 灰色分隔线 -->
                    <div class="border-b border-gray-200 -mx-8"></div>
                </div>

                <!-- 验证方式 -->
                <div class="mb-6 flex items-center justify-between py-3 px-4 bg-gray-50 rounded-lg">
                    <span class="text-[14px] text-gray-700">使用手机{{ phoneNumber }}验证</span>
                    <button class="text-[14px] text-gray-700 font-medium hover:text-gray-900">
                        切换验证
                    </button>
                </div>

                <!-- 验证通过按钮 -->
                <div class="mb-6">
                    <button
                        class="w-full py-3 text-[15px] font-medium text-[#FF4D00] bg-white border border-gray-200 rounded-lg hover:bg-gray-50 transition-all">
                        验证通过
                    </button>
                </div>

                <!-- 验证码输入 -->
                <div class="mb-6">
                    <div class="flex items-center gap-3">
                        <input v-model="verificationCode" type="text" placeholder="请输入验证码" maxlength="6"
                            class="flex-1 px-4 py-3 text-[15px] rounded-lg bg-gray-50 border border-gray-200 focus:border-[#FF4D00] focus:ring-1 focus:ring-[#FF4D00] outline-none transition-all" />
                        <button @click="startCountdown" :disabled="countdown > 0"
                            class="px-6 py-3 text-[15px] font-medium rounded-lg transition-all whitespace-nowrap border"
                            :class="countdown > 0
                                ? 'text-gray-400 bg-gray-100 border-gray-200 cursor-not-allowed'
                                : 'text-gray-700 bg-white border-gray-300 hover:bg-gray-50'">
                            {{ countdownText }}
                        </button>
                    </div>
                    <p class="text-[13px] text-gray-400 mt-2">已发送短信验证码到绑定手机</p>
                </div>

                <!-- 底部按钮 -->
                <div class="flex items-center justify-center gap-3">
                    <button @click="handleClose"
                        class="px-6 py-2 text-[15px] font-medium text-gray-700 bg-[#F5F5F5] rounded-lg hover:bg-gray-200 transition-all">
                        取消
                    </button>
                    <button @click="handleVerify"
                        class="px-6 py-2 text-[15px] font-medium text-white bg-[#FF4D00] rounded-lg hover:bg-[#FF4D00]/90 transition-all">
                        完成
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
