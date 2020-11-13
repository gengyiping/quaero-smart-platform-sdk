package com.quaero.quaerosmartplatform.domain.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.quaero.quaerosmartplatform.domain.enumeration.ResultCode;
import com.quaero.quaerosmartplatform.domain.result.PlatformResult;
import com.quaero.quaerosmartplatform.utils.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by echisan on 2018/6/23
 */
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailService;

    public static JWTAuthorizationFilter filter;

    @PostConstruct
    public void init() {
        filter = this;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String token = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        if(token != null && StringUtils.startsWith(token, JwtTokenUtils.TOKEN_PREFIX)) {
            token = StringUtils.substring(token, JwtTokenUtils.TOKEN_PREFIX.length());
        } else {
            chain.doFilter(request, response);
            return;
        }

        try {
            String username = JwtTokenUtils.getUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                /*
                 *  注意：
                 *       这里代码不应该从数据库中去查，而是从缓存中根据token去查，目前只是做测试，无关紧要
                 *      如果是真正的项目实际开发需要增加缓存
                 */
                UserDetails userDetails = filter.userDetailService.loadUserByUsername(username);

                if (JwtTokenUtils.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(PlatformResult.failure(ResultCode.LOGIN_FAILED)));
            return;
        }

        chain.doFilter(request, response);
    }
}
