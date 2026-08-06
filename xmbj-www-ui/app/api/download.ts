import { get } from '~/utils/api'

export interface DownloadLinkItem {
  id: number
  platform: string
  archLabel?: string
  archDesc?: string
  version?: string
  downloadUrl: string
  sortOrder: number
  status: number
  remark?: string
}

export const downloadApi = {
  /** 获取已启用的下载链接列表（公开接口，无需登录） */
  list() {
    return get<DownloadLinkItem[]>('/xmbj/downloadLink/public/list')
  }
}
