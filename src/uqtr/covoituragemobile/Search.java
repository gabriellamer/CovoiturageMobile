package uqtr.covoituragemobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;

public class Search extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		EditText description = (EditText) findViewById(R.id.search_description);
		CheckBox man = (CheckBox) findViewById(R.id.search_man);
		CheckBox woman = (CheckBox) findViewById(R.id.search_woman);
		CheckBox driver = (CheckBox) findViewById(R.id.search_driver);
		CheckBox passenger = (CheckBox) findViewById(R.id.search_passenger);
		CheckBox heater = (CheckBox) findViewById(R.id.search_heater);
		CheckBox airConditionner = (CheckBox) findViewById(R.id.search_air_conditionner);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_user_ads:
	        	Intent adsIntent = new Intent(this, UserAds.class);
	    		startActivity(adsIntent);
	            return true;
	        case R.id.action_user_profile:
	        	Intent userIntent = new Intent(this, ManageUser.class);
	    		startActivity(userIntent);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
