package com.example.tranh.pomodoro.networks.jsonmodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tranh on 1/19/2017.
 */

public class RegisterResponseJSON {
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("token")
    private String token;

    @Override
    public String toString() {
        return "RegisterResponseJSON{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RegisterResponseJSON(int code, String message, String token) {

        this.code = code;
        this.message = message;
        this.token = token;
    }
}
