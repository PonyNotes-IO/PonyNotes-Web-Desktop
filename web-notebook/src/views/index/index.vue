<template>
    <div style="height: 100vh;background-color: #030300;">
        <xm-nav-bar></xm-nav-bar>
        <div class="scale-root">
            <div class="page flex-col">
                <div class="box_1 flex-col xm-center">
                    <!-- <xm-nav-bar></xm-nav-bar> -->
                    <!-- 添加与导航栏等高的占位元素 -->
                    <div class="nav-placeholder" style="height: 85px;"></div>
                    <div class="xm-content">
                        <div class="group_2 flex-row justify-between">
                            <div class="section_1 flex-col justify-between">
                                <div class="text-wrapper_3">
                                    <span class="text_3">让</span>
                                    <span class="text_4">笔记</span>
                                    <span class="paragraph_1">
                                        更简单
                                        <br />
                                        让知识更
                                    </span>
                                    <span class="text_5">智慧</span>
                                </div>
                                <span class="paragraph_2">
                                    开源、本地优先、云同步，多模态记录与AI&nbsp;并存，助你高效构建个人与团队的知识系统
                                    <!-- <br /> -->
                                </span>
                                <div class="section_2 flex-row justify-between">
                                    <div class="block_1 flex-row">
                                        <div class="image-text_1 flex-row justify-between">
                                            <img class="label_1" referrerpolicy="no-referrer"
                                                src="./assets/img/SketchPng630d6775ec369daac8b984dc7f5ae96401fee631aedff405d7fb0fdc9413ea2d.png" />
                                            <span class="text-group_1" @click="navTo('/login')" >开始注册</span>
                                        </div>
                                    </div>
                                    <div class="block_2 flex-row">
                                        <div class="image-text_2 flex-row justify-between">
                                            <img class="label_2" referrerpolicy="no-referrer"
                                                src="./assets/img/SketchPngd2952b379366e8edf9d14d628bf98d447739c9a1f0fe337e6006a4833523babb.png" />
                                            <span class="text-group_2" @click="navTo('/download')">立即下载</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <img class="image_1" referrerpolicy="no-referrer"
                                src="./assets/img/img_1.png" />
                        </div>
                        <div class="group_3 flex-col"></div>
                    </div>
                    
                </div>
                <div class="box_2 flex-col xm-center">
                    <index-text :text="'易于使用且功能强大'"></index-text>
                    <index-pic :data="data1"></index-pic>

                    <index-text :text="'独一无二的手写体验'"></index-text>
                    <index-pic :data="data2"></index-pic>

                    <index-text :text="'一起思维发散，打破页面限制'"></index-text>
                    <index-pic :data="data3"></index-pic>

                    <index-text :text="'AI 功能，自选模型，更懂你'"></index-text>
                    <index-pic :data="data4"></index-pic>
                </div>
                <div class="box_21 flex-col xm-center">
                    <div class="text-wrapper_13">
                        <span class="text_41">专为</span>
                        <span class="text_42">重视隐私</span>
                        <span class="text_43">的人打造</span>
                    </div>
                    <div class="grid_1 flex-row">
                        <div class="list-items_1 flex-row" v-for="(item, index) in loopData0" :key="index">
                            <img class="image_2" referrerpolicy="no-referrer" :src="item.lanhuimage0" />
                            <div class="text-group_5 flex-col justify-between">
                                <span class="text_44" v-html="item.lanhutext0"></span>
                                <span class="text_45" v-html="item.lanhutext1"></span>
                            </div>
                        </div>
                    </div>
                    <div class="text-wrapper_14">
                        <span class="text_46">利用API</span>
                        <span class="text_47">让笔记进行联动</span>
                    </div>
                    <div class="text-wrapper_15 flex-col">
                        <span class="paragraph_3">
                            xiaomabiji&nbsp;APl
                            <br />
                            <br />
                            POST&nbsp;.https://xiac1010日skey/lication/jsonContent-type:&nbsp;app
                            <br />
                            "Hello，.#xiaomabiji“conte!it”xiaomabijihttps://.com
                            <br />
                            ]
                        </span>
                    </div>
                    <div class="text-wrapper_16">
                        <span class="text_48">全平台</span>
                        <span class="text_49">同步，即可下载</span>
                    </div>
                    <div class="block_3 flex-row justify-between">
                        <div class="image-text_3 flex-row justify-between">
                            <img class="image_3" referrerpolicy="no-referrer"
                                src="./assets/img/SketchPng800b471f99280214555e0d7cc52b2e1e8e2ef87fdc34842760f06d6fd0e8c0e0.png" />
                            <span class="text-group_6">iPhone</span>
                        </div>
                        <div class="image-text_4 flex-row justify-between">
                            <img class="label_3" referrerpolicy="no-referrer"
                                src="./assets/img/SketchPng9e4b776630404b9eb5083b837165d1d2770aa0de746e62b497e3b25891badfda.png" />
                            <span class="text-group_7">iPad</span>
                        </div>
                        <div class="image-text_5 flex-row justify-between">
                            <img class="label_4" referrerpolicy="no-referrer"
                                src="./assets/img/SketchPng701446c2ad80e24c44a87677c876524f41d257a82565fa5b815f1b23182594bf.png" />
                            <span class="text-group_8">Windows</span>
                        </div>
                        <div class="image-text_6 flex-row justify-between">
                            <img class="label_5" referrerpolicy="no-referrer"
                                src="./assets/img/SketchPng6e6b2701e4ccb698072c895a259e9d32d27ccc46ef21500c186bb906dfd3b747.png" />
                            <span class="text-group_9">MacOS</span>
                        </div>
                        <div class="image-text_7 flex-row justify-between">
                            <img class="label_6" referrerpolicy="no-referrer"
                                src="./assets/img/SketchPngf04345ffe4606a3c704dd9f6833a750ecdeaa436d6933f2c1504fcc45f926fce.png" />
                            <span class="text-group_10">Android</span>
                        </div>
                        <div class="image-text_8 flex-row justify-between">
                            <img class="label_7" referrerpolicy="no-referrer"
                                src="./assets/img/SketchPngf9bd931dac19a9e9a4dbca82edbd4bbc0ed7b0d65ca1132ecd39f4a1d0e04f98.png" />
                            <span class="text-group_11">浏览器</span>
                        </div>
                    </div>
                </div>
                <!-- <div class="box_22 flex-col xm-center">
                    <div class="text-wrapper_17 flex-row">
                        <span class="text_50">产品</span>
                        <span class="text_51">资源</span>
                        <span class="text_52">联系我们</span>
                    </div>
                    <div class="section_4 flex-row">
                        <div class="image-text_9 flex-row justify-between">
                            <img class="image_4" referrerpolicy="no-referrer"
                                src="./assets/img/SketchPng41fbb4267a0d3b7935101b9b58f0bb3b7e1627d1e86dd4ef7a128136e3cc9f25.png" />
                            <span class="text-group_12">小马笔记</span>
                        </div>
                        <div class="text-wrapper_18 flex-col justify-between">
                            <span class="text_53">桌面端</span>
                            <span class="text_54">手机端</span>
                        </div>
                        <div class="text-wrapper_19 flex-col justify-between">
                            <span class="text_55">使用教程</span>
                            <span class="text_56">快速上手</span>
                        </div>
                        <div class="text-wrapper_20 flex-col justify-between">
                            <span class="text_57">B站</span>
                            <span class="text_58">抖音</span>
                        </div>
                    </div>
                    <div class="text-wrapper_21 flex-row">
                        <span class="text_59">京ICP备2025131868号</span>
                        <span class="text_60">隐私协议</span>
                        <span class="text_61">常见问题</span>
                        <span class="text_62">小红书</span>
                    </div>
                    <div class="text-wrapper_22 flex-row justify-between">
                        <span class="text_63">用户协议</span>
                        <span class="text_64">邮箱</span>
                    </div>
                </div> -->
                <div class="group_13 flex-col">
                    <index-bottom-pc></index-bottom-pc> 
                </div>
                <!-- <div class="box_23 flex-row xm-center">
                    <img class="label_8" referrerpolicy="no-referrer"
                        src="./assets/img/SketchPng6e4248d8672958c570c9f90d2925913a2521d2344c9bbbada776abec0abf98e8.png" />
                    <span class="text_65">小马笔记</span>
                    <span class="text_66">功能</span>
                    <span class="text_67">价格</span>
                    <span class="text_68">下载</span>
                </div> -->
            </div>
        </div>
    </div>    
</template>
<script>
// import IndexPic from '@/components/index-pic.vue';
import IndexPic from '@/components/index-pic-tab.vue';
import XmNavBar from '@/components/xm-nav-bar.vue';
import IndexText from '@/components/index-text.vue';
import IndexBottomPC from '@/components/index-bottom-pc.vue';
export default {
    components: {
        IndexPic,XmNavBar,IndexText,'index-bottom-pc': IndexBottomPC
    },
    data() {
        return {
            data1: {
                tabs: [
                    '模块化笔记',
                    '多维表',
                    '任务系统',
                    '模版复用',
                    '知识站点',
                ],
                details: [
                    { 
                        title: '模块化笔记', 
                        desc: '像搭积木一样编辑笔记，一键插入文字、表格、代码、音视频、网页等内容，自由组合，灵活创作',
                        img: require('./assets/img/carousel_1.png')
                    },
                    { 
                        title: '多维表', 
                        desc: '支持多视图数据库，用表格、看板、日历等方式查看同一组数据',
                        img: require('./assets/img/carousel_1.png')
                     
                    },
                    { title: '任务系统', desc: '内置任务管理功能，支持待办事项、多状态、优先级、截止日期，笔记即项目管理',
                        img: require('./assets/img/carousel_1.png')
                     
                    },
                    { title: '模版复用', desc: '提供丰富场景模版，支持自定义保存模版，快速复用，提高记录效率' ,
                        img: require('./assets/img/carousel_1.png')
                    },
                    { title: '知识站点', desc: '将笔记一键发布为静态网站，打造专属知识库或项目主页，支持自定义域名和样式' ,
                        img: require('./assets/img/carousel_1.png')
                    },
                ]
            },
            data2: {
                tabs: [
                    '手写笔记',
                    '多种纸张模板',
                    '手写工具箱',
                    '智能书写识别',
                    '图形辅助绘图',
                ],
                details: [
                    { title: '手写笔记', desc: '支持流畅书写与自然笔迹回放，打破键盘限制，模拟纸笔体验' ,
                        img: require('./assets/img/carousel_1.png')
                    },
                    { title: '多种纸张模板', desc: '提供横线、方格、康奈尔笔记、空白等纸张背景，适配学习和工作场景' ,
                        img: require('./assets/img/carousel_1.png')
                    },
                    { title: '手写工具箱', desc: '多种笔刷、颜色、荧光笔、橡皮工具，满足个性化标注与创作' ,
                        img: require('./assets/img/carousel_1.png')
                    },
                    { title: '智能书写识别', desc: '支持手写文字转文本、手势擦除，助力结构化整理与搜索' ,
                        img: require('./assets/img/carousel_1.png')},
                    { title: '图形辅助绘图', desc: '自动识别手绘图形，生成标准线条、箭头、图标，让思维导图和草图更清晰专业' ,
                        img: require('./assets/img/carousel_1.png')},
                ]
            },
            data3: {
                tabs: [
                    '白板页面',
                    '自然书写',
                    '文字编辑',
                    '图片导入',
                    '实时协同',
                ],
                details: [
                    { 
                        title: '白板页面', 
                        desc: '支持手绘直线、箭头、图形，自动矫正为标准图形，适合绘制流程图、思维导图、界面草图' ,
                        img: require('./assets/img/carousel_1.png')
                    },
                    { 
                        title: '自然书写', 
                        desc: '兼容Apple Pencil和各类手写笔，支持压感与笔迹粗细调节，体验接近真实书写' ,
                        img: require('./assets/img/carousel_1.png')
                    },
                    { 
                        title: '文字编辑', 
                        desc: '插入文本框，支持富文本格式，搭配手绘内容轻松表达逻辑',
                        img: require('./assets/img/carousel_1.png') 
                    },
                    { 
                        title: '图片导入', 
                        desc: '拖拽图片或截图快速贴入白板，支持标注、涂鸦与覆盖绘制',
                        img: require('./assets/img/carousel_1.png') 
                    },
                    { 
                        title: '实时协同', 
                        desc: '多人在线同时编辑白板，支持远程头脑风暴与教学演示',
                        img: require('./assets/img/carousel_1.png') 
                    },
                ]
            },
            data4: {
                tabs: [
                    '自由选择大模型',
                    'AI总结与问答',
                    'AI写作助手',
                    '笔记智能搜索',
                ],
                details: [
                    {
                         title: '自由选择大模型', 
                         desc: '像搭积木一样编辑笔记，一键插入文字、表格、代码、音视频、网页等内容，自由组合，灵活创作',
                        img: require('./assets/img/carousel_1.png') 
                    },
                    { 
                        title: 'AI总结与问答', 
                        desc: '选中任意笔记内容，一键生成摘要、提炼重点，支持基于上下文的智能问答',
                        img: require('./assets/img/carousel_1.png') 
                    },
                    { 
                        title: 'AI写作助手', 
                        desc: '输入大纲或灵感碎片，AI 帮你扩写文章、润色表达、生成标题，提升创作效率',
                        img: require('./assets/img/carousel_1.png') 
                    },
                    { 
                        title: '笔记智能搜索', 
                        desc: '理解你的搜索意图，不再局限关键词匹配，支持自然语言搜索与上下文推',
                        img: require('./assets/img/carousel_1.png') 
                    },
                ]
            },

            loopData0: [
                {
                    lanhuimage0:require('./assets/img/icon_loop1.png'),
                    lanhutext0: '本地优先，数据自有',
                    lanhutext1: '默认本地保存，用户完全掌控数据存储位置，无需依赖云端即可使用全部核心功能'
                },
                {
                    lanhuimage0:require('./assets/img/icon_loop2.png'),
                    lanhutext0: '全程加密传输',
                    lanhutext1: '采用行业标准&nbsp;HTTPS&nbsp;+加密协议，保障笔记在云同步过程中的数据安全'
                },
                {
                    lanhuimage0:require( './assets/img/icon_loop3.png'),
                    lanhutext0: '开源透明，拒绝黑箱',
                    lanhutext1: '核心模块开源，代码可查，技术透明，真正做到“你看得见的安全“'
                },
                {
                    lanhuimage0:require('./assets/img/icon_loop4.png'),
                    lanhutext0: '数据可随时导出',
                    lanhutext1: '笔记支持&nbsp;Markdown、PDF、HTML&nbsp;等格式导出，避免平台锁定，随时迁移无负担'
                }
            ],
            constants: {}
        };
    },
    mounted() {
        this.handleResize();
        window.addEventListener('resize', this.handleResize);
        this.initIntersectionObserver(); // 调用方法
    },
    beforeDestroy() {
        window.removeEventListener('resize', this.handleResize);
        if (this.observer) this.observer.disconnect();
    },
    methods: {
        handleResize() {
        // 以1920为设计稿宽度
        const baseWidth = 1920;
        // const baseHeight = 8449;
        const scale = window.innerWidth / baseWidth;
        document.querySelector('.scale-root').style.transform = `scale(${scale})`;
        document.querySelector('.scale-root').style.transformOrigin = 'top left';
        // 可选：设置根容器宽高，避免溢出
        document.querySelector('.scale-root').style.width = baseWidth + 'px';
        document.querySelector('.scale-root').style.height = '8449px';
        // document.querySelector('.scale-root').style.height = baseHeight * scale + 'px'; // 动态计算高度
        // document.querySelector('.scale-root').style.overflow = 'hidden'; // 防止内容溢出（由外层滚动控制）
        },
        initIntersectionObserver() {
            const items = document.querySelectorAll('.list-items_1');
            const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                observer.unobserve(entry.target);
                }
            });
            }, { threshold: 0.5 });
            items.forEach(item => observer.observe(item));
        },
        navTo(url) {
        this.$router.push(url);
        }
    }
};
</script>
<style scoped lang="less" src="./assets/index.less" />
<style scoped>
/* 全局覆盖所有文本元素（p、h1~h6、span 等） */
p, h1, h2, h3, h4, h5, h6, span, a {
    cursor: pointer !important; /* 强制覆盖默认样式 */
}
.scale-root {
  width: 100%; 
  height: 100vh;
  overflow: auto; /* 允许滚动 */
  /* overflow: hidden; */
}
.group_13 {
  position: relative;
  bottom: 0;
  width:  100%;
  display: flex;            
  justify-content: center;  /* 水平居中 */
  align-items: center;      /* 垂直居中（若导航栏高度>内容高度时生效） */

}
</style>