package com.project.mobilecomputing.weathernow.models;

/**
 * Created by Kannan on 11/11/2015.
 */
public class DayForecast {
    public long longDate;
    public String weather;
    public Temp temperature = new Temp();

    public Temp getTemperature() {
        return temperature;
    }

    public String getWeather() {

        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public long getLongDate() {
        return longDate;
    }

    public void setLongDate(long longDate) {
        this.longDate = longDate;
    }

    public class Temp {
        private float minTemp;
        private float maxTemp;

        public float getMinTemp() {
            return minTemp;
        }

        public void setMinTemp(float minTemp) {
            this.minTemp = minTemp;
        }

        public float getMaxTemp() {
            return maxTemp;
        }

        public void setMaxTemp(float maxTemp) {
            this.maxTemp = maxTemp;
        }
    }
}
