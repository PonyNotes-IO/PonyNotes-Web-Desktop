import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

export function getToken() {
  // 从Cookies中获取TokenKey对应的值
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
