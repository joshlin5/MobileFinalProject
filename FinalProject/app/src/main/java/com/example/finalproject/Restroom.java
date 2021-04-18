package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Restroom extends AppCompatActivity implements CorrectDialog.correctDialogListener{

    Button highScoreButton, currentScoreButton, question, questionText, answer, answer1, answer2, answer3, destination;
    int highScore, currentScore;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hallway);
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

        // Explanation of correct answer
        String explanation = "You should always wash your hands after using the restroom. " +
                "Make sure to use soap when washing your hands and wash for at least 20 seconds.";

        // 3 is correct answer
        answer1.setOnClickListener(v -> {
            String wrongAnswer = "Wrong Answer! " + explanation;
            currentScore += 1;
            editor.putInt("currentScore", currentScore);
            editor.apply();
            currentScoreButton.setText("Current Score: " + currentScore);
            CorrectDialog dialog = new CorrectDialog(wrongAnswer, false);
            dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
        });
        answer2.setOnClickListener(v -> {
            String wrongAnswer = "Wrong Answer! " + explanation;
            CorrectDialog dialog = new CorrectDialog(wrongAnswer, false);
            dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
        });
        answer3.setOnClickListener(v -> {
            String correctAnswer = "Correct Answer! " + explanation;
            CorrectDialog dialog = new CorrectDialog(correctAnswer, true);
            dialog.show(getSupportFragmentManager(), "Correct Answer");
        });
        destination.setOnClickListener(v -> {
            Intent intent = new Intent(this, Hallway.class);
            // Lets Hallway know what the previous activity was
            intent.putExtra("previousActivity", "restroom");
            startActivity(intent);
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