package com.example.tranh.pomodoro.networks.services;

import com.example.tranh.pomodoro.networks.jsonmodels.RegisterResponseJSON;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by tranh on 1/19/2017.
 */

public interface RegisterService {
    @POST("register")
    Call<RegisterResponseJSON> register(@Body RequestBody body);
}
