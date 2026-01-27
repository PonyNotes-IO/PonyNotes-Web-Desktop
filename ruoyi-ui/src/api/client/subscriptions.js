import request from '@/utils/request'

export function getMySubscriptions(userId, pageNum = 1, pageSize = 10) {
    return request({
        url: '/api/ponynotes/my/subscriptions',
        method: 'get',
        params: { userId, pageNum, pageSize }
    })
}

export function subscribe(userId, planId, billingType) {
    return request({
        url: '/api/ponynotes/subscribe',
        method: 'post',
        params: { userId, planId, billingType }
    })
}

export function cancelSubscription(userId) {
    return request({
        url: '/api/ponynotes/cancel',
        method: 'post',
        params: { userId }
    })
}
