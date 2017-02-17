package com.example.tranh.pomodoro.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.adapters.ColorApdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment{
    private ColorApdapter colorApdapter;
    @BindView(R.id.rv_color)
    RecyclerView rv_color;
    @BindView(R.id.ed_pertask)
    EditText perTask;
    TaskFragment.TaskDetailChangerListenner taskDetailChangerListener;

    public TaskDetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        taskDetailChangerListener= (TaskFragment.TaskDetailChangerListenner) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void setupUI(View view) {
        ButterKnife.bind(this,view);
        colorApdapter=new ColorApdapter();
        rv_color.setAdapter(colorApdapter);
        rv_color.setLayoutManager(new GridLayoutManager(this.getContext(),4));
        perTask.setText("0.0");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_task_detail, container, false);
        setupUI(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_task,menu);
        menu.removeItem(R.id.action_settings);
    }
}
