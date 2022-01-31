package com.example.weatherapplication;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoClient {

    public static void downloadImage(String url, ImageView img)
    {
            Picasso.get().load(url).into(img);
    }

}