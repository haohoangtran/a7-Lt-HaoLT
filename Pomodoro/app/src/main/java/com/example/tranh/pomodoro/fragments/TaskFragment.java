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
import com.example.tranh.pomodoro.evenbus_event.DataChange;
import com.example.tranh.pomodoro.evenbus_event.TaskAction;
import com.example.tranh.pomodoro.networks.NetContext;
import com.example.tranh.pomodoro.networks.services.TaskActionService;
import com.example.tranh.pomodoro.utils.TaskActionEnum;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
public class TaskFragment extends Fragment {
    private int count;
    private final int  MAX_REQUEST=5;
    private static final String TAG =TaskFragment.class.toString() ;
    @BindView(R.id.rv_task)
    RecyclerView rv_task;
    private TaskAdapter taskAdapter;
    private  DbContext dbContext;
    ProgressDialog dialog;

    public TaskFragment() {

    }
    public void getAllTask() {
        dialog.show();
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

                }
                dialog.dismiss();
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
        EventBus.getDefault().register(this);
        dialog = ProgressDialog.show(getContext(), getString(R.string.loadding),
                getString(R.string.please_wait), true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
    }
    @Subscribe
    public void onDeleteTask(TaskAction taskAction){
        if (taskAction.getActionEnum()==TaskActionEnum.DELETE){
            deleteTask(taskAction.getTask());
        }
        if (taskAction.getActionEnum()==TaskActionEnum.EDIT){

            TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
            taskDetailFragment.setTittle(getString(R.string.edit));
            taskDetailFragment.setTask(taskAction.getTask());
            taskDetailFragment.setTaskAction(TaskActionEnum.EDIT);
            EventBus.getDefault().post(new FragmentChange(taskDetailFragment,true));
        }
    }
        public void deleteTask(final Task task){
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.delete_title)
                    .setMessage(R.string.delete_question)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog1, final int which) {
                            dialog.show();
                            TaskActionService deleteTask=NetContext.instance.create(TaskActionService.class);
                            deleteTask.deleteTask(task.getId()).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Log.e(TAG, String.format("onResponse: %s", task.toString()) );
                                    dbContext.remove(task);
                                    taskAdapter.notifyDataSetChanged();
                                    Toast.makeText(getContext(), R.string.delete_complete, Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(getContext(), R.string.delete_failed, Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.fab)
    void onFabClick() {
        TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
        taskDetailFragment.setTittle(getString(R.string.task_detail));
        taskDetailFragment.setTaskAction(TaskActionEnum.ADD_NEW);
        EventBus.getDefault().post(new FragmentChange(taskDetailFragment,true));
    }
    @Subscribe
    public void onDatachange(DataChange dataChange){
        getAllTask();
        Log.e(TAG, "onDatachange: datachange" );
        Toast.makeText(getContext(), dataChange.getToastNotification(), Toast.LENGTH_SHORT).show();
    }
}