package com.shizhen5452.justlook.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Create by EminemShi on 2017/2/6
 */

public class DateUtils {

    public static String getDate(String str){
        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        ParsePosition pos = new ParsePosition(0);
        Date date = format.parse(str,pos);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week_index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index=0;
        }
        int month = calendar.get(Calendar.MONTH) + 1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        return month+"月"+day+"日 "+weeks[week_index];
    }


}
