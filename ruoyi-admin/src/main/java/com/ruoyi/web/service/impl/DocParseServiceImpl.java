package com.ruoyi.web.service.impl;

import com.aliyun.docmind_api20220711.models.SubmitDocStructureJobResponse;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.component.BaiDuOcrComponent;
import com.ruoyi.web.model.ParseRequest;
import com.ruoyi.web.service.DocParseService;
import com.ruoyi.web.service.SmsService;
import com.ruoyi.web.util.DocParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocParseServiceImpl implements DocParseService {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private SmsService smsService;

    @Autowired
    private BaiDuOcrComponent baiDuOcrComponent;


    @Override
    public Object parsePdf(ParseRequest request) {

        Object parse = baiDuOcrComponent.parse(redisCache, request);
        if(parse != null) {
            return parse;
        }

        SubmitDocStructureJobResponse response = smsService.executeWithAli(DocParseUtil.parse(request));
        if(response.getBody() != null && response.getBody().getData() != null) {
            String id = response.getBody().getData().getId();
           Object o =  smsService.executeWithAli(DocParseUtil.result(id,5));
           if(o != null) {
               return o;
           }
        }
        return response.getBody();
    }
}
