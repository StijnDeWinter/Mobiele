package com.example.wolkje.aphasia.model;

import android.content.Context;
import android.util.Log;

import com.example.wolkje.aphasia.model.question.Question;
import com.example.wolkje.aphasia.model.question.Type1Question;
import com.example.wolkje.aphasia.model.question.Type2Question;
import com.example.wolkje.aphasia.model.question.Type3Question;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import static android.content.ContentValues.TAG;

/**
 * Created by Wolkje on 3/01/2017 at 11:13 at 11:14.
 * Part of ${PACKAGE_NAME}.
 */

public class QuestionMaker {

    private Context context;

    public QuestionMaker(Context context) {
        this.context = context;

    }
    /*
    * Method to evoke when a new list question of specific type needs to be generated
    *
    * Type1
    * The patient is shown a picture (e.g. a candle) and is expected to say out loud what he sees (e.g. "candle!").
    * The app should record this and make it possible for researchers to easily access this data.
    *
    * Type2
    * The patient is shown a series of pictures (e.g. an ear, a hand, an arm and a finger).
    * The app lets the patient hear a word (e.g. "hand").
    * The patient is then expected to pick the corresponding icon.
    *
    * Type3
    * The patient is shown a sentence describing a scenario and has to pick the picture that best matches it.
    *
    * As it turns out, code-wise, Type 2 and 3 are exactly the same. The File System is requiring it
    * to be separated and from a designing VP that's also the best approach, ergo.
    * */

    public ArrayList<Question> generateQuestionList(String type, int a) {
        ArrayList<Question> questions;
        switch (type.replaceAll("\\s", "")) {  //strips any spaces, tabs and commas from the string
            case "type1":
                questions = generateType1Questions(a);
                break;
            case "type2":
                questions = generateType2Questions(a);
                break;
            case "type3":
                questions = generateType3Questions(a);
                break;
            default:
                questions = null;
                break;
        }
        return questions;
    }


    private ArrayList<Question> generateType1Questions(int a) {
        ArrayList<Question> questions = new ArrayList<>(a);

        String question;
        String answer;
        ArrayList<String> possibleAnswers;

        FileInputStream input;
        InputStreamReader inputReader;
        BufferedReader bufferedReader;

        for (int i = 0; i < a; i++) {
            try {

                /*Reading question from file*/
                input = context.openFileInput("Data/Type1/Question" + i + "/question.txt");
                inputReader = new InputStreamReader(input);
                bufferedReader = new BufferedReader(inputReader);
                question = bufferedReader.readLine() != null ? bufferedReader.readLine() : "Question not found.";

                /*Counting amount of possible answers*/
                /*File[] files = new File("Data/Type1/Question" + i).listFiles();
                int amountFiles = 0;
                for (File file : files) { //is safe way of counting amount of possible answers. files.lenght - 1 works too (for now)
                    String name = file.getName();
                    if(name.endsWith(".jpg")){
                        amountFiles++;
                    }
                }

                *//*setting the correct answer*//*
                answer = ("Data/Type1/Vraag" + i + "/answer0");

                *//*adding bogus answers, randomly selected from the map*//*
                possibleAnswers = new ArrayList<>(4);
                ArrayList<String> tempAnswers = new ArrayList<>(amountFiles);
                for (int j = 0; j < amountFiles; j++){
                    tempAnswers.add("Data/Type1/Question" + i + "/answer" + j);
                }
                addThreeRandomAnswers(tempAnswers);*/

                Question question1 = new Type1Question(question);
                questions.add(question1);

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception exep) {
                Log.d(TAG, "generateType1Questions: error occured");
            }
        }
        return questions;
    }

    private ArrayList<Question> generateType2Questions(int a) {
        ArrayList<Question> questions = new ArrayList<>(a);

        String question;
        String answer;
        ArrayList<String> possibleAnswers;

        FileInputStream input;
        InputStreamReader inputReader;
        BufferedReader bufferedReader;

        for (int i = 0; i < a; i++) {
            try {

                /*Reading question from file*/
                input = context.openFileInput("Data/Type2/Question" + i + "/question.txt");
                inputReader = new InputStreamReader(input);
                bufferedReader = new BufferedReader(inputReader);
                question = bufferedReader.readLine() != null ? bufferedReader.readLine() : "Question not found.";

                /*Counting amount of possible answers*/
                File[] files = new File("Data/Type2/Question" + i).listFiles();
                int amountFiles = 0;
                /*
                * is safe way of counting amount of possible answers. files.length - 1 works too
                * as the only other file in the map should be a .txt containing the question.
                * Going for the safe and "open" option here
                * */
                for (File file : files) {
                    String name = file.getName();
                    if (name.endsWith(".jpg")) {
                        amountFiles++;
                    }
                }

                /*setting the correct answer*/
                answer = ("Data/Type2/Question" + i + "/answer0");

                /*adding bogus answers, randomly selected from the map*/
                possibleAnswers = new ArrayList<>(4);
                ArrayList<String> tempAnswers = new ArrayList<>(amountFiles);
                for (int j = 0; j < amountFiles; j++) {
                    tempAnswers.add("Data/Type2/Question" + i + "/answer" + j);
                }
                addThreeRandomAnswers(tempAnswers);

                Question question1 = new Type2Question(question, answer, possibleAnswers);
                questions.add(question1);

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception exep) {
                Log.d(TAG, "generateType2Questions: error occured");
            }
        }
        return questions;
    }

    private ArrayList<Question> generateType3Questions(int a) {
        /*
        * actually exactly the same as Type 2, but for the sake of easily editable code
        * it's copied in case something must change later on
        * the File System is of course demanding it to be separated, but then again
        * we could easily put both type of questions in the same map, making this method
        * obsolete.
        * */
        ArrayList<Question> questions = new ArrayList<>(a);

        String question;
        String answer;
        ArrayList<String> possibleAnswers;

        FileInputStream input;
        InputStreamReader inputReader;
        BufferedReader bufferedReader;

        for (int i = 0; i < a; i++) {
            try {

                /*Reading question from file*/
                input = context.openFileInput("Data/Type3/Question" + i + "/question.txt");
                inputReader = new InputStreamReader(input);
                bufferedReader = new BufferedReader(inputReader);
                question = bufferedReader.readLine() != null ? bufferedReader.readLine() : "Question not found.";

                /*Counting amount of possible answers*/
                File[] files = new File("Data/Type3/Question" + i).listFiles();
                int amountFiles = 0;
                /*
                * is safe way of counting amount of possible answers. files.length - 1 works too
                * as the only other file in the map should be a .txt containing the question.
                * Going for the safe and "open" option here
                * */
                for (File file : files) {
                    String name = file.getName();
                    if (name.endsWith(".jpg")) {
                        amountFiles++;
                    }
                }

                /*setting the correct answer*/
                answer = ("Data/Type3/Question" + i + "/answer0");

                /*adding bogus answers, randomly selected from the map*/
                possibleAnswers = new ArrayList<>(4);
                ArrayList<String> tempAnswers = new ArrayList<>(amountFiles);
                for (int j = 0; j < amountFiles; j++) {
                    tempAnswers.add("Data/Type3/Question" + i + "/answer" + j);
                }
                addThreeRandomAnswers(tempAnswers);

                Question question1 = new Type3Question(question, answer, possibleAnswers);
                questions.add(question1);

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception exep) {
                Log.d(TAG, "generateType3Questions: error occured");
            }
        }
        return questions;
    }


    private ArrayList<String> addThreeRandomAnswers(ArrayList<String> answers) {
        ArrayList<String> clone = new ArrayList<>(answers);
        clone = (ArrayList<String>) clone.subList(1, clone.size());
        Collections.shuffle(clone);
        clone.add(0, answers.get(0));
        return clone;
    }


}