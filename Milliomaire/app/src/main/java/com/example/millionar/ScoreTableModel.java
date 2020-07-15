package com.example.millionar;

public class ScoreTableModel {

    private String name;
    private String score;

    public ScoreTableModel() {
        // Default constructor
    }

    public ScoreTableModel(String name, String score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }
}
