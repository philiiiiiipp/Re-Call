package bling.bling.caller.ui;

import java.util.Date;

import bling.bling.caller.R;
import bling.bling.caller.R.id;
import bling.bling.caller.R.layout;
import bling.bling.caller.R.menu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.Menu;
import android.widget.TextView;

/**
 * Activity to create an alarm
 * 
 * @author philipp
 * 
 */
public class CallDetailActivity extends Activity {

	/**
	 * Intent name of the number data
	 */
	public static final String NUMBER_INTENT = "call_detail_number_intent";

	/**
	 * Intent name of the date data
	 */
	public static final String DATE_INTENT = "call_detail_date_intent";

	/**
	 * Intent name of the name data
	 */
	public static final String NAME_INTENT = "call_detail_name_intent";

	/**
	 * Intent name of the type data
	 */
	public static final String TYPE_INTENT = "call_detail_type_intent";

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_detail);

		Intent intent = this.getIntent();

		String number = intent.getStringExtra(NUMBER_INTENT);
		String name = intent.getStringExtra(NAME_INTENT);
		int callType = intent.getIntExtra(TYPE_INTENT,
				CallLog.Calls.INCOMING_TYPE);

		if (name != null) {
			this.setTitle(name);
		} else {
			this.setTitle(number);
		}

		Date date = new Date(intent.getLongExtra(DATE_INTENT, 0));
		TextView dateView = (TextView) findViewById(R.id.nameOrNumber);
		dateView.setText(date.toLocaleString());
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.call_detail, menu);
		return true;
	}

}
