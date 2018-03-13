package com.chuxin.law.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zhaoyapeng
 * @version create time:18/3/514:24
 * @Email zyp@jusfoun.com
 * @Description ${日期 model}
 */
public class DateUtil {
    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static String getDateBefore(String startTime, int day) {
        String end = "";
        Date date;
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format1.parse(startTime);
            Calendar now = Calendar.getInstance();
            now.setTime(date);
            now.set(Calendar.DATE, now.get(Calendar.DATE) - day);

            end = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH) +" "+getWeek(now.get(Calendar.DAY_OF_WEEK));
        } catch (ParseException e) {
            e.printStackTrace();
        }



        return end;
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static String getDateAfter(String startTime, int day) {
        String end = "";
        Date date;
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format1.parse(startTime);
            Calendar now = Calendar.getInstance();
            now.setTime(date);
            now.set(Calendar.DATE, now.get(Calendar.DATE) +day);

            end = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH) +" "+getWeek(now.get(Calendar.DAY_OF_WEEK));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return end;
    }

    public static String getWeek(int dayOfWeek){
        switch (dayOfWeek)
        {
            case 1:
                return "星期日(休息日)";
            case 2:
                return "星期一(工作日)";
            case 3:
                return "星期二(工作日)";
            case 4:
                return "星期三(工作日)";
            case 5:
                return "星期四(工作日)";
            case 6:
                return "星期五(工作日)";
            case 7:
                return "星期六(休息日)";
        }
        return "";
    }

    /**
     * 日期转字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String parseDateToString(Date date) {
        if (date == null) {
            return null;
        }
        try {
            DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateformat.format(date);
        } catch (Exception e) {
            return null;
        }
    }


    public static int compareDate(String startDay,String endDay,int stype) {
        int n = 0;
        String[] u = {"天", "月", "年"};
        String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM-dd";

        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(startDay));
            c2.setTime(df.parse(endDay));
        } catch (Exception e3) {
            System.out.println("wrong occured");
        }
        //List list = new ArrayList();
        while (!c1.after(c2)) {                   // 循环对比，直到相等，n 就是所要的结果
            //list.add(df.format(c1.getTime()));    // 这里可以把间隔的日期存到数组中 打印出来
            n++;
            if (stype == 1) {
                c1.add(Calendar.MONTH, 1);          // 比较月份，月份+1
            } else {
                c1.add(Calendar.DATE, 1);           // 比较天数，日期+1
            }
        }
        n = n - 1;
        if (stype == 2) {
            n = (int) n / 365;
        }
        System.out.println(startDay + " -- " + endDay + " 相差多少" + u[stype] + ":" + n);
        return n;
    }
    /**
     * 整数时间转换成字符串
     *
     * @param time
     * @return
     */
    public static String parseTimeToString(int time) {

        time /= 1000;
        int minute = time / 60;
        // int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }


    /**
     *  计算 两个日期之间的天数
     * */
    @SuppressWarnings("deprecation")
    public static int getDutyDays(String strStartDate,String strEndDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate=null;
        Date endDate = null;

        try {
            startDate=df.parse(strStartDate);
            endDate = df.parse(strEndDate);
        } catch (ParseException e) {
            System.out.println("非法的日期格式,无法进行转换");
            e.printStackTrace();
        }
        int result = 0;
        while (startDate.compareTo(endDate) <= 0) {
            if (startDate.getDay() != 6 && startDate.getDay() != 0)
                result++;
            startDate.setDate(startDate.getDate() + 1);
        }

        return result;
    }


    /**
     *  计算某个日期之后 N个工作日的日期
     * */
    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static String getDateWorkAfter(String startTime, int day,boolean isBefore) {
        String end = "";
        Date date;
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format1.parse(startTime);
            Calendar now = Calendar.getInstance();
            now.setTime(date);

            for(int i=0;i<day;i++){
                if(isBefore){
                    now.add(Calendar.DAY_OF_MONTH,-1);
                    if(isWeekDay(now.get(Calendar.DAY_OF_WEEK))){
                        i--;
                    }
                }else{
                    now.add(Calendar.DAY_OF_MONTH,1);
                    if(isWeekDay(now.get(Calendar.DAY_OF_WEEK))){
                        i--;
                    }
                }
            }

            end = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH) +" "+getWeek(now.get(Calendar.DAY_OF_WEEK));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return end;
    }


    /**
     *  是否是周末
     * */
    public static boolean isWeekDay(int DAY_OF_WEEK){
        if(DAY_OF_WEEK==1||DAY_OF_WEEK==7){
            return true;
        }
        return false;
    }

}
