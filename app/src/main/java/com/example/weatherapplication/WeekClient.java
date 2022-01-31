package com.example.weatherapplication;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WeekClient {
    private TextView textView;
    public static void getItem (String position, TextView textView, ArrayList <String> weekCollection, int i) {
        textView.findViewById(R.id.day_of_the_week);
        textView.setText(weekCollection.get(i));
    }
}
