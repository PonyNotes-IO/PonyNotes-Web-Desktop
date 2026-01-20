<template>
  <div class="modal-overlay active" @click.self="handleClose">
    <div class="modal">
      <div class="modal-header">
        <h3 class="modal-title">更改密码</h3>
        <button class="modal-close" @click="handleClose">&times;</button>
      </div>
      <div class="modal-body">
        <p class="text-gray-400 mb-4">请选择新密码进行设置</p>
        
        <div class="form-group">
          <label class="form-label">输入新密码</label>
          <input 
            type="password" 
            class="form-input" 
            placeholder="请输入新密码" 
            v-model="newPassword"
            @input="handlePasswordCheck" 
          >
        </div>
        
        <div class="form-group">
          <label class="form-label">请再次输入新密码</label>
          <input 
            type="password" 
            class="form-input" 
            placeholder="请再次输入新密码" 
            v-model="confirmPassword"
            @input="handlePasswordCheck" 
          >
          <span v-if="showPasswordError" class="text-red-500 text-sm mt-1">
            {{ passwordErrorText  }} <!-- 显示“请输入手机号”或“格式不正确” -->
          </span>
          <span v-else class="text-gray-500 text-sm mt-1">
             密码设置格式正确。
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
import { ref, defineEmits, defineProps } from 'vue';
import { updatePassword, validatePassword } from '../../api/ponynote_login.js'; 
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
const newPassword = ref('');
const confirmPassword = ref('');
const loading = ref(false);
const showPasswordError = ref(false); // 控制密码错误提示是否显示
const passwordErrorText = ref(''); // 存储密码专属错误提示文本（替换 showPhoneErrorText）
// 处理关闭弹窗
/* eslint-disable-next-line no-unused-vars */
const handleClose = () => {
  emit('close');
};

// 新增：实时校验密码（格式、长度、一致性）
/* eslint-disable-next-line no-unused-vars */
const handlePasswordCheck = async () => {
  const password = newPassword.value;
  const confirmPwd = confirmPassword.value;


  if (password === '') {
    showPasswordError.value = true;
    passwordErrorText.value = '请输入新密码';
  } 

  else if (password.length < 6) {
    showPasswordError.value = true;
    passwordErrorText.value = '密码长度不能少于6位，请补充';
  } else if (password.length >= 6) {
    const isValid = validatePassword(password);
    if (!isValid) {
      showPasswordError.value = true;
      passwordErrorText.value = '密码格式不符合要求（建议包含字母、数字或特殊字符）';
    } else if (confirmPwd === '') {
      showPasswordError.value = true;
      passwordErrorText.value = '请再次输入新密码以确认';
    } else if (password !== confirmPwd) {
      showPasswordError.value = true;
      passwordErrorText.value = '两次输入的密码不一致，请重新确认';
    } else {
      showPasswordError.value = false;
      passwordErrorText.value = '';
    }
  }
};
// 确认更改密码
/* eslint-disable-next-line no-unused-vars */
const handleConfirm = async () => {
  if (!newPassword.value) {
    // emit('show-notification', '请输入新密码', 'error');
    showPasswordError.value = true;
    passwordErrorText.value = '请输入新密码';
    return;
  }
  
  if (!confirmPassword.value) {
    // emit('show-notification', '请再次输入新密码', 'error');
    showPasswordError.value = true;
    passwordErrorText.value = '请再次输入新密码以确认';
    return;
  }
  
  if (newPassword.value !== confirmPassword.value) {
    // emit('show-notification', '两次输入的密码不一致', 'error');
    showPasswordError.value = true;
    passwordErrorText.value = '两次输入的密码不一致，请重新确认';
    return;
  }
  
  // 密码强度验证
  if (newPassword.value.length < 6) {
    showPasswordError.value = true;
    passwordErrorText.value = '密码长度不能少于6位，请补充';
    // emit('show-notification', '密码长度不能少于6位', 'error');
    return;
  }
  
  // 调用密码格式验证接口
  const isValid = validatePassword(newPassword.value);
  if (!isValid) {
    // emit('show-notification', '密码格式不符合要求', 'error');
    showPasswordError.value = true;
    passwordErrorText.value = '密码格式不符合要求（建议包含字母、数字或特殊字符）';
    return;
  }
  
  try {
    loading.value = true;
    // 调用更新密码接口
    const response = await updatePassword({
      username: props.userName,
      password: newPassword.value,
    });
    
    if (response.data.code === 200) {
      // emit('show-notification', '密码修改成功', 'success');
      emit('close');
      emit('success');
      
      // 重置表单
      newPassword.value = '';
      confirmPassword.value = '';
    } else {
      emit('show-notification', response.data.msg || '密码修改失败', 'error');
    }
  } catch (error) {
    console.error('修改密码失败:', error);
    // emit('show-notification', '网络错误，密码修改失败', 'error');
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped lang="less" src="./assets/model.less" />
