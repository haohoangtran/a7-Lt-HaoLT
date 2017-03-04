package com.example.tranh.pomodoro.evenbus_event;

/**
 * Created by tranh on 3/4/2017.
 */

public class TimerCommandEvent {
    private TimerCommand timerCommand;

    public TimerCommand getTimerCommand() {
        return timerCommand;
    }

    public TimerCommandEvent(TimerCommand timerCommand) {
        this.timerCommand = timerCommand;
    }
}
