package uqtr.covoituragemobile;

import java.util.HashMap;
import model.CovoiturageContract.AdEntry;
import model.CovoiturageContract.CovoiturageDbHelper;
import ServerTasks.CreateAd;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class ManageAds extends Activity {

	final String URL_CREATE_AD = "http://mil08.uqtr.ca/~milnx613/manageAd.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_ads);
		
		if(getIntent().hasExtra("adId")) {
			EditText title = (EditText) findViewById(R.id.etTitle);
			EditText nbPlace = (EditText) findViewById(R.id.etNbPlace);
			CheckBox driver = (CheckBox) findViewById(R.id.cbxDriver);
			CheckBox airConditionner = (CheckBox) findViewById(R.id.cbxAirConditionner);
			CheckBox heater = (CheckBox) findViewById(R.id.cbxHeater);
			EditText description = (EditText) findViewById(R.id.etDescription);
			
			title.setText(getIntent().getStringExtra("adTitle"));
			nbPlace.setText(getIntent().getStringExtra("adNbPlace"));
			driver.setChecked(Boolean.valueOf(getIntent().getStringExtra("adDriver")));
			airConditionner.setChecked(Boolean.valueOf(getIntent().getStringExtra("adAirCond")));
			heater.setChecked(Boolean.valueOf(getIntent().getStringExtra("adHeater")));
			description.setText(getIntent().getStringExtra("adDescription"));
			
			final Button btnDelete = (Button) findViewById(R.id.btnDelete);
			btnDelete.setVisibility(View.VISIBLE);
			btnDelete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteAd(getIntent().getStringExtra("adId"));
				}
			});
		}
		
		final Button btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(getIntent().hasExtra("adId")) {
					String adId = getIntent().getStringExtra("adId");
					modifyAd(adId);
				} else {
					String userId = getIntent().getStringExtra("userId");
					createAd(userId);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_ads, menu);
		return true;
	}
	
	private void createAd(String userId) {
		EditText title = (EditText) findViewById(R.id.etTitle);
		EditText nbPlace = (EditText) findViewById(R.id.etNbPlace);
		CheckBox driver = (CheckBox) findViewById(R.id.cbxDriver);
		CheckBox airConditionner = (CheckBox) findViewById(R.id.cbxAirConditionner);
		CheckBox heater = (CheckBox) findViewById(R.id.cbxHeater);
		EditText description = (EditText) findViewById(R.id.etDescription);
			
		// Building Parameters
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("URL", URL_CREATE_AD);
		params.put("context", this);
		params.put("idUser", userId);
		params.put("title", title.getText().toString());
		params.put("nbPlace", nbPlace.getText().toString());
		params.put("driver", driver.isChecked()? "1": "0");
		params.put("airConditionner", airConditionner.isChecked()? "1": "0");
		params.put("heater", heater.isChecked()? "1": "0");
		params.put("description", description.getText().toString());

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        
        if(networkInfo != null && networkInfo.isConnected())
        {
        	new CreateAd().execute(params);
        }
        else
        {
        	AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

            dlgAlert.setMessage("No network connection available.");
            dlgAlert.setTitle("Error Message...");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        
		finish();
	}
	
	private void modifyAd(String adId) {
		EditText title = (EditText) findViewById(R.id.etTitle);
		EditText nbPlace = (EditText) findViewById(R.id.etNbPlace);
		CheckBox driver = (CheckBox) findViewById(R.id.cbxDriver);
		CheckBox airConditionner = (CheckBox) findViewById(R.id.cbxAirConditionner);
		CheckBox heater = (CheckBox) findViewById(R.id.cbxHeater);
		EditText description = (EditText) findViewById(R.id.etDescription);
		
		CovoiturageDbHelper mDbHelper = new CovoiturageDbHelper(this);
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(AdEntry.F_ID_AD, Integer.parseInt(adId));
		values.put(AdEntry.F_DRIVER, driver.isChecked());
		values.put(AdEntry.F_TITLE, title.getText().toString());
		values.put(AdEntry.F_DESCRIPTION, description.getText().toString());
		values.put(AdEntry.F_NB_PLACE, Integer.parseInt(nbPlace.getText().toString()));
		values.put(AdEntry.F_AIR_CONDITIONNER, airConditionner.isChecked());
		values.put(AdEntry.F_HEATER, heater.isChecked());
		
		String selection = AdEntry.F_ID_AD + " = ?";
		
		String[] selectionArgs = {
				adId
		};
		
		db.update(AdEntry.T_AD, values, selection, selectionArgs);
		
		finish();
	}
	
	private void deleteAd(String adId) {
		CovoiturageDbHelper mDbHelper = new CovoiturageDbHelper(this);
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		String selection = AdEntry.F_ID_AD + " = ?";
		
		String[] selectionArgs = {
				adId
		};
		
		db.delete(AdEntry.T_AD, selection, selectionArgs);
		
		finish();
	}
}
