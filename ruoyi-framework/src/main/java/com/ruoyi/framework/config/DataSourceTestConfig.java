package com.ruoyi.framework.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DataSourceTestConfig
{
    private final ApplicationContext applicationContext;

    public DataSourceTestConfig(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void testDataSourceBeans()
    {
        System.out.println("DataSourceTestConfig: 开始检查数据源 Bean");
        
        String[] beanNames = applicationContext.getBeanNamesForType(javax.sql.DataSource.class);
        System.out.println("DataSourceTestConfig: 找到的 DataSource Bean 数量: " + beanNames.length);
        for (String beanName : beanNames)
        {
            System.out.println("DataSourceTestConfig: DataSource Bean 名称: " + beanName);
        }
        
        try
        {
            Object masterDataSource = applicationContext.getBean("masterDataSource");
            System.out.println("DataSourceTestConfig: masterDataSource 存在: " + masterDataSource);
        }
        catch (Exception e)
        {
            System.out.println("DataSourceTestConfig: masterDataSource 不存在: " + e.getMessage());
        }
        
        try
        {
            Object slaveDataSource = applicationContext.getBean("slaveDataSource");
            System.out.println("DataSourceTestConfig: slaveDataSource 存在: " + slaveDataSource);
        }
        catch (Exception e)
        {
            System.out.println("DataSourceTestConfig: slaveDataSource 不存在: " + e.getMessage());
        }
        
        try
        {
            Object dynamicDataSource = applicationContext.getBean("dynamicDataSource");
            System.out.println("DataSourceTestConfig: dynamicDataSource 存在: " + dynamicDataSource);
        }
        catch (Exception e)
        {
            System.out.println("DataSourceTestConfig: dynamicDataSource 不存在: " + e.getMessage());
        }
    }
}
