package com.example.weatherapplication;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

 public class OkHTTPActivity {

    public Object request (String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url (url)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
     public static void main(String[] args) throws IOException {
         OkHTTPActivity okHTTPActivity = new OkHTTPActivity();
         String str = okHTTPActivity.request("http://api.openweathermap.org/data/2.5/weather?q=Ivanovo&appid=ac2862a2ad769bc608885cf9a31b072c").toString();
         System.out.println(str);
     }
}
