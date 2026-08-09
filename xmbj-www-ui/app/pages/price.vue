<script setup>
import { ref, onMounted, nextTick, watch } from 'vue'
// 引入 AOS 动画库
import AOS from 'aos'
import Toast from '../utils/toast'
import 'aos/dist/aos.css'

useSeoMeta({
    title: '价格方案 - 小马笔记',
})
const api = useApi()
const router = useRouter()
const route = useRoute()
// 计费周期切换：0为月付，1为年付
const billingCycle = ref(0)
const alipayFormContainer = ref(null);
const loading = ref(false);
const loadingText = ref('订单生成中...');
// 选中的计划索引：0免费, 1学生, 2标准, 3团队
const selectedPlan = ref(0)
const plans = ref([]);
const alipayHtml = ref('');



const initPlans = () => {
    api.payment.planLists().then(res => {
        console.log('获取价格计划列表',res);
        if (res.code === 200) {
            plans.value = res.rows || [];
        }
    })
};

const handleSelectPlan = (index) => {
    selectedPlan.value = index;
    // alert(`您选择了 ${plans.value[index].planNameCn}`);
    loading.value = true;
    setTimeout(() => {
        loading.value = false;
        router.push({
            path: '/download',
            query: {
                planId: plans.value[index].id,
                billingType: billingCycle.value,
            }
        })
    }, 300);
    // api.payment.createPayment({
    //     planId: plans.value[index].id,
    //     billingType: billingCycle.value,
    //     userInfo: '',
    //     'paymentType':'alipay',
    // }).then(res => {
    //     console.log('创建支付订单',alipayFormContainer.value,alipayFormContainer,res);
    //     proccessResult(res);
    // }).finally(() => {
    //     loading.value = false;
    // })
}
const proccessResult = res => {
    if (res.code === 200) {
        alipayHtml.value = res.data.payUrl;
        // 使用nextTick确保DOM更新后再获取表单
        nextTick(() => {
            const form = alipayFormContainer.value?.querySelector("form");

            if (form) {
                loadingText.value = '即将跳转支付页面...';
                setTimeout(() => {
                    form.submit(); // 自动提交表单
                }, 500);
            } else {
                console.error("未找到支付宝返回的表单", res.data?.payUrl);
                Toast.error('支付页面生成失败，请联系客服');
            }
        });
        // router.push(res.data.paymentUrl)
    } else {
        Toast.error(res.msg || '创建订单失败，请稍后重试');
    }
}


// Toast工具已直接导入，可通过 Toast.success() 等方法调用
const openApp = ()=> {
    var iframe = document.createElement('iframe');
    const appUrl = `ponynotes://payment-success`;
    iframe.style.display = 'none';
    iframe.src = appUrl;
    document.body.appendChild(iframe);

    // 尝试唤起应用
    // window.location.href = appUrl;
    // 设置超时时间（例如500ms）
    const timeout = 1000;
    const downloadUrl = location.origin + '/download';
    const timer = setTimeout(() => {
        // 超时后执行回退逻辑（如跳转到下载页）
        Toast.warning('打开App超时，请检查是否已安装PonyNotes,即将跳转下载页面');
        setTimeout(() => {
            window.location.href = downloadUrl;
        }, 2500);
    }, timeout);

    // 如果页面失去焦点（应用成功打开），则清除超时
    window.onblur = function() {
        clearTimeout(timer);
    };
};

const proccessPaymentResult = res => {
    if (res.code === 200 && res.data == 'success') {
        Toast.success('支付成功');
        openApp();
        // alipayHtml.value = res.data.payUrl;
    }
}
const formatStorage = (gb) => {
    if (gb >= 1024 *1024) {
        return (gb / 1024 / 1024).toFixed(2) + 'TB';
    } else if (gb >= 1024) {
        return (gb / 1024).toFixed(2) + 'GB';
    } else {
        return gb.toFixed(2) + 'MB';
    }
}
const isValidBillingType = (val) => {
    const num = Number(val);
    return !isNaN(num) && (num === 0 || num === 1);
};

