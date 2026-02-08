import request from '@/utils/request'

// 查询AI模型管理列表
export function listModels(query) {
  return request({
    url: '/system/models/list',
    method: 'get',
    params: query
  })
}

// 查询AI模型管理详细
export function getModels(id) {
  return request({
    url: '/system/models/' + id,
    method: 'get'
  })
}

// 新增AI模型管理
export function addModels(data) {
  return request({
    url: '/system/models',
    method: 'post',
    data: data
  })
}

// 修改AI模型管理
export function updateModels(data) {
  return request({
    url: '/system/models',
    method: 'put',
    data: data
  })
}

// 删除AI模型管理
export function delModels(id) {
  return request({
    url: '/system/models/' + id,
    method: 'delete'
  })
}
