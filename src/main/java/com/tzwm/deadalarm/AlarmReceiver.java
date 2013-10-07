package com.tzwm.deadalarm;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by tzwm on 10/4/13.
 */
public class AlarmReceiver extends BroadcastReceiver {
    static final int WAKEUP_NORMA = 0;

    static int wakeupWay = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        MediaController mediaController = new MediaController(context);
        mediaController.startPlaying();

        Toast toast = Toast.makeText(context, "Time's Up!", Toast.LENGTH_LONG);
        toast.show();
    }
}