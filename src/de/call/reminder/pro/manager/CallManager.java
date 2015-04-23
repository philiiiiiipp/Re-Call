package de.call.reminder.pro.manager;

import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.provider.ContactsContract;

public class CallManager {

	/**
	 * Query fields for the call history
	 */
	private final static String[] QUERY_FIELDS_HISTORY = {
			android.provider.CallLog.Calls._ID,
			android.provider.CallLog.Calls.NUMBER,
			android.provider.CallLog.Calls.TYPE,
			android.provider.CallLog.Calls.CACHED_NAME,
			android.provider.CallLog.Calls.DATE };

	/**
	 * Order of the call history
	 */
	private final static String QUERY_ORDER_HISTORY = android.provider.CallLog.Calls.DATE
			+ " DESC";

	/**
	 * Query fields for all contacts
	 */
	private final static String[] QUERY_FIELDS_CONTACTS = {
			ContactsContract.CommonDataKinds.Phone._ID,
			ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
			ContactsContract.CommonDataKinds.Phone.NUMBER };

	/**
	 * Order of all contacts
	 */
	private final static String QUERY_ORDER_CONTACTS = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
			+ " ASC";

	/**
	 * Get the database cursor for the call history ordered by date
	 * 
	 * @param context
	 * @return an sql cursor with the whole call history
	 */
	public static Cursor getHistoryCursor(final Context context) {
		return context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
				QUERY_FIELDS_HISTORY, null, null, QUERY_ORDER_HISTORY);
	}

	/**
	 * Get the database cursor all contacts ordered by name
	 * 
	 * @param context
	 * @return an sql cursor with all contacts
	 */
	public static Cursor getContactsCursor(final Context context) {
		return context.getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				QUERY_FIELDS_CONTACTS, null, null, QUERY_ORDER_CONTACTS);
	}

	/**
	 * Get last called call container
	 * 
	 * @param context
	 * @param maxCalls
	 * @return the last called user
	 */
	public static CallContainer getLastCalled(final Context context) {
		CallContainer result;

		Cursor managedCursor = context.getContentResolver().query(
				CallLog.Calls.CONTENT_URI, QUERY_FIELDS_HISTORY, null, null,
				QUERY_ORDER_HISTORY);

		int numberID = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
		int typeID = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
		int nameID = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
		int dateID = managedCursor.getColumnIndex(CallLog.Calls.DATE);

		if (!managedCursor.moveToFirst())
			return new CallContainer("No Entry", "No Entry", Calls.MISSED_TYPE,
					new Date());

		String calledNumber = managedCursor.getString(numberID);
		String calledName = managedCursor.getString(nameID);
		int callType = managedCursor.getInt(typeID);
		Date callDate = new Date(managedCursor.getLong(dateID));

		result = new CallContainer(calledNumber, calledName, callType, callDate);

		managedCursor.close();

		return result;
	}
}
