<template>
  <div class="modal-overlay active" @click.self="handleClose">
    <div class="modal">
      <div class="modal-header">
        <h3 class="modal-title">更改手机号</h3>
        <button class="modal-close" @click="handleClose">&times;</button>
      </div>
      <div class="modal-body">
        <p class="text-gray-400 mb-4">请选择新手机号进行绑定</p>
        
        <!-- 手机号输入区域 -->
        <div class="form-group">
          <div class="flex">
            <!-- <span>+86</span> -->
            <input 
              type="tel" 
              class="form-input" 
              placeholder="请输入新的手机号" 
              v-model="newPhoneNumber"
              @input="handlePhoneInput"
            >
          </div>
        </div>
        
        <!-- 验证通过区域 -->
        <div class="form-group"  v-if="isPhoneValid">
          <div class="flex justify-between items-center bg-dark-lighter p-3 rounded">
            <div class="text-primary">验证通过</div>
            <div class="w-4 h-4 rounded-full bg-primary"></div>
          </div>
        </div>
        
        <!-- 验证码区域 -->
        <div class="form-group">
          <div class="flex">
            <input 
              type="text" 
              class="form-verify-code flex-1 mr-2" 
              placeholder="请输入验证码" 
              v-model="verificationCode"
            >
            <button 
              class="btn-primary whitespace-nowrap" 
              :disabled="isGetCodeDisabled"
              @click="handleGetVerificationCode"
            >
              {{ codeButtonText }}
            </button>
          </div>
          <!-- <span class="text-gray-500 text-sm mt-1">已发送短信验证码到绑定手机</span>
          <span v-if="showPhoneError" class="text-red-500 text-sm mt-1">
            请输入11位有效手机号
          </span> -->
          <!-- 验证码区域下方的提示文本：修改为动态文本 -->
          <span v-if="showPhoneError" class="text-red-500 text-sm mt-1">
            {{ showPhoneErrorText }} <!-- 显示“请输入手机号”或“格式不正确” -->
          </span>
          <span v-if="isGetCodeDisabled" class="text-gray-500 text-sm mt-1">
            已发送短信验证码到绑定手机
          </span>   
        </div>
      </div>
      <div class="modal-footer">
        <button class="btn-secondary" @click="handleClose">取消</button>
        <button class="btn-primary" @click="handleConfirm">完成</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onBeforeUnmount  } from 'vue';
import { sendCaptcha, changePhoneByCode, validateInput } from '../../api/ponynote_login.js'; 
// 新增：通过props接收参数
const props = defineProps({
  userName: {
    type: String,
    required: true
  },
  account: {
    type: String,
    required: true
  }
});
// 定义emits
const emit = defineEmits(['close', 'success', 'show-notification']);

