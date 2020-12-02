package com.quaero.quaerosmartplatform.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quaero.quaerosmartplatform.domain.enumeration.ResultCode;
import com.quaero.quaerosmartplatform.domain.filter.JWTAuthorizationFilter;
import com.quaero.quaerosmartplatform.domain.result.PlatformResult;
import com.quaero.quaerosmartplatform.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.PrintWriter;

/**
 * spring-security配置
 */
@Configuration
@EnableWebSecurity
//开启 Spring Security 方法级安全注解
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .httpBasic()
                //未登录
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    PrintWriter out = response.getWriter();
                    out.write(objectMapper.writeValueAsString(PlatformResult.failure(ResultCode.USER_NOT_LOGGED_IN)));
                    out.flush();
                    out.close();
                })
                .and()
                .authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/logout").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                //.antMatchers("/api/materialTransfer/stockInquire").hasRole("AAA")
                .anyRequest().authenticated() //必须授权才能范围
                .and()
                .formLogin()
                .loginPage("/")
                .loginPage("/login")   //登录请求页
                .loginProcessingUrl("/api/login")  //登录POST请求路径
                .successHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpStatus.OK.value());
                    PrintWriter out = response.getWriter();
                    try {
                        // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
                        UserDetails details = (UserDetails) authentication.getPrincipal();
                        if (details == null) {
                            out.write(objectMapper.writeValueAsString(PlatformResult.failure(ResultCode.LOGIN_FAILED)));
                        } else {
                            String token = JwtTokenUtils.TOKEN_PREFIX + JwtTokenUtils.createToken(details);
                            // 重定向
                            response.setHeader(JwtTokenUtils.TOKEN_HEADER, token);
                            out.write(objectMapper.writeValueAsString(PlatformResult.success(token)));
                        }
                    } catch (Exception e) {
                        out.write(objectMapper.writeValueAsString(PlatformResult.failure(ResultCode.LOGIN_FAILED)));
                    } finally {
                        out.flush();
                        out.close();
                    }
                })
                //登录失败
                .failureHandler((request, response, ex) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    PrintWriter out = response.getWriter();
                    PlatformResult platformResult = new PlatformResult();
                    platformResult.setCode(401);
                    if (ex instanceof UsernameNotFoundException || ex instanceof BadCredentialsException) {
                        platformResult.setMsg("账号不存在或密码错误");
                        //out.write(objectMapper.writeValueAsString(PlatformResult.failure(ResultCode.USER_LOGIN_ERROR)));
                    } else if (ex instanceof DisabledException) {
                        platformResult.setMsg("账号已被禁用，请联系管理员!");
                        //out.write(objectMapper.writeValueAsString(PlatformResult.failure(ResultCode.USER_ACCOUNT_FORBIDDEN)));
                    } else if (ex instanceof LockedException) {
                        platformResult.setMsg("账户被锁定，请联系管理员!");
                    } else if (ex instanceof CredentialsExpiredException) {
                        platformResult.setMsg("密码过期，请联系管理员!");
                    } else if (ex instanceof AccountExpiredException) {
                        platformResult.setMsg("账户过期，请联系管理员!");
                    } else {
                        platformResult.setMsg("登录失败!");
                        //out.write(objectMapper.writeValueAsString(PlatformResult.failure(ResultCode.LOGIN_FAILED)));
                    }
                    out.write(objectMapper.writeValueAsString(platformResult));
                    out.flush();
                    out.close();
                })
                .and()
                .logout()
                .logoutUrl("/api/logout")
                //退出成功，返回json
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpStatus.OK.value());
                    PrintWriter out = response.getWriter();
                    out.write(objectMapper.writeValueAsString(PlatformResult.success("退出成功")));
                    out.flush();
                    out.close();
                })
                .permitAll();
        // 禁用缓存
        http.headers().cacheControl();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  //基于token，不需要sessio
        //开启跨域访问
        http.cors().configurationSource(corsConfigurationSource());
        //开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
        http.csrf().disable();
        // 添加JWT filter
        http.addFilterBefore(new JWTAuthorizationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                //没有权限，返回json
                .accessDeniedHandler((request, response, ex) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    PrintWriter out = response.getWriter();
                    out.write(objectMapper.writeValueAsString(PlatformResult.failure(ResultCode.PERMISSION_NO_ACCESS)));
                    out.flush();
                    out.close();
                });
    }

    //跨域配置
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");    //同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        corsConfiguration.addAllowedHeader("*");//header，允许哪些header，本案中使用的是token，此处可将*替换为token；
        corsConfiguration.addAllowedMethod("*");    //允许的请求方法，PSOT、GET等
        ((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**", corsConfiguration); //配置允许跨域访问的url
        return source;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/swagger-ui.html")
                .antMatchers("/webjars/**")
                .antMatchers("/v2/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/css/**")
                .antMatchers("/images/**")
                .antMatchers("/js/**")
                .antMatchers("/layui/**")
                .antMatchers("/druid/**");
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //对默认的UserDetailsService进行覆盖
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
