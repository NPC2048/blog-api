package com.liangyuelong.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangyuelong.blog.common.constant.CacheKeyConstants;
import com.liangyuelong.blog.dao.UserMapper;
import com.liangyuelong.blog.entity.BlogUser;
import com.liangyuelong.blog.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 用户 Service 实现
 *
 * @author yuelong.liang
 */
@Service("userService")
@CacheConfig(cacheNames = CacheKeyConstants.DEFAULT_CACHE_NAME)
public class UserServiceImpl extends ServiceImpl<UserMapper, BlogUser> implements UserService {

    @Override
    @Cacheable(unless = "#result == null")
    public BlogUser selectByUsername(String username) {
        return baseMapper.selectByUsername(username);
    }

    @Override
    @CachePut
    public boolean updateById(BlogUser blogUser) {
        return super.updateById(blogUser);
    }

    @Override
    @CacheEvict
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public BlogUser selectByMail(String mail) {
        return baseMapper.selectByMail(mail);
    }

}
