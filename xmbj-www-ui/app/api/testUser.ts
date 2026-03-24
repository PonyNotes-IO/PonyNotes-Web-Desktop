import { get, post, put, del } from '~/utils/api'
import type { UserEntity } from '~/types/api'

export const testUserApi = {
  getUserList() {
    return get<UserEntity[]>('/test/user/list')
  },

  getUser(userId: number) {
    return get<UserEntity>(`/test/user/${userId}`)
  },

  saveUser(params: UserEntity) {
    return post<string>('/test/user/save', undefined, params)
  },

  updateUser(data: UserEntity) {
    return put<string>('/test/user/update', data)
  },

  deleteUser(userId: number) {
    return del<string>(`/test/user/${userId}`)
  },
}
