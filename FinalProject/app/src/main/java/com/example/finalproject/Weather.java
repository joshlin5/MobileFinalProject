package com.example.finalproject;

import android.Manifest;
import android.annotation.SuppressLint;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.VolleyError;
import android.location.Location;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


import java.util.List;

public class Weather extends AppCompatActivity  {
    private FetchData mFetchData;
    private final int REQUEST_LOCATION_PERMISSIONS = 0;
    private LocationManager locManager = null;
    public FusedLocationProviderClient client;
    private LocationListener locListener = null;
    ImageView background;
    private String desc;
    public String longitude = null;
    public String latitude = null;
    public Intent x;

    private static final String TAG = "Weather";



    private FetchData.OnWeatherReceivedListener mFetchListener =
            new FetchData.OnWeatherReceivedListener(){
            @Override
                public void onDataReceived(List<Weather> data) {

                    Weather tempp = data.get(0);
                    x.putExtra("resource", tempp.desc);
                    setResult(Activity.RESULT_OK,x);
                    Log.d(TAG,"success");
                    finish();
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG,"error");
                }
            };
    @SuppressLint("MissingPermission")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
        background = findViewById(R.id.background);
        x = new Intent();
         client =
                LocationServices.getFusedLocationProviderClient(this);
        if (hasLocationPermission()) {
            findLocation();
        }
        if(longitude == null || latitude == null){
            longitude = "0";
            latitude = "0";
        }

    }

    public void SetDesc(String s) {
        desc = s;
    }

    @SuppressLint("MissingPermission")
    private void findLocation() {
        client.getLastLocation()
                .addOnSuccessListener(this,new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null) {
                            latitude = Double.toString(location.getLatitude());
                            longitude = Double.toString(location.getLongitude());
                         //    Toast toast3 = Toast.makeText(Weather.this, longitude,Toast.LENGTH_SHORT);
                           // toast3.show();
                            Log.d(TAG, "location = " + location);
                            mFetchData = new FetchData(Weather.this);
                            mFetchData.fetchforecast(mFetchListener,longitude,latitude);
                        }
                    }
                });
    }
    private boolean hasLocationPermission() {
        // Request fine location permission if not already granted
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this ,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    REQUEST_LOCATION_PERMISSIONS);
            return false;
        }
        return true;
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Find the location when permission is granted
        if (requestCode == REQUEST_LOCATION_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                findLocation();
            }
        }
    }
}
