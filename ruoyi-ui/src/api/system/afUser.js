import request from '@/utils/request'

// 查询用户列表
export function listAfUser(query) {
  return request({
    url: '/system/afUser/list',
    method: 'get',
    params: query
  })
}

// 查询用户详细
export function getAfUser(uid) {
  return request({
    url: '/system/afUser/' + uid,
    method: 'get'
  })
}

// 新增用户
export function addAfUser(data) {
  return request({
    url: '/system/afUser',
    method: 'post',
    data: data
  })
}

// 修改用户
export function updateAfUser(data) {
  return request({
    url: '/system/afUser',
    method: 'put',
    data: data
  })
}

// 删除用户
export function delAfUser(uid) {
  return request({
    url: '/system/afUser/' + uid,
    method: 'delete'
  })
}

// 批量删除用户
export function delAfUserByIds(uids) {
  return request({
    url: '/system/afUser/batch',
    method: 'delete',
    data: uids
  })
}