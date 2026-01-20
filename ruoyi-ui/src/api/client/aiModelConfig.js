import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询AI模型配置列表
export function listAiModelConfig(query) {
  return request({
    url: '/api/ponynotes/aiModelConfig/list',
    method: 'get',
    params: query
  })
}

// 查询AI模型配置详细
export function getAiModelConfig(id) {
  return request({
    url: '/api/ponynotes/aiModelConfig/' + parseStrEmpty(id),
    method: 'get'
  })
}

// 新增AI模型配置
export function addAiModelConfig(data) {
  return request({
    url: '/api/ponynotes/aiModelConfig/add',
    method: 'post',
    data: data
  })
}

// 修改AI模型配置
export function updateAiModelConfig(data) {
  return request({
    url: '/api/ponynotes/aiModelConfig/update',
    method: 'put',
    data: data
  })
}

// 删除AI模型配置
export function delAiModelConfig(id) {
  return request({
    url: '/api/ponynotes/aiModelConfig/' + id,
    method: 'delete'
  })
}

// 批量删除AI模型配置
export function delAiModelConfigs(ids) {
  return request({
    url: '/api/ponynotes/aiModelConfig',
    method: 'delete',
    data: ids
  })
}

// 停用AI模型配置
export function disableAiModelConfig(id) {
  return request({
    url: '/api/ponynotes/aiModelConfig/' + id + '/disable',
    method: 'put'
  })
}

// 启用AI模型配置
export function enableAiModelConfig(id) {
  return request({
    url: '/api/ponynotes/aiModelConfig/' + id + '/enable',
    method: 'put'
  })
}

// 查询所有活跃的AI模型配置
export function getActiveAiModelConfigs() {
  return request({
    url: '/api/ponynotes/aiModelConfig/active',
    method: 'get'
  })
}
