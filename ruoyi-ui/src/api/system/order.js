import request from '@/utils/request'

// 查询订单列表
export function listOrder(query) {
  return request({
    url: '/system/payment/list',
    method: 'get',
    params: query
  })
}

// 查询订单详细信息
export function getOrder(id) {
  return request({
    url: '/system/payment/info/' + id,
    method: 'get'
  })
}

// 新增订单
export function addOrder(data) {
  return request({
    url: '/system/payment',
    method: 'post',
    data: data
  })
}

// 修改订单
export function updateOrder(data) {
  return request({
    url: '/system/payment',
    method: 'put',
    data: data
  })
}

// 删除订单
export function delOrder(id) {
  return request({
    url: '/system/payment/' + id,
    method: 'delete'
  })
}

// 导出订单
export function exportOrder(query) {
  return request({
    url: '/system/payment/export',
    method: 'get',
    params: query
  })
}
