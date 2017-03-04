package com.example.tranh.pomodoro.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.adapters.viewholders.ColorViewHolder;
import com.example.tranh.pomodoro.database.DbContext;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by tranh on 2/10/2017.
 */

public class ColorApdapter extends RecyclerView.Adapter<ColorViewHolder> {
    private int selectPositon;
    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView=layoutInflater.inflate(R.layout.item_color,parent,false);
        return new ColorViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final ColorViewHolder holder, final int position) {
        String s= DbContext.colors[position];
        holder.bind(s);
        if (selectPositon==position){
            holder.setCheck(true);
        }else {
            holder.setCheck(false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPositon=position;
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public int getItemCount() {
        return DbContext.colors.length;
    }
    public String getSelectColor(){
        return DbContext.colors[selectPositon];
    }
    public void setSelectColor(String color){
        selectPositon=0;
        for (int i = 0; i < DbContext.colors.length; i++) {
            if(DbContext.colors[i].equalsIgnoreCase(color)){
                selectPositon=i;
            }
        }
        notifyDataSetChanged();
    }
}
