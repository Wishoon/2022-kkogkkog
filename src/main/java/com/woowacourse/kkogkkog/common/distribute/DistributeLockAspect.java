package com.woowacourse.kkogkkog.common.distribute;

import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@RequiredArgsConstructor
@Component
public class DistributeLockAspect {

    private static final String REDISSON_KEY_PREFIX = "RLOCK_";

    private final RedissonClient redissonClient;
    private final AopRequiresNewTransaction aopRequiresNewTransaction;

    @Around("@annotation(com.woowacourse.kkogkkog.common.distribute.DistributeLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributeLock lock = method.getAnnotation(DistributeLock.class);

        String key = REDISSON_KEY_PREFIX + getDynamicValueParser(
            signature.getParameterNames(), joinPoint.getArgs(), lock.key());
        RLock rLock = redissonClient.getLock(key);

        try {
            boolean available = rLock.tryLock(lock.waitTime(), lock.leaseTime(), lock.timeUnit());
            if (!available) {
                return false;
            }

            log.info("###### Get Lock Success {} ######", key);
            return aopRequiresNewTransaction.proceed(joinPoint);
        } catch (final Exception e) {
            Thread.currentThread().interrupt();
            throw new InterruptedException();
        } finally {
            log.info("###### UnLocked Success ######");
            rLock.unlock();
        }
    }

    private Object getDynamicValueParser(final String[] parameterNames, final Object[] args, final String key) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }

        return parser.parseExpression(key).getValue(context, Object.class);
    }
}
