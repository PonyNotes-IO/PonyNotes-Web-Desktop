import { userApi } from '~/api/user'
import { testUserApi } from '~/api/testUser'
import { paymentApi } from '~/api/payment'

export const useApi = () => {
  return {
    user: userApi,
    testUser: testUserApi,
    payment: paymentApi,
  }
}
