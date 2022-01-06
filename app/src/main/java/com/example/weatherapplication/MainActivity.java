package com.example.weatherapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    private String apiKey = "&appid=ac2862a2ad769bc608885cf9a31b072c";
    private String cityURL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private TextInputEditText cityInput ;
    private TextView temp_on_celcius;
    private TextView weatherText;
    private int i ;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i = 0;
        cityInput =findViewById(R.id.textInputEditText);
        temp_on_celcius = findViewById(R.id.temperature_on_celcius);
        weatherText = findViewById(R.id.weather);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //Async класс
        StrictMode.setThreadPolicy(policy);
    }
    public String getURL () {
        return (cityURL + cityInput.getText() + "&units=metric" + apiKey);
    }
    public void onClickSearch (View view) throws IOException {
        OkHTTPActivity okHTTPActivity = new OkHTTPActivity();
        String jsonWeather = okHTTPActivity.request(getURL()).toString();
        Thread threadOneDayWeather = new Thread(){
            public void run (){
                    runOnUiThread(new Runnable() { // запуск
                        @Override
                        public void run() {

                                if (!TextUtils.isEmpty(cityInput.getText().toString())) {
                                        GsonBuilder gsonBuilder = new GsonBuilder();
                                        gsonBuilder.setPrettyPrinting();
                                        Gson gson = gsonBuilder.create();
                                        JsonWeather weather = gson.fromJson(jsonWeather, JsonWeather.class);
                                        temp_on_celcius.setText( weather.getMain().get("temp").toString());

                                    //     weatherText.setText(weather.getWeather().getAsJsonArray().get(0).toString());
                                    weatherText.setText(weather.getWeather().get(0).getAsJsonObject().get("main").toString());

                                } else {
                                    Toast checkCity = Toast.makeText(getApplicationContext(),"Пожалуйста введите название населенного пункта", Toast.LENGTH_LONG);
                                    checkCity.show();
                                }
                                System.out.print("Json равен" + jsonWeather);
                        }
                    });
            }
        }; threadOneDayWeather.start();

    }
}



