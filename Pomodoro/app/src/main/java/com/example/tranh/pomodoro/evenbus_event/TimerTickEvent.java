package com.example.tranh.pomodoro.evenbus_event;

/**
 * Created by tranh on 3/4/2017.
 */

public class TimerTickEvent {
    private long tick;
    public enum Status{
        STOP,
        PAUSE
    }
    private Status status;

    public long getTick() {
        return tick;
    }

    public TimerTickEvent(long tick) {
        this.tick = tick;
    }

    public TimerTickEvent(long tick, Status status) {
        this.tick = tick;
        this.status = status;
    }
}
