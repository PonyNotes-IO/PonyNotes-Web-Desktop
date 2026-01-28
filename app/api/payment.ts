import { post,get } from '~/utils/api'
import type { CreatePaymentParams } from '~/types/api'

export const paymentApi = {
  createPayment(params: CreatePaymentParams) {
    return post<Record<string, any>>('/api/payment/create', params,undefined,{
      // headers: {'Content-Type': 'application/x-www-form-urlencoded'}
      'Content-Type': 'application/x-www-form-urlencoded'
    })
  },
  planLists() {
    return get<Record<string, any>>('/api/ponynotes/plans/list')
  }
}
