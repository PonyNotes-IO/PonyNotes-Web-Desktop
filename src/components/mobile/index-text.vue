<template>
  <div class="text-section">
    <h2 v-if="title">{{ title }}</h2>
    <p v-if="content">{{ content }}</p>
    <slot></slot>
  </div>
</template>

<script>
export default {
  props: ['title', 'content']
}
</script>
