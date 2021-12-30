package com.example.weatherapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {
    private String apiKey = "&appid=ac2862a2ad769bc608885cf9a31b072c";
    private String cityURL = "http://api.openweathermap.org/data/2.5/weather?q=";
    public String city ;
    private TextInputEditText cityInput ;
    private TextInputEditText codeInput;
    private TextView textView;
    private Button search;
    private String jsonWeather;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityInput =findViewById(R.id.textInputEditText);
        codeInput = findViewById(R.id.textInputEditText2);
        textView = findViewById(R.id.infWeather);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //Async класс
        StrictMode.setThreadPolicy(policy);
    }
    public String getURL () {
        return (cityURL + cityInput.getText() + "&units=metric" + apiKey);
    }
    public void onClickSearch (View view)  {
        Thread thread = new Thread(){
            public void run (){
                    runOnUiThread(new Runnable() { // запуск
                        @Override
                        public void run() {
                            try {
                                if (!TextUtils.isEmpty(cityInput.getText().toString())) {
                                    OkHTTPActivity okHTTPActivity = new OkHTTPActivity();
                                    String jsonWeather = okHTTPActivity.request(getURL()).toString();

                                        GsonBuilder gsonBuilder = new GsonBuilder();
                                        gsonBuilder.setPrettyPrinting();
                                        Gson gson = gsonBuilder.create();
                                        JsonWeather weather = gson.fromJson(jsonWeather, JsonWeather.class);
                                        jsonWeather = gson.toJson(weather);
                                       textView.setText( weather.getMain().get("temp").toString());


                                } else {
                                    Toast checkCity = Toast.makeText(getApplicationContext(),"Пожалуйста введите название населенного пункта", Toast.LENGTH_LONG);
                                    checkCity.show();
                                }
                                System.out.print("Json равен" + jsonWeather);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            }
        }; thread.start();
    }
}



