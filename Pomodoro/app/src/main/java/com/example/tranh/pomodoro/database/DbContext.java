package com.example.tranh.pomodoro.database;

import android.content.Context;
import android.util.Log;

import com.example.tranh.pomodoro.database.models.Task;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.content.ContentValues.TAG;

/**
 * Created by tranh on 2/8/2017.
 */

public class DbContext {
    public static String[] colors = {"#F44336", "#E91E63", "#9C27B0", "#673AB7",
            "#80DEEA", "#2196F3", "#76FF03", "#009688"};

    private Realm realm;

    public Realm getRealm() {
        return realm;
    }

    public DbContext(Context context) {
        Realm.init(context);
        this.realm = Realm.getDefaultInstance();
    }

    public Task add(Task task) {
        realm.beginTransaction();
        Task newTask = realm.copyToRealm(task);
        realm.commitTransaction();
        return newTask;
    }

    public void update(Task old, String color, String name, float payment) {
        realm.beginTransaction();
        old.setName(name);
        old.setColor(color);
        old.setPaymentPerHour(payment);
        realm.commitTransaction();
    }

    public void addOrUpdate(Task task) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(task);
        realm.commitTransaction();
    }

    public void remove(final Task task) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Task> result = realm.where(Task.class).equalTo("id", task.getId()).findAll();
                result.deleteAllFromRealm();
            }
        });
    }

    public List<Task> allPersions() {
        String TAG = DbContext.class.toString();
        List<Task> tasks = realm.where(Task.class).findAll();
        Log.e(TAG, String.format("allPersions: %s", tasks.size()));
        return realm.where(Task.class).findAll();
    }

    public void removeAll() {
        realm.beginTransaction();
        realm.delete(Task.class);
        realm.commitTransaction();
    }

}
