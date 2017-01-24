package com.example.wolkje.aphasia;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wolkje.aphasia.model.Manager;
import com.example.wolkje.aphasia.model.Questioning;
import com.example.wolkje.aphasia.model.question.Question;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.wolkje.aphasia.R.id.answerButtonType2Option1;
import static com.example.wolkje.aphasia.R.id.answerButtonType2Option2;
import static com.example.wolkje.aphasia.R.id.answerButtonType2Option3;
import static com.example.wolkje.aphasia.R.id.answerButtonType2Option4;
import static com.example.wolkje.aphasia.R.id.buttonPlayAudio;

/**
 * Created by gijs on 5/01/2017.
 */

public class TestActivity2 extends AppCompatActivity {

    private int currentPosition;
    private ArrayList<Question> questions;
    private Questioning questioning;
    private TextView questionType2;
    private ImageButton buttonType2Option1;
    private ImageButton buttonType2Option2;
    private ImageButton buttonType2Option3;
    private ImageButton buttonType2Option4;
    TextToSpeech tts;
    private Button audio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);
        questionType2 = (TextView) findViewById(R.id.TextViewType2Question);
        questioning = new Questioning(getIntent().getExtras().getString("name"));
        addListenerOnButton();

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                }
            }
        });

        currentPosition = 0;
        try {
            questions = new Manager(getApplicationContext()).generateQuestionList("type2", 5);
        } catch (Exception e) {
            Log.d("error: ", "onCreate: " + e.getMessage());
        }
    }

    protected void onStart() {
        super.onStart();
        startQuestioning(0);
    }

    private void startQuestioning(int currentPosition) {
        /*
        * sets the buttons to display to the next question and images
        * */
//        questionType2.setText(questions.get(currentPosition).getQuestion());
        Log.d("testactivity2 ", "startQuestioning: getQuestion = " + questions.get(currentPosition).getQuestion());
        Log.d("testactivity3 ", "startQuestioning: possible answer 1 = " + questions.get(currentPosition).getPossibleAnswers().get(0));
        questionType2.setText(getResources().getIdentifier("@string/" + questions.get(currentPosition).getQuestion(), "string", getPackageName()));
        buttonType2Option1.setImageResource(getResources().getIdentifier("@drawable/" + questions.get(currentPosition).getPossibleAnswers().get(0), "drawable", getPackageName()));
        buttonType2Option2.setImageResource(getResources().getIdentifier("@drawable/" + questions.get(currentPosition).getPossibleAnswers().get(1), "drawable", getPackageName()));
        buttonType2Option3.setImageResource(getResources().getIdentifier("@drawable/" + questions.get(currentPosition).getPossibleAnswers().get(2), "drawable", getPackageName()));
        buttonType2Option4.setImageResource(getResources().getIdentifier("@drawable/" + questions.get(currentPosition).getPossibleAnswers().get(3), "drawable", getPackageName()));
    }

    private void addListenerOnButton() {
        /*
        * capture answer
        * currentPosition++
        * startQuestioning(currentPosition) if need be, else evoke end of activity
        * */
        buttonType2Option1 = (ImageButton) findViewById(answerButtonType2Option1);
        buttonType2Option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Selected option 1, proceeding", Toast.LENGTH_LONG).show();
                questioning.addQuestion(questions.get(currentPosition), questions.get(currentPosition).getPossibleAnswers().get(0));
                currentPosition++;
                Log.d("addListenerOnButton", "onClick: currentposition: = " + currentPosition);
                if (currentPosition < questions.size()) {
                    startQuestioning(currentPosition);
                } else {
                    endOfTest();
                }
            }
        });
        buttonType2Option2 = (ImageButton) findViewById(answerButtonType2Option2);
        buttonType2Option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Selected option 2, proceeding", Toast.LENGTH_LONG).show();
                questioning.addQuestion(questions.get(currentPosition), questions.get(currentPosition).getPossibleAnswers().get(1));
                currentPosition++;
                if (currentPosition < questions.size()) {
                    startQuestioning(currentPosition);
                } else {
                    endOfTest();
                }
            }
        });
        buttonType2Option3 = (ImageButton) findViewById(answerButtonType2Option3);
        buttonType2Option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Selected option 3, proceeding", Toast.LENGTH_LONG).show();
                questioning.addQuestion(questions.get(currentPosition), questions.get(currentPosition).getPossibleAnswers().get(2));
                currentPosition++;
                if (currentPosition < questions.size()) {
                    startQuestioning(currentPosition);
                } else {
                    endOfTest();
                }
            }
        });
        buttonType2Option4 = (ImageButton) findViewById(answerButtonType2Option4);
        buttonType2Option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Selected option 4, proceeding", Toast.LENGTH_LONG).show();
                questioning.addQuestion(questions.get(currentPosition), questions.get(currentPosition).getPossibleAnswers().get(3));
                currentPosition++;
                if (currentPosition < questions.size()) {
                    startQuestioning(currentPosition);
                } else {
                    endOfTest();
                }
            }
        });

        audio = (Button) findViewById(buttonPlayAudio);
        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = getString(getResources().getIdentifier("@string/" + questions.get(currentPosition).getQuestion(), "string", getPackageName()));
                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });
    }

    private void endOfTest() {
        questioning.markCompleted();
        setResult(Activity.RESULT_OK, getIntent());
        Toast.makeText(getApplicationContext(), "Test of type 2 ended, returning to home...", Toast.LENGTH_LONG).show();
        finish();
    }


}
