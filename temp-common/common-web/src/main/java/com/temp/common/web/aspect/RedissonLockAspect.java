package com.temp.common.web.aspect;

import com.temp.common.exception.ApiException;
import com.temp.common.redis.annotation.RedisLock;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁切面类
 */
@SuppressWarnings("ALL")
@Slf4j
@Aspect
@Order(1)
@Component
public class RedissonLockAspect {

    @Resource
    private RedissonClient redissonClient;
    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    private final DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();


    @Around("@annotation(com.temp.common.redis.annotation.RedisLock)")
    public Object interceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedisLock lock = method.getAnnotation(RedisLock.class);
        String lockKey = getLockKeyName(lock, joinPoint);
        RLock rLock = redissonClient.getLock(lockKey);
        boolean flag = getLock(rLock, lock);
        try {
            if (flag) {
                return joinPoint.proceed();
            } else {
                throw new ApiException("network busy, please try again later");
            }
        } catch (Exception e) {
            if (e instanceof ApiException) {
                throw e;
            }
            throw new ApiException("network busy, please try again later", e);
        } finally {
            if (flag) {
                if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                    rLock.unlock();
                }
            }
        }
    }

    private String getLockKeyName(RedisLock lock, ProceedingJoinPoint joinPoint) {
        String lockKeyPrefix = lock.lockKey();
        String businessCode = lock.businessCode();
        if (Objects.nonNull(lockKeyPrefix) && Objects.nonNull(businessCode)) {
            return lockKeyPrefix + ":" + this.evaluateExpression(businessCode, joinPoint);
        } else if (Objects.nonNull(lockKeyPrefix)) {
            return this.evaluateExpression(lockKeyPrefix, joinPoint);
        } else if (Objects.nonNull(businessCode)) {
            return this.evaluateExpression(businessCode, joinPoint);
        } else {
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            return className + "." + methodName;
        }
    }

    private boolean getLock(RLock rLock, RedisLock lock) throws InterruptedException {
        int waitTime = lock.waitTime();
        int leaseTime = lock.leaseTime();
        boolean flag = false;
        if (waitTime > 0 && leaseTime > 0) {
            flag = rLock.tryLock(waitTime, leaseTime, TimeUnit.MICROSECONDS);
        } else if (leaseTime > 0) {
            flag = rLock.tryLock(0, leaseTime, TimeUnit.MILLISECONDS);
        } else if (waitTime > 0) {
            flag = rLock.tryLock(waitTime, TimeUnit.MILLISECONDS);
        } else {
            flag = rLock.tryLock();
        }
        return flag;
    }

    /**
     * 解析el表达式
     *
     * @param expression
     * @param point
     * @return
     */
    private String evaluateExpression(String expression, ProceedingJoinPoint point) {
        // 获取目标对象
        Object target = point.getTarget();
        // 获取方法参数
        Object[] args = point.getArgs();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();

        EvaluationContext context = new MethodBasedEvaluationContext(target, method, args, parameterNameDiscoverer);
        Expression exp = spelExpressionParser.parseExpression(expression);
        return exp.getValue(context, String.class);
    }
}
