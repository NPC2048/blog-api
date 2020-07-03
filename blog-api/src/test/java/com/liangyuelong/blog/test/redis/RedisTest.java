package com.liangyuelong.blog.test.redis;

import com.liangyuelong.blog.BlogApiApplication;
import com.liangyuelong.blog.entity.BlogUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Resource;

/**
 * redis 测试
 *
 * @author yuelong.liang
 */
@SpringBootTest(classes = BlogApiApplication.class)
public class RedisTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() {
        RedisSerializer<?> redisSerializer = redisTemplate.getValueSerializer();
        System.out.println(redisSerializer);
        BlogUser user = new BlogUser();
        user.setEmail("npc24@qq.com");
        redisTemplate.opsForValue().set("abc", user);
    }


}
