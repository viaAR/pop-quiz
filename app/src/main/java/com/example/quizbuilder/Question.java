package com.example.quizbuilder;

import java.util.ArrayList;

public class Question {
    String question;
    int image;
    String optionOne;
    String optionTwo;
    String optionThree;
    String optionFour;
    String answer;

    public Question(String question, int image, String optionOne, String optionTwo, String optionThree, String optionFour, String answer) {
        this.question = question;
        this.image = image;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionThree = optionThree;
        this.optionFour = optionFour;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getOptionA() {
        return optionOne;
    }

    public void setOptionA(String optionOne) {
        this.optionOne = optionOne;
    }

    public String getOptionB() {
        return optionTwo;
    }

    public void setOptionB(String optionTwo) {
        this.optionTwo = optionTwo;
    }

    public String getOptionC() {
        return optionThree;
    }

    public void setOptionC(String optionThree) {
        this.optionThree = optionThree;
    }

    public String getOptionD() {
        return optionFour;
    }

    public void setOptionD(String optionFour) {
        this.optionFour = optionFour;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
