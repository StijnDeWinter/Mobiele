package com.example.wolkje.aphasia.model.question;

import java.util.ArrayList;

/**
 * Created by Wolkje on 3/01/2017.
 * The patient is shown a picture (e.g. a candle) and is expected to say out loud
 * what he sees (e.g. "candle!"). The app should record this and make it possible for researchers
 * to easily access this data.
 */

public class Type1Question extends Question {

    public Type1Question(String question, String answer, ArrayList<String> possibleAnswers) {
        super(question, answer, possibleAnswers);
    }
    public Type1Question(String question){
        super(question, null, null);
    }

    @Override
    public String getType() {
        return "type1";
    }

    public String getAnswer() {
        return super.getAnswer();
    }

    public ArrayList<String> getPossibleAnswers() {
        return super.getPossibleAnswers();
    }

    public String getQuestion() {
        return super.getQuestion();
    }
}
