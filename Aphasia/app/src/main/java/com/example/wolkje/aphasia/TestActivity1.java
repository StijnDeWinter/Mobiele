package com.example.wolkje.aphasia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.wolkje.aphasia.model.question.Question;

import java.util.ArrayList;

/**
 * Created by gijs on 5/01/2017.
 */

public class TestActivity1 extends AppCompatActivity {
    private int currentPosition;
    private ArrayList<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);
        currentPosition = 0;
        //questions = new QuestionMaker(getApplicationContext()).generateQuestionList("type1", 10);
    }

    protected void onStart() {
        super.onStart();
        startQuestioning(0);
    }

    private void startQuestioning(int currentPosition) {
        /*
        * set the thing to display to the next question
        * */
    }
}
