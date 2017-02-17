package com.example.tranh.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerData;
    private EditText editTextInput;
    private Button buttonSelect;
    private CheckBox checkBox;
    private TextView textView;
    private RadioGroup radioGroup;
    private RatingBar ratingBar;
    private SeekBar seekBar;
    private final Vector<String> s = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getReferences();
        setupUI();
        addListener();
        System.out.println(seekBar.getMax());
        System.out.println(ratingBar.getMax());
        spinnerData.setSelection(2);
        radioGroup.check(R.id.rd_enable);
    }

    private void addListener() {
        spinnerData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, String.format("%d", position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s.add(editTextInput.getText().toString());
                editTextInput.getText().clear();
                setupUI();
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupUI();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rd_enable:
                        spinnerData.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rd_disable:
                        spinnerData.setVisibility(View.INVISIBLE);
                        break;
                }
                setupUI();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            }

        });


    }

    private void setupUI() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                s
        );
        spinnerData.setAdapter(arrayAdapter);
        if (!checkBox.isChecked()) {
            buttonSelect.setVisibility(View.INVISIBLE);
        } else
            buttonSelect.setVisibility(View.VISIBLE);
        textView.setText("Spinner size: " + spinnerData.getAdapter().getCount());

    }

    private void getReferences() {
        spinnerData = (Spinner) findViewById(R.id.sn_Data);
        editTextInput = (EditText) findViewById(R.id.et_Input);
        buttonSelect = (Button) findViewById(R.id.bt_Select);
        checkBox = (CheckBox) findViewById(R.id.cb_check);
        textView = (TextView) findViewById(R.id.tv_size);
        radioGroup = (RadioGroup) findViewById(R.id.rg_CheckSpinner);
        ratingBar = (RatingBar) findViewById(R.id.rt_rate);
        seekBar = (SeekBar) findViewById(R.id.sb_changeStar);
    }


}