// 响应式数据
const newPhoneNumber = ref('');
const verificationCode = ref('');
const isGetCodeDisabled = ref(false);
const codeButtonText = ref('获取验证码');
const countdownTimer = ref(null);
const loading = ref(false);
const isPhoneValid = ref(false); // 手机号是否验证通过
const showPhoneError = ref(false); // 是否显示手机号格式错误提示
const showPhoneErrorText = ref(''); // 新增：动态提示文本（解决 not defined 错误）
// 处理手机号输入（补全部分）
/* eslint-disable-next-line no-unused-vars */
const handlePhoneInput = (e) => {
  // 1. 过滤非数字字符（只保留数字）
  let value = e.target.value.replace(/\D/g, '');
  
  // 2. 限制长度不超过11位（中国手机号规则）
  if (value.length > 11) {
    value = value.slice(0, 11);
  }
  
  // 3. 更新输入值
  newPhoneNumber.value = value;
  
  // 4. 验证手机号格式（以1开头，第二位3-9，共11位）
  const isValid = validateInput(newPhoneNumber.value, 'phone');
  
  // 5. 更新验证状态
  isPhoneValid.value = isValid;
    // 优化 showPhoneError 触发条件：覆盖3种场景
  if (value.length === 0) {
    showPhoneError.value = true;
    showPhoneErrorText.value = '请输入手机号'; // 新增：空输入时的提示文本
  } else if (!isValid) {
    showPhoneError.value = true;
    showPhoneErrorText.value = '手机号格式不正确（需为11位有效数字）'; // 格式错误提示
  } else {
    showPhoneError.value = false; // 验证通过时隐藏提示
    showPhoneErrorText.value = '';
  }
};
/* eslint-disable-next-line no-unused-vars */
const handleClose = () => {
  emit('close'); // 触发关闭事件，通知父组件隐藏弹窗
};
// 获取验证码
/* eslint-disable-next-line no-unused-vars */
const handleGetVerificationCode = async () => {
  if (!newPhoneNumber.value) {
    emit('show-notification', '请输入新的手机号', 'error');
    return;
  }
  
  // 验证手机号格式
  const phoneRegex = /^1[3-9]\d{9}$/;
  if (!phoneRegex.test(newPhoneNumber.value)) {
    emit('show-notification', '请输入正确的手机号', 'error');
    return;
  }
  
  try {
    loading.value = true;
    // 调用发送验证码接口
    const response = await sendCaptcha({  
      accountType: 'phone', 
      account: newPhoneNumber.value
    });
    
    if (response.data.code === 200) {
      emit('show-notification', '验证码已发送至手机', 'success');
      
      // 禁用获取验证码按钮，开始倒计时
      isGetCodeDisabled.value = true;
      let countdown = 60;
      codeButtonText.value = `${countdown}秒后重新获取`;
      
      countdownTimer.value = setInterval(() => {
        countdown--;
        codeButtonText.value = `${countdown}秒后重新获取`;
        
        if (countdown <= 0) {
          clearInterval(countdownTimer.value);
          isGetCodeDisabled.value = false;
          codeButtonText.value = '获取验证码';
        }
      }, 1000);
    } else {
      emit('show-notification', response.data.msg || '验证码发送失败', 'error');
    }
  } catch (error) {
    console.error('发送验证码失败:', error);
    emit('show-notification', '网络错误，验证码发送失败', 'error');
  } finally {
    loading.value = false;
  }
};

// 确认更改手机号
/* eslint-disable-next-line no-unused-vars */
const handleConfirm = async () => {
  if (!newPhoneNumber.value) {
    emit('show-notification', '请输入新的手机号', 'error');
    return;
  }
  
  // 验证手机号
  if (!validateInput(newPhoneNumber.value, 'phone')) {
    emit('show-notification', '请输入正确的手机号', 'error');
    return;
  }
  
  if (!verificationCode.value) {
    emit('show-notification', '请输入验证码', 'error');
    return;
  }
  
  try {
    loading.value = true;
    // 调用更改手机号接口
    const response = await changePhoneByCode({ 
        username: props.userName, 
        phone: newPhoneNumber.value, 
        account: props.account,
        code: verificationCode.value
    });
    
    if (response.data.code === 200) {
      emit('show-notification', '手机号修改成功', 'success');
      emit('close');
      emit('success', newPhoneNumber.value);
      
      // 重置表单
      newPhoneNumber.value = '';
      verificationCode.value = '';
      
      // 重置获取验证码按钮
      if (countdownTimer.value) {
        clearInterval(countdownTimer.value);
        countdownTimer.value = null;
      }
      isGetCodeDisabled.value = false;
      codeButtonText.value = '获取验证码';
    } else {
      emit('show-notification', response.data.msg || '手机号修改失败', 'error');
    }
  } catch (error) {
    console.error('修改手机号失败:', error);
    emit('show-notification', '网络错误，手机号修改失败', 'error');
  } finally {
    loading.value = false;
  }
};

// 清理定时器
onBeforeUnmount(() => {
  if (countdownTimer.value) {
    clearInterval(countdownTimer.value);
  }
});
</script>

<style scoped lang="less" src="./assets/model.less" />
