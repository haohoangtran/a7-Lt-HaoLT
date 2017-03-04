package com.example.tranh.pomodoro.services;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.evenbus_event.TimerCommandEvent;
import com.example.tranh.pomodoro.evenbus_event.TimerTickEvent;
import com.example.tranh.pomodoro.settings.Setting;
import com.example.tranh.pomodoro.settings.SharedPrefs;
import com.example.tranh.pomodoro.utils.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by tranh on 3/4/2017.
 */

public class TimerService extends Service {
    private CountDownTimer coutTimer;
    private final String TAG = TimerService.class.toString();
    private long timeUntilFinished;
    private final Setting SETTING=Util.getSetting();
    private boolean isPause;
    private boolean isStop;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        startTimer(SETTING.getTimeBreak() * 60 * 1000 + 1000, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe
    public void onCommand(TimerCommandEvent event) {
        switch (event.getTimerCommand()){
            case START_TIMER:
                startTimer(SETTING.getTimeBreak() * 60 * 1000 + 1000, 1000);
                break;
            case STOP_TIMER:
                coutTimer.onFinish();
                coutTimer.cancel();
                break;
            case PAUSE_TIME:
                coutTimer.cancel();
                break;
            case RESUME_TIME:
                startTimer(timeUntilFinished, 1000);
                break;
        }
    }

    private void startTimer(long millisInFuture, long countDownInterval) {

        coutTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            public void onTick(long millisUntilFinished) {
                long secondRemaining = millisUntilFinished / 1000;
                int minute = (int) (secondRemaining / 60);
                int second = (int) (secondRemaining - (minute * 60));
                timeUntilFinished = millisUntilFinished;
                TimerTickEvent time=new TimerTickEvent(millisUntilFinished);
                EventBus.getDefault().post(time);
            }

            public void onFinish() {
                TimerTickEvent event=new TimerTickEvent(timeUntilFinished);
                EventBus.getDefault().post(event);
            }

        };
        coutTimer.start();
    }
}
