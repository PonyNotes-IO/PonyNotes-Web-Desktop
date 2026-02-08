import request from '@/utils/request'

export function listWorkspace(query) {
  return request({
    url: '/xmbj/workspace/list',
    method: 'get',
    params: query
  })
}

export function getWorkspace(workspaceId) {
  return request({
    url: '/xmbj/workspace/' + workspaceId,
    method: 'get'
  })
}

export function addWorkspace(data) {
  return request({
    url: '/xmbj/workspace',
    method: 'post',
    data: data
  })
}

export function updateWorkspace(data) {
  return request({
    url: '/xmbj/workspace',
    method: 'put',
    data: data
  })
}

export function delWorkspace(workspaceId) {
  return request({
    url: '/xmbj/workspace/' + workspaceId,
    method: 'delete'
  })
}
