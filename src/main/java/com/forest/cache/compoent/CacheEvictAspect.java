package com.forest.cache.compoent;

import com.forest.cache.sign.RedisCacheEvict;
import com.forest.cache.util.RedisCacheUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static com.forest.cache.constant.CacheConstant.CACHE;

/**
 * @author senlin
 * @date 2020/3/10
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class CacheEvictAspect {
    private final StringRedisTemplate redisTemplate;

    @Pointcut("@annotation(com.forest.cache.sign.RedisCacheEvict)")
    public void redisCacheEvict() {
    }

    @Before("redisCacheEvict()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    @AfterReturning(value = "redisCacheEvict()", returning = "ret")
    public void doAfterReturning(Object ret) {
    }

    @Around("redisCacheEvict()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        RedisCacheEvict annotation = method.getAnnotation(RedisCacheEvict.class);
        String value = annotation.value() + ":";
        String keys = annotation.key();
        Object[] args = joinPoint.getArgs();
        String key = RedisCacheUtil.getCacheKey(keys, args, method);
        String cacheKey = CACHE + value + key;
        redisTemplate.delete(cacheKey);
        return joinPoint.proceed();
    }


}
