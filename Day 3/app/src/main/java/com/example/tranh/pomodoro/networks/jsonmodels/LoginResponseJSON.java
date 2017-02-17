package com.example.tranh.pomodoro.networks.jsonmodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tranh on 1/18/2017.
 */

public class LoginResponseJSON {
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("token")
    private String token;

    public LoginResponseJSON(int code, String message, String token) {
        this.code = code;
        this.message = message;
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponseJSON{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
