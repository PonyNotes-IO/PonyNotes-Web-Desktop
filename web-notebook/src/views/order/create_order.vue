<template>
  <div class="member-container">
    <div class="card">
      <!-- 头部标题 -->
      <div class="header">
        <h1 class="title">您还不是会员，开通会员即可享受36项会员特权</h1>
        <button class="close-btn" @click="onClose">
          <i class="fa fa-times"></i>
        </button>
      </div>
      
      <!-- 会员类型标签 -->
      <div class="tabs-container">
        <div class="tabs">
          <button 
            v-for="tab in tabs" 
            :key="tab.id"
            :class="['tab-btn', currentTab === tab.id ? 'tab-active' : '']"
            @click="currentTab = tab.id"
          >
            {{ tab.name }}
          </button>
        </div>
      </div>
      
      <!-- 内容区域 - 学生版 -->
      <div v-if="currentTab === 'student'" class="content">
        <!-- 获赠权益 -->
        <div class="benefits-section">
          <h2 class="section-title">获赠权益</h2>
          <div class="benefits-grid">
            <div class="benefit-card" v-for="benefit in studentBenefits" :key="benefit.id">
              <div class="benefit-icon">
                <i :class="['fa', benefit.icon]"></i>
              </div>
              <p class="benefit-name">{{ benefit.name }}</p>
            </div>
          </div>
          <div class="more-link">
            <a href="#">查看更多</a>
          </div>
        </div>
        
        <!-- 购买时长 -->
        <div class="duration-section">
          <h2 class="section-title">选择购买时长</h2>
          <div class="duration-grid">
            <div class="duration-card recommended">
              <div class="recommended-tag">推荐</div>
              <p class="duration-name">包月30天</p>
              <p class="duration-price">¥3</p>
            </div>
            <div class="duration-card">
              <p class="duration-name">包月365天</p>
              <p class="duration-price">¥30</p>
              <p class="duration-desc">平均每天0.08元</p>
            </div>
          </div>
        </div>
        
        <!-- 协议确认和支付区域 -->
        <PaymentSection :amount="3" />
      </div>
      
      <!-- 内容区域 - 会员补充包 -->
      <div v-if="currentTab === 'addon'" class="content">
        <!-- 个人云存储空间 -->
        <div class="storage-section">
          <div class="storage-header">
            <h2 class="section-title">个人云存储空间</h2>
            <a href="#" class="more-link">查看更多</a>
          </div>
          
          <div class="storage-grid">
            <div class="storage-card active">
              <h3 class="storage-name">A扩充方案</h3>
              <p class="storage-desc">存储空间 5G</p>
              <p class="storage-desc">Altken 100次/天</p>
              <p class="storage-price">5元/月</p>
            </div>
            <div class="storage-card">
              <h3 class="storage-name">B扩充方案</h3>
              <p class="storage-desc">Altken 500次/天</p>
              <p class="storage-price">15元/月</p>
            </div>
            <div class="storage-card">
              <h3 class="storage-name">C扩充方案</h3>
              <p class="storage-desc">存储空间 5G</p>
              <p class="storage-desc">Altken 100次/天</p>
              <p class="storage-price">25元/月</p>
            </div>
          </div>
        </div>
        
        <!-- 协议确认和支付区域 -->
        <PaymentSection :amount="3" :checked="true" />
      </div>
      
      <!-- 标准版内容区域 -->
      <div v-if="currentTab === 'standard'" class="content">
        <div class="empty-state">
          <p>标准版会员权益内容</p>
        </div>
      </div>
      
      <!-- 团队版内容区域 -->
      <div v-if="currentTab === 'team'" class="content">
        <div class="empty-state">
          <p>团队版会员权益内容</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PaymentSection from './payment_secion.vue';

export default {
  components: {
    PaymentSection
  },
  data() {
    return {
      currentTab: 'student',
      tabs: [
        { id: 'student', name: '学生版' },
        { id: 'standard', name: '标准版' },
        { id: 'team', name: '团队版' },
        { id: 'addon', name: '会员补充包' }
      ],
      studentBenefits: [
        { id: 1, name: '小马AI', icon: 'fa-robot' },
        { id: 2, name: '小马日历', icon: 'fa-calendar' },
        { id: 3, name: '小马收藏夹', icon: 'fa-star' },
        { id: 4, name: '云端同步', icon: 'fa-cloud' },
        { id: 5, name: '100T空间', icon: 'fa-hdd-o' }
      ]
    };
  },
  methods: {
    onClose() {
      // 关闭逻辑，可根据实际需求实现
      console.log('关闭会员页面');
    }
  }
};
</script>
<style scoped lang="less" src="./assets/create_order.less" />
