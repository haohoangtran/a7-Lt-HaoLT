package com.example.tranh.pomodoro.evenbus_event;

/**
 * Created by tranh on 3/4/2017.
 */

public class DataChange {
    private String toastNotification;

    public String getToastNotification() {
        return toastNotification;
    }

    public DataChange(String toastNotification) {

        this.toastNotification = toastNotification;
    }
}
