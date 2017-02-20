package com.example.tranh.pomodoro.adapters.viewholders;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.activities.TimerActivity;
import com.example.tranh.pomodoro.database.models.Task;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by tranh on 2/8/2017.
 */

public class TaskViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.v_color)
    View vTaskColor;
    @BindView(R.id.tv_task_name)
    TextView tvTaskname;
    @BindView(R.id.ib_task)
    ImageButton imageBtTask;
    @BindView(R.id.cb_task_intasklist)
    CheckBox checkBox;

    public TaskViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
    public void bind(Task task){
        //1: bind color
        //vTaskColor.setBackgroundColor(Color.parseColor(task.getColor()));
        GradientDrawable drawable= (GradientDrawable) vTaskColor.getBackground();
        drawable.setColor(Color.parseColor(task.getColor()));
        if (task.isDone()){
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);
        }
        //2:Bind task name
        tvTaskname.setText(task.getName());
    }



}
