package bling.bling.caller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import android.os.Bundle;
import android.provider.CallLog;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ArrayAdapter<String> _adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _adapter = new ArrayAdapter<String>(this, 
                android.R.layout.simple_list_item_1);
        
    	ListView v = (ListView) this.findViewById(R.id.listView1);
    	v.setAdapter(_adapter);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	getCallDetails();
    }

    /**
     * Load all recent call's into the adapter
     */
    private void getCallDetails() {
    	_adapter.clear();
    	
        Cursor managedCursor = this.getApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null,null, null, null); 
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
       
        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
            case CallLog.Calls.OUTGOING_TYPE:
                dir = "OUTGOING";
                break;

            case CallLog.Calls.INCOMING_TYPE:
                dir = "INCOMING";
                break;

            case CallLog.Calls.MISSED_TYPE:
                dir = "MISSED";
                break;
            }
            
            _adapter.add("" +phNumber);
        }
        managedCursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
