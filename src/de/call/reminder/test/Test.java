package de.call.reminder.test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import de.call.reminder.manager.CallContainer;

public class Test {

	/**
	 * Creates test data for our UI
	 * 
	 * @return a list of dummy data
	 */
	public static List<CallContainer> getDummyData() {
		List<CallContainer> dummyData = new LinkedList<CallContainer>();

		dummyData.add(new CallContainer("1234", "Dummy1", 1, new Date(System
				.currentTimeMillis() - 10000)));
		dummyData.add(new CallContainer("12312", "Dummy2", 1, new Date(System
				.currentTimeMillis() - 20000)));
		dummyData.add(new CallContainer("523234", "Dummy3", 1, new Date(System
				.currentTimeMillis() - 30000)));
		dummyData.add(new CallContainer("52346", "Dummy4", 1, new Date(System
				.currentTimeMillis() - 40000)));
		dummyData.add(new CallContainer("341231634", "Dummy5", 1, new Date(
				System.currentTimeMillis() - 50000)));

		return dummyData;
	}
}
