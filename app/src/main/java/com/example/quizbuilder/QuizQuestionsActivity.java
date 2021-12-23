package com.example.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizQuestionsActivity extends AppCompatActivity {

    ArrayList<String> quizAnswers; // Hold all the possible answers
    ArrayList<Question> questionList; // Hold the assembled question
    List<Question> allQuestions; // Hold all assembled questions
    Question ques; // Object to interact with each assembled question

    int index = 0;
    int correctAnswers = 0;
    int incorrectAnswers = 0;
    int currentProgress = 1;

    String username;

    TextView tv_question, tv_progress, tv_option_one, tv_option_two, tv_option_three, tv_option_four;
    ImageView iv_image;
    Button btn_submit;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);

        quizAnswers = new ArrayList<>();
        questionList = new ArrayList<>();

        // Connect all the views & widgets
        connectInteractables();

        // Read in all the possible answers
        readAnswers();

        // Create the quiz questions with their respective answers
        createQuizData();

        // Compile all the questionList's and shuffle before setting the view data
        allQuestions = questionList;
        Collections.shuffle(allQuestions);
        ques = questionList.get(index);

        // Set the data for each quiz question
        setQuizData();
    }


    private void readAnswers() {
        String answer = null;
        BufferedReader br = null;

        try {
            // Open the file for reading
            InputStream is = getResources().openRawResource(R.raw.quiz_answers);
            br = new BufferedReader(new InputStreamReader(is));

            while ((answer = br.readLine()) != null){

                // Separate the values by comma
                String[] tokens = answer.split(",");
                // Add all the token separated values to the quizAnswer list
                quizAnswers.addAll(Arrays.asList(tokens).subList(0, 4));
            }
            is.close();
        }
        catch (Exception e) {
            Log.wtf("QuizQuestionsActivity", "Error reading the file on line " + answer, e);

            e.printStackTrace();
        }
    }

    private void createQuizData() {
        String logo = null;
        String value = null;
        String rDrawableName = null;
        int resourceID;
        BufferedReader br = null;
        Map<String, String> map = new HashMap<>();

        // Temp list - For shuffling each question in the option list
        ArrayList<String> tempList = new ArrayList<>();

        // Track where the correct answers is placed in the list
        int correctAnsCounter = 0;

        try {
            // Open the file for reading
            InputStream is = getResources().openRawResource(R.raw.quiz_logos);
            br = new BufferedReader(new InputStreamReader(is));

            while ((logo = br.readLine()) != null){

                // Make a temp list that can be shuffled
                for (int i = correctAnsCounter; i < correctAnsCounter + 4; i++) {
                    tempList.add(quizAnswers.get(i));
                }
                // Shuffle the list
                Collections.shuffle(tempList);

                // Find each drawable ID
                rDrawableName = logo;

                // Get the resource in the drawable folder
                resourceID = getResources().getIdentifier(rDrawableName , "drawable", getPackageName());

                // Map the correct answers to the image resource
                value = quizAnswers.get(correctAnsCounter);
                map.put(logo, value);

                questionList.add(new Question("Who's logo is this?", resourceID, tempList.get(0), tempList.get(1), tempList.get(2), tempList.get(3), value));

                // Correct answers are the first string of every new line
                correctAnsCounter += 4;

                // Reset the temp list for the next question
                tempList.clear();
            }
            is.close();
        }
        catch (Exception e) {
            Log.wtf("QuizQuestionsActivity", "Error reading the file on line " + logo, e);

            e.printStackTrace();
        }
    }

    private void setQuizData() {
        tv_question.setText(ques.getQuestion());

        iv_image.setImageResource(ques.getImage());

        tv_option_one.setText(ques.optionOne);
        tv_option_two.setText(ques.optionTwo);
        tv_option_three.setText(ques.optionThree);
        tv_option_four.setText(ques.optionFour);

        progressBar.setProgress(currentProgress);
        tv_progress.setText(Integer.toString(currentProgress) + "/" + progressBar.getMax());

    }

    private void connectInteractables() {
        tv_question = findViewById(R.id.tv_question);
        tv_progress = findViewById(R.id.tv_progress);
        tv_option_one = findViewById(R.id.tv_option_one);
        tv_option_two = findViewById(R.id.tv_option_two);
        tv_option_three = findViewById(R.id.tv_option_three);
        tv_option_four = findViewById(R.id.tv_option_four);

        progressBar = findViewById(R.id.progressBar);

        iv_image = findViewById(R.id.iv_image);

        btn_submit = findViewById(R.id.btn_submit);
    }

    public void correct(TextView tv)
    {
        tv.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_border));
        tv.setTypeface(null, Typeface.BOLD);

        disableButton();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetOptions();
                enableButton();

                correctAnswers++;
                index++;
                currentProgress++;

                if (index < questionList.size()) {

                    ques = questionList.get(index);

                    progressBar.setProgress(currentProgress);

                    setQuizData();
                }
                else {
                    gameOver();
                }
            }
        });
    }

    public void incorrect(TextView tv)
    {
        tv.setBackground(ContextCompat.getDrawable(this, R.drawable.incorrect_border));
        tv.setTypeface(null, Typeface.BOLD);

        disableButton();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetOptions();
                enableButton();

                incorrectAnswers++;
                index++;
                currentProgress++;

                if (index < questionList.size()) {

                    progressBar.setProgress(currentProgress);

                    ques = questionList.get(index);
                    setQuizData();
                }
                else {
                    gameOver();
                }
            }
        });
    }

    private void gameOver() {
        username = getIntent().getStringExtra("username");

        Intent i = new Intent(QuizQuestionsActivity.this, GameOver.class);
        i.putExtra("correctAnswers", correctAnswers);
        i.putExtra("incorrectAnswers", incorrectAnswers);
        i.putExtra("username", username);

        startActivity(i);
    }

    public void enableButton() {
        tv_option_one.setClickable(true);
        tv_option_two.setClickable(true);
        tv_option_three.setClickable(true);
        tv_option_four.setClickable(true);
    }

    public void disableButton() {
        tv_option_one.setClickable(false);
        tv_option_two.setClickable(false);
        tv_option_three.setClickable(false);
        tv_option_four.setClickable(false);
    }

    public void resetOptions() {
        tv_option_one.setBackground(ContextCompat.getDrawable(this, R.drawable.default_border));
        tv_option_two.setBackground(ContextCompat.getDrawable(this, R.drawable.default_border));
        tv_option_three.setBackground(ContextCompat.getDrawable(this, R.drawable.default_border));
        tv_option_four.setBackground(ContextCompat.getDrawable(this, R.drawable.default_border));

        tv_option_one.setTypeface(null, Typeface.NORMAL);
        tv_option_two.setTypeface(null, Typeface.NORMAL);
        tv_option_three.setTypeface(null, Typeface.NORMAL);
        tv_option_four.setTypeface(null, Typeface.NORMAL);
    }

    public void optionOneClick(View view) {
        if (ques.getOptionA().equals(ques.answer)) {
            correct(tv_option_one);
        }
        else {
            incorrect(tv_option_one);
        }
    }

    public void optionTwoClick(View view) {
        if (ques.getOptionB().equals(ques.answer)) {
            correct(tv_option_two);
        }
        else {
            incorrect(tv_option_two);
        }
    }

    public void optionThreeClick(View view) {
        if (ques.getOptionC().equals(ques.answer)) {
            correct(tv_option_three);
        }
        else {
            incorrect(tv_option_three);
        }
    }

    public void optionFourClick(View view) {
        if (ques.getOptionD().equals(ques.answer)) {
            correct(tv_option_four);
        }
        else {
            incorrect(tv_option_four);
        }
    }
}