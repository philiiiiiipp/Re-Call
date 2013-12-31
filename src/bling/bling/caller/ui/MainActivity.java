package bling.bling.caller.ui;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import bling.bling.caller.R;
import bling.bling.caller.manager.CallManager;
import bling.bling.caller.utils.IntentStringExtra;

/**
 * List activity for the whole call history
 * 
 * @author philipp
 * 
 */
public class MainActivity extends Activity implements OnItemClickListener {

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

	protected void calledItemClicked(final View view) {

	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onResume() {
		super.onResume();
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
		intent.putExtra(IntentStringExtra.NUMBER_INTENT, calledNumber);
		intent.putExtra(IntentStringExtra.NAME_INTENT, calledName);
		intent.putExtra(IntentStringExtra.TYPE_INTENT, callType);
		intent.putExtra(IntentStringExtra.DATE_INTENT, callDate.getTime());

		this.startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {

		Toast.makeText(this, "Alarm was set successfully.", Toast.LENGTH_SHORT)
				.show();
	}
}