let paymentInited = false;
const handleCreatePayment = () => {
    if (paymentInited) return false;
    const query = route.query;
    const planId = Number(query.planId);
    const billingType = Number(query.billingType);
    const userInfo = query.userInfo;

    // query 尚未就绪（hydration 早期），静默跳过，等待 watch 触发
    if (!planId || isNaN(planId) || !isValidBillingType(billingType) || !userInfo) {
        return false;
    }
    paymentInited = true;

    loading.value = true;
    api.payment.createPayment({
        planId,
        billingType,
        userInfo: String(userInfo),
        paymentType: 'alipay',
    }).then(res => {
        console.log('创建支付订单', alipayFormContainer.value, alipayFormContainer, res);
        proccessResult(res);
    }).catch(err => {
        console.error('创建支付订单失败:', err);
        paymentInited = false;
        Toast.error('创建订单失败，请稍后重试');
    }).finally(() => {
        loading.value = false;
    });
    return true;
};

let paymentResultInited = false;
const handlePaymentResult = () => {
    if (paymentResultInited) return false;
    const query = route.query;
    const orderNo = query.orderNo;
    const paymentType = query.paymentType;
    const status = query.status;

    if (!orderNo || !paymentType || !status) {
        return false;
    }
    paymentResultInited = true;

    loadingText.value = '查询订单支付结果中...';
    loading.value = true;
    api.payment.paymentStatus(String(orderNo)).then(res => {
        console.log('查询支付订单', res);
        proccessPaymentResult(res);
    }).catch(err => {
        console.error('查询支付结果失败:', err);
        Toast.error('查询支付结果失败，请刷新页面重试');
    }).finally(() => {
        loading.value = false;
    });
    return true;
};

onMounted(() => {
    AOS.init({
        duration: 1000,
        easing: 'ease-out-quint',
        once: false,
        offset: 50,
        anchorPlacement: 'top-bottom',
    });

    handlePaymentResult();
    handleCreatePayment();
    initPlans();

    // 静态站点 hydration 时，route.query 可能在 onMounted 之后才被解析，
    // 这里监听 query 变化，确保参数就绪后能触发下单。
    watch(
        () => route.query,
        () => {
            handlePaymentResult();
            handleCreatePayment();
        },
        { deep: true }
    );
})
</script>

