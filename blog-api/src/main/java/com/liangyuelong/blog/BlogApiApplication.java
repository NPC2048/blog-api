package com.liangyuelong.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * api 服务程序入口
 *
 * @author yuelong.liang
 */
@EnableConfigurationProperties
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableCaching
@EnableScheduling
@EnableAsync
@MapperScan("com.liangyuelong.blog.dao")
@SpringBootApplication
public class BlogApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApiApplication.class, args);
    }
}
