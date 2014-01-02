package de.call.reminder.utils;

import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CallLog;
import de.call.reminder.manager.CallContainer;

/**
 * Utils collection
 * 
 * @author philipp
 * 
 */
public class Utils {

	/**
	 * Extracts the called details from the given intent
	 * 
	 * @param intent
	 * @return the filled call container or null of the intent does not contain
	 *         call data
	 */
	public static CallContainer extractCallContainer(final Intent intent) {
		String number = intent.getStringExtra(Globals.NUMBER_INTENT);
		if (number == null)
			return null;

		String name = intent.getStringExtra(Globals.NAME_INTENT);
		int callType = intent.getIntExtra(Globals.TYPE_INTENT,
				CallLog.Calls.INCOMING_TYPE);

		Date date = new Date(intent.getLongExtra(Globals.DATE_INTENT, 0));

		return new CallContainer(number, name, callType, date);
	}

	/**
	 * Set or update the alarm
	 * 
	 * @param packageContext
	 *            The activity context
	 * @param cls
	 *            The class to get called when the alarm fires
	 * @param extras
	 *            Intent extras for the called class
	 * @param time
	 *            The time in ms after which the alarm should fire
	 */
	public static void setAlarm(final Context packageContext,
			final Class<?> cls, final Bundle extras, final long time) {
		Intent intent = new Intent(packageContext, cls);

		// Add all information e.g. called number, name etc.
		intent.putExtras(extras);

		setAlarm(packageContext, intent, time);
	}

	/**
	 * Set or update the alarm
	 * 
	 * @param packageContext
	 *            The activity context
	 * @param cls
	 *            The class to get called when the alarm fires
	 * @param callContainer
	 *            The call container used for the alarm
	 * @param time
	 *            The time in ms after which the alarm should fire
	 */
	public static void setAlarm(final Context packageContext,
			final Class<?> cls, final CallContainer callContainer,
			final long time) {
		Intent intent = new Intent(packageContext, cls);

		// Add all information e.g. called number, name etc.
		fillIntentWith(callContainer, intent);

		setAlarm(packageContext, intent, time);
	}

	/**
	 * Actually register the alarm
	 * 
	 * @param packageContext
	 *            The current context
	 * @param intent
	 *            The intent which should be fired
	 * @param time
	 *            The time after which it should go off
	 */
	private static void setAlarm(final Context packageContext,
			final Intent intent, final long time) {
		AlarmManager a = (AlarmManager) packageContext
				.getSystemService(Context.ALARM_SERVICE);

		PendingIntent pIntent = PendingIntent.getActivity(packageContext, 0,
				intent, PendingIntent.FLAG_CANCEL_CURRENT);

		a.set(AlarmManager.RTC, time, pIntent);
	}

	/**
	 * Fills the given intent with the call container
	 * 
	 * @param callContainer
	 * @param intent
	 */
	public static void fillIntentWith(final CallContainer callContainer,
			final Intent intent) {
		intent.putExtra(Globals.NUMBER_INTENT, callContainer.getPhoneNumber());
		intent.putExtra(Globals.NAME_INTENT, callContainer.getName());
		intent.putExtra(Globals.TYPE_INTENT, callContainer.getCallType());
		intent.putExtra(Globals.DATE_INTENT, callContainer.getCallTime());
	}

	/**
	 * Convert the given milliseconds to seconds
	 * 
	 * @param ms
	 * @return converted milliseconds
	 */
	public static long convertMStoSec(final long ms) {
		return ms / 1000 / 60;
	}

	/**
	 * Convert the given seconds to milliseconds
	 * 
	 * @param sec
	 * @return
	 */
	public static long convertSecToMs(final long sec) {
		return sec * 1000 * 60;
	}
}
