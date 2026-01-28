import request from '@/utils/request'

// 查询App版本管理列表
export function listAppVersion(query) {
  return request({
    url: '/xmbj/appVersion/list',
    method: 'get',
    params: query
  })
}

// 查询App版本管理详细
export function getAppVersion(id) {
  return request({
    url: '/xmbj/appVersion/' + id,
    method: 'get'
  })
}

// 新增App版本管理
export function addAppVersion(data) {
  return request({
    url: '/xmbj/appVersion',
    method: 'post',
    data: data
  })
}

// 修改App版本管理
export function updateAppVersion(data) {
  return request({
    url: '/xmbj/appVersion',
    method: 'put',
    data: data
  })
}

// 删除App版本管理
export function delAppVersion(id) {
  return request({
    url: '/xmbj/appVersion/' + id,
    method: 'delete'
  })
}
