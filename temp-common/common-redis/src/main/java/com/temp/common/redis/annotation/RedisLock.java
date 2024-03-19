package com.temp.common.redis.annotation;

import java.lang.annotation.*;

/**
 * 描述：自定义分布式锁注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisLock {

    /**
     * 锁的key
     */
    String lockKey() default "";

    /**
     * 业务code
     */
    String businessCode() default "";

    /**
     * 获取锁的等待时间(毫秒)
     */
    int waitTime() default 0;

    /**
     * 释放的时间(毫秒)
     */
    int leaseTime() default -1;
}
