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
    const isNoteSharePath = (path) => {
      return path.replace(/\?.*/, '').includes('/noteshare');
    };
    if (isNoteSharePath(this.$route.path)) {
      this.$router.push('/noteshare' + this.$route.fullPath.replace(/.*\/noteshare/, ''));
      return;
    }
    // 在应用创建时检测设备类型
    const deviceType = await deviceDetector.detect()
    deviceDetector.cacheToLocalStorage(deviceType)

    // 根据设备类型和当前路径决定是否需要重定向
    const isMobilePath = this.$route.path.startsWith('/mobile')
    if (deviceType === 'mobile' && !isMobilePath && !isNoteSharePath(this.$route.path)) {
      // 检测到移动设备时，直接重定向到移动端首页
      this.$router.push('/mobile/home')
    } else if (deviceType === 'pc' && isMobilePath && !isNoteSharePath(this.$route.path)) {
      // PC端访问时重定向到PC端首页
      this.$router.replace('/index')
    } else if (isNoteSharePath(this.$route.path)) {
      // 如果是笔记分享路径，则不做重定向
      return;
    }
  }
}
</script>

<style lang="less" src="./assets/common.less">
</style>
