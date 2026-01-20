import request from '@/utils/request'

// 查询快速笔记列表
export function listNote(query) {
  return request({
    url: '/xmbj/note/list',
    method: 'get',
    params: query
  })
}

// 查询快速笔记详细
export function getNote(id) {
  return request({
    url: '/xmbj/note/' + id,
    method: 'get'
  })
}

// 新增快速笔记
export function addNote(data) {
  return request({
    url: '/xmbj/note',
    method: 'post',
    data: data
  })
}

// 修改快速笔记
export function updateNote(data) {
  return request({
    url: '/xmbj/note',
    method: 'put',
    data: data
  })
}

// 删除快速笔记
export function delNote(id) {
  return request({
    url: '/xmbj/note/' + id,
    method: 'delete'
  })
}
