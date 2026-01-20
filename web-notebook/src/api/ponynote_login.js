import request from '@/utils/request'
// import captchaUtil from '@/utils/captchaUtil'


// 获取用户信息方法
export function getuserinfo(params) {
  console.log(params)
  return request({
    url: '/api/getuserinfo',
    method: 'post',
    data: {
      account:params.account,
      accountType:params.accountType
    },
    headers: {
      isToken: false,
      repeatSubmit: true
    },
    timeout: 20000
  })
}


export function sendCaptcha(params) {
  let api_path = ''
  let data = {}
  let accountType = params.accountType
  let account = params.account

  if(accountType === 'phone'){
    api_path = '/verify/sendSmsCode'
    data = { phoneNumber: account }
  } else if(accountType === 'email'){
    api_path = '/verify/sendEmailCode'
    data = { email: account }
  }
  console.log(params)
  return request({
    url: api_path,
    method: 'post',
    data: data,
    headers: {
      isToken: false,
      repeatSubmit: true
    },
    timeout: 20000
  })
}


export function verifyCaptcha(params) {
  let api_path = ''
  let data = {}
  let accountType = params.accountType
  let inputValue = params.inputValue

  if(accountType === 'phone'){
    api_path = '/verify/verifySmsCode'
    data = { phoneNumber: inputValue, code: params.code }
  } else if(accountType === 'email'){
    api_path = '/verify/verifyEmailCode'
    data = { email: inputValue , code: params.code }
  }
  console.log(params)
  return request({
    url: api_path,
    method: 'post',
    data: data,
    headers: {
      isToken: false,
      repeatSubmit: true
    },
    timeout: 20000
  })
}


export function loginWithCode(params) {
  let api_path = ''
  let data = {}
   api_path = '/api/loginWithCode'
  let accountType = params.accountType
  let loginType ='code'
  let inputValue = params.inputValue
  let code = params.code
  let phone = ''
  let email = ''
  if (accountType === 'phone') {
    phone = inputValue
  }else{
    email = inputValue
  }
  data = { 
    accountType: accountType,
    loginType: loginType,
    phone: phone,
    email: email,
    code: code 
  }
  return request({
    url: api_path,
    method: 'post',
    data: data,
    headers: {
      isToken: false,
      repeatSubmit: true
    },
    timeout: 20000
  })
}

export function loginWithPassWord(params) {
  let api_path = ''
  let data = {}
   api_path = '/api/loginwithPassword'
  let accountType = params.accountType
  let phone = ''
  let email = ''
  if (accountType === 'phone') {
    phone = params.inputValue
  }
  if (accountType === 'email'){
    email = params.inputValue
  }
  data = { 
    accountType: params.accountType,
    phone: phone,
    email: email,
    password: params.password
  }
  return request({
    url: api_path,
    method: 'post',
    data: data,
    headers: {
      isToken: false,
      repeatSubmit: true
    },
    timeout: 20000
  })
}

export function setPassword(params) {
  let api_path = ''
  let data = {}
   api_path = '/api/setPassword'
  let accountType = params.accountType
  let loginType ='password'
  let inputValue = params.account
  let password = params.password
  let phone = ''
  let email = ''
  let isFirstLogin = params.isFirstLogin
  let isFirstEmailLogin = params.isFirstEmailLogin
  console.log(isFirstLogin + '  ' + isFirstEmailLogin)
  if (accountType === 'phone') {
    phone = inputValue
  }else{
    email = inputValue
  }
  data = { 
    accountType: accountType,
    loginType: loginType,
    phone: phone,
    email: email,
    password: password
  }
  return request({
    url: api_path,
    method: 'post',
    data: data,
    headers: {
      isToken: false,
      repeatSumit: true
    },
    timeout: 20000
  })
}

export function updatePassword(params) {
  let api_path = ''
  let data = {}
   api_path = '/api/updatePassword'

  data = { 
    username: params.username,
    password: params.password
  }
  return request({
    url: api_path,
    method: 'post',
    data: data,
    headers: {
      isToken: false,
      repeatSumit: true
    },
    timeout: 20000
  })
}
export function registerUser(params) {
    let data = {}
    let api_path = '/api/registerUser'
    let phone = ''
    let email = ''
    let accountType = params.accountType
    if (accountType === 'phone'){
        phone = params.account
    }else{
        email = params.account
    }

    data = {
        phone: phone,
        email: email,
        loginType: params.accountType
    }
    return request({
        url: api_path,
        method: 'post',
        data: data,
        headers: {
          isToken: false,
          repeatSubmit: true
        },
        timeout: 20000
    })
}

export function bindPhone(params) {
  let api_path = ''
  let data = {}
  api_path = '/api/bindPhone'
  data = { 
    accountType: params.accountType,
    loginType: params.loginType,
    phone: params.phone,  // 这里期望phone参数
    email: params.email   // 这里期望email参数
  }
  return request({
    url: api_path,
    method: 'post',
    data: data,
    headers: {
      isToken: false,
      repeatSubmit: true
    },
    timeout: 20000
  })
}

