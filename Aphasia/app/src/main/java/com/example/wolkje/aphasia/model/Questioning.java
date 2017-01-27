package com.example.wolkje.aphasia.model;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.wolkje.aphasia.model.question.Question;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Wolkje on 9/01/2017 at 14:39.
 * Part of com.example.wolkje.aphasia.model.
 */

public class Questioning {
    private HashMap<Question, String> askedQuestions;
    private Date completionDate;
    private String patientName;
    private String type;

    public Questioning(String patientName) {
        askedQuestions = new LinkedHashMap<>();
        this.patientName = patientName;
    }

    HashMap<Question, String> getAskedQuestions() {
        return askedQuestions;
    }

    String getPatientName() {
        return patientName;
    }

    public void addQuestion(Question question, String givenAnswer) {
        if (question != null) {
            if (givenAnswer != null && !givenAnswer.equals("")) {
                askedQuestions.put(question, givenAnswer);
                type = question.getQuestion().substring(0, 5);
            } else {
                Log.d(TAG, "addQuestion: something wrong with the given answer");
            }
        } else {
            Log.d(TAG, "addQuestion: something wrong with the question");
        }
    }


    public void markCompleted() {
        /*
        *  questioning needs to be saved here
        *  if possible at level op the types.
        * */
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date date = new Date(System.currentTimeMillis());
        setDate(date);
        File writeLocation = new File(Environment.getExternalStorageDirectory() + File.separator + "questions" + File.separator + "questionings");
        int amountOfFiles = 0;
        for (File file : writeLocation.listFiles()) {
            String fileName = file.getName();
            if (fileName.contains(patientName.replaceAll("\\s", ""))) {
                amountOfFiles++;
            }
        }
        try {
            File newFile = new File(Environment.getExternalStorageDirectory() + File.separator + "questions" + File.separator + "questionings" + File.separator + patientName.replaceAll("\\s", "") + (amountOfFiles + 1) + ".txt");
            newFile.createNewFile();
            PrintWriter writer = new PrintWriter(new FileOutputStream(newFile));

            writer.println("questiontype: " + type);
            writer.println("patientname: " + patientName);
            writer.println("timestamp completion: " + date);
                for (Map.Entry<Question, String> question : getAskedQuestions().entrySet()) {
                    writer.println();
                    writer.println("question: " + question.getKey().getQuestion());
                    if (question.getKey().getPossibleAnswers() != null && question.getKey().getPossibleAnswers().size() > 0) {
                        for (int i = 0; i < question.getKey().getPossibleAnswers().size(); i++) {
                            writer.println("possible answer " + (i + 1) + ": " + question.getKey().getPossibleAnswers().get(i));
                        }
                    }
                    if (question.getKey().getAnswer() != null && !question.getKey().getAnswer().equals("")) {
                        writer.println("correct answer: " + question.getKey().getAnswer());
                    }
                    writer.println("given answer: " + question.getValue());
                }

            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDate(Date date) {
        if (date != null && date instanceof Date) {
            this.completionDate = date;
        } else {
            Log.d(TAG, "setDate: something wrong with the date");
        }
    }
}
