package com.liangyuelong.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.liangyuelong.blog.dao.DBLockMapper;
import com.liangyuelong.blog.entity.DBLock;
import com.liangyuelong.blog.service.DBLockService;
import org.springframework.stereotype.Service;

@Service("dbLockService")
public class DBLockServiceImpl extends ServiceImpl<DBLockMapper, DBLock> implements DBLockService {

    @Override
    public DBLock getByKey(String key) {
        return baseMapper.selectOne(new QueryWrapper<DBLock>().eq("dbkey", key));
    }

    @Override
    public boolean removeByKey(String key) {
        return SqlHelper.retBool(baseMapper.delete(new QueryWrapper<DBLock>().eq("dbkey", key)));
    }

}
