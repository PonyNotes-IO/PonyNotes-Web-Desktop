export const useUserStore = () => {
  const isLoggedIn = useState('user_isLoggedIn', () => false)
  const userInfo = useState('user_info', () => null)
  const token = useState('user_token', () => '')

  const setUser = (user: any, userToken: string) => {
    userInfo.value = user
    token.value = userToken
    isLoggedIn.value = true
    
    if (process.client) {
      localStorage.setItem('token', userToken)
      localStorage.setItem('userInfo', JSON.stringify(user))
    }
  }

  const clearUser = () => {
    userInfo.value = null
    token.value = ''
    isLoggedIn.value = false
    
    if (process.client) {
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
    }
  }

  const initUser = () => {
    if (process.client) {
      const savedToken = localStorage.getItem('token')
      const savedUserInfo = localStorage.getItem('userInfo')
      
      if (savedToken && savedUserInfo) {
        token.value = savedToken
        userInfo.value = JSON.parse(savedUserInfo)
        isLoggedIn.value = true
      }
    }
  }

  return {
    isLoggedIn,
    userInfo,
    token,
    setUser,
    clearUser,
    initUser,
  }
}
