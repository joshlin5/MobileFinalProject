package com.example.finalproject;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


public class FetchData extends Application {

    private RequestQueue mRequestQueue;
    private final String WEBAPI_BASE_URL = "https://api.openweathermap.org/data/2.5/weather?units=imperial&appid=6a10b16fd379129835d433d917aaf006";
    private final String TAG = "FetchData";



    //function
    public interface OnWeatherReceivedListener {
        void onDataReceived(List<Weather> data);
        void onErrorResponse(VolleyError error);
    }
    public FetchData(Context context){
        mRequestQueue = Volley.newRequestQueue(context);

    }




    public void fetchforecast(final OnWeatherReceivedListener listener, String lon, String lat) {

        String url = Uri.parse(WEBAPI_BASE_URL).buildUpon()
                .appendQueryParameter("lat", lat)
                .appendQueryParameter("lon",lon).build().toString();




        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG,response.toString());
                        List<Weather> data = parseJsonData(response);
                        listener.onDataReceived(data);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onErrorResponse(error);
                    }
                });

        mRequestQueue.add(request);
    }

    private List<Weather> parseJsonData(JSONObject json) {

        // Create a list of subjects
        List<Weather> data = new ArrayList<>();

        try {
            JSONObject dataObj = json.getJSONObject("main");
            JSONArray weatherArray = json.getJSONArray("weather");
            Weather field = new Weather();

            JSONObject subjectObj = weatherArray.getJSONObject(0);

            field.SetDesc(subjectObj.getString("description"));
            data.add(field);


        }
        catch (Exception e) {
            Log.e(TAG, "One or more fields not found in the JSON data");
        }


        return data;
    }



}