<template>
    <!-- 全局容器 -->
    <div class="w-full bg-white min-h-screen font-sans overflow-x-hidden selection:bg-[#FF4D00] selection:text-white">

        <!-- 加载提示 -->
        <div v-if="loading" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-[9999]">
            <div class="bg-white rounded-2xl px-8 py-6 flex flex-col items-center gap-4 shadow-2xl">
                <div class="w-12 h-12 border-4 border-[#FF4D00] border-t-transparent rounded-full animate-spin"></div>
                <span class="text-gray-900 font-medium text-[16px]">{{ loadingText }}</span>
            </div>
        </div>

        <!-- =========================================================
             顶部区域 (底部边缘自然淡出背景)
        ========================================================= -->
        <div class="relative w-full bg-no-repeat bg-cover bg-top pb-64 header-bg-mask"
            style="background-image: url('/images/topbg.png');">

            <div
                class="absolute bottom-0 left-0 w-full h-32 bg-gradient-to-t from-white via-white/40 to-transparent pointer-events-none">
            </div>

            <div class="relative z-10">
                <!-- 导航栏 (已移至 Layout) -->

                <!-- 主标题区域 (主标题居中) -->
                <header class="max-w-[1140px] mx-auto px-6 pt-32">
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
                <div v-for="(plan, index) in plans" :key="index">
                    <div @mouseenter="selectedPlan = index"
                        :class="[selectedPlan === index ? 'border-2 border-[#FF4D00]' : 'border border-[#FFD9C6]']"
                        class="bg-white rounded-2xl p-5 pb-6 flex flex-col h-full shadow-none cursor-pointer transition-all duration-300">
                        <div class="h-24 mt-1">
                            <h3 class="text-[16px] font-medium text-gray-900 mb-1">{{ plan.planNameCn }}</h3>
                            <div class="flex items-baseline gap-0.5">
                                <span class="text-[14px] font-bold">¥</span>
                                <span class="text-[34px] font-bold leading-none text-gray-900">{{billingCycle === 0 ? plan.monthlyPriceYuan : plan.yearlyPriceYuan}}</span>
                            </div>
                        </div>
                        <button @click="handleSelectPlan(index)"
                            :class="[selectedPlan === index ? 'bg-[#FF4D00] text-white' : 'bg-[#F3F4F6] text-gray-600']"
                            class="w-full py-2.5 rounded-xl font-bold mb-6 border-none shadow-none text-[14px] transition-colors duration-300">立即体验</button>
                        <ul v-if="plan.planCode === 'standard'" :class="[selectedPlan === 2 ? 'text-gray-950 font-bold' : 'text-gray-400 font-medium']"
                            class="space-y-2.5 text-[13px] px-1 leading-snug transition-colors duration-300">
                            <li>学生与职场人的智能知识助手</li>
                            <li>适合学习、工作与日常效率提升</li>
                            <li>AI 智能笔记，高效整理信息</li>
                            <li>10GB 云同步空间，多端随时访问</li>
                            <li>快速记录、搜索与管理个人知识让每一次记录，都成为未来的价值</li>
                        </ul>
                        <ul v-if="plan.planCode === 'profersor'" :class="[selectedPlan === 2 ? 'text-gray-950 font-bold' : 'text-gray-400 font-medium']"
                            class="space-y-2.5 text-[13px] px-1 leading-snug transition-colors duration-300">
                            <li>内容创作者的 AI 创作工作台</li>
                            <li>适合OPC、创作者与知识工作者</li>
                            <li>无限灵感记录，承载大量内容创作</li>
                            <li>50GB 云同步空间，随时随地创作</li>
                            <li>写作、整理、归档一站完成从灵感捕捉到内容发布，让创作更高效</li>
                        </ul>
                        <ul v-if="plan.planCode === 'mfb'" :class="[selectedPlan === 0 ? 'text-gray-950 font-bold' : 'text-gray-400 font-medium']"
                            class="space-y-2.5 text-[13px] px-1 transition-colors duration-300">
                            <li>开启你的 AI 智能笔记之旅</li>
                            <li>适合个人体验与轻量记录</li>
                            <li>本地免费使用，数据掌握在自己手中</li>
                            <li>AI 图文生成，快速记录灵感</li>
                            <li>基础知识库管理，构建个人信息中心随时记录，让知识开始沉淀</li>
                        </ul>
                        <ul v-if="plan.planCode === 'hiclass'" :class="[selectedPlan === 3 ? 'text-gray-950 font-bold' : 'text-gray-400 font-medium']"
                        class="space-y-2.5 text-[13px] px-1 leading-snug transition-colors duration-300">
                            <li>团队协作的智能知识空间</li>
                            <li>适合团队、工作室与企业使用</li>
                            <li>专属权限管理，保障团队知识安全</li>
                            <li>共享工作区，高效协同创作</li>
                            <li>大容量云空间，统一沉淀团队资产</li>
                            <li>AI 能力赋能团队内容生产不只是共享文件，更是共同构建团队智慧</li>
                        </ul>
                        <ul :class="[selectedPlan === index ? 'text-gray-950 font-bold' : 'text-gray-400 font-medium']"
                            class="space-y-2.5 text-[13px] px-1 transition-colors duration-300">
                            <li v-for="feature in plan.features" :key="feature">{{ feature }}</li>
                        </ul>
                    </div>

                </div>
                <!-- 免费版 -->
                <!-- <div @mouseenter="selectedPlan = 0"
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
                        :class="[selectedPlan === 0 ? 'bg-[#FF4D00] text-white' : 'bg-[#F3F4F6] text-gray-600']"
                        class="w-full py-2.5 rounded-xl font-bold mb-6 border-none shadow-none text-[14px] transition-colors duration-300">立即开始</button>
                    <ul :class="[selectedPlan === 0 ? 'text-gray-950 font-bold' : 'text-gray-400 font-medium']"
                        class="space-y-2.5 text-[13px] px-1 transition-colors duration-300">
                        <li>本地免费使用</li>
                        <li>你的随身知识库系统</li>
                        <li>AI图文生成 + 云存储</li>
                        <li>开启智能学习体验</li>
                    </ul>
                </div> -->

                <!-- 学生版 -->
                <!-- <div @mouseenter="selectedPlan = 1"
                    :class="[selectedPlan === 1 ? 'border-2 border-[#FF4D00]' : 'border border-[#FFD9C6]']"
                    class="bg-white rounded-2xl p-5 pb-6 flex flex-col h-full shadow-none cursor-pointer transition-all duration-300">
                    <div class="h-24 mt-1">
                        <h3 class="text-[16px] font-medium text-gray-900 mb-1">学生版</h3>
                        <div class="flex items-baseline gap-0.5">
                            <span class="text-[14px] font-bold">¥</span>
                            <span class="text-[34px] font-bold leading-none text-gray-900">{{ billingCycle === 0 ? '3' : '30' }}</span>
                            <span class="text-[#FF4D00] text-[12px] font-bold ml-1">每月2G存储空间</span>
                        </div>
                    </div>
                    <button
                        :class="[selectedPlan === 1 ? 'bg-[#FF4D00] text-white' : 'bg-[#F3F4F6] text-gray-600']"
                        class="w-full py-2.5 rounded-xl font-bold mb-6 border-none shadow-none text-[14px] transition-colors duration-300">去升级</button>
                    <ul :class="[selectedPlan === 1 ? 'text-gray-950 font-bold' : 'text-gray-400 font-medium']"
                        class="space-y-2.5 text-[13px] px-1 leading-snug transition-colors duration-300">
                        <li>轻装上阵，AI助学</li>
                        <li>从学生版开始小投入</li>
                        <li>大升级学习效率跃升一步!</li>
                        <li>AI图文生成 + 云存储</li>
                        <li>开启智能学习体验</li>
                    </ul>
                </div> -->

                <!-- 标准版 -->
                <!-- <div @mouseenter="selectedPlan = 2"
                    :class="[selectedPlan === 2 ? 'border-2 border-[#FF4D00]' : 'border border-[#FFD9C6]']"
                    class="bg-white rounded-2xl p-5 pb-6 flex flex-col h-full shadow-none cursor-pointer transition-all duration-300">
                    <div class="h-24 mt-1">
                        <h3 class="text-[16px] font-medium text-gray-900 mb-1">标准版</h3>
                        <div class="flex items-baseline gap-0.5">
                            <span class="text-[14px] font-bold">¥</span>
                            <span class="text-[34px] font-bold leading-none text-gray-900">{{ billingCycle === 0 ? '8' : '80' }}</span>
                            <span class="text-[#FF4D00] text-[12px] font-bold ml-1">每月10G存储空间</span>
                        </div>
                    </div>
                    <button
                        :class="[selectedPlan === 2 ? 'bg-[#FF4D00] text-white' : 'bg-[#F3F4F6] text-gray-600']"
                        class="w-full py-2.5 rounded-xl font-bold mb-6 border-none shadow-none text-[14px] transition-colors duration-300">去升级</button>
                    <ul :class="[selectedPlan === 2 ? 'text-gray-950 font-bold' : 'text-gray-400 font-medium']"
                        class="space-y-2.5 text-[13px] px-1 leading-snug transition-colors duration-300">
                        <li>面向内容创作者</li>
                        <li>与高效工作者的智能笔记</li>
                        <li>空间丰富AI+10GB云同步</li>
                        <li>助你随时记录、随处创作</li>
                        <li>一站整合写作、整理、归档</li>
                    </ul>
                </div> -->

                <!-- 团队版 -->
                <!-- <div @mouseenter="selectedPlan = 3"
                    :class="[selectedPlan === 3 ? 'border-2 border-[#FF4D00]' : 'border border-[#FFD9C6]']"
                    class="bg-white rounded-2xl p-5 pb-6 flex flex-col h-full shadow-none cursor-pointer transition-all duration-300">
                    <div class="h-24 mt-1">
                        <h3 class="text-[16px] font-medium text-gray-900 mb-1">团队版</h3>
                        <div class="flex items-baseline gap-0.5">
                            <span class="text-[14px] font-bold">¥</span>
                            <span class="text-[34px] font-bold leading-none text-gray-900">{{ billingCycle === 0 ? '18' : '180' }}</span>
                            <span class="text-[#FF4D00] text-[12px] font-bold ml-1">每月20G存储空间</span>
                        </div>
                    </div>
                    <button
                        :class="[selectedPlan === 3 ? 'bg-[#FF4D00] text-white' : 'bg-[#F3F4F6] text-gray-600']"
                        class="w-full py-2.5 rounded-xl font-bold mb-6 border-none shadow-none text-[14px] transition-colors duration-300">去升级</button>
                    <ul :class="[selectedPlan === 3 ? 'text-gray-950 font-bold' : 'text-gray-400 font-medium']"
                        class="space-y-2.5 text-[13px] px-1 leading-snug transition-colors duration-300">
                        <li>团队协作不止是共享</li>
                        <li>更是高效共创</li>
                        <li>专属权限+共享工作区</li>
                        <li>大容量云空间轻松协同办公</li>
                        <li>配套AI能力为团队注入内容</li>
                    </ul>
                </div> -->
            </div>
        </section>





        <!-- ================= 空间补充包 (调整为扁平化比例) ================= -->
         <!-- 调整 py-6 缩短高度，mb 缩短间距 -->
        <!-- <section class="max-w-[1140px] mx-auto px-6 mt-16 text-left">
            <div class="max-w-[950px] ml-0">
                <div class="mb-6" data-aos="fade-right">
                    <span
                        class="border border-[#FFD9C6] text-[#FF4D00] px-5 py-1 rounded-full font-medium text-[13px]">空间补充包</span>
                </div>
                <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
                    
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
        </section> -->

        <!-- ================= 小马AI对话 (调整为扁平化比例) ================= -->
        <!-- <section class="max-w-[1140px] mx-auto px-6 mt-16 text-left">
            <div class="max-w-[950px] ml-0">
                <div class="mb-6" data-aos="fade-right">
                    <span
                        class="border border-[#FFD9C6] text-[#FF4D00] px-5 py-1 rounded-full font-medium text-[13px]">小马AI对话</span>
                </div>
                <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
                    
                    <div class="bg-white rounded-2xl py-6 px-8 border border-[#FFD9C6] text-center shadow-none"
                        data-aos="fade-up" data-aos-delay="100">
                        <p class="text-[15px] font-bold text-gray-900 mb-2">基础-500次</p>
                        <div class="text-[28px] font-bold mb-5 text-gray-900 leading-none">¥ 10</div>
                        <button
                            class="w-full bg-[#E5E7EB] text-gray-900 py-2.5 rounded-xl font-bold border-none shadow-none text-sm">购买</button>
                    </div>
                    <div class="bg-white rounded-2xl py-6 px-8 border border-[#FFD9C6] text-center shadow-none"
                        data-aos="fade-up" data-aos-delay="200">
                        <p class="text-[15px] font-bold text-gray-900 mb-2">标准-1500次</p>
                        <div class="text-[28px] font-bold mb-5 text-gray-900 leading-none">¥ 30</div>
                        <button
                            class="w-full bg-[#E5E7EB] text-gray-900 py-2.5 rounded-xl font-bold border-none shadow-none text-sm">购买</button>
                    </div>
                    <div class="bg-white rounded-2xl py-6 px-8 border border-[#FFD9C6] text-center shadow-none"
                        data-aos="fade-up" data-aos-delay="300">
                        <p class="text-[15px] font-bold text-gray-900 mb-2">高级-3000次</p>
                        <div class="text-[28px] font-bold mb-5 text-gray-900 leading-none">¥ 58</div>
                        <button
                            class="w-full bg-[#E5E7EB] text-gray-900 py-2.5 rounded-xl font-bold border-none shadow-none text-sm">购买</button>
                    </div>
                </div>
            </div>
        </section> -->

        <!-- ================= 方案与功能详细对比 (全量手动展开) ================= -->
        <section class="max-w-[1100px] mx-auto px-6 mt-32">
            <h2 class="text-[42px] font-bold text-gray-900 mb-12" data-aos="fade-right">方案与功能</h2>

            <div class="flex flex-col gap-1.5" data-aos="fade-up">
                <!-- 头部 -->
                <div
                    class="grid grid-cols-5 bg-[#F5F5F7] py-4 px-6 rounded-2xl items-center text-[16px] font-bold text-gray-900">
                    <div></div>
                    <div  v-for="(plan, index) in plans" :key="index" class="text-center">{{plan.planNameCn}}</div>
                </div>
                <!-- 基础项 -->
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">页面/块数量</div>
                    <div v-for="(plan, index) in plans" :key="index" class="text-center text-gray-500">无限制</div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">云存储空间</div>
                    <div v-for="(plan, index) in plans" :key="index" class="text-center text-gray-500">{{formatStorage(plan.cloudStorageGb)}}</div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">导入与导出</div>
                    <div v-for="(plan, index) in plans" :key="index" class="flex justify-center">
                        <div v-if="!plan.hasInbox" class="text-center text-gray-500">—</div>
                        <div v-else class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    </div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">收件箱</div>
                    <div v-for="(plan, index) in plans" :key="index" class="flex justify-center">
                        <div v-if="!plan.hasInbox" class="text-center text-gray-500">—</div>
                        <div v-else class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    </div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">多端同步(iPad、Mac、Windows、Web)</div>
                    <div class="flex justify-center" v-for="(plan, index) in plans" :key="index">
                        <div v-if="!plan.hasMultiDeviceSync" class="text-center text-gray-500">—</div>
                        <div v-else class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    </div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">支持 API</div>
                    <div class="flex justify-center" v-for="(plan, index) in plans" :key="index">
                        <div v-if="!plan.hasApiSupport" class="text-center text-gray-500">—</div>
                        <div v-else class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    </div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">版本历史</div>
                    <div class="flex justify-center" v-for="(plan, index) in plans" :key="index">
                        <div v-if="plan.planCode == 'mfb'" class="text-center text-gray-500">{{plan.versionHistoryDays}}</div>
                        <div v-else class="text-center text-gray-900">{{plan.versionHistoryDays}}天</div>
                    </div>
                </div>

                <!-- AI 分类 -->
                <div class="mt-6 mb-2 px-2 text-[#FF4D00] font-bold text-[16px]">AI功能</div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">AI对话</div>
                    <div class="flex justify-center" v-for="(plan, index) in plans" :key="index">
                        <div v-if="plan.aiChatCountPerMonth == 0" class="text-center text-gray-500">—</div>
                        <div v-else class="text-center text-gray-900">{{plan.aiChatCountPerMonth}}次/月</div>
                    </div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">图片生成</div>
                    <div class="flex justify-center" v-for="(plan, index) in plans" :key="index">
                        <div v-if="plan.aiImageGenerationPerMonth == 0" class="text-center text-gray-500">—</div>
                        <div v-else class="text-center text-gray-900">{{plan.aiImageGenerationPerMonth}}次/月</div>
                    </div>
                </div>

                <!-- 协作 分类 -->
                <div class="mt-6 mb-2 px-2 text-[#FF4D00] font-bold text-[16px]">共享和协作</div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">分享链接</div>
                    <div class="flex justify-center" v-for="(plan, index) in plans" :key="index">
                        <div v-if="!plan.hasShareLink" class="text-center text-gray-500">—</div>
                        <div v-else class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    </div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">发布</div>
                    <div class="flex justify-center" v-for="(plan, index) in plans" :key="index">
                        <div v-if="!plan.hasPublish" class="text-center text-gray-500">—</div>
                        <div v-else class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    </div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">工作区成员</div>
                    <div class="flex justify-center" v-for="(plan, index) in plans" :key="index">
                        <div v-if="plan.workspaceMemberLimit == 0" class="text-center text-gray-500">—</div>
                        <div v-else class="text-center text-gray-900">{{plan.workspaceMemberLimit}}</div>
                    </div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">协作工作区</div>
                    <div class="flex justify-center" v-for="(plan, index) in plans" :key="index">
                        <div v-if="plan.collaborativeWorkspaceLimit == 0" class="text-center text-gray-500">—</div>
                        <div v-else class="text-center text-gray-900">{{plan.collaborativeWorkspaceLimit}}个</div>
                    </div>
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
                    <div class="flex justify-center" v-for="(plan, index) in plans" :key="index">
                        <div v-if="!plan.hasSpaceMemberManagement" class="text-center text-gray-500">—</div>
                        <div v-else class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    </div>
                </div>
                <div class="grid grid-cols-5 bg-[#F5F5F7] py-3.5 px-6 rounded-2xl items-center text-[14.5px]">
                    <div class="text-gray-900 font-medium">空间成员分组</div>
                    <div class="flex justify-center" v-for="(plan, index) in plans" :key="index">
                        <div v-if="!plan.hasSpaceMemberGrouping" class="text-center text-gray-500">—</div>
                        <div v-else class="flex justify-center"><img src="/images/check.png" class="w-4 h-4" /></div>
                    </div>
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
                                <p>如果当前套餐空间不足，可以通过升级会员套餐获得更多云存储空间。<br/>
                                    不同会员套餐提供不同容量：<br/>
                                    免费版：300MB<br/>
                                    标准版：10GB<br/>
                                    专业版：50GB<br/>
                                    高级版：150GB<br/>
                                    升级后，新的存储空间和会员权益会立即生效。
                                </p>
                            </div>
                        </div>
                        <!-- 2 -->
                        <div class="space-y-3" data-aos="fade-up">
                            <h4 class="text-[18px] font-bold text-white">2、AI 对话/图片额度用完了怎么办？</h4>
                            <div class="text-[15px] text-white/70 leading-relaxed font-medium">
                                <p>当前套餐内 AI 使用额度达到上限后，可以升级到更高版本会员，获得更多 AI 使用额度和更强的创作能力。<br>
