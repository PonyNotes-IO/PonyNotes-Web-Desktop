<!-- app/layouts/default.vue -->
<template>
  <div class="min-h-screen flex flex-col">
    <!-- 页面主体内容 -->
    <slot />

    <!-- 底部组件 -->
    <AppFooter />
  </div>
</template>