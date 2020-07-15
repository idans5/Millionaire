package com.example.millionar;

public class Config {

    private User user;
    private String LastScore;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private static Config instance = null;

    public String getLastScore() {
        return LastScore;
    }

    public void setLastScore(String lastScore) {
        LastScore = lastScore;
    }


    public static Config getInstance(){
        if(instance == null){
            instance = new Config();
        }
        return instance;
    }
}