不同套餐提供不同 AI 使用权益：<br/>
免费版：体验基础 AI 功能<br/>
标准版：满足日常学习与工作需求<br/>
专业版：适合高频创作和知识整理<br/>
高级版：满足团队协作和更高强度使用场景</p>
                            </div>
                        </div>
                        <!-- 3 -->
                        <div class="space-y-3" data-aos="fade-up">
                            <h4 class="text-[18px] font-bold text-white">3、可以随时升级或降级套餐吗？</h4>
                            <div class="text-[15px] text-white/70 leading-relaxed font-medium">
                                <p>支持随时升级会员套餐。<br/>
升级时系统会根据当前套餐剩余时间自动计算费用，补充差额后即可完成升级。<br/>
目前暂不支持主动降级套餐，以保障数据空间和已有权益的稳定使用。</p>
                            </div>
                        </div>
                        <!-- 4 -->
                        <div class="space-y-3" data-aos="fade-up">
                            <h4 class="text-[18px] font-bold text-white">4、不同套餐之间有什么区别？</h4>
                            <div class="text-[15px] text-white/70 leading-relaxed font-medium">
                                <p>不同套餐主要区别在于：<br/>
云存储空间大小  <br/>
AI 使用额度<br/>
多端同步能力<br/>
协作工作区数量<br/>
团队协作能力<br/>
你可以根据个人记录、内容创作或团队协作需求选择适合的套餐。</p>
                            </div>
                        </div>
                        <!-- 5 -->
                        <div class="space-y-3" data-aos="fade-up">
                            <h4 class="text-[18px] font-bold text-white">5、我的数据会因未续费而丢失吗？</h4>
                            <div class="text-[15px] text-white/70 leading-relaxed font-medium">
                                <p>停止续费后，你的本地数据仍会保留。<br/>
