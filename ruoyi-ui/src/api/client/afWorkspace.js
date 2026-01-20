import request from '@/utils/request'

// 查询工作区信息列表
export function listAfWorkspace(query) {
  return request({
    url: '/api/ponynotes/afworkspaces/list',
    method: 'get',
    params: query
  })
}

// 查询工作区信息详细
export function getAfWorkspace(id) {
  return request({
    url: '/api/ponynotes/afworkspaces/' + id,
    method: 'get'
  })
}

// 新增工作区信息
export function addAfWorkspace(data) {
  return request({
    url: '/api/ponynotes/afworkspaces',
    method: 'post',
    data: data
  })
}

// 修改工作区信息
export function updateAfWorkspace(data) {
  return request({
    url: '/api/ponynotes/afworkspaces',
    method: 'put',
    data: data
  })
}

// 删除工作区信息
export function delAfWorkspace(id) {
  return request({
    url: '/api/ponynotes/afworkspaces/' + id,
    method: 'delete'
  })
}
