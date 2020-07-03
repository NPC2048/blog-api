package com.liangyuelong.blog.common.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义 http 状态码
 *
 * @author yuelong.liang
 */
public enum BlogHttpStatusEnum {

    // 未登录
    NO_LOGIN(1001, "未登录，请先登录");

    @Getter
    @Setter
    private int value;

    @Getter
    @Setter
    private String reasonPhrase;

    BlogHttpStatusEnum(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

}
