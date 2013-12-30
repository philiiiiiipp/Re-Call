package bling.bling.caller.utils;

import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CallLog;
import bling.bling.caller.manager.CallContainer;

/**
 * Utils collection
 * 
 * @author philipp
 * 
 */
public class Utils {

	/**
	 * The snooze time, when it should be asked again
	 */
	public static long SNOOZE_TIME = 10000;

	/**
	 * Extracts the called details from the given intent
	 * 
	 * @param intent
	 * @return the filled call container
	 */
	public static CallContainer extractCallContainer(final Intent intent) {
		String number = intent.getStringExtra(IntentStringExtra.NUMBER_INTENT);
		String name = intent.getStringExtra(IntentStringExtra.NAME_INTENT);
		int callType = intent.getIntExtra(IntentStringExtra.TYPE_INTENT,
				CallLog.Calls.INCOMING_TYPE);

		Date date = new Date(intent.getLongExtra(IntentStringExtra.DATE_INTENT,
				0));

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
		AlarmManager a = (AlarmManager) packageContext
				.getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent(packageContext, cls);

		// Add all information e.g. called number, name etc.
		intent.putExtras(extras);

		PendingIntent pIntent = PendingIntent.getActivity(packageContext, 0,
				intent, PendingIntent.FLAG_CANCEL_CURRENT);

		a.set(AlarmManager.RTC, System.currentTimeMillis() + time, pIntent);
	}
}
