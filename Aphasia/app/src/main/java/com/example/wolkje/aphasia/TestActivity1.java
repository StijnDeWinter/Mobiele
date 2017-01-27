package com.example.wolkje.aphasia;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
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
    private Button startRecording;
    private Button stopRecording;
    private String AudioSavePathInDevice = null;
    private MediaRecorder mediaRecorder ;
    private Random random ;
    private String RandomAudioFileName = "test1";
    public static final int RequestPermissionCode = 1;
    private MediaPlayer mediaPlayer ;
    private String writingLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);
        questionType1 = (TextView) findViewById(R.id.TextViewType1Question);
        questioning = new Questioning(getIntent().getExtras().getString("name"));
        startRecording = (Button) findViewById(R.id.buttonRecordAnswer);
        stopRecording = (Button) findViewById(R.id.buttonStopRecordAnswer);
        submitAnswer = (Button) findViewById(buttonNextQuestion);
        stopRecording.setEnabled(false);
        submitAnswer.setEnabled(false);
        random = new Random();


        addListenerOnButton();

        currentPosition = 0;
        try {
            /*
            * is currently at 1, but should be 10 in the current design of the app
            * is at 1 since lazy ass Gijs didn't make any questions yet
            * */
            questions = new Manager(getApplicationContext()).generateQuestionList("type1", 5);
            String patientNameStripped = getIntent().getExtras().getString("name").replaceAll("\\s", "");
            File writeLocation = new File(Environment.getExternalStorageDirectory() + File.separator + "questions" + File.separator + "questionings");
            int amountOfFiles = 0;
            for (File file : writeLocation.listFiles()) {
                String fileName = file.getName();
                if (fileName.contains(patientNameStripped)) {
                    amountOfFiles++;
                }
            }


            Log.d("boe", "onCreate: patientname" + patientNameStripped);
            File folder = new File(Environment.getExternalStorageDirectory() + "/questions/questionings/" + patientNameStripped + (amountOfFiles+1));
            folder.mkdir();
            writingLocation = (Environment.getExternalStorageDirectory() + "/questions/questionings/" + patientNameStripped + (amountOfFiles+1)).toString();
        } catch (Exception e) {
            Log.d("error: ", "onCreate: " + e.getMessage());
        }

        startRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission()) {
                    AudioSavePathInDevice =
                            writingLocation + "/question" + currentPosition + ".3gp";

                    MediaRecorderReady();

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    startRecording.setEnabled(false);
                    stopRecording.setEnabled(true);
                    submitAnswer.setEnabled(false);
                    Toast.makeText(TestActivity1.this, "Recording started",
                            Toast.LENGTH_SHORT).show();
                } else {
                    requestPermission();
                }

            }


        });

        stopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                mediaRecorder.reset();
                mediaRecorder.release();
                stopRecording.setEnabled(false);
                submitAnswer.setEnabled(true);
                startRecording.setEnabled(true);

                Toast.makeText(TestActivity1.this, "Recording Completed", Toast.LENGTH_LONG).show();
            }
        });
        /*pictures = (ListView) findViewById(R.id.image_list);
        pictures.setAdapter(new com.example.wolkje.aphasia.PictureListAdapter(TestActivity2.this, picturesID));*/
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
        answer = (EditText) findViewById(answerTextField);
        submitAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startRecording.setEnabled(true);
                submitAnswer.setEnabled(false);
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
    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(TestActivity1.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(TestActivity1.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(TestActivity1.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }
}
