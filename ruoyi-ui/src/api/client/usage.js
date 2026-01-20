import request from '@/utils/request'

// 获取用户使用情况
export function getUsage(userId) {
  return request({
    url: '/api/ponynotes/usage/my',
    method: 'get',
    params: { userId }
  })
}

// 记录使用情况
export function recordUsage(data) {
  return request({
    url: '/api/ponynotes/usage/record',
    method: 'post',
    data: data
  })
}
