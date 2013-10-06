package com.tzwm.deadalarm;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
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
    private CountDownTimer mCountDownTimer;

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
        setText(DateUtils.formatElapsedTime(mBase));

        mCountDownTimer = new CountDownTimer(mBase*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                CountDownTextView.this.setText(DateUtils.formatElapsedTime(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                CountDownTextView.this.setText(DateUtils.formatElapsedTime(mBase));
            }
        };
    }

    public void stopTimer() {
        mCountDownTimer.cancel();
    }

    private void init() {
        mBase = 0;
        setText(DateUtils.formatElapsedTime(mBase));
        handler = new Handler();
        setTextColor(Color.WHITE);
        setTextSize(50);

        mCountDownTimer = new CountDownTimer(mBase*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                CountDownTextView.this.setText(DateUtils.formatElapsedTime(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                CountDownTextView.this.setText(DateUtils.formatElapsedTime(mBase));
            }
        };
    }

    @Override
    public void run() {
        mCountDownTimer.start();
    }

}
