import request from '@/utils/request'

// 查询第三方登录绑定列表
export function listBind(query) {
  return request({
    url: '/system/bind/list',
    method: 'get',
    params: query
  })
}

// 查询第三方登录绑定详细
export function getBind(bindId) {
  return request({
    url: '/system/bind/' + bindId,
    method: 'get'
  })
}

// 新增第三方登录绑定
export function addBind(data) {
  return request({
    url: '/system/bind',
    method: 'post',
    data: data
  })
}

// 修改第三方登录绑定
export function updateBind(data) {
  return request({
    url: '/system/bind',
    method: 'put',
    data: data
  })
}

// 删除第三方登录绑定
export function delBind(bindId) {
  return request({
    url: '/system/bind/' + bindId,
    method: 'delete'
  })
}
