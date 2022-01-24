package com.example.weatherapplication;

import com.google.gson.JsonArray;
import org.json.JSONObject;


public class JsonDailyWeather  {
    private JsonArray daily;
    private String lat;
  //  private List <JsonObject> listDailyWeather;

    JsonDailyWeather () {};
    public String getlat () {
        return lat;
    }
    public JsonArray getDaily() {
        return daily;
    }
 /*   public List <JsonObject> getListDailyWeather () throws  JSONException {
        List <JsonObject> listDailyWeather = new ArrayList<JsonObject>();
        for (int i = 0; i < 7; i++) {
            listDailyWeather.add(getDaily().get(i).getAsJsonObject());
        }
        return listDailyWeather;
    } */
}
