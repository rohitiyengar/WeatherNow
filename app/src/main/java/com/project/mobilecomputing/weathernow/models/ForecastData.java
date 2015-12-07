package com.project.mobilecomputing.weathernow.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kannan on 11/11/2015.
 */
public class ForecastData {
    public String city;
    public String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<DayForecast> fiveDayForecast = new ArrayList<DayForecast>();

    public List<DayForecast> getFiveDayForecast() {
        return fiveDayForecast;
    }

    public void addDayForecast(DayForecast dayForecast) {
        fiveDayForecast.add(dayForecast);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
