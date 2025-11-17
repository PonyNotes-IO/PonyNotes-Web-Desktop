package com.ruoyi.component;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.model.ParseRequest;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class BaiDuOcrComponent {

    public static final String ACCESS_TOKEN_KEY = "BAIDU_ACCESS_TOKEN_KEY";

    @Value("${baidu.appId:}")
    private String appId;
    @Value("${baidu.apiKey:}")
    private String apiKey;
    @Value("${baidu.apiSecret:}")
    private String apiSecret;

    public static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().readTimeout(300, TimeUnit.SECONDS).build();


    public Object parse(RedisCache redisCache, ParseRequest param) {
        String accessToken = getOrExecute(redisCache,ACCESS_TOKEN_KEY,7200,() -> {
            try {
                return getAccessToken(apiKey,apiSecret);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            String fileBase64 = encodeFileToBase64(param.getDoc().getInputStream());
            RequestBody body = new FormBody.Builder()
                    .add("file_data", fileBase64)
                    .add("file_name",param.getDoc().getName())

                    .build();
//                    .create(mediaType,"fileName="+param.getDoc().getName());
            Request request = new Request.Builder()
                    .url("https://aip.baidubce.com/rest/2.0/brain/online/v2/parser/task?access_token=" + accessToken)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Accept", "application/json")
                    .build();
            try (Response response = HTTP_CLIENT.newCall(request).execute()) {
                System.out.println(response.body().string());
                JSONObject task = JSONObject.parseObject(response.body().string());
                if(task.containsKey("result") && task.getJSONObject("result").containsKey("task_id")) {
                    String taskId = task.getJSONObject("result").getString("task_id");

                    FormBody taskRequest = new FormBody.Builder()
                            .add("task_id", taskId)
                            .build();
                    int curr = 0;
                    Request resultRequest = new Request.Builder()
                            .url("https://aip.baidubce.com/rest/2.0/brain/online/v2/parser/task/query?access_token=" + accessToken)
                            .method("POST", taskRequest)
                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
                            .addHeader("Accept", "application/json")
                            .build();
                    do{
                        Thread.sleep(curr == 0 ? 10000:5000);
                        try (Response resultResponse = HTTP_CLIENT.newCall(resultRequest).execute()) {
                            String string = resultResponse.body().string();
                            System.out.println(string);
                            JSONObject jsonObject = JSONObject.parseObject(string);
                            if(StringUtils.equals(jsonObject.getString("status"),"success")) {
                                return jsonObject;
                            }

                        }

                        curr++;
                    } while (curr < 5);
                }
                return task;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private <T> T getOrExecute(RedisCache redisCache,String key , int timeout, Supplier<T> getter) {
        Object o = redisCache.getCacheObject(key);
        if(o != null) {
            return (T) o;
        } else {
            T t = getter.get();
            redisCache.setCacheObject(key,t,timeout,TimeUnit.SECONDS);
            return t;
        }

    }
    public static String encodeFileToBase64(InputStream path) throws IOException {
//        Path path = Paths.get(filePath);
        byte[] fileBytes = IOUtils.readFully(path,path.available());
        return Base64.getEncoder().encodeToString(fileBytes);
    }
    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    static String getAccessToken(String apiKey,String secretKey) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + apiKey
                + "&client_secret=" + secretKey);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            return JSONObject.parseObject(response.body().string()).getString("access_token");
        }
    }
}
