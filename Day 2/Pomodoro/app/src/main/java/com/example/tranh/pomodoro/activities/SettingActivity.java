package com.example.tranh.pomodoro.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.settings.Setting;
import com.example.tranh.pomodoro.settings.SharedPrefs;

import java.util.Vector;

public class SettingActivity extends AppCompatActivity {
    private static final String TAG = SettingActivity.class.toString();
    SeekBar seekBarTimeWork;
    SeekBar seekBarTimeBreak;
    SeekBar seekBarBreakLong;
    Spinner spinner;
    Button button;
    TextView textViewWork;
    TextView textBreak;
    TextView textLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getRef();
        setupUI();
        addListenner();
        if (SharedPrefs.getInstance().getSettings() != null) {
            getSetting();
        } else {
            defaultSetting();
            setTextforTv();
        }
    }

    private void defaultSetting() {
        seekBarTimeBreak.setProgress(20);
        seekBarBreakLong.setProgress(20);
        seekBarTimeWork.setProgress(20);
        spinner.setSelection(0);
    }

    private void setupUI() {
        Integer[] items = new Integer[]{0, 1, 2, 3, 4, 5, 6};
        ArrayAdapter<Integer> integerArrayAdapter = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, items);
        spinner.setAdapter(integerArrayAdapter);
    }

    private void addListenner() {
        seekBarBreakLong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                setTextforTv();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                saveSettings();
            }
        });
        seekBarTimeWork.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                setTextforTv();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                saveSettings();
            }
        });
        seekBarTimeBreak.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                setTextforTv();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                saveSettings();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                saveSettings();
                setTextforTv();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultSetting();
                setTextforTv();
            }
        });
    }

    public void setTextforTv() {
        textViewWork.setText(String.format("Work time %s minutes", seekBarTimeWork.getProgress()));
        textLong.setText(String.format("Long break %s minutes", seekBarBreakLong.getProgress()));
        textBreak.setText(String.format("Break %s minutes", seekBarTimeBreak.getProgress()));
    }

    private void saveSettings() {
        int work = seekBarTimeWork.getProgress();
        int timeBreak = seekBarTimeBreak.getProgress();
        int breakLong = seekBarBreakLong.getProgress();
        int number = spinner.getSelectedItemPosition();
        SharedPrefs.getInstance().put(new Setting(work, timeBreak, breakLong, number));
    }

    private void getSetting() {
        Setting setting = SharedPrefs.getInstance().getSettings();
        seekBarTimeWork.setProgress(setting.getTimeWork());
        seekBarTimeBreak.setProgress(setting.getTimeBreak());
        seekBarBreakLong.setProgress(setting.getTimeBreakLong());
        spinner.setSelection(setting.getNumberBreak());
    }


    private void getRef() {
        seekBarTimeWork = (SeekBar) findViewById(R.id.sb_timeWork);
        seekBarTimeBreak = (SeekBar) findViewById(R.id.sb_timeBreak);
        seekBarBreakLong = (SeekBar) findViewById(R.id.sb_timeBreakLong);
        spinner = (Spinner) findViewById(R.id.sp_longBreakAfter);
        button = (Button) findViewById(R.id.bt_restore);
        textViewWork = (TextView) findViewById(R.id.tv_timeWork);
        textBreak = (TextView) findViewById(R.id.tv_timeBreak);
        textLong = (TextView) findViewById(R.id.tv_timeBreakLong);
    }

}
