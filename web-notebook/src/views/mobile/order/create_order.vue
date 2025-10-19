<template>
  <div id="app" class="min-h-screen flex flex-col bg-gray-100 font-sans">
    <!-- 页面内容 -->
    <main class="flex-grow container mx-auto px-4 py-8 max-w-md">
      <!-- 会员套餐选择页面 -->
      <div v-if="currentPage === 'packages'" class="page active">
        <div class="bg-white rounded-xl shadow-card p-6 mb-6">
          <div class="flex justify-between items-center mb-6">
            <h1 class="text-xl font-bold text-gray-800">开通会员</h1>
            <button class="text-gray-400 hover:text-gray-600" @click="closePage">
              <i class="fa fa-times text-xl"></i>
            </button>
          </div>
          
          <p class="text-gray-600 mb-6">选择适合您的会员套餐，享受更多特权</p>
          
          <!-- 套餐卡片列表 -->
          <div class="space-y-4 mb-8">
            <!-- 学生版 -->
            <div 
              class="package-card border rounded-lg p-4 cursor-pointer card-hover" 
              :class="{ 'border-primary bg-primary/5': selectedPackage === 'student' }"
              @click="selectPackage('student')"
            >
              <div class="flex justify-between items-start mb-3">
                <div>
                  <h3 class="font-bold text-gray-800">学生版</h3>
                  <p class="text-sm text-gray-500">适合学生使用的基础套餐</p>
                </div>
                <span class="bg-primary/10 text-primary text-xs px-2 py-1 rounded-full">推荐</span>
              </div>
              
              <div class="flex space-x-4 mb-3">
                <div 
                  class="duration-option flex-1 border rounded p-2 text-center cursor-pointer"
                  :class="{ 'border-primary bg-primary/5': selectedPackage === 'student' && selectedDuration === 'monthly' }"
                  @click.stop="selectDuration('student', 'monthly')"
                >
                  <p class="text-sm text-gray-500">包月</p>
                  <p class="font-bold text-gray-800">¥3<span class="text-xs text-gray-400">/月</span></p>
                </div>
                <div 
                  class="duration-option flex-1 border rounded p-2 text-center cursor-pointer"
                  :class="{ 'border-primary bg-primary/5': selectedPackage === 'student' && selectedDuration === 'yearly' }"
                  @click.stop="selectDuration('student', 'yearly')"
                >
                  <p class="text-sm text-gray-500">包年</p>
                  <p class="font-bold text-gray-800">¥30<span class="text-xs text-gray-400">/年</span></p>
                </div>
              </div>
              
              <div class="benefits">
                <p class="text-xs text-gray-500 mb-2">包含权益：</p>
                <div class="flex flex-wrap gap-2">
                  <span class="bg-gray-100 text-gray-600 text-xs px-2 py-1 rounded" v-for="benefit in packages.student.benefits" :key="benefit">{{ benefit }}</span>
                </div>
              </div>
            </div>
            
            <!-- 标准版 -->
            <div 
              class="package-card border rounded-lg p-4 cursor-pointer card-hover" 
              :class="{ 'border-primary bg-primary/5': selectedPackage === 'standard' }"
              @click="selectPackage('standard')"
            >
              <div class="flex justify-between items-start mb-3">
                <div>
                  <h3 class="font-bold text-gray-800">标准版</h3>
                  <p class="text-sm text-gray-500">适合个人日常使用</p>
                </div>
              </div>
              
              <div class="flex space-x-4 mb-3">
                <div 
                  class="duration-option flex-1 border rounded p-2 text-center cursor-pointer"
                  :class="{ 'border-primary bg-primary/5': selectedPackage === 'standard' && selectedDuration === 'monthly' }"
                  @click.stop="selectDuration('standard', 'monthly')"
                >
                  <p class="text-sm text-gray-500">包月</p>
                  <p class="font-bold text-gray-800">¥8<span class="text-xs text-gray-400">/月</span></p>
                </div>
                <div 
                  class="duration-option flex-1 border rounded p-2 text-center cursor-pointer"
                  :class="{ 'border-primary bg-primary/5': selectedPackage === 'standard' && selectedDuration === 'yearly' }"
                  @click.stop="selectDuration('standard', 'yearly')"
                >
                  <p class="text-sm text-gray-500">包年</p>
                  <p class="font-bold text-gray-800">¥80<span class="text-xs text-gray-400">/年</span></p>
                </div>
              </div>
              
              <div class="benefits">
                <p class="text-xs text-gray-500 mb-2">包含权益：</p>
                <div class="flex flex-wrap gap-2">
                  <span class="bg-gray-100 text-gray-600 text-xs px-2 py-1 rounded" v-for="benefit in packages.standard.benefits" :key="benefit">{{ benefit }}</span>
                </div>
              </div>
            </div>
            
            <!-- 团队版 -->
            <div 
              class="package-card border rounded-lg p-4 cursor-pointer card-hover" 
              :class="{ 'border-primary bg-primary/5': selectedPackage === 'team' }"
              @click="selectPackage('team')"
            >
              <div class="flex justify-between items-start mb-3">
                <div>
                  <h3 class="font-bold text-gray-800">团队版</h3>
                  <p class="text-sm text-gray-500">适合团队协作使用</p>
                </div>
              </div>
              
              <div class="flex space-x-4 mb-3">
                <div 
                  class="duration-option flex-1 border rounded p-2 text-center cursor-pointer"
                  :class="{ 'border-primary bg-primary/5': selectedPackage === 'team' && selectedDuration === 'monthly' }"
                  @click.stop="selectDuration('team', 'monthly')"
                >
                  <p class="text-sm text-gray-500">包月</p>
                  <p class="font-bold text-gray-800">¥18<span class="text-xs text-gray-400">/月</span></p>
                </div>
                <div 
                  class="duration-option flex-1 border rounded p-2 text-center cursor-pointer"
                  :class="{ 'border-primary bg-primary/5': selectedPackage === 'team' && selectedDuration === 'yearly' }"
                  @click.stop="selectDuration('team', 'yearly')"
                >
                  <p class="text-sm text-gray-500">包年</p>
                  <p class="font-bold text-gray-800">¥180<span class="text-xs text-gray-400">/年</span></p>
                </div>
              </div>
              
              <div class="benefits">
                <p class="text-xs text-gray-500 mb-2">包含权益：</p>
                <div class="flex flex-wrap gap-2">
                  <span class="bg-gray-100 text-gray-600 text-xs px-2 py-1 rounded" v-for="benefit in packages.team.benefits" :key="benefit">{{ benefit }}</span>
                </div>
              </div>
            </div>
            
            <!-- 会员补充包 -->
            <div 
              class="package-card border rounded-lg p-4 cursor-pointer card-hover" 
              :class="{ 'border-primary bg-primary/5': selectedPackage === 'addon' }"
              @click="selectPackage('addon')"
            >
              <div class="flex justify-between items-start mb-3">
                <div>
                  <h3 class="font-bold text-gray-800">会员补充包</h3>
                  <p class="text-sm text-gray-500">扩展存储空间和功能</p>
                </div>
              </div>
              
              <div class="flex space-x-4 mb-3">
                <div 
                  class="duration-option flex-1 border rounded p-2 text-center cursor-pointer"
                  :class="{ 'border-primary bg-primary/5': selectedPackage === 'addon' && selectedDuration === 'monthly' }"
                  @click.stop="selectDuration('addon', 'monthly')"
                >
                  <p class="text-sm text-gray-500">包月</p>
                  <p class="font-bold text-gray-800">¥5<span class="text-xs text-gray-400">/月</span></p>
                </div>
              </div>
              
              <div class="benefits">
                <p class="text-xs text-gray-500 mb-2">包含权益：</p>
                <div class="flex flex-wrap gap-2">
                  <span class="bg-gray-100 text-gray-600 text-xs px-2 py-1 rounded" v-for="benefit in packages.addon.benefits" :key="benefit">{{ benefit }}</span>
                </div>
              </div>
            </div>
          </div>
          
          <!-- 底部操作区 -->
          <div class="border-t pt-4">
            <div class="flex justify-between items-center mb-4">
              <span class="text-gray-600">已选：</span>
              <span class="font-medium text-gray-800">{{ selectedPackage ? `${packages[selectedPackage].name} (${selectedDuration === 'monthly' ? '月付' : '年付'})` : '未选择套餐' }}</span>
            </div>
            <div class="flex justify-between items-center mb-6">
              <span class="text-gray-600">应付金额：</span>
              <span class="font-bold text-xl text-primary">¥{{ selectedPackage && selectedDuration ? packages[selectedPackage].price[selectedDuration] : 0 }}</span>
            </div>
            <button 
              class="w-full flex items-center justify-center"
              :class="(selectedPackage && selectedDuration) ? 'btn-primary' : 'btn-disabled'"
              :disabled="!selectedPackage || !selectedDuration"
              @click="goToPayment"
            >
              <span>下一步</span>
              <i class="fa fa-arrow-right ml-2"></i>
            </button>
          </div>
        </div>
        
        <!-- 协议提示 -->
        <div class="text-center text-xs text-gray-400 mt-4">
          <p>点击"下一步"即表示您同意</p>
          <p class="mt-1">《会员服务协议》和《隐私政策》</p>
        </div>
      </div>
      
      <!-- 支付确认页面 -->
      <div v-if="currentPage === 'payment'" class="page">
        <div class="bg-white rounded-xl shadow-card p-6 mb-6">
          <div class="flex justify-between items-center mb-6">
            <h1 class="text-xl font-bold text-gray-800">确认支付</h1>
            <button class="text-gray-400 hover:text-gray-600" @click="currentPage = 'packages'">
              <i class="fa fa-arrow-left text-xl"></i>
            </button>
          </div>
          
          <!-- 支付信息 -->
          <div class="mb-8">
            <div class="flex items-center mb-6">
              <div class="w-24 h-24 bg-gray-100 rounded-lg flex items-center justify-center mr-6">
                <img :src="paymentQrcode" alt="支付二维码" class="w-20 h-20 object-contain">
              </div>
              <div>
                <p class="text-gray-500 text-sm">应付金额</p>
                <p class="text-2xl font-bold text-primary">¥{{ selectedPackage && selectedDuration ? packages[selectedPackage].price[selectedDuration] : 0 }}</p>
              </div>
            </div>
            
            <div class="bg-gray-50 rounded-lg p-4 mb-6">
              <h3 class="font-medium text-gray-800 mb-3">订单信息</h3>
              <div class="space-y-2 text-sm">
                <div class="flex justify-between">
                  <span class="text-gray-500">套餐名称</span>
                  <span class="text-gray-800">{{ selectedPackage ? packages[selectedPackage].name : '-' }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-500">购买时长</span>
                  <span class="text-gray-800">{{ selectedDuration === 'monthly' ? '月付' : selectedDuration === 'yearly' ? '年付' : '-' }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-500">订单编号</span>
                  <span class="text-gray-800">{{ orderId }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-500">创建时间</span>
                  <span class="text-gray-800">{{ orderTime }}</span>
                </div>
              </div>
            </div>
            
            <!-- 支付方式选择 -->
            <div class="mb-6">
              <h3 class="font-medium text-gray-800 mb-3">选择支付方式</h3>
              <div class="flex space-x-4">
                <div 
                  class="payment-method flex-1 border rounded-lg p-3 text-center cursor-pointer"
                  :class="{ 'border-primary bg-primary/5': selectedPaymentMethod === 'wechat' }"
                  @click="selectPaymentMethod('wechat')"
                >
                  <div class="flex justify-center mb-2">
                    <i class="fa fa-weixin text-2xl text-green-500"></i>
                  </div>
                  <p class="text-sm">微信支付</p>
                </div>
                <div 
                  class="payment-method flex-1 border rounded-lg p-3 text-center cursor-pointer"
                  :class="{ 'border-primary bg-primary/5': selectedPaymentMethod === 'alipay' }"
                  @click="selectPaymentMethod('alipay')"
                >
                  <div class="flex justify-center mb-2">
                    <i class="fa fa-credit-card text-2xl text-blue-500"></i>
                  </div>
                  <p class="text-sm">支付宝</p>
                </div>
              </div>
            </div>
            
            <!-- 支付提示 -->
            <div class="bg-yellow-50 border border-yellow-200 rounded-lg p-3 text-sm text-yellow-700 mb-6">
              <p class="flex items-start">
                <i class="fa fa-exclamation-circle mt-0.5 mr-2"></i>
                <span>请在15分钟内完成支付，超时后订单将自动取消</span>
              </p>
            </div>
          </div>
          
          <!-- 支付按钮 -->
          <button 
            class="w-full flex items-center justify-center"
            :class="selectedPaymentMethod ? 'btn-primary' : 'btn-disabled'"
            :disabled="!selectedPaymentMethod"
            @click="processPayment"
          >
            <i class="fa fa-weixin mr-2" v-if="selectedPaymentMethod === 'wechat'"></i>
            <i class="fa fa-credit-card mr-2" v-if="selectedPaymentMethod === 'alipay'"></i>
            <span>使用{{ selectedPaymentMethod === 'wechat' ? '微信' : selectedPaymentMethod === 'alipay' ? '支付宝' : '微信或者支付宝' }}支付</span>
          </button>
        </div>
        
        <!-- 支付帮助 -->
        <div class="bg-white rounded-xl shadow-card p-4">
          <h3 class="font-medium text-gray-800 mb-3">支付遇到问题？</h3>
          <div class="space-y-2 text-sm">
            <a href="#" class="flex items-center text-gray-600 hover:text-primary">
              <i class="fa fa-question-circle mr-2"></i>
              <span>支付后未到账怎么办？</span>
            </a>
            <a href="#" class="flex items-center text-gray-600 hover:text-primary">
              <i class="fa fa-question-circle mr-2"></i>
              <span>如何查看我的订单？</span>
            </a>
            <a href="#" class="flex items-center text-gray-600 hover:text-primary">
              <i class="fa fa-question-circle mr-2"></i>
              <span>联系客服</span>
            </a>
          </div>
        </div>
      </div>
      
      <!-- 支付结果页面 -->
      <div v-if="currentPage === 'result'" class="page">
        <div class="bg-white rounded-xl shadow-card p-6 mb-6">
          <div class="flex justify-between items-center mb-6">
            <h1 class="text-xl font-bold text-gray-800">支付结果</h1>
            <button class="text-gray-400 hover:text-gray-600" @click="currentPage = 'packages'">
              <i class="fa fa-times text-xl"></i>
            </button>
          </div>
          
          <!-- 支付成功 -->
          <div v-if="paymentResult?.success" class="text-center py-6">
            <div class="w-16 h-16 bg-success/10 rounded-full flex items-center justify-center mx-auto mb-4">
              <i class="fa fa-check text-2xl text-success"></i>
            </div>
            <h2 class="text-xl font-bold text-gray-800 mb-2">支付成功</h2>
            <p class="text-gray-500 mb-6">您已成功开通会员，感谢您的支持！</p>
            
            <div class="bg-gray-50 rounded-lg p-4 mb-6 text-left">
              <div class="space-y-2 text-sm">
                <div class="flex justify-between">
                  <span class="text-gray-500">套餐名称</span>
                  <span class="text-gray-800 font-medium">{{ selectedPackage ? packages[selectedPackage].name : '-' }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-500">购买时长</span>
                  <span class="text-gray-800">{{ selectedDuration === 'monthly' ? '1个月' : selectedDuration === 'yearly' ? '12个月' : '-' }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-500">支付金额</span>
                  <span class="text-gray-800 font-medium">¥{{ selectedPackage && selectedDuration ? packages[selectedPackage].price[selectedDuration] : 0 }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-500">生效时间</span>
                  <span class="text-gray-800">{{ paymentResult?.startTime }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-500">到期时间</span>
                  <span class="text-gray-800">{{ paymentResult?.endTime }}</span>
                </div>
              </div>
            </div>
            
            <div class="flex space-x-4">
              <button class="btn-secondary flex-1" @click="currentPage = 'packages'">
                <span>返回首页</span>
              </button>
              <button class="btn-primary flex-1" @click="viewMembership">
                <span>查看会员中心</span>
              </button>
            </div>
          </div>
          
          <!-- 支付失败 -->
          <div v-else-if="paymentResult" class="text-center py-6">
            <div class="w-16 h-16 bg-danger/10 rounded-full flex items-center justify-center mx-auto mb-4">
              <i class="fa fa-times text-2xl text-danger"></i>
            </div>
            <h2 class="text-xl font-bold text-gray-800 mb-2">支付失败</h2>
            <p class="text-gray-500 mb-2">抱歉，支付未能完成</p>
            <p class="text-gray-400 text-sm mb-6">请检查您的支付信息或稍后再试</p>
            
            <div class="flex space-x-4">
              <button class="btn-secondary flex-1" @click="currentPage = 'packages'">
                <span>返回首页</span>
              </button>
              <button class="btn-primary flex-1" @click="currentPage = 'payment'">
                <span>重新支付</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </main>
    
    <!-- 底部导航 -->
    <footer class="bg-white border-t py-4">
      <div class="container mx-auto px-4 max-w-md">
        <div class="flex justify-around">
          <a href="#" class="flex flex-col items-center text-primary">
            <i class="fa fa-home text-xl"></i>
            <span class="text-xs mt-1">首页</span>
          </a>
          <a href="#" class="flex flex-col items-center text-gray-400">
            <i class="fa fa-gift text-xl"></i>
            <span class="text-xs mt-1">会员</span>
          </a>
          <a href="#" class="flex flex-col items-center text-gray-400">
            <i class="fa fa-user text-xl"></i>
            <span class="text-xs mt-1">我的</span>
          </a>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentPage: 'packages',
      selectedPackage: 'student',
      selectedDuration: 'monthly',
      selectedPaymentMethod: null,
      orderId: '',
      orderTime: '',
      paymentQrcode: 'https://picsum.photos/96/96',
      paymentResult: null,
      packages: {
        student: {
          id: 'student',
          name: '学生版',
          description: '适合学生使用的基础套餐',
          price: {
            monthly: 3,
            yearly: 30
          },
          benefits: ['小马AI', '小马日历', '小马收藏夹', '云端同步', '100T空间'],
          recommended: true
        },
        standard: {
          id: 'standard',
          name: '标准版',
          description: '适合个人日常使用',
          price: {
            monthly: 8,
            yearly: 80
          },
          benefits: ['小马AI', '小马日历', '小马收藏夹', '云端同步', '100T空间'],
          recommended: false
        },
        team: {
          id: 'team',
          name: '团队版',
          description: '适合团队协作使用',
          price: {
            monthly: 18,
            yearly: 180
          },
          benefits: ['小马AI', '小马日历', '小马收藏夹', '云端同步', '100T空间'],
          recommended: false
        },
        addon: {
          id: 'addon',
          name: '会员补充包',
          description: '扩展存储空间和功能',
          price: {
            monthly: 5
          },
          benefits: ['存储空间5G', 'Altken 100次/天'],
          recommended: false
        }
      }
    };
  },
  methods: {
    selectPackage(packageId) {
      this.selectedPackage = packageId;
      // 如果选择的是补充包，默认选择月付
      if (packageId === 'addon' && !this.selectedDuration) {
        this.selectedDuration = 'monthly';
      }
    },
    selectDuration(packageId, duration) {
      // 如果点击的不是当前选中的套餐，先切换套餐
      if (this.selectedPackage !== packageId) {
        this.selectedPackage = packageId;
      }
      this.selectedDuration = duration;
    },
    selectPaymentMethod(method) {
      this.selectedPaymentMethod = method;
      // 更新二维码
      this.paymentQrcode = method === 'wechat' 
        ? 'https://picsum.photos/seed/wechat/96/96' 
        : 'https://picsum.photos/seed/alipay/96/96';
    },
    goToPayment() {
      if (this.selectedPackage && this.selectedDuration) {
        this.createOrder();
        this.currentPage = 'payment';
      } else {
        alert('请选择套餐和时长');
      }
    },
    createOrder() {
      // 生成订单ID
      this.orderId = 'ORD' + new Date().getTime();
      // 设置订单时间
      this.orderTime = this.formatDateTime(new Date());
    },
    processPayment() {
      if (!this.selectedPaymentMethod) {
        alert('请选择支付方式');
        return;
      }
      
      // 获取支付按钮并显示加载状态
      const payButton = document.querySelector('.btn-primary:disabled, .btn-primary:not(:disabled)');
      if (payButton) {
        const originalContent = payButton.innerHTML;
        payButton.innerHTML = '<i class="fa fa-spinner fa-spin mr-2"></i> 处理中...';
        payButton.disabled = true;
        
        // 模拟支付过程
        setTimeout(() => {
          // 随机模拟支付成功或失败（90%成功率）
          const success = Math.random() > 0.1;
          const now = new Date();
          
          // 设置支付结果
          this.paymentResult = {
            success,
            orderId: this.orderId,
            amount: this.selectedPackage ? this.packages[this.selectedPackage].price[this.selectedDuration] : 0,
            startTime: this.formatDateTime(now),
            endTime: this.calculateEndTime(now)
          };
          
          // 显示结果页面
          this.currentPage = 'result';
        }, 2000);
      }
    },
    calculateEndTime(startDate) {
      const endDate = new Date(startDate);
      if (this.selectedDuration === 'monthly') {
        endDate.setMonth(startDate.getMonth() + 1);
      } else {
        endDate.setFullYear(startDate.getFullYear() + 1);
      }
      return this.formatDateTime(endDate);
    },
    formatDateTime(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      const seconds = String(date.getSeconds()).padStart(2, '0');
      
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },
    closePage() {
      if (confirm('确定要关闭会员购买页面吗？已选信息将不会保存。')) {
        alert('关闭成功');
      }
    },
    viewMembership() {
      alert('即将跳转到会员中心');
    }
  },
  mounted() {
    // 初始化订单信息
    this.createOrder();
  }
};
</script>
