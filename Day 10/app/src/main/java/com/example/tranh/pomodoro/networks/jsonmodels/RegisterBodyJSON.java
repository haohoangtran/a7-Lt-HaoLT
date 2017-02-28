package com.example.tranh.pomodoro.networks.jsonmodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tranh on 1/19/2017.
 */

public class RegisterBodyJSON {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    @Override
    public String toString() {
        return "RegisterBodyJSON{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegisterBodyJSON(String username, String password) {

        this.username = username;
        this.password = password;
    }
}
