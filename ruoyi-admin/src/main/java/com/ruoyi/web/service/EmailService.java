package com.ruoyi.web.service;

/**
 * 邮箱服务接口
 */
public interface EmailService {

    /**
     * 生成随机验证码
     * @return 6位数字验证码
     */
    String generateVerifyCode();

    /**
     * 发送邮箱验证码
     * @param email 邮箱地址
     * @param verifyCode 验证码
     * @return 是否发送成功
     */
    boolean sendVerifyCode(String email, String verifyCode);

    /**
     * 验证邮箱验证码
     * @param email 邮箱地址
     * @param inputCode 输入的验证码
     * @param storedCode 存储的验证码
     * @return 是否验证通过
     */
    boolean verifyEmailCode(String email, String inputCode, String storedCode);


     boolean verifyEmailCode(String email, String inputCode);
}

