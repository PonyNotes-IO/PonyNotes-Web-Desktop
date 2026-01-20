<!-- app/layouts/default.vue -->
<script setup lang="ts">
const userStore = useUserStore()
onMounted(() => {
    userStore.initUser()
})
</script>

<template>
  <div class="min-h-screen flex flex-col">
    <!-- 顶部组件 -->
    <AppHeader />

    <!-- 页面主体内容 -->
    <slot />

    <!-- 底部组件 -->
    <AppFooter />
  </div>
</template>