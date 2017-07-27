package com.wenny.one.util;

import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wenny on 2016/11/1.
 */

public class TimeUtil {

    public static String getDates(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);

        String string = strtodate.toString();
        String date = "";
        String[] split = string.split(" ");
        date = split[0] + " " + split[2] + " " + split[1] + "." + split[5];
        return date;
    }

    public static String getDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);

        String string = strtodate.toString();
        String date = "";
        String[] split = string.split(" ");
        date = split[1] + " " + split[2] + "." + split[5];
        return date;
    }

    public static String getDateMoth(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);

        String string = strtodate.toString();
        String date = "";
        String[] split = string.split(" ");
        date = split[1] + "." + split[5];
        return date;
    }

    public static List<String> getListTime(String endDateStr) {
        List<String> strings = new ArrayList<>();
        //获得当前时间
        Date nowDateShort = new Date(System.currentTimeMillis());

        //最后时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date endDate = formatter.parse(endDateStr, pos);

        Log.d("print", "nowDateShort" + nowDateShort  + " \n endDate  " + endDate);

        Log.d("print", "nowDateShort" + getDateString(nowDateShort)  + " \n endDate  " + getDateString(endDate) );


        Calendar calendar = Calendar.getInstance();
        while (!getDateString(endDate).equals(getDateString(nowDateShort))) {
            calendar.setTime(endDate);
            calendar.add(Calendar.MONTH,+1);
            endDate = calendar .getTime();
            strings.add(getDateString(endDate) + "-01");
        }
        return strings;
    }

    public static String getDateString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static List<String> getListTimeShow(List<String> listTime){
        List<String> listTimeShow = new ArrayList<>();
        for (int i = listTime.size() - 1; i >=0 ; i--) {
            if(i == listTime.size() - 1){
                listTimeShow.add("本月");
            } else {
                listTimeShow.add(TimeUtil.getDateMoth(listTime.get(i)));
            }
        }
        return listTimeShow;
    }


}
