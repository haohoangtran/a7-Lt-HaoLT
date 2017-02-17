package com.example.tranh.pomodoro.settings;

/**
 * Created by tranh on 1/14/2017.
 */

public class Login {
    private String userName;
    private String passWord;

    public Login(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "LoginCredendials{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }

    public String getPassWord() {
        return passWord;
    }
}

