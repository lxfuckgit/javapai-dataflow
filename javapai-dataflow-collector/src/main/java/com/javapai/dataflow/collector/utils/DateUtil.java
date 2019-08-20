package com.javapai.dataflow.collector.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_MINUTE_PATTERN = "yyyyMMddHHmm";

    public static final int SECONDQUARTERDAY = 60 * 60 * 6;
    public static final int SECONDHALFDAY = 60 * 60 * 12;
    public static final int SECOND1DAY = 60 * 60 * 24;
    public static final int MILLIS1MINUTE = 60 * 1000;
    public static final int SECONDHALFHOUR = 60 * 30;
    public static final int MILLIS1DAY = SECOND1DAY * 1000;

    public static String getToday() {
        return getStrDate(new Date());
    }

    public static String getStrDate(Date date) {
        return getStrDate(date, DEFAULT_DATE_PATTERN);
    }

    public static String getStrDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date getDate(String date) {
        return getDate(DEFAULT_DATE_PATTERN, date);
    }

    public static Date getDate(String pattern, String date) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getTodayEnd() {
        return getDateEnd(new Date());
    }

    public static Date getDateEnd(Date date) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        return todayEnd.getTime();
    }

    public static String getHourMinute(Date date) {
        String qualifierName = null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour < 10) {
            qualifierName = "0" + String.valueOf(hour);
        } else {
            qualifierName = String.valueOf(hour);
        }

        int minute = calendar.get(Calendar.MINUTE);
        if (minute <= 15) {
            qualifierName += "15";
        } else if (minute <= 30) {
            qualifierName += "30";
        } else if (minute <= 45) {
            qualifierName += "45";
        } else {
            if (hour == 23) {
                qualifierName = "00";
            } else {
                ++hour;
                if (hour < 10) {
                    qualifierName = "0" + String.valueOf(hour);
                } else {
                    qualifierName = String.valueOf(hour);
                }
            }
            qualifierName += "00";
        }

        return qualifierName;

    }

    /**
     * 获取日期段中的所有Date
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List<Date> getDates(Date beginDate, Date endDate) {
        List<Date> dates = new ArrayList<Date>();
        dates.add(beginDate);

        Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(beginDate);
        while (endDate.after(beginCal.getTime())) {
            beginCal.add(Calendar.DAY_OF_MONTH, 1);
            dates.add(beginCal.getTime());
        }
        return dates;
    }

    /**
     * 根据一段时间区间，按周拆分成多个时间段
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List<KeyValueForDate> getKeyValueForDateByWeekly(Date beginDate, Date endDate) {
        List<KeyValueForDate> list = new ArrayList<KeyValueForDate>();

        Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(beginDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int startDayOfWeek = beginCal.get(Calendar.DAY_OF_WEEK);
        int endDayOfWeek = endCal.get(Calendar.DAY_OF_WEEK);

        Calendar cal = Calendar.getInstance();
        while (beginCal.getTime().before(endDate)) {
            KeyValueForDate keyValueForDate = new KeyValueForDate();
            cal.setTime(beginCal.getTime());

            if (beginCal.getTime().equals(beginDate)) {
                // 获得当前日期是一个星期的第几天，按中国的习惯一个星期的第一天是星期一
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                if (1 == dayOfWeek)
                    dayOfWeek = 7;
                else
                    dayOfWeek--;

                keyValueForDate.setStartDate(beginDate);
                int offset = 7 - dayOfWeek;
                cal.add(Calendar.DATE, offset);
                keyValueForDate.setEndDate(cal.getTime());
            } else if (beginCal.get(Calendar.WEEK_OF_YEAR) == endCal.get(Calendar.WEEK_OF_YEAR) && beginCal.get(Calendar.YEAR) == endCal.get(Calendar.YEAR)) {
                // 获取星期的第一天，按中国的习惯一个星期的第一天是星期一
                cal.set(Calendar.DAY_OF_WEEK, 1);
                cal.add(Calendar.DATE, 1);
                keyValueForDate.setStartDate(cal.getTime());
                keyValueForDate.setEndDate(endDate);
            } else {
                // 获取星期的第一天，按中国的习惯一个星期的第一天是星期一
                cal.set(Calendar.DAY_OF_WEEK, 1);
                cal.add(Calendar.DATE, 1);
                keyValueForDate.setStartDate(cal.getTime());
                cal.add(Calendar.DATE, 6);
                keyValueForDate.setEndDate(cal.getTime());
            }

            list.add(keyValueForDate);
            beginCal.add(Calendar.WEEK_OF_MONTH, 1);// 进行当前日期星期加1

        }

        if (endDayOfWeek < startDayOfWeek) {
            KeyValueForDate keyValueForDate = new KeyValueForDate();
            cal.setTime(endDate);
            cal.set(Calendar.DAY_OF_WEEK, 1);
            cal.add(Calendar.DATE, 1);
            keyValueForDate.setStartDate(cal.getTime());
            keyValueForDate.setEndDate(endDate);
            list.add(keyValueForDate);
        }

        return list;

    }

    /**
     * 根据一段时间区间，按月份拆分成多个时间段
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List<KeyValueForDate> getKeyValueForDateByMonthly(Date beginDate, Date endDate) {
        List<KeyValueForDate> list = new ArrayList<KeyValueForDate>();

        Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(beginDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int startDayOfMonth = beginCal.get(Calendar.DAY_OF_MONTH);
        int endDayOfMonth = endCal.get(Calendar.DAY_OF_MONTH);

        Calendar cal = Calendar.getInstance();
        while (beginCal.getTime().before(endDate)) {
            KeyValueForDate keyValueForDate = new KeyValueForDate();
            cal.setTime(beginCal.getTime());

            if (beginCal.getTime().equals(beginDate)) {
                cal.set(Calendar.DAY_OF_MONTH, beginCal.getActualMaximum(Calendar.DAY_OF_MONTH));
                keyValueForDate.setStartDate(beginCal.getTime());
                keyValueForDate.setEndDate(cal.getTime());
            } else if (beginCal.get(Calendar.MONTH) == endCal.get(Calendar.MONTH) && beginCal.get(Calendar.YEAR) == endCal.get(Calendar.YEAR)) {
                cal.set(Calendar.DAY_OF_MONTH, 1);//取第一天
                keyValueForDate.setStartDate(cal.getTime());
                keyValueForDate.setEndDate(endDate);
            } else {
                cal.set(Calendar.DAY_OF_MONTH, 1);//取第一天
                keyValueForDate.setStartDate(cal.getTime());
                cal.set(Calendar.DAY_OF_MONTH, beginCal.getActualMaximum(Calendar.DAY_OF_MONTH));
                keyValueForDate.setEndDate(cal.getTime());
            }
            list.add(keyValueForDate);
            beginCal.add(Calendar.MONTH, 1);// 进行当前日期月份加1
        }

        if (endDayOfMonth < startDayOfMonth) {
            KeyValueForDate keyValueForDate = new KeyValueForDate();
            cal.setTime(endDate);
            cal.set(Calendar.DAY_OF_MONTH, 1);//取第一天

            keyValueForDate.setStartDate(cal.getTime());
            keyValueForDate.setEndDate(endDate);
            list.add(keyValueForDate);
        }

        return list;
    }

    public static List<String> getMinutes(Date beginDate, Date endDate) {
        List<String> minutes = new ArrayList<String>();

        Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(beginDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int startSecondOfDay = beginCal.get(Calendar.SECOND);
        int endSendOfDay = endCal.get(Calendar.SECOND);

        Calendar cal = Calendar.getInstance();
        while (beginCal.getTime().before(endDate)) {
            cal.setTime(beginCal.getTime());
            minutes.add(getStrDate(cal.getTime(), DEFAULT_MINUTE_PATTERN));
            beginCal.setTime(addMinute(beginCal.getTime(), 1));//当前日期加1分钟
        }

        if (endSendOfDay <= startSecondOfDay) {
            minutes.add(getStrDate(endDate, DEFAULT_MINUTE_PATTERN));
        }

        return minutes;
    }

    public static Date addMinute(Date date, int minute) {
        Date d = new Date();
        d.setTime(date.getTime() + minute * MILLIS1MINUTE);
        return d;
    }

    public static void main(String[] args) throws Exception {
        byte[] bytes = "45".getBytes();
        System.out.println(bytes);


        System.out.println("current time: " + new Date().getTime());

        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-11-22 00:00:00");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-12-23 00:00:00");

        System.out.println(startDate.getTime());
        System.out.println(endDate.getTime());

        Date todayEnd = DateUtil.getTodayEnd();
        System.out.println(todayEnd);

        List<Date> dates = DateUtil.getDates(startDate, endDate);
        for (Date date : dates) {
            System.out.println(date);
        }

        List<KeyValueForDate> list = DateUtil.getKeyValueForDateByWeekly(startDate, endDate);
        //List<KeyValueForDate> list = DateUtil.getKeyValueForDateByMonthly(startDate, endDate);
        for (KeyValueForDate keyValueForDate : list) {
            System.out.println(keyValueForDate);
        }

        Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-16 00:00:30");
        Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-16 01:14:29");
        List<String> halfMinutes = DateUtil.getMinutes(start, end);
        System.out.println(halfMinutes.size());
        for (String halfMinute : halfMinutes) {
            System.out.println(halfMinute);
        }

        String strDate=DateUtil.getStrDate(new Date(),"yyyy-MM-dd HH:mm");
        System.out.println(strDate.substring(0, 15));

    }

    public static Date addDay(Date date, int day) {
        Date d = new Date();
        d.setTime(date.getTime() + day * MILLIS1DAY);
        return d;
    }

}
