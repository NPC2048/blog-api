package com.liangyuelong.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 公共配置
 *
 * @author yuelong.liang
 */
@Configuration
public class CommonConfig {

    @Bean
    public BufferedImageHttpMessageConverter addConverter() {
        return new BufferedImageHttpMessageConverter();
    }

    /**
     * 通用线程池配置
     * @return ThreadPoolTaskExecutor
     */
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        // 核心线程数
        pool.setCorePoolSize(1);
        // 最大线程数
        pool.setMaxPoolSize(1);
        // 线程队列大小
        pool.setQueueCapacity(1);
        pool.setThreadNamePrefix("pool-");
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        pool.initialize();
        return pool;
    }

}
