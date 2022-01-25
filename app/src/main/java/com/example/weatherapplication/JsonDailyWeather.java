package com.example.weatherapplication;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;


public class JsonDailyWeather  {
    private JsonArray daily;
    private JsonObject current;
  //  private List <JsonObject> listDailyWeather;

    JsonDailyWeather () {};
    public JsonObject getCurrent () {
        return current;
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
