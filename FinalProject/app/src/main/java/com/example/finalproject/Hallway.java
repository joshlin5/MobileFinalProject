package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Hallway extends AppCompatActivity {
    String previousActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hallway);
        Intent mIntent = getIntent();
        previousActivity = mIntent.getStringExtra("previousActivity");
    }
}