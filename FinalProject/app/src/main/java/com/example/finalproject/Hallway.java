package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public class Hallway extends AppCompatActivity implements CorrectDialog.correctDialogListener{

    // Previous activity's name, determines question shown
    String previousActivity, username;
    Button highScoreButton, currentScoreButton, question, questionText, answer, answer1, answer2, answer3, destination, usernameDisplay;
    int highScore, currentScore;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hallway);

        // Initializing shared pref file and editor
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
    }

    @Override
    public void onResume(){
        super.onResume();

        // Getting previous activity's name from Intent
        Intent mIntent = getIntent();
        previousActivity = mIntent.getStringExtra("previousActivity");

        // Setting all buttons to visible except the destination button
        question.setVisibility(View.VISIBLE);
        questionText.setVisibility(View.VISIBLE);
        answer.setVisibility(View.VISIBLE);
        answer1.setVisibility(View.VISIBLE);
        answer2.setVisibility(View.VISIBLE);
        answer3.setVisibility(View.VISIBLE);
        destination.setVisibility(View.INVISIBLE);

        // Display high score and current score text
        highScore = prefs.getInt("highScore", -1);
        currentScore = prefs.getInt("currentScore", -1);
        highScoreButton.setText("High Score: " + highScore);
        currentScoreButton.setText("Current Score: " + currentScore);

        username = prefs.getString("username", "ERROR");
        usernameDisplay.setText("Username: " + username);

        // If previous activity was MainActivity.java
        if(previousActivity.equals("main")) {
            // Explanation of correct answer
            String explanation = "You should always pull the mask over your nose so there is less chance you breath in a virus. " +
                    "But most importantly, the mask should cover the mouth so it will stop most of your virus and bacteria you breath out from reaching others.";
            // 1 is correct answer
            answer1.setOnClickListener(v -> {
                // String to display in CorrectDialog.java
                String correctAnswer = "Correct Answer! " + explanation;

                // Updates current score in pref file and displays it
                currentScore += 1;
                editor.putInt("currentScore", currentScore);
                editor.apply();
                currentScoreButton.setText("Current Score: " + currentScore);

                // Showing dialog
                CorrectDialog dialog = new CorrectDialog(correctAnswer, true);
                dialog.show(getSupportFragmentManager(), "Correct Answer");
            });
            // Wrong answer
            answer2.setOnClickListener(v -> {
                // String to display in CorrectDialog.java
                String wrongAnswer = "Wrong Answer! " + explanation;
                CorrectDialog dialog = new CorrectDialog(wrongAnswer, false);
                dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
            });
            answer3.setOnClickListener(v -> {
                // String to display in CorrectDialog.java
                String wrongAnswer = "Wrong Answer! " + explanation;
                CorrectDialog dialog = new CorrectDialog(wrongAnswer, false);
                dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
            });
            destination.setOnClickListener(v -> {
                // Starts new activity when destination button is clicked
                Intent intent = new Intent(this, Classroom.class);
                startActivity(intent);
            });
        }
        // If previous activity was Classroom.java
        else if (previousActivity.equals("classroom")) {
            // Setting question, answer, and destination text
            questionText.setText("You notice one of your classmate, Bob, is coughing and looks pale. What do you do?");
            answer1.setText("Tell your friends and laugh at Bob with them");
            answer2.setText("Tell the teacher that Bob is coughing and looks pale");
            answer3.setText("Keep walking and ignore him");
            destination.setText("Go to Restroom");

            // Explanation after they choose an answer
            String explanation = "If you ever notice one of your classmates look sick or is coughing, always tell the teacher whenever you get a chance so they can be sent to the nurse." +
                    "If you let a sick student stay in your class, there is a chance some people in your class will also become sick.";

            // 2 is correct answer
            answer1.setOnClickListener(v -> {
                // Displays wrong answer and game over
                String wrongAnswer = "Wrong Answer! " + explanation;
                CorrectDialog dialog = new CorrectDialog(wrongAnswer, false);
                dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
            });
            answer2.setOnClickListener(v -> {
                // Displays correct answer and updates current score
                String correctAnswer = "Correct Answer! " + explanation;
                currentScore += 1;
                currentScoreButton.setText("Current Score: " + currentScore);
                editor.putInt("currentScore", currentScore);
                editor.apply();
                CorrectDialog dialog = new CorrectDialog(correctAnswer, true);
                dialog.show(getSupportFragmentManager(), "Correct Answer");
            });
            answer3.setOnClickListener(v -> {
                // Displays wrong answer and game over
                String wrongAnswer = "Wrong Answer! " + explanation;
                CorrectDialog dialog = new CorrectDialog(wrongAnswer, false);
                dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
            });
            destination.setOnClickListener(v -> {
                // Starts Restroom Activity
                Intent intent = new Intent(this, Restroom.class);
                startActivity(intent);
            });
        }
        // If previous activity was Restroom.java
        else if(previousActivity.equals("restroom")) {
            // Setting question, answer, and destination text
            questionText.setText("You are standing in line with your mask on about to go to the Cafeteria. How far away should you stand from you other classmate?");
            answer1.setText("3 feet away");
            answer2.setText("6 feet away");
            answer3.setText("You see your friends, so you go stand close to them so you can talk with them");
            destination.setText("Go to Cafeteria");

            // Explanation after they choose an answer
            String explanation = "When you have your masks on at school, make sure to stand three feet away from other students to stay safe from COVID-19," +
                    " even if they are you friends and you do not think they have COVID.";

            // 1 is correct answer
            answer3.setOnClickListener(v -> {
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
            answer1.setOnClickListener(v -> {
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
            destination.setOnClickListener(v -> {
                // Starts Cafeteria activity
                Intent intent = new Intent(this, Cafeteria.class);
                startActivity(intent);
            });
        }
    }

    @Override
    public void onDialogPositiveClick() {
        // After the dialog disappears, turn everything invisible and just display the next destination button
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
        // After the dialog disappears, turn everything invisible and just display the next destination button
        onDialogPositiveClick();
    }
}