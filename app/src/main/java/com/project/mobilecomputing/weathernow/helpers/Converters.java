package com.project.mobilecomputing.weathernow.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by rohit.iyengar on 11/8/2015.
 */
public class Converters {
    /***
     * Method to convert bytes into formatted time
     *
     * @param time
     * @return String with desired time format.
     */
    public static String timeStampConverter(long time) {
        Date date = new Date(time * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    /**
     * Method to convert bytes into formatted time
     *
     * @param time
     * @return String with desired time format.
     */
    public static String timeStampConverterForDate(long time) {
        Date date = new Date(time * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    /***
     * Method to convert Country code into Country.
     *
     * @param code
     * @return Country name as String.
     */
    public static String countryCodeConverter(String code) {
        Locale l = new Locale("", code);
        return l.getDisplayCountry();
    }
}
