package com.temp.common.redis.util;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * 布隆过滤器工具类
 *
 * @Author Administrator
 * @Date 2023/9/25 14:54
 **/
@Component
public class BloomFilterUtil {

    /**
     * 预期插入数量
     */
    private static final long EXPECTEDINSERTIONS = 10000000L;
    /**
     * 误判率
     */
    private static final double FALSEPROBABILITY = 0.01;
    @Resource
    private RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        // 启动项目时初始化bloomFilter
//        create(CommonConstant.DEVICE_ID_BLOOM_FILTER,
//                EXPECTEDINSERTIONS, FALSEPROBABILITY);
//
//        create(CommonConstant.NEW_USER_BLOOM_FILTER,
//                EXPECTEDINSERTIONS, FALSEPROBABILITY);
    }


    /**
     * 创建布隆过滤器
     *
     * @param filterName         过滤器名称
     * @param expectedInsertions 预测插入数量
     * @param falsePositiveRate  误判率
     */
    public <T> RBloomFilter<T> create(String filterName, long expectedInsertions, double falsePositiveRate) {
        RBloomFilter<T> bloomFilter = redissonClient.getBloomFilter(filterName);
        bloomFilter.tryInit(expectedInsertions, falsePositiveRate);
        return bloomFilter;
    }

    /**
     * 获取布隆过滤器
     */
    public <T> RBloomFilter<T> get(String filterName) {
        return redissonClient.getBloomFilter(filterName);
    }
}
