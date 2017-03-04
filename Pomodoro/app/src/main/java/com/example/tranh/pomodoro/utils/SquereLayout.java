package com.example.tranh.pomodoro.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by tranh on 3/4/2017.
 */

public class SquereLayout extends RelativeLayout {

    public SquereLayout(Context context) {
        super(context);

    }
    public SquereLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquereLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=width;
        heightMeasureSpec=MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
