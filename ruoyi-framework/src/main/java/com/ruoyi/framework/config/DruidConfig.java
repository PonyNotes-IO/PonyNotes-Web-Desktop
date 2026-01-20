package com.ruoyi.framework.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.sql.DataSource;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.util.Utils;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.config.properties.DruidProperties;
import com.ruoyi.framework.datasource.DynamicDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * druid 配置多数据源
 * 
 * @author ruoyi
 */
@Configuration
public class DruidConfig implements ApplicationContextAware
{
    private ApplicationContext applicationContext;

    public DruidConfig()
    {
        System.out.println("DruidConfig: 构造函数被调用");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
        System.out.println("DruidConfig: setApplicationContext 被调用");
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource(DruidProperties druidProperties)
    {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    @Bean
    public DataSource slaveDataSource(DruidProperties druidProperties, Environment env)
    {
        System.out.println("DruidConfig: 正在创建 slaveDataSource Bean");
        DruidDataSource dataSource = new DruidDataSource();
        try
        {
            dataSource.setDriverClassName(env.getProperty("spring.datasource.druid.slave.driver-class-name"));
            dataSource.setUrl(env.getProperty("spring.datasource.druid.slave.url"));
            dataSource.setUsername(env.getProperty("spring.datasource.druid.slave.username"));
            dataSource.setPassword(env.getProperty("spring.datasource.druid.slave.password"));
            
            DataSource result = druidProperties.dataSource(dataSource);
            System.out.println("DruidConfig: slaveDataSource Bean 创建成功: " + result);
            return result;
        }
        catch (Exception e)
        {
            System.out.println("DruidConfig: 创建 slaveDataSource Bean 失败: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    @org.springframework.context.annotation.DependsOn("slaveDataSource")
    @org.springframework.beans.factory.annotation.Autowired
    public DynamicDataSource dataSource(@org.springframework.beans.factory.annotation.Qualifier("masterDataSource") DataSource masterDataSource,
                                    @org.springframework.beans.factory.annotation.Qualifier("slaveDataSource") DataSource slaveDataSource)
    {
        System.out.println("DruidConfig: dataSource 方法被调用");
        System.out.println("DruidConfig: masterDataSource = " + masterDataSource);
        System.out.println("DruidConfig: slaveDataSource = " + slaveDataSource);
        
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
        System.out.println("DruidConfig: 添加主数据源 MASTER");
        
        if (slaveDataSource != null)
        {
            targetDataSources.put(DataSourceType.SLAVE.name(), slaveDataSource);
            System.out.println("DruidConfig: 添加从数据源 SLAVE");
        }
        else
        {
            System.out.println("DruidConfig: 从数据源 SLAVE 为 null，跳过添加");
        }
        
        DynamicDataSource dynamicDataSource = new DynamicDataSource(masterDataSource, targetDataSources);
        System.out.println("DruidConfig: DynamicDataSource 创建完成");
        return dynamicDataSource;
    }

    /**
     * 事务管理器，绑定到动态数据源
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dynamicDataSource)
    {
        return new DataSourceTransactionManager(dynamicDataSource);
    }
    
    /**
     * 设置数据源
     * 
     * @param targetDataSources 备选数据源集合
     * @param sourceName 数据源名称
     * @param beanName bean名称
     */
    public void setDataSource(Map<Object, Object> targetDataSources, String sourceName, String beanName)
    {
        try
        {
            DataSource dataSource = SpringUtils.getBean(beanName);
            targetDataSources.put(sourceName, dataSource);
            System.out.println("DruidConfig: 成功添加数据源 " + sourceName + " -> " + beanName);
        }
        catch (Exception e)
        {
            System.out.println("DruidConfig: 添加数据源失败 " + sourceName + " -> " + beanName + ", 错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 去除监控页面底部的广告
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    @ConditionalOnProperty(name = "spring.datasource.druid.statViewServlet.enabled", havingValue = "true")
    public FilterRegistrationBean removeDruidFilterRegistrationBean()
    {
        System.out.println("DruidConfig: 创建 removeDruidFilterRegistrationBean");
        // 提取common.js的配置路径
        String pattern = "/druid/*";
        String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");
        final String filePath = "support/http/resources/js/common.js";
        // 创建filter进行过滤
        Filter filter = new Filter()
        {
            @Override
            public void init(javax.servlet.FilterConfig filterConfig) throws ServletException
            {
            }
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException
            {
                chain.doFilter(request, response);
                // 重置缓冲区，响应头不会被重置
                response.resetBuffer();
                // 获取common.js
                String text = Utils.readFromResource(filePath);
                // 正则替换banner, 除去底部的广告信息
                text = text.replaceAll("<a.*?banner\"></a><br/>", "");
                text = text.replaceAll("powered.*?shrek.wang</a>", "");
                response.getWriter().write(text);
            }
            @Override
            public void destroy()
            {
            }
        };
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns(commonJsPattern);
        System.out.println("DruidConfig: removeDruidFilterRegistrationBean 创建完成");
        return registrationBean;
    }
}
