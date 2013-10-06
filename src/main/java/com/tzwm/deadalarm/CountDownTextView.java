package com.tzwm.deadalarm;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by tzwm on 10/6/13.
 */
public class CountDownTextView extends TextView implements Runnable{
    private long mBase;
    private Handler handler;

    public CountDownTextView(Context context) {
        super(context);
        init();
    }

    public CountDownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CountDownTextView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setBase(long now) {
        mBase = now;
    }

    private void init() {
        mBase = 0;
        setText("" + mBase);
        handler = new Handler();
        setTextColor(Color.WHITE);
        setTextSize(50);
    }

    @Override
    public void run() {
        mBase--;
        setText("" + mBase);
        handler.postDelayed(this, 1000);
    }
}
