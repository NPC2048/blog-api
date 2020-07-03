package com.liangyuelong.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liangyuelong.blog.entity.base.BaseVersionEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基于数据库的分布式锁
 *
 * @author yuelong.liang
 */
@TableName("dblock")
@EqualsAndHashCode(callSuper = true)
@Data
public class DBLock extends BaseVersionEntity {

    /**
     * 唯一
     */
    @TableField("dbkey")
    private String dbkey;

    /**
     * 有效时间（秒）
     */
    private int expire;

}
