import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询应用版本列表
export function listAppVersion(query) {
    return request({
        url: '/api/ponynotes/appVersion/list',
        method: 'get',
        params: query
    })
}

// 查询应用版本详细
export function getAppVersion(id) {
    return request({
        url: '/api/ponynotes/appVersion/' + parseStrEmpty(id),
        method: 'get'
    })
}

// 新增应用版本
export function addAppVersion(data) {
    return request({
        url: '/api/ponynotes/appVersion',
        method: 'post',
        data: data
    })
}

// 修改应用版本
export function updateAppVersion(data) {
    return request({
        url: '/api/ponynotes/appVersion',
        method: 'put',
        data: data
    })
}

// 删除应用版本
export function delAppVersion(id) {
    return request({
        url: '/api/ponynotes/appVersion/' + id,
        method: 'delete'
    })
}

// 批量删除应用版本
export function delAppVersions(ids) {
    return request({
        url: '/api/ponynotes/appVersion',
        method: 'delete',
        data: ids
    })
}

// 停用应用版本
export function disableAppVersion(id) {
    return request({
        url: '/api/ponynotes/appVersion/' + id + '/disable',
        method: 'put'
    })
}

// 启用应用版本
export function enableAppVersion(id) {
    return request({
        url: '/api/ponynotes/appVersion/' + id + '/enable',
        method: 'put'
    })
}

// 查询所有活跃的应用版本
export function getActiveAppVersions() {
    return request({
        url: '/api/ponynotes/appVersion/active',
        method: 'get'
    })
}
