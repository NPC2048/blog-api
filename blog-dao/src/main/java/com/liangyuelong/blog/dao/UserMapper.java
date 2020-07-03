package com.liangyuelong.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liangyuelong.blog.entity.BlogUser;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户
 *
 * @author yuelong.liang
 */
public interface UserMapper extends BaseMapper<BlogUser> {

    /**
     * 根据用户名查询用户
     * - 与用户所拥有胡角色与权限
     *
     * @param username 用户名
     * @return User
     */
    @Select("select * from blog_user u WHERE u.username = #{username} AND u.state=1")
    @Results(@Result(column = "user_name", property = "roles", javaType = Set.class,
            many = @Many(select = "com.liangyuelong.blog.dao.RoleMapper.listByUserName")))
    BlogUser selectRoleAndPermissionByUsername(String username);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return User
     */
    @Select("select * from blog_user u WHERE u.username = #{username} AND u.state=1")
    BlogUser selectByUsername(String username);

    /**
     * 根据邮箱查询用户
     *
     * @param mail 邮箱
     * @return User
     */
    @Select("select * from blog_user WHERE mail = #{mail} AND state=1")
    BlogUser selectByMail(String mail);


    /**
     * 删除时加上乐观锁, 避免其他方法更新后被此方法直接删除
     *
     * @param id id
     * @return int
     */
    @Delete("delete from blog_user where id=#{id} and version=#{version}")
    @Override
    int deleteById(Serializable id);
}
