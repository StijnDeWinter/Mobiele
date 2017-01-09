package com.example.wolkje.aphasia.model;

import android.util.Log;

import com.example.wolkje.aphasia.model.question.Question;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by Wolkje on 9/01/2017 at 14:39.
 * Part of com.example.wolkje.aphasia.model.
 */

public class Questioning {
    private HashMap<Question, String> askedQuestions;
    private Date completionDate;
    private String patientName;

    public Questioning(String patientName){
        askedQuestions = new HashMap<>();
        this.patientName = patientName;
    }

    public HashMap<Question, String> getAskedQuestions(){
        return askedQuestions;
    }

    public String getPatientName(){
        return patientName;
    }
    public void addQuestion(Question question, String givenAnswer){
        if(question != null && question instanceof Question){
            if(givenAnswer != null && !givenAnswer.equals("")){
                askedQuestions.put(question, givenAnswer);                
            }
            else {
                Log.d(TAG, "addQuestion: something wrong with the given answer");
            }
        }
        else {
            Log.d(TAG, "addQuestion: something wrong with the question");
        }
    }


    public void markCompleted(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date date = new Date(System.currentTimeMillis());
        setDate(date);
    }

    private void setDate(Date date){
        if(date !=null && date instanceof Date){
            this.completionDate = date;
        }
        else {
            Log.d(TAG, "setDate: something wrong with the date");
        }
    }
}
