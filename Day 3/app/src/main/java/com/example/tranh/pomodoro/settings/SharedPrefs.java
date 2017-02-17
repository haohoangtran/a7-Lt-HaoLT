package com.example.tranh.pomodoro.settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by tranh on 1/14/2017.
 */

public class SharedPrefs {
    private static final String SHARE_PREFS_NAME = "SP";
    private static final String LOGIN_KEY = "login";
    private static final String SETTING_KEY = "setting";
    private SharedPreferences sharedPreferences;
    private static SharedPrefs instance;


    public SharedPrefs(Context context) {
        this.sharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME,
                context.MODE_PRIVATE
        );
    }

    public static void init(Context context) {
        SharedPrefs.instance = new SharedPrefs(context);
    }

    public static SharedPrefs getInstance() {
        return instance;
    }

    public void put(Login login) {
        Gson gson = new Gson();
        String json = gson.toJson(login);
        sharedPreferences.edit().putString(LOGIN_KEY, json).commit();
    }

    public void put(Setting setting){

        Gson gson = new Gson();
        String json = gson.toJson(setting);
        sharedPreferences.edit().putString(SETTING_KEY ,json).commit();
    }
    public Setting getSettings(){
        String settingJSON = this.sharedPreferences.getString(SETTING_KEY, null);
        if (settingJSON == null) {
            return null;
        } else {
            return (new Gson().fromJson(settingJSON, Setting.class));
        }
    }
    public Login getLogin() {
        String loginJSON = this.sharedPreferences.getString(LOGIN_KEY, null);
        if (loginJSON == null) {
            return null;
        } else {
            return (new Gson().fromJson(loginJSON, Login.class));
        }
    }
}
