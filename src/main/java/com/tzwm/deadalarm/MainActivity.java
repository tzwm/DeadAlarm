package com.tzwm.deadalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.tzwm.hello");
                PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this,
                        1, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                AlarmManager arm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MINUTE, 1);
                arm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
