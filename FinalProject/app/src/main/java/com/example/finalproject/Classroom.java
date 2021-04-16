package com.example.finalproject;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Classroom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classroom);
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_LANDSCAPE);
    }
}