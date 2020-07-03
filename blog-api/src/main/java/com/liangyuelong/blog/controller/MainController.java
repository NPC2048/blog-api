package com.liangyuelong.blog.controller;

import com.liangyuelong.blog.common.Result;
import com.liangyuelong.blog.service.BlogService;
import com.liangyuelong.blog.service.UserService;
import com.liangyuelong.blog.utils.JsonUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author yuelong.liang
 */
@RestController
public class MainController {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserService userService;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private BlogService blogService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/users")
    public Mono<Result> users() {
        System.out.println(JsonUtils.toJson(Result.success(userService.list())));
        return Mono.just(Result.success(userService.list()));
    }

}
