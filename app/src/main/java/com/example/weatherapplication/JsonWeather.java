package com.example.weatherapplication;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonWeather extends JSONObject {
    private JsonArray weather;
    private JsonObject main;
    JsonWeather (){};
    public JsonObject getMain() {
        return main;
    }
    public JsonArray getWeather() { return weather; }
}
