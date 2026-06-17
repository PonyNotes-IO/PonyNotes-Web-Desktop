import request from '@/utils/request'

// 查询用户管理11列表
export function listUsermgr(query) {
  return request({
    url: '/xmbj/afuser/usermgr/list',
    method: 'get',
    params: query
  })
}

// 查询用户管理11详细
export function getUsermgr(uid) {
  return request({
    url: '/xmbj/afuser/usermgr/' + uid,
    method: 'get'
  })
}

// 新增用户管理11
export function addUsermgr(data) {
  return request({
    url: '/xmbj/afuser/usermgr',
    method: 'post',
    data: data
  })
}

// 修改用户管理11
export function updateUsermgr(data) {
  return request({
    url: '/xmbj/afuser/usermgr',
    method: 'put',
    data: data
  })
}

// 删除用户管理11
export function delUsermgr(uid) {
  return request({
    url: '/xmbj/afuser/usermgr/' + uid,
    method: 'delete'
  })
}
