package com.example.tranh.pomodoro.fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.activities.TaskActivity;
import com.example.tranh.pomodoro.activities.TimerActivity;
import com.example.tranh.pomodoro.adapters.TaskAdapter;
import com.example.tranh.pomodoro.database.DbContext;
import com.example.tranh.pomodoro.database.models.Task;
import com.example.tranh.pomodoro.networks.NetContext;
import com.example.tranh.pomodoro.networks.services.TaskActionService;
import com.example.tranh.pomodoro.utils.TaskActionEnum;
import com.example.tranh.pomodoro.utils.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment implements TaskAdapter.Buttonclick,TaskDetailFragment.Datachange{
    private int count;
    private final int  MAX_REQUEST=5;
    private static final String TAG =TaskFragment.class.toString() ;
    @BindView(R.id.rv_task)
    RecyclerView rv_task;
    TaskChangeListenner taskChangeListenner;
    private TaskAdapter taskAdapter;
    private  DbContext dbContext;
    ProgressDialog dialog;

    public TaskFragment() {

    }
    public void getAllTask() {
        dialog = ProgressDialog.show(getContext(), getString(R.string.loadding),
                getString(R.string.please_wait), true);
        dbContext=new DbContext(getContext());
        TaskActionService getAllTaskService = NetContext.instance.create(TaskActionService.class);
        getAllTaskService.getAllTask().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                List<Task> tasks=response.body();
                if(tasks!=null){
                    dbContext.removeAll();
                    for (int i = 0; i < tasks.size(); i++) {
                        dbContext.addOrUpdate(tasks.get(i));
                    }
                    taskAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Log.e(TAG, String.format("onFailure: %s", t.toString()) );
                Toast.makeText(getContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });



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
        getAllTask();
        taskAdapter = new TaskAdapter(dbContext.allPersions());
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
                TaskDetailFragment taskDetailFragment=new TaskDetailFragment();
                taskDetailFragment.setTittle(getString(R.string.edit));
                taskDetailFragment.setTask(task);
                taskDetailFragment.setTaskAction(TaskActionEnum.EDIT);
                ((TaskActivity)getActivity()).replaceFragment(taskDetailFragment,true);
            }
        });
        taskAdapter.setTaskItemClickDelete(new TaskAdapter.TaskItemClickDelete() {
            @Override
            public void onItemClick(final Task task) {
                count=0;
                Log.e(TAG, String.format("onItemClick: 1 %s", task.toString()) );
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, final int which) {
                               TaskActionService deleteTask=NetContext.instance.create(TaskActionService.class);
                                deleteTask.deleteTask(task.getId()).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Log.e(TAG, String.format("onResponse: %s", task.toString()) );
                                        dbContext.remove(task);
                                        taskAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        while (count++<MAX_REQUEST) {
                                            Toast.makeText(getContext(), String.format("Lỗi! Thử lại lần %d", count), Toast.LENGTH_SHORT).show();
                                            Util.enqueueWithRetry(call, this);
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
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
        startActivity(intent);
    }

    @Override
    public void onDatachangerListener() {
        taskAdapter.notifyDataSetChanged();
        List<Task> tasks1=dbContext.allPersions();
        for (int i = 0; i < tasks1.size(); i++) {
            Log.e(TAG, String.format("onDatachangerListener: %s", tasks1.get(i).toString()) );
        }
    }
}
