package com.example.weatherapplication;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.function.DoubleBinaryOperator;

public class DailyWeather {
    private String day_of_the_week;
    private Integer iconWeather;
    private String dayWeather;
    private String nightWeather;

    public DailyWeather( int iconWeather, String dayWeather, String nightWeather) {
        this.iconWeather = iconWeather;
        this.dayWeather = dayWeather;
        this.nightWeather = nightWeather;
    };



    public String getDayWeather() {
        return dayWeather;
    }

    public void setDayWeather(String dayWeather) {
        this.dayWeather = dayWeather;
    }

    public Integer getIconWeather() {
        return iconWeather;
    }

    public void setIconWeather(Integer iconWeather) {
        this.iconWeather = iconWeather;
    }

    public String getNightWeather() {
        return nightWeather;
    }

    public void setNightWeather(String nightWeather) {
        this.nightWeather = nightWeather;
    }

    public  String getDay_of_the_week() {
        return day_of_the_week;
    }

    public void setDay_of_the_week( String day_of_the_week) {
        this.day_of_the_week = day_of_the_week;
    }
}
