package bling.bling.caller;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import bling.bling.caller.manager.CallContainer;
import bling.bling.caller.manager.CallManager;
import bling.bling.caller.manager.CallManagerListener;

public class MainActivity extends Activity implements CallManagerListener {

	private ArrayAdapter<String> _adapter;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);

		ListView v = (ListView) this.findViewById(R.id.listView1);
		v.setAdapter(_adapter);

		CallManager.get().subscribe(this);
	}

	@Override
	protected void onStop() {
		super.onStop();

		CallManager.get().unsubscribe(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		CallManager.get().getCallData(this, 100);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void recieveCallData(final List<CallContainer> callData) {
		_adapter.clear();

		for (CallContainer c : callData) {
			_adapter.add(c.getName() + c.getPhoneNumber());
		}

	}
}
