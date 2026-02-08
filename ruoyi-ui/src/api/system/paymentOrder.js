import request from '@/utils/request'

// 查询支付订单列表
export function listPaymentOrder(query) {
  return request({
    url: '/system/paymentOrder/list',
    method: 'post',
    data: query
  })
}

// 新增支付订单
export function addPaymentOrder(data) {
  return request({
    url: '/system/paymentOrder/add',
    method: 'post',
    data: data
  })
}

// 修改支付订单
export function editPaymentOrder(data) {
  return request({
    url: '/system/paymentOrder/edit',
    method: 'post',
    data: data
  })
}

// 删除支付订单
export function removePaymentOrder(ids) {
  return request({
    url: '/system/paymentOrder/remove',
    method: 'post',
    data: ids
  })
}

// 查询支付订单详情
export function getPaymentOrder(id) {
  return request({
    url: '/system/paymentOrder/detail/' + id,
    method: 'get'
  })
}

// 导出支付订单列表
export function exportPaymentOrder(query) {
  return request({
    url: '/system/paymentOrder/export',
    method: 'post',
    data: query
  })
}
