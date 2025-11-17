// 单独的轮询管理模块 pollManager.js
export const pollManager = {
  timers: new Map(),
  
  setTimer(orderNo, timer) {
    this.clearTimer(orderNo)
    this.timers.set(orderNo, timer)
  },
  
  clearTimer(orderNo) {
    if (this.timers.has(orderNo)) {
      clearInterval(this.timers.get(orderNo))
      this.timers.delete(orderNo)
    }
  },
  
  clearAll() {
    this.timers.forEach(timer => clearInterval(timer))
    this.timers.clear()
  }
}