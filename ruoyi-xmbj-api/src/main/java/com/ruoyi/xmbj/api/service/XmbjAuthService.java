package com.ruoyi.xmbj.api.service;

import com.ruoyi.xmbj.api.protocol.ClientUser;
import com.ruoyi.xmbj.api.protocol.PhoneLoginRequest;
import com.ruoyi.xmbj.api.protocol.PhoneLoginResponse;
import com.ruoyi.xmbj.api.protocol.AbstractResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * XMBJ API 认证服务
 * 
 * 负责：
 * 1. 与 XMBJ API 进行用户认证（手机登录）
 * 2. 管理和缓存用户的 access_token
 * 3. 处理 token 的过期和刷新
 * 
 * @author ruoyi
 */
@Service
public class XmbjAuthService {

    private static final Logger log = LoggerFactory.getLogger(XmbjAuthService.class);

    // Redis 中 XMBJ token 的 Key 前缀
    private static final String XMBJ_TOKEN_KEY_PREFIX = "xmbj:token:";

    // Redis 中 XMBJ 用户信息的 Key 前缀
    private static final String XMBJ_USER_KEY_PREFIX = "xmbj:user:";


    private SmsServiceClient smsServiceClient;

    private PonynotesService ponynotesService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate; // 注入RedisTemplate

    /**
     * 通过手机号和验证码获取 XMBJ 的 access_token
     *
     * 流程：
     * 1. 调用 XMBJ API 的 phoneLogin 接口
     * 2. 解析响应并提取 access_token
     * 3. 返回 token 信息
     *
     * @param phone 手机号
     * @param code  验证码
     * @return PhoneLoginResponse 包含 access_token 等信息
     * @throws Exception 如果 API 调用失败
     */
    public PhoneLoginResponse getXmbjTokenByPhoneCode(String phone, String code) {
        try {
            log.info("开始调用 XMBJ 手机登录接口，手机号: {}", phone);

            PhoneLoginRequest request = new PhoneLoginRequest();
            request.setPhone(phone);
            request.setCode(code);

            // 调用 XMBJ SMS 服务的 phoneLogin 接口
            AbstractResponse<PhoneLoginResponse> response = smsServiceClient.phoneLogin(request);

            if (response == null) {
                log.error("XMBJ phoneLogin 返回为空");
                throw new RuntimeException("XMBJ phoneLogin response is null");
            }

            // 检查响应状态
            if (response.getData() == null) {
                log.error("XMBJ phoneLogin 返回数据为空，错误信息: {}", response.getMsg());
                throw new RuntimeException("XMBJ phoneLogin failed: " + response.getMsg());
            }

            PhoneLoginResponse loginResponse = response.getData();
            log.info("XMBJ 手机登录成功，用户ID: {}", loginResponse.getUser().getId());

            return loginResponse;
        } catch (Exception e) {
            log.error("调用 XMBJ phoneLogin 接口异常", e);
            throw new RuntimeException("Failed to get XMBJ token: " + e.getMessage(), e);
        }
    }

    /**
     * 将 XMBJ 的 access_token 存储到 Redis
     *
     * 存储结构：
     * - Key: xmbj:token:{userId}
     * - Value: access_token
     * - TTL: expires_in 秒
     *
     * 同时存储用户信息和完整的 token 响应信息
     *
     * @param userId        本地系统的用户 ID
     * @param loginResponse XMBJ 登录响应，包含 access_token 和过期信息
     */
    public void storeAccessToken(Long userId, PhoneLoginResponse loginResponse) {
        try {
            if (userId == null || loginResponse == null) {
                throw new IllegalArgumentException("userId and loginResponse cannot be null");
            }

            String accessToken = loginResponse.getAccess_token();
            Long expiresIn = loginResponse.getExpires_in();

            if (accessToken == null || accessToken.isEmpty()) {
                throw new RuntimeException("access_token from XMBJ is empty");
            }

            String tokenKey = XMBJ_TOKEN_KEY_PREFIX + userId;
            String userKey = XMBJ_USER_KEY_PREFIX + userId;

            // 计算 token 过期的时间戳
            long expiresAtTimestamp = System.currentTimeMillis() + (expiresIn * 1000);

            // 将 token 存储到 Redis（自动过期）
            if (expiresIn != null && expiresIn > 0) {
                redisTemplate.opsForValue().set(tokenKey, accessToken, java.time.Duration.ofSeconds(expiresIn));
                log.info("已将 XMBJ access_token 存储到 Redis，用户ID: {}，过期时间: {}秒", userId, expiresIn);
            } else {
                redisTemplate.opsForValue().set(tokenKey, accessToken);
                log.warn("XMBJ access_token 没有过期时间信息，用户ID: {}", userId);
            }

            // 存储用户信息（用于记录用户在 XMBJ 系统的身份）
            storeUserInfo(userKey, loginResponse, expiresIn);

            log.info("XMBJ token 和用户信息已存储到 Redis，用户ID: {}", userId);
        } catch (Exception e) {
            log.error("存储 XMBJ token 到 Redis 失败，用户ID: {}", userId, e);
            throw new RuntimeException("Failed to store access token: " + e.getMessage(), e);
        }
    }

