package com.ruoyi.web.service;

/**
 * 短信服务接口
 */
public interface SmsService {

    /**
     * 生成随机验证码
     * @return 6位数字验证码
     */
    String generateVerifyCode();

    /**
     * 发送短信验证码
     * @param phoneNumber 手机号码
     * @param verifyCode 验证码
     * @return 是否发送成功
     */
    boolean sendVerifyCode(String phoneNumber, String verifyCode);

    /**
     * 验证短信验证码
     * @param phoneNumber 手机号码
     * @param inputCode 输入的验证码
     * @param storedCode 存储的验证码
     * @return 是否验证通过
     */
    boolean verifySmsCode(String phoneNumber, String inputCode, String storedCode);

    boolean verifyLoginCode(String phone, String code);
}
