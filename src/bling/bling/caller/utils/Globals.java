package bling.bling.caller.utils;

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
	public static long SNOOZE_TIME = 1000 * 60 * 5;

	/**
	 * Quit the app after activity is finished
	 */
	public static int RESULT_QUIT = 30041987;
}
