package com.example.weatherapplication;

import java.util.ArrayList;

public class PicassoCollection {

    public  static ArrayList<String> getListIconWeather (JsonDailyWeather weather) {
        ArrayList <String> iconWeather = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            iconWeather.add("https://openweathermap.org/img/wn/" + weather.getDaily().get(i).getAsJsonObject()
                    .get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString()
            +"@2x.png");
        }
        return iconWeather;
    }
}
