// src/mixins/scaleMixin.js
export default {
  data() {
    return {
      observer: null, // 用于存储 IntersectionObserver 实例
    };
  },
  mounted() {
    this.handleResize();
    window.addEventListener('resize', this.handleResize);
    this.initIntersectionObserver();
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.handleResize);
    if (this.observer) this.observer.disconnect();
  },
  methods: {
    handleResize() {
      const baseWidth = 1920; // 设计稿基准宽度
      const scale = window.innerWidth / baseWidth;
      // 注意：需确保每个使用该混入的页面，根元素都有 .scale-root 类名
      const scaleRoot = document.querySelector('.scale-root');
      if (scaleRoot) {
        scaleRoot.style.transform = `scale(${scale})`;
        scaleRoot.style.transformOrigin = 'top left';
        scaleRoot.style.width = `${baseWidth}px`;
        scaleRoot.style.height = '120%';
      }
    },
    initIntersectionObserver() {
      const items = document.querySelectorAll('.list-items_1');
      if (items.length) {
        this.observer = new IntersectionObserver(
          (entries) => {
            entries.forEach((entry) => {
              if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                this.observer.unobserve(entry.target);
              }
            });
          },
          { threshold: 0.5 }
        );
        items.forEach((item) => this.observer.observe(item));
      }
    },
  },
};