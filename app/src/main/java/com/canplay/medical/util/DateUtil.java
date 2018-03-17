package com.canplay.medical.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 获取时间戳
     *
     * @return 获取时间戳
     */
    public static Long getTimeLong() {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        return time.getTime();

    }

    public static Date getSystemDate() {
        Date date = new Date();

        return date;
    }

    public static String getSystemTime() {
        return getDateLongToString(getTimeLong(), 4);
    }

    public static boolean isLittle(long messageTime){
        Long newTime = getTimeLong();
        String newString = getDateToString(newTime);
        String oldString = getDateToString(messageTime);
        // 计算的时间差
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(newString);
            Date d2 = df.parse(oldString);
            // 这样得到的差值是微秒级别
            long diff = d1.getTime() - d2.getTime() > 0 ? d1.getTime() - d2.getTime() : d2.getTime() - d1.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff/ (1000 * 60) - days * ( 60 * 24) - hours * 60) ;
            long s = (diff / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60);
            if(days==0&&hours==0&&minutes==0&&s<60){//设置时间差为60s
                return true;
            }else {
                if(minutes==1&&s==0){
                    return true;
                }
                return false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static long getLittleTime(long messageTime){
        Long newTime = getTimeLong();
        String newString = getDateToString(newTime);
        String oldString = getDateToString(messageTime);
        // 计算的时间差
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(newString);
            Date d2 = df.parse(oldString);
            // 这样得到的差值是微秒级别
            long diff = d1.getTime() - d2.getTime() > 0 ? d1.getTime() - d2.getTime() : d2.getTime() - d1.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff/ (1000 * 60) - days * ( 60 * 24) - hours * 60) ;
            long s = (diff / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60);//计算出秒差（60s以内：现在时间的前一分钟都可以）
            if(isLittle(messageTime)){
                return 60-s;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
       return 0;
    }
    /**
     * 将时间转化为年月日
     *
     * @param time
     * @param type 默认年月日时分
     *             1：年月日
     *             2：月日
     *             3：月日时分
     *             4：时分
     * @return
     */
    public static String getDateLongToString(long time, int type) {
        Date d = new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;//tmd.因为老外计算只有0-11月
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        String minutesStr = minutes < 10 ? "0" + minutes : minutes + "";
        String hoursStr = hours == 0 ? hours + "0" : hours + "";
        if (type == 1) {
            return year + "年" + month + "月" + day + "日";
        }
        if (type == 2) {
            return month + "月" + day + "日";
        }
        if (type == 3) {
            return month + "月" + day + "日" + " " + hoursStr + ":" + minutesStr;
        }
        if (type == 4) {
            return hoursStr + ":" + minutesStr;
        }
        return year + "年" + month + "月" + day + "日" + " " + hoursStr + ":" + minutesStr;
    }
    /*时间戳转换成日期*/
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }
    /**
     * 计算获得时间差
     *
     * @param newTime：本次获取的时间戳
     * @param oldTime：上次获取的时间
     * @return
     */
    public static String getTimeDistance(long newTime, long oldTime) {

        String newString = getDateToString(newTime);
        String oldString = getDateToString(oldTime);
        // 计算的时间差
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timedistance = null;
        try {
            Date d1 = df.parse(newString);
            Date d2 = df.parse(oldString);
            // 这样得到的差值是微秒级别
            long diff = d1.getTime() - d2.getTime() > 0 ? d1.getTime() - d2.getTime() : d2.getTime() - d1.getTime();

//            long days = diff / (24 * 60 * 60 * 1000);
//            long hours = (diff / (60 * 60 * 1000) - days * 24);
//            long minutes = ((diff / (60 * 1000)) - days * 24 * 60 - hours * 60);
//            long s = (diff / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60);


            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff/ (1000 * 60) - days * ( 60 * 24) - hours * 60) ;
            long s = (diff / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60);
            if (days == 0) {
                if (hours == 0) {
                    if (minutes == 0) {
                        if(s == 0){
                            timedistance ="1秒";
                        }else {
                            timedistance =s+ "秒";
                        }
                    }else {
                        timedistance = "" + minutes + "分"+s+ "秒";
                    }
                } else {
                    if (minutes == 0) {
                        timedistance = "" + hours + "小时"+s+ "秒";
                    } else {
                        timedistance = "" + hours + "小时" + minutes + "分"+s+ "秒";
                    }
                }
            } else {
                if (hours == 0) {
                    if (minutes == 0) {
                        timedistance = "" + days + "天";
                    }else {
                        timedistance = "" + days + "天" + minutes + "分"+s+ "秒";
                    }
                }else {
                    if (minutes == 0) {
                        timedistance = "" + days + "天" + hours + "小时"+s+ "秒";
                    } else {
                        timedistance = "" + days + "天" + hours + "小时" + minutes + "分"+s+ "秒";
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timedistance;
    }

}
