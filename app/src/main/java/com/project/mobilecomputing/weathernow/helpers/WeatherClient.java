package com.project.mobilecomputing.weathernow.helpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rohit.iyengar on 11/7/2015.
 * Reference: http://www.survivingwithandroid.com/2013/05/build-weather-app-json-http-android.html
 * Reference: http://openweathermap.org/api
 */
public class WeatherClient {
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
    private static String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";

    /***
     * Method to take in city/country name and hit the OpenWeatherMap API to get weather data.
     *
     * @param location
     * @return response String.
     */
    public String getWeatherData(String location) {
        HttpURLConnection conn = null;
        InputStream input = null;
        String APIKey = "937e6570a97eab119af49fa7c2d04ba7";//Rohit Iyengar API Key.
        try {
            conn = (HttpURLConnection) (new URL(BASE_URL + "q=" + location + "&appid=" + APIKey)).openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();
            StringBuffer buffer = new StringBuffer();
            input = conn.getInputStream();
            BufferedReader obj = new BufferedReader(new InputStreamReader(input));
            String line = null;
            while ((line = obj.readLine()) != null)
                buffer.append(line + "\r\n");

            input.close();
            conn.disconnect();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (Throwable t) {

            }
            try {
                conn.disconnect();
            } catch (Throwable t) {

            }
        }

        return null;

    }
    /*Overloaded*/

    /***
     * Overloaded method to take in the latitude and longitude and hit the OpenWeatherMap API to get weather data.
     *
     * @param lat
     * @param lon
     * @return response String
     */
    public String getWeatherData(String lat, String lon) {
        HttpURLConnection conn = null;
        InputStream input = null;
        String APIKey = "937e6570a97eab119af49fa7c2d04ba7";//Rohit Iyengar API Key.
        try {
            conn = (HttpURLConnection) (new URL(BASE_URL + "lat=" + lat + "&lon=" + lon + "&appid=" + APIKey)).openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();
            StringBuffer buffer = new StringBuffer();
            input = conn.getInputStream();
            BufferedReader obj = new BufferedReader(new InputStreamReader(input));
            String line = null;
            while ((line = obj.readLine()) != null)
                buffer.append(line + "\r\n");

            input.close();
            conn.disconnect();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (Throwable t) {

            }
            try {
                conn.disconnect();
            } catch (Throwable t) {

            }
        }

        return null;

    }

    /***
     * Method to take in city/country name and hit the OpenWeatherMap API to get forcast data.
     *
     * @param location
     * @return response String.
     */
    public String getForecastData(String location) {
        HttpURLConnection conn = null;
        InputStream input = null;
        String APIKey = "937e6570a97eab119af49fa7c2d04ba7";//Rohit Iyengar API Key.
        try {
            conn = (HttpURLConnection) (new URL(FORECAST_BASE_URL + "q=" + location + "&mode=json&units=metric&cnt=5&appid=" + APIKey)).openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();
            StringBuffer buffer = new StringBuffer();
            input = conn.getInputStream();
            BufferedReader obj = new BufferedReader(new InputStreamReader(input));
            String line = null;
            while ((line = obj.readLine()) != null)
                buffer.append(line + "\r\n");

            input.close();
            conn.disconnect();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (Throwable t) {

            }
            try {
                conn.disconnect();
            } catch (Throwable t) {

            }
        }

        return null;

    }
}
