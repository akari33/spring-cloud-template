package com.temp.common.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 定时任务相关配置
 */
@Configuration
@EnableScheduling
//配置文件读取是否启用此配置
@ConditionalOnProperty(prefix = "scheduling", name = "enabled", havingValue = "true")
public class SpringTaskConfig {

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 设置线程池大小
        taskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        return taskScheduler;
    }

}
