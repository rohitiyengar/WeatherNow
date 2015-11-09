package com.project.mobilecomputing.weathernow;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.mobilecomputing.weathernow.helpers.GPSLocationProvider;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        weatherImageView=(ImageView)findViewById(R.id.weatherImageView);
        gpsImageView=(ImageView)findViewById(R.id.gpsImageView);
        mapImageView=(ImageView)findViewById(R.id.mapImageView);
        historyImageView=(ImageView)findViewById(R.id.historyImageView);
        favImageView=(ImageView)findViewById(R.id.favImageView);
        aboutImageView=(ImageView)findViewById(R.id.aboutImageView);

        weatherImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeScreen.this,"Get Weather",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(HomeScreen.this,"Get Weather From GPS",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(HomeScreen.this,"Map",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(HomeScreen.this,"About Us",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
