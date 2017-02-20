package com.example.tranh.pomodoro.networks.services;

import com.example.tranh.pomodoro.database.models.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by tranh on 2/20/2017.
 */

public interface GetAllTask {
    @GET("task")
    Call<List<Task>> getAllTask(@Header("Authorization") String header);
}
