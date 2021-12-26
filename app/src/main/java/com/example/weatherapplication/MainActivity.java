package com.example.weatherapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends Activity {
    private String apiKet = "ac2862a2ad769bc608885cf9a31b072c";
    private String cityURL = "http://api.openweathermap.org/data/2.5/weather?q=";

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
}