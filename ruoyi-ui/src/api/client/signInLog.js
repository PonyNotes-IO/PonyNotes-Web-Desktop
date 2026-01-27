import request from '@/utils/request'

// 查询登录日志列表
export function listSignInLog(query) {
  return request({
    url: '/api/ponynotes/signInLog/list',
    method: 'get',
    params: query
  })
}

// 查询登录概况
export function getSignInOverview(query) {
  return request({
    url: '/api/ponynotes/signInLog/overview',
    method: 'get',
    params: query
  })
}

// 查询登录记录列表
export function signThirdPartList(query) {
  return request({
    url: '/api/ponynotes/signInLog/signThirdPartList',
    method: 'get',
    params: query
  })
}

// 解绑第三方登录
export function unbind(query) {
  return request({
    url: '/api/ponynotes/signInLog/unbind',
    method: 'put',
    data: query
  })
}

// 导出登录日志
export function exportSignInLog(query) {
  return request({
    url: '/api/ponynotes/signInLog/export',
    method: 'post',
    data: query,
    responseType: 'blob'
  })
}
