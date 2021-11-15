package com.example.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizQuestionsActivity extends AppCompatActivity {

    ArrayList<Question> questionList;
    List<Question> allQuestions;
    Question ques;
    int index = 0;
    int correctAnswers = 0;
    int incorrectAnswers = 0;
    //int progress = 1;


    TextView tv_question, tv_progress, tv_option_one, tv_option_two, tv_option_three, tv_option_four;
    ImageView iv_image;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);

        connectInteractables();

        // List
        questionList = new ArrayList<>();
        questionList.add(new Question("Who's logo is this?", R.drawable.seattle_kraken, "Seattle Seahawks", "Seattle Kraken", "San Diego Serpents", "St Louis Snakes", "Seattle Kraken"));
        questionList.add(new Question("Who's logo is this?", R.drawable.chicago_bears, "Chicago Cubs", "Cleveland Cavaliers", "California Cardinals", "Chicago Bears", "Chicago Bears"));
        questionList.add(new Question("Who's logo is this?", R.drawable.portland_timbers, "Alabama Loggers", "Utah Lumberjacks", "Portland Timbers", "Edmonton Pioneers", "Portland Timbers"));
        questionList.add(new Question("Who's logo is this?", R.drawable.arizona_coyotes, "Arizona Coyotes", "Denver Wolves", "Carolina Wolfpack", "Houston Hounds", "Arizona Coyotes"));
        questionList.add(new Question("Who's logo is this?", R.drawable.colorado_rockies, "Charlotte Rockets", "Charlestown Rapids", "Colorado Rockies", "Carolina Raiders", "Colorado Rockies"));
        questionList.add(new Question("Who's logo is this?", R.drawable.columbus_blue_jackets, "Phoenix Stars", "Columbus Blue Jackets", "Washington Aces", "Houston Astros", "Columbus Blue Jackets"));
        questionList.add(new Question("Who's logo is this?", R.drawable.los_angeles_angels, "Alabama Angels", "Alberta Roughnecks", "Los Angeles Angels", "Oregon Ariels", "Los Angeles Angels"));
        questionList.add(new Question("Who's logo is this?", R.drawable.los_angeles_rams, "Los Angeles Raiders", "Los Angeles Chargers", "Los Angeles Rams", "Los Angeles Storm", "Los Angeles Rams"));
        questionList.add(new Question("Who's logo is this?", R.drawable.new_england_revolution, "South Dakota Americans", "Minnesota Patriots", "Georgia Stars", "New England Revolution", "New England Revolution"));
        questionList.add(new Question("Who's logo is this?", R.drawable.toronto_blue_jays, "Toronto Blue Jays", "Montreal Ravens", "Vancouver Hawks", "Alberta Cardinals", "Toronto Blue Jays"));

        allQuestions = questionList;
        Collections.shuffle(allQuestions);
        ques = questionList.get(index);

        setQuizData();

    }

    private void setQuizData() {
        tv_question.setText(ques.getQuestion());

        iv_image.setImageResource(ques.getImage());

        tv_option_one.setText(ques.optionOne);
        tv_option_two.setText(ques.optionTwo);
        tv_option_three.setText(ques.optionThree);
        tv_option_four.setText(ques.optionFour);

        //tv_progress.setText(Integer.toString(progress) + "/10");
    }

    private void connectInteractables() {
        tv_question = findViewById(R.id.tv_question);
        tv_progress = findViewById(R.id.tv_progress);
        tv_option_one = findViewById(R.id.tv_option_one);
        tv_option_two = findViewById(R.id.tv_option_two);
        tv_option_three = findViewById(R.id.tv_option_three);
        tv_option_four = findViewById(R.id.tv_option_four);

        iv_image = findViewById(R.id.iv_image);

        btn_submit = findViewById(R.id.btn_submit);
    }

    public void correct()
    {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctAnswers++;
                index++;

                ques = questionList.get(index);
                setQuizData();
            }
        });
    }

    public void incorrect()
    {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                incorrectAnswers++;

                if (index < questionList.size() - 1) {

                    index++;
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
        Intent i = new Intent(QuizQuestionsActivity.this, GameOver.class);
        startActivity(i);
    }
}