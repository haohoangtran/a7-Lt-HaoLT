package com.example.tranh.pomodoro.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.activities.TaskActivity;
import com.example.tranh.pomodoro.activities.TimerActivity;
import com.example.tranh.pomodoro.adapters.viewholders.TaskViewHolder;
import com.example.tranh.pomodoro.database.DbContext;
import com.example.tranh.pomodoro.database.models.Task;

import static android.content.ContentValues.TAG;

/**
 * Created by tranh on 2/8/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    public interface TaskItemClickListener {
        void onItemClick(Task task);
    }
    private TaskItemClickListener taskItemClickListener;

    public TaskAdapter() {

    }
    public interface Buttonclick{
        void onClick();
    }



    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //1:create view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_task, parent, false);
        //2: create ViewHolder
        return new TaskViewHolder(itemView);
    }

    public void setTaskItemClickListener(TaskItemClickListener taskItemClickListener1) {
        this.taskItemClickListener = taskItemClickListener1;
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, final int position) {
        //get data on position

        final Task task = DbContext.instance.allTask().get(position);
        //bind data into view
        final ImageButton button= (ImageButton) holder.itemView.findViewById(R.id.ib_task);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=holder.itemView.getContext();
                Intent intent=new Intent(context,TimerActivity.class);
                context.startActivity(intent);
            }
        });
        final CheckBox checkBox= (CheckBox) holder.itemView.findViewById(R.id.cb_task_intasklist);
        View view=holder.itemView.findViewById(R.id.v_color);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()){
                    checkBox.setChecked(false);
                    Log.d(TAG, "onClick: hiện "+task.isDone());
                    task.setDone(false);

                }else {
                    checkBox.setChecked(true);
                    Log.d(TAG, "onClick: ẩn "+task.isDone());
                    task.setDone(true);
                }
            }
        });
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.e(TAG, "onCheckedChanged: aaaaa" );
//            }
//        });
        holder.bind(task);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // send event
                    if (taskItemClickListener != null) {
                        taskItemClickListener.onItemClick(task);
                    }
                }
        });
    }

    @Override
    public int getItemCount() {
        return DbContext.instance.allTask().size();
    }
}
