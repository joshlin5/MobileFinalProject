package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Hallway extends AppCompatActivity implements correctDialog.correctDialogListener{

    String previousActivity;
    Button highScoreButton, currentScoreButton, question, questionText, answer, answer1, answer2, answer3, destination;
    int highScore, currentScore;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hallway);
        Intent mIntent = getIntent();
        previousActivity = mIntent.getStringExtra("previousActivity");
        prefs = this.getSharedPreferences("myPrefs.xml", Context.MODE_PRIVATE);
        editor = prefs.edit();
        highScore = prefs.getInt("highScore", -1);
        currentScore = prefs.getInt("currentScore", -1);

        highScoreButton = findViewById(R.id.highScore);
        currentScoreButton = findViewById(R.id.currentScore);
        question = findViewById(R.id.question);
        questionText = findViewById(R.id.questionText);
        answer = findViewById(R.id.highScore);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        destination = findViewById(R.id.destination);

        highScoreButton.setText("High Score: " + highScore);
        currentScoreButton.setText("Current Score: " + currentScore);

        if(previousActivity.equals("main")) {
            String explanation = "You should always pull the mask over your nose so there is less chance you breath in a virus. " +
                    "But most importantly, the mask should cover the mouth so it will stop most of your virus and bacteria you breath out from reaching others.";
            // 1 is correct answer
            answer1.setOnClickListener(v -> {
                String correctAnswer = "Correct Answer! " + explanation;
                currentScore += 1;
                editor.putInt("currentScore", currentScore);
                currentScoreButton.setText("Current Score: " + currentScore);
                correctDialog dialog = new correctDialog(correctAnswer, true);
                dialog.show(getSupportFragmentManager(), "Correct Answer");
            });
            answer2.setOnClickListener(v -> {
                String wrongAnswer = "Wrong Answer! " + explanation;
                correctDialog dialog = new correctDialog(wrongAnswer, false);
                dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
            });
            answer3.setOnClickListener(v -> {
                String wrongAnswer = "Wrong Answer! " + explanation;
                correctDialog dialog = new correctDialog(wrongAnswer, false);
                dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
            });
        } else if (previousActivity.equals("classroom")) {
            // 2 is correct answer


        }
        else if(previousActivity.equals("restroom")) {
            // 3 is correct answer

        }
    }

    @Override
    public void onDialogPositiveClick() {

    }

    @Override
    public void onDialogNegativeClick() {

    }
}