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


public class NotificationService extends Service {
    public WeatherThread weatherThread;
    GPSLocationProvider gps;
    double latitude;
    double longitude;
    static int notificationNumber=99999;

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override

    public void onCreate() {
        weatherThread=new WeatherThread(); //Initialize Thread.
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        weatherThread.start(); //Start Thread.
        return START_STICKY;
    }

    @Override

    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    public void getWeather()
    {
        WeatherData weather = new WeatherData();
        String data="";
        data = ((new WeatherClient()).getWeatherData(latitude+"", longitude+""));
        try {
            weather = JSONParser.getWeatherData(data);
            /*Insert Logic to check for weather conditions to give alerts.*/
            showNotification("Weather Alert In Your Area!",weather.conditions.getCondition()+": "+weather.conditions.getDescr().toUpperCase());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

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

    class WeatherThread extends Thread{
        static final long DELAY = 1000*5*60;

        public WeatherThread()
        {
            gps= new GPSLocationProvider(NotificationService.this);
            if(gps.canGetLocation()){

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

            }
            else{

                gps.showSettingsAlert();
            }
        }
        @Override
        public void run(){
            while(true){

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
