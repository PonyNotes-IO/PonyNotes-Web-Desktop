import { post, get } from '~/utils/api'
import type { LoginBody, LoginVo, AccountQueryRequest } from '~/types/api'

export const userApi = {
  login(data: LoginBody) {
    return post('/login', data)
  },

  loginWithCode(data: LoginVo) {
    return post('/api/loginWithCode', data)
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

  getRouters() {
    return get('/getRouters')
  },
}
