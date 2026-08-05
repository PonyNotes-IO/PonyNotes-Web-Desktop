<script setup>
import { ref, computed } from 'vue'

const props = defineProps({
    isVisible: {
        type: Boolean,
        default: false
    }
})

const emit = defineEmits(['close'])

const api = useApi()
const userStore = useUserStore()
const router = useRouter()

const phoneNumber = ref('')
const agreeTerms = ref(false)
const verificationCode = ref('')
const countdown = ref(0)
const isCodeSent = ref(false)
const loading = ref(false)

const dyLoginUrl = `https://open.douyin.com/platform/oauth/connect?client_key=awwln96o098l1hik&redirect_uri=https://www.xiaomabiji.com/douyin/callback&scope=user_info&state=t${new Date().getTime()}`

const isRegisterButtonActive = computed(() => {
    return phoneNumber.value.trim() !== '' && agreeTerms.value && verificationCode.value.trim() !== ''
})

const countdownText = computed(() => {
    return countdown.value > 0 ? `${countdown.value}s` : '获取验证码'
})

const isValidPhoneOrEmail = computed(() => {
    const value = phoneNumber.value.trim()
    const phoneRegex = /^1[3-9]\d{9}$/
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    return phoneRegex.test(value) || emailRegex.test(value)
})

const sendVerificationCode = async () => {
    if (countdown.value > 0 || !isValidPhoneOrEmail.value) return

    try {
        loading.value = true
        const isPhone = /^1[3-9]\d{9}$/.test(phoneNumber.value.trim())
        
        await api.user.sendCaptcha({
            accountType: isPhone ? 'phone' : 'email',
            account: phoneNumber.value.trim()
            // [isPhone ? 'phone' : 'email']: phoneNumber.value.trim(),
            // loginType: 'code'
        })

        isCodeSent.value = true
        countdown.value = 60

        const timer = setInterval(() => {
            countdown.value--
            if (countdown.value <= 0) {
                clearInterval(timer)
            }
        }, 1000)
    } catch (error) {
        console.error('发送验证码失败:', error)
        alert('发送验证码失败，请稍后重试')
    } finally {
        loading.value = false
    }
}

const handleRegister = async () => {
    if (!isRegisterButtonActive.value) {
        if (!agreeTerms.value) {
            alert('请先阅读并同意用户协议和隐私政策')
        }
        return
    }

    // --- 临时跳过验证逻辑 (仅内存有效，刷新即消失) ---
    // userStore.isLoggedIn.value = true
    // userStore.userInfo.value = { username: '小马用户', phone: '18888888888' }
    // userStore.token.value = 'mock-token-123'
    
    // alert('登录成功 (已跳过验证)')
    // emit('close')
    // router.push('/account')
    // return;
    // -----------------------

    try {
        loading.value = true
        const isPhone = /^1[3-9]\d{9}$/.test(phoneNumber.value.trim())
        
        const loginWithCodeResponse  = await api.user.doCodeLogin({
            accountType: isPhone ? 'phone' : 'email',
            inputValue: phoneNumber.value.trim(),
            code: verificationCode.value.trim()
        });
        console.log(loginWithCodeResponse,'loginWithCodeResponse');
        if(loginWithCodeResponse.code !== 200){
            alert(loginWithCodeResponse.msg || '登录失败，请重试')
            return false;
        }else{
            // 登录成功，存入token到localStorage
            const token = loginWithCodeResponse.token;
            console.log("登录成功，token："+token,loginWithCodeResponse)
            userStore.setUser(loginWithCodeResponse.data, loginWithCodeResponse.token)
               
            localStorage.setItem('authToken',token);
            // alert('登录成功!')
            emit('close')
            router.push('/account')
        }
        
    } catch (error) {
        console.error('登录失败:', error)
        alert('登录失败，请检查验证码是否正确')
    } finally {
        loading.value = false
    }
}

const closeModal = () => {
    phoneNumber.value = ''
    verificationCode.value = ''
    agreeTerms.value = false
    isCodeSent.value = false
    countdown.value = 0
    emit('close')
}

const stopPropagation = (event) => {
    event.stopPropagation()
}

const handleDouyinLogin = async () => {
    try {
        loading.value = true
        const windowWidth = 800
        const windowHeight = 600
        const left = (window.screen.width - windowWidth) / 2
        const top = (window.screen.height - windowHeight) / 2
        const windowFeatures = `width=${windowWidth},height=${windowHeight},left=${left},top=${top},location=no,menubar=no,toolbar=no,resizable=yes,scrollbars=yes`
        window.open(dyLoginUrl, '_blank', windowFeatures)
        // const response = await api.user.douyinLogin()
        
        // if (response.code === 200) {
        //     // 抖音登录成功，存入token到localStorage
        //     const token = response.token
        //     console.log("抖音登录成功，token：" + token, response)
        //     userStore.setUser(response.data, response.token)
            
        //     localStorage.setItem('authToken', token)
        //     emit('close')
        //     router.push('/account')
        // } else {
        //     alert(response.msg || '抖音登录失败，请重试')
        // }
    } catch (error) {
        console.error('抖音登录失败:', error)
        alert('抖音登录失败，请稍后重试')
    } finally {
        loading.value = false
    }
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

                <!-- 验证码输入框和发送按钮 -->
                <div class="flex items-center gap-3 mb-4">
                    <input v-model="verificationCode" type="text" placeholder="输入验证码" maxlength="6"
                        class="flex-1 px-5 py-4 text-[15px] rounded-[12px] bg-[#F5F5F7] border-none focus:ring-1 focus:ring-[#FF4D00]/20 outline-none transition-all placeholder:text-gray-400 font-medium" />
                    <button @click="sendVerificationCode" :disabled="countdown > 0 || !isValidPhoneOrEmail || loading"
                        class="px-6 py-4 text-[15px] font-bold rounded-[12px] transition-all whitespace-nowrap"
                        :class="countdown > 0 || !isValidPhoneOrEmail || loading
                            ? 'bg-[#F5F5F7] text-gray-400 cursor-not-allowed'
                            : 'bg-[#FF4D00] text-white hover:bg-opacity-95'">
                        {{ loading ? '发送中...' : countdownText }}
                    </button>
                </div>

                <!-- 登录注册按钮 -->
                <button @click="handleRegister" :disabled="loading || !isRegisterButtonActive"
                    class="w-full py-3.5 mb-4 rounded-[18px] text-[16px] font-bold transition-all duration-300 border-none shadow-none"
                    :class="isRegisterButtonActive && !loading
                        ? 'bg-[#FF4D00] text-white hover:bg-opacity-95 cursor-pointer shadow-lg shadow-[#FF4D00]/20'
                        : 'bg-[#F5F5F7] text-[#FF4D00] cursor-not-allowed'">
                    {{ loading ? '登录中...' : '登录注册' }}
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

                <button @click="handleDouyinLogin"
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