package com.liangyuelong.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liangyuelong.blog.entity.BlogUser;

/**
 * 用户 Service
 *
 * @author yuelong.liang
 */
public interface UserService extends IService<BlogUser> {

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return User
     */
    BlogUser selectByUsername(String username);

    /**
     * 根据邮箱查找用户
     *
     * @param mail 邮箱
     * @return User
     */
    BlogUser selectByMail(String mail);

}
