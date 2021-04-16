package com.example.finalproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

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

}