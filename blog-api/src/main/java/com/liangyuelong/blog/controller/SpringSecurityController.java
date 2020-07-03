package com.liangyuelong.blog.controller;

import com.liangyuelong.blog.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Security 测试 Controller
 *
 * @author yuelong.liang
 */
@Slf4j
@RestController
@RequestMapping("/se")
public class SpringSecurityController {

    @RequestMapping("/index")
    public Result index() {
        return Result.success("当前登录用户:" + SecurityContextHolder.getContext().getAuthentication());
    }

    @RequestMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result hasAdmin() {
        return Result.success("拥有 admin 角色");
    }

    @RequestMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Result hasUser() {
        return Result.success("拥有 user 角色");
    }


}
