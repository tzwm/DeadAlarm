package com.tzwm.deadalarm;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class CountDownActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CountDownSurfaceView(this));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.count_down, menu);
        return true;
    }

}
