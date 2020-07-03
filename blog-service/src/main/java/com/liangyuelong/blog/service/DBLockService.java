package com.liangyuelong.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liangyuelong.blog.entity.DBLock;

public interface DBLockService extends IService<DBLock> {

    /**
     * 根据 key 查找 lock
     *
     * @param key key
     * @return entity
     */
    DBLock getByKey(String key);

    /**
     * 根据 key 从数据库删除 lock
     *
     * @param key key
     * @return boolean
     */
    boolean removeByKey(String key);

}
