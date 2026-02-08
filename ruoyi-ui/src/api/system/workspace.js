import request from '@/utils/request'

// 查询工作空间列表
export function listWorkspace(query) {
  return request({
    url: '/system/workspace/list',
    method: 'get',
    params: query
  })
}

// 查询工作空间详细
export function getWorkspace(workspaceId) {
  return request({
    url: '/system/workspace/' + workspaceId,
    method: 'get'
  })
}

// 新增工作空间
export function addWorkspace(data) {
  return request({
    url: '/system/workspace',
    method: 'post',
    data: data
  })
}

// 修改工作空间
export function updateWorkspace(data) {
  return request({
    url: '/system/workspace',
    method: 'put',
    data: data
  })
}

// 删除工作空间
export function deleteWorkspace(workspaceId) {
  return request({
    url: '/system/workspace/' + workspaceId,
    method: 'delete'
  })
}

// 批量删除工作空间
export function deleteWorkspaceByIds(workspaceIds) {
  return request({
    url: '/system/workspace/batch',
    method: 'delete',
    data: workspaceIds
  })
}