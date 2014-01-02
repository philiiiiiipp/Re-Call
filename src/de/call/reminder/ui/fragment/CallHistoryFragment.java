package de.call.reminder.ui.fragment;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import de.call.reminder.R;
import de.call.reminder.manager.CallManager;
import de.call.reminder.ui.CallDetailActivity;
import de.call.reminder.ui.adapter.DatabaseAdapter;
import de.call.reminder.utils.Globals;

public class CallHistoryFragment extends ListFragment {

	/**
	 * The database adapter for the call history
	 */
	private DatabaseAdapter _adapter;

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.call_history, container,
				false);

		_adapter = new DatabaseAdapter(this.getActivity(),
				CallManager.getHistoryCursor(this.getActivity()), true);
		this.setListAdapter(_adapter);

		return rootView;
	}

	@Override
	public void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		// do something with the data

		Cursor cursor = (Cursor) _adapter.getItem(position);

		int numberID = cursor.getColumnIndex(CallLog.Calls.NUMBER);
		int typeID = cursor.getColumnIndex(CallLog.Calls.TYPE);
		int nameID = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
		int dateID = cursor.getColumnIndex(CallLog.Calls.DATE);

		String calledNumber = cursor.getString(numberID);
		String calledName = cursor.getString(nameID);
		int callType = cursor.getInt(typeID);
		Date callDate = new Date(cursor.getLong(dateID));

		Intent intent = new Intent(this.getActivity(), CallDetailActivity.class);
		intent.putExtra(Globals.NUMBER_INTENT, calledNumber);
		intent.putExtra(Globals.NAME_INTENT, calledName);
		intent.putExtra(Globals.TYPE_INTENT, callType);
		intent.putExtra(Globals.DATE_INTENT, callDate.getTime());

		this.getActivity().setResult(Activity.RESULT_OK, intent);
		this.getActivity().finish();
	}
}
