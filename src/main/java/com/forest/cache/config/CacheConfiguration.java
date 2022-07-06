package com.forest.cache.config;

import com.forest.cache.compoent.CacheAspect;
import com.forest.cache.compoent.CacheEvictAspect;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author senlin
 * @date 2021/5/7
 */
@Configuration
@AllArgsConstructor
public class CacheConfiguration {
    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate redisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @DependsOn("redisTemplate")
    public CacheAspect cacheAspect(StringRedisTemplate redisTemplate) {
        return new CacheAspect(redisTemplate);
    }

    @Bean
    @DependsOn("redisTemplate")
    public CacheEvictAspect cacheEvictAspect(StringRedisTemplate redisTemplate) {
        return new CacheEvictAspect(redisTemplate);
    }

}
