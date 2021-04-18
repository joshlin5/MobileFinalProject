package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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
        answer = findViewById(R.id.answer);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        destination = findViewById(R.id.destination);

        question.setVisibility(View.VISIBLE);
        questionText.setVisibility(View.VISIBLE);
        answer.setVisibility(View.VISIBLE);
        answer1.setVisibility(View.VISIBLE);
        answer2.setVisibility(View.VISIBLE);
        answer3.setVisibility(View.VISIBLE);
        destination.setVisibility(View.INVISIBLE);

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
                editor.apply();
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
            questionText.setText("You notice one of your classmate, Bob, is coughing and looks pale. What do you do?");
            answer1.setText("Tell your friends and laugh at Bob with them");
            answer2.setText("Tell the teacher that Bob is coughing and looks pale");
            answer3.setText("Keep walking and ignore him");

            String explanation = "If you ever notice one of your classmates look sick or is coughing, always tell the teacher whenever you get a chance so they can be sent to the nurse." +
                    "If you let a sick student stay in your class, there is a chance some people in your class will also become sick.";

            // 2 is correct answer
            answer1.setOnClickListener(v -> {
                String wrongAnswer = "Wrong Answer! " + explanation;
                correctDialog dialog = new correctDialog(wrongAnswer, false);
                dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
            });
            answer2.setOnClickListener(v -> {
                String correctAnswer = "Correct Answer! " + explanation;
                currentScore += 1;
                currentScoreButton.setText("Current Score: " + currentScore);
                editor.putInt("currentScore", currentScore);
                editor.apply();
                correctDialog dialog = new correctDialog(correctAnswer, true);
                dialog.show(getSupportFragmentManager(), "Correct Answer");
            });
            answer3.setOnClickListener(v -> {
                String wrongAnswer = "Wrong Answer! " + explanation;
                correctDialog dialog = new correctDialog(wrongAnswer, false);
                dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
            });


        }
        else if(previousActivity.equals("restroom")) {
            questionText.setText("You are about to cough but you do not have a mask on. What do you do?");
            answer1.setText("Cough as loud as you can so everyone knows you coughed");
            answer2.setText("Cover your mouth with your hands and cough");
            answer3.setText("Cover your mouth with your elbow and cough");

            String explanation = "When coughing, make sure to cough into your elbow and wash your hands and elbows as soon as possible after. " +
                    "It's best not to cough into your hands because you could easily touch something else with you hands and transfer the germs.";

            // 3 is correct answer
            answer1.setOnClickListener(v -> {
                String wrongAnswer = "Wrong Answer! " + explanation;
                correctDialog dialog = new correctDialog(wrongAnswer, false);
                dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
            });
            answer2.setOnClickListener(v -> {
                String correctAnswer = "Wrong Answer! " + explanation;
                correctDialog dialog = new correctDialog(correctAnswer, false);
                dialog.show(getSupportFragmentManager(), "Wrong Answer/Game Over");
            });
            answer3.setOnClickListener(v -> {
                String wrongAnswer = "Correct Answer! " + explanation;
                currentScore += 1;
                editor.putInt("currentScore", currentScore);
                editor.apply();
                currentScoreButton.setText("Current Score: " + currentScore);
                correctDialog dialog = new correctDialog(wrongAnswer, true);
                dialog.show(getSupportFragmentManager(), "Correct Answer");
            });
        }
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