package com.tzwm.deadalarm;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by tzwm on 10/6/13.
 */
public class TimeTextView extends TextView {
    private int hourOfDay, minute;
    private static final String STR_FORMAT = "00";
    DecimalFormat df;

    public TimeTextView(Context context) {
        super(context);
        init();
    }

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setTime(int _hourOfDay, int _minute) {
        hourOfDay = _hourOfDay;
        minute = _minute;
        setText(df.format(hourOfDay) + ":" + df.format(minute));
    }

    private void init() {
        hourOfDay = 0;
        minute = 0;
        df = new DecimalFormat(STR_FORMAT);
        setText(df.format(hourOfDay) + ":" + df.format(minute));
        setTextColor(Color.WHITE);
        setTextSize(50);
    }

}
