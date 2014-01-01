package bling.bling.caller.manager;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.CallLog.Calls;

public class CallManager {

	/**
	 * CallManager singleton instance
	 */
	private static CallManager _instance = new CallManager();

	/**
	 * Set of all subscribed listeners
	 */
	private final Set<CallManagerListener> _listeners = new HashSet<CallManagerListener>();

	private final static String[] QUERY_FIELDS = {
			android.provider.CallLog.Calls._ID,
			android.provider.CallLog.Calls.NUMBER,
			android.provider.CallLog.Calls.TYPE,
			android.provider.CallLog.Calls.CACHED_NAME,
			android.provider.CallLog.Calls.DATE };

	private final static String QUERY_ORDER = android.provider.CallLog.Calls.DATE
			+ " DESC";

	/**
	 * Get the CallManager singleton
	 * 
	 * @return CallManager instance
	 */
	public static CallManager get() {
		return _instance;
	}

	/**
	 * Get the database cursor for the call history
	 * 
	 * @param context
	 * @return an sql cursor with the whole call history
	 */
	public static Cursor getCursor(final Context context) {
		return context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
				QUERY_FIELDS, null, null, QUERY_ORDER);
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
				CallLog.Calls.CONTENT_URI, QUERY_FIELDS, null, null,
				QUERY_ORDER);

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

	/**
	 * Sends the call data to all listeners
	 * 
	 * @param callData
	 */
	private void sendCallData(final List<CallContainer> callData) {
		for (CallManagerListener l : _listeners) {
			l.recieveCallData(callData);
		}
	}

	/**
	 * Register for call manager updates
	 * 
	 * @param listener
	 * @return true if it was successfully registered
	 */
	public boolean subscribe(final CallManagerListener listener) {
		return _listeners.add(listener);
	}

	/**
	 * Unsubscribe listener
	 * 
	 * @param listener
	 * @return true if the listener was unsubscribed
	 */
	public boolean unsubscribe(final CallManagerListener listener) {
		return _listeners.remove(listener);
	}
}
