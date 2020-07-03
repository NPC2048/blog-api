package com.liangyuelong.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liangyuelong.blog.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限
 *
 * @author yuelong.liang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("blog_permission")
public class Permission extends BaseEntity {

    /**
     * 权限名称
     */
    private String name;

    private String url;

}
