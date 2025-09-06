<template>
  <div class="page flex-col">
    <div class="section_3 flex-col">
      <!-- <div class="group_2 flex-row">
        <img
          class="thumbnail_3"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng50df99f41c5b411f42c7412ae774fd2bb2488d446ca2d01a637b7c54af62d535.png"
        />
        <span class="text_2">小马笔记</span>
        <span class="text_3">登录</span>
        <span class="text_4">注册</span>
        <img
          class="thumbnail_4"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng0fa58b251acd9fdd3cf775e74f4ab4baf08feb1e112b74c5ac39d12508ef9ff7.png"
        />
      </div> -->
      <MobileNavBar 
        :show-logo="true"
        :show-title="true"
        :show-login="true"
        :show-register="true"
        :show-more="true"
      />
      <div class="group_3 flex-row">
        <div class="text-wrapper_2">
          <span class="text_5">让</span>
          <span class="text_6">笔记</span>
          <span class="text_7"></span>
          <span class="paragraph_1">
            更简单
            <br />
            让知识
          </span>
          <span class="text_7"></span>
          <span class="paragraph_1">更</span><span class="text_6">智慧</span>
          
        </div>
      </div>
      <div class="text-wrapper_3 flex-row">
        <span class="paragraph_2">
          开源、本地优先、云同步
          <br />
          多模态记录与AI&nbsp;并存
          <br />
          助你高效构建个人与团队的知识系统
        </span>
      </div>
      <div class="group_4 flex-col"></div>
    </div>
    <img
      class="image_2"
      referrerpolicy="no-referrer"
      src="./assets/img/image.png"
    />
    <div class="section_4 flex-col">
      <button class="button_1 flex-col" @click="onClick_1"><span class="text_9">免费下载使用</span></button>
    </div>
    <div class="section_5 flex-col">
      <span class="text_10">易于使用且功能强大</span>
      <!-- 轮播图 banner 1--------> 
      <div class="banner-wrapper">
        <!-- 轮播图容器 -->
        <div class="carousel-container" ref="carouselContainer">
          <div class="box_1 flex-col">
            <div class="section_6 flex-col">
                <!--轮播 box1 轮播-->
                <MobileCarousel 
                  :items="productImages" 
                  :interval="5000"
                  @item-click="handleProductClick"
                />
            </div>
          </div>

        </div>
      </div>

    </div>
    <div class="section_7 flex-col">
      <div class="box_3 flex-row">
        <div class="text-group_1 flex-col">
          <span class="text_11">模块化笔记</span>
          <div class="text-wrapper_4">
            <span class="text_12">像搭积木一样编辑笔记</span>
            <span class="paragraph_3"><br /></span>
            <span class="text_13">一键插入文字、表格、代码、音视频、网页等内容，自由组合，灵活创作</span>
          </div>
        </div>
      </div>
    </div>
    <div class="section_8 flex-col"><div class="box_4 flex-col"></div></div>
    <div class="section_9 flex-col">
      <div class="block_1 flex-row">
        <div class="text-group_2 flex-col">
          <span class="text_14">多维表</span>
          <div class="text-wrapper_5">
            <span class="text_15">像搭积木一样编辑笔记</span>
            <span class="paragraph_4"><br /></span>
            <span class="text_16">一键插入文字、表格、代码、音视频、网页等内容，自由组合，灵活创作</span>
          </div>
        </div>
      </div>
    </div>
    <div class="section_10 flex-col"><div class="group_6 flex-col"></div></div>
    <div class="section_11 flex-col">
      <div class="section_12 flex-row">
        <div class="text-group_3 flex-col">
          <span class="text_17">任务系统</span>
          <div class="text-wrapper_6">
            <span class="paragraph_5">
              内置任务管理功能
              <br />
            </span>
            <span class="paragraph_6">
              支持待办事项、多状态、优先级、截止日期
              <br />
              笔记即项目管理
            </span>
          </div>
        </div>
      </div>
    </div>
    <div class="section_13 flex-col"><div class="block_2 flex-col"></div></div>
    <div class="section_14 flex-col">
      <div class="box_5 flex-row">
        <div class="text-group_4 flex-col">
          <span class="text_18">模版复用</span>
          <div class="text-wrapper_7">
            <span class="paragraph_7">
              提供丰富场景模版
              <br />
            </span>
            <span class="paragraph_8">
              支持自定义保存模版，快速复用，提高记录
              <br />
              效率
            </span>
          </div>
        </div>
      </div>
    </div>
    <div class="section_15 flex-col"><div class="block_3 flex-col"></div></div>
    <div class="section_16 flex-col">
      <div class="box_6 flex-row">
        <div class="text-group_5 flex-col">
          <span class="text_19">知识站点</span>
          <div class="text-wrapper_8">
            <span class="paragraph_9">
              将笔记一键发布为静态网站
              <br />
            </span>
            <span class="paragraph_10">
              打造专属知识库或项目主页，支持自定义域
              <br />
              名和样式
            </span>
          </div>
        </div>
      </div>
    </div>
    <div class="section_17 flex-col"><div class="box_7 flex-col"></div></div>
    <div class="section_18 flex-col">
      <span class="text_20">独一无二的手写体验</span>
      <div class="group_7 flex-col">
        <div class="box_8 flex-col">
          <!--轮播 box8 轮播-->
                <MobileCarousel 
                  :items="writeFeelImages" 
                  :interval="5000"
                  @item-click="handleProductClick"
                />
        </div>
      </div>
      <div class="text-group_6 flex-col">
        <span class="text_21">手写笔记</span>
        <div class="text-wrapper_9">
          <span class="paragraph_11">
            支持流畅书写与自然笔迹回放
            <br />
          </span>
          <span class="text_22">打破键盘限制，模拟纸笔体验</span>
        </div>
      </div>
      <div class="group_8 flex-col"><div class="box_9 flex-col"></div></div>
      <div class="text-group_7 flex-col">
        <span class="text_23">多种纸张模板</span>
        <div class="text-wrapper_10">
          <span class="paragraph_12">
            提供横线、方格、康奈尔笔记
            <br />
          </span>
          <span class="text_24">空白等纸张背景，适配学习和工作场景</span>
        </div>
      </div>
      <div class="group_9 flex-col"><div class="group_10 flex-col"></div></div>
      <div class="text-group_8 flex-col">
        <span class="text_25">手写工具箱</span>
        <div class="text-wrapper_11">
          <span class="paragraph_13">
            多种笔刷、颜色、荧光笔、橡皮工具，
            <br />
          </span>
          <span class="text_26">满足个性化标注与创作</span>
        </div>
      </div>
      <div class="group_11 flex-col"><div class="group_12 flex-col"></div></div>
      <div class="text-group_9 flex-col">
        <span class="text_27">智能书写识别</span>
        <div class="text-wrapper_12">
          <span class="paragraph_14">
            支持手写文字转文本
            <br />
          </span>
          <span class="text_28">手势擦除，助力结构化整理与搜索</span>
        </div>
      </div>
      <div class="group_13 flex-col"><div class="group_14 flex-col"></div></div>
      <div class="text-group_10 flex-col">
        <span class="text_29">图形辅助绘图</span>
        <div class="text-wrapper_13">
          <span class="paragraph_15">
            自动识别手绘图形
            <br />
          </span>
          <span class="text_30">生成标准线条、箭头、图标，让思维导图和草图更清晰专业</span>
        </div>
      </div>
      <div class="group_15 flex-col"><div class="block_4 flex-col"></div></div>
      <span class="text_31">独一无二的手写体验</span>
    </div>
    <div class="section_19 flex-col">
      <span class="text_32">一起思维发散，打破页面限制</span>
    </div>
    <div class="section_20 flex-col">
      <div class="box_12 flex-col">
        <MobileCarousel 
                  :items="thinkExtendImages" 
                  :interval="5000"
                  @item-click="handleProductClick"
                />
      </div>
    </div>
    <div class="section_21 flex-col">
      <div class="box_13 flex-row">
        <div class="text-group_11 flex-col">
          <span class="text_33">无限白板</span>
          <div class="text-wrapper_14">
            <span class="paragraph_16">
              支持手绘直线、箭头、图形，
              <br />
            </span>
            <span class="text_34">自动矫正为标准图形，适合绘制流程图、思维导图、界面草图</span>
          </div>
        </div>
      </div>
    </div>
    <div class="section_22 flex-col"><div class="box_14 flex-col"></div></div>
    <div class="section_23 flex-col">
      <div class="box_15 flex-row">
        <div class="text-group_12 flex-col">
          <span class="text_35">自然书写</span>
          <div class="text-wrapper_15">
            <span class="text_36">兼容&nbsp;Apple&nbsp;Pencil&nbsp;和各类手写笔</span>
            <span class="text_37">支持压感与笔迹粗细调节，体验接近真实书写</span>
          </div>
        </div>
      </div>
    </div>
    <div class="section_24 flex-col"><div class="group_17 flex-col"></div></div>
    <div class="section_25 flex-col">
      <div class="group_18 flex-row">
        <div class="text-group_13 flex-col">
          <span class="text_38">文字编辑</span>
          <div class="text-wrapper_16">
            <span class="paragraph_17">
              插入文本框，支持富文本格式
              <br />
            </span>
            <span class="text_39">搭配手绘内容轻松表达逻辑</span>
          </div>
        </div>
      </div>
    </div>
    <div class="section_26 flex-col"><div class="group_19 flex-col"></div></div>
    <div class="section_27 flex-col">
      <div class="group_20 flex-row">
        <div class="text-group_14 flex-col">
          <span class="text_40">图片导入</span>
          <div class="text-wrapper_17">
            <span class="paragraph_18">
              拖拽图片或截图快速贴入白板
              <br />
            </span>
            <span class="text_41">支持标注、涂鸦与覆盖绘制</span>
          </div>
        </div>
      </div>
    </div>
    <div class="section_28 flex-col"><div class="box_16 flex-col"></div></div>
    <div class="section_29 flex-col">
      <div class="box_17 flex-row">
        <div class="text-group_15 flex-col">
          <span class="text_42">实时协同</span>
          <div class="text-wrapper_18">
            <span class="paragraph_19">
              多人在线同时编辑白板，
              <br />
            </span>
            <span class="text_43">支持远程头脑风暴与教学演示</span>
          </div>
        </div>
      </div>
    </div>
    <div class="section_30 flex-col"><div class="section_31 flex-col"></div></div>
    <div class="section_32 flex-col">
      <span class="text_44">自选模型，更懂你</span>
      <div class="box_18 flex-col">
        <div class="section_33 flex-col">
            <MobileCarousel 
                  :items="modelFreedomImages" 
                  :interval="5000"
                  @item-click="handleProductClick"
                />  
        </div>
    </div>
      <div class="text-group_16 flex-col">
        <span class="text_45">自由选择大模型</span>
        <div class="text-wrapper_19">
          <span class="paragraph_20">
            小马笔记支持多种主流大语言模型
            <br />
          </span>
          <span class="text_46">按需切换，满足不同场景需求</span>
        </div>
      </div>
      <div class="box_19 flex-col"><div class="box_20 flex-col"></div></div>
      <div class="text-group_17 flex-col">
        <span class="text_47">AI总结与问答</span>
        <div class="text-wrapper_20">
          <span class="paragraph_21">
            选中任意笔记内容，一键生成摘要
            <br />
          </span>
          <span class="text_48">提炼重点，支持基于上下文的智能问答</span>
        </div>
      </div>
      <div class="box_21 flex-col"><div class="box_22 flex-col"></div></div>
      <div class="text-group_18 flex-col">
        <span class="text_49">AI写作助手</span>
        <div class="text-wrapper_21">
          <span class="paragraph_22">
            输入大纲或灵感碎片，AI&nbsp;帮你扩写文章
            <br />
          </span>
          <span class="text_50">润色表达、生成标题，提升创作效率</span>
        </div>
      </div>
      <div class="box_23 flex-col"><div class="group_21 flex-col"></div></div>
      <div class="text-group_19 flex-col">
        <span class="text_51">笔记智能搜索</span>
        <div class="text-wrapper_22">
          <span class="paragraph_23">
            AI&nbsp;理解你的搜索意图，
            <br />
          </span>
          <span class="text_52">不再局限关键词匹配，支持自然语言搜索与上下文推荐</span>
        </div>
      </div>
      <div class="box_24 flex-col"><div class="box_25 flex-col"></div></div>
    </div>
    <div class="section_34 flex-col">
      <div class="block_7 flex-row">
        <div class="image-text_1 flex-row justify-between">
          <img
            class="label_1"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng4c606d9796baec607a5e00f14a358def7986c2654146da51c741ae559c11eeca.png"
          />
          <div class="text-group_20 flex-col">
            <span class="text_53">数据可随时导出</span>
            <span class="paragraph_24">
              笔记支持&nbsp;Markdown、PDF、HTML&nbsp;等格式导
              <br />
              出，避免平台锁定，随时迁移无负担
            </span>
          </div>
        </div>
      </div>
      <span class="text_54">利用API让笔记进行联动</span>
      <span class="text_55">XIAOMABIJI&nbsp;&nbsp;&gt;_API</span>
      <span class="paragraph_25">
        POST&nbsp;.https://xiac1010日skey/lication
        <br />
        /jsonContent-type:&nbsp;app"Hello，
        <br />
        .#xiaomabiji“conte!it”xiaomabijihttps://.com]
      </span>
      <span class="text_56">全平台同步&nbsp;即可下载</span>
      <div class="block_8 flex-row">
        <div class="image-text_2 flex-row justify-between">
          <img
            class="thumbnail_5"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng2f57bc3f0e2b67ae5d63eddcb37be226b2d069f18e1399971543af6a8f546217.png"
          />
          <span class="text-group_21">iPhone</span>
        </div>
        <div class="image-text_3 flex-row justify-between">
          <img
            class="thumbnail_6"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng6906606e65ad9833b304f4ac355833c650132c830fab7733c819986fcabe8a1d.png"
          />
          <span class="text-group_22">iPad</span>
        </div>
        <div class="image-text_4 flex-row justify-between">
          <img
            class="thumbnail_7"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPngbabd9249e7e7cf44e118be52df00802cef82514eb5dac2d6fa55e628132b6326.png"
          />
          <span class="text-group_23">MacOS</span>
        </div>
      </div>
      <div class="block_9 flex-row">
        <div class="image-text_5 flex-row justify-between">
          <img
            class="thumbnail_8"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng5c55174f68edcd1d1be074d72aa607641351717765ed381909b9a7fc06a62b15.png"
          />
          <span class="text-group_24">Windows</span>
        </div>
        <div class="image-text_6 flex-row justify-between">
          <img
            class="thumbnail_9"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng28b0d95e975d61e52b483c66ad4544892678bdaeea0c8cf6a17c5ac8d8913418.png"
          />
          <span class="text-group_25">Android</span>
        </div>
        <div class="image-text_7 flex-row justify-between">
          <img
            class="thumbnail_10"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng3fc0721d40e7c2d0b7e7a8b0762f52eb11e3070858bbcef2565ec507de8f3909.png"
          />
          <span class="text-group_26">浏览器</span>
        </div>
      </div>
      <img
        class="image_3"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPngc3cc6a7ca75372f0f6467730d8d155adbb9873dbeda987761f1d805e761d1909.png"
      />
    </div>
    <div class="section_35 flex-col">
      <div class="text-wrapper_23 flex-row justify-between">
        <span class="text_57">产品</span>
        <span class="text_58">资源</span>
        <span class="text_59">联系我们</span>
      </div>
      <div class="text-wrapper_24 flex-row">
        <span class="text_60">桌面端</span>
        <span class="text_61">使用教程</span>
        <span class="text_62">B站</span>
      </div>
      <div class="text-wrapper_25 flex-row">
        <span class="text_63">手机端</span>
        <span class="text_64">快速上手</span>
        <span class="text_65">抖音</span>
      </div>
      <div class="text-wrapper_26 flex-row justify-between">
        <span class="text_66">隐私协议</span>
        <span class="text_67">常见问题</span>
        <span class="text_68">小红书</span>
      </div>
      <span class="text_69">邮箱</span>
      <img
        class="image_4"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPngc3cc6a7ca75372f0f6467730d8d155adbb9873dbeda987761f1d805e761d1909.png"
      />
      <div class="text-wrapper_27">
        <span class="text_70">小马笔记</span>
        <span class="text_71">&nbsp;|</span>
        <span class="text_72">&nbsp;京ICP备2025131868号</span>
      </div>
    </div>
    <div class="section_36 flex-col">
      <span class="text_73">专为重视隐私的人打造</span>
      <div class="list_1 flex-col">
        <div class="list-items_1 flex-row justify-between" v-for="(item, index) in loopData0" :key="index">
          <img class="image_5" referrerpolicy="no-referrer" :src="item.lanhuimage0" />
          <div class="text-group_27 flex-col">
            <span class="text_74" v-html="item.lanhutext0"></span>
            <span class="text_75" v-html="item.lanhutext1"></span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'BannerCarousel',
  data() {
    return {
      productImages: [
        { image: require('./assets/img/banner_1.png'), title: '产品1' },
        { image: require('./assets/img/banner_2.png'), title: '产品2' }
      ],
      writeFeelImages: [
        { image: require('./assets/img/write_feel_1.png'), title: '手写体验1' },
        { image: require('./assets/img/write_feel_2.png'), title: '手写体验2' }
      ],
      thinkExtendImages: [
        { image: require('./assets/img/think_extend_1.png'), title: '思维发散1' },
        { image: require('./assets/img/think_extend_2.png'), title: '思维发散2' }
      ],
      modelFreedomImages: [
        { image: require('./assets/img/model_freedom_1.png'), title: '模型自由1' },
        { image: require('./assets/img/model_freedom_2.png'), title: '模型自由2' }
      ],
      loopData0: [
        {
          lanhuimage0:
            'https://lanhu-oss-proxy.lanhuapp.com/SketchPngce11e58bdb88fec8c977ec50bf05c513e1a28869930a7361845d70d67c8219bd',
          lanhutext0: '全程加密传输',
          lanhutext1: '采用行业标准&nbsp;HTTPS&nbsp;+加密协议，保障笔记在云<br/>同步过程中的数据安全'
        },
        {
          lanhuimage0:
            'https://lanhu-oss-proxy.lanhuapp.com/SketchPngfd54c13a8aad4ef5fcebd26fd739ff1cabe02fd78d2e8d539cb17147e63d8297',
          lanhutext0: '开源透明，拒绝黑箱',
          lanhutext1: '核心模块开源，代码可查，技术透明，真正做<br/>到“你看得见的安全“'
        }
      ],
      constants: {}
    };
  },
  methods: {
    onClick_1() {
      alert(1);
      // this.$router.push('/mobile/download');

      // 方案2：直接跳转并捕获错误
      this.$router.push('/mobile/download').catch(err => {
        if (err.name === 'NavigationDuplicated') {
          console.warn('重复导航已忽略');
        } else {
          console.error('跳转失败:', err);
        }
      });

    },
    
    handleProductClick(item) {
      console.log('点击了轮播图项:', item);
      // 可以根据需要添加点击处理逻辑
    }
  }
};
</script>
<style scoped lang="less" src="./assets/index.rem.less" />

