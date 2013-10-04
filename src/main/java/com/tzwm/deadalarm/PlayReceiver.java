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
public class PlayReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast toast = Toast.makeText(context, "Ok", Toast.LENGTH_SHORT);
        toast.show();
    }
}
