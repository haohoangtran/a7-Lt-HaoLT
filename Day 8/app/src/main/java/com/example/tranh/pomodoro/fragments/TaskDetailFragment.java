package com.example.tranh.pomodoro.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.activities.TaskActivity;
import com.example.tranh.pomodoro.adapters.ColorApdapter;
import com.example.tranh.pomodoro.adapters.TaskAdapter;
import com.example.tranh.pomodoro.database.DbContext;
import com.example.tranh.pomodoro.database.models.Task;
import com.example.tranh.pomodoro.decorations.TaskColorDecoration;
import com.example.tranh.pomodoro.utils.TaskActionEnum;
import com.example.tranh.pomodoro.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {
    private ColorApdapter colorApdapter;
    @BindView(R.id.rv_color)
    RecyclerView rv_color;
    @BindView(R.id.ed_pertask)
    EditText et_perTask;
    @BindView(R.id.ed_nametask)
    EditText et_nameTask;
    private String tittle;
    public Task task;

    public TaskActionEnum getTaskAction() {
        return taskAction;
    }

    public void setTaskAction(TaskActionEnum taskAction) {
        this.taskAction = taskAction;
    }

    private TaskActionEnum taskAction;


    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public TaskDetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        colorApdapter = new ColorApdapter();
        rv_color.setAdapter(colorApdapter);
        rv_color.setLayoutManager(new GridLayoutManager(this.getContext(), 4));
        rv_color.addItemDecoration(new TaskColorDecoration());
        if (task != null) {
            et_nameTask.setText(task.getName());
            et_perTask.setText(String.format("%.1f", task.getPaymentPerHour()));
            colorApdapter.setSelectColor(task.getColor());
        }
        if (getActivity() instanceof TaskActivity) {
            ((TaskActivity) getActivity()).getSupportActionBar().setTitle(tittle);
            Log.e(TAG, String.format("setupUI: %s", tittle));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        setupUI(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_task, menu);
        menu.removeItem(R.id.action_settings);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mni_ok) {
            Util.hideSoftKeyboard(getActivity());
            String taskname = et_nameTask.getText().toString();
            String payment = et_perTask.getText().toString();
            float paymentPerHour;
            try {
                if (payment.isEmpty()) {
                    paymentPerHour = 0;
                } else {
                    paymentPerHour = Float.parseFloat(payment);
                }
            } catch (NumberFormatException e) {
                Log.e(TAG, "onOptionsItemSelected: Not a number");
                String[] str = payment.split(",");
                // nó đổi . thành , nên làm ntn
                paymentPerHour = Float.parseFloat(str[0] + "." + str[1]);
            }
            String color = colorApdapter.getSelectColor();
            Task newTask = new Task(taskname, color, paymentPerHour);
            if (!taskname.isEmpty()) {
                if (taskAction == TaskActionEnum.ADD_NEW) {
                    DbContext.instance.addTask(newTask);
                }
                if (taskAction == TaskActionEnum.EDIT) {
                    DbContext.instance.removeTask(task);
                    DbContext.instance.addTask(newTask);
                }
                getActivity().onBackPressed();
            } else {
                et_nameTask.setError(getString(R.string.name_not_empty));
            }
        }
        return false;
    }


}
