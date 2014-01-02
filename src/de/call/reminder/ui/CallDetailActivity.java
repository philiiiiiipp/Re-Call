package de.call.reminder.ui;

import java.text.DateFormat;
import java.util.Calendar;

import de.call.reminder.manager.CallContainer;
import de.call.reminder.manager.CallManager;
import de.call.reminder.ui.extension.ActivityWithSettings;
import de.call.reminder.utils.Utils;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import bling.bling.caller.R;

/**
 * Activity to create an alarm
 * 
 * @author philipp
 * 
 */
public class CallDetailActivity extends ActivityWithSettings {

	/**
	 * The alarm time
	 */
	private Calendar _time;

	/**
	 * The currently shown call container
	 */
	private CallContainer _activeCallContainer;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// enable dithering
		// getWindow().setFormat(PixelFormat.RGBA_8888);
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);

		setContentView(R.layout.activity_call_detail);

		_activeCallContainer = getCallContainer();

		doLayout();

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
		finalTime.setText(DateFormat.getDateTimeInstance().format(
				_time.getTime()));

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

				finalTime.setText(DateFormat.getDateTimeInstance().format(
						_time.getTime()));
			}
		};
		days.addChangingListener(wheelListener);
		hours.addChangingListener(wheelListener);
		mins.addChangingListener(wheelListener);
	}

	/**
	 * Fill all fields with the data from the active call container
	 */
	private void doLayout() {
		TextView t = (TextView) this.findViewById(R.id.name);
		if (_activeCallContainer.getName() != null) {
			t.setText(_activeCallContainer.getName());
		} else {
			t.setText("-");
		}

		t = (TextView) this.findViewById(R.id.number);
		t.setText(_activeCallContainer.getPhoneNumber());

		t = (TextView) this.findViewById(R.id.time);
		t.setText(DateFormat.getTimeInstance().format(
				_activeCallContainer.getCallTime()));

		t = (TextView) this.findViewById(R.id.date);
		t.setText(DateFormat.getDateInstance().format(
				_activeCallContainer.getCallTime()));
	}

	/**
	 * Get the currently used call container, either from intent or from the
	 * call history
	 * 
	 * @return the correct call container
	 */
	private CallContainer getCallContainer() {
		CallContainer cc = Utils.extractCallContainer(this.getIntent());

		if (cc != null)
			return cc;

		return CallManager.getLastCalled(this);
	}

	/**
	 * Show the call history
	 * 
	 * @param view
	 */
	public void showMoreCalls(final View view) {
		this.startActivityForResult(
				new Intent(this, CallHistoryActivity.class),
				CallHistoryActivity.REQUEST_CODE);
	}

	/**
	 * Set the alarm for the given time
	 * 
	 * @param view
	 */
	public void setAlarm(final View view) {
		Utils.setAlarm(this, SnoozeOrCallActivity.class, _activeCallContainer,
				_time.getTimeInMillis());

		// Verify the user that the alarm was set.
		Toast.makeText(this, this.getString(R.string.alarm_set_successfully),
				Toast.LENGTH_SHORT).show();

		finish();
	}

	@Override
	public void onActivityResult(final int requestCode, final int resultCode,
			final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case (CallHistoryActivity.REQUEST_CODE): {
			if (resultCode == Activity.RESULT_OK) {
				_activeCallContainer = Utils.extractCallContainer(data);
				doLayout();
			}
			break;
		}
		}
	}
}
