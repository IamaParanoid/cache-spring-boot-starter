package com.forest.cache.sign;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 删除缓存
 *
 * @author senlin
 * @date 2021/4/16
 * @since 5.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCacheEvict {
    /**
     * 缓存 value
     */
    String value();

    /**
     * 缓存 key
     */
    String key();
}
