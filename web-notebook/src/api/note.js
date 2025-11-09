import request from '@/utils/request'
// 获取笔记分享内容
export function getNoteShareContent(workspaceId, viewId) {
  return request({
    // `/api/getnotecontent/${workspaceId}/${viewId}`
    url: `/api/noteshare/getnotecontent/${workspaceId}/${viewId}` ,
    method: 'get',
    params: {
      workspaceId,
      viewId,
    },
    headers: {
      isToken: false,
      repeatSubmit: true
    },
    timeout: 20000
  })
}