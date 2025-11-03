<template>
  <div id="app" style="height: 100vh;">
    <router-view/>
  </div>
</template>

<script>
import { deviceDetector } from './util/device-utils'

export default {
  name: 'App',
  async created() {
    // 在应用创建时检测设备类型
    const deviceType = await deviceDetector.detect()
    deviceDetector.cacheToLocalStorage(deviceType)
    
    // 根据设备类型和当前路径决定是否需要重定向
    const isMobilePath = this.$route.path.startsWith('/mobile')
    if (deviceType === 'mobile' && !isMobilePath) {
      // 检测到移动设备时，直接重定向到移动端首页
      this.$router.push('/mobile/home')
    } else if (deviceType === 'pc' && isMobilePath) {
      // PC端访问时重定向到PC端首页
      this.$router.replace('/index')
    }
  }
}
</script>

<style lang="less" src="./assets/common.less">
</style>
