package bling.bling.caller.ui;

import java.text.DateFormat;
import java.util.Calendar;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
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

	/**
	 * The alarm time
	 */
	Calendar _time;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// enable dithering
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);

		setContentView(R.layout.activity_call_detail);

		CallContainer cc = Utils.extractCallContainer(this.getIntent());

		TextView t = (TextView) this.findViewById(R.id.description);
		switch (cc.getCallType()) {
		case CallLog.Calls.INCOMING_TYPE:
			t.setText(this.getString(R.string.alarm_incoming_call));
			break;
		case CallLog.Calls.OUTGOING_TYPE:
			t.setText(this.getString(R.string.alarm_outgoing_call));
			break;
		case CallLog.Calls.MISSED_TYPE:
			t.setText(this.getString(R.string.alarm_missed_call));
			break;

		default:
			break;
		}

		if (cc.getName() != null) {
			this.setTitle(cc.getName());
		} else {
			this.setTitle(cc.getPhoneNumber());
		}

		t = (TextView) this.findViewById(R.id.name);
		if (cc.getName() != null) {
			t.setText(cc.getName());
		} else {
			t.setText("-");
		}

		t = (TextView) this.findViewById(R.id.number);
		t.setText(cc.getPhoneNumber());

		t = (TextView) this.findViewById(R.id.time);
		t.setText(DateFormat.getTimeInstance().format(cc.getCallTime()));

		t = (TextView) this.findViewById(R.id.date);
		t.setText(DateFormat.getDateInstance().format(cc.getCallTime()));

		final WheelView days = (WheelView) findViewById(R.id.day);
		days.setViewAdapter(new NumericWheelAdapter(this, 0, 999));

		final WheelView hours = (WheelView) findViewById(R.id.hour);
		hours.setViewAdapter(new NumericWheelAdapter(this, 0, 23));
		hours.setCyclic(true);

		final WheelView mins = (WheelView) findViewById(R.id.mins);
		mins.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));
		mins.setCyclic(true);

		// set current time
		_time = Calendar.getInstance();
		_time.add(Calendar.MINUTE, 15);

		final TextView finalTime = (TextView) this.findViewById(R.id.finalTime);
		finalTime.setText(this.getString(R.string.alarm_set_to)
				+ DateFormat.getDateTimeInstance().format(_time.getTime()));

		hours.setCurrentItem(0);
		mins.setCurrentItem(15);

		OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
			@Override
			public void onChanged(final WheelView wheel, final int oldValue,
					final int newValue) {

				_time = Calendar.getInstance();
				_time.add(Calendar.DATE, days.getCurrentItem());
				_time.add(Calendar.HOUR, hours.getCurrentItem());
				_time.add(Calendar.MINUTE, mins.getCurrentItem());

				finalTime.setText(CallDetailActivity.this
						.getString(R.string.alarm_set_to)
						+ DateFormat.getDateTimeInstance().format(
								_time.getTime()));
			}
		};
		days.addChangingListener(wheelListener);
		hours.addChangingListener(wheelListener);
		mins.addChangingListener(wheelListener);
	}

	/**
	 * Show the call history
	 * 
	 * @param view
	 */
	public void showMoreCalls(final View view) {

	}

	/**
	 * Set the alarm for the given time
	 * 
	 * @param view
	 */
	public void setAlarm(final View view) {
		Utils.setAlarm(this, SnoozeOrCallActivity.class, this.getIntent()
				.getExtras(), _time.getTimeInMillis());

		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.call_detail, menu);
		return true;
	}

}
