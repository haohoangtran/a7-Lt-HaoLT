package com.example.tranh.pomodoro.evenbus_event;

import com.example.tranh.pomodoro.database.models.Task;
import com.example.tranh.pomodoro.utils.TaskActionEnum;

/**
 * Created by tranh on 3/4/2017.
 */

public class TaskAction {
    private Task task;
    private TaskActionEnum actionEnum;

    public Task getTask() {
        return task;
    }

    public TaskActionEnum getActionEnum() {
        return actionEnum;
    }

    public TaskAction(Task task, TaskActionEnum actionEnum) {

        this.task = task;
        this.actionEnum = actionEnum;
    }
}
