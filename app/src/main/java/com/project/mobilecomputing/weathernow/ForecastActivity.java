package com.project.mobilecomputing.weathernow;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mobilecomputing.weathernow.helpers.Converters;
import com.project.mobilecomputing.weathernow.helpers.JSONParser;
import com.project.mobilecomputing.weathernow.helpers.WeatherClient;
import com.project.mobilecomputing.weathernow.models.ForecastData;

import org.json.JSONException;

public class ForecastActivity extends Activity {
    TextView cityTextForecast;
    ImageView dayoneimage;
    ImageView daytwoimage;
    ImageView daythreeimage;
    ImageView dayfourimage;
    ImageView dayfiveimage;

    TextView dateone;
    TextView datetwo;
    TextView datethree;
    TextView datefour;
    TextView datefive;

    TextView tempone;
    TextView temptwo;
    TextView tempthree;
    TextView tempfour;
    TextView tempfive;

    TextView condone;
    TextView condtwo;
    TextView condthree;
    TextView condfour;
    TextView condfive;

    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#06272E")));

        cityTextForecast = (TextView) findViewById(R.id.citytextforecast);

        dayoneimage = (ImageView) findViewById(R.id.dayoneimage);
        daytwoimage = (ImageView) findViewById(R.id.daytwoimage);
        daythreeimage = (ImageView) findViewById(R.id.daythreeimage);
        dayfourimage = (ImageView) findViewById(R.id.dayfourimage);
        dayfiveimage = (ImageView) findViewById(R.id.dayfiveimage);

        dateone = (TextView) findViewById(R.id.dateone);
        datetwo = (TextView) findViewById(R.id.datetwo);
        datethree = (TextView) findViewById(R.id.datethree);
        datefour = (TextView) findViewById(R.id.datefour);
        datefive = (TextView) findViewById(R.id.datefive);

        tempone = (TextView) findViewById(R.id.tempone);
        temptwo = (TextView) findViewById(R.id.temptwo);
        tempthree = (TextView) findViewById(R.id.tempthree);
        tempfour = (TextView) findViewById(R.id.tempfour);
        tempfive = (TextView) findViewById(R.id.tempfive);

        condone = (TextView) findViewById(R.id.condone);
        condtwo = (TextView) findViewById(R.id.condtwo);
        condthree = (TextView) findViewById(R.id.condthree);
        condfour = (TextView) findViewById(R.id.condfour);
        condfive = (TextView) findViewById(R.id.condfive);

        city = getIntent().getStringExtra("cityforecast");
        cityTextForecast.setText(city);

        try {
            JSONWeatherTask task = new JSONWeatherTask();
            task.execute(city.replaceAll("\\s", "%20"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * Class to call API and populate screen in an Async Manner.
     */
    private class JSONWeatherTask extends AsyncTask<String, Void, ForecastData> {

        @Override
        protected ForecastData doInBackground(String... params) {
            ForecastData forecast = new ForecastData();
            String data = "";
            data = ((new WeatherClient()).getForecastData(params[0]));

            try {
                forecast = JSONParser.getForecastData(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return forecast;
        }


        @Override

        protected void onPostExecute(ForecastData forecast) {

            super.onPostExecute(forecast);
            try {
                /**
                 * Set all data on screen based on response
                 */
                dateone.setText(Converters.timeStampConverterForDate(forecast.getFiveDayForecast().get(0).getLongDate()));
                datetwo.setText(Converters.timeStampConverterForDate(forecast.getFiveDayForecast().get(1).getLongDate()));
                datethree.setText(Converters.timeStampConverterForDate(forecast.getFiveDayForecast().get(2).getLongDate()));
                datefour.setText(Converters.timeStampConverterForDate(forecast.getFiveDayForecast().get(3).getLongDate()));
                datefive.setText(Converters.timeStampConverterForDate(forecast.getFiveDayForecast().get(4).getLongDate()));

                tempone.setText("Min: " + forecast.getFiveDayForecast().get(0).getTemperature().getMinTemp()
                        + "° C | Max: " + forecast.getFiveDayForecast().get(0).getTemperature().getMaxTemp() + "° C");

                temptwo.setText("Min: " + forecast.getFiveDayForecast().get(1).getTemperature().getMinTemp()
                        + "° C | Max: " + forecast.getFiveDayForecast().get(1).getTemperature().getMaxTemp() + "° C");

                tempthree.setText("Min: " + forecast.getFiveDayForecast().get(2).getTemperature().getMinTemp()
                        + "° C | Max: " + forecast.getFiveDayForecast().get(2).getTemperature().getMaxTemp() + "° C");

                tempfour.setText("Min: " + forecast.getFiveDayForecast().get(3).getTemperature().getMinTemp()
                        + "° C | Max: " + forecast.getFiveDayForecast().get(3).getTemperature().getMaxTemp() + "° C");

                tempfive.setText("Min: " + forecast.getFiveDayForecast().get(4).getTemperature().getMinTemp()
                        + "° C | Max: " + forecast.getFiveDayForecast().get(4).getTemperature().getMaxTemp() + "° C");

                condone.setText(forecast.getFiveDayForecast().get(0).getWeather().toUpperCase());
                condtwo.setText(forecast.getFiveDayForecast().get(1).getWeather().toUpperCase());
                condthree.setText(forecast.getFiveDayForecast().get(2).getWeather().toUpperCase());
                condfour.setText(forecast.getFiveDayForecast().get(3).getWeather().toUpperCase());
                condfive.setText(forecast.getFiveDayForecast().get(4).getWeather().toUpperCase());

                //Section for Images
                String condition1 = forecast.getFiveDayForecast().get(0).getWeather();
                String condition2 = forecast.getFiveDayForecast().get(1).getWeather();
                String condition3 = forecast.getFiveDayForecast().get(2).getWeather();
                String condition4 = forecast.getFiveDayForecast().get(3).getWeather();
                String condition5 = forecast.getFiveDayForecast().get(4).getWeather();

                //Condition 1

                if (condition1.contains("clear")) {
                    dayoneimage.setBackgroundResource(R.drawable.sunicon);
                } else if (condition1.contains("rain")) {
                    dayoneimage.setBackgroundResource(R.drawable.rainicon);
                } else if (condition1.contains("storm")) {
                    dayoneimage.setBackgroundResource(R.drawable.stormicon);
                } else if (condition1.contains("cloud")) {
                    dayoneimage.setBackgroundResource(R.drawable.cloudsicon);
                } else if (condition1.contains("snow")) {
                    dayoneimage.setBackgroundResource(R.drawable.snowicon);
                } else if (condition1.contains("haze")) {
                    dayoneimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition1.contains("mist")) {
                    dayoneimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition1.contains("fog")) {
                    dayoneimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition1.contains("smoke")) {
                    dayoneimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition1.contains("dust")) {
                    dayoneimage.setBackgroundResource(R.drawable.fogicon);
                } else {
                    dayoneimage.setBackgroundResource(R.drawable.weathericon);
                }

                //Condition 2

                if (condition2.contains("clear")) {
                    daytwoimage.setBackgroundResource(R.drawable.sunicon);
                } else if (condition2.contains("rain")) {
                    daytwoimage.setBackgroundResource(R.drawable.rainicon);
                } else if (condition2.contains("storm")) {
                    daytwoimage.setBackgroundResource(R.drawable.stormicon);
                } else if (condition2.contains("cloud")) {
                    daytwoimage.setBackgroundResource(R.drawable.cloudsicon);
                } else if (condition2.contains("snow")) {
                    daytwoimage.setBackgroundResource(R.drawable.snowicon);
                } else if (condition2.contains("haze")) {
                    daytwoimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition2.contains("mist")) {
                    daytwoimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition2.contains("fog")) {
                    daytwoimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition2.contains("smoke")) {
                    daytwoimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition2.contains("dust")) {
                    daytwoimage.setBackgroundResource(R.drawable.fogicon);
                } else {
                    daytwoimage.setBackgroundResource(R.drawable.weathericon);
                }

                //Condition 3

                if (condition3.contains("clear")) {
                    daythreeimage.setBackgroundResource(R.drawable.sunicon);
                } else if (condition3.contains("rain")) {
                    daythreeimage.setBackgroundResource(R.drawable.rainicon);
                } else if (condition3.contains("storm")) {
                    daythreeimage.setBackgroundResource(R.drawable.stormicon);
                } else if (condition3.contains("cloud")) {
                    daythreeimage.setBackgroundResource(R.drawable.cloudsicon);
                } else if (condition3.contains("snow")) {
                    daythreeimage.setBackgroundResource(R.drawable.snowicon);
                } else if (condition3.contains("haze")) {
                    daythreeimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition3.contains("mist")) {
                    daythreeimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition3.contains("fog")) {
                    daythreeimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition3.contains("smoke")) {
                    daythreeimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition3.contains("dust")) {
                    daythreeimage.setBackgroundResource(R.drawable.fogicon);
                } else {
                    daythreeimage.setBackgroundResource(R.drawable.weathericon);
                }

                //Condition 4

                if (condition4.contains("clear")) {
                    dayfourimage.setBackgroundResource(R.drawable.sunicon);
                } else if (condition4.contains("rain")) {
                    dayfourimage.setBackgroundResource(R.drawable.rainicon);
                } else if (condition4.contains("storm")) {
                    dayfourimage.setBackgroundResource(R.drawable.stormicon);
                } else if (condition4.contains("cloud")) {
                    dayfourimage.setBackgroundResource(R.drawable.cloudsicon);
                } else if (condition4.contains("snow")) {
                    dayfourimage.setBackgroundResource(R.drawable.snowicon);
                } else if (condition4.contains("haze")) {
                    dayfourimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition4.contains("mist")) {
                    dayfourimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition4.contains("fog")) {
                    dayfourimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition4.contains("smoke")) {
                    dayfourimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition4.contains("dust")) {
                    dayfourimage.setBackgroundResource(R.drawable.fogicon);
                } else {
                    dayfourimage.setBackgroundResource(R.drawable.weathericon);
                }

                //Condition 5

                if (condition5.contains("clear")) {
                    dayfiveimage.setBackgroundResource(R.drawable.sunicon);
                } else if (condition5.contains("rain")) {
                    dayfiveimage.setBackgroundResource(R.drawable.rainicon);
                } else if (condition5.contains("storm")) {
                    dayfiveimage.setBackgroundResource(R.drawable.stormicon);
                } else if (condition5.contains("cloud")) {
                    dayfiveimage.setBackgroundResource(R.drawable.cloudsicon);
                } else if (condition5.contains("snow")) {
                    dayfiveimage.setBackgroundResource(R.drawable.snowicon);
                } else if (condition5.contains("haze")) {
                    dayfiveimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition5.contains("mist")) {
                    dayfiveimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition5.contains("fog")) {
                    dayfiveimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition5.contains("smoke")) {
                    dayfiveimage.setBackgroundResource(R.drawable.fogicon);
                } else if (condition5.contains("dust")) {
                    dayfiveimage.setBackgroundResource(R.drawable.fogicon);
                } else {
                    dayfiveimage.setBackgroundResource(R.drawable.weathericon);
                }

            } catch (Exception e) {
                Toast.makeText(ForecastActivity.this, "No Data Found.", Toast.LENGTH_LONG).show();
                finish();
            }

        }

    }

}
