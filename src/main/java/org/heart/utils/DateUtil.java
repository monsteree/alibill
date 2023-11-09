package org.heart.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public final class DateUtil {

    /**
     * 14位时间格式-format
     */
    public static final String DATE_14 = "yyyyMMddHHmmss";

    public static final String DATE_8 = "yyyyMMdd";

    public static final String HOUR = "HH";

    public static final String DATE_19 = "yyyy-MM-dd HH:mm:ss";

    public static String getChar14() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_14));
    }


    public static Integer getHour() {
        return Integer.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern(HOUR)));
    }

    public static String getChar8() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_8));
    }

    /**
     * 获取utc +8 时区 13位毫秒
     * @return
     */
    public static Long getUTCTime(){
        Instant now = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8));
        return now.toEpochMilli();
    }

    /**
     * char 14 转成 utc 北京时间 毫秒
     * @param time
     * @return
     */
    public static Long getChar14ToUTC(String time){
        LocalDateTime localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(DATE_14));
//        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * utc 转成 char 14 北京时间 毫秒
     * @param utcTime
     * @return
     */
    public static String getUTCToChar14(Long utcTime){
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(utcTime), ZoneOffset.of("+8"));
        return localDateTime.format(DateTimeFormatter.ofPattern(DATE_14));
    }

    /**
     *
     * @param inputDateTime 输入时间
     * @param inPattern     输入时间格式
     * @param outPattern    输出日期格式
     * @return
     */
    public static String convert(String inputDateTime,String inPattern, String outPattern){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inPattern);
        LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, inputFormatter);
        // 格式化为目标格式的字符串
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outPattern);
        return dateTime.format(outputFormatter);
    }


    private DateUtil() {
    }
}