package de.call.reminder.pro.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import de.call.reminder.pro.R;
import de.call.reminder.pro.ui.adapter.TabsAdapter;
import de.call.reminder.pro.ui.extension.ActionBarActivityWithSettings;

/**
 * List activity for the whole call history
 *
 * @author philipp
 *
 */
public class CallHistoryActivity extends ActionBarActivityWithSettings
implements TabListener {

	/**
	 * Request code
	 */
	public static final int REQUEST_CODE = 27051990;

	/**
	 * The view pager
	 */
	private ViewPager _viewPager;

	/**
	 * The tab adapter
	 */
	private TabsAdapter _tabAdapter;

	/**
	 * The action bar
	 */
	private ActionBar _actionBar;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_viewPager = (ViewPager) findViewById(R.id.pager);
		_actionBar = this.getSupportActionBar();
		_tabAdapter = new TabsAdapter(getSupportFragmentManager());

		_viewPager.setAdapter(_tabAdapter);
		_actionBar.setHomeButtonEnabled(false);
		_actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		String[] pages = this.getResources().getStringArray(
				R.array.history_pages);

		// Adding Tabs
		for (String tab_name : pages) {
			_actionBar.addTab(_actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		_viewPager
		.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(final int position) {
				_actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(final int arg0,
					final float arg1, final int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(final int arg0) {
			}
		});
	}

	@Override
	public void onTabReselected(final Tab arg0, final FragmentTransaction arg1) {

	}

	@Override
	public void onTabSelected(final Tab tab, final FragmentTransaction arg1) {
		_viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(final Tab arg0, final FragmentTransaction arg1) {

	}
}
