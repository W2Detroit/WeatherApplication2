package com.example.weatherapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final String API_KEY = "&appid=ac2862a2ad769bc608885cf9a31b072c";
    private final String CITY_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private final String ICON_URL = "http://openweathermap.org/img/wn/";
    private final String URL_DAILY = "https://api.openweathermap.org/data/2.5/onecall?lat=";
    private final String URL_EXCLUDE = "&exclude=current,minutely,hourly,alerts";
    private final String FINAL_URL_PNG = "@2x.png";
    private TextInputEditText cityInput;
    private TextView weatherText, temp_on_celcius, latitude, longitude, her;
    private ImageView imageWeather;
    private LocationManager locationManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageWeather = findViewById(R.id.imageWeather);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longithude);
        cityInput = findViewById(R.id.textInputEditText);
        temp_on_celcius = findViewById(R.id.temperature_on_celcius);
        weatherText = findViewById(R.id.weather);
        her = findViewById(R.id.her);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); //обеспечивает доступ к системной службе
        // определения местоположения Android;
        //  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 50, this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //Async класс
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast checkGPS = Toast.makeText(getApplicationContext(),"Включите доступ к геоданным в настройках приложения", Toast.LENGTH_LONG);
            checkGPS.show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 50, locationListener);
        checkEnabled();
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            showLocation(location);
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            checkEnabled();
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            checkEnabled();
        }
    };
        public String showLocation (Location location) {
            if (location == null) {
                return null; }
            else if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                longitude.setText(String.valueOf(location.getLongitude()));
                latitude.setText(String.valueOf(location.getLatitude()));
                return (URL_DAILY + String.valueOf(location.getLatitude())
                        + "&lon="+ String.valueOf(location.getLongitude())+
                        URL_EXCLUDE + API_KEY);
            }
            return null;
        }
        private void checkEnabled () {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
    public String getCurrentURL() {
        return (CITY_URL + cityInput.getText() + "&units=metric" + API_KEY);
    }
    public void onClickSearch (View view) throws IOException {
        OkHTTPActivity okHTTPActivity = new OkHTTPActivity();
        String jsonWeather = okHTTPActivity.request(getCurrentURL()).toString();
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
                                        temp_on_celcius.setText(( weather.getMain().get("temp").toString() + "°С"));
                                        weatherText.setText(weather.getWeather().get(0).getAsJsonObject().get("main").getAsString());
                                    //Сначала получаем массив с помощью get(0) и представляем его как JsonObject.
                                    String IdIcon =  weather.getWeather().get(0).getAsJsonObject().get("icon").getAsString();
                                       Picasso.get()
                                               .load(ICON_URL + IdIcon + FINAL_URL_PNG)
                                               .into (imageWeather);


                                } else {
                                    Toast checkCity = Toast.makeText(getApplicationContext(),"Пожалуйста введите название населенного пункта", Toast.LENGTH_LONG);
                                    checkCity.show();
                                }
                        }
                    });
            }
        }; threadOneDayWeather.start();

    }


}



