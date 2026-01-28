import request from '@/utils/request'

export function listSubscriptionAddons(query) {
  return request({
    url: '/xmbj/subscriptionAddons/list',
    method: 'get',
    params: query
  })
}

export function getSubscriptionAddons(id) {
  return request({
    url: '/xmbj/subscriptionAddons/' + id,
    method: 'get'
  })
}

export function addSubscriptionAddons(data) {
  return request({
    url: '/xmbj/subscriptionAddons',
    method: 'post',
    data: data
  })
}

export function updateSubscriptionAddons(data) {
  return request({
    url: '/xmbj/subscriptionAddons',
    method: 'put',
    data: data
  })
}

export function delSubscriptionAddons(id) {
  return request({
    url: '/xmbj/subscriptionAddons/' + id,
    method: 'delete'
  })
}

export function exportSubscriptionAddons(query) {
  return request({
    url: '/xmbj/subscriptionAddons/export',
    method: 'post',
    params: query
  })
}
