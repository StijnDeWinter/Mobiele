package com.example.wolkje.aphasia;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wolkje.aphasia.model.Manager;
import com.example.wolkje.aphasia.model.Questioning;
import com.example.wolkje.aphasia.model.question.Question;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.wolkje.aphasia.R.id.buttonNextQuestion;
import static com.example.wolkje.aphasia.R.id.answerTextField;
import static com.example.wolkje.aphasia.R.id.imageViewQuestion;

/**
 * Created by gijs on 5/01/2017.
 */

public class TestActivity1 extends AppCompatActivity {

    private int currentPosition;
    private ArrayList<Question> questions;
    private Questioning questioning;
    private TextView questionType1;
    private ImageView questionImage;
    private EditText answer;
    private Button submitAnswer;

    /** Alles voor opnemen van geluid **/
    private static final String LOG_TAG = "AudioRecord";
    private static String mFileName = null;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;
    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        questionType1 = (TextView) findViewById(R.id.TextViewType1Question);
        questioning = new Questioning(getIntent().getExtras().getString("name"));
        mRecordButton = (RecordButton) findViewById(R.id.buttonRecordAnswer);


        addListenerOnButton();

        currentPosition = 0;
        try {
            /*
            * is currently at 1, but should be 10 in the current design of the app
            * is at 1 since lazy ass Gijs didn't make any questions yet
            * */
            questions = new Manager(getApplicationContext()).generateQuestionList("type1", 5);
        } catch (Exception e) {
            Log.d("error: ", "onCreate: " + e.getMessage());
        }
        /*pictures = (ListView) findViewById(R.id.image_list);
        pictures.setAdapter(new com.example.wolkje.aphasia.PictureListAdapter(TestActivity2.this, picturesID));*/
        //questions = new QuestionMaker(getApplicationContext()).generateQuestionList("type1", 10);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
    }

    protected void onStart() {
        super.onStart();
        startQuestioning(0);
    }

    private void startQuestioning(int currentPosition) {
        /*
        * set the thing to display to the next question
        * */
//        questionType1.setText(questions.get(currentPosition).getQuestion());
        Log.d("question:", " " + questions.get(currentPosition).getQuestion());
        questionType1.setText(getResources().getIdentifier("@string/" + questions.get(currentPosition).getQuestion(), "string", getPackageName()));
        questionImage = (ImageView) findViewById(imageViewQuestion);
        questionImage.setImageResource(getResources().getIdentifier("@drawable/type1question" + (currentPosition + 1) + "photo", "drawable", getPackageName()));
    }

    private void addListenerOnButton() {
        /*
        * capture answer
        * currentPosition++
        * startQuestioning(currentPosition) if need be, else evoke end of activity
        * */

        submitAnswer = (Button) findViewById(buttonNextQuestion);
        answer = (EditText) findViewById(answerTextField);
        submitAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Proceeding...", Toast.LENGTH_LONG).show();
                questioning.addQuestion(questions.get(currentPosition), answer.getText().toString());
                currentPosition++;
                if (currentPosition < questions.size()) {
                    startQuestioning(currentPosition);
                    answer.setText("");
                } else {
                    endOfTest();
                }
            }
        });
    }

    private void endOfTest() {
        questioning.markCompleted();
        setResult(Activity.RESULT_OK, getIntent());
        Toast.makeText(getApplicationContext(), "Test of type 1 ended, returning to home...", Toast.LENGTH_LONG).show();
        finish();
    }

    /** ALLES VOOR OPNEMEN VAN GELUID **/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void startRecording() {
        mRecorder  = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    class RecordButton extends Button {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }
}
