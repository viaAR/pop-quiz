package com.example.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String username;

    Button btn_start;
    EditText et_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = findViewById(R.id.btn_start);
        et_name = findViewById(R.id.et_name);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check to make sure the user entered a name
                if (et_name.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(),"Enter a name",Toast.LENGTH_SHORT).show();
                }
                else {
                    // Pass the users name through to the next activity
                    username = et_name.getText().toString();

                    Intent i = new Intent(MainActivity.this, QuizQuestionsActivity.class);
                    i.putExtra("username", username);
                    MainActivity.this.startActivity(i);
                }
            }
        });
    }
}