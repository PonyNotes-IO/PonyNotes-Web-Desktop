package com.ruoyi.web.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.web.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 邮箱服务实现类
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${verify.subject}")
    private String verifySubject;


    @Resource
    private RedisTemplate<String, String> redisTemplate;  // 补充注入RedisTemplate

    @Value("${verify.code.expire.minutes:5}")  // 验证码过期时间，默认5分钟
    private int codeExpireMinutes;

    /**
     * 生成随机验证码
     */
    @Override
    public String generateVerifyCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 生成6位数字验证码
        return String.valueOf(code);
    }

    /**
     * 发送邮箱验证码
     */
    @Override
    public boolean sendVerifyCode(String email, String verifyCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setSubject(verifySubject);
            message.setText("您的验证码是：" + verifyCode + "，有效期为5分钟，请尽快使用。");
            mailSender.send(message);
            redisTemplate.opsForValue().set(email, verifyCode, 5, TimeUnit.MINUTES);
            // 立即读取验证
            String check = redisTemplate.opsForValue().get(email);
            System.out.println("存入Redis的验证码：" + check); // 若为null则存入失败
            return true;
        } catch (Exception e) {
            throw new ServiceException("邮件发送失败: " + e.getMessage());
        }
    }

    /**
     * 验证邮箱验证码
     */
    @Override
    public boolean verifyEmailCode(String email, String inputCode, String storedCode) {
        // 验证码为空
        if (inputCode == null || inputCode.isEmpty()) {
            return false;
        }
        
        // 验证码不正确
        if (!inputCode.equals(storedCode)) {
            return false;
        }
        
        return true;
    }

    @Override
    public boolean verifyEmailCode(String email, String inputCode) {
         String key = "email:verify:" + email;
//        String storedCode = redisTemplate.opsForValue().get(key);
        Object storedObj = redisTemplate.opsForValue().get(key);
        String storedCode = storedObj != null ? storedObj.toString() : null;
        if (storedCode == null) {
            return false;
        }
        
        boolean verified = inputCode.equals(storedCode);
        if (verified) {
            redisTemplate.delete(key);
        }
        return verified;
    }

}
