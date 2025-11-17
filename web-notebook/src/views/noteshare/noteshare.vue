<template>
  <div class="min-h-screen bg-white flex flex-col">
    <div class="h-16 bg-primary text-white flex items-center px-6 shadow-md">
        <xm-nav-common-bar></xm-nav-common-bar>
    </div>  
    <!-- 顶部加载提示 -->
    <div v-if="loading" class="flex-1 flex items-center justify-center">
      <div class="text-center">
        <div class="w-12 h-12 border-4 border-primary border-t-transparent rounded-full animate-spin mb-4"></div>
        <p class="text-gray-600">加载笔记中...</p>
      </div>
    </div>

    <!-- 错误提示 -->
    <div v-else-if="error" class="flex-1 flex items-center justify-center">
      <div class="text-center">
        <h1 class="text-2xl font-bold text-gray-800 mb-2">笔记获取失败</h1>
        <p class="text-gray-600 mb-4">请检查链接是否有效，或稍后重试</p>
        <button @click="reload" class="px-4 py-2 bg-primary text-white rounded-md hover:bg-primary/90 transition">
          重新加载
        </button>
      </div>
    </div>

    <!-- 笔记内容渲染 -->
    <div v-else class="flex-1">
      <div class="prose max-w-none px-4 py-8" v-html="noteContent"></div>
    </div>

    <!-- 浮动打开App按钮 -->
    <!-- <button
      class="fixed bottom-8 right-8 w-16 h-16 rounded-full bg-primary text-white flex items-center justify-center shadow-lg hover:bg-primary/90 transition-all z-50"
      @click="openApp"
    >
      <i class="fa fa-mobile text-2xl"></i>
      <span class="hidden md:block ml-2">打开App</span>
    </button> -->
    <!-- 浮动打开App按钮 -->
    <button
      class="openApp"
      @click="openApp"
    >
      <i class="fa fa-mobile text-2xl"></i>
      <span class="hidden md:block ml-2">打开App</span>
    </button>
  </div>
</template>

<script>
import { getNoteShareContent } from '../../api/note.js';
import XmNavCommonBar from '@/components/xm-nav-common-bar.vue';
export default {
  components: {
    XmNavCommonBar
  },
  data() {
    return {
      loading: true,
      error: false,
      noteContent: ''
    };
  },
  computed: {
    workspaceId() {
      return this.$route.query.workspaceId;
    },
    viewId() {
      return this.$route.query.viewId;
    }
  },
  methods: {
    async fetchNote() {
      try {
        this.loading = true;
        this.error = false;
        const response = await getNoteShareContent(this.workspaceId, this.viewId);
        this.noteContent = response.data;
      } catch (e) {
        this.error = true;
        console.error('笔记加载失败:', e);
      } finally {
        this.loading = false;
      }
    },
    openApp() {

      var iframe = document.createElement('iframe');
      const appUrl = `ponynotes://note?workspaceId=${this.workspaceId}&viewId=${this.viewId}`;
      iframe.style.display = 'none';
      iframe.src = appUrl;
      document.body.appendChild(iframe);

      // 尝试唤起应用
      // window.location.href = appUrl;
      // 设置超时时间（例如500ms）
      const timeout = 1000;
      const downloadUrl = location.origin + '/#/download';
      const timer = setTimeout(() => {
          // 超时后执行回退逻辑（如跳转到下载页）
          this.$toast('打开App超时，请检查是否已安装PonyNotes');
          setTimeout(() => {
            window.location.href = downloadUrl;
          }, 500);
      }, timeout);

      // 如果页面失去焦点（应用成功打开），则清除超时
      window.onblur = function() {
          clearTimeout(timer);
      };
    },
    openApp1() {
      
          // 记录当前时间，用于后续判断
      const startTime = Date.now();
      const appUrl = `appflowy-flutter://note?workspaceId=${this.workspaceId}&viewId=${this.viewId}`;
      // 下载页 URL
      const downloadUrl = location.origin + '/#/download';
      // 尝试唤起应用
      window.location.href = appUrl;

      window.location.href = 'appflowy-flutter://note?workspaceId=' + this.workspaceId + '&viewId=' + this.viewId;
      // window.location.href = "appflowy-flutter://note?viewId=test-note-id-123s";
     // 检测应用是否唤起成功
      function checkAppOpened() {
        // 计算时间差（超过 3 秒认为唤起失败）
        const timeDiff = Date.now() - startTime;
        // 检测页面可见性（如果应用被唤起，当前页面会失去焦点变为不可见）
        const isPageVisible = document.visibilityState === 'visible';

        // 若 3 秒后页面仍可见，且时间差超过阈值，说明唤起失败
        if (isPageVisible && timeDiff > 3000) {
          window.location.href = downloadUrl;
        } else if (!isPageVisible) {
          // 页面不可见，说明应用已唤起，无需跳转
          return;
        } else {
          // 继续检测（每 100ms 检测一次）
          setTimeout(checkAppOpened, 100);
        }
      }
      // 启动检测
      setTimeout(checkAppOpened, 100);
      // setTimeout(() => {
      //   window.location.href = 'https://xiaomabiji.com/#/download';
      // }, 2000);
    },
    reload() {
      this.fetchNote();
    }
  },
  mounted() {
    this.fetchNote();
  }
};
</script>
<style scoped>
/* 全局样式重置 */
:root {
  --primary: #4F46E5; /* 主色调，可替换为你的品牌色 */
}
.flex-1{
  width:100vw;
}
/* 加载动画 */
.animate-spin {
  animation: spin 1s linear infinite;
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

/* 适配markdown渲染的样式（可选） */
.prose {
  max-width: 100%;
  line-height: 1.8;
}

.prose h1, .prose h2, .prose h3 {
  margin: 1.5rem 0 1rem;
  font-weight: 600;
}

.prose p {
  margin-bottom: 1rem;
}
.openApp {
  position: fixed;
  top: 80%;
  left: 80%;
  transform: translate(-50%, -50%);
  width: 5rem;
  height: 5rem;
  border-radius: 9999px;
  background-color: #f89575;
  color: rgb(241, 236, 236);
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s ease;
  z-index: 999;
}
</style>