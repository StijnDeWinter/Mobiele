package com.example.wolkje.aphasia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wolkje.aphasia.model.Manager;
import com.example.wolkje.aphasia.model.question.Question;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button btnTest1;
    private Button btnTest2;
    private Button btnTest3;
    private Manager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
        manager = new Manager(getApplicationContext());
    }

    private void addListenerOnButton() {
        final Context context = this;
        btnTest1 = (Button) findViewById(R.id.btnTest1);
        btnTest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Starting a Type 1 test...", Toast.LENGTH_LONG);
                startTestTypeOne();
            }
        });
        btnTest2 = (Button) findViewById(R.id.btnTest2);
        btnTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Starting a Type 2 test...", Toast.LENGTH_LONG);
                Intent intent = new Intent(context, TestActivity2.class);
                startActivity(intent);
            }
        });
        btnTest3 = (Button) findViewById(R.id.btnTest3);
        btnTest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Starting a Type 3 test...", Toast.LENGTH_LONG);
                Intent intent = new Intent(context, TestActivity3.class);
                startActivity(intent);
            }
        });

    }

    private void startTestTypeOne(){
        ArrayList<Question> questions = manager.generateQuestionList("type1", 10);
        for(int n = 0; n < 10; n++){

        }


        Intent intent = new Intent(getApplicationContext(), TestActivity1.class);
        startActivity(intent);
    }

    private void startTestTypeTwo(){
        ArrayList<Question> questions = manager.generateQuestionList("type2", 10);
        for(int n = 0; n < 10; n++){

        }


        Intent intent = new Intent(getApplicationContext(), TestActivity1.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
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
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
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
}
