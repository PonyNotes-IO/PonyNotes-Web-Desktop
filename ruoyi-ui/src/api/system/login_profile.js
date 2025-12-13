import request from '@/utils/request'

// 查询用户登录概况列表
export function listProfile(query) {
  return request({
    url: '/system/profile/list',
    method: 'get',
    params: query
  })
}

// 查询用户登录概况详细
export function getProfile(profileId) {
  return request({
    url: '/system/profile/' + profileId,
    method: 'get'
  })
}

// 新增用户登录概况
export function addProfile(data) {
  return request({
    url: '/system/profile',
    method: 'post',
    data: data
  })
}

// 修改用户登录概况
export function updateProfile(data) {
  return request({
    url: '/system/profile',
    method: 'put',
    data: data
  })
}

// 删除用户登录概况
export function delProfile(profileId) {
  return request({
    url: '/system/profile/' + profileId,
    method: 'delete'
  })
}
