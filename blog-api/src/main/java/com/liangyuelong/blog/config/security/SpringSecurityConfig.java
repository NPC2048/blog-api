package com.liangyuelong.blog.config.security;

import com.liangyuelong.blog.config.security.login.BlogAuthenticationTokenFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;

/**
 * Spring Security 配置
 *
 * @author yuelong.liang
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Slf4j
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private BlogAuthenticationTokenFilter blogAuthenticationTokenFilter;

    /**
     * 未登录
     */
    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 无权限访问
     */
    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用 csrf
        http.csrf().disable()
                // 启用跨域访问
                .cors().and()
                // 认证失败处理
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint).and().authorizeRequests()
                .antMatchers("/user/**").hasAnyRole("ROLE_USER", "USER")
                .antMatchers("/manage/**").hasAnyRole("ROLE_MANAGER", "MANAGER")
                .antMatchers(HttpMethod.OPTIONS, "/**").anonymous()
                // 其余的 url 都不需要授权访问
                .anyRequest().permitAll()
                .and()
                .logout()
                .permitAll();
        // 无权限访问处理
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        // 禁用 session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).disable();
        http.addFilterAt(blogAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 未登录
     * 返回 401
     *
     * @return AuthenticationEntryPoint
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
                response.setStatus(BlogHttpStatusConstant.ERROR_USERNAME_PASSWORD);
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        };
    }

    /**
     * 无权限访问
     * 返回 403
     *
     * @return AccessDeniedHandler
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, e) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return ((request, response, authentication) -> {
            System.out.println(authentication);
            System.out.println("退出登录");
        });
    }

}
