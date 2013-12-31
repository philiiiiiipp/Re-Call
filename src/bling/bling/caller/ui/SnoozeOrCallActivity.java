package bling.bling.caller.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import bling.bling.caller.R;
import bling.bling.caller.manager.CallContainer;
import bling.bling.caller.utils.Utils;

/**
 * Activity which is called after an alarm went off. It gives the possibility to
 * either snooze, call or drop the desired call.
 * 
 * @author philipp
 * 
 */
public class SnoozeOrCallActivity extends Activity {

	private CallContainer _call;

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
	}

	/**
	 * Stop the current Alarm
	 * 
	 * @param view
	 */
	public void dropClicked(final View view) {
		finish();
	}

	/**
	 * Snooze the current alarm to the predefined snooze time
	 * 
	 * @param view
	 */
	public void snoozeClicked(final View view) {
		Utils.setAlarm(this, SnoozeOrCallActivity.class, this.getIntent()
				.getExtras(), System.currentTimeMillis() + Utils.SNOOZE_TIME);

		finish();
	}

	/**
	 * Calls the number and finishes this alarm activity
	 * 
	 * @param view
	 */
	public void callClicked(final View view) {
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + _call.getPhoneNumber()));
		startActivity(callIntent);

		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.snooze_or_call, menu);
		return true;
	}

}
