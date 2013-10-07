package com.tzwm.deadalarm;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class AlarmListActivity extends Activity {
    private Vector<MyAlarm> myAlarms;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_alarmlist);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.mytitlebar);

        myAlarms = new Vector<MyAlarm>();
        mListView = (ListView) findViewById(R.id.listView);

        MyAlarm myAlarm = new MyAlarm(this, 21, 00);
        myAlarms.add(myAlarm);
        myAlarm.open();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<HashMap<String, Object>> myList = new ArrayList<HashMap<String, Object>>();

        for(Iterator i=myAlarms.iterator(); i.hasNext();) {
            MyAlarm tmp = (MyAlarm)i.next();
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("item", tmp);
            map.put("ItemTime", String.valueOf(tmp.hourOfDay) + ":" + String.valueOf(tmp.minute));
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
    
}
