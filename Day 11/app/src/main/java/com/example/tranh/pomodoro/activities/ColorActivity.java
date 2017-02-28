package com.example.tranh.pomodoro.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.adapters.ColorApdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorActivity extends AppCompatActivity {
    private ColorApdapter colorApdapter;
    @BindView(R.id.rv_color)
    RecyclerView rv_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        setupUI();

    }

    private void setupUI() {
        ButterKnife.bind(this);
        colorApdapter = new ColorApdapter();
        rv_color.setAdapter(colorApdapter);
//        rv_color.setLayoutManager(new GridLayoutManager(this,4));
        rv_color.setLayoutManager(new GridLayoutManager(this, 8));
    }

}
