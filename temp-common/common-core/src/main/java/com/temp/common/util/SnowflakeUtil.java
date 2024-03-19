package com.temp.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 雪花算法工具类
 *
 * @Author Administrator
 * @Date 2023/8/4 11:07
 **/
public class SnowflakeUtil {

    private static Snowflake snowflake = IdUtil.getSnowflake();


    /**
     * 生成long 类型的ID
     *
     * @return
     */
    public static Long getId() {
        return snowflake.nextId();
    }

    /**
     * 生成String 类型的ID
     *
     * @return
     */
    public static String getIdStr() {
        return snowflake.nextIdStr();
    }

}
