package com.ruoyi.xmbj.api.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Slf4j
@Service
public class OssService {

    private final OSS ossClient;
    private final String bucketName;

    @Autowired
    public OssService(OSS ossClient, @Value("${oss.bucket-name}") String bucketName) {
        this.ossClient = ossClient;
        this.bucketName = bucketName;
    }

    /**
     * Upload a file to OSS.
     *
     * @param key         OSS object key, e.g. "scenes/files/{roomId}/{fileId}"
     * @param inputStream file content
     * @param contentLength file size in bytes
     */
    public void uploadFile(String key, InputStream inputStream, long contentLength) {
        log.debug("Uploading to OSS: bucket={}, key={}, size={}", bucketName, key, contentLength);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);
        metadata.setContentType("application/octet-stream");

        PutObjectRequest request = new PutObjectRequest(bucketName, key, inputStream, metadata);
        ossClient.putObject(request);
        log.info("Uploaded to OSS: {}", key);
    }

    /**
     * Download a file from OSS.
     *
     * @param key OSS object key
     * @return OSSObject containing the stream and metadata
     */
    public OSSObject downloadFile(String key) {
        log.debug("Downloading from OSS: bucket={}, key={}", bucketName, key);
        GetObjectRequest request = new GetObjectRequest(bucketName, key);
        return ossClient.getObject(request);
    }

    /**
     * Check if a file exists in OSS.
     */
    public boolean fileExists(String key) {
        return ossClient.doesObjectExist(bucketName, key);
    }

    /**
     * Build the OSS object key for a scene file.
     *
     * @param roomId collaboration room ID
     * @param fileId excalidraw file ID
     * @return OSS key like "scenes/files/{roomId}/{fileId}"
     */
    public String buildFileKey(String roomId, String fileId) {
        return String.format("scenes/files/%s/%s", roomId, fileId);
    }
}
