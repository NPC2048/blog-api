package com.liangyuelong.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangyuelong.blog.dao.PermissionMapper;
import com.liangyuelong.blog.entity.Permission;
import com.liangyuelong.blog.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * @author yuelong.liang
 */
@Service("permissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
}
