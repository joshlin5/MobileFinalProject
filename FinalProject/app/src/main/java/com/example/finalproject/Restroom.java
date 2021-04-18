package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Restroom extends AppCompatActivity implements CorrectDialog.correctDialogListener{

    Button highScoreButton, currentScoreButton, question, questionText, answer, answer1, answer2, answer3, destination, usernameDisplay;
    int highScore, currentScore;
    String username;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    boolean second = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restroom);
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

        questionText.setText("You are about to cough but you do not have a mask on. What do you do?");
        answer1.setText("Cough as loud as you can so everyone knows you coughed");
        answer2.setText("Cover your mouth with your hands and cough");
        answer3.setText("Cover your mouth with your elbow and cough");

        // Explanation after they choose an answer
        String explanation = "When coughing, make sure to cough into your elbow and wash your hands and elbows as soon as possible after. " +
                "It's best not to cough into your hands because you could easily touch something else with you hands and transfer the germs.";

        // 3 is correct answer
        answer1.setOnClickListener(v -> {
            // Displays wrong answer and game over
            String wrongAnswer = "Wrong Answer! " + explanation;
            CorrectDialog dialog = new CorrectDialog(wrongAnswer, false);
            dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
        });
        answer2.setOnClickListener(v -> {
            // Displays wrong answer and game over
            String correctAnswer = "Wrong Answer! " + explanation;
            CorrectDialog dialog = new CorrectDialog(correctAnswer, false);
            dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
        });
        answer3.setOnClickListener(v -> {
            // Updates correct answer and gives explanation
            String wrongAnswer = "Correct Answer! " + explanation;
            currentScore += 1;
            editor.putInt("currentScore", currentScore);
            editor.apply();
            currentScoreButton.setText("Current Score: " + currentScore);
            // Displays dialog
            CorrectDialog dialog = new CorrectDialog(wrongAnswer, true);
            dialog.show(getSupportFragmentManager(), "Correct Answer");
        });
    }

    @Override
    public void onDialogPositiveClick() {
        if(second) {
            // After the dialog disappears, turn everything invisible and just display the next destination button
            question.setVisibility(View.INVISIBLE);
            questionText.setVisibility(View.INVISIBLE);
            answer.setVisibility(View.INVISIBLE);
            answer1.setVisibility(View.INVISIBLE);
            answer2.setVisibility(View.INVISIBLE);
            answer3.setVisibility(View.INVISIBLE);
            destination.setVisibility(View.VISIBLE);
        }
        else {
            second = true;
            questionText.setText("You use the bathroom. What do you do next?");
            answer1.setText("You leave the restroom");
            answer3.setText("You wash your hands with water for 20 seconds before leaving");
            answer2.setText("You wash your hands with soap and water for 20 seconds before leaving");

            // Explanation of correct answer
            String explanation = "You should always wash your hands after using the restroom. " +
                    "Make sure to use soap when washing your hands and wash for at least 20 seconds.";

            // 2 is correct answer
            answer1.setOnClickListener(v -> {
                String wrongAnswer = "Wrong Answer! " + explanation;
                CorrectDialog dialog = new CorrectDialog(wrongAnswer, false);
                dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
            });
            answer3.setOnClickListener(v -> {
                String wrongAnswer = "Wrong Answer! " + explanation;
                CorrectDialog dialog = new CorrectDialog(wrongAnswer, false);
                dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
            });
            answer2.setOnClickListener(v -> {
                String correctAnswer = "Correct Answer! " + explanation;
                currentScore += 1;
                editor.putInt("currentScore", currentScore);
                editor.apply();
                currentScoreButton.setText("Current Score: " + currentScore);
                CorrectDialog dialog = new CorrectDialog(correctAnswer, true);
                dialog.show(getSupportFragmentManager(), "Correct Answer");
            });
            destination.setOnClickListener(v -> {
                // Starts Cafeteria activity
                Intent intent = new Intent(this, Hallway.class);
                intent.putExtra("previousActivity", "restroom");
                startActivity(intent);
            });
        }
    }

    @Override
    public void onDialogNegativeClick() {
        // After the dialog disappears, turn everything invisible and just display the next destination button
        onDialogPositiveClick();
    }
}