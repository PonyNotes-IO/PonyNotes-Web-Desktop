package com.ruoyi.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;

public class OrderNoGenerator {
    private static final Logger log = LoggerFactory.getLogger(OrderNoGenerator.class);

    // 机器标识（2位，避免多机器冲突）
    private static final String MACHINE_ID;
    // 进程ID（2位，避免同一机器多进程冲突）
    private static final String PID;
    // 自增序列号（3位，同一毫秒内自增，避免同一时间戳冲突）
    private static final AtomicLong SEQUENCE = new AtomicLong(0);
    // 上次生成订单号的时间戳（毫秒），用于控制序列号重置
    private static long LAST_TIMESTAMP = -1L;

    static {
        // 初始化机器标识（取本地非回环IP的哈希后2位）
        MACHINE_ID = initMachineId();
        // 初始化进程ID（取进程ID后2位）
        PID = initProcessId();
    }

    /**
     * 生成唯一订单号
     * 格式：{支付类型}_{时间戳(13位)}_{机器标识(2位)}_{进程ID(2位)}_{序列号(3位)}
     * 示例：alipay_1698765432100_8f_1a_001
     *
     * @param paymentType 支付类型（如alipay/wechat）
     * @return 唯一订单号
     */
    public static String generate(String paymentType) {
        // 1. 获取当前时间戳（毫秒），确保时间递增（解决时钟回拨问题）
        long timestamp = getCurrentTimestamp();

        // 2. 处理序列号：同一毫秒内自增，超过999则等待下一毫秒
        long sequence;
        synchronized (OrderNoGenerator.class) {
            if (timestamp == LAST_TIMESTAMP) {
                // 同一毫秒：序列号自增，最大999
                sequence = SEQUENCE.incrementAndGet();
                if (sequence > 999) {
                    // 序列号超过最大值，等待到下一毫秒
                    timestamp = waitUntilNextMillis(LAST_TIMESTAMP);
                    sequence = 0;
                    SEQUENCE.set(0);
                }
            } else {
                // 不同毫秒：序列号重置为0
                SEQUENCE.set(0);
                sequence = 0;
            }
            LAST_TIMESTAMP = timestamp;
        }

        // 3. 拼接订单号
        return String.format("%s_%d_%s_%s_%03d",
                paymentType,
                timestamp,
                MACHINE_ID,
                PID,
                sequence);
    }

    /**
     * 获取当前时间戳（毫秒），并处理时钟回拨（确保时间不小于上次时间）
     */
    private static long getCurrentTimestamp() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < LAST_TIMESTAMP) {
            // 时钟回拨：使用上次时间+1（避免时间倒退导致重复）
            log.warn("时钟回时钟回拨，上次时间：{}，当前时间：{}", LAST_TIMESTAMP, timestamp);
            timestamp = LAST_TIMESTAMP + 1;
        }
        return timestamp;
    }

    /**
     * 等待到下一毫秒（当序列号用尽时）
     */
    private static long waitUntilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    /**
     * 初始化机器标识（取本地非回环IP的哈希值后2位十六进制防止多机器冲突）
     */
    private static String  initMachineId() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    // 排除回环地址和IPv6
                    if (!addr.isLoopbackAddress() && addr.getHostAddress().indexOf(':') == -1) {
                        // 取IP地址哈希后2位十六进制（避免暴露真实IP）
                        int hash = addr.getHostAddress().hashCode() & 0xFF;
                        return String.format("%02x", hash);
                    }
                }
            }
        } catch (SocketException e) {
            log.warn("获取机器标识失败，使用默认值", e);
        }
        // 异常时使用默认值
        return "00";
    }

    /**
     * 初始化进程ID（取进程ID后2位十六进制，防止同一机器多进程冲突）
     */
    private static String initProcessId() {
        try {
            // 从JVM信息中获取进程ID（兼容Windows/Linux）
            String processName = ManagementFactory.getRuntimeMXBean().getName();
            String pid = processName.split("@")[0];
            int pidInt = Integer.parseInt(pid);
            // 取进程ID后2位十六进制
            return String.format("%02x", pidInt & 0xFF);
        } catch (Exception e) {
            log.warn("获取进程ID失败，使用默认值", e);
        }
        // 异常时使用默认值
        return "00";
    }
}