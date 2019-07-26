package com.example.ifasanat.Helper;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateGenerator {

    public static Date GetGeorgianMoment(String moment)
    {
        String[] parts = moment.split(" ");
        String[] date = parts[0].split("/");
        int year = Integer.parseInt(date[0]), month = Integer.parseInt(date[0]), day = Integer.parseInt(date[0]);
        int hour, minute;
        try
        {
            String[] time = parts[1].split(":");
            hour =  Integer.parseInt(time[0]);
            minute =  Integer.parseInt(time[1]);
        }
        catch (Exception e)
        {
            hour = 0; minute = 0;
        }

        return new Date(year, month, day, hour, minute, 0);
    }


    public static Date DateSimple(String moment){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");
        Date d = new Date();
        try {
             d = sdf.parse(moment);
        } catch (ParseException ex) {
            Log.v("Exception", ex.getLocalizedMessage());
        }
        return d;
    }
}
