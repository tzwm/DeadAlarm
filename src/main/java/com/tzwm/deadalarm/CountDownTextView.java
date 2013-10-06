package com.tzwm.deadalarm;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.DateFormat;

/**
 * Created by tzwm on 10/6/13.
 */
public class CountDownTextView extends TextView implements Runnable{
    private int mBase;
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

    public void setBase(int now) {
        mBase = now;
    }

    private void init() {
        mBase = 0;
        setText(DateUtils.formatElapsedTime(mBase));
        handler = new Handler();
        setTextColor(Color.WHITE);
        setTextSize(50);
    }

    @Override
    public void run() {
        mBase--;
        setText(DateUtils.formatElapsedTime(mBase));
        if(mBase == 0)
            return;
        handler.postDelayed(this, 1000);
    }
}
