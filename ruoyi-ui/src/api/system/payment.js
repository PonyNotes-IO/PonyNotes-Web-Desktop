import request from '@/utils/request'

// 创建支付订单并生成二维码
export function createPayment(data) {
  return request({
    url: '/api/payment/create',
    method: 'post',
    params: {
      amount: data.amount,
      paymentType: data.paymentType
    }
  })
}

// 查询支付状态
export function checkPaymentStatus(orderNo) {
  return request({
    url: '/api/payment/status',
    method: 'get',
    params: {
      orderNo: orderNo
    }
  })
}

// 轮询检查支付状态(每3秒查询一次，最多查询5分钟)
export function pollPaymentStatus(orderNo, onSuccess, onError, onTimeout) {
  const maxAttempts = 100; // 5分钟 = 300秒，每3秒一次 = 100次
  let attempts = 0;

  const poll = () => {
    checkPaymentStatus(orderNo).then(response => {
      const status = response.data;

      // 支付成功
      if (status === 'success') {
        onSuccess && onSuccess(response);
        return;
      }

      // 支付失败或过期
      if (status === 'failed' || status === 'expired') {
        onError && onError(status);
        return;
      }

      // 达到最大尝试次数
      if (++attempts >= maxAttempts) {
        onTimeout && onTimeout();
        return;
      }

      // 继续轮询
      if (status === 'pending') {
        setTimeout(poll, 3000);
      }
    }).catch(error => {
      onError && onError(error);
    });
  };

  // 开始轮询
  poll();
}
