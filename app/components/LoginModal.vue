<script setup>
import { ref, computed, watch } from 'vue'

const props = defineProps({
    isVisible: {
        type: Boolean,
        default: false
    }
})

const emit = defineEmits(['close'])

const phoneNumber = ref('')
const agreeTerms = ref(false)

// 动态计算按钮是否激活
const isRegisterButtonActive = computed(() => {
    // 检查非空且同意条款
    return phoneNumber.value.trim() !== '' && agreeTerms.value
})

const handleRegister = () => {
    if (isRegisterButtonActive.value) {
        console.log(`注册/登录请求: ${phoneNumber.value}`)
        emit('close')
    }
}

const closeModal = () => {
    emit('close')
}

// 阻止事件冒泡，防止点击模态框内容时关闭
const stopPropagation = (event) => {
    event.stopPropagation()
}
</script>

<template>
    <!-- 使用 Teleport 将弹窗渲染到 body 下，解决父元素 transform 导致的 fixed 定位失效问题 -->
    <Teleport to="body">
        <!-- 模态框背景遮罩：z-[9999] 确保优先级最高 -->
        <!-- 绝对使用 fixed inset-0 确保在视口中央，不随滚动条移动 -->
        <div v-if="props.isVisible" @click="closeModal"
            class="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-[9999]">

            <!-- 模态框内容区 (像素级精调：宽矮比例 & 精致圆角) -->
            <div @click="stopPropagation"
                class="bg-white rounded-[32px] w-[90%] max-w-[520px] px-12 py-8 relative shadow-2xl overflow-hidden">

                <!-- 头部 Logo 和关闭按钮 -->
                <div class="flex justify-between items-start mb-5">
                    <div class="flex items-center gap-2">
                        <img src="/images/login/login_ico.png" class="w-7 h-7 rounded-md" alt="小马笔记 Logo" />
                        <span class="text-[17px] font-bold text-gray-900">小马笔记</span>
                    </div>
                    <button @click="closeModal" class="transition-opacity hover:opacity-70 absolute top-6 right-6 p-2">
                        <img src="/images/login/close.png" class="w-[18px] h-auto object-contain" alt="关闭" />
                    </button>
                </div>

                <!-- 输入框 (精调圆角) -->
                <input v-model="phoneNumber" type="text" placeholder="输入邮箱或手机号"
                    class="w-full px-5 py-4 mb-4 text-[15px] rounded-[12px] bg-[#F5F5F7] border-none focus:ring-1 focus:ring-[#FF4D00]/20 outline-none transition-all placeholder:text-gray-400 font-medium" />

                <!-- 登录注册按钮 (颜色逻辑优化：勾选协议即变色) -->
                <button @click="handleRegister" :disabled="!isRegisterButtonActive"
                    class="w-full py-3.5 mb-4 rounded-[18px] text-[16px] font-bold transition-all duration-300 border-none shadow-none"
                    :class="agreeTerms
                        ? 'bg-[#FF4D00] text-white hover:bg-opacity-95 cursor-pointer shadow-lg shadow-[#FF4D00]/20'
                        : 'bg-[#F5F5F7] text-[#FF4D00] cursor-not-allowed'">
                    登录注册
                </button>

                <!-- 服务协议复选框 -->
                <div class="flex items-center justify-center text-[13px] mb-8 group">
                    <div @click="agreeTerms = !agreeTerms" class="flex items-center cursor-pointer select-none">
                        <!-- 自定义 Checkbox 图标容器 -->
                        <div class="w-[16px] h-[16px] flex-shrink-0 mr-2 flex items-center justify-center">
                            <!-- 已勾选状态 (橙色图标) -->
                            <img v-if="agreeTerms" src="/images/login/logincheck.png"
                                class="w-full h-full object-contain" alt="checked" />
                            <!-- 未勾选状态 (极细灰色圆圈) -->
                            <div v-else
                                class="w-[14px] h-[14px] rounded-full border-[1px] border-gray-300 group-hover:border-gray-400 transition-colors">
                            </div>
                        </div>

                        <span class="text-gray-500">
                            我已阅读并同意
                            <a href="#" @click.stop class="text-[#FF4D00] hover:underline font-medium">《用户协议》</a>
                            与
                            <a href="#" @click.stop class="text-[#FF4D00] hover:underline font-medium">《隐私政策》</a>
                        </span>
                    </div>
                </div>


                <!-- 社交登录按钮 (还原为圆角胶囊样式) -->
                <button
                    class="w-full py-3.5 mb-4 rounded-full border border-[#EEEEEE] bg-white text-gray-800 text-[15px] font-bold flex items-center justify-center gap-3 hover:bg-[#F9F9F9] transition-colors shadow-none">
                    <img src="/images/login/weixin.png" class="h-6 w-6" alt="微信登录" />
                    微信登录
                </button>

                <button
                    class="w-full py-3.5 rounded-full border border-[#EEEEEE] bg-white text-gray-800 text-[15px] font-bold flex items-center justify-center gap-3 hover:bg-[#F9F9F9] transition-colors shadow-none">
                    <img src="/images/login/douyin.png" class="h-6 w-6" alt="抖音登录" />
                    抖音登录
                </button>

            </div>
        </div>
    </Teleport>
</template>

<style scoped>
/* 模态框背景色和复选框 accent 颜色 */
.bg-opacity-60 {
    background-color: rgba(0, 0, 0, 0.6);
}
</style>