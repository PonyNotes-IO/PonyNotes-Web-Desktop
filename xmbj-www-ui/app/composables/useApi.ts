import { userApi } from '~/api/user'
import { testUserApi } from '~/api/testUser'
import { paymentApi } from '~/api/payment'
import { downloadApi } from '~/api/download'

export const useApi = () => {
  return {
    user: userApi,
    testUser: testUserApi,
    payment: paymentApi,
    download: downloadApi,
  }
}
