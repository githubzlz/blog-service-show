package com.zlz.blog.common.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-08-12 14:11
 * @description 时间工具
 */
public class DateUtil {

    /**
     * 获取当天的0时
     *
     * @return
     */
    public static Date getZeroDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}
