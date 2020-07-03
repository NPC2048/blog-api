package com.liangyuelong.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liangyuelong.blog.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 博客内容实体类
 * @author yuelong.liang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("blog_blog")
public class Blog extends BaseEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 描述
     */
    private String description;

    /**
     * 图片 id
     */
    private Long mainImageId;

//    private FileInfo mainImage;

}
