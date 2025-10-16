import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'


export function getToken() {
  // 从Cookies中获取TokenKey对应的值

  return Cookies.get(TokenKey) || localStorage.getItem(TokenKey)
}

export function setToken(token) {
  localStorage.setItem(TokenKey, token)
  return Cookies.set(TokenKey, token, { expires: 7 })
}

export function removeToken() {
  localStorage.removeItem(TokenKey)
  return Cookies.remove(TokenKey)
}


  // 检查 Token 是否存在
export function  hasToken() {
  return !!this.getToken()
}