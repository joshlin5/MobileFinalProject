package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Cafeteria extends AppCompatActivity implements CorrectDialog.correctDialogListener  {
    Button highScoreButton, currentScoreButton, question, questionText, answer, answer1, answer2, answer3, destination, usernameDisplay;
    int highScore, currentScore;
    String username;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cafeteria);
        // Initializing
        prefs = this.getSharedPreferences("myPrefs.xml", Context.MODE_PRIVATE);
        editor = prefs.edit();

        // Initializing buttons
        highScoreButton = findViewById(R.id.highScore);
        currentScoreButton = findViewById(R.id.currentScore);
        question = findViewById(R.id.question);
        questionText = findViewById(R.id.questionText);
        answer = findViewById(R.id.answer);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        destination = findViewById(R.id.destination);
        usernameDisplay = findViewById(R.id.usernameDisplay);

        // Setting correct visibility
        question.setVisibility(View.VISIBLE);
        questionText.setVisibility(View.VISIBLE);
        answer.setVisibility(View.VISIBLE);
        answer1.setVisibility(View.VISIBLE);
        answer2.setVisibility(View.VISIBLE);
        answer3.setVisibility(View.VISIBLE);
        destination.setVisibility(View.INVISIBLE);

        // Displaying high score and current score
        highScore = prefs.getInt("highScore", -1);
        currentScore = prefs.getInt("currentScore", -1);
        highScoreButton.setText("High Score: " + highScore);
        currentScoreButton.setText("Current Score: " + currentScore);

        username = prefs.getString("username", "ERROR");
        usernameDisplay.setText("Username: " + username);


        String explanation = "While inside you mask should always be on except when eating. If your mask is not on then you must keep at least 3ft from others. " +
                "Even with your mask on you should try to keep distance from others";
        // 1 is correct answer
        answer1.setOnClickListener(v -> {
            String correctAnswer = "Correct Answer! " + explanation;
            currentScore += 1;
            editor.putInt("currentScore", currentScore);
            editor.apply();
            currentScoreButton.setText("Current Score: " + currentScore);
            CorrectDialog dialog = new CorrectDialog(correctAnswer, true);
            dialog.show(getSupportFragmentManager(), "Correct Answer");
        });
        answer2.setOnClickListener(v -> {
            String wrongAnswer = "Wrong Answer! " + explanation;
            CorrectDialog dialog = new CorrectDialog(wrongAnswer, false);
            dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
        });
        answer3.setOnClickListener(v -> {
            String wrongAnswer = "Wrong Answer! " + explanation;
            CorrectDialog dialog = new CorrectDialog(wrongAnswer, false);
            dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
        });
        destination.setOnClickListener(v -> {
            //Intent intent = new Intent(this, Hallway.class);
            //intent.putExtra("previousActivity", "classroom");
            //startActivity(intent);
        });

    }

    @Override
    public void onDialogPositiveClick() {
        question.setVisibility(View.INVISIBLE);
        questionText.setVisibility(View.INVISIBLE);
        answer.setVisibility(View.INVISIBLE);
        answer1.setVisibility(View.INVISIBLE);
        answer2.setVisibility(View.INVISIBLE);
        answer3.setVisibility(View.INVISIBLE);
        destination.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDialogNegativeClick() {

    }

}
