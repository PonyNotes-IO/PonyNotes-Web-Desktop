package com.ruoyi.framework.datasource;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.apache.ibatis.executor.Executor;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

@Intercepts({
        @Signature(type = Executor.class,method = "query",args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
        ,@Signature(type = Executor.class,method = "queryCursor",args = {MappedStatement.class, Object.class, RowBounds.class})
        ,@Signature(type = Executor.class,method = "update",args = {MappedStatement.class, Object.class})
//        ,@Signature(type = Executor.class,method = "query",args = {})

//        ,@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class DynamicDataSourceInterceptor implements Interceptor {
    Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        String msId = ms.getId();
        logger.warn("当前执行的SQL语句ID: [{}]", msId);
        
        // 检查是否包含 xmbj 相关的包路径，但排除 AppVersionMapper
        boolean containsXmbj = msId.contains("xmbj");
        logger.warn("是否包含xmbj（排除AppVersion）: [{}]", containsXmbj);
        
        // 根据包路径选择数据源
        if(!containsXmbj) {
            logger.warn("执行主库查询[{}]", msId);
            logger.warn("设置数据源类型为: MASTER");
            DynamicDataSourceContextHolder.setDataSourceType("MASTER");
        } else {
            logger.warn("执行从库查询[{}]", msId);
            logger.warn("设置数据源类型为: SLAVE");
            DynamicDataSourceContextHolder.setDataSourceType("SLAVE");
        }
        try {
            return invocation.proceed();
        } finally {
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}