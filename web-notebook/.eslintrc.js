// .eslintrc.js
module.exports = {
  env: {
    browser: true,
    // 替换 es2021 为 es2020 或 es6
    es2020: true, // 或 es6: true
    node: true
  },
  parser: 'vue-eslint-parser',
  parserOptions: {
    ecmaVersion: 2018, // 与 env 版本保持一致
    sourceType: "module"
  },
  extends: [
    "eslint:recommended",
    "plugin:vue/essential"  // 必须添加，提供 Vue 语法支持
 ],
  plugins: ["vue"],
  globals: {
    defineProps: "readonly",
    defineEmits: "readonly"
  },
  rules: {
    "vue/no-deprecated-destroyed-lifecycle": "off",  // 关闭该检测规则
    'vue/multi-word-component-names': 'off', // 关闭组件命名规则检测
  }
};