package com.example.finalproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserDatabase mUserDb;
    private UserDao userDao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserDb = UserDatabase.getInstance(getApplicationContext());
        UserDao userDao = mUserDb.userDao();

    }

    public List<String> getAllUsers() {
        return userDao.getAllUsers();
    }

    public int getHighScore(String username) {
        return userDao.highScore(username);
    }

    public List<Integer> getAllScores(String username) {
        return userDao.getAllScores(username);
    }

    public void insertUser(User user) {
        userDao.insertUser(user);
    }
    public void onClick(View view) {
        Intent intent = new Intent(this, Weather.class);
        startActivity(intent);
    }
}