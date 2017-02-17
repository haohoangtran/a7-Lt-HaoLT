package com.example.tranh.pomodoro.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.adapters.viewholders.TaskViewHolder;
import com.example.tranh.pomodoro.database.DbContext;
import com.example.tranh.pomodoro.database.models.Task;

/**
 * Created by tranh on 2/8/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //1:create view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView=layoutInflater.inflate(R.layout.item_task,parent,false);
        //2: create ViewHolder
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        //get data on position
        Task task = DbContext.instance.allTask().get(position);

        //bind data into view
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return DbContext.instance.allTask().size();
    }
}
