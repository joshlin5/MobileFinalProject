package com.example.finalproject;

import android.Manifest;
import android.annotation.SuppressLint;

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

    private static final String TAG = "Weather";



    private FetchData.OnWeatherReceivedListener mFetchListener =
            new FetchData.OnWeatherReceivedListener(){
            @Override
                public void onDataReceived(List<Weather> data) {

                    Weather tempp = data.get(0);
                    if(tempp.desc.equals("clear sky"))
                        background.setImageResource(R.drawable.school);

                    else if(tempp.desc.equals("rain") || tempp.desc.equals("shower rain"))
                        background.setBackgroundResource(R.drawable.rain);
                    else
                        background.setImageResource(R.drawable.schoolcloudy);
                    Log.d(TAG,"success");
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
         client =
                LocationServices.getFusedLocationProviderClient(this);
        if (hasLocationPermission()) {
            Toast toast = Toast.makeText(Weather.this, "going in",Toast.LENGTH_SHORT);
            toast.show();
            findLocation();
        }

        if(longitude == null || latitude == null){
            Toast toast = Toast.makeText(Weather.this, "Fuck!",Toast.LENGTH_SHORT);
            toast.show();
            longitude = "34.595583";
            latitude = "-82.681513";

        }


        mFetchData = new FetchData(this);
        mFetchData.fetchforecast(mFetchListener,longitude,latitude);
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
                            // Toast toast = Toast.makeText(Weather.this, longitude,Toast.LENGTH_SHORT);
                            //toast.show();
                            Log.d(TAG, "location = " + location);


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
                client.getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                latitude = Double.toString(location.getLatitude());
                                longitude = Double.toString(location.getLongitude());


                            }
                        });
            }
        }
    }


}
