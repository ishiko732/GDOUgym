package edu.gdou.gym_java.utils;

import lombok.val;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class StringTimeStampUtils {

    public static Timestamp StringToTimeStamp(String time){
        return Timestamp.valueOf(time);
    }

    public static String TimeStampToString(Timestamp timestamp){
        val simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(timestamp);
    }

    public static String TimeStampToString(String pattern,Timestamp timestamp){
        val simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(timestamp);
    }

}
