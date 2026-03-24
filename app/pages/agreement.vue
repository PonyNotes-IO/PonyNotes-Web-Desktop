<script setup>
import { ref, onMounted } from 'vue'
import AOS from 'aos'
import 'aos/dist/aos.css'

useSeoMeta({
    title: '用户协议 - 小马笔记',
})


const toastVisible = ref(false);
const toastMessage = ref('');


onMounted(() => {
    AOS.init({
        duration: 800,
        easing: 'ease-out-quint',
        once: true,
        offset: 50,
        anchorPlacement: 'top-bottom',
    })
})
</script>

<template>
    <!-- 基础结构和全局样式 -->
    <div class="w-full bg-white font-sans overflow-x-hidden selection:bg-[#FF4D00] selection:text-white relative">

        <!-- Toast 轻提示 -->
        <Transition name="toast">
            <div v-if="toastVisible" 
                 class="fixed top-8 left-1/2 transform -translate-x-1/2 z-[9999] 
                        bg-gray-900 text-white px-6 py-3 rounded-lg shadow-lg 
                        flex items-center gap-3 min-w-[200px] justify-center">
                <svg class="w-5 h-5 text-[#FF4D00]" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clip-rule="evenodd"/>
                </svg>
                <span class="text-sm font-medium">{{ toastMessage }}</span>
            </div>
        </Transition>

        <!-- =========================================================
             装饰性背景光晕
        ========================================================= -->
        <!-- 左侧蓝色/青色光晕 (优化性能) -->
        <div class="absolute left-[-100px] top-[40%] w-[300px] h-[300px] bg-gradient-to-r from-[#00FFFF] to-[#71F2B5] opacity-20 blur-[60px] rounded-full pointer-events-none z-0 will-change-transform"></div>
        
        <!-- 右侧粉色/橙色光晕 (优化性能) -->
        <div class="absolute right-[-100px] top-[60%] w-[300px] h-[300px] bg-gradient-to-l from-[#FF4081] to-[#FF99C2] opacity-20 blur-[60px] rounded-full pointer-events-none z-0 will-change-transform"></div>


        <!-- =========================================================
             SECTION 1: 顶部英雄区 (包含背景图)
             修改点：pb-[650px] 大幅增大底部填充，以拉长背景图
        ========================================================= -->
        <section class="relative w-full bg-no-repeat bg-top z-10"
            style="background-image: url('/images/download/bg@2x.png'); 
                   background-size: 100% auto;">

                <!-- 导航栏 (已移至 Layout) -->

            <div class="max-w-[1140px] mx-auto px-6 text-center pt-44 pb-[650px]"> <!-- 增大底部填充 -->
                <h1 class="text-[40px] md:text-[48px] font-bold text-gray-900 tracking-tight mb-10" data-aos="zoom-in">
                    小马笔记用户服务协议
                </h1>

                
            </div>
        </section>


        <!-- =========================================================
             SECTION 2: 下载卡片列表区域
             修改点：-mt-[600px] 配合上面的 pb-[650px] 重新计算负边距
        ========================================================= -->
        <section class="w-full bg-white relative z-30 pt-10 pb-20">
            <div class="max-w-[880px] mx-auto px-6 -mt-[690px]" data-aos="zoom-in">
                <div class="word">
                            
    <div class="Section0"  style="layout-grid:15.6000pt;" ><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" ></font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >为使用北京爱欧爱科技有限公司及其关联公司（以下简称</font><font face="等线" >“我们”）共同运营的“小马笔记”</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >应用程序、</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >网页端、微信小程序（以下简称</font><font face="等线" >“小马笔记”）的产品提供的相关服务（以下简称“本服务”），请你审慎阅读、充分理解本协议各条款内容，并选择接受或不接受本协议，特别是免除或者限制责任的条款、法律适用和争议解决条款，免除或者限制责任的条款将以粗体标识，你应重点阅读。你的注册、登录、使用等行为将视为对本协议的接受，并视为你同意接受本协议各项条款的约束，本协议将构成你与“小马笔记”之间直接有约束力的法律文件。</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >如果你不具备中华人民共和国法律法规规定的与你行为相适应的民事行为能力，请在监护人陪同和指导下阅读本协议，并在使用本协议项下服务前取得监护人的同意，若你不具备前述与你行为相适应的民事行为能力，则你及你的监护人应依照法律规定承担因此而导致的一切后果。</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >请确认你依照中华人民共和国法律（为本协议之目的，含行政法规、部门规章、规范性文件，下同）及你所在地之法律、及你自愿或应当接受其管辖之法律（以下合称</font><font face="等线" >“法律”），有权签署并同意接受本协议的约束。如果你代表你的雇主或单位，请在签署前，确认并保证你已获得签署本协议的充分授权。</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >特别提示：本服务中涉及由人工智能模型提供的服务及内容，均为人工智能模型自动生成，可能出现错误或遗漏，我们对本服务生成内容的准确性、完整性和功能性不作任何保证或承诺，其生成的内容不代表我们的态度或观点。你在使用本服务涉及医疗、法律等相关专业问题及内容时，并不能获得医疗诊断或法律服务等专业意见。若你需要该等专业意见，应咨询医生、律师等专业人士。我们无法承担你因使用本服务给你或任何第三方造成的损害或责任。与人工智能功能相关的个人信息处理规则，详见《隐私政策》。</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >一、服务内容</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >1.1本服务是指：我们向你提供的通过小马笔记进行笔记记录、笔记优化、搜索查询等各项功能和服务。我们可以对提供的服务内容予以变更，并对用户进行相应的提示。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >1.2你理解，我们仅提供与小马笔记相关的技术服务，除此之外与相关网络服务有关的设备（如个人电脑、手机、及其他与接入互联网或移动网有关的装置）及所需的费用（如为接入互联网而支付的电话费及上网费、为使用移动网络而支付的手机费）均由你自行负担。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >二、注册与登录</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >2.1你可以通过直接输入手机号码或通过微信账号及绑定手机号码的注册小马笔记账号。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >2.2注册账号或授权登录时，请你按照小马笔记的提示及要求填写或提供资料、信息，并确保用户身份及信息的真实性、准确性和完整性；如果资料发生变化，请你及时更改。你使用的帐号名称、头像等注册信息中不得出现违法和不良信息，经小马笔记审核，如存在上述情况，将不予注册；同时，在注册后，如发现用户帐号头像等注册信息存在违法和不良信息的，小马笔记有权不经通知单方采取限期改正、暂停使用、注销登记等措施。当注册成功后，你应采取合理措施维护账号的安全，对由此用户名登入系统后所发生的所有活动和事件负责，并承担由此产生的直接或者间接法律责任。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >2.3你应了解，当你注册成功并使用微信账号或手机号码登陆小马笔记时，我们无法对非法或未经你授权使用你账号的行为作出甄别，因此，小马笔记对任何使用你账号登录并使用小马笔记相关服务的行为不承担任何责任。你同意并承诺：</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >2.3.1当你的账号遭到未获授权的使用，或者发生其他任何安全问题时，你会立即有效通知小马笔记，同时你理解小马笔记对你的任何请求采取行动均需要合理时间，且小马笔记应你请求而采取的行动可能无法避免或阻止侵害后果的形成或扩大；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >2.3.2账号因你主动泄露、遭他人攻击、诈骗等行为导致的损失及后果，你应通过司法、行政等救济途径向侵权行为人追偿。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >2.4小马笔记有权根据自己的判定，在怀疑账号被不当使用时，拒绝或限制账号使用，或注销该账号，同时终止本协议。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >2.5 你应当在注册、使用账号时承诺遵守法律法规、社会主义制度、国家利益、公民合法权益、公共秩序、社会道德风尚和信息真实性等七条底线，不得在账号注册资料中出现违法和不良信息，且用户保证其在注册和使用账号时，不得有以下情形：</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >2.5.1 违反宪法或法律法规规定的；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >2.5.2 国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >2.5.3 国家荣誉和利益的，损害公共利益的；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >2.5.4 煽动民族仇恨、民族歧视，破坏民族团结的；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >2.5.5 破坏国家宗教政策，宣扬邪教和封建迷信的；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >2.5.6 散布谣言，扰乱社会秩序，破坏社会稳定的</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >2.5.7 散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >2.5.8 侮辱或者诽谤他人，侵害他人合法权益的；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >2.5.9 含有法律、行政法规禁止的其他内容的。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >2.6你充分了解并同意，你必须为自己注册帐号下的一切行为负责，应对本服务中的内容自行加以判断，并承担因使用内容而引起的所有风险，包括因对内容的正确性、完整性或实用性的依赖而产生的风险。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >三、用户使用</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.1你同意，遵守法律及本协议规定，秉承善意使用小马笔记，且保证：</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.1.1不会利用小马笔记进行非法活动、或进行侵犯他人权利或损害他人利益的活动；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.1.2不会以非法方式获取或利用小马笔记其他用户的信息；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.1.3不会以技术方式攻击、破坏或改变小马笔记的部分或全部、或干扰其运行；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.1.4不会以非法方式获取或使用小马笔记的任何代码或其他技术或商业信息；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.1.5不会对小马笔记运行的任何程序进行反向工程、反向编译、反向汇编或改写；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.1.6不会未经许可使用小马笔记的名称、公司名称、商标、商业标识、小程序版式或内容、或其他由我们享有知识产权或权利的信息或资料；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.1.7不会侵犯我们的商标权、著作权、专利权、其他知识产权或其他合法权利或权益，不会以任何方式贬损我们的商业声誉；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.1.8不以商业目的使用小马笔记，不对小马笔记的任何部分或全部进行商业性质利用、复制、拷贝、出售、调查、广告，不将小马笔记用于其他任何商业目的或商业性质的活动，但小马笔记与你另行签订有协议或小马笔记另行指定可供你使用的特定商业服务除外。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.1.9不会利用小马笔记进行其他违反法律法规或违背公序良俗的活动。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >如你违反上述约定，小马笔记可能将你违反法律或侵犯第三方权利或权益的记录报告给行政主管部门、司法机关。</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.2你应当确保你在小马笔记内上传的信息不包含以下内容：</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.2.1违反国家法律法规禁止性规定的；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.2.2政治宣传、封建迷信、淫秽、色情、赌博、暴力、恐怖或者教唆犯罪的；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.2.3欺诈、虚假、不准确或存在误导性的；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.2.4侵犯他人知识产权或涉及第三方商业秘密及其他专有权利的；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.2.5侮辱、诽谤、恐吓、涉及他人隐私等侵害他人合法权益的；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.2.6存在可能破坏、篡改、删除、影响小马笔记任何系统正常运行或未经授权秘密获取小马笔记及其他用户的数据、个人资料的病毒、木马、爬虫等恶意软件、程序代码的；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.2.7其他违背社会公共利益或公共道德或依据小马笔记相关规则的规定不适合上传的内容。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >3.3你理解并同意，应当通过正当合法途径获得全部服务，其他非官方途径提供的服务，均不受到我们的保护。 </span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >四、用户产生的内容</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >4.1你在小马笔记以各种形式产生的一切信息，均应符合国家法律法规等相关规定及网站相关规定，符合社会公序良俗，并不侵犯任何第三方主体的合法权益。如果我们发现你通过小马笔记产生的内容不符合前述规定，我们有权予以处理。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >五、知识产权</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >5.1我们对小马笔记服务及软件所包含的受知识产权法或其他法律保护的资料享有相应的权利。小马笔记的整体内容（包括但不仅限于设计图样以及其他图样、产品、服务名称等）的知识产权归属我们，未经事先合法书面的授权，任何人不得复制或用作其他用途。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >5.2 小马笔记对其专有内容、原创内容和其他通过授权取得的独占或者独家内容享有知识产权。未经小马笔记书面许可，任何单位和个人不得私自复制、转载、传播和提供收听服务或者有其他侵犯小马笔记知识产权的行为。否则，将承担法律责任。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >六、隐私保护</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >6.1小马笔记可能需要你填写能识别用户身份的个人信息以便为你提供更好的使用体验。“个人信息”包括但不限于用户使用的手机号等信息。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >6.2小马笔记承诺，将按照法律法规规定的方式及保证你安全稳定使用小马笔记的方式收集、使用、保存、管理你在注册或使用小马笔记过程中提供的个人信息，不以非法方式披露你的个人信息。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >6.3 你同意小马笔记按照《隐私政策》对用户个人信息进行收集、使用、保存、共享和转让，具体条款详见《隐私政策》。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >七、免责约定</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >7.17.1小马笔记尽可能保证其稳定运行。你应理解并同意，因法律、政策、技术、经济、管理的原因，小马笔记不会因以下情形出现而对你承担责任：</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >7.1.1小马笔记无法使用、中断使用或无法完全适合用户的使用要求；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >7.1.2小马笔记受到干扰，无法及时、安全、可靠运行，或出现任何错误；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >7.1.3 经由小马笔记取得的任何产品、服务或其他材料不符合你的期望；</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >7.1.4 用户资料遭到未经授权的使用或修改。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >7.2 小马笔记为达成你使用商品或服务之目会在所需的最短期限内保留你的个人信息，但法律法规另有规定的情形除外。如你注销小马笔记账号，或小马笔记所提供的服务终止或取消，小马笔记可以从服务器上永久地删除用户的数据，除法律有明确规定外，此种情形下，小马笔记无法向用户返还或提供任何数据。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >7.3你在使用小马笔记服务的过程中应遵守国家法律法规及政策规定，你使用小马笔记服务而产生的行为后果由你自行承担。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >7.4你理解并同意，小马笔记向你提供的服务、产品或内容，可能会根据法律法规、监管政策的变化、权利人或版权方的要求、产品计划、运营情况等原因进行变更、中止、中断或终止，包括但不限于服务、产品或内容的永久或暂时下架、停止等，前述措施一经实施即生效且小马笔记对此免责。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >八、通知</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >8.1小马笔记向你发送的通知可通过短信、电话或在小程序、网页端内显著位置公告等方式进行传送。小马笔记将通过上述方法之一将消息传递给你，告知服务条款的修改、服务变更、或其它重要事情。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >九、违约责任</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >9.1如因小马笔记违反有关法律、法规或本协议项下的任何条款而给你造成损失的，相应的损害赔偿责任由小马笔记承担。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >9.2你同意保障和维护小马笔记及其他用户的合法权益，如因你违反有关法律、法规或本协议项下的任何条款而给小马笔记或任何其他任何第三方造成损失，你同意承担由此造成的损害赔偿责任。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >十、法律适用</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >10.1你在使用小马笔记时，应遵守中华人民共和国法律、及你所在地之法律、及你自愿或应当接收其管辖之法律，本协议的解释及履行应按照中华人民共和国法律进行。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >十一、争议解决</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >11.1你和小马笔记均同意，因本协议解释或执行引起的任何争议，双方应首先友好协商解决。协商不成时，则任一方均可将争议提交北京仲裁委员会依据其届时有效的仲裁规则以仲裁方式解决。仲裁地点为北京，仲裁语言为中文。仲裁裁决为终局的，对各方均有法律约束力。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >十二、其他</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >12.1小马笔记可能不时发布针对用户的相关协议，并可能将该相关协议作为对本协议的补充或修改而将其内容作为本协议的一部分。请你及时关注并阅读相关协议。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >12.2本协议任一条款被视为废止、无效或不可执行，该条应视为可分的且并不影响本协议其余条款的有效性及可执行性。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >12.3如对本规则或相关事宜有任何问题，你可随时发送邮件至</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >support</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >@</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >xiaomabiji</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" >.com与我们联系。</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >本用户服务协议生效日期：</font>202</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >6</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >年</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >2</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >月</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >14</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >日</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >本用户服务协议生效日期：</font>202</span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >6</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >年</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >2</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >月</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >14</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><font face="等线" >日</font></span><span style="mso-spacerun:'yes';font-family:等线;mso-bidi-font-family:'Times New Roman';
    font-size:10.5000pt;mso-font-kerning:1.0000pt;" ><o:p></o:p></span></p></div>

                </div>
            </div>
        </section>

    </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap');

h1, h3 {
    letter-spacing: -0.01em;
}

.cursor-pointer {
    transition: border-color 0.2s ease !important;
}

/* Toast 过渡动画 */
.toast-enter-active,
.toast-leave-active {
    transition: all 0.3s ease;
}

.toast-enter-from {
    opacity: 0;
    transform: translate(-50%, -20px);
}

.toast-leave-to {
    opacity: 0;
    transform: translate(-50%, -20px);
}

/* 优化性能：强制使用硬件加速 */
.will-change-transform {
    will-change: transform; 
}

@media (max-width: 768px) {
    /* 移动端修正顶部 section 的 padding */
    section:first-of-type > div {
        padding-top: 1.5rem !important;
        padding-bottom: 350px !important; 
    }
    
    /* 移动端修正卡片负边距 */
    .max-w-\[880px\] {
        margin-top: -150px !important; /* 增大负边距 */
    }
}
</style>