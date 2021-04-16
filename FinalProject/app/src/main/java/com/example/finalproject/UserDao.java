package com.example.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT MAX(score) FROM User WHERE username = :username")
    public int highScore(String username);

    @Query("SELECT score FROM User WHERE username = :username")
    public List<Integer> getAllScores(String username);

    @Query("SELECT DISTINCT username FROM User")
    public List<String> getAllUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertQuestion(User user);

    @Update
    public void updateQuestion(User user);

    @Delete
    public void deleteQuestion(User user);
}
