export const deviceDetector = {
  async detect() {
    const ua = navigator.userAgent
    const isMobile = /Mobile|Android|iP(hone|od)|Windows Phone/i.test(ua)
    return isMobile ? 'mobile' : 'pc'
  },
  
  cacheToLocalStorage(type) {
    localStorage.setItem('lastDeviceType', type)
  }
}
