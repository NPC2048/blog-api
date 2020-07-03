package com.liangyuelong.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liangyuelong.blog.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * 角色
 *
 * @author yuelong.liang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("blog_role")
public class Role extends BaseEntity {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色所拥有的权限
     */
    private Set<Permission> permissions;

}
