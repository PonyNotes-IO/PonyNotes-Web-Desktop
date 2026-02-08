package com.ruoyi.framework.config.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.lang3.RegExUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import com.ruoyi.common.annotation.Anonymous;

/**
 * 设置Anonymous注解允许匿名访问的url
 * 
 * @author ruoyi
 */
@Configuration
public class PermitAllUrlProperties implements InitializingBean, ApplicationContextAware
{
    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");
    private static final Logger log = LoggerFactory.getLogger(PermitAllUrlProperties.class);

    private ApplicationContext applicationContext;

    private List<String> urls = new ArrayList<>();

    public String ASTERISK = "*";

    @Override
    public void afterPropertiesSet()
    {
        log.info("PermitAllUrlProperties: 开始扫描 @Anonymous 注解");
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        map.keySet().forEach(info -> {
            HandlerMethod handlerMethod = map.get(info);

            // 获取方法上边的注解 替代path variable 为 *
            Anonymous method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Anonymous.class);
            Optional.ofNullable(method).ifPresent(anonymous -> {
                try
                {
                    Object patternsCondition = info.getPatternsCondition();
                    if (patternsCondition != null)
                    {
                        java.lang.reflect.Method getPatternsMethod = patternsCondition.getClass().getMethod("getPatterns");
                        Set<String> patterns = (Set<String>) getPatternsMethod.invoke(patternsCondition);
                        patterns.forEach(url -> {
                            String processedUrl = RegExUtils.replaceAll(url, PATTERN, ASTERISK);
                            log.info("PermitAllUrlProperties: 找到方法级别的 @Anonymous 注解，URL: {}", processedUrl);
                            urls.add(processedUrl);
                        });
                    }
                }
                catch (Exception e)
                {
                    log.error("PermitAllUrlProperties: 处理方法级别的 @Anonymous 注解时出错: {}", e.getMessage());
                }
            });

            // 获取类上边的注解, 替代path variable 为 *
            Anonymous controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Anonymous.class);
            Optional.ofNullable(controller).ifPresent(anonymous -> {
                try
                {
                    Object patternsCondition = info.getPatternsCondition();
                    if (patternsCondition != null)
                    {
                        java.lang.reflect.Method getPatternsMethod = patternsCondition.getClass().getMethod("getPatterns");
                        Set<String> patterns = (Set<String>) getPatternsMethod.invoke(patternsCondition);
                        patterns.forEach(url -> {
                            String processedUrl = RegExUtils.replaceAll(url, PATTERN, ASTERISK);
                            log.info("PermitAllUrlProperties: 找到类级别的 @Anonymous 注解，URL: {}", processedUrl);
                            urls.add(processedUrl);
                        });
                    }
                }
                catch (Exception e)
                {
                    log.error("PermitAllUrlProperties: 处理类级别的 @Anonymous 注解时出错: {}", e.getMessage());
                }
            });
        });
        log.info("PermitAllUrlProperties: 扫描完成，共找到 {} 个允许匿名访问的 URL", urls.size());
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException
    {
        this.applicationContext = context;
    }

    public List<String> getUrls()
    {
        return urls;
    }

    public void setUrls(List<String> urls)
    {
        this.urls = urls;
    }
}
