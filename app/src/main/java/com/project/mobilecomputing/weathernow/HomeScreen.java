package com.project.mobilecomputing.weathernow;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeScreen extends Activity {
    ImageView weatherImageView;
    ImageView gpsImageView;
    ImageView mapImageView;
    ImageView historyImageView;
    ImageView favImageView;
    ImageView aboutImageView;

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
            }
        });

        gpsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeScreen.this,"Get Weather From GPS",Toast.LENGTH_SHORT).show();
                Intent weatherIntent = new Intent(HomeScreen.this, WeatherDetails.class);
                startActivity(weatherIntent);
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
