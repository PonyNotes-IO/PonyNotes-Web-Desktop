// 引入微信 JS-SDK（若页面已通过 script 标签引入 jweixin-1.6.0.js，可省略此 import）
import wx from 'weixin-js-sdk';

// 引入后端接口：获取 JS-SDK 签名配置（需后端配合提供接口）
import { getWxJsSdkConfig } from '@/api/payment';

/**
 * 初始化微信 JS-SDK 配置
 * @param {string} currentUrl - 当前网页 URL（必须去除 # 及后面内容，微信签名要求）
 * @returns {Promise<void>} - 初始化成功/失败 Promise
 */
export const initWxConfig = async (currentUrl) => {
  try {
    // 1. 先判断是否在微信环境（非微信环境无需初始化，直接抛出错误）
    if (!isWeChatEnv()) {
      throw new Error('请在微信浏览器中打开以使用微信支付');
    }

    // 2. 调用后端接口，获取 JS-SDK 签名所需参数（appId、timestamp、nonceStr、signature）
    const res = await getWxJsSdkConfig(currentUrl);
    if (res.code !== 200 || !res.data) {
      throw new Error(`JS-SDK 配置参数获取失败：${res.msg || '未知错误'}`);
    }
    const { appId, timestamp, nonceStr, signature } = res.data;

    // 3. 调用 wx.config 初始化 SDK（微信官方方法）
    await new Promise((resolve, reject) => {
      wx.config({
        debug: false, // 调试模式：true 时会弹出日志弹窗，上线前改为 false
        appId: appId, // 公众号 AppID（后端返回，避免前端硬编码）
        timestamp: timestamp, // 生成签名的时间戳（后端生成，秒级字符串/数字均可）
        nonceStr: nonceStr, // 生成签名的随机串（后端生成）
        signature: signature, // 后端生成的签名（基于 currentUrl + jsapi_ticket 生成）
        jsApiList: ['chooseWXPay'] // 需要使用的 JS 接口列表：仅需支付则只填 chooseWXPay
      });

      // 4. 配置成功回调
      wx.ready(() => {
        console.log('微信 JS-SDK 初始化成功');
        resolve(); // 初始化成功，resolve Promise
      });

      // 5. 配置失败回调
      wx.error((err) => {
        console.error('微信 JS-SDK 初始化失败：', err);
        reject(new Error(`JS-SDK 配置失败：${JSON.stringify(err)}`));
      });
    });
  } catch (err) {
    console.error('JS-SDK 初始化异常：', err);
    throw err; // 抛出错误，供调用方（支付页面）捕获并提示用户
  }
};

/**
 * 判断是否在微信内置浏览器环境
 * @returns {boolean} - true：微信环境；false：非微信环境
 */
export const isWeChatEnv = () => {
  const userAgent = window.navigator.userAgent.toLowerCase();
  return userAgent.includes('micromessenger'); // 微信浏览器 UA 包含 micromessenger
};

/**
 * 调起微信支付（封装成工具方法，可选，也可直接在支付页面调用 wx.chooseWXPay）
 * @param {object} payParams - 后端返回的支付参数（appId、timeStamp、nonceStr、package、signType、paySign）
 * @returns {Promise<object>} - 支付成功/失败结果
 */
export const callWeChatPay = (payParams) => {
  return new Promise((resolve, reject) => {
    wx.chooseWXPay({
      appId: payParams.appId,
      timestamp: payParams.timeStamp, // 后端返回的秒级时间戳（字符串/数字均可）
      nonceStr: payParams.nonceStr,
      package: payParams.package, // 格式：prepay_id=xxx（后端返回，必须带前缀）
      signType: payParams.signType, // 签名类型：后端用 RSA 则填 RSA（微信支付 v3 标准）
      paySign: payParams.paySign, // 后端生成的支付签名
      success: (res) => {
        // 支付成功回调（仅表示调起支付面板成功，最终支付结果需以后端回调为准）
        if (res.errMsg === 'chooseWXPay:ok') {
          resolve({ success: true, msg: '支付调起成功', res });
        } else {
          resolve({ success: false, msg: `支付调起失败：${res.errMsg}`, res });
        }
      },
      fail: (err) => {
        // 支付失败回调（如用户取消、签名错误等）
        console.error('微信支付调起失败：', err);
        reject(new Error(`支付失败：${err.errMsg || '未知错误'}`));
      },
      complete: (res) => {
        // 支付完成回调（无论成功失败都会执行）
        console.log('微信支付完成：', res);
      }
    });
  });
};