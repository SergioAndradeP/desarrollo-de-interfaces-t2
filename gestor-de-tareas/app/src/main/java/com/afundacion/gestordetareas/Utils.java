package com.afundacion.gestordetareas;


import android.view.View;
import android.view.LayoutInflater;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public  class Utils {

    public static boolean DateIsFuture(String date){
        Calendar c1 = Calendar.getInstance();
        int pYear = c1.get(Calendar.YEAR);
        int pMonth = c1.get(Calendar.MONTH)+1;
        int pDay= c1.get(Calendar.DAY_OF_MONTH);
        String[] dateSplitted= date.split("/",3);
        if(Integer.parseInt(dateSplitted[2]) > pYear){
            return true;
        } else if (Integer.parseInt(dateSplitted[2])==pYear && Integer.parseInt(dateSplitted[1])>pMonth){
            return true;
        } else if(Integer.parseInt(dateSplitted[1])==pMonth && Integer.parseInt(dateSplitted[0])>=pDay){
            return true;
        }
        return false;
    }

    public Calendar dateFormat(String date) throws ParseException {
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = format.parse(date);
        c1.setTime(date1);
        return c1;
    }

}
