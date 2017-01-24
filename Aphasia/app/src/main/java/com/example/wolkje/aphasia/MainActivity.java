package com.example.wolkje.aphasia;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.wolkje.aphasia.model.Manager;
import com.example.wolkje.aphasia.model.Questioning;

public class MainActivity extends AppCompatActivity {
    private final int TEST_TYPE_1 = 1;
    private final int TEST_TYPE_2 = 2;
    private final int TEST_TYPE_3 = 3;
    private Manager manager;
    private Questioning questioning;
    private String patient;

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("bleh1", "Permission is granted");
                return true;
            } else {

                Log.v("bleh2", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            Log.v("bleh3", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("bleh4", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
        manager = new Manager(getApplicationContext());
        isStoragePermissionGranted();
    }

    private void addListenerOnButton() {
        Button btnTest1;
        Button btnTest2;
        Button btnTest3;
        btnTest1 = (Button) findViewById(R.id.btnTest1);
        btnTest2 = (Button) findViewById(R.id.btnTest2);
        btnTest3 = (Button) findViewById(R.id.btnTest3);
        if (btnTest1 != null && btnTest2 != null && btnTest3 != null) {
            btnTest1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("Name patient");
                    dialog.setMessage("Fill in the name:");

                    final EditText input = new EditText(MainActivity.this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    dialog.setView(input);

                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            patient = input.getText().toString();
                            Toast.makeText(getApplicationContext(), "Starting a Type 1 test...", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), TestActivity1.class);
                           /*
                            * needs to be changed to the actual name of the patient
                            * */
                            intent.putExtra("name", patient);
                            startActivityForResult(intent, TEST_TYPE_1);
                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();

                }
            });
            btnTest2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("Name patient");
                    dialog.setMessage("Fill in the name:");

                    final EditText input = new EditText(MainActivity.this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    dialog.setView(input);

                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            patient = input.getText().toString();
                            Toast.makeText(getApplicationContext(), "Starting a Type 2 test...", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), TestActivity2.class);
                            /*
                            * needs to be changed to the actual name of the patient
                               * */
                            intent.putExtra("name", patient);
                            startActivityForResult(intent, TEST_TYPE_2);
                        }
                    });

                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
            });
            btnTest3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("Name patient");
                    dialog.setMessage("Fill in the name:");

                    final EditText input = new EditText(MainActivity.this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    dialog.setView(input);

                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            patient = input.getText().toString();
                            Toast.makeText(getApplicationContext(), "Starting a Type 3 test...", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), TestActivity3.class);
                            /*
                            * needs to be changed to the actual name of the patient
                            * */
                            intent.putExtra("name", patient);
                            startActivityForResult(intent, TEST_TYPE_3);
                        }
                    });

                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
            });
        } else {
            Log.d("MainActivity", "addListenerOnButton: error while configuring MainActivity buttons");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Toast.makeText(getApplicationContext(), "Test type 1 has ended without errors.", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "Test type 2 has ended without errors.", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), "Test type 3 has ended without errors.", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Log.d("MainActivity", "onActivityResult: something went wrong when handling ActivityResult");
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        * handles the actions for the menu
        * */
        switch (item.getItemId()) {
            case R.id.reset:
                //go back to main page
                //delete all incomplete data  --> OnDestroy() still missing
                Toast.makeText(getApplicationContext(), "Resetting", Toast.LENGTH_LONG);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit:
                //exit the app
                //delete all incomplete data    --> OnDestroy() still missing
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Exiting...")
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /*private void startTestTypeOne(String patientName){
        *//*
        * parameter for amount of questions can be added but is out of scope
        * *//*
        ArrayList<Question> questions = manager.generateQuestionList("type1");
        questioning = new Questioning(patientName);
        for(int n = 0; n < 10; n++){
            Intent intent = new Intent(getApplicationContext(), TestActivity1.class);
            intent.putExtra("question", questions.get(n).getQuestion());
            intent.putExtra("answer1", questions.get(n).getPossibleAnswers().get(0));
            intent.putExtra("answer2", questions.get(n).getPossibleAnswers().get(1));
            intent.putExtra("answer3", questions.get(n).getPossibleAnswers().get(2));
            intent.putExtra("answer4", questions.get(n).getPossibleAnswers().get(3));
            startActivityForResult(intent, REQUEST_ANSWER);
        }


    }*/


 /*   private void startTestTypeTwo() {
        try {
            ArrayList<Question> questions = manager.generateQuestionList("type2");
            for (int n = 0; n < 10; n++) {

            }
            Intent intent = new Intent(getApplicationContext(), TestActivity1.class);
            startActivity(intent);
        } catch (Exception e) {
            Log.d("error", "startTestTypeTwo: " + e.getMessage());
        }
    }*/



}
