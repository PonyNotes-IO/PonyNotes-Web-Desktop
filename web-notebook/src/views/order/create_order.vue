<template>
  <div class="member-container">
    <div class="card">
      <!-- 头部标题 -->
      <div class="header">
        <h1 class="title">您还不是会员，开通会员即可享受36项会员特权</h1>
        <button class="close-btn" @click="onClose">
            <img src="./assets/img/close_icon.png" alt="关闭" />
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
          
          <div class="more flex-row">
            <span class="section-title flex-col">获赠权益</span>
            <span class ="more-link  flex-col">查看更多</span>
          </div>
          <div class="benefits-grid flex-row">
            <div class="benefit-card flex-colA" v-for="benefit in studentBenefits" :key="benefit.id">
              <div class="benefit-icon"  :style="{ backgroundImage: `url(${require('./assets/img/' + benefit.icon + '.png')})` }">
                <i :class="['fa', benefit.icon]"></i>
              </div>
              <p class="benefit-name">{{ benefit.name }}</p>
            </div>
          </div>

        </div>
        
        <!-- 购买时长 -->
        <div class="duration-section">
          <h2 class="section-title">选择购买时长</h2>
          <div class="duration-grid">
             <!-- :class="['tab-btn', currentTab === tab.id ? 'tab-active' : '']" -->
           
            <div :class="['duration-card', currentDuration === '30' ? 'recommended' : ''] " 
                @click="currentDuration = '30'" >
              <!-- <div class="recommended-tag">推荐</div> -->
              <p class="duration-name">包月30天</p>
              <p class="duration-price">¥3</p>
            </div>
            <div :class="['duration-card', currentDuration === '365' ? 'recommended' : ''] "
            @click="currentDuration = '365'" >
              <p class="duration-name">包月365天</p>
              <p class="duration-price">¥30</p>
            </div>
          </div>
        </div>
        
        <!-- 协议确认和支付区域 -->
        <PaymentSection :amount="paymentAmount" />
      </div>
      

      
      <!-- 标准版内容区域 -->
      <div v-if="currentTab === 'standard'" class="content">
        <!-- 获赠权益 -->
        <div class="benefits-section">
          
          <div class="more flex-row">
            <span class="section-title flex-col">获赠权益</span>
            <span class ="more-link  flex-col">查看更多</span>
          </div>
          <div class="benefits-grid flex-row">
            <div class="benefit-card flex-col" v-for="benefit in studentBenefits" :key="benefit.id">
              <div class="benefit-icon"  :style="{ backgroundImage: `url(${require('./assets/img/' + benefit.icon + '.png')})` }">
                <i :class="['fa', benefit.icon]"></i>
              </div>
              <p class="benefit-name">{{ benefit.name }}</p>
            </div>
          </div>

        </div>
        
        <!-- 购买时长 -->
        <div class="duration-section">
          <h2 class="section-title">选择购买时长</h2>
          <div class="duration-grid">
             <!-- :class="['tab-btn', currentTab === tab.id ? 'tab-active' : '']" -->
           
            <div :class="['duration-card', currentDuration === '30' ? 'recommended' : ''] " 
                @click="currentDuration = '30'" >
              <!-- <div class="recommended-tag">推荐</div> -->
              <p class="duration-name">包月30天</p>
              <p class="duration-price">¥8</p>
            </div>
            <div :class="['duration-card', currentDuration === '365' ? 'recommended' : ''] "
            @click="currentDuration = '365'" >
              <p class="duration-name">包月365天</p>
              <p class="duration-price">¥80</p>
            </div>
          </div>
        </div>
        
        <!-- 协议确认和支付区域 -->
       <PaymentSection :amount="paymentAmount" />
      </div>
      
      <!-- 团队版内容区域 -->
      <div v-if="currentTab === 'team'" class="content">
        <!-- 获赠权益 -->
        <div class="benefits-section">
          
          <div class="more flex-row">
            <span class="section-title flex-col">获赠权益</span>
            <span class ="more-link  flex-col">查看更多</span>
          </div>
          <div class="benefits-grid flex-row">
            <div class="benefit-card flex-colA" v-for="benefit in studentBenefits" :key="benefit.id">
              <div class="benefit-icon"  :style="{ backgroundImage: `url(${require('./assets/img/' + benefit.icon + '.png')})` }">
                <i :class="['fa', benefit.icon]"></i>
              </div>
              <p class="benefit-name">{{ benefit.name }}</p>
            </div>
          </div>

        </div>
        
        <!-- 购买时长 -->
        <div class="duration-section">
          <h2 class="section-title">选择购买时长</h2>
          <div class="duration-grid">
             <!-- :class="['tab-btn', currentTab === tab.id ? 'tab-active' : '']" -->
           
            <div :class="['duration-card', currentDuration === '30' ? 'recommended' : ''] " 
                @click="currentDuration = '30'" >
              <!-- <div class="recommended-tag">推荐</div> -->
              <p class="duration-name">包月30天</p>
              <p class="duration-price">¥18</p>
            </div>
            <div :class="['duration-card', currentDuration === '365' ? 'recommended' : ''] "
            @click="currentDuration = '365'" >
              <p class="duration-name">包月365天</p>
              <p class="duration-price">¥180</p>
            </div>
          </div>
        </div>
        
        <!-- 协议确认和支付区域 -->
         <PaymentSection :amount="paymentAmount" />
      </div>

      <!-- 内容区域 - 会员补充包 -->
      <div v-if="currentTab === 'addon'" class="content">
        <!-- 个人云存储空间 -->
        <div class="storage-section">
          <div class="storage-header">
            <span class="section-title">个人云存储空间</span>
            <span class="more-link">查看更多</span>
          </div>
          
          <div class="storage-grid">
            <div :class="['storage-card', currentAddon  === '5' ? 'active' : '']"
            @click="currentAddon = '5'" >
              <h3 class="storage-name">A扩充方案</h3>
              <p class="storage-desc">存储空间 5G</p>
              <p class="storage-desc">Altken 100次/天</p>
              <p class="storage-price">5元/月</p>
            </div>
            <div :class="['storage-card', currentAddon  === '15' ? 'active' : '']"
            @click="currentAddon  = '15'">
              <h3 class="storage-name">B扩充方案</h3>
              <p class="storage-desc">Altken 500次/天</p>
              <p class="storage-price">15元/月</p>
            </div>
            <div :class="['storage-card', currentAddon  === '25' ? 'active' : '']"
            @click="currentAddon  = '25'">
              <h3 class="storage-name">C扩充方案</h3>
              <p class="storage-desc">存储空间 5G</p>
              <p class="storage-desc">Altken 100次/天</p>
              <p class="storage-price">25元/月</p>
            </div>
          </div>
        </div>
        
        <!-- 协议确认和支付区域 -->
        <PaymentSection :amount="paymentAmount" :checked="true" />
      </div>

    </div>
  </div>
