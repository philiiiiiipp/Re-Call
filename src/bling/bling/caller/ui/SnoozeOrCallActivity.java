package bling.bling.caller.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import bling.bling.caller.R;
import bling.bling.caller.manager.CallContainer;
import bling.bling.caller.utils.Globals;
import bling.bling.caller.utils.Utils;

/**
 * Activity which is called after an alarm went off. It gives the possibility to
 * either snooze, call or drop the desired call.
 * 
 * @author philipp
 * 
 */
public class SnoozeOrCallActivity extends Activity {

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

		this.setResult(Globals.RESULT_QUIT);
		finish();
	}

	/**
	 * Snooze the current alarm to the predefined snooze time
	 * 
	 * @param view
	 */
	public void snoozeClicked(final View view) {
		stopAlarm();

		Utils.setAlarm(this, SnoozeOrCallActivity.class, this.getIntent()
				.getExtras(), System.currentTimeMillis() + Globals.SNOOZE_TIME);

		Toast.makeText(
				this,
				this.getString(R.string.alarm_snoozed) + " "
						+ Utils.convertMStoSec(Globals.SNOOZE_TIME) + " "
						+ this.getString(R.string.minutesLong),
				Toast.LENGTH_SHORT).show();

		this.setResult(Globals.RESULT_QUIT);
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

		this.setResult(Globals.RESULT_QUIT);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.snooze_or_call, menu);
		return true;
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
