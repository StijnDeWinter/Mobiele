package com.example.wolkje.aphasia.model;

import android.content.Context;

import com.example.wolkje.aphasia.model.question.Question;

import java.util.ArrayList;

/**
 * Created by Wolkje on 9/01/2017 at 14:59.
 * Part of com.example.wolkje.aphasia.model.
 */

public class Manager {

    private QuestionMaker questionMaker;
    private ArrayList<Questioning> questionings;
    private ArrayList<String> patients;

    public Manager(Context context){
        questionMaker = new QuestionMaker(context);
        questionings = new ArrayList<>();
        patients = new ArrayList<>();
    }


    public ArrayList<Question> generateQuestionList(String type, int... amount){;
        //if a doesn't exist, value is set to 10
        int a1 = amount.length > 0 ? amount[0] : 10;
        return questionMaker.generateQuestionList(type, a1);
    }

    public void addPatient(String name){
        if(!patients.contains(name)){
            patients.add(name);
        }
    }

    public ArrayList<Questioning> getAllQuestionings(){
        ArrayList<ArrayyList<Question>> questions;


        //Hashmap<Questioning, Type> ???



        return questions;
    }


}
