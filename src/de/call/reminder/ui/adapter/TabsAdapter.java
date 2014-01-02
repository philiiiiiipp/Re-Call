package de.call.reminder.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import de.call.reminder.ui.fragment.CallHistoryFragment;
import de.call.reminder.ui.fragment.ContactsFragment;

public class TabsAdapter extends FragmentPagerAdapter {

	public TabsAdapter(final FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(final int fragmentNumber) {
		switch (fragmentNumber) {
		case 0:
			return new CallHistoryFragment();
		case 1:
			return new ContactsFragment();

		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return 2;
	}
}
