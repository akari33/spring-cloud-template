package com.temp.config;


import com.temp.common.util.MyDateUtil;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Date;
import java.util.Properties;

public class CustomTableShardingAlgorithm implements StandardShardingAlgorithm<Date> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        //拿到创建时间
        Date createTime = preciseShardingValue.getValue();
        String columnName = preciseShardingValue.getColumnName();
        if (createTime == null) {
            throw new UnsupportedOperationException(columnName + ":列，分表精确分片值为NULL;");
        }
        //因为是按周分表，所以后辍是年份+周数
        //将日期转换成年份+周数的字符串
        String createTimeStr = MyDateUtil.getYearWeekStrByDate(createTime);
        //获取逻辑表名
        String tableName = preciseShardingValue.getLogicTableName();
        for (String each : collection) {
            if (each.startsWith(tableName)) {
                return tableName + createTimeStr;
            }
        }
        return null;
    }

    @Override
    public Collection<String> doSharding(Collection collection, RangeShardingValue rangeShardingValue) {
        return null;
    }

    @Override
    public Properties getProps() {
//        Properties properties = new Properties();
//        properties.setProperty("strategy", "standard");
//        return properties;
        return null;
    }

    @Override
    public String getType() {
        //自定义分表算法
//        return "CLASS_BASED";
        return "";
    }

    @Override
    public void init(Properties properties) {

    }
}
