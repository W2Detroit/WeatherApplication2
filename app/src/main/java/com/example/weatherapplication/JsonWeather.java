package com.example.weatherapplication;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONObject;

public class JsonWeather extends JSONObject {
    private JsonArray weather;
    private String name;
    private JsonObject main;
    private JsonObject coord;
    JsonWeather (){};

    public JsonObject getCoord() { return coord; }
    public JsonObject getMain() {
        return main;
    }
    public JsonArray getWeather() { return weather; }
    public String getName () {return name;}
}
