package com.forest.cache.util;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Objects;

public class RedisCacheUtil {
    public static EvaluationContext bindParam(Method method, Object[] args) {
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        //获取方法的参数名
        String[] params = discoverer.getParameterNames(method);
        //将参数名与参数值对应起来
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < Objects.requireNonNull(params).length; len++) {
            context.setVariable(params[len], args[len]);
        }
        return context;
    }

    public static String getCacheKey(String keys, Object[] args, Method method) {
        if (!keys.contains( "#")) {
            return keys;
        }
        ExpressionParser parser = new SpelExpressionParser();
        String[] array = keys.split("-");
        StringBuilder cacheKey = new StringBuilder();
        for (String s : array) {
            Expression expression = parser.parseExpression(s);
            EvaluationContext context = bindParam(method, args);
            Object key=expression.getValue(context);
            cacheKey.append(null==key ? "":key);
        }
        return cacheKey.toString();
    }
}
