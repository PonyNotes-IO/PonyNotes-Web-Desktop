package com.ruoyi.xmbj.api.config;

import com.ruoyi.common.utils.SecurityUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ruoyi.xmbj.api.service.XmbjAuthService;

/**
 * PonyNotes API 客户端配置
 * 
 * 主要功能：
 * 1. 配置 Feign 客户端的请求拦截器
 * 2. 自动为每个请求添加 Bearer token（XMBJ access_token）
 * 3. 处理 token 过期的场景
 * 
 * @author ruoyi
 */
@Configuration
public class PonynotesClientConfig {

    private static final Logger log = LoggerFactory.getLogger(PonynotesClientConfig.class);

    @Autowired
    private XmbjAuthService xmbjAuthService;

    /**
     * 为 PonyNotes Feign 客户端注册请求拦截器
     * 
     * 拦截器的职责：
     * 1. 获取当前用户的 XMBJ access_token
     * 2. 将 token 添加到请求头中（Authorization: Bearer {token}）
     * 3. 如果 token 不存在，记录警告信息
     * 
     * 工作流程：
     * - 每次发送请求前触发此拦截器
     * - 从 SecurityUtils 获取当前用户 ID
     * - 从 Redis 获取该用户的 access_token
     * - 添加到 HTTP 请求头中
     * 
     * @return RequestInterceptor 拦截器实例
     */
    @Bean
    public RequestInterceptor ponynotesRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                try {
                    // 获取当前认证用户的 ID
                    Long userId = SecurityUtils.getUserId();

                    if (userId == null) {
                        // 如果无当前用户信息，可能是在后台任务或内部调用中
                        log.debug("未能获取当前用户信息，将不添加认证 token");
                        return;
                    }

                    // 从 Redis 获取用户的 XMBJ access_token
                    String accessToken = xmbjAuthService.getAccessToken(userId);

                    if (accessToken == null || accessToken.isEmpty()) {
                        // Token 不存在或已过期
                        log.warn("用户的 XMBJ access_token 不存在或已过期，用户ID: {}", userId);
                        // 不中断请求，让 API 返回 401 Unauthorized
                        return;
                    }

                    // 将 token 添加到请求头
                    // 格式: Authorization: Bearer {access_token}
                    String authorizationHeader = "Bearer " + accessToken;
                    template.header("Authorization", authorizationHeader);

                    log.debug("已为 PonyNotes 请求添加 Bearer token，用户ID: {}", userId);

                } catch (Exception e) {
                    log.error("在 PonyNotes Feign 拦截器中获取 token 异常", e);
                    // 不中断请求，让请求继续
                }
            }
        };
    }

    /**
     * 可选：添加日志级别配置
     * 
     * 用于调试 Feign 请求和响应
     * 
     * @return Feign 日志级别
     */
    @Bean
    public feign.Logger.Level feignLoggerLevel() {
        // 可以设置为 NONE, BASIC, HEADERS, FULL
        // BASIC: 记录请求方法、URL 和响应状态码
        // HEADERS: 记录请求/响应头和状态码
        // FULL: 记录完整的请求/响应（包含 body）
        return feign.Logger.Level.BASIC;
    }
}
