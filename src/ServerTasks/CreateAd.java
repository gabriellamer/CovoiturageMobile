package ServerTasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import model.CovoiturageContract.AdEntry;
import model.CovoiturageContract.CovoiturageDbHelper;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class CreateAd extends AsyncTask<HashMap<String, String>, Void, String> {

	@Override
	protected String doInBackground(HashMap<String, String>... params) {
		try
		{
			return doCreate(params);
		}
		catch(IOException e)
		{
			return "Unable to retrieve web page. URL may be invalid.";
		}
	}
	
	private String doCreate(HashMap<String, String>... params) throws IOException
	{
		InputStream is = null;
		// Only display the first 500 characters of the retrieved web page content.
		//int len = 500;
		
		try
		{
			URL url = new URL(params[0].get("URL"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000/* milliseconds */);
			conn.setConnectTimeout(15000/* milliseconds */);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			
			for (HashMap.Entry<String, String> entry : params[0].entrySet()) {
			    String key = entry.getKey();
			    String value = entry.getValue();
			    
			    if(key != "URL")
			    {
			    	conn.addRequestProperty(key, value);
			    }
			}
			
			conn.connect();
			
			is = conn.getInputStream();
			
			InputStreamReader isr = new InputStreamReader(is,"UTF-8");
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(isr);
			String read = br.readLine();
			
			while(read != null) 
			{
				sb.append(read);
				read = br.readLine();
			}
			
			String adId = sb.toString();
			
			return adId;
			
//			try
//			{
//				JSONArray array = new JSONArray(jsonString);
//				
//				for(int i = 0; i< array.length(); i++)
//				{
//					JSONObject object = array.getJSONObject(i);
//					return object.getString("data");
//				}
//			}
//			catch(JSONException e)
//			{
//				e.printStackTrace();
//			}
		}
		finally
		{
			if(is != null)
			{
				is.close();
			}
		}
	}

	@Override
	protected void onPostExecute(String result) {
		
//		CovoiturageDbHelper mDbHelper = new CovoiturageDbHelper(getApplicationContext());
//		SQLiteDatabase db = mDbHelper.getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(AdEntry.F_ID_USER, Integer.parseInt(userId));
//		values.put(AdEntry.F_DRIVER, driver.isChecked());
//		values.put(AdEntry.F_TITLE, title.getText().toString());
//		values.put(AdEntry.F_DESCRIPTION, description.getText().toString());
//		values.put(AdEntry.F_NB_PLACE, Integer.parseInt(nbPlace.getText().toString()));
//		values.put(AdEntry.F_AIR_CONDITIONNER, airConditionner.isChecked());
//		values.put(AdEntry.F_HEATER, heater.isChecked());
//		
//		db.insert(AdEntry.T_AD, null, values);
		
		super.onPostExecute(result);
	}

	
}
