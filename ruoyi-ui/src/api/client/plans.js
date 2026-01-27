import request from '@/utils/request'

export function getPlans(pageNum = 1, pageSize = 10) {
  return request({
    url: '/api/ponynotes/listPlans',
    method: 'get',
    params: { pageNum, pageSize }
  })
}

export function getPlanById(id) {
  return request({
    url: '/api/ponynotes/plans/' + id,
    method: 'get'
  })
}
