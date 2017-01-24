package com.example.wolkje.aphasia.model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.wolkje.aphasia.model.question.Question;
import com.example.wolkje.aphasia.model.question.Type1Question;
import com.example.wolkje.aphasia.model.question.Type2Question;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static android.content.ContentValues.TAG;

/**
 * Created by Wolkje on 3/01/2017 at 11:13 at 11:14.
 * Part of ${PACKAGE_NAME}.
 */

class QuestionMaker {

    private Context context;

    QuestionMaker(Context context) {
        this.context = context;

    }


    /* Checks if external storage is available for read and write */
    private boolean isExternalStorageWritable() throws IOException {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            throw new IOException("Can't write to SD card --> questionings can't be saved.");
            //should never occur
        } else {
            throw new IOException("Can't read SD card --> can't make questionings.");
        }
    }

    ArrayList<Question> generateQuestionList(String type, int a) throws IOException {
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

        isExternalStorageWritable();
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

        String question = "";
        String answer = "";

        File sdcard = Environment.getExternalStorageDirectory();
        StringBuilder questionBuilder = new StringBuilder();
        BufferedReader bufferedReader;

        for (int i = 1; i <= a; i++) {

            try {

                /*Reading question from file*/
                File questionfile = new File(sdcard + File.separator + "questions" + File.separator + "type1" + File.separator + "question" + i + File.separator, "question.txt");
                try {
                    bufferedReader = new BufferedReader(new FileReader(questionfile));
                    questionBuilder.append(bufferedReader.readLine());
                    bufferedReader.close();
                    question = questionBuilder.toString();
                    questionBuilder.setLength(0);
                } catch (Exception e) {
                    Log.d(TAG, "generateType1Questions: error while reading the questionfile");
                    e.printStackTrace();
                }
                try {
                    answer = ("type1question" + i + "answer");
                } catch (Exception ex) {
                    Log.d(TAG, "generateType2Questions: error while setting answerstring");
                    ex.printStackTrace();
                }
                Question question1 = new Type1Question(question, answer, null);
                Log.d(TAG, "generateType1Questions: " + question);
                questions.add(question1);
            } catch (Exception exep) {
                Log.d(TAG, "generateType1Questions: problems finding the file on SD");
                exep.printStackTrace();
            }
        }
        return questions;
    }

    private ArrayList<Question> generateType2Questions(int a) {
        /*
        * Tested, works.
        * */

        ArrayList<Question> questions = new ArrayList<>(a);

        String question = "";
        String answer = "";
        ArrayList<String> possibleAnswers = new ArrayList<>();

        File sdcard = Environment.getExternalStorageDirectory();
        StringBuilder questionBuilder = new StringBuilder();
        BufferedReader bufferedReader;

        for (int i = 1; i <= a; i++) {

            try {

                /*Reading question from file*/
                File questionfile = new File(sdcard + File.separator + "questions" + File.separator + "type2" + File.separator + "question" + i + File.separator, "question.txt");
                try {
                    bufferedReader = new BufferedReader(new FileReader(questionfile));
                    questionBuilder.append(bufferedReader.readLine());
                    bufferedReader.close();
                    question = questionBuilder.toString();
                    Log.d(TAG, "generateType2Questions: " + questionBuilder.toString());
                    questionBuilder.setLength(0);
                } catch (Exception e) {
                    Log.d(TAG, "generateType1Questions: error while reading the questionfile");
                    e.printStackTrace();
                }
                try {
                    answer = ("type2question" + i + "answer0");
                } catch (Exception ex) {
                    Log.d(TAG, "generateType2Questions: error while setting answerstring");
                    ex.printStackTrace();
                }
                try {
                    File[] files = new File(sdcard + File.separator + "questions" + File.separator + "type2" + File.separator + "question" + i + File.separator).listFiles();
                    int amountFiles = 0;
                    /*Counting amount of possible answers
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
                 /*adding bogus answers, randomly selected from the map*/
                    possibleAnswers = new ArrayList<>(4);
                    ArrayList<String> tempAnswers = new ArrayList<>(amountFiles);
                    for (int j = 0; j < amountFiles; j++) {
                        tempAnswers.add("type2question" + i + "answer" + j);
                    }
                    ArrayList<String> tempList = addThreeRandomAnswers(tempAnswers);
                    for (int k = 0; k < tempList.size(); k++) {
                        possibleAnswers.add(tempList.get(k));
                    }
                } catch (Exception exe) {
                    Log.d(TAG, "generateType2Questions: problem while setting bogus answers");
                    exe.printStackTrace();
                }
                Question question1 = new Type2Question(question, answer, possibleAnswers);
                questions.add(question1);
            } catch (Exception exep) {
                Log.d(TAG, "generateType1Questions: problems finding the file on SD");
                exep.printStackTrace();
            }
        }
        return questions;
    }

    private ArrayList<Question> generateType3Questions(int a) {
        
        /*
        * Not tested, unknown result.
        * 
        * */
        
        /*
        * actually exactly the same as Type 2, but for the sake of easily editable code
        * it's copied in case something must change later on
        * the File System is of course demanding it to be separated, but then again
        * we could easily put both type of questions in the same map, making this method
        * obsolete.
        * */
        ArrayList<Question> questions = new ArrayList<>(a);

        String question = "";
        String answer = "";
        ArrayList<String> possibleAnswers = new ArrayList<>();

        File sdcard = Environment.getExternalStorageDirectory();
        StringBuilder questionBuilder = new StringBuilder();
        BufferedReader bufferedReader;

        for (int i = 1; i <= a; i++) {

            try {

                /*Reading question from file*/
                File questionfile = new File(sdcard + File.separator + "questions" + File.separator + "type3" + File.separator + "question" + i + File.separator, "question.txt");
                try {
                    bufferedReader = new BufferedReader(new FileReader(questionfile));
                    questionBuilder.append(bufferedReader.readLine());
                    bufferedReader.close();
                    question = questionBuilder.toString();
                    questionBuilder.setLength(0);
                } catch (Exception e) {
                    Log.d(TAG, "generateType1Questions: error while reading the questionfile");
                    e.printStackTrace();
                }
                try {
                    answer = (sdcard + File.separator + "questions" + File.separator + "type3" + File.separator + "question" + i + File.separator + "type3question" + i + "answer0");/*Counting amount of possible answers*/
                } catch (Exception ex) {
                    Log.d(TAG, "generateType2Questions: error while setting answerstring");
                    ex.printStackTrace();
                }
                try {
                    File[] files = new File(sdcard + File.separator + "questions" + File.separator + "type3" + File.separator + "question" + i + File.separator).listFiles();
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
                 /*adding bogus answers, randomly selected from the map*/
                    possibleAnswers = new ArrayList<>(4);
                    ArrayList<String> tempAnswers = new ArrayList<>(amountFiles);
                    for (int j = 0; j < amountFiles; j++) {
                        tempAnswers.add("type3question" + i + "answer" + j);
                    }
                    ArrayList<String> tempList = addThreeRandomAnswers(tempAnswers);
                    for (int k = 0; k < tempList.size(); k++) {
                        possibleAnswers.add(tempList.get(k));
                    }
                } catch (Exception exe) {
                    Log.d(TAG, "generateType2Questions: problem while setting bogus answers");
                    exe.printStackTrace();
                }
                Question question1 = new Type2Question(question, answer, possibleAnswers);
                questions.add(question1);
            } catch (Exception exep) {
                Log.d(TAG, "generateType1Questions: problems finding the file on SD");
                exep.printStackTrace();
            }
        }
        return questions;
    }


    private ArrayList<String> addThreeRandomAnswers(ArrayList<String> answers) {
        /*
        *  Tested, works
        * */
        ArrayList<String> result = new ArrayList<>(4);
        ArrayList<String> clone = new ArrayList<>(answers.subList(1, answers.size()));
        Collections.shuffle(clone);
        for (int i = 0; i < clone.size(); i++) {
            result.add(i, clone.get(i));
        }
        result.add(answers.get(0));
        Collections.shuffle(result);
        return result;
    }


}