package com.tzwm.deadalarm;

import android.app.Activity;
import android.os.Bundle;

public class TimeSettingActivity extends Activity {
    public CountDownTextView mCountDownTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        mCountDownTextView = (CountDownTextView)findViewById(R.id.view2);
    }

}
