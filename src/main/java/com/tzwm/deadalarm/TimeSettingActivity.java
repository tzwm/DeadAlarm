package com.tzwm.deadalarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class TimeSettingActivity extends Activity {
	public TimeTextView mTimeTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timesetting);

		mTimeTextView = (TimeTextView) findViewById(R.id.view2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.time_setting, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_delete:
			Intent intent = new Intent(this, AlarmListActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;

		case R.id.menu_checkmark:
			newAlarm();
			Intent intent1 = new Intent(this, AlarmListActivity.class);
			intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent1);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void newAlarm() {
		MyAlarm myAlarm = new MyAlarm(this, mTimeTextView.hourOfDay,
				mTimeTextView.minute);
		myAlarm.type = WakeUpSettingActionProvider.type;
		AlarmListActivity.myAlarms.add(myAlarm);
		myAlarm.open();
	}
}