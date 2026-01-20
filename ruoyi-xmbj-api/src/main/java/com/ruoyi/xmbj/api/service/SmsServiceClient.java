package com.ruoyi.xmbj.api.service;


import com.ruoyi.xmbj.api.Urls;
import com.ruoyi.xmbj.api.protocol.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "sms-service-client",url = Urls.API_HOST,path = "/api/sms")
public interface SmsServiceClient {

    @PostMapping("/send-code")
    AbstractResponse<SendSmsCodeResponse> sendCode(@RequestBody SendSmsCodeRequest body);
    @PostMapping("/verify-code")
    AbstractResponse<VerifySmsCodeResponse> verifyCode(@RequestBody VerifySmsCodeRequest body);
    @PostMapping("/phone-login")
    AbstractResponse<PhoneLoginResponse> phoneLogin(@RequestBody PhoneLoginRequest body);
}
