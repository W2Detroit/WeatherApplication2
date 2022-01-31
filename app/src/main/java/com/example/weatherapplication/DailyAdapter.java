package com.example.weatherapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import java.util.ArrayList;
import java.util.List;

public class DailyAdapter extends ArrayAdapter<DailyWeather> {
    private LayoutInflater inflater;
    private int layout;
    private List<DailyWeather> list_dailyWeather;
    private ArrayList <String> listIcons;
    private ArrayList <String> weekCollection;

    public DailyAdapter(@NonNull Context context, int resource, @NonNull List<DailyWeather> dailyWeather,
                        ArrayList <String> listIcons, ArrayList <String> weekCollection) {
        super(context, resource, dailyWeather);
        this.list_dailyWeather = dailyWeather;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.listIcons = listIcons;
        this.weekCollection = weekCollection;
    }
    public View getView (int position, View convertView, ViewGroup parent) {
        View view= inflater.inflate(this.layout, parent, false);

        ImageView img = view.findViewById(R.id.icon_weather);
        PicassoClient.downloadImage(listIcons.get(position), img);
        TextView day_of_the_week = (TextView) view.findViewById(R.id.day_of_the_week);
        TextView day_weather = (TextView) view.findViewById(R.id.day_weather);
        TextView night_weather = (TextView) view.findViewById(R.id.night_weather);
        WeekClient.getItem(listIcons.get(position), day_of_the_week,weekCollection, position);
        DailyWeather  dailyWeather = list_dailyWeather.get(position);

        day_weather.setText(dailyWeather.getDayWeather());
        night_weather.setText(dailyWeather.getNightWeather());

        return view;
    }
}
