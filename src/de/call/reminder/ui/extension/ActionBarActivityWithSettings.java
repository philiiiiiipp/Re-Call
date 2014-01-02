package de.call.reminder.ui.extension;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import de.call.reminder.R;
import de.call.reminder.ui.SettingsActivity;

/**
 * Wrapper class to provide basic functionality
 * 
 * @author philipp
 * 
 */
public class ActionBarActivityWithSettings extends ActionBarActivity {

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
