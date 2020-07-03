package com.liangyuelong.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangyuelong.blog.dao.RoleMapper;
import com.liangyuelong.blog.entity.Role;
import com.liangyuelong.blog.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author yuelong.liang
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
