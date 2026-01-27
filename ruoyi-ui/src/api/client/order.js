import request from '@/utils/request'

// 查询支付订单列表（分页）
export function listOrder(query) {
  return request({
    url: '/api/ponynotes/order/list',
    method: 'get',
    params: query
  })
}

// 查询支付订单详情
export function getOrder(orderNo) {
  return request({
    url: `/api/ponynotes/order/${orderNo}`,
    method: 'get'
  })
}

// 获取订单关联的订阅/补充包详情（兼容旧接口）
export function getOrderSubscription(query) {
  return request({
    url: '/api/ponynotes/order/subscription',
    method: 'get',
    params: query
  })
}

// 创建支付订单（如果需要，通常由支付流程创建）
export function createOrder(data) {
  return request({
    url: '/api/ponynotes/order',
    method: 'post',
    data: data
  })
}

// 更新支付订单（如修改状态）
export function updateOrder(data) {
  return request({
    url: '/api/ponynotes/order',
    method: 'put',
    data: data
  })
}

// 删除支付订单
export function deleteOrder(orderNo) {
  return request({
    url: `/api/ponynotes/order/${orderNo}`,
    method: 'delete'
  })
}
