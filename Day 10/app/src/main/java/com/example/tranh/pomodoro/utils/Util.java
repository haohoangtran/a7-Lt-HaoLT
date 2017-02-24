package com.example.tranh.pomodoro.utils;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.tranh.pomodoro.networks.services.TaskActionService;
import com.example.tranh.pomodoro.settings.Setting;
import com.example.tranh.pomodoro.settings.SharedPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by tranh on 2/15/2017.
 */

public class Util {
    private static final String TAG = Util.class.toString();

    public static void hideSoftKeyboard(Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }
    public static Setting getSetting() {
        Setting setting = SharedPrefs.getInstance().getSettings();
        if (setting!=null) {
            return setting;
        }else {
            return new Setting(20,20,20,0);// mặc định
        }
    }
    private static int count;
    public static <T> void enqueueWithRetry(Call<T> call,Callback<T> callback) {
               call.clone().enqueue(callback);
    }

}
