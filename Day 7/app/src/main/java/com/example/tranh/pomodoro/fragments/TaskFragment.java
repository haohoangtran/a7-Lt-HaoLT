package com.example.tranh.pomodoro.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.activities.TaskActivity;
import com.example.tranh.pomodoro.adapters.TaskAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {
    @BindView(R.id.rv_task)
    RecyclerView rv_task;
    TaskChangerListenner taskChangerListenner;
    TaskDetailChangerListenner taskDetailChangerListenner;

    private TaskAdapter taskAdapter;

    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            taskChangerListenner = (TaskChangerListenner) context;
            taskDetailChangerListenner= (TaskDetailChangerListenner) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        taskDetailChangerListenner.onTaskDetailChangerListenner();
    }
    public interface TaskChangerListenner{
        public void onTaskChangerListenner();
    }
    public interface TaskDetailChangerListenner{
        public void onTaskDetailChangerListenner();
    }


}
