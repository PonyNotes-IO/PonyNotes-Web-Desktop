export interface LoginBody {
  username: string
  password: string
  code?: string
  uuid?: string
}

export interface LoginVo {
  username?: string
  password?: string
  phone?: string
  email?: string
  code?: string
  loginType?: string
  accountType?: string
}

export interface AccountQueryRequest {
  account?: string
  accountType?: string
}

export interface UserEntity {
  userId?: number
  username?: string
  password?: string
  mobile?: string
}

export interface CreatePaymentParams {
  amount: number
  paymentType: string
  userInfo: string
  productName?: string
  openid?: string
  url?: string
}
