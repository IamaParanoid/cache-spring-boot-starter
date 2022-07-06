package com.forest.cache.sign;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 获取缓存
 *
 * @author senlin
 * @date 2021/4/16
 * @since 5.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {
    /**
     * 缓存 value
     */
    String value();
    /**
     * 缓存 key
     */
    String key();
    /**
     * 缓存 时间
     */
    long time() default 100;

    /**
     * 缓存时间单位
     */
    TimeUnit TIME_UNIT() default TimeUnit.SECONDS;
}
