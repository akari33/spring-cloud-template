package com.temp.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;


@Configuration
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //禁止全表更新删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        //如果配置多个插件,切记分页最后添加
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public DefaultIdentifierGenerator defaultIdentifierGenerator() {
        Random workerIdRandom = new Random();
        Random datacenterIdRandom = new Random();
        long workerId = workerIdRandom.nextInt(31);
        long datacenterId = datacenterIdRandom.nextInt(31);
        return new DefaultIdentifierGenerator(workerId, datacenterId);
    }
}


