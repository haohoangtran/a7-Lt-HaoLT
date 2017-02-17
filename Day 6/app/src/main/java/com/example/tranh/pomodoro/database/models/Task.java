package com.example.tranh.pomodoro.database.models;

/**
 * Created by tranh on 2/8/2017.
 */

public class Task {
    private String name;
    private String color;

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Task(String name, String color) {

        this.name = name;
        this.color = color;
    }
}
