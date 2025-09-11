// captchaUtil.js
import { Toast } from 'vant'; // 若使用了vant的Toast提示，需引入

// 发送验证码
export async function sendCaptcha({
  axiosInstance, // 传入axios实例，方便请求
  inputValue,
  isPhoneNumber,
  isFirstLogin,
  isFirstEmailLogin,
  setIsSendingCaptcha, // 用于设置发送中状态的回调
  startCountdown // 倒计时方法的回调
}) {
  if (setIsSendingCaptcha) setIsSendingCaptcha(true);
  try {
    const response = await axiosInstance.post('/api/auth/send-captcha', {
      account: inputValue,
      accountType: isPhoneNumber ? 'phone' : 'email',
      isFirstLogin: isPhoneNumber ? isFirstLogin : isFirstEmailLogin
    });

    const { code, message } = response.data;
    if (code === 200) {
      Toast.success('验证码发送成功');
      if (startCountdown) startCountdown();
    } else {
      Toast.fail(message || '验证码发送失败');
      if (setIsSendingCaptcha) setIsSendingCaptcha(false);
    }
  } catch (error) {
    console.error('发送验证码失败：', error);
    Toast.fail('发送验证码失败，请重试');
    if (setIsSendingCaptcha) setIsSendingCaptcha(false);
  }
}

// 验证验证码
export async function verifyCaptcha({
  axiosInstance,
  inputValue,
  isPhoneNumber,
  captchaValue,
  captchaType,
  setIsLoading, // 用于设置加载状态的回调
  showCaptchaPopup, // 用于控制验证码弹窗显示的回调
  showSetPasswordPopup, // 用于控制设置密码弹窗显示的回调
  showResetPasswordPopup, // 用于控制重置密码弹窗显示的回调
  handleLoginSuccess // 登录成功回调
}) {
  if (!captchaValue) {
    Toast('请输入验证码');
    return;
  }
  if (setIsLoading) setIsLoading(true);
  try {
    const response = await axiosInstance.post('/api/auth/verify-captcha', {
      account: inputValue,
      accountType: isPhoneNumber ? 'phone' : 'email',
      captcha: captchaValue,
      type: captchaType
    });

    const { code, message } = response.data;
    if (code !== 200) {
      Toast.fail(message || '验证码错误');
      return;
    }

    if (captchaType === 'register') {
      if (showCaptchaPopup) showCaptchaPopup(false);
      if (showSetPasswordPopup) showSetPasswordPopup(true);
    } else if (captchaType === 'login') {
      if (handleLoginSuccess) handleLoginSuccess();
    } else if (captchaType === 'reset') {
      if (showCaptchaPopup) showCaptchaPopup(false);
      if (showResetPasswordPopup) showResetPasswordPopup(true);
    }
  } catch (error) {
    console.error('验证验证码失败：', error);
    Toast.fail('验证失败，请重试');
  } finally {
    if (setIsLoading) setIsLoading(false);
  }
}

// 开始倒计时
export function startCountdown({
  countdown, // 倒计时初始值
  setCountdown, // 设置倒计时值的回调
  setIsSendingCaptcha, // 设置发送中状态的回调
  captchaTimer, // 定时器引用
  setCaptchaTimer // 设置定时器的回调
}) {
  let seconds = countdown || 60;
  if (setCountdown) setCountdown(seconds);
  const timer = setInterval(() => {
    seconds--;
    if (setCountdown) setCountdown(seconds);
    if (seconds <= 0) {
      clearInterval(timer);
      if (setIsSendingCaptcha) setIsSendingCaptcha(false);
    }
  }, 1000);
  if (setCaptchaTimer) setCaptchaTimer(timer);
}