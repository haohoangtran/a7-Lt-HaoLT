package com.example.tranh.pomodoro.networks.services;

import com.example.tranh.pomodoro.database.models.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by tranh on 2/24/2017.
 */

public interface TaskActionService {
    @POST("task")
    Call<Task> addNewTask( @Body Task task);

    @GET("task")
    Call<List<Task>> getAllTask();

    @DELETE("task/{id}")
    Call<Void> deleteTask(@Path("id") String id);
    @PUT("task/{id}")
    Call<Void> editTask(@Path("id") String id,@Body Task task);
}
