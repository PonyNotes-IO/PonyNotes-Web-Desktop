package com.ruoyi.web.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.web.service.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

import javax.annotation.Resource;

/**
 * 阿里云短信服务实现类
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Value("${aliyun.sms.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.sms.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.sms.sign-name}")
    private String signName;

    @Value("${aliyun.sms.template-code}")
    private String templateCode;

    @Value("${verify.code.expire-minutes}")
    private int codeExpireMinutes;

    @Resource
    private RedisTemplate<String, String> redisTemplate;  // 注入RedisTemplate


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
     * 发送短信验证码
     */
    @Override
    public boolean sendVerifyCode(String phoneNumber, String verifyCode) {
        try {
            // 创建配置
            Config config = new Config()
                    .setAccessKeyId(accessKeyId)
                    .setAccessKeySecret(accessKeySecret);
            // 访问的域名
            config.endpoint = "dysmsapi.aliyuncs.com";
            
            // 创建客户端
            Client client = new Client(config);
            
            // 构建请求
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(phoneNumber)
                    .setSignName(signName)
                    .setTemplateCode(templateCode)
                    .setTemplateParam("{\"code\":\"" + verifyCode + "\"}");
            
            // 发送请求
            SendSmsResponse response = client.sendSms(sendSmsRequest);
            
            // 处理响应
            if ("OK".equals(response.getBody().getCode())) {
                return true;
            } else {
                throw new ServiceException("短信发送失败: " + response.getBody().getMessage());
            }
        } catch (Exception e) {
            throw new ServiceException("短信发送异常: " + e.getMessage());
        }
    }

    /**
     * 验证短信验证码
     */
    @Override
    public boolean verifySmsCode(String phoneNumber, String inputCode, String storedCode) {
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
    public boolean verifyLoginCode(String phone, String code) {
        String key = "sms:verify:" + phone;
//        String storedCode = redisTemplate.opsForValue().get(key);
        Object storedObj = redisTemplate.opsForValue().get(key);
        String storedCode = storedObj != null ? storedObj.toString() : null;
        if (storedCode == null) {
            return false;
        }
        
        boolean verified = code.equals(storedCode);
        if (verified) {
            redisTemplate.delete(key);
        }
        return verified;
    }
}
