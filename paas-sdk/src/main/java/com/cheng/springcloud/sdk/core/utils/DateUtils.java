package com.cheng.springcloud.sdk.core.utils;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author haizhu_cheng@163.com
 * @version Revision 1.0.0
 * @版权 版权所有 (c) 2018
 * @创建日期 2019年4月11日
 * @功能说明 日期工具类
 */
public class DateUtils {
    public static final long JVM_START_TIME = ManagementFactory.getRuntimeMXBean().getStartTime();

    /**
     * @return 获取档期日期，以yyyy-MM-dd  HH:mm:ss.SSS格式输出
     */
    public static String getCurrentTimeStamp() {
        return transferLongToDate(String.valueOf(System.currentTimeMillis()));
    }

    /**
     * 转化long值的日期为yyyy-MM-dd  HH:mm:ss.SSS格式的日期
     *
     * @param millSec 日期long值
     * @return 日期，以yyyy-MM-dd  HH:mm:ss.SSS格式输出
     */
    public static String transferLongToDate(String millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = new Date(Long.parseLong(millSec));
        return sdf.format(date);
    }

    /**
     * 获取某个时间加上一定毫秒时长后的新的时间
     *
     * @param start       开始时间
     * @param plusMillSec 增加的时长
     * @return 时间字符串
     */
    public static String plusMillSecToDate(Date start, long plusMillSec) {
        long millSec = start.getTime() + plusMillSec;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = new Date(millSec);
        return sdf.format(date);
    }
}
