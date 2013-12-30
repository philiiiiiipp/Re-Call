package bling.bling.caller.manager;

import java.text.DateFormat;
import java.util.Date;

import android.provider.CallLog;

/**
 * Class representation of a call, holding all available details
 * 
 * @author philipp
 * 
 */
public class CallContainer {

	private final String _phoneNumber;

	private final String _name;

	private final int _callType;

	private final Date _callTime;

	public CallContainer(final String phoneNumber, final String name,
			final int callType, final Date callTime) {
		_phoneNumber = phoneNumber;
		_name = name;
		_callType = callType;
		_callTime = callTime;
	}

	/**
	 * The phone number called
	 */
	public String getPhoneNumber() {
		return _phoneNumber;
	}

	/**
	 * The name of the person called if available
	 */
	public String getName() {
		return _name;
	}

	/**
	 * The call type
	 * 
	 * @see CallLog.Calls CallLog.Calls.TYPES
	 */
	public int getCallType() {
		return _callType;
	}

	/**
	 * @return the time the call happened
	 */
	public Date getCallTime() {
		return _callTime;
	}

	/**
	 * Get a formated String representation of the call time
	 * 
	 * @see java.text.DateFormat DateFormat.getDateTimeInstance
	 * 
	 * @return called time in String representation
	 */
	public String getFormatedDate() {

		return DateFormat.getDateTimeInstance().format(_callTime);
	}
}
