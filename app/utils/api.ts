/**
 * API 请求工具函数
 * 基于 Nuxt3 的 $fetch 封装
 */

// API 基础配置
// const API_BASE_URL = 'http://localhost:8080'
const API_BASE_URL = 'https://www.xiaomabiji.com/prod-api'

export interface ApiResponse<T = any> {
  code: number
  msg: string
  data: T
}

/**
 * 通用请求封装
 */
export const request = async <T = any>(
  url: string,
  options?: RequestInit & { params?: Record<string, any> }
): Promise<ApiResponse<T>> => {
  const { params, ...fetchOptions } = options || {}

  // 构建完整 URL
  let fullUrl = `${API_BASE_URL}${url}`

  // 处理 query 参数
  if (params && Object.keys(params).length > 0) {
    const queryString = new URLSearchParams(
      Object.entries(params).reduce((acc, [key, value]) => {
        if (value !== undefined && value !== null) {
          acc[key] = String(value)
        }
        return acc
      }, {} as Record<string, string>)
    ).toString()
    fullUrl += `?${queryString}`
  }

  try {
    const response = await $fetch<ApiResponse<T>>(fullUrl, {
      ...fetchOptions,
      headers: {
        'Content-Type': 'application/json',
        ...fetchOptions.headers,
      },
    } as any)
    return response
  } catch (error: any) {
    // 统一错误处理
    console.error('API Request Error:', error)
    throw error
  }
}

/**
 * GET 请求
 */
export const get = <T = any>(url: string, params?: Record<string, any>) => {
  return request<T>(url, {
    method: 'GET',
    params,
  })
}

/**
 * POST 请求
 */
export const post = <T = any>(url: string, data?: any, params?: Record<string, any>,headers?: Record<string, any>) => {
  return request<T>(url, {
    method: 'POST',
    body: data,
    params,
    headers,
  })
}

/**
 * PUT 请求
 */
export const put = <T = any>(url: string, data?: any) => {
  return request<T>(url, {
    method: 'PUT',
    body: data,
  })
}

/**
 * DELETE 请求
 */
export const del = <T = any>(url: string, params?: Record<string, any>) => {
  return request<T>(url, {
    method: 'DELETE',
    params,
  })
}
