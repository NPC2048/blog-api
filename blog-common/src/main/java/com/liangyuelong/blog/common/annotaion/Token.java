package com.liangyuelong.blog.common.annotaion;

import java.lang.annotation.*;

/**
 * 操作资源前先需要获取 token
 *
 * @author yuelong.liang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Token {



}
