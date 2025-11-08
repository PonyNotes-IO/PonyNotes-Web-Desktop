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
//        DataSource annotation = invocation.getMethod().getAnnotation(DataSource.class);
        if(!ms.getId().startsWith("com.ruoyi.xmbj.")) {
            logger.warn("执行主库查询[{}]",ms.getId());
            DynamicDataSourceContextHolder.setDataSourceType("master");
        } else {
            logger.warn("查询从库,执行主库查询[{}]",ms.getId());
            DynamicDataSourceContextHolder.setDataSourceType("slave");
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