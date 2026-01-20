package com.ruoyi.common.enums;

import java.util.Arrays;

/**
 * 支付类型枚举
 * 对应前端的 'wechat'（微信支付）和 'alipay'（支付宝支付）
 */
public enum PaymentTypeEnum {

    WECHAT("wechat", "微信支付"),
    ALIPAY("alipay", "支付宝支付");

    /**
     * 支付类型标识（与前端交互的关键字）
     */
    private final String code;

    /**
     * 支付类型描述
     */
    private final String desc;

    PaymentTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // Getter方法
    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据code获取枚举实例（用于前端参数转换）
     */
    public static PaymentTypeEnum getByCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("不支持的支付类型：" + code));
    }

    /**
     * 校验支付类型是否合法
     */
    public static boolean isValid(String code) {
        return Arrays.stream(values()).anyMatch(type -> type.code.equals(code));
    }
}