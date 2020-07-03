package com.liangyuelong.blog.common.form;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liangyuelong.blog.entity.Blog;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 博客分页查询表单
 *
 * @author yuelong.liang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogPageForm extends Page<Blog> {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

}
