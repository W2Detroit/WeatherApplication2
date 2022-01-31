package com.example.weatherapplication;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class WeekCollection {
    public static ArrayList<String> getWeekCollection() {
        ArrayList<String> weekCollection = new ArrayList<>();
        Calendar calendar1 = new GregorianCalendar();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EE, dd");
        weekCollection.add(0,simpleDateformat.format(calendar1.getTime()));
        for (int i = 1; i <= 7; i++) {
          calendar1.add(calendar1.DAY_OF_WEEK, +1);
          Calendar forStore = (Calendar) calendar1.clone();
          weekCollection.add (String.valueOf(simpleDateformat.format(forStore.getTime())));
        }
        return weekCollection;
    }
}
