import request from '@/utils/request'

export function listFiles(query) {
  return request({
    url: '/system/qiniu/file/list',
    method: 'get',
    params: query
  })
}

export function getStorageInfo() {
  return request({
    url: '/system/qiniu/storage/info',
    method: 'get'
  })
}

export function deleteFile(key) {
  return request({
    url: '/system/qiniu/file/delete',
    method: 'delete',
    params: { key: key }
  })
}

export function getCapacityTrend() {
  return request({
    url: '/system/qiniu/capacity/trend',
    method: 'get'
  })
}
