import request from '@/utils/request'

// 查询系统登录日志列表
export function listLogininfor(query) {
  return request({
    url: '/monitor/logininfor/list',
    method: 'post',
    data: query
  })
}

// 删除系统登录日志
export function deleteLogininfor(infoIds) {
  return request({
    url: '/monitor/logininfor/remove',
    method: 'post',
    data: infoIds
  })
}

// 清空系统登录日志
export function cleanLogininfor() {
  return request({
    url: '/monitor/logininfor/clean',
    method: 'post'
  })
}
