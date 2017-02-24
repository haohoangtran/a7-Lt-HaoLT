package com.example.tranh.pomodoro.settings;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tranh on 1/14/2017.
 */

public class Login {
    private String userName;
    private String passWord;
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Login(String userName, String passWord, String accessToken) {
        this.userName = userName;
        this.passWord = passWord;
        this.accessToken = accessToken;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "Login{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }

    public String getPassWord() {
        return passWord;
    }
}

