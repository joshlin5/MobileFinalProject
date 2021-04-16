package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private UserDatabase mUserDb;
    private UserDao userDao;
    Button buttonStart, buttonAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.startButton);
        buttonAbout = findViewById(R.id.aboutButton);
        buttonStart.setOnClickListener(v -> {
            //Intent intent = new Intent(this, Hallway.class);
            //startActivity(intent);
        });
        buttonAbout.setOnClickListener(v -> {
            //Intent intent = new Intent(this, About.class);
            //startActivity(intent);
        });
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

}