<template>
    <div class="scale-root">
        <div class="page flex-col ">
            <div class="group_1 flex-col xm-center">
                    <xm-nav-bar></xm-nav-bar>
                <div class="group_3 flex-row xm-center">
                    <div class="text-group_1 flex-col justify-between">
                        <span class="text_7">AI&nbsp;工具赋能内容创作</span>
                        <span class="text_8">简化工作流程，做事效率倍增</span>
                    </div>
                </div>
            </div>
            <div class="group_6 flex-col xm-center">
                <span class="text_24">桌面端</span>
                <div class="block_1 flex-row justify-between">
                    <div class="group_7 flex-col download-item">
                        <img class="image_2" referrerpolicy="no-referrer"
                            src="./assets/img/SketchPngd3ae197f455fa28788bb26767608b1dccb9164f81337a39dfeffa0dbc9c6b61b.png" />
                        <div class="text-group_3 flex-col justify-between">
                            <span class="text_25">小马笔记window</span>
                            <span class="text_26">仅支持64位系统</span>
                        </div>
                        <div class="image-text_2 flex-row justify-between">
                            <img class="label_2" referrerpolicy="no-referrer"
                                src="./assets/img/SketchPngddc407b98ee973936143a7430a79d75251724c3069b31e7a81c7afd8a739ff21.png" />
                            <span class="text-group_4">立即下载</span>
                        </div>
                    </div>
                    <div class="group_8 flex-col download-item">
                        <img class="image_3" referrerpolicy="no-referrer"
                            src="./assets/img/SketchPngbd9190f707c9db1810ca4aafd5a3bc40c3eb8568804a2ae040fb9bafb14c3609.png" />
                        <div class="text-group_5 flex-col justify-between">
                            <span class="text_27">小马笔记Mac</span>
                            <span class="text_28">仅支持M1机型</span>
                        </div>
                        <div class="image-text_3 flex-row justify-between">
                            <img class="label_3" referrerpolicy="no-referrer"
                                src="./assets/img/SketchPngddc407b98ee973936143a7430a79d75251724c3069b31e7a81c7afd8a739ff21.png" />
                            <span class="text-group_6">立即下载</span>
                        </div>
                    </div>
                </div>
                <span class="text_29">移动端</span>
                <div class="block_2 flex-row justify-between">
                    <div class="group_9 flex-col download-item">
                        <img class="image_4" referrerpolicy="no-referrer"
                            src="./assets/img/SketchPngdeadd20785006c98d78ff9ca2471492d2ee9211527325b9a492294736751f4f0.png" />
                        <div class="text-group_7 flex-col justify-between">
                            <span class="text_30">小马笔记安卓端</span>
                            <span class="text_31">仅支持64位系统</span>
                        </div>
                        <div class="image-text_4 flex-row justify-between">
                            <img class="label_4" referrerpolicy="no-referrer"
                                src="./assets/img/SketchPngddc407b98ee973936143a7430a79d75251724c3069b31e7a81c7afd8a739ff21.png" />
                            <span class="text-group_8">立即下载</span>
                        </div>
                    </div>
                    <div class="group_10 flex-col download-item">
                        <img class="image_5" referrerpolicy="no-referrer"
                            src="./assets/img/SketchPngc02c854cecf2e00c3205a24899abd418816f64c65ae59483c5fbd9fed07464df.png" />
                        <div class="text-group_9 flex-col justify-between">
                            <span class="text_32">小马笔记ios版</span>
                            <span class="text_33">仅支持M1机型</span>
                        </div>
                        <div class="image-text_5 flex-row justify-between">
                            <img class="label_5" referrerpolicy="no-referrer"
                                src="./assets/img/SketchPngddc407b98ee973936143a7430a79d75251724c3069b31e7a81c7afd8a739ff21.png" />
                            <span class="text-group_10">立即下载</span>
                        </div>
                    </div>
                </div>
                <span class="text_34">ipad端</span>
                <div class="block_3 flex-row justify-between">
                    <div class="image-wrapper_1 flex-row">
                        <img class="image_6" referrerpolicy="no-referrer"
                            src="./assets/img/SketchPng5ca6447a908c6de7fa2c2ae6a9d1298c4a348f3eba2c7b6550ee52724e96ee9f.png" />
                        <img class="image_7" referrerpolicy="no-referrer"
                            src="./assets/img/SketchPnge457ba50fa406f4b22296b920312834b1e669a8f42ac33c759cf00de55ad18d4.png" />
                    </div>
                    <div class="group_11 flex-col download-item">
                        <img class="image_8" referrerpolicy="no-referrer"
                            src="./assets/img/SketchPngc02c854cecf2e00c3205a24899abd418816f64c65ae59483c5fbd9fed07464df.png" />
                        <div class="text-group_11 flex-col justify-between">
                            <span class="text_35">小马笔记ios版</span>
                            <span class="text_36">仅支持M1机型</span>
                        </div>
                        <div class="image-text_6 flex-row justify-between">
                            <img class="label_6" referrerpolicy="no-referrer"
                                src="./assets/img/SketchPngddc407b98ee973936143a7430a79d75251724c3069b31e7a81c7afd8a739ff21.png" />
                            <span class="text-group_12">立即下载</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="group_13 flex-col">
                <index-bottom-pc></index-bottom-pc> 
            </div>

        </div>
    </div>    
</template>
<script>
import XmNavBar from '@/components/xm-nav-bar.vue';
import IndexBottomPC from '@/components/index-bottom-pc.vue';
// import IndexBottom from '@/components/index-bottom.vue';
export default {
    components: {
        XmNavBar, 
        'index-bottom-pc': IndexBottomPC
    },
    data() {
        return {
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
        const scale = window.innerWidth / baseWidth;
        document.querySelector('.scale-root').style.transform = `scale(${scale})`;
        document.querySelector('.scale-root').style.transformOrigin = 'top left';
        // 可选：设置根容器宽高，避免溢出
        document.querySelector('.scale-root').style.width = baseWidth + 'px';
        document.querySelector('.scale-root').style.height = '120%';
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
  height: 100%;
  overflow: hidden;
}
.group_2 {
  top: 0;
  width: 1400px;
  display: flex;            
  justify-content: center;  /* 水平居中 */
  align-items: center;      /* 垂直居中（若导航栏高度>内容高度时生效） */
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