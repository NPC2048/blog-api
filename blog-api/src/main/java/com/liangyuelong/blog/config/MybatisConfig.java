package com.liangyuelong.blog.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.Date;

/**
 * mybatis 配置
 *
 * @author yuelong.liang
 */
@Configuration
public class MybatisConfig implements MetaObjectHandler {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        this.setFieldValByName("createTime", now, metaObject);
        this.setFieldValByName("updateTime", now, metaObject);
        this.setFieldValByName("state", (byte) 1, metaObject);
        // 密码
    }

    @Override
    public void updateFill(MetaObject metaObject) {
    }

    /**
     * 开启乐观锁插件
     *
     * @return OptimisticLockerInterceptor
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
