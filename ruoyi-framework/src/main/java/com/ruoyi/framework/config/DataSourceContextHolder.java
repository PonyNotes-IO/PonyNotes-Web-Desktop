package com.ruoyi.framework.config;

/**
 * 动态数据源上下文管理器
 * 用于在运行时选择主数据库或从数据库
 */
public class DataSourceContextHolder {

    /**
     * 线程本地变量，存储当前线程的数据源标识
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 数据源标识：主数据库 (MySQL)
     */
    public static final String MASTER = "master";

    /**
     * 数据源标识：从数据库 (PostgreSQL)
     */
    public static final String SLAVE = "slave";

    /**
     * 设置当前线程的数据源
     * 
     * @param dataSourceKey 数据源标识 (master/slave)
     */
    public static void setDataSourceKey(String dataSourceKey) {
        CONTEXT_HOLDER.set(dataSourceKey);
    }

    /**
     * 获取当前线程的数据源
     * 
     * @return 数据源标识，如果未设置则返回 master
     */
    public static String getDataSourceKey() {
        return CONTEXT_HOLDER.get() != null ? CONTEXT_HOLDER.get() : MASTER;
    }

    /**
     * 清除当前线程的数据源设置
     */
    public static void clearDataSourceKey() {
        CONTEXT_HOLDER.remove();
    }

    /**
     * 切换到主数据库 (MySQL)
     */
    public static void useMaster() {
        setDataSourceKey(MASTER);
    }

    /**
     * 切换到从数据库 (PostgreSQL)
     */
    public static void useSlave() {
        setDataSourceKey(SLAVE);
    }
}
