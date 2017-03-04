package com.example.tranh.pomodoro.database.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tranh on 2/8/2017.
 */

public class Task extends RealmObject{
    @SerializedName("name")
    private String name;
    @SerializedName("color")
    private String color;
    @SerializedName("payment_per_hour")
    private float paymentPerHour;
    @SerializedName("done")
    private boolean isDone;

    @SerializedName("local_id")
    @PrimaryKey
    private String id;

    public Task() {
    }

    public Task(String id, String name, String color, float paymentPerHour, boolean isDone) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.paymentPerHour = paymentPerHour;
        this.isDone = isDone;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public float getPaymentPerHour() {
        return paymentPerHour;
    }

    public void setPaymentPerHour(float paymentPerHour) {
        this.paymentPerHour = paymentPerHour;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", paymentPerHour=" + paymentPerHour +
                ", isDone=" + isDone +
                ", id='" + id + '\'' +
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
