package com.liangyuelong.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liangyuelong.blog.common.Result;
import com.liangyuelong.blog.common.form.BlogPageForm;
import com.liangyuelong.blog.entity.Blog;
import com.liangyuelong.blog.service.BlogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 博客 controller
 *
 * @author yuelong.liang
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private BlogService blogService;

    /**
     * 分页查询博客
     *
     * @param form 表单
     * @return blog
     */
    @RequestMapping("/list")
    public Result list(BlogPageForm form) {
        QueryWrapper<Blog> wrapper = Wrappers.emptyWrapper();
        return Result.success(blogService.page(form, wrapper));
    }

    @RequestMapping("/search")
    public Result search(BlogPageForm form) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(form.getTitle())) {
            wrapper.like("title", form.getTitle());
        }
        if (StringUtils.isNotBlank(form.getContent())) {
            wrapper.like("content", form.getContent());
        }
        return Result.success(blogService.page(form, wrapper));
    }

}
