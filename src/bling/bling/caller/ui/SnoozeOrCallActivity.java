package bling.bling.caller.ui;

import bling.bling.caller.R;
import bling.bling.caller.R.layout;
import bling.bling.caller.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SnoozeOrCallActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_snooze_or_call);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.snooze_or_call, menu);
		return true;
	}

}
