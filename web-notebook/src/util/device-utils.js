export const deviceDetector = {
  async detect() {
    const ua = navigator.userAgent
    const isMobile = /Mobile|Android|iP(hone|od)|Windows Phone/i.test(ua)
    return isMobile ? 'mobile' : 'pc'
  },
  // async detect() {
  //   const ua = navigator.userAgent.toLowerCase();
  //   const isMobile = /mobile|android|iphone|ipod|windows phone/i.test(ua);
  //   const isTablet = /(tablet|ipad|playbook|silk)|(android(?!.*mobile))/i.test(ua);
  //   return isMobile || isTablet ? 'mobile' : 'pc';
  // },
  cacheToLocalStorage(type) {
    localStorage.setItem('lastDeviceType', type)
  }
}
