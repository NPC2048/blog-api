package com.liangyuelong.blog.entity.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yuelong.liang
 */
@Data
public class BaseEntity implements Serializable {

    protected long id;

    @TableField(fill = FieldFill.INSERT)
    protected Date createTime;

    @TableField(fill = FieldFill.INSERT, update = "NOW()")
    protected Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    protected byte state;

}
