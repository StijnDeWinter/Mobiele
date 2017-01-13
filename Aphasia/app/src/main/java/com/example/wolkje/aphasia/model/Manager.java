package com.example.wolkje.aphasia.model;

import android.content.Context;

import com.example.wolkje.aphasia.model.question.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Wolkje on 9/01/2017 at 14:59.
 * Part of com.example.wolkje.aphasia.model.
 *
 *
 *  TO DO: questionings moet nog steeds ingelezen worden
 *
 *
 *
 */

public class Manager {

    private QuestionMaker questionMaker;
    private ArrayList<Questioning> questionings;
    private ArrayList<String> patients;

    public Manager(Context context) {
        questionMaker = new QuestionMaker(context);
        questionings = new ArrayList<>();
        patients = new ArrayList<>();
    }


    public ArrayList<Question> generateQuestionList(String type, int... amount) throws IOException {
        /*
        * if a doesn't exist, value is set to 10
        * is never actually used, but seemed logical to implement it at the time
        * */
        int a1 = amount.length > 0 ? amount[0] : 10;
        return questionMaker.generateQuestionList(type, a1);
    }

    public void testsForPatient(String patientName, String... type) {
        /*
        * lists all the questionings for a given patient.
        * a type can be specified and will act as a filter
        * */
        ArrayList<Questioning> questioningsForPatient = new ArrayList<>();
        Map.Entry<Question, String> entry;
        if (type.length > 0) {
            if (type.length == 3) {
                for (Questioning q : questionings) {
                    if (q.getPatientName().equals(patientName)) {
                        questioningsForPatient.add(q);
                    }
                }
            } else if (type.length == 2) {
                for (Questioning q : questionings) {
                    entry = q.getAskedQuestions().entrySet().iterator().next();
                    if (q.getPatientName().equals(patientName) && entry.getKey().getType().equals(type[0]) || entry.getKey().getType().equals(type[1])) {
                        questioningsForPatient.add(q);
                    }
                }
            } else if (type.length == 1) {
                for (Questioning q : questionings) {
                    entry = q.getAskedQuestions().entrySet().iterator().next();
                    if (q.getPatientName().equals(patientName) && entry.getKey().getType().equals(type[0])) {
                        questioningsForPatient.add(q);
                    }
                }
            }
        } else {
            for (Questioning q : questionings) {
                if (q.getPatientName().equals(patientName)) {
                    questioningsForPatient.add(q);
                }
            }
        }
    }

    public void addPatient(String name) {
        if (!patients.contains(name)) {
            patients.add(name);
        }
    }

    public ArrayList<String> allPatients() {
        return patients;
    }

    public ArrayList<Questioning> getAllQuestionings() {
        /*
        * returns all questionings, no formatting at all, needs to happen by evoker
        * which kinda sounds like it's breaking MCV pattern, when I think about it
        * */
        return questionings;
    }


}
