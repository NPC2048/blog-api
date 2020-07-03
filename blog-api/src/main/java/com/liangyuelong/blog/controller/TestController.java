package com.liangyuelong.blog.controller;

import com.liangyuelong.blog.common.Result;
import com.liangyuelong.blog.dao.RoleMapper;
import com.liangyuelong.blog.dao.UserMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试 controller
 *
 * @author yuelong.liang
 */
@RestController
public class TestController {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/role")
    public Result role() {
        return Result.success(roleMapper.listByUserId(1L));
    }

    @RequestMapping("/roleByName")
    public Result roleByName() {
        return Result.success(roleMapper.listByUserName("npc2048"));
    }

    @RequestMapping("/userByName")
    public Result userByName() {
        return Result.success(userMapper.selectByUsername("npc2048"));
    }

}
