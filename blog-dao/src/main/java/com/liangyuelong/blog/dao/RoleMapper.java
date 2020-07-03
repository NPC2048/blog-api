package com.liangyuelong.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liangyuelong.blog.entity.Role;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * @author yuelong.liang
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户 id 查询角色
     *
     * @param id 用户 id
     * @return 角色集合
     */
    @Select("select * from blog_role r LEFT JOIN blog_user_r" +
            "ole ur ON r.id = ur.role_id AND ur.user_id=#{id}")
    @Results({
            @Result(column = "role_id", property = "permissions",
                    javaType = Set.class, many = @Many(select = "com.liangyuelong.blog.dao.PermissionMapper.listByRoleId"))
    })
    List<Role> listByUserId(Long id);

    /**
     * 根据用户名称查询角色以及权限
     *
     * @param username 用户名
     * @return 角色集合
     */
    @Select("select * from blog_role r LEFT JOIN blog_user_role ur ON r.id = ur.role_id " +
            "LEFT JOIN blog_user u ON u.id = ur.user_id AND u.user_name = #{username}")
    @Results({
            @Result(property = "permissions", column = "role_id", javaType = Set.class,
                    many = @Many(select = "com.liangyuelong.blog.dao.PermissionMapper.listByRoleId"))
    })
    Set<Role> listByUserName(String username);

}
