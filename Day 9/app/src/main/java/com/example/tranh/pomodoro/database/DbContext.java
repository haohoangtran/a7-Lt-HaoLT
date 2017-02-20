package com.example.tranh.pomodoro.database;

import com.example.tranh.pomodoro.database.models.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tranh on 2/8/2017.
 */

public class DbContext {
    public static final DbContext instance = new DbContext();
    public static String[] colors = {"#F44336", "#E91E63", "#9C27B0", "#673AB7",
            "#80DEEA", "#2196F3", "#76FF03", "#009688"};
    private static List<Task> taskList=new ArrayList<>();

    public List<Task> allTask() {
        return taskList;
    }
    public void addTask(Task task){
        taskList.add(task);
    }

    public static void setTaskList(List<Task> taskList) {
        DbContext.taskList = taskList;
    }

    public void removeTask(Task task){
        taskList.remove(task);
    }

    private DbContext() {
    }
}
