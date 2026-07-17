package com.ruoyi.web.controller.tool;

import com.aliyun.oss.model.OSSObject;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.qiniu.QiniuUtils;
import com.ruoyi.xmbj.api.service.OssService;
import com.ruoyi.xmbj.service.IAfUserSubscriptionsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/api/scenes")
@CrossOrigin(origins = "*")
public class SceneController {

    private static final String SHARE_DIR = "./data/share";
    @Autowired
    private IAfUserSubscriptionsService afUserSubscriptionsService;



    @GetMapping("scheduled/executeUserPlan")
    public R<?> executeUserPlan() {
        afUserSubscriptionsService.updateProductSubscriptionsPlan();
        return R.ok();
    }

    @Autowired
    private OssService ossService;
//    private QiniuUtils qiniuUtils;
    private static final String STORAGE_DIR = "./data/scenes";
    private final Map<String, SceneData> sceneCache = new ConcurrentHashMap<>();

    public SceneController() {
        log.info("SceneController initialized");
        try {
            Files.createDirectories(Paths.get(STORAGE_DIR));
            log.info("Storage directory created: {}", STORAGE_DIR);
        } catch (IOException e) {
            log.error("Failed to create storage directory", e);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SceneData {
        private int sceneVersion;
        private String iv;
        private String ciphertext;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaveRequest {
        private String roomId;
        private int sceneVersion;
        private String iv;
        private String ciphertext;
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> getScene(@PathVariable String roomId) {
        try {
            SceneData data = loadScene(roomId);
            if (data != null) {
                return ResponseEntity.ok(data);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Failed to load scene: {}", roomId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> saveScene(@RequestBody SaveRequest request) {
        try {
            saveSceneToFile(request.getRoomId(), request.getSceneVersion(), request.getIv(), request.getCiphertext());
            return ResponseEntity.ok(new HashMap<String,Object>(){{put("success", true);}});
        } catch (Exception e) {
            log.error("Failed to save scene: {}", request.getRoomId(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    private SceneData loadScene(String roomId) throws IOException {
        SceneData cached = sceneCache.get(roomId);
        if (cached != null) {
            return cached;
        }

        Path filePath = getScenePath(roomId);
        if (Files.exists(filePath)) {
            List<String> content = Files.readAllLines(filePath);
            SceneData data = parseSceneData(content);
            sceneCache.put(roomId, data);
            return data;
        }
        return null;
    }

    private void saveSceneToFile(String roomId, int sceneVersion, String iv, String ciphertext) throws IOException {
        SceneData data = new SceneData(sceneVersion, iv, ciphertext);
        if(roomId != null) {
            sceneCache.put(roomId, data);
            Path filePath = getScenePath(roomId);
            String content = String.format("%d\n%s\n%s", sceneVersion, iv, ciphertext);
            Files.write(filePath, content.getBytes(StandardCharsets.UTF_8));
        }

    }

    private Path getScenePath(String roomId) {
        String safeRoomId = roomId.replaceAll("[^a-zA-Z0-9_-]", "_");
        return Paths.get(STORAGE_DIR, safeRoomId + ".txt");
    }

    private SceneData parseSceneData(List<String> content) {
        if (content != null && content.size() >= 3) {
            return new SceneData(
                    Integer.parseInt(content.get(0)),
                    content.get(1).trim(),
                    content.get(2).trim()
            );
        }
        return null;
    }


    // ===== File upload/download for collaboration images =====

    /**
     * Upload an image file (already encrypted+compressed by client) to Aliyun.
     * Uses multipart/form-data as required by Java file upload.
     *
     * @param roomId collaboration room ID
     * @param fileId excalidraw file ID
     * @param file   multipart file containing encrypted binary data
     */
    @PostMapping(value = "/files/{roomId}/{fileId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(
            @PathVariable String roomId,
            @PathVariable String fileId,
            @RequestParam("file") MultipartFile file) {
        Map<String,Object> result = new HashMap<>();
        try {
            String safeRoomId = sanitizeId(roomId);
            String safeFileId = sanitizeId(fileId);
            String ossFileKey = buildFileKey(safeRoomId, safeFileId);

            log.info("Uploading file to Aliyun: roomId={}, fileId={}, size={}", safeRoomId, safeFileId, file.getSize());

            try (InputStream is = file.getInputStream()) {
                ossService.uploadFile(ossFileKey,is,file.getSize());
            }
            result.put("success", true);
            result.put( "fileId", safeFileId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Failed to upload file to Aliyun: roomId={}, fileId={}", roomId, fileId, e);
            result.put("success", false);
            result.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(result);
        }
    }

    /**
     * Download an image file from Aliyun.
     * Returns the encrypted binary data as application/octet-stream.
     *
     * @param roomId collaboration room ID
     * @param fileId excalidraw file ID
     */
    @GetMapping("/files/{roomId}/{fileId}")
    public void downloadFile(
            @PathVariable String roomId,
            @PathVariable String fileId, HttpServletResponse response) {
        try {
            String safeRoomId = sanitizeId(roomId);
            String safeFileId = sanitizeId(fileId);
            String ossFileKey = buildFileKey(safeRoomId, safeFileId);

            if (!ossService.fileExists(ossFileKey)) {
                log.warn("File not found in Aliyun: roomId={}, fileId={}", safeRoomId, safeFileId);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

//            long contentLength = ossService.getFileSize(ossFileKey);

            OSSObject ossObject = ossService.downloadFile(ossFileKey);
            long contentLength = ossObject.getObjectMetadata().getContentLength();

            log.info("Downloading file: roomId={}, fileId={}, size={}", safeRoomId, safeFileId, contentLength);

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setContentLengthLong(contentLength);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileId + "\"");
            response.setHeader(HttpHeaders.CACHE_CONTROL, "public, max-age=31536000");

            try (InputStream is = ossObject.getObjectContent()) {
                FileCopyUtils.copy(is, response.getOutputStream());
            }
        } catch (Exception e) {
            log.error("Failed to download file from Aliyun: roomId={}, fileId={}", roomId, fileId, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 上传加密画布数据，生成分享链接 ID。
     * 前端调用方式：fetch(BACKEND_V2_POST, { method: "POST", body: payload.buffer })
     * 期望返回 JSON：{ "id": "xxx" }
     */
    @PostMapping("v2/post")
    public ResponseEntity<?> createShare(HttpServletRequest request) {
        Map<String,Object> ret = new HashMap<>();
        ret.put("success", true);
        try {
            String id = UUID.randomUUID().toString().replace("-", "");

            byte[] data = IOUtils.readFully(request.getInputStream(),request.getContentLength());

            if (data.length == 0) {
                        ret.put("error", true);
                        ret.put("message", "Empty payload");
                return ResponseEntity.badRequest().body(ret);
            }

            Path filePath = getSharePath(id);
            Files.write(filePath, data);

            log.info("Share created: id={}, size={} bytes", id, data.length);

            Map<String, Object> result = new HashMap<>();
            result.put("id", id);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            log.error("Failed to create share", e);
            ret.put("error", true);
            ret.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(ret);
        }
    }

    /**
     * 根据 ID 下载加密画布数据。
     * 前端调用方式：fetch(BACKEND_V2_GET + id)
     * 期望返回二进制数据（application/octet-stream）
     */
    @GetMapping("v2/{id}")
    public ResponseEntity<?> getShare(@PathVariable String id) {
        try {
            String safeId = sanitizeId(id);
            Path filePath = getSharePath(safeId);

            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] data = Files.readAllBytes(filePath);

            log.info("Share downloaded: id={}, size={} bytes", safeId, data.length);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CACHE_CONTROL, "public, max-age=31536000")
                    .body(data);

        } catch (Exception e) {
            log.error("Failed to get share: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    private Path getSharePath(String id) {
        String safeId = sanitizeId(id);
        return Paths.get(SHARE_DIR, safeId + ".bin");
    }

    /**
     * Sanitize room/file IDs to prevent path traversal in Aliyun keys.
     */
    private String sanitizeId(String id) {
        return id.replaceAll("[^a-zA-Z0-9_-]", "_");
    }

    /**
     * Build the Aliyun object key for a scene file.
     *
     * @param roomId collaboration room ID
     * @param fileId excalidraw file ID
     * @return Aliyun key like "scenes/files/{roomId}/{fileId}"
     */
    private String buildFileKey(String roomId, String fileId) {
        return String.format("scenes/files/%s/%s", roomId, fileId);
    }

}