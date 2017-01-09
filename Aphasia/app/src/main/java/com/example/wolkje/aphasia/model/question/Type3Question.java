package com.example.wolkje.aphasia.model.question;

import java.util.ArrayList;

/**
 * Created by Wolkje on 3/01/2017.
 */

public class Type3Question extends Question {
    public Type3Question(String question, String answer, ArrayList<String> possibleAnswers) {
        super(question, answer, possibleAnswers);
    }


    @Override
    public String getType() {
        return "type3";
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
