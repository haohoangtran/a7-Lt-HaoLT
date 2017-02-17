package com.example.tranh.pomodoro;

import android.app.Application;
import android.util.Log;

import com.example.tranh.pomodoro.database.DbContext;
import com.example.tranh.pomodoro.database.models.Task;
import com.example.tranh.pomodoro.settings.SharedPrefs;

/**
 * Created by tranh on 1/14/2017.
 */

public class PomodoroAplication extends Application {
    private static final String TAG=PomodoroAplication.class.toString();
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefs.init(this);
        for (Task task : DbContext.instance.allTask()) {
            Log.w(TAG, String.format("onCreate: %s", task));
        }
    }
}
