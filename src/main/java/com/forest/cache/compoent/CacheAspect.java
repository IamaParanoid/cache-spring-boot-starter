package com.forest.cache.compoent;

import com.forest.cache.constant.CacheConstant;
import com.forest.cache.sign.RedisCache;
import com.forest.cache.util.JacksonUtil;
import com.forest.cache.util.RedisCacheUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author senlin
 * @date 2020/3/10
 */
@Aspect
@Component
@AllArgsConstructor
public class CacheAspect {
    private final StringRedisTemplate redisTemplate;

    @Pointcut("@annotation(com.forest.cache.sign.RedisCache)")
    public void redisCache() {
    }

    @Before("redisCache()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    @AfterReturning(value = "redisCache()", returning = "ret")
    public void doAfterReturning(Object ret) throws Throwable {
    }

    @Around("redisCache()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Long a = System.currentTimeMillis();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Class<?> returnType = methodSignature.getReturnType();
        RedisCache annotation = method.getAnnotation(RedisCache.class);
        String value = annotation.value() + ":";
        String keys = annotation.key();
        Object[] args = joinPoint.getArgs();
        String key = RedisCacheUtil.getCacheKey(keys, args, method);
        String cacheKey = CacheConstant.CACHE + value + key;
        long cacheTime = annotation.time();
        String responseStr = redisTemplate.opsForValue().get(cacheKey);
        Object result;
        if (StringUtils.isBlank(responseStr)) {
            //  执行后续方法
            result = joinPoint.proceed();
            redisTemplate.opsForValue().set(cacheKey, JacksonUtil.toJsonString(result), cacheTime, annotation.TIME_UNIT());
        } else {
            result = JacksonUtil.getObjectFromJson(responseStr, returnType);
        }
        return result;
    }
}
