package com.tzwm.deadalarm;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class AlarmListActivity extends Activity {
    static Vector<MyAlarm> myAlarms = new Vector<MyAlarm>();

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmlist);

        mListView = (ListView) findViewById(R.id.listView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<HashMap<String, Object>> myList = new ArrayList<HashMap<String, Object>>();

        for(Iterator i=myAlarms.iterator(); i.hasNext();) {
            MyAlarm tmp = (MyAlarm)i.next();
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("item", tmp);
            DecimalFormat df;
            df = new DecimalFormat("00");
            map.put("ItemTime", df.format(tmp.hourOfDay) + ":" + df.format(tmp.minute));
            if(tmp.isOpened)
                map.put("ItemTurn", "ON");
            else
                map.put("ItemTurn", "OFF");
            myList.add(map);
        }

        SimpleAdapter mSchedule = new SimpleAdapter(this, myList,
                R.layout.listitem_alarm,
                new String[] {"ItemTime", "ItemTurn"},
                new int[] {R.id.ItemTime, R.id.ItemTurn});

        mListView.setAdapter(mSchedule);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.alarm_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_plus:
                Intent intent = new Intent(this, TimeSettingActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
