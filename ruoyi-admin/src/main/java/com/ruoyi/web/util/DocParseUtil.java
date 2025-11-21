package com.ruoyi.web.util;

import com.aliyun.docmind_api20220711.Client;
import com.aliyun.docmind_api20220711.models.GetDocStructureResultRequest;
import com.aliyun.docmind_api20220711.models.GetDocStructureResultResponse;
import com.aliyun.docmind_api20220711.models.SubmitDocStructureJobAdvanceRequest;
import com.aliyun.docmind_api20220711.models.SubmitDocStructureJobResponse;

import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.ruoyi.web.model.ParseRequest;
import com.ruoyi.web.service.SmsService;

public class DocParseUtil {

    static String ENDPOINT = "docmind-api.cn-hangzhou.aliyuncs.com";
//    static String ENDPOINT = "docmind-api.cn-beijing.aliyuncs.com";

    /**
     * 调用阿里云文档解析
     * @param file /
     * @return /
     */
    public static SmsService.AliExecutor<SubmitDocStructureJobResponse> parse(ParseRequest file ) {
        return (accessKey,accessSecret) -> {
            RuntimeOptions runtime = new RuntimeOptions();
            // 替换成具体异步任务提交类API接口的入参和方法，示例方法是文档智能解析。
            SubmitDocStructureJobAdvanceRequest advanceRequest = new SubmitDocStructureJobAdvanceRequest();
            Client client = getClient(ENDPOINT,accessKey,accessSecret);
            advanceRequest.fileUrlObject = file.getDoc().getInputStream();
            advanceRequest.fileName = file.getDoc().getName();
            return client.submitDocStructureJobAdvance(advanceRequest, runtime);

        };

    }

    private static Client getClient(String endpoint,String accessKey,String accessSecret) throws Exception {
        Config config = new Config()
                // 通过Credentials获取配置中的AccessKey ID。
                .setAccessKeyId(accessKey)
                // 通过Credentials获取配置中的AccessKey Secret。
                .setAccessKeySecret(accessSecret);
        // 访问的域名，支持IPv4和IPv6两种方式，IPv6请使用docmind-api-dualstack.cn-hangzhou.aliyuncs.com。
//        config.endpoint = "docmind-api.cn-hangzhou.aliyuncs.com";
        config.endpoint = endpoint;
        return new Client(config);
    }


    public static SmsService.AliExecutor<Object> result(String id,int times) {
        return (accessKey, accessSecret) -> {
            Client client = getClient(ENDPOINT, accessKey, accessSecret);
            int i =0;
            GetDocStructureResultRequest resultRequest = new GetDocStructureResultRequest();
            resultRequest.id = id;
            do {
                try {
                    if(times > 0) {
                        Thread.sleep(i == 0 ? 10000: 50000);
                    }
                    GetDocStructureResultResponse response = client.getDocStructureResult(resultRequest);
                    if(response.getBody() != null && response.getBody().completed) {
                        return response.getBody();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                i++;
            } while (i < times);
            return null;

        };

    }
}
