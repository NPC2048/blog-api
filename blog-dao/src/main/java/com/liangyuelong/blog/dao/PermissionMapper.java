package com.liangyuelong.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liangyuelong.blog.entity.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yuelong.liang
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据角色 id 查询权限
     *
     * @param id 角色 id
     * @return 权限集合
     */
    @Select("select * from blog_permission p LEFT JOIN blog_role_permission rp ON p.id = rp.permission_id AND rp.role_id=#{id}")
    List<Permission> listByRoleId(Long id);

}
