package com.example.tranh.pomodoro.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.activities.TaskActivity;
import com.example.tranh.pomodoro.activities.TimerActivity;
import com.example.tranh.pomodoro.adapters.TaskAdapter;
import com.example.tranh.pomodoro.database.models.Task;
import com.example.tranh.pomodoro.utils.TaskActionEnum;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment implements TaskAdapter.Buttonclick{

    private static final String TAG =TaskFragment.class.toString() ;
    @BindView(R.id.rv_task)
    RecyclerView rv_task;
    TaskChangeListenner taskChangeListenner;
    private TaskAdapter taskAdapter;

    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        taskChangeListenner= (TaskChangeListenner) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        taskAdapter = new TaskAdapter();
        rv_task.setAdapter(taskAdapter);
        rv_task.setLayoutManager(new LinearLayoutManager(this.getContext()));
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setTitle(R.string.title_activity_task);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        rv_task.addItemDecoration(dividerItemDecoration);
        setHasOptionsMenu(true);
        taskAdapter.setTaskItemClickListener(new TaskAdapter.TaskItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Log.e(TAG, String.format("onItemClick: %s", task) );
                TaskDetailFragment taskDetailFragment=new TaskDetailFragment();
                taskDetailFragment.setTittle(getString(R.string.edit));
                taskDetailFragment.setTask(task);
                taskDetailFragment.setTaskAction(TaskActionEnum.EDIT);
                ((TaskActivity)getActivity()).replaceFragment(taskDetailFragment,true);
            }
        });
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
        taskDetailFragment.setTittle(getString(R.string.task_detail));
        taskDetailFragment.setTaskAction(TaskActionEnum.ADD_NEW);
        taskChangeListenner.onTaskChangeListenner(taskDetailFragment,true);
    }


    @Override
    public void onClick() {
        Intent intent=new Intent(getContext(),TimerActivity.class);
        Log.e(TAG, "onClick: vào" );
        startActivity(intent);
    }
}
