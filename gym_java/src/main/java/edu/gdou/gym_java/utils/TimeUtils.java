package edu.gdou.gym_java.utils;

import lombok.val;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtils {

    public static Timestamp StringToTimeStamp(String time) {
        return Timestamp.valueOf(time);
    }

    public static String TimeStampToString(Timestamp timestamp) {
        val simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(timestamp);
    }

    public static String TimeStampToString(String pattern, Timestamp timestamp) {
        val simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(timestamp);
    }

    public static Timestamp nowToTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String nowToString(String pattern) {
        return TimeStampToString(pattern, nowToTimeStamp());
    }

    public static String nowToString() {
        return TimeStampToString(nowToTimeStamp());
    }

    public static Date nowToDate(){
        return new Date(System.currentTimeMillis());
    }

    public static Timestamp DateToTimeStamp(Date date) {
        return new Timestamp(date.getTime());
    }

    public static Date TimeStampToDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }

    //相隔天数
    public static int getDayDiffer(Date startDate, Date endDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long startDateTime = dateFormat.parse(dateFormat.format(startDate)).getTime();
        long endDateTime = dateFormat.parse(dateFormat.format(endDate)).getTime();
        return (int) ((endDateTime - startDateTime) / (1000 * 3600 * 24));
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

}
