package com.example.tranh.pomodoro.networks.jsonmodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tranh on 1/18/2017.
 */

public class LoginResponseJSON {
    @SerializedName("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public LoginResponseJSON(String accessToken) {

        this.accessToken = accessToken;
    }
}
