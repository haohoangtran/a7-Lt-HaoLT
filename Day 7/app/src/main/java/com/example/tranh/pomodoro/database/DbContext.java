package com.example.tranh.pomodoro.database;

import com.example.tranh.pomodoro.database.models.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tranh on 2/8/2017.
 */

public class DbContext {
    public static final DbContext instance =new DbContext();
    public static String[] colors={"#F44336","#E91E63","#9C27B0","#673AB7",
                                    "#3F51B5","#2196F3","#03A9F4","#009688"};
    public List<Task>allTask(){
        ArrayList<Task> tasks =new ArrayList<>();
        tasks.add(new Task("Study RecyclerView","#EF5350"));

        tasks.add(new Task("$ RecyclerView","#EC407A"));

        tasks.add(new Task("Tay to RecyclerView","#7B1FA2"));

        tasks.add(new Task("Controller","#C51162"));

        tasks.add(new Task("API Study","#2196F3"));
        return tasks;
    }

    private DbContext() {
    }
}
