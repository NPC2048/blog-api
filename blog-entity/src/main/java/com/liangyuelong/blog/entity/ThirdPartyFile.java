package com.liangyuelong.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liangyuelong.blog.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 第三方文件信息
 * 存储在云服务的文件的基本信息
 *
 * @author yuelong.liang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("blog_third_party_file")
public class ThirdPartyFile extends BaseEntity {

    /**
     * 文件名
     */
    private String name;

    /**
     * 源文件名
     */
    private String originName;

    /**
     * 文件类型
     */
    private String contentType;

    /**
     * 文件拓展名
     */
    private String ext;

    /**
     * MD5 值
     */
    private String md5;

}
