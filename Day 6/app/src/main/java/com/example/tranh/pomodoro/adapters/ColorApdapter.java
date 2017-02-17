package com.example.tranh.pomodoro.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.adapters.viewholders.ColorViewHolder;
import com.example.tranh.pomodoro.adapters.viewholders.TaskViewHolder;
import com.example.tranh.pomodoro.database.DbContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tranh on 2/10/2017.
 */

public class ColorApdapter extends RecyclerView.Adapter<ColorViewHolder>{
    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView=layoutInflater.inflate(R.layout.item_color,parent,false);
        return new ColorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ColorViewHolder holder, int position) {
        String s= DbContext.colors[position];
        holder.bind(s);
    }

    @Override
    public int getItemCount() {
        return DbContext.colors.length;
    }

}
