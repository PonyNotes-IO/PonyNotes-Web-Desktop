package com.ruoyi.framework.security.filter;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.security.token.PonyNotesAuthenticationToken;
import com.ruoyi.framework.web.service.PonyNotesTokenService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class PonynotesJwtTokenFilter extends OncePerRequestFilter  {

    @Autowired
    private PonyNotesTokenService ponyNotesTokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Claims loginUser = ponyNotesTokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser) && isNotLogin(SecurityUtils.getAuthentication()))
        {
            PonyNotesAuthenticationToken authenticationToken = new PonyNotesAuthenticationToken(loginUser.getSubject(),loginUser.getSubject());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private boolean isNotLogin(Authentication authentication) {
        if(authentication == null) return true;
        return "anonymousUser".equals(authentication.getPrincipal());
    }
}
