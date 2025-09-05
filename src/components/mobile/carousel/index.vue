
<template>
  <div class="carousel-wrapper">
    <!-- 轮播图容器 -->
    <div 
      class="carousel-container"
      ref="carouselContainer"
      @touchstart="startDrag"
      @touchmove="handleDrag"
      @touchend="endDrag"
    >
      <!-- 轮播图轨道 -->
      <div 
        class="carousel-track" 
        :style="trackStyle"
      >
        <div 
          class="carousel-item" 
          v-for="(item, index) in items" 
          :key="index"
          @click="handleItemClick(item)"
        >
          <img 
            :src="item.image" 
            :alt="item.title"
            class="carousel-image"
          >
        </div>
      </div>
      
      <!-- 导航指示器 -->
      <div class="carousel-indicators" v-if="showIndicators">
        <span 
          v-for="(item, index) in items" 
          :key="index"
          :class="{ 'active': currentIndex === index }"
          @click="goToSlide(index)"
        ></span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MobileCarousel',
  props: {
    items: {
      type: Array,
      required: true,
      default: () => []
    },
    interval: {
      type: Number,
      default: 3000
    },
    autoPlay: {
      type: Boolean,
      default: true
    },
    showIndicators: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      currentIndex: 0,
      intervalId: null,
      isDragging: false,
      startPos: 0,
      currentTranslate: 0,
      prevTranslate: 0
    }
  },
  computed: {
    trackStyle() {
      return {
        transform: `translateX(-${this.currentIndex * 100}%)`,
        transition: this.isDragging ? 'none' : 'transform 0.5s ease'
      }
    }
  },
  methods: {
    // 自动轮播控制
    startAutoPlay() {
      if (!this.autoPlay) return
      this.stopAutoPlay()
      this.intervalId = setInterval(() => {
        this.currentIndex = (this.currentIndex + 1) % this.items.length
      }, this.interval)
    },
    stopAutoPlay() {
      if (this.intervalId) {
        clearInterval(this.intervalId)
        this.intervalId = null
      }
    },
    
    // 拖拽相关逻辑（移动端触摸事件）
    startDrag(e) {
      this.isDragging = true
      this.startPos = e.touches[0].clientX
      this.stopAutoPlay()
    },
    
    handleDrag(e) {
      if (!this.isDragging) return
      const currentPos = e.touches[0].clientX
      const diff = currentPos - this.startPos
      this.currentTranslate = this.prevTranslate + diff
    },
    
    endDrag() {
      if (!this.isDragging) return
      
      const threshold = this.$refs.carouselContainer.offsetWidth * 0.2
      const movedBy = this.currentTranslate - this.prevTranslate
      
      if (movedBy < -threshold) {
        // 向左滑动，切换到下一张
        this.currentIndex = (this.currentIndex + 1) % this.items.length
      } else if (movedBy > threshold) {
        // 向右滑动，切换到上一张
        this.currentIndex = (this.currentIndex - 1 + this.items.length) % this.items.length
      }
      
      this.isDragging = false
      this.prevTranslate = -this.currentIndex * this.$refs.carouselContainer.offsetWidth
      this.startAutoPlay()
    },
    
    // 跳转到指定幻灯片
    goToSlide(index) {
      this.stopAutoPlay()
      this.currentIndex = index
      this.startAutoPlay()
    },
    
    // 处理点击事件
    handleItemClick(item) {
      this.$emit('item-click', item)
    }
  },
  
  mounted() {
    this.startAutoPlay()
    // 初始化拖拽位置
    this.$nextTick(() => {
      if (this.$refs.carouselContainer) {
        this.prevTranslate = -this.currentIndex * this.$refs.carouselContainer.offsetWidth
      }
    })
  },
  
  beforeDestroy() {
    this.stopAutoPlay()
  }
}
</script>

<style scoped>
.carousel-wrapper {
  width: 100%;
  padding: 0 0px;
  margin: 0px 0 0px 0;
}

.carousel-container {
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;
  touch-action: pan-y;
  box-shadow: 0px 0px 6px 0px rgba(0, 0, 0, 0.2);
  border-radius: 8px;
}

.carousel-track {
  display: flex;
  height: 100%;
  will-change: transform;
}

.carousel-item {
  flex: 0 0 100%;
  height: 100%;
  position: relative;
}

.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 8px;
  border: 0.5px solid rgba(0, 0, 0, 0.15);
}

.carousel-indicators {
  position: absolute;
  bottom: 10px;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  gap: 6px;
}

.carousel-indicators span {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.4);
  cursor: pointer;
  transition: all 0.3s ease;
}

.carousel-indicators span.active {
  background-color: #ff7e33;
  transform: scale(1.2);
}

/* 移动端适配 */
@media (max-width: 767px) {
  .carousel-container {
    height: 210px;
  }
  
  .carousel-indicators span {
    width: 6px;
    height: 6px;
  }
}
</style>