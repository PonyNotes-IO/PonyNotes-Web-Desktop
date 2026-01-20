import request from '@/utils/request'

// 查询订阅套餐列表
export function listPlans(query) {
  return request({
    url: '/xmbj/plans/list',
    method: 'get',
    params: query
  })
}

// 查询订阅套餐详细
export function getPlans(id) {
  return request({
    url: '/xmbj/plans/' + id,
    method: 'get'
  })
}

// 新增订阅套餐
export function addPlans(data) {
  return request({
    url: '/xmbj/plans',
    method: 'post',
    data: data
  })
}

// 修改订阅套餐
export function updatePlans(data) {
  return request({
    url: '/xmbj/plans',
    method: 'put',
    data: data
  })
}

// 删除订阅套餐
export function delPlans(id) {
  return request({
    url: '/xmbj/plans/' + id,
    method: 'delete'
  })
}
