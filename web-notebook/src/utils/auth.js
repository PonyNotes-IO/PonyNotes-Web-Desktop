import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'
const UserInfoKey = 'User-Info'

export function getToken() {
  const token = Cookies.get(TokenKey)
  console.log('getToken', token)
  return Cookies.get(TokenKey) || localStorage.getItem(TokenKey)
}

export function setToken(token) {
  localStorage.setItem(TokenKey, token)
  return Cookies.set(TokenKey, token, { expires: 7 })
}

export function removeToken() {
  localStorage.removeItem(TokenKey)
  localStorage.removeItem(UserInfoKey) // 清除用户信息
  return Cookies.remove(TokenKey)
}

// 检查 Token 是否存在
export function hasToken() {
  const token = getToken()
  console.log('js ---------hasToken', token)
  if (token) {
    return true
  }else{
    return false
  }
}

// 设置用户信息
export function setUserInfo(userInfo) {
  localStorage.setItem(UserInfoKey, JSON.stringify(userInfo))
}

// 获取用户信息
export function getUserInfo() {
  const userInfo = localStorage.getItem(UserInfoKey)
  return userInfo ? JSON.parse(userInfo) : null
}

// 清除用户信息
export function clearUserInfo() {
  localStorage.removeItem(UserInfoKey)
}