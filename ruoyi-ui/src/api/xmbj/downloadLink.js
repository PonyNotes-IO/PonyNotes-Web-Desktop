import request from '@/utils/request'

// 查询下载链接列表
export function listDownloadLink(query) {
  return request({
    url: '/xmbj/downloadLink/list',
    method: 'get',
    params: query
  })
}

// 查询下载链接详细
export function getDownloadLink(id) {
  return request({
    url: '/xmbj/downloadLink/' + id,
    method: 'get'
  })
}

// 新增下载链接
export function addDownloadLink(data) {
  return request({
    url: '/xmbj/downloadLink',
    method: 'post',
    data: data
  })
}

// 修改下载链接
export function updateDownloadLink(data) {
  return request({
    url: '/xmbj/downloadLink',
    method: 'put',
    data: data
  })
}

// 删除下载链接
export function delDownloadLink(id) {
  return request({
    url: '/xmbj/downloadLink/' + id,
    method: 'delete'
  })
}
