package com.project.mobilecomputing.weathernow;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.project.mobilecomputing.weathernow.helpers.GPSLocationProvider;
import com.project.mobilecomputing.weathernow.helpers.JSONParser;
import com.project.mobilecomputing.weathernow.helpers.WeatherClient;
import com.project.mobilecomputing.weathernow.models.WeatherData;

import org.json.JSONException;

/***
 * A sticky service class which checks for weather conditions every 10 minutes and sends a notification alert.
 */
public class NotificationService extends Service {
    public WeatherThread weatherThread;
    GPSLocationProvider gps;
    double latitude;
    double longitude;
    static int notificationNumber = 99999;

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override

    public void onCreate() {
        weatherThread = new WeatherThread(); //Initialize Thread.
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!weatherThread.isAlive())
            weatherThread.start(); //Start Thread.
        return START_STICKY;
    }

    @Override

    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    /***
     * Method to get weather data for current location.
     */
    public void getWeather() {
        WeatherData weather = new WeatherData();
        String data = "";
        data = ((new WeatherClient()).getWeatherData(latitude + "", longitude + ""));
        try {
            weather = JSONParser.getWeatherData(data);
            /*We have commented out this if condition since we wanted to test notification service in the absence of extreme conditions.
            * Please uncomment for accurate functionality*/
            /*
            if(weather.conditions.getCondition().contains("rain")||weather.conditions.getCondition().contains("storm")
                    ||weather.conditions.getCondition().contains("snow")||weather.conditions.getCondition().contains("fog")
                    ||weather.conditions.getCondition().contains("smoke")||weather.conditions.getCondition().contains("dust"))*/
            showNotification("Weather Alert In Your Area!", weather.conditions.getCondition() + ": " + weather.conditions.getDescr().toUpperCase());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /***
     * Show a notification in the Notification Drawer
     * Reference: http://developer.android.com/training/notify-user/build-notification.html
     *
     * @param title
     * @param text
     */
    public void showNotification(String title, String text) {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, HomeScreen.class), 0);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.applogosmall);
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("Weather Alert!")
                .setSmallIcon(R.drawable.smalllogo)
                .setLargeIcon(largeIcon)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationNumber++, notification);
    }

    /***
     * Thread class to call getWeather() every 10 minutes.
     */
    class WeatherThread extends Thread {
        static final long DELAY = 1000 * 10 * 60; //10 Minute Delay.

        public WeatherThread() {
            gps = new GPSLocationProvider(NotificationService.this);
            if (gps.canGetLocation()) {

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

            } else {

                gps.showSettingsAlert();
            }
        }

        @Override
        public void run() {
            while (true) {

                try {
                    getWeather();
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;

                }
            }
        }

    }

}
