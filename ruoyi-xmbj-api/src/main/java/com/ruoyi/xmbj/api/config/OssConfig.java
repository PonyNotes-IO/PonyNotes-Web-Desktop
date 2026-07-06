package com.ruoyi.xmbj.api.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Slf4j
@Getter
@Configuration
public class OssConfig {

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.access-key-id}")
    private String accessKeyId;

    @Value("${oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${oss.bucket-name}")
    private String bucketName;

    private OSS ossClient;

    @Bean
    public OSS ossClient() {
        log.info("Initializing OSS client with endpoint: {}, bucket: {}", endpoint, bucketName);
        this.ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        return this.ossClient;
    }

    @PreDestroy
    public void destroy() {
        if (this.ossClient != null) {
            this.ossClient.shutdown();
            log.info("OSS client shutdown");
        }
    }
}
