// 检测移动端设备
export const isMobile = () => {
  return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)
}

// 格式化手机号
export const formatPhoneNumber = (phone) => {
  if (!phone) return ''
  return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
}

// 验证手机号
export const validatePhone = (phone) => {
  return /^1[3-9]\d{9}$/.test(phone)
}

// 验证邮箱
export const validateEmail = (email) => {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
}

// 验证密码强度
export const validatePassword = (password) => {
  return password.length >= 8
}

// 倒计时功能
export const countDown = (seconds, callback) => {
  let timer = setInterval(() => {
    seconds--
    callback(seconds)
    if (seconds <= 0) {
      clearInterval(timer)
    }
  }, 1000)
  return timer
}

// 阻止默认滚动行为
export const preventScroll = (prevent) => {
  if (prevent) {
    document.body.style.overflow = 'hidden'
    document.body.style.height = '100%'
  } else {
    document.body.style.overflow = ''
    document.body.style.height = ''
  }
}