</template>

<script>
import PaymentSection from './payment_section.vue';

export default {
  components: {
    PaymentSection
  },
  props: {
    packageInfo: { // 父组件传递的套餐对象
      type: Object,
      default: () => ({}) // 默认空对象，避免报错
    },
    payType: {
      type: Number
    },
  },
  data() {
    return {
      currentTab: 'student',
      currentDuration: '30', // '1' 月付，'2' 年付
      currentPayType: 0, // 存储支付类型
      currentAddon: '5',
      iconImages: {
        ai_icon: require('./assets/img/ai_icon.png'),
        calendar_icon: require('./assets/img/calendar_icon.png'),
        favorite_icon: require('./assets/img/favorite_icon.png'),
        cloud_icon: require('./assets/img/cloud_icon.png'),
        storage_icon: require('./assets/img/storage_icon.png'),
      },
      tabs: [
        { id: 'student', name: '学生版' },
        { id: 'standard', name: '标准版' },
        { id: 'team', name: '团队版' },
        { id: 'addon', name: '会员补充包' }
      ],
      studentBenefits: [
        { id: 1, name: '小马AI', icon: 'ai_icon' },
        { id: 2, name: '小马日历', icon: 'calendar_icon' },
        { id: 3, name: '小马收藏夹', icon: 'favorite_icon' },
        { id: 4, name: '云端同步', icon: 'cloud_icon' },
        { id: 5, name: '100T空间', icon: 'storage_icon' }
      ]
    };
  },
  created() {
    // 若父组件传递了套餐对象且包含 packageType，则切换到对应标签页
    if (this.packageInfo && this.packageInfo.packageType) {
      this.currentTab = this.packageInfo.packageType;
    }
    if (this.payType) {
        this.currentPayType = this.payType;
        console.log('payType:', this.payType);
        // 根据支付类型设置时长
        if (this.currentPayType === 1) { // 月付
            this.currentDuration = '30';
            console.log('currentDuration:', this.currentDuration);
        } else if (this.currentPayType === 2) { // 年付
            this.currentDuration = '365';
             console.log('currentDuration:', this.currentDuration);
        } else{
            this.currentDuration = ''; // 默认月付
             console.log('currentDuration:', this.currentDuration);
        }
    }
  },
  computed: {
    paymentAmount() {
      // 价格映射表：[套餐类型, 时长] → 价格
      const priceMap = {
        'student_30': 3,
        'student_365': 30,
        'standard_30': 8,
        'standard_365': 80,
        'team_30': 18,
        'team_365': 180,
        'addon_5': 5,
        'addon_15': 15,
        'addon_25': 25
      };
      // 拼接套餐类型和时长，从映射表取价格
      const key = this.currentTab === 'addon'?`addon_${this.currentAddon}`:`${this.currentTab}_${this.currentDuration}`;
      return priceMap[key] || 0; // 无匹配时默认0
    }
  },
  methods: {
    onClose() {
        this.purchaseVisible = false;
        // 方式2：向父组件发送关闭事件
        this.$emit('close');
    }
  }
};
</script>
<style scoped lang="less" src="./assets/create_order.less" />
