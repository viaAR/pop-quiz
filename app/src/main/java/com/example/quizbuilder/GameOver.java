package com.example.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class GameOver extends AppCompatActivity {

    int correctAnswers, incorrectAnswers;
    String username;

    CircularProgressBar circularProgressBar;
    TextView tv_result_score, tv_final_message;
    Button btn_return_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        // Pull the value of the correct/incorrect answers
        correctAnswers = getIntent().getIntExtra("correctAnswers", 0);
        incorrectAnswers = getIntent().getIntExtra("incorrectAnswers", 0);

        connectIntractable();

        // Update the progress bar for the final results
        circularProgressBar.setProgress(correctAnswers);
        tv_result_score.setText(correctAnswers + "/10");

        // Output the final message with users name
        username = getIntent().getStringExtra("username");
        tv_final_message.setText("Thanks for playing " + username + "!");

        btn_return_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnHome();
            }
        });

    }

    private void connectIntractable(){
        circularProgressBar = findViewById(R.id.circularProgressBar);
        tv_result_score = findViewById(R.id.tv_result_score);
        tv_final_message = findViewById(R.id.tv_final_message);
        btn_return_home = findViewById(R.id.btn_return_home);
    }

    private void returnHome() {
        Intent i = new Intent(GameOver.this, MainActivity.class);
        startActivity(i);
    }
}