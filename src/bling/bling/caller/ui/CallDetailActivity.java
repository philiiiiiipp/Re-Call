package bling.bling.caller.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import bling.bling.caller.R;
import bling.bling.caller.manager.CallContainer;
import bling.bling.caller.utils.Utils;

/**
 * Activity to create an alarm
 * 
 * @author philipp
 * 
 */
public class CallDetailActivity extends Activity {

	TimePicker _timePicker;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_detail);

		CallContainer cc = Utils.extractCallContainer(this.getIntent());

		if (cc.getName() != null) {
			this.setTitle(cc.getName());
		} else {
			this.setTitle(cc.getPhoneNumber());
		}

		TextView dateView = (TextView) findViewById(R.id.nameOrNumber);
		dateView.setText(cc.getFormatedDate());

		_timePicker = (TimePicker) this.findViewById(R.id.timePicker);
		_timePicker.setIs24HourView(true);
		_timePicker.setCurrentHour(0);
		_timePicker.setCurrentMinute(15);
	}

	/**
	 * Set the alarm for the given time
	 * 
	 * @param view
	 */
	public void setAlarm(final View view) {
		long countDownInMS = _timePicker.getCurrentHour() * 60 * 60 * 1000;
		countDownInMS += _timePicker.getCurrentMinute() * 60 * 1000;

		Utils.setAlarm(this, SnoozeOrCallActivity.class, this.getIntent()
				.getExtras(), countDownInMS);

		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.call_detail, menu);
		return true;
	}

}
