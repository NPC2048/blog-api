package com.liangyuelong.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangyuelong.blog.dao.BlogMapper;
import com.liangyuelong.blog.entity.Blog;
import com.liangyuelong.blog.service.BlogService;
import org.springframework.stereotype.Service;

/**
 * 博客 Service 实现
 *
 * @author yuelong.liang
 */
@Service("blogService")
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    void test() {
        this.baseMapper.findAll();
    }
}
