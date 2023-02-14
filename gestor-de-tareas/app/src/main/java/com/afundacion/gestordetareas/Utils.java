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
        String[] dateSplitted= date.split("-",3);
        if(Integer.parseInt(dateSplitted[0]) > pYear){
            return true;
        } else if (Integer.parseInt(dateSplitted[0])==pYear && Integer.parseInt(dateSplitted[1])>pMonth){
            return true;
        } else if(Integer.parseInt(dateSplitted[1])==pMonth && Integer.parseInt(dateSplitted[2])>=pDay){
            return true;
        }
        return false;
    }



}
