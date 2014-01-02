package de.call.reminder.ui.extension;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import bling.bling.caller.R;
import de.call.reminder.ui.SettingsActivity;

/**
 * Wrapper class to provide basic functionality
 * 
 * @author philipp
 * 
 */
public class ActivityWithSettings extends Activity {

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		Intent i = new Intent(this, SettingsActivity.class);
		startActivity(i);
		return true;
	}
}
