import request from '@/utils/request'

// 查询用户订阅信息列表
export function listAfUserSubscription(query) {
    return request({
        url: '/ponynotes/afUserSubscription/list',
        method: 'get',
        params: query
    })
}

// 查询用户订阅信息详细
export function getAfUserSubscription(id) {
    return request({
        url: '/ponynotes/afUserSubscription/' + id,
        method: 'get'
    })
}

// 新增用户订阅信息
export function addAfUserSubscription(data) {
    return request({
        url: '/ponynotes/afUserSubscription',
        method: 'post',
        data: data
    })
}

// 修改用户订阅信息
export function updateAfUserSubscription(data) {
    return request({
        url: '/ponynotes/afUserSubscription',
        method: 'put',
        data: data
    })
}

// 删除用户订阅信息
export function delAfUserSubscription(id) {
    return request({
        url: '/ponynotes/afUserSubscription/' + id,
        method: 'delete'
    })
}
