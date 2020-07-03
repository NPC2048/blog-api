package com.liangyuelong.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liangyuelong.blog.entity.Blog;
import org.apache.ibatis.annotations.Select;

/**
 * @author yuelong.liang
 */
public interface BlogMapper extends BaseMapper<Blog> {

    /**
     * 查询所有内容
     */
    @Select("select * from blog_blog")
    void findAll();

}
