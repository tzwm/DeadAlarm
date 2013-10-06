package com.tzwm.deadalarm;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.widget.Chronometer;
import android.widget.RelativeLayout;

public class CountDownActivity extends Activity {
    public CountDownTextView mCountDownTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        mCountDownTextView = (CountDownTextView)findViewById(R.id.view2);
        mCountDownTextView.setBase(50);
        new Thread(mCountDownTextView).start();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.count_down, menu);
//        return true;
//    }

}
