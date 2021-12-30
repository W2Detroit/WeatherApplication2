package com.example.weatherapplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonWeather {
    private Map<String, Object> main;
    JsonWeather (){};
    public Map<String, Object> getMain() {
        return main;
    }
}