    /**
     * 从 Redis 获取用户的 XMBJ access_token
     *
     * @param userId 用户 ID
     * @return access_token，如果不存在或已过期则返回 null
     */
    public String getAccessToken(Long userId) {
        try {
            if (userId == null) {
                return null;
            }

            String tokenKey = XMBJ_TOKEN_KEY_PREFIX + userId;
            Object tokenObj = redisTemplate.opsForValue().get(tokenKey);
            String accessToken = tokenObj != null ? tokenObj.toString() : null;

            if (accessToken == null) {
                log.warn("XMBJ access_token 不存在或已过期，用户ID: {}", userId);
                return null;
            }

            log.debug("成功获取 XMBJ access_token，用户ID: {}", userId);
            return accessToken;
        } catch (Exception e) {
            log.error("从 Redis 获取 XMBJ token 失败，用户ID: {}", userId, e);
            return null;
        }
    }

    /**
     * 检查用户的 XMBJ access_token 是否存在且有效
     *
     * @param userId 用户 ID
     * @return true 表示 token 存在且有效；false 表示 token 不存在或已过期
     */
    public boolean hasValidAccessToken(Long userId) {
        return getAccessToken(userId) != null;
    }

    /**
     * 删除用户的 XMBJ access_token（登出时使用）
     *
     * @param userId 用户 ID
     */
    public void removeAccessToken(Long userId) {
        try {
            if (userId == null) {
                return;
            }

            String tokenKey = XMBJ_TOKEN_KEY_PREFIX + userId;
            String userKey = XMBJ_USER_KEY_PREFIX + userId;

            redisTemplate.delete(tokenKey);
            redisTemplate.delete(userKey);

            log.info("已删除 XMBJ token 和用户信息，用户ID: {}", userId);
        } catch (Exception e) {
            log.error("删除 XMBJ token 失败，用户ID: {}", userId, e);
        }
    }

    /**
     * 存储用户在 XMBJ 系统的身份信息
     *
     * 包含：
     * - XMBJ 用户 ID
     * - 邮箱
     * - 创建时间等
     *
     * @param userKey       Redis key
     * @param loginResponse 登录响应
     * @param expiresIn     过期时间
     */
    private void storeUserInfo(String userKey, PhoneLoginResponse loginResponse, Long expiresIn) {
        try {
            PhoneLoginResponse.PhoneLoginUser user = loginResponse.getUser();
            if (user != null) {
                // 存储用户信息，与 token 相同的过期时间
                Long ttl = expiresIn != null ? expiresIn : 3600L;
                redisTemplate.opsForValue().set(userKey, user, java.time.Duration.ofSeconds(ttl));
                log.debug("XMBJ 用户信息已存储，XMBJ用户ID: {}", user.getId());
            }
        } catch (Exception e) {
            log.error("存储 XMBJ 用户信息失败", e);
            // 不抛异常，因为用户信息不影响 token 的使用
        }
    }

    /**
     * 获取用户在 XMBJ 系统的身份信息
     *
     * @param userId 本地用户 ID
     * @return XMBJ 用户信息，如果不存在则返回 null
     */
    public PhoneLoginResponse.PhoneLoginUser getXmbjUserInfo(Long userId) {
        try {
            if (userId == null) {
                return null;
            }

            String userKey = XMBJ_USER_KEY_PREFIX + userId;
            Object userObj = redisTemplate.opsForValue().get(userKey);
            return (PhoneLoginResponse.PhoneLoginUser) userObj;
        } catch (Exception e) {
            log.error("获取 XMBJ 用户信息失败，用户ID: {}", userId, e);
            return null;
        }
    }

    /**
     * 使用刷新令牌刷新 access_token
     *
     * 注意：当前 XMBJ API 是否支持 refresh_token 需要与后端确认
     * 如果支持，可在此实现相应逻辑
     *
     * @param userId 用户 ID
     * @return 新的 access_token，如果刷新失败则返回 null
     */
    public String refreshAccessToken(Long userId) {
        try {
            log.info("开始刷新 XMBJ access_token，用户ID: {}", userId);

            // 获取 XMBJ 用户信息中的刷新令牌
            PhoneLoginResponse.PhoneLoginUser userInfo = getXmbjUserInfo(userId);

            if (userInfo == null) {
                log.warn("无法获取 XMBJ 用户信息用于刷新 token，用户ID: {}", userId);
                return null;
            }

            // TODO: 根据 XMBJ API 文档实现 token 刷新逻辑
            // 目前 XMBJ API 可能不支持 refresh_token，需要重新登录获取新的 token
            log.warn("XMBJ 暂不支持 token 刷新，用户需要重新登录，用户ID: {}", userId);

            return null;
        } catch (Exception e) {
            log.error("刷新 XMBJ token 失败，用户ID: {}", userId, e);
            return null;
        }
    }

    /**
     * 根据用户信息获取 XMBJ 系统的客户用户信息
     *
     * @param userInfo 用户信息
     * @return ClientUser 客户用户信息，如果获取失败则返回 null
     */
    public ClientUser getClientUserByUserInfo(String userInfo) {
        try {
            log.info("开始获取 XMBJ 客户用户信息，用户信息: {}", userInfo);
            return ponynotesService.getClientUserByUserInfo(userInfo);
        } catch (Exception e) {
            log.error("获取 XMBJ 客户用户信息失败，用户信息: {}", userInfo, e);
            return null;
        }
    }
}
