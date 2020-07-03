package com.liangyuelong.blog.common.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * 状态枚举类
 *
 * @author yuelong.liang
 */
public enum StateEnum {

    // 启用
    ENABLE((byte) 1),
    // 禁用
    DISABLE((byte) 0);

    @Getter
    @Setter
    private byte code;

    StateEnum(byte code) {
        this.code = code;
    }

}
