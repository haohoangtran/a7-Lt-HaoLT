package com.example.tranh.pomodoro;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.example.tranh.pomodoro.activities.TimerActivity;
import com.example.tranh.pomodoro.database.DbContext;
import com.example.tranh.pomodoro.database.models.Task;
import com.example.tranh.pomodoro.evenbus_event.TimerCommand;
import com.example.tranh.pomodoro.evenbus_event.TimerCommandEvent;
import com.example.tranh.pomodoro.services.TimerService;
import com.example.tranh.pomodoro.settings.SharedPrefs;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by tranh on 1/14/2017.
 */

public class PomodoroAplication extends Application {
    private static final String TAG=PomodoroAplication.class.toString();
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefs.init(this);
        Intent intent=new Intent(this, TimerService.class);
        startService(intent);
    }

}
