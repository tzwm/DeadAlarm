package com.tzwm.deadalarm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class AlarmListActivity extends Activity {
	static Vector<MyAlarm> myAlarms = new Vector<MyAlarm>();

	private ListView mListView;
	private SimpleAdapter mSimpleAdapter;
	private ArrayList<HashMap<String, Object>> myList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarmlist);

		mListView = (ListView) findViewById(R.id.listView);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				clickListItem(position);
			}
		});
		mListView
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {

						final int t_position = position;
						AlertDialog dialog;
						AlertDialog.Builder builder = new AlertDialog.Builder(
								AlarmListActivity.this);
						builder.setTitle("choose alarm type");

						builder.setSingleChoiceItems(new String[] { "Normal Mode",
								"Sound Reproduce", "Punch The Ball" }, 0,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										changeAlarmType(t_position, which);
										dialog.dismiss();
									}
								});
						builder.setNegativeButton("cancel", null);
						dialog = builder.create();
						dialog.show();
						return true;
					}
				});
		// mListView.setOnTouchListener(new View.OnTouchListener() {
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// float x = 0, y = 0, upx = 0, upy = 0;
		// if (event.getAction() == MotionEvent.ACTION_DOWN) {
		// x = event.getX();
		// y = event.getY();
		// }
		// if (event.getAction() == MotionEvent.ACTION_UP) {
		// upx = event.getX();
		// upy = event.getY();
		// int position1 = ((ListView) v).pointToPosition((int) x,
		// (int) y);
		// int position2 = ((ListView) v).pointToPosition((int) upx,
		// (int) upy);
		//
		// if (position1 == position2 && Math.abs(x - upx) > 100) {
		// View view = ((ListView) v).getChildAt(position1);
		// removeListItem(position1);
		// }
		// }
		// return false;
		// }
		// });
	}

	private void changeAlarmType(int position, int type) {
		if (position == -1)
			return;
		HashMap<String, Object> map = (HashMap<String, Object>) mListView
				.getItemAtPosition(position);
		MyAlarm alarm = (MyAlarm) map.get("item");
		alarm.type = type;
		switch (type) {
		case 0:
			Toast.makeText(AlarmListActivity.this, "Normal Mode",
					Toast.LENGTH_LONG).show();
			break;
		case 1:
			Toast.makeText(AlarmListActivity.this, "Sound Reproduce",
					Toast.LENGTH_LONG).show();
			break;
		case 2:
			Toast.makeText(AlarmListActivity.this, "Punch The Ball",
					Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
		mSimpleAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		super.onResume();

		updateList();
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

	private void updateList() {
		myList = new ArrayList<HashMap<String, Object>>();

		for (Iterator i = myAlarms.iterator(); i.hasNext();) {
			MyAlarm tmp = (MyAlarm) i.next();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("item", tmp);
			DecimalFormat df;
			df = new DecimalFormat("00");
			map.put("ItemTime",
					df.format(tmp.hourOfDay) + ":" + df.format(tmp.minute));
			if (tmp.isOpened)
				map.put("ItemTurn", "ON");
			else
				map.put("ItemTurn", "OFF");
			myList.add(map);
		}

		mSimpleAdapter = new SimpleAdapter(this, myList,
				R.layout.listitem_alarm,
				new String[] { "ItemTime", "ItemTurn" }, new int[] {
						R.id.ItemTime, R.id.ItemTurn });

		mListView.setAdapter(mSimpleAdapter);
	}

	private void clickListItem(final int position) {
		HashMap<String, Object> map = (HashMap<String, Object>) mListView
				.getItemAtPosition(position);
		MyAlarm alarm = (MyAlarm) map.get("item");
		alarm.changeState();
		if (alarm.isOpened)
			map.put("ItemTurn", "ON");
		else
			map.put("ItemTurn", "OFF");
		mSimpleAdapter.notifyDataSetChanged();
	}

	private void removeListItem(final int position) {
		if (position == -1)
			return;
		HashMap<String, Object> map = (HashMap<String, Object>) mListView
				.getItemAtPosition(position);
		MyAlarm alarm = (MyAlarm) map.get("item");
		alarm.close();
		myAlarms.remove(alarm);
		myList.remove(position);
		mSimpleAdapter.notifyDataSetChanged();
	}
}
