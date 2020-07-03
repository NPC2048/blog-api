package com.liangyuelong.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liangyuelong.blog.entity.DBLock;
import org.apache.ibatis.annotations.Select;

/**
 * mapper
 *
 * @author yuelong.liang
 */
public interface DBLockMapper extends BaseMapper<DBLock> {

    /**
     * 根据 key 查找 lock
     *
     * @param key key
     * @return entity
     */
    @Select("select * from dblock where key=#{key}")
    DBLock selectByKey(String key);

}
