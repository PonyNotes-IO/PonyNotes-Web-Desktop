import request from '@/utils/request'

export default {
  // 1. 分页查询用户列表（匹配后端list接口，参数为ClientUser实体字段）
  listAfUser(query) {
    return request({
      url: '/api/ponynotes/afusers/list',
      method: 'get',
      params: query
    })
  },
  // 2. 根据ID查询用户详情（匹配后端getAfuser接口）
  getAfUser(afuserId) {
    return request({
      url: `/api/ponynotes/afusers/getAfuser/${afuserId}`,
      method: 'get'
    })
  },
  // 3. 修改用户信息（匹配后端edit接口，参数为ClientUser实体完整字段）
  updateAfUser(data) {
    return request({
      url: '/api/ponynotes/afusers/edit',
      method: 'put',
      data: data
    })
  },
  // 4. 批量删除用户（匹配后端remove接口）
  removeAfUser(afuserIds) {
    return request({
      url: `/api/ponynotes/afusers/remove/${afuserIds.join(',')}`,
      method: 'delete'
    })
  },
  // 5. 批量停用用户（匹配后端stop接口）
  stopAfUser(afuserIds) {
    return request({
      url: `/api/ponynotes/afusers/stop/${afuserIds.join(',')}`,
      method: 'put'
    })
  },
  // 6. 查询用户关联工作区信息（匹配后端getAfUserWorkSpace接口）
  getAfUserWorkSpace(afuserId) {
    return request({
      url: `/api/ponynotes/afusers/getAfUserWorkSpace/${afuserId}`,
      method: 'get'
    })
  }
<<<<<<< HEAD
=======

  getAfW
>>>>>>> 694369914507b779589b6d0d7034a90c7c321bb3
}
