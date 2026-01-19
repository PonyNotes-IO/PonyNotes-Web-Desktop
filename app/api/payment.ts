import { post } from '~/utils/api'
import type { CreatePaymentParams } from '~/types/api'

export const paymentApi = {
  createPayment(params: CreatePaymentParams) {
    return post<Record<string, any>>('/api/payment/create', undefined, params)
  },
}
