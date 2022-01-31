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
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String API_KEY = "&appid=ac2862a2ad769bc608885cf9a31b072c";
    private final String CITY_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    private final String ICON_URL = "https://openweathermap.org/img/wn/";
    private final String URL_CURRENT = "https://api.openweathermap.org/data/2.5/weather?lat=";
    private final String URL_DAILY = "https://api.openweathermap.org/data/2.5/onecall?lat=";
    private final String URL_EXCLUDE = "&exclude=minutely,hourly,alerts";
    private final String FINAL_URL_PNG = "@2x.png";
    private TextInputEditText cityInput;
    private TextView weatherText, temp_on_celcius, name_city,her;
    private ImageView imageWeather,icon_weather;
    private LocationManager locationManager;
    private List<DailyWeather> dailyWeathers = new ArrayList();
    private ArrayList <String> day_of_week = new ArrayList();
    private ListView list_of_days;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageWeather = findViewById(R.id.imageWeather);
        cityInput = findViewById(R.id.textInputEditText);
        temp_on_celcius = findViewById(R.id.temperature_on_celcius);
        weatherText = findViewById(R.id.weather);
        name_city = findViewById(R.id.name_city);
        icon_weather = findViewById(R.id.icon_weather);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); //обеспечивает доступ к системной службе
        // определения местоположения Android;
        list_of_days = findViewById(R.id.list_of_days);
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

    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            try {
                showLocation(location);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            checkEnabled();
            try {
                showLocation(locationManager.getLastKnownLocation(provider));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            checkEnabled();
        }
    };

    public void getCurrentWeather (String jsonWeather) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
            JsonWeather weather =gson.fromJson(jsonWeather, JsonWeather.class);
            temp_on_celcius.setText(( weather.getMain().get("temp").toString() + "°С"));
            weatherText.setText(weather.getWeather().get(0).getAsJsonObject().get("main").getAsString());
            name_city.setText(weather.getName());
            //Сначала получаем массив с помощью get(0) и представляем его как JsonObject.
            String IdIcon =  weather.getWeather().get(0).getAsJsonObject().get("icon").getAsString();
            Picasso.get()
                    .load(ICON_URL + IdIcon + FINAL_URL_PNG)
                    .into (imageWeather);
        }

        public List <DailyWeather> getLatLonWeather (String jsonWeather)   {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            Gson gson = gsonBuilder.create();
            JsonDailyWeather weather = gson.fromJson(jsonWeather, JsonDailyWeather.class);
            Calendar calendar = new GregorianCalendar();
            calendar.get(Calendar.DAY_OF_WEEK);
            calendar.roll(calendar.get(Calendar.DAY_OF_WEEK),+1);
            for (Integer i = 0; i <= 7; i++) {
                dailyWeathers.add (i, new DailyWeather(

                        R.drawable.ic_launcher_background,
                        weather.getDaily().get(i).getAsJsonObject().get("temp").getAsJsonObject().get("day").getAsString(),
                        weather.getDaily().get(i).getAsJsonObject().get("temp").getAsJsonObject().get("night").getAsString()
                        ));
            }
            DailyAdapter adapter = new DailyAdapter(this, R.layout.list_item,dailyWeathers,
                    PicassoCollection.getListIconWeather(weather), WeekCollection.getWeekCollection());
            list_of_days.setAdapter(adapter);
            return dailyWeathers;
        }
        public String getLat (Location location) {
            return String.valueOf(location.getLatitude());
        }
        public String getLon (Location location) {
            return String.valueOf(location.getLongitude());
        }
        public void showLocation (Location location) throws IOException{
            if (location == null) {
                return; }
            else if (location.getProvider().equals(LocationManager.GPS_PROVIDER))  {
                OkHTTPActivity okHTTPActivity = new OkHTTPActivity();
                String dailyJsonWeather = (String) okHTTPActivity.request ((URL_DAILY + getLat(location)
                        + "&lon="+ getLon(location) +"&units=metric"+
                        URL_EXCLUDE + API_KEY));
              String currentJsonWeather = (String) okHTTPActivity.request(URL_CURRENT +getLat(location) + "&lon=" + getLon(location)+
                      "&units=metric" + API_KEY);
                Thread threadGPSWeather = new Thread(){
                    public void run (){
                        runOnUiThread(new Runnable() { // запуск
                            @Override
                            public void run() {
                                getLatLonWeather(dailyJsonWeather);
                                getCurrentWeather(currentJsonWeather);
                            }
                        });
                    }
                }; threadGPSWeather.start();
            }
            return;
        }
    public void checkEnabled () {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
    public String getCurrentURL() {
        return (CITY_URL + cityInput.getText() + "&units=metric" + API_KEY);
    }

    public void onClickSearch (View view) throws IOException {
        OkHTTPActivity okHTTPActivity = new OkHTTPActivity();
        String currentJsonWeather = (String) okHTTPActivity.request(getCurrentURL());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        JsonWeather weather = gson.fromJson(currentJsonWeather, JsonWeather.class);
        String dailyJsonWeather = (String) okHTTPActivity.request(URL_DAILY + weather.getCoord().get("lat").toString()
                + "&lon=" + weather.getCoord().get("lon").toString() + "&units=metric" + URL_EXCLUDE + API_KEY);
        Thread threadOneDayWeather = new Thread(){
            public void run (){
                    runOnUiThread(new Runnable() { // запуск
                        @Override
                        public void run() {

                                if (!TextUtils.isEmpty(cityInput.getText().toString()))
                                 { if (getLatLonWeather(dailyJsonWeather).size() == 7) {
                                       getCurrentWeather(currentJsonWeather);
                                       getLatLonWeather(dailyJsonWeather);

                                } else if (getLatLonWeather(dailyJsonWeather).size() > 7){
                                     getLatLonWeather(dailyJsonWeather).clear();
                                     getCurrentWeather(currentJsonWeather);
                                     getLatLonWeather(dailyJsonWeather); }
                                 } else  {
                                    Toast checkCity = Toast.makeText(getApplicationContext(),"Пожалуйста введите название населенного пункта", Toast.LENGTH_LONG);
                                    checkCity.show();
                                }
                        }
                    });
            }
        }; threadOneDayWeather.start();

    }


}



