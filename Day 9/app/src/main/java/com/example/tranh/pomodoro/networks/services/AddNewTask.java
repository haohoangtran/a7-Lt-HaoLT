package com.example.tranh.pomodoro.networks.services;

import com.example.tranh.pomodoro.database.models.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by tranh on 2/20/2017.
 */

public interface AddNewTask {
 @POST("task")
    Call<Task> addNewTask(@Header("Content-Type")String type, @Header("Authorization")String token,@Body Task task);
}
