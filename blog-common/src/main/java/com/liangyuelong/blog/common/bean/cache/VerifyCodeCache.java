package com.liangyuelong.blog.common.bean.cache;

import lombok.Data;

/**
 * 验证码缓存 model
 *
 * @author yuelong.liang
 */
@Data
public class VerifyCodeCache {

    private String code;

    private Integer count = 0;

}
