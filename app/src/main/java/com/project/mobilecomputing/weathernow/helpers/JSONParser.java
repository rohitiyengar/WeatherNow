package com.project.mobilecomputing.weathernow.helpers;

import com.project.mobilecomputing.weathernow.models.DayForecast;
import com.project.mobilecomputing.weathernow.models.ForecastData;
import com.project.mobilecomputing.weathernow.models.Location;
import com.project.mobilecomputing.weathernow.models.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rohit.iyengar on 11/7/2015.
 * Referenced from http://www.survivingwithandroid.com/2013/05/build-weather-app-json-http-android.html
 */
public class JSONParser {
    public static WeatherData getWeatherData(String result)throws JSONException
    {
        Location location=new Location();
        WeatherData weatherData= new WeatherData();
        JSONObject obj=new JSONObject(result);
        JSONObject coord=getObject("coord",obj);
        location.setLatitude(getFloat("lat",coord));
        location.setLongitude(getFloat("lon", coord));
        JSONObject sys=getObject("sys",obj);
        location.setCountry(getString("country", sys));
        location.setCity(getString("name", obj));
        location.setSunrise(getInt("sunrise", sys));
        location.setSunset(getInt("sunset", sys));
        weatherData.location=location;
        JSONArray jArr = obj.getJSONArray("weather");
        JSONObject weatherObj = jArr.getJSONObject(0);
        weatherData.conditions.setWeatherId(getInt("id", weatherObj));
        weatherData.conditions.setDescr(getString("description", weatherObj));
        weatherData.conditions.setCondition(getString("main", weatherObj));
        weatherData.conditions.setIcon(getString("icon", weatherObj));
        JSONObject main = getObject("main", obj);
        weatherData.conditions.setHumidity(getInt("humidity", main));
        weatherData.conditions.setPressure(getInt("pressure", main));
        weatherData.temperature.setMaxTemp(getFloat("temp_max", main));
        weatherData.temperature.setMinTemp(getFloat("temp_min", main));
        weatherData.temperature.setTemp(getFloat("temp", main));
        JSONObject wind = getObject("wind", obj);
        weatherData.wind.setSpeed(getFloat("speed", wind));
        weatherData.wind.setDeg(getFloat("deg", wind));
        JSONObject cloud = getObject("clouds", obj);
        weatherData.clouds.setPerc(getInt("all", cloud));
        return weatherData;
    }
    public static ForecastData getForecastData(String result) throws JSONException
    {
        ForecastData forecastData= new ForecastData();
        JSONObject jsonObject = new JSONObject(result);
        JSONObject city = getObject("city",jsonObject);
        forecastData.setCity(getString("name",city));
        forecastData.setCountry(getString("country",city));
        JSONArray forecastList = jsonObject.getJSONArray("list");
        for(int i=0;i<5;i++)
        {
            DayForecast dayForecast= new DayForecast();
            JSONObject dayForecastObj = forecastList.getJSONObject(i);
            dayForecast.setLongDate(getInt("dt",dayForecastObj));
            JSONObject temp = getObject("temp",dayForecastObj);
            dayForecast.temperature.setMaxTemp(getFloat("max",temp));
            dayForecast.temperature.setMinTemp(getFloat("min",temp));
            JSONArray weather = dayForecastObj.getJSONArray("weather");
            JSONObject weatherObj = weather.getJSONObject(0);
            dayForecast.setWeather(getString("description",weatherObj));
            forecastData.addDayForecast(dayForecast);
        }
        return forecastData;
    }
    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

}
