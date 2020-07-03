package com.liangyuelong.blog.config;

import com.liangyuelong.blog.common.constant.CacheKeyConstants;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * redis 配置
 *
 * @author yuelong.liang
 */
@Configuration
public class RedisConfig {

    @Bean
    public GenericJackson2JsonRedisSerializer jsonRedisSerializer() {
        // objectMapper
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(RedisSerializer<?> jackson2JsonRedisSerializer) {
        return RedisCacheConfiguration.defaultCacheConfig()
                // 默认 1 分钟后失效
                .entryTtl(Duration.ofMinutes(1))
                .disableCachingNullValues()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
    }


    @Bean
    public CacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory, RedisCacheConfiguration redisCacheConfiguration) {
        // 初始化缓存空间 set 集合
        Set<String> cacheNames = new HashSet<>();
        cacheNames.add(CacheKeyConstants.DEFAULT_CACHE_NAME);
        // 配置对应配置
        Map<String, RedisCacheConfiguration> cacheConfigMap = new HashMap<>(2);
        cacheConfigMap.put(CacheKeyConstants.DEFAULT_CACHE_NAME, redisCacheConfiguration);
        // 配置缓存管理器
        return RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(cacheConfigMap).build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory lettuceConnectionFactory,
                                                       RedisSerializer<?> jsonRedisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setDefaultSerializer(jsonRedisSerializer);
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        redisTemplate.setEnableDefaultSerializer(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName()).append(".");
            sb.append(method.getName()).append(".");
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

}
