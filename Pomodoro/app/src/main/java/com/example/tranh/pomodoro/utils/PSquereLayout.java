package com.example.tranh.pomodoro.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by tranh on 3/4/2017.
 */

public class PSquereLayout extends RelativeLayout {
    public PSquereLayout(Context context) {
        super(context);
    }

    public PSquereLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PSquereLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height=MeasureSpec.getSize(heightMeasureSpec);
        int width=height;
        widthMeasureSpec=MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
