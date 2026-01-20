<template>
  <div class="modal-overlay active" @click.self="handleClose">
    <div class="modal">
      <div class="modal-header">
        <h3 class="modal-title">绑定邮箱</h3>
        <button class="modal-close" @click="handleClose">&times;</button>
      </div>
      <div class="modal-body">
        <div class="form-group">
          <label class="form-label">请输入新的邮箱</label>
          <input 
            type="email" 
            class="form-input" 
            placeholder="请输入邮箱地址" 
            v-model="newEmail"
            @input="handleEmailInput"
          >
        </div>
        
        <div class="form-group">
          <label class="form-label">6位短信验证码</label>
          <div class="form-actions">
            <input 
              type="text" 
              class="form-verify-code" 
              placeholder="请输入验证码" 
              v-model="verificationCode"
            >
            <button 
              class="btn-resend" 
              :disabled="isResendDisabled"
              @click="handleResendCode"
            >
              {{ resendButtonText }}
            </button>
          </div>
          <span v-if="showEmailError" class="text-red-500 text-sm mt-1">
            {{ showEmailErrorText }} <!-- 显示“请输入手机号”或“格式不正确” -->
          </span>
          <span v-else class="text-gray-500 text-sm mt-1">
             邮箱格式正确，请查收验证码。
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
import { ref, onBeforeUnmount } from 'vue';
import { sendCaptcha, validateInput, bindEmail }  from '../../api/ponynote_login';

// 接收父组件参数
const props = defineProps({
  userName: {
    type: String,
    required: true
  },
  phoneNumber: {
    type: String,
    required: true
  }
});

// 定义emits
const emit = defineEmits(['close', 'success', 'show-notification']);

// 响应式数据
const newEmail = ref('');
const verificationCode = ref('');
const isResendDisabled = ref(false);
const resendButtonText = ref('重新获取');
const countdownTimer = ref(null);
const loading = ref(false);
const showEmailError = ref(false); // 控制错误提示是否显示
const showEmailErrorText = ref(''); // 存储具体错误提示文本
// 新增：处理关闭弹窗（之前遗漏了这个方法）
/* eslint-disable-next-line no-unused-vars */
const handleClose = () => {
  emit('close');
};

const setEmailError = (isError, text) => {
  showEmailError.value = isError;
  showEmailErrorText.value = text;
};

// 新增：邮箱输入实时校验方法
/* eslint-disable-next-line no-unused-vars */
const handleEmailInput = () => {
  const email = newEmail.value.trim(); // 去除首尾空格（避免空字符误判）
  if (email === '') {
    setEmailError(true, '请输入邮箱地址'); // 同步显示
  } else if (!validateInput(email, 'email')) {
    setEmailError(true, '邮箱格式不正确，请输入如 xxx@xxx.com 的有效格式'); // 同步显示
  } else {
     setEmailError(false, ''); // 调用统一方法
  }
};
// 重新获取验证码
/* eslint-disable-next-line no-unused-vars */
const handleResendCode = async () => {
  if (!newEmail.value) {
    // emit('show-notification', '请先输入邮箱地址', 'error');
    setEmailError(true, '请输入邮箱地址');
    return;
  }
  
  // 验证邮箱格式
  if (!validateInput(newEmail.value, 'email')) {
    setEmailError(true, '邮箱格式不正确，请输入如 xxx@xxx.com 的有效格式');
    // emit('show-notification', '请输入正确的邮箱地址', 'error');
    return;
  }
  
  try {
    // 调用发送验证码接口
    setEmailError(false, '');
    const response = await sendCaptcha({
      accountType: 'email', 
      account:  newEmail.value
    });
    if (response.status !== 200 || response.data.code !== 200) {
      emit('show-notification', response.data.msg || '验证码发送失败，请重试', 'error');
      setEmailError(true, '验证码发送失败，请重试')
      return;
    }
    if (response.data.code === 200) {
      // emit('show-notification', '验证码已发送至邮箱', 'success');
      
      // 禁用重新获取按钮，开始倒计时
      isResendDisabled.value = true;
      let countdown = 60;
      resendButtonText.value = `${countdown}秒后重新获取`;
      
      countdownTimer.value = setInterval(() => {
        countdown--;
        resendButtonText.value = `${countdown}秒后重新获取`;
        
        if (countdown <= 0) {
          clearInterval(countdownTimer.value);
          isResendDisabled.value = false;
          resendButtonText.value = '重新获取';
        }
      }, 1000);
    } else {
      setEmailError(true, '验证码发送失败，请重试')
      emit('show-notification', response.data.msg || '验证码发送失败', 'error');
    }
  } catch (error) {
    console.error('发送验证码失败:', error);
    setEmailError(true, '网络错误，验证码发送失败')
    emit('show-notification', '网络错误，验证码发送失败', 'error');
  }
};

// 确认绑定邮箱
/* eslint-disable-next-line no-unused-vars */
const handleConfirm = async () => {
  if (!newEmail.value) {
    // emit('show-notification', '请输入邮箱地址', 'error');
    setEmailError(true, '请输入邮箱地址');
    return;
  }
  // 验证邮箱格式
  if (!validateInput(newEmail.value, 'email')) {
    // emit('show-notification', '请输入正确的邮箱地址', 'error');
    setEmailError(true, '邮箱格式不正确，请输入如 xxx@xxx.com 的有效格式');
    return;
  }
  if (!verificationCode.value) {
    emit('show-notification', '请输入验证码', 'error');
    return;
  }
  setEmailError(false, '');
  try {
    loading.value = true;
    // 调用绑定邮箱接口
    const response = await bindEmail({
      username: props.userName,
      account: props.phoneNumber,
      phone: props.phoneNumber,
      email: newEmail.value, 
      code: verificationCode.value,
      loginType: 'code'
    });
    
    if (response.data.code === 200) {
      emit('show-notification', '邮箱绑定成功', 'success');
      emit('close');
      emit('success', newEmail.value);
      
      // 重置表单
      newEmail.value = '';
      verificationCode.value = '';
      
      // 重置重新获取按钮
      if (countdownTimer.value) {
        clearInterval(countdownTimer.value);
        countdownTimer.value = null;
      }
      isResendDisabled.value = false;
      resendButtonText.value = '重新获取';
    } else {
      emit('show-notification', response.data.msg || '邮箱绑定失败', 'error');
    }
  } catch (error) {
    console.error('绑定邮箱失败:', error);
    emit('show-notification', '网络错误，邮箱绑定失败', 'error');
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