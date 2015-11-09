package com.project.mobilecomputing.weathernow;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mobilecomputing.weathernow.helpers.Converters;
import com.project.mobilecomputing.weathernow.helpers.JSONParser;
import com.project.mobilecomputing.weathernow.helpers.WeatherClient;
import com.project.mobilecomputing.weathernow.models.WeatherData;

import org.json.JSONException;
import org.w3c.dom.Text;

public class WeatherDetails extends Activity {
    RelativeLayout weatherLayout;
    TextView tempTextView;
    TextView conditionsTextView;
    TextView pressureTextView;
    TextView sunriseTextView;
    TextView sunsetTextView;
    TextView cityText;
    Button forecastButton;
    Integer mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

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
            else if(mode==1) {
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
                conditionsTextView.setText(weather.conditions.getCondition().toUpperCase() + "(" + weather.conditions.getDescr().toUpperCase() + ")");
                tempTextView.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "° C");//Converting to celsius
                pressureTextView.setText("" + weather.conditions.getPressure() + " hPa");
                sunriseTextView.setText(Converters.timeStampConverter(weather.location.getSunrise())+"");
                sunsetTextView.setText(Converters.timeStampConverter(weather.location.getSunset())+"");

            }
            catch (Exception e)
            {
                Toast.makeText(WeatherDetails.this, "No Data Found.", Toast.LENGTH_LONG).show();
                finish();
            }

        }

    }


}
