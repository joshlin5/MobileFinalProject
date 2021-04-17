package com.example.finalproject;

import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.List;


public class Location implements LocationListener {

    public double longitude;
    public double latitude;
    private Weather mWeather;



    @Override
    public void onLocationChanged(android.location.Location loc) {
        mWeather = new Weather();
        longitude = loc.getLongitude();

        latitude = loc.getLatitude();
        mWeather.setLatitude(latitude);
        mWeather.setLongitude(longitude);



    }


    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider,
                                int status, Bundle extras) {
        // TODO Auto-generated method stub
    }



}
