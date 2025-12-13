import request from '@/utils/request'

// 查询订阅套餐列表
export function listPlans(query) {
  return request({
    url: '/system/plans/list',
    method: 'get',
    params: query
  })
}

// 查询订阅套餐详细
export function getPlans(id) {
  return request({
    url: '/system/plans/' + id,
    method: 'get'
  })
}

// 新增订阅套餐
export function addPlans(data) {
  return request({
    url: '/system/plans',
    method: 'post',
    data: data
  })
}

// 修改订阅套餐
export function updatePlans(data) {
  return request({
    url: '/system/plans',
    method: 'put',
    data: data
  })
}

// 删除订阅套餐
export function delPlans(id) {
  return request({
    url: '/system/plans/' + id,
    method: 'delete'
  })
}
