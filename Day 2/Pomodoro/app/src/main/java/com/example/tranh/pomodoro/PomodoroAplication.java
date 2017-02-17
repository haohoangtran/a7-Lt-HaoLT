package com.example.tranh.pomodoro;

import android.app.Application;
import android.util.Log;

import com.example.tranh.pomodoro.settings.SharedPrefs;

/**
 * Created by tranh on 1/14/2017.
 */

public class PomodoroAplication extends Application {
    private static final String TAG=PomodoroAplication.class.toString();
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: uyuy");
        SharedPrefs.init(this);
    }
}
