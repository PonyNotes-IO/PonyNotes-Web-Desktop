import { post, get } from '~/utils/api'
import type { LoginBody, LoginVo, AccountQueryRequest } from '~/types/api'

export const userApi = {
  login(data: LoginBody) {
    return post('/login', data)
  },

  loginWithCode(data: LoginVo) {
    let api_path = ''
    let submitObj = {}
    let accountType = data.accountType
    let inputValue = data.phone || data.email

    if (accountType === 'phone') {
      api_path = '/verify/verifySmsCode'
      submitObj = { phoneNumber: inputValue, code: data.code }
    } else if (accountType === 'email') {
      api_path = '/verify/verifyEmailCode'
      submitObj = { email: inputValue, code: data.code }
    }
    return post(api_path, submitObj)
  },

  doCodeLogin(params: { accountType: string, inputValue: string, code: string }) {
    let api_path = ''
    let data = {}
    api_path = '/api/loginWithCode'
    let accountType = params.accountType
    let loginType = 'code'
    let inputValue = params.inputValue
    let code = params.code
    let phone = ''
    let email = ''
    if (accountType === 'phone') {
      phone = inputValue
    } else {
      email = inputValue
    }
    data = {
      accountType: accountType,
      loginType: loginType,
      phone: phone,
      email: email,
      code: code
    }
    return post(api_path, data);
  },

  registerUser(data: LoginVo) {
    return post('/api/registerUser', data)
  },

  bindEmail(data: LoginVo) {
    return post('/api/bindEmail', data)
  },

  bindPhone(data: LoginVo) {
    return post('/api/bindPhone', data)
  },

  changePhoneByCode(data: LoginVo) {
    return post('/api/changePhoneByCode', data)
  },

  setPassword(data: LoginVo) {
    return post('/api/setPassword', data)
  },

  updatePassword(data: LoginVo) {
    return post('/api/updatePassword', data)
  },

  getUserInfo(data: AccountQueryRequest) {
    return post('/api/getuserinfo', data)
  },

  getInfo() {
    return get('/getInfo')
  },

  getuserinfo(params: AccountQueryRequest) {
    console.log(params)
    return post('/api/getuserinfo', {
      account: params.account,
      accountType: params.accountType
    });
  },

  getRouters() {
    return get('/getRouters')
  },

  sendCaptcha(params: { accountType: string, account: string }) {
    let api_path = ''
    let data = {}
    let accountType = params.accountType
    let account = params.account

    if (accountType === 'phone') {
      api_path = '/verify/sendSmsCode'
      data = { phoneNumber: account, accountType: accountType }
    } else if (accountType === 'email') {
      api_path = '/verify/sendEmailCode'
      data = { email: account, accountType: accountType }
    }
    console.log(params)
    return post(api_path, data)
  },

  douyinLogin() {
    return post('/api/douyinLogin')
  }
}
