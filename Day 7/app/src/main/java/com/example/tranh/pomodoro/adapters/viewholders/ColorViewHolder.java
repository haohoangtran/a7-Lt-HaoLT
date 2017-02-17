package com.example.tranh.pomodoro.adapters.viewholders;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.example.tranh.pomodoro.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tranh on 2/10/2017.
 */

public class ColorViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.cb_color)
    CheckBox cb_color;
    @BindView(R.id.v_color)
    View v_color;
    public ColorViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
    public void bind(String color){
        GradientDrawable drawable= (GradientDrawable) v_color.getBackground();
        drawable.setColor(Color.parseColor(color));
    }
}
