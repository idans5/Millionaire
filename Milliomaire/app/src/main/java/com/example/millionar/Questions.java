package com.example.millionar;

import androidx.annotation.NonNull;

import java.util.List;

public class Questions {
    private List<String> answers;
    private String correctIndex;
    private String question;

    public Questions(List<String> answers, String correctIndex, String question) {
        this.answers = answers;
        this.correctIndex = correctIndex;
        this.question = question;
    }

    public Questions()
    {

    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getCorrectIndex() {
        return correctIndex;
    }

    public void setCorrectIndex(String correctIndex) {
        this.correctIndex = correctIndex;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
