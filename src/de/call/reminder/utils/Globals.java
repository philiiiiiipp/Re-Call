package de.call.reminder.utils;

public class Globals {

	/*
	 * Intent
	 */

	/**
	 * Intent name of the number data
	 */
	public static final String NUMBER_INTENT = "call_detail_number_intent";

	/**
	 * Intent name of the date data
	 */
	public static final String DATE_INTENT = "call_detail_date_intent";

	/**
	 * Intent name of the name data
	 */
	public static final String NAME_INTENT = "call_detail_name_intent";

	/**
	 * Intent name of the type data
	 */
	public static final String TYPE_INTENT = "call_detail_type_intent";

	/*
	 * Other
	 */

	/**
	 * The snooze time, when it should be asked again
	 */
	public static long DEFAULT_SNOOZE_TIME = 5;

	/**
	 * The default maximum alarm duration
	 */
	public static long DEFAULT_MAX_ALARM_DURATION = 2;

	/**
	 * Quit the app after activity is finished
	 */
	public static int RESULT_QUIT = 30041987;

	/*
	 * Preferences
	 */

	/**
	 * Preference field for the snooze time
	 */
	public static final String SNOOZE_TIME_PREFERENCE = "snooze_time_preference";

	/**
	 * Preference field for the maximum alarm duration
	 */
	public static final String MAX_ALARM_TIME_PREFERENCE = "max_alarm_duration_preference";
}
