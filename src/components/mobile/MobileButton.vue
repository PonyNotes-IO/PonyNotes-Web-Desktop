<template>
  <button
    class="mobile-button"
    :class="[type, { small: small, disabled: disabled }]"
    :disabled="disabled"
    @click="handleClick"
  >
    <slot></slot>
  </button>
</template>

<script>
export default {
  name: 'MobileButton',
  props: {
    type: {
      type: String,
      default: 'primary', // primary, secondary, text
      validator: value => ['primary', 'secondary', 'text'].includes(value)
    },
    small: {
      type: Boolean,
      default: false
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    handleClick() {
      this.$emit('click')
    }
  }
}
</script>

<style lang="less" scoped>
.mobile-button {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  padding: 12px 16px;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s;
  border: none;
  outline: none;
  width: 100%;

  &.primary {
    background-color: #ff6b00;
    color: #fff;

    &:hover:not(.disabled) {
      background-color: #ff8c00;
    }

    &.disabled {
      background-color: #666;
      cursor: not-allowed;
    }
  }

  &.secondary {
    background-color: #333;
    color: #fff;

    &:hover:not(.disabled) {
      background-color: #444;
    }

    &.disabled {
      background-color: #222;
      color: #666;
      cursor: not-allowed;
    }
  }

  &.text {
    background-color: transparent;
    color: #ff6b00;

    &:hover:not(.disabled) {
      color: #ff8c00;
    }

    &.disabled {
      color: #666;
      cursor: not-allowed;
    }
  }

  &.small {
    padding: 8px 12px;
    font-size: 14px;
    width: auto;
  }
}
</style>
