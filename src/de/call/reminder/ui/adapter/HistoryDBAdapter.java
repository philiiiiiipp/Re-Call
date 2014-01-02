package de.call.reminder.ui.adapter;

import java.text.DateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import de.call.reminder.R;

public class HistoryDBAdapter extends CursorAdapter {

	/**
	 * Adapter to bind the call history to a ListView
	 * 
	 * @param context
	 * @param c
	 * @param autoRequery
	 */
	public HistoryDBAdapter(final Context context, final Cursor c,
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

		TextView textViewPersonName = (TextView) view
				.findViewById(R.id.firstLine);

		int numberID = cursor.getColumnIndex(CallLog.Calls.NUMBER);
		int typeID = cursor.getColumnIndex(CallLog.Calls.TYPE);
		int nameID = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
		int dateID = cursor.getColumnIndex(CallLog.Calls.DATE);

		String calledNumber = cursor.getString(numberID);
		String calledName = cursor.getString(nameID);
		int callType = cursor.getInt(typeID);
		Date callDate = new Date(cursor.getLong(dateID));

		ImageView image = (ImageView) view.findViewById(R.id.icon);
		switch (callType) {
		case CallLog.Calls.OUTGOING_TYPE:
			image.setImageResource(R.drawable.dialed_number);
			break;
		case CallLog.Calls.INCOMING_TYPE:
			image.setImageResource(R.drawable.received_call);
			break;
		case CallLog.Calls.MISSED_TYPE:
			image.setImageResource(R.drawable.missed_call);
		default:
			break;
		}

		if (calledName == null) {
			textViewPersonName.setText(calledNumber);
		} else {
			textViewPersonName.setText(calledName);
		}

		TextView textViewDate = (TextView) view.findViewById(R.id.secondLine);
		DateFormat formater = DateFormat.getDateTimeInstance();
		textViewDate.setText(formater.format(callDate));
	}
}
