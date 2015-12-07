package com.project.mobilecomputing.weathernow;

import android.app.ActionBar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double latitude;
    double longitude;

    String temp;
    String conditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#06272E")));
        actionBar.setTitle("WeatherNow");

        latitude = Double.parseDouble(getIntent().getExtras().get("lat").toString());
        longitude = Double.parseDouble(getIntent().getExtras().get("lon").toString());
        temp = getIntent().getExtras().get("temp").toString();
        conditions = getIntent().getExtras().get("cond").toString();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng current = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(current).title(temp + " | Conditions: " + conditions));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
    }


}

