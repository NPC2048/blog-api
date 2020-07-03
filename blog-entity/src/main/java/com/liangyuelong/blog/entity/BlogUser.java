package com.liangyuelong.blog.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.liangyuelong.blog.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * @author yuelong.liang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("blog_user")
public class BlogUser extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    @Version
    @TableField(fill = FieldFill.INSERT, update = "%s+1")
    private int version;

    /**
     * token
     */
    @TableField(exist = false)
    private String token;

    /**
     * 用户所拥有的角色
     */
    @TableField(exist = false)
    private Set<Role> roles;

}
