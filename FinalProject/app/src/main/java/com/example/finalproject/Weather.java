package com.example.finalproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.VolleyError;

import java.util.List;

public class Weather extends AppCompatActivity {
    private FetchData mFetchData;
    private LocationManager locManager = null;
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

                    if(tempp.desc.equals("rain") || tempp.desc.equals("shower rain"))
                        background.setImageResource(R.drawable.rain);

                    else
                        background.setImageResource(R.drawable.schoolcloudy);

                    Log.d(TAG,"success");
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG,"error");
                }


            };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.weather);
        locManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        locListener = new Location();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locListener);

        mFetchData = new FetchData(this);


        mFetchData.fetchforecast(mFetchListener,longitude,latitude);


    }
    public void setLongitude(double s) {
       // longitude = Double.toString(s);
        longitude = "-82.8595922";

    }

    public void setLatitude(double s) {
       // latitude = Double.toString(s);
        latitude = "34.6556779";

    }
    public void SetDesc(String s) {
        desc = s;
    }


}
