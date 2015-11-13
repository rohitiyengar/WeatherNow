package com.project.mobilecomputing.weathernow;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.mobilecomputing.weathernow.helpers.GPSLocationProvider;
import com.project.mobilecomputing.weathernow.helpers.JSONParser;
import com.project.mobilecomputing.weathernow.helpers.WeatherClient;
import com.project.mobilecomputing.weathernow.models.WeatherData;

import org.json.JSONException;

public class HomeScreen extends Activity {
    ImageView weatherImageView;
    ImageView gpsImageView;
    ImageView mapImageView;
    ImageView historyImageView;
    ImageView favImageView;
    ImageView aboutImageView;
    GPSLocationProvider gps;
    double latitude;
    double longitude;
    String temp;
    String conditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        gps= new GPSLocationProvider(HomeScreen.this);
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

        }
        else{

            gps.showSettingsAlert();
        }

        weatherImageView=(ImageView)findViewById(R.id.weatherImageView);
        gpsImageView=(ImageView)findViewById(R.id.gpsImageView);
        mapImageView=(ImageView)findViewById(R.id.mapImageView);
        historyImageView=(ImageView)findViewById(R.id.historyImageView);
        favImageView=(ImageView)findViewById(R.id.favImageView);
        aboutImageView=(ImageView)findViewById(R.id.aboutImageView);

        weatherImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cityIntent= new Intent(HomeScreen.this,CityEntry.class);
                startActivity(cityIntent);
            }
        });

        gpsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gps= new GPSLocationProvider(HomeScreen.this);
                if(gps.canGetLocation()){

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    Intent weatherIntent = new Intent(HomeScreen.this, WeatherDetails.class);
                    weatherIntent.putExtra("mode",1);//GPS Mode.
                    weatherIntent.putExtra("lat",latitude);
                    weatherIntent.putExtra("lon",longitude);
                    startActivity(weatherIntent);
                }
                else{

                    gps.showSettingsAlert();
                }
            }
        });

        mapImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONWeatherTask task = new JSONWeatherTask();
                task.execute(latitude+"",longitude+"");

            }
        });

        historyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeScreen.this,"Location History",Toast.LENGTH_SHORT).show();
            }
        });

        favImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeScreen.this,"Favorites",Toast.LENGTH_SHORT).show();

            }
        });

        aboutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeScreen.this, "About Us", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private class JSONWeatherTask extends AsyncTask<String, Void, WeatherData> {

        @Override
        protected WeatherData doInBackground(String... params) {
            WeatherData weather = new WeatherData();
            String data="";
            data = ((new WeatherClient()).getWeatherData(params[0], params[1]));
            try {
                weather = JSONParser.getWeatherData(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;
        }

        @Override
        protected void onPostExecute(WeatherData weather) {

            super.onPostExecute(weather);
            try{
                temp=Math.round((weather.temperature.getTemp() - 273.15)) + "° C";
                conditions=weather.conditions.getCondition().toUpperCase();

                Intent mapIntent = new Intent(HomeScreen.this, MapsActivity.class);

                mapIntent.putExtra("lat",latitude);
                mapIntent.putExtra("lon",longitude);
                mapIntent.putExtra("temp",temp);
                mapIntent.putExtra("cond",conditions);
                startActivity(mapIntent);

            }
            catch (Exception e)
            {
                Toast.makeText(HomeScreen.this, "No Data Found.", Toast.LENGTH_LONG).show();
                finish();
            }

        }

    }
}
