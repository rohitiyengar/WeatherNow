package com.project.mobilecomputing.weathernow.models;

import java.io.Serializable;

/**
 * Created by rohit.iyengar on 11/7/2015.
 * Reference: http://www.survivingwithandroid.com/2013/05/build-weather-app-json-http-android.html
 */
public class Location implements Serializable {
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private long sunset;
    private long sunrise;


    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }
}