云同步、AI 等会员功能将暂停使用。<br/>
为了保障数据安全，请及时续费或提前备份重要内容。</p>
                            </div>
                        </div>
                        <!-- 6 -->
                        <div class="space-y-3" data-aos="fade-up">
                            <h4 class="text-[18px] font-bold text-white">6、付费订阅后是否支持退款？</h4>
                            <div class="text-[15px] text-white/70 leading-relaxed font-medium">
                                <p>目前暂不支持退款。<br/>
购买前请确认套餐内容是否符合需求。会员权益开通后会立即生效，请谨慎选择适合自己的套餐。</p>
                            </div>
                        </div>
                        <!-- 7 -->
                        <div class="space-y-3" data-aos="fade-up">
                            <h4 class="text-[18px] font-bold text-white">7、可以通过第三方工具同步或备份数据吗？</h4>
                            <div class="text-[15px] text-white/70 leading-relaxed font-medium">
                                <p>不建议使用未经官方支持的第三方同步工具。<br/>
第三方工具可能导致数据冲突、同步异常或内容损坏。<br/>
建议使用小马笔记提供的同步功能管理数据，保障笔记安全。</p>
                            </div>
                        </div>
                        <!-- 8 -->
                        <div class="space-y-3" data-aos="fade-up">
                            <h4 class="text-[18px] font-bold text-white">8、我在电脑端购买订阅，如何在 iPad 上恢复使用？</h4>
                            <div class="text-[15px] text-white/70 leading-relaxed font-medium">
                                <p>使用同一个账号登录小马笔记，系统会自动同步你的会员权益。<br/>
如果使用 Apple 内购后未自动恢复，可以点击 「恢复购买」 按钮进行手动同步。</p>
                            </div>
                        </div>

                        <!-- 8 -->
                        <div class="space-y-3" data-aos="fade-up">
                            <h4 class="text-[18px] font-bold text-white">9、免费版和会员版有什么区别？</h4>
                            <div class="text-[15px] text-white/70 leading-relaxed font-medium">
                                <p>免费版可以体验小马笔记的核心功能，适合个人轻量记录。<br/>
升级会员后，可以获得更大的云存储空间、更高的 AI 使用额度、多端同步以及更多协作能力，更适合长期知识积累和高频使用。</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>


        <div ref="alipayFormContainer" v-html="alipayHtml"></div>
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