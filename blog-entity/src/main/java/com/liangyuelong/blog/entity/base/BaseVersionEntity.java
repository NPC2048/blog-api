package com.liangyuelong.blog.entity.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 有乐观锁的基础实体类
 *
 * @author yuelong.liang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseVersionEntity extends BaseEntity {

    @Version
    @TableField(fill = FieldFill.INSERT, update = "%s+1")
    private int version;

}
