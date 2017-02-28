package com.example.tranh.pomodoro.settings;

/**
 * Created by tranh on 1/16/2017.
 */

public class Setting {
    private int timeWork;
    private int timeBreak;
    private int timeBreakLong;
    private int numberBreak;

    public int getTimeWork() {
        return timeWork;
    }

    public int getTimeBreak() {
        return timeBreak;
    }

    public int getTimeBreakLong() {
        return timeBreakLong;
    }

    public int getNumberBreak() {
        return numberBreak;
    }

    public Setting(int timeWork, int timeBreak, int timeBreakLong, int numberBreak) {

        this.timeWork = timeWork;
        this.timeBreak = timeBreak;
        this.timeBreakLong = timeBreakLong;
        this.numberBreak = numberBreak;
    }
}
