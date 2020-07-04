package com.zyfgoup.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 将日期转成cron表达式工具类
 * Created by ozc on 2017/12/8.
 *
 * @author ozc
 * @version 1.0
 */
public class Date2Cron {

    /***
     *
     * @param date
     * @param dateFormat :
     * @return
     */
    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /***
     * convert Date to cron ,eg.  "0 07 10 15 1 ? 2016"
     * @param date  : 时间点
     * @return
     */
    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }

    public static void main(String[] args) {
        String cron = Date2Cron.getCron(new Date());
        System.out.println(cron);
    }
}
