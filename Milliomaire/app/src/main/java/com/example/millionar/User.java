package com.example.millionar;

public class User {
    private String Email;
    private String Password;
    private String Name;
    private String key;

    public User(String email, String password, String name, String level) {
        Email = email;
        Password = password;
        Name = name;
        Level = level;
    }

    public User() {

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    private String Level;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
