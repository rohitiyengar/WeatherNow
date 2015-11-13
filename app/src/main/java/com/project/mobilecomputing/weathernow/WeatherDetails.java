package com.project.mobilecomputing.weathernow;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mobilecomputing.weathernow.helpers.Converters;
import com.project.mobilecomputing.weathernow.helpers.JSONParser;
import com.project.mobilecomputing.weathernow.helpers.WeatherClient;
import com.project.mobilecomputing.weathernow.models.WeatherData;

import org.json.JSONException;


public class WeatherDetails extends Activity {
    RelativeLayout weatherLayout;
    RelativeLayout mainLayout;
    TextView tempTextView;
    TextView conditionsTextView;
    TextView pressureTextView;
    TextView sunriseTextView;
    TextView sunsetTextView;
    TextView cityText;
    Button forecastButton;
    String temperature;
    String conditions;
    String city;
    String cityForecast;
    Integer mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        mainLayout=(RelativeLayout) findViewById(R.id.mainLayout);
        weatherLayout = (RelativeLayout) findViewById(R.id.weatherLayout);
        tempTextView = (TextView) findViewById(R.id.tempText);
        conditionsTextView = (TextView) findViewById(R.id.conditionTextView);
        pressureTextView = (TextView) findViewById(R.id.pressureTextView);
        sunriseTextView = (TextView) findViewById(R.id.sunriseTextView);
        sunsetTextView = (TextView) findViewById(R.id.sunsetTextView);
        cityText=(TextView)findViewById(R.id.cityTextView);
        forecastButton = (Button) findViewById(R.id.forecastButton);
        mode=getIntent().getIntExtra("mode",0);
        if(mode==1)
        {
            System.out.println(getIntent().getExtras().get("lat"));
            System.out.println(getIntent().getExtras().get("lon"));
            JSONWeatherTask task = new JSONWeatherTask();
            task.execute(getIntent().getExtras().get("lat").toString(),getIntent().getExtras().get("lon").toString());
        }
        else if(mode==0)
        {
            JSONWeatherTask task = new JSONWeatherTask();
            task.execute(getIntent().getExtras().get("city").toString());
        }

        ActionBar actionBar = getActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#06272E")));

        forecastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forecastIntent = new Intent(WeatherDetails.this, ForecastActivity.class);
                forecastIntent.putExtra("cityforecast",cityForecast);
                startActivity(forecastIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_share:
                String message = "The current temperature at "+city+" is "+temperature+" with "+conditions+" conditions \r\n Shared through WeatherNow© App!";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Share Current Weather Conditions"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, WeatherData> {

        @Override
        protected WeatherData doInBackground(String... params) {
            WeatherData weather = new WeatherData();
            String data="";
            if(mode==0)
            {
                data = ((new WeatherClient()).getWeatherData(params[0]));

            }
            else if(mode==1)
            {
                data = ((new WeatherClient()).getWeatherData(params[0], params[1]));
            }

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
                cityText.setText(weather.location.getCity().toUpperCase() + ", " + Converters.countryCodeConverter(weather.location.getCountry()));
                conditionsTextView.setText(weather.conditions.getCondition() + " (" + weather.conditions.getDescr().toUpperCase() + ")");
                tempTextView.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "° C");//Converting to celsius
                pressureTextView.setText("" + weather.conditions.getPressure() + " hPa");
                sunriseTextView.setText(Converters.timeStampConverter(weather.location.getSunrise())+"");
                sunsetTextView.setText(Converters.timeStampConverter(weather.location.getSunset())+"");
                if(weather.conditions.getIcon().contains("d"))
                {

                    mainLayout.setBackgroundColor(Color.parseColor("#16A7CE"));
                    if(weather.conditions.getCondition().contains("Clear"))
                    {
                        weatherLayout.setBackgroundResource(R.drawable.clear);
                    }
                    else if(weather.conditions.getCondition().contains("Rain"))
                    {
                        weatherLayout.setBackgroundResource(R.drawable.rain);
                    }
                    else if(weather.conditions.getCondition().contains("Cloud"))
                    {
                        weatherLayout.setBackgroundResource(R.drawable.clouds);
                    }
                    else if(weather.conditions.getCondition().contains("storm"))
                    {
                        weatherLayout.setBackgroundResource(R.drawable.thunder);
                    }
                    else if(weather.conditions.getCondition().contains("Snow"))
                    {
                        weatherLayout.setBackgroundResource(R.drawable.snow);
                    }
                    else if(weather.conditions.getCondition().contains("Mist"))
                    {
                        weatherLayout.setBackgroundResource(R.drawable.fog);
                    }
                    else if(weather.conditions.getCondition().contains("Extreme"))
                    {
                        weatherLayout.setBackgroundResource(R.drawable.thunder);
                    }
                    else if(weather.conditions.getCondition().contains("Atmosphere"))
                    {
                        weatherLayout.setBackgroundResource(R.drawable.fog);
                    }
                }
                else
                {

                    mainLayout.setBackgroundColor(Color.parseColor("#073440"));
                    if(weather.conditions.getCondition().contains("Clear"))
                    {
                        weatherLayout.setBackgroundResource(R.drawable.nightclear);
                    }
                    else if(weather.conditions.getCondition().contains("Rain"))
                    {
                        weatherLayout.setBackgroundResource(R.drawable.nightrain);
                    }
                    else if(weather.conditions.getCondition().contains("Cloud"))
                    {
                        weatherLayout.setBackgroundResource(R.drawable.nightclouds);
                    }
                    else if(weather.conditions.getCondition().contains("storm"))
                    {
                        weatherLayout.setBackgroundResource(R.drawable.nightthunder);
                    }
                    else if(weather.conditions.getCondition().contains("Snow"))
                    {
                        weatherLayout.setBackgroundResource(R.drawable.nightsnow);
                    }
                    else if(weather.conditions.getCondition().contains("Mist"))
                    {
                        weatherLayout.setBackgroundResource(R.drawable.nightfog);
                    }
                    else if(weather.conditions.getCondition().contains("Extreme"))
                    {
                        weatherLayout.setBackgroundResource(R.drawable.nightthunder);
                    }
                    else if(weather.conditions.getCondition().contains("Atmosphere"))
                    {
                        weatherLayout.setBackgroundResource(R.drawable.nightfog);
                    }
                }
                temperature=Math.round((weather.temperature.getTemp() - 273.15)) + "° C";
                city=weather.location.getCity().toUpperCase() + ", " + Converters.countryCodeConverter(weather.location.getCountry());
                cityForecast = weather.location.getCity();
                conditions=weather.conditions.getCondition() + " (" + weather.conditions.getDescr().toUpperCase() + ")";
            }
            catch (Exception e)
            {
                Toast.makeText(WeatherDetails.this, "No Data Found.", Toast.LENGTH_LONG).show();
                finish();
            }

        }

    }


}
