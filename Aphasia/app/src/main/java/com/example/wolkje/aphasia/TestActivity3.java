package com.example.wolkje.aphasia;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wolkje.aphasia.model.Manager;
import com.example.wolkje.aphasia.model.Questioning;
import com.example.wolkje.aphasia.model.question.Question;

import java.util.ArrayList;

import static com.example.wolkje.aphasia.R.id.answerButtonType3Option1;
import static com.example.wolkje.aphasia.R.id.answerButtonType3Option2;
import static com.example.wolkje.aphasia.R.id.answerButtonType3Option3;
import static com.example.wolkje.aphasia.R.id.answerButtonType3Option4;

/**
 * Created by Wolkje on 13/01/2017 at 17:56.
 * Part of com.example.wolkje.aphasia.
 */

class TestActivity3 {

    public class TestActivity2 extends AppCompatActivity {

        private int currentPosition;
        private ArrayList<Question> questions;
        private Questioning questioning;
        private TextView questionType3;
        private ImageButton buttonType3Option1;
        private ImageButton buttonType3Option2;
        private ImageButton buttonType3Option3;
        private ImageButton buttonType3Option4;


        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.test2);
            questionType3 = (TextView) findViewById(R.id.TextViewType3Question);
            questioning = new Questioning(getIntent().getExtras().getString("name"));
            addListenerOnButton();

            currentPosition = 0;
            try {
            /*
            * is currently at 1, but should be 10 in the current design of the app
            * is at 1 since lazy ass Gijs didn't make any questions yet
            * */
                questions = new Manager(getApplicationContext()).generateQuestionList("type3", 1);
            } catch (Exception e) {
                Log.d("error: ", "onCreate: " + e.getMessage());
            }
        /*pictures = (ListView) findViewById(R.id.image_list);
        pictures.setAdapter(new com.example.wolkje.aphasia.PictureListAdapter(TestActivity2.this, picturesID));*/
        }

        protected void onStart() {
            super.onStart();
            startQuestioning(0);
        }

        private void startQuestioning(int currentPosition) {
        /*
        * sets the buttons to display to the next question and images
        * */
            questionType3.setText(questions.get(currentPosition).getQuestion());
            buttonType3Option1.setImageResource(getResources().getIdentifier("@drawable/" + questions.get(currentPosition).getPossibleAnswers().get(0), "drawable", getPackageName()));
            buttonType3Option2.setImageResource(getResources().getIdentifier("@drawable/" + questions.get(currentPosition).getPossibleAnswers().get(1), "drawable", getPackageName()));
            buttonType3Option3.setImageResource(getResources().getIdentifier("@drawable/" + questions.get(currentPosition).getPossibleAnswers().get(2), "drawable", getPackageName()));
            buttonType3Option4.setImageResource(getResources().getIdentifier("@drawable/" + questions.get(currentPosition).getPossibleAnswers().get(3), "drawable", getPackageName()));
        }

        private void addListenerOnButton() {
        /*
        * capture answer
        * currentPosition++
        * startQuestioning(currentPosition) if need be, else evoke end of activity
        * */
            buttonType3Option1 = (ImageButton) findViewById(answerButtonType3Option1);
            buttonType3Option1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Toast.makeText(getApplicationContext(), "Selected option 1, proceeding...", Toast.LENGTH_LONG).show();
                    questioning.addQuestion(questions.get(currentPosition), questions.get(currentPosition).getPossibleAnswers().get(0));
                    currentPosition++;
                    if (currentPosition < questions.size()) {
                        startQuestioning(currentPosition);
                    } else {
                        endOfTest();
                    }
                }
            });
            buttonType3Option2 = (ImageButton) findViewById(answerButtonType3Option2);
            buttonType3Option2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Toast.makeText(getApplicationContext(), "Selected option 2, proceeding...", Toast.LENGTH_LONG).show();
                    questioning.addQuestion(questions.get(currentPosition), questions.get(currentPosition).getPossibleAnswers().get(1));
                    currentPosition++;
                    if (currentPosition < 1) {
                        startQuestioning(currentPosition);
                    } else {
                        endOfTest();
                    }
                }
            });
            buttonType3Option3 = (ImageButton) findViewById(answerButtonType3Option3);
            buttonType3Option3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Toast.makeText(getApplicationContext(), "Selected option 3, proceeding...", Toast.LENGTH_LONG).show();
                    questioning.addQuestion(questions.get(currentPosition), questions.get(currentPosition).getPossibleAnswers().get(2));
                    currentPosition++;
                    if (currentPosition < 1) {
                        startQuestioning(currentPosition);
                    } else {
                        endOfTest();
                    }
                }
            });
            buttonType3Option4 = (ImageButton) findViewById(answerButtonType3Option4);
            buttonType3Option4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Toast.makeText(getApplicationContext(), "Selected option 4, proceeding...", Toast.LENGTH_LONG).show();
                    questioning.addQuestion(questions.get(currentPosition), questions.get(currentPosition).getPossibleAnswers().get(3));
                    currentPosition++;
                    if (currentPosition < 1) {
                        startQuestioning(currentPosition);
                    } else {
                        endOfTest();
                    }
                }
            });

        }

        private void endOfTest() {
            questioning.markCompleted();
            setResult(Activity.RESULT_OK, getIntent());
            Toast.makeText(getApplicationContext(), "Test of type 3 ended, returning to home...", Toast.LENGTH_LONG).show();
            finish();
        }


    }


}