export function bindEmail(params) {
  let api_path = ''
  let data = {}
  api_path = '/api/bindEmail'
  data = { 
    accountType: 'email',
    loginType: params.loginType,
    phone: params.email,
    email: params.email,
    username: params.username,
    code: params.code 
  }
  return request({
    url: api_path,
    method: 'post',
    data: data,
    headers: {
      isToken: false,
      repeatSubmit: true
    },
    timeout: 20000
  })
}
// 登录方法
export function login(username, password, code, uuid) {
  const data = {
    username,
    password,
    code,
    uuid
  }
  return request({
    url: '/login',
    headers: {
      isToken: false,
      repeatSubmit: false
    },
    method: 'post',
    data: data
  })
}

// 注册方法
export function register(data) {
  return request({
    url: '/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}


    // 验证密码规则
 export function  validatePasswordRule(password) {
  // 密码规则：8位以上，包含数字、字母中至少两种，且不能包含汉字
  const minLength = 8;
  const hasNumber = /\d/.test(password);
  const hasLetter = /[a-zA-Z]/.test(password);
  // 检测是否包含汉字（\u4e00-\u9fa5 是汉字的Unicode范围）
  const hasChinese = /[\u4e00-\u9fa5]/.test(password);
  
  if (hasChinese) {
    return '密码不能包含汉字'; // 优先提示汉字错误
  }
  
  if (password.length < minLength) {
    return '密码长度不能少于8位';
  }
  
  if (!hasNumber && !hasLetter) {
    return '密码需包含数字或字母';
  }
  
  return '200'; // 验证通过
}


        // 验证密码规则
export function  validatePassword(password) {
  // 密码规则：8位以上，包含大小写字母、数字和特殊字符
  const minLength = 8;
  const hasUpper = /[A-Z]/.test(password);
  const hasLower = /[a-z]/.test(password);
  const hasNumber = /\d/.test(password);
  const hasSpecial = /[!@#$%^&*(),.?":{}|<>]/.test(password);
  let passwordRuleError ='200'
  if (password.length < minLength) {
    passwordRuleError = '密码长度不能少于8位';
    return passwordRuleError;
  }
  if (!hasUpper) {
    passwordRuleError = '密码需包含大写字母';
    return passwordRuleError;
  }
  if (!hasLower) {
    passwordRuleError = '密码需包含小写字母';
    return passwordRuleError;
  }
  if (!hasNumber) {
    passwordRuleError = '密码需包含数字';
    return passwordRuleError;
  }
  if (!hasSpecial) {
    passwordRuleError = '密码需包含特殊字符';
    return passwordRuleError;
  }
  return passwordRuleError;
}

export function changePhoneByCode(params) {
  console.log(params)
  return request({
    url: '/api/changePhoneByCode',
    method: 'post',
    data: {
      username:params.username,
      phone:params.phone,
      code:params.code,
      account:params.account,
      loginType:'code'
    },
    headers: {
      isToken: false,
      repeatSubmit: true
    },
    timeout: 20000
  })
}

export function  validateInput(value, accountType) {
    // 手机号校验
  const phone = value.trim();
  // const phoneReg = /^1[3-9]\d{9}$/;
  // const emailReg = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const forbiddenPhones = ['00000000000', '12345678901'];
  // 主流号段正则（移动、联通、电信、虚拟运营商）
  const phoneReg = /^(1(3[0-9]|4[01478]|5[0-35-9]|6[2567]|7[013578]|8[0-9]|9[356789]))\d{8}$/;
  // 虚拟运营商号段
  const virtualReg = /^(165|167|170[0-2])\d{8}$/;
  // 号段有效性
  const validPhone = (phoneReg.test(phone) || virtualReg.test(phone)) &&
    phone.length === 11 &&
    /^\d{11}$/.test(phone) &&
    !forbiddenPhones.includes(phone);
    // 邮箱校验
    const email = value.trim();
    // const emailReg = /^([A-Za-z0-9]+([._-+][A-Za-z0-9]+)*)@([A-Za-z0-9]+([-][A-Za-z0-9]+)*\.)+[A-Za-z]{2,}$/;
    const emailReg = /^([A-Za-z0-9]+([._+][A-Za-z0-9]+)*)@([A-Za-z0-9]+([-][A-Za-z0-9]+)*\.)+[A-Za-z]{2,}$/;
    const atCount = (email.match(/@/g) || []).length;
    const localPart = email.split('@')[0];
    const domainPart = email.split('@')[1] || '';
    const tld = domainPart.split('.').pop() || '';
    const validEmail =
      email.length <= 254 &&
      localPart.length >= 1 &&
      localPart.length <= 64 &&
      atCount === 1 &&
      !email.startsWith('@') &&
      !email.endsWith('@') &&
      domainPart.includes('.') &&
      !domainPart.startsWith('.') &&
      !domainPart.endsWith('.') &&
      /^[A-Za-z]{2,}$/.test(tld) &&
      emailReg.test(email);
    if (accountType === 'email') {
      return validEmail;
    }else if (accountType === 'phone') {
      return  validPhone;
    }else{
      let isInputValid = validPhone || validEmail; 
      return isInputValid;
    }
    
}