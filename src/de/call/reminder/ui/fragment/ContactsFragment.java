package de.call.reminder.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import de.call.reminder.R;
import de.call.reminder.manager.CallManager;
import de.call.reminder.ui.CallDetailActivity;
import de.call.reminder.ui.adapter.ContactsDBAdapter;
import de.call.reminder.utils.Globals;

public class ContactsFragment extends ListFragment {

	/**
	 * The database adapter for the call history
	 */
	private ContactsDBAdapter _adapter;

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.call_history, container,
				false);

		_adapter = new ContactsDBAdapter(this.getActivity(),
				CallManager.getContactsCursor(this.getActivity()), true);
		this.setListAdapter(_adapter);

		return rootView;
	}

	@Override
	public void onListItemClick(final ListView l, final View v,
			final int position, final long id) {

		Cursor cursor = (Cursor) _adapter.getItem(position);

		int nameID = cursor
				.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

		int numberID = cursor.getColumnIndex(Phone.NUMBER);
		String calledNumber = cursor.getString(numberID);
		String calledName = cursor.getString(nameID);

		Intent intent = new Intent(this.getActivity(), CallDetailActivity.class);
		intent.putExtra(Globals.NUMBER_INTENT, calledNumber);
		intent.putExtra(Globals.NAME_INTENT, calledName);

		this.getActivity().setResult(Activity.RESULT_OK, intent);
		this.getActivity().finish();
	}
}
