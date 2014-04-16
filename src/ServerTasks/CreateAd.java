package ServerTasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

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
		InputStream inputStream = null;
        String result = "";

		try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(params[0].get("URL"));

            String json = "";
 
            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
 
            for (HashMap.Entry<String, String> entry : params[0].entrySet()) {
			    String key = entry.getKey();
			    String value = entry.getValue();
			    
			    if(key != "URL")
			    {
			    	jsonObject.put(key, value);
			    }
			}
            
            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
 
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
            
            // 6. set httpPost Entity
            httpPost.setEntity(se);
 
            // 7. Set some headers to inform server about the type of the content   
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
 
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
 
            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // 10. convert input stream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
		
		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
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
