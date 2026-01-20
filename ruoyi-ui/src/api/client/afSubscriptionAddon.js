import request from '@/utils/request'

// 查询订阅补充包信息列表
export function listAfSubscriptionAddon(query) {
  return request({
    url: '/api/ponynotes/addons/list',
    method: 'get',
    params: query
  })
}

// 查询订阅补充包信息详细
export function getAfSubscriptionAddon(id) {
  return request({
    url: '/api/ponynotes/addons/' + id,
    method: 'get'
  })
}

// 新增订阅补充包信息
export function addAfSubscriptionAddon(data) {
  return request({
    url: '/api/ponynotes/addons',
    method: 'post',
    data: data
  })
}

// 修改订阅补充包信息
export function updateAfSubscriptionAddon(data) {
  return request({
    url: '/api/ponynotes/addons',
    method: 'put',
    data: data
  })
}

// 删除订阅补充包信息
export function delAfSubscriptionAddon(id) {
  return request({
    url: '/api/ponynotes/addons/' + id,
    method: 'delete'
  })
}
