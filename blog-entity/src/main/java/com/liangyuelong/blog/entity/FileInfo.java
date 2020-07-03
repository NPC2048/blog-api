package com.liangyuelong.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liangyuelong.blog.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件信息实体类
 *
 * @author yuelong.liang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("blog_file_info")
public class FileInfo extends BaseEntity {

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 拓展名
     */
    private String ext;

    /**
     * 文件类型
     */
    private String contentType;

    /**
     * md5
     */
    private String md5;

    /**
     * 文件大小
     */
    private Integer size;

}
