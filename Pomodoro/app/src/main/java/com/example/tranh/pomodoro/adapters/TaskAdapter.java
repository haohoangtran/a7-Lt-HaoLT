package com.example.tranh.pomodoro.adapters;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.activities.TimerActivity;
import com.example.tranh.pomodoro.adapters.viewholders.TaskViewHolder;
import com.example.tranh.pomodoro.database.models.Task;
import com.example.tranh.pomodoro.evenbus_event.DataChange;
import com.example.tranh.pomodoro.evenbus_event.TaskAction;
import com.example.tranh.pomodoro.utils.TaskActionEnum;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by tranh on 2/8/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private List<Task> taskList;

    public TaskAdapter(List<Task> list) {
        taskList = list;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //1:create view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_task, parent, false);
        //2: create ViewHolder
        return new TaskViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final TaskViewHolder holder, final int position) {
        //get data on position
        final Task task = taskList.get(position);
        //bind data into view
        final ImageButton button = (ImageButton) holder.itemView.findViewById(R.id.ib_task);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                Intent intent = new Intent(context, TimerActivity.class);
                EventBus.getDefault().post(new TaskAction(task, TaskActionEnum.DEFAULT));
                context.startActivity(intent);
            }
        });
        final CheckBox checkBox = (CheckBox) holder.itemView.findViewById(R.id.cb_task_intasklist);
        View view = holder.itemView.findViewById(R.id.v_color);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
//                    task.setDone(false);

                } else {
                    checkBox.setChecked(true);
//                    task.setDone(true);
                }
            }
        });
        holder.bind(task);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send event
                EventBus.getDefault().post(new TaskAction(task,TaskActionEnum.EDIT));
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                EventBus.getDefault().post(new TaskAction(task,TaskActionEnum.DELETE));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
