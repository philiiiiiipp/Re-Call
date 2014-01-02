package de.call.reminder.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.call.reminder.R;

public class ContactsDBAdapter extends CursorAdapter {

	/**
	 * Adapter to bind the call history to a ListView
	 * 
	 * @param context
	 * @param c
	 * @param autoRequery
	 */
	public ContactsDBAdapter(final Context context, final Cursor c,
			final boolean autoRequery) {
		super(context, c, autoRequery);
	}

	@Override
	public View newView(final Context context, final Cursor cursor,
			final ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View retView = inflater.inflate(R.layout.call_history_row, parent,
				false);

		return retView;
	}

	@Override
	public void bindView(final View view, final Context context,
			final Cursor cursor) {

		int nameID = cursor
				.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
		TextView textViewPersonName = (TextView) view
				.findViewById(R.id.firstLine);

		int numberID = cursor.getColumnIndex(Phone.NUMBER);
		String calledNumber = cursor.getString(numberID);
		String calledName = cursor.getString(nameID);
		// ImageView image = (ImageView) view.findViewById(R.id.icon);

		textViewPersonName.setText(calledName);

		TextView textViewDate = (TextView) view.findViewById(R.id.secondLine);
		textViewDate.setText(calledNumber);
	}
}