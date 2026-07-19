package com.ruoyi.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import com.ruoyi.common.utils.StringUtils;

/**
 * Repeatable 过滤器
 * 
 * @author ruoyi
 */
public class RepeatableFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String contentType = httpRequest.getContentType();
            String requestUri = httpRequest.getRequestURI();
            boolean isJsonRequest = StringUtils.startsWithIgnoreCase(contentType, MediaType.APPLICATION_JSON_VALUE);
            boolean isBinaryShareRequest = requestUri.contains("/api/scenes/v2/post");
            if (isJsonRequest) {
                requestWrapper = new RepeatedlyRequestWrapper(httpRequest, response);
            } else if (isBinaryShareRequest) {
                requestWrapper = new BinaryRepeatedlyRequestWrapper(httpRequest);
            }
        }
        if (null == requestWrapper)
        {
            chain.doFilter(request, response);
        }
        else
        {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy()
    {

    }
}
