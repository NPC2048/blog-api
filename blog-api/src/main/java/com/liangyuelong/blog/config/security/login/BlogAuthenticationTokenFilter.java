package com.liangyuelong.blog.config.security.login;

import com.liangyuelong.blog.common.constant.GlobalConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 判断 token 是否有效
 * 如果有效，则从 jwt 中获取认证主题存入 context
 *
 * @author yuelong.liang
 */
@Component
public class BlogAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(GlobalConstants.REQUEST_HEADER);
        if (StringUtils.isNotEmpty(token)) {
            // 如果 redis 存在该 key, 则说明已经验证过，直接从数据库获取权限信息
            String username = redisTemplate.opsForValue().get(token);
            if (StringUtils.isNotEmpty(username)) {
                UserDetails userDetail = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetail.getUsername(), userDetail.getPassword(), userDetail.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                        request));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
