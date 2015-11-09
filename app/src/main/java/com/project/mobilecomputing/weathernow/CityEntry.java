package com.project.mobilecomputing.weathernow;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CityEntry extends Activity {
    EditText cityEditText;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_entry);
        cityEditText=(EditText)findViewById(R.id.cityEditText);
        nextButton=(Button)findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cityEditText.getText().length()==0)
                {
                    Toast.makeText(CityEntry.this, "Please Enter A City Name", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent weatherIntent = new Intent(CityEntry.this, WeatherDetails.class);
                    weatherIntent.putExtra("mode",0);//City Mode.
                    weatherIntent.putExtra("city",cityEditText.getText().toString().replaceAll("\\s","%20"));
                    startActivity(weatherIntent);

                }
            }
        });
    }
}
