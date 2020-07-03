package com.liangyuelong.blog.common.annotaion;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 给任务起名, 项目启动后加载这些类到内存
 * 配合 spring 一起用
 * 如果名称为空, 则获取 spring 中的 bean 名
 * @author yuelong.liang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BcmTask {

    @AliasFor("name")
    String value() default "";

    /**
     * 任务的名称
     *
     * @return String
     */
    @AliasFor("value")
    String name() default "";

    /**
     * 任务描述
     *
     * @return String
     */
    String describe() default "";

    /**
     * 执行的方法，可为空
     * @return String[]
     */
    Method[] methods() default {};

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface Method {

        @AliasFor("name")
        String value() default "";

        /**
         * 方法名称
         *
         * @return String
         */
        @AliasFor("value")
        String name() default "";

        /**
         * 方法作用描述
         * @return String
         */
        String describe() default "";

    }

}
