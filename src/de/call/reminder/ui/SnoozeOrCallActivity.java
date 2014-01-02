package de.call.reminder.ui;

import de.call.reminder.manager.CallContainer;
import de.call.reminder.ui.extension.ActivityWithSettings;
import de.call.reminder.utils.Globals;
import de.call.reminder.utils.Utils;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import bling.bling.caller.R;

/**
 * Activity which is called after an alarm went off. It gives the possibility to
 * either snooze, call or drop the desired call.
 * 
 * @author philipp
 * 
 */
public class SnoozeOrCallActivity extends ActivityWithSettings {

	/**
	 * The call which is getting reminded
	 */
	private CallContainer _call;

	/**
	 * The used alarm
	 */
	private Ringtone _alarmTone;

	/**
	 * Vibrator for the alarm :-)
	 */
	private Vibrator _alarmVibrator;

	private static final long[] VIBRATION_PATTERN = { 0, 2000 };

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_snooze_or_call);

		_call = Utils.extractCallContainer(this.getIntent());

		TextView t = (TextView) this.findViewById(R.id.nameOrNumber);

		if (_call.getName() != null) {
			t.setText(_call.getName());
		} else {
			t.setText(_call.getPhoneNumber());
		}
		Uri notification = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_ALARM);
		_alarmTone = RingtoneManager.getRingtone(getApplicationContext(),
				notification);

		_alarmTone.play();

		_alarmVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		_alarmVibrator.vibrate(VIBRATION_PATTERN, 0);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * Silence the currently playing alarm
	 * 
	 * @param view
	 */
	public void silenceAlarm(final View view) {
		stopAlarm();
	}

	/**
	 * Stop the current Alarm
	 * 
	 * @param view
	 */
	public void dropClicked(final View view) {
		stopAlarm();

		finish();
	}

	/**
	 * Snooze the current alarm to the predefined snooze time
	 * 
	 * @param view
	 */
	public void snoozeClicked(final View view) {
		stopAlarm();

		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this);

		// Seems like the default EditTextPreference saves only String values
		// which causes this hack here
		long snoozeTime = Globals.DEFAULT_SNOOZE_TIME;

		// Just in case a false number slips in.
		try {
			snoozeTime = Long.parseLong(settings.getString(
					Globals.SNOOZE_TIME_PREFERENCE, Globals.DEFAULT_SNOOZE_TIME
							+ ""));
		} catch (Exception e) {
			snoozeTime = Globals.DEFAULT_SNOOZE_TIME;
		}

		Utils.setAlarm(this, SnoozeOrCallActivity.class, this.getIntent()
				.getExtras(),
				System.currentTimeMillis() + Utils.convertSecToMs(snoozeTime));

		Toast.makeText(
				this,
				this.getString(R.string.alarm_snoozed) + " " + snoozeTime + " "
						+ this.getString(R.string.minutesLong),
				Toast.LENGTH_SHORT).show();

		finish();
	}

	/**
	 * Calls the number and finishes this alarm activity
	 * 
	 * @param view
	 */
	public void callClicked(final View view) {
		stopAlarm();

		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + _call.getPhoneNumber()));
		startActivity(callIntent);

		finish();
	}

	/**
	 * Stop the currently playing alarm, if playing
	 */
	private void stopAlarm() {
		if (_alarmTone.isPlaying())
			_alarmTone.stop();

		_alarmVibrator.cancel();
	}
}
