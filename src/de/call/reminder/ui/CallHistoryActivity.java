package de.call.reminder.ui;

import java.util.Date;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import de.call.reminder.R;
import de.call.reminder.manager.CallManager;
import de.call.reminder.ui.adapter.DatabaseAdapter;
import de.call.reminder.ui.extension.ActivityWithSettings;
import de.call.reminder.utils.Globals;

/**
 * List activity for the whole call history
 * 
 * @author philipp
 * 
 */
public class CallHistoryActivity extends ActivityWithSettings implements
		OnItemClickListener {

	/**
	 * Request code
	 */
	public static final int REQUEST_CODE = 27051990;

	/**
	 * The database adapter for the call history
	 */
	private DatabaseAdapter _adapter;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_adapter = new DatabaseAdapter(this, CallManager.getCursor(this), true);

		ListView v = (ListView) this.findViewById(R.id.listView1);
		v.setAdapter(_adapter);
		v.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(final AdapterView<?> arg0, final View arg1,
			final int position, final long id) {

		Cursor cursor = (Cursor) _adapter.getItem(position);

		int numberID = cursor.getColumnIndex(CallLog.Calls.NUMBER);
		int typeID = cursor.getColumnIndex(CallLog.Calls.TYPE);
		int nameID = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
		int dateID = cursor.getColumnIndex(CallLog.Calls.DATE);

		String calledNumber = cursor.getString(numberID);
		String calledName = cursor.getString(nameID);
		int callType = cursor.getInt(typeID);
		Date callDate = new Date(cursor.getLong(dateID));

		Intent intent = new Intent(this, CallDetailActivity.class);
		intent.putExtra(Globals.NUMBER_INTENT, calledNumber);
		intent.putExtra(Globals.NAME_INTENT, calledName);
		intent.putExtra(Globals.TYPE_INTENT, callType);
		intent.putExtra(Globals.DATE_INTENT, callDate.getTime());

		setResult(RESULT_OK, intent);
		finish();
	}
}
