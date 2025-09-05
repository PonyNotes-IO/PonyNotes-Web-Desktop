<template>
  <div class="mobile-input" :class="{ 'has-error': error }">
    <input
      :type="type"
      :placeholder="placeholder"
      :value="value"
      @input="$emit('input', $event.target.value)"
      @focus="handleFocus"
      @blur="handleBlur"
    />
    <div v-if="error" class="error-message">{{ error }}</div>
  </div>
</template>

<script>
export default {
  name: 'MobileInput',
  props: {
    value: {
      type: [String, Number],
      default: ''
    },
    type: {
      type: String,
      default: 'text'
    },
    placeholder: {
      type: String,
      default: ''
    },
    error: {
      type: String,
      default: ''
    }
  },
  methods: {
    handleFocus() {
      this.$emit('focus')
    },
    handleBlur() {
      this.$emit('blur')
    }
  }
}
</script>

<style lang="less" scoped>
.mobile-input {
  margin-bottom: 16px;

  input {
    width: 100%;
    padding: 12px 16px;
    background-color: #222;
    border: 1px solid #333;
    border-radius: 8px;
    color: #fff;
    font-size: 16px;
    outline: none;
    transition: border-color 0.3s;

    &:focus {
      border-color: #ff6b00;
    }

    &::placeholder {
      color: #666;
    }
  }

  &.has-error {
    input {
      border-color: #ff3b30;
    }
  }

  .error-message {
    color: #ff3b30;
    font-size: 12px;
    margin-top: 4px;
  }
}
</style>
