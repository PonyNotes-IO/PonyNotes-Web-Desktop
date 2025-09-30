package com.ruoyi.web.controller.system;


import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.model.EmailRequest;
import com.ruoyi.web.model.SmsRequest;
import com.ruoyi.web.service.EmailService;
import com.ruoyi.web.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * 验证码控制器
 */
@RestController
@RequestMapping("/verify")
public class VerifyController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 验证码有效期 5分钟
    private static final long VERIFY_CODE_EXPIRE = 5;

    /**
     * 发送短信验证码
     */

    @Anonymous     
    @PostMapping(value ="/sendSmsCode", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult sendSmsCode(@RequestBody SmsRequest smsRequest) {
        String phoneNumber = smsRequest.getPhoneNumber();
        // 生成验证码
        String verifyCode = smsService.generateVerifyCode();
        
        // 发送短信
        boolean success = smsService.sendVerifyCode(phoneNumber, verifyCode);
        
        if (success) {
            // 存储验证码到Redis，设置过期时间
            String key = "sms:verify:" + phoneNumber;
            redisTemplate.opsForValue().set(key, verifyCode, VERIFY_CODE_EXPIRE, TimeUnit.MINUTES);
            return AjaxResult.success("短信验证码发送成功");
        }
        
        return AjaxResult.error("短信验证码发送失败");
    }

    /**
     * 验证短信验证码
     */
    @Anonymous
    @PostMapping(value ="/verifySmsCode", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult verifySmsCode(@RequestBody SmsRequest smsRequest ) {
        String phoneNumber  = smsRequest.getPhoneNumber();
        String code = smsRequest.getCode();
        String key = "sms:verify:" + phoneNumber;
        String storedCode = redisTemplate.opsForValue().get(key);
        
        if (storedCode == null) {
            return AjaxResult.error("验证码已过期，请重新获取");
        }
        
        boolean verified = smsService.verifySmsCode(phoneNumber, code, storedCode);
        
        if (verified) {
            // 验证成功后删除验证码
            redisTemplate.delete(key);
            return AjaxResult.success("验证码验证成功");
        }
        
        return AjaxResult.error("验证码不正确");
    }

    /**
     * 发送邮箱验证码
    */
    @Anonymous
    @PostMapping(value ="/sendEmailCode", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult sendEmailCode(@RequestBody EmailRequest emailRequest) {
        String email = emailRequest.getEmail();
        // 生成验证码
        String verifyCode = emailService.generateVerifyCode();
        
        // 发送邮件
        boolean success = emailService.sendVerifyCode(email, verifyCode);
        
        if (success) {
            // 存储验证码到Redis，设置过期时间
            String key = "email:verify:" + email;
            redisTemplate.opsForValue().set(key, verifyCode, VERIFY_CODE_EXPIRE, TimeUnit.MINUTES);
            return AjaxResult.success("邮箱验证码发送成功");
        }
        
        return AjaxResult.error("邮箱验证码发送失败");
    }

    /**
     * 验证邮箱验证码
     */
    @Anonymous
    @PostMapping(value ="/verifyEmailCode", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult verifyEmailCode(@RequestBody EmailRequest emailRequest) {
        String email = emailRequest.getEmail();
        String code = emailRequest.getCode();
        String key = "email:verify:" + email;
        String storedCode = redisTemplate.opsForValue().get(key);
        
        if (storedCode == null) {
            return AjaxResult.error("验证码已过期，请重新获取");
        }
        
        boolean verified = emailService.verifyEmailCode(email, code, storedCode);
        
        if (verified) {
            // 验证成功后删除验证码
            redisTemplate.delete(key);
            return AjaxResult.success("验证码验证成功");
        }
        
        return AjaxResult.error("验证码不正确");
    }
}
