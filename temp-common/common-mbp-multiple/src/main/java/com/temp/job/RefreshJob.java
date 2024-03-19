package com.temp.job;

import com.temp.common.util.MyDateUtil;

import java.time.LocalDateTime;

/**
 * 刷新分表数据库定时任务
 */
//@Component
public class RefreshJob {

//    @Resource
//    private CreateTableMapper createTableMapper;


    /**
     * 定时任务(每天晚11点查询第二天是否是下一周，创建表）
     */
//    @Scheduled(cron = "0 0 23 * * ?")
    public void createWcTableJobHandler() throws Exception {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        //加一天
        LocalDateTime tomorrow = now.plusDays(1);
        //计算出明天是第几周
        String yearWeekStrByLocalDateTime = MyDateUtil.getYearWeekStrByLocalDateTime(tomorrow);
        String tableName = "test" + "_" + yearWeekStrByLocalDateTime;
        //存在不会插入
//        createTableMapper.createTableByName(tableName);
    }
}
