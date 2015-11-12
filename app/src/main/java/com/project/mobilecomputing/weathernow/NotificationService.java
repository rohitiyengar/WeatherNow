package com.project.mobilecomputing.weathernow;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    public WeatherThread weatherThread;
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override

    public void onCreate() {
        weatherThread=new WeatherThread();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        weatherThread.start();
        return START_STICKY;
    }

    @Override

    public void onDestroy() {

        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

    }

    class WeatherThread extends Thread{
        static final long DELAY = 5000;
        @Override
        public void run(){
            while(true){

                try {
                    System.out.println("Hello");
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;

                }
            }
        }

    }

}
