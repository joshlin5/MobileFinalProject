package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo(name = "username")
    private String mUsername;

    @ColumnInfo(name = "score")
    private int mScore;

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public int getScore() {
        return mScore;
    }

    public void getScore(int score) {
        mScore = score;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getUsername() {
        return mUsername;
    }
}

