package ServerTasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import model.CovoiturageContract.AdEntry;
import model.CovoiturageContract.CovoiturageDbHelper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class CreateAd extends AsyncTask<HashMap<String, Object>, Void, String> {

	@Override
	protected String doInBackground(HashMap<String, Object>... params) {
		try
		{
			return doCreate(params);
		}
		catch(IOException e)
		{
			return "Unable to retrieve web page. URL may be invalid.";
		}
	}
	
	private String doCreate(HashMap<String, Object>... params) throws IOException
	{
		InputStream inputStream = null;
        String result = "";

		try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost((String) params[0].get("URL"));

            String json = "";
 
            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
 
            for (HashMap.Entry<String, Object> entry : params[0].entrySet()) {
			    String key = entry.getKey();
			    Object value = entry.getValue();
			    
			    if(key != "URL" && key != "context")
			    {
			    	jsonObject.put(key,(String) value);
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
		
		String adId = "";
		
		try 
		{
			JSONObject response = new JSONObject(result);
			adId = response.getString("message");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		CovoiturageDbHelper mDbHelper = new CovoiturageDbHelper((Context)params[0].get("context"));
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(AdEntry.F_ID_AD, Integer.parseInt(adId));
		values.put(AdEntry.F_ID_USER, Integer.parseInt((String)params[0].get("idUser")));
		values.put(AdEntry.F_DRIVER, (String)params[0].get("driver"));
		values.put(AdEntry.F_TITLE, (String)params[0].get("title"));
		values.put(AdEntry.F_DESCRIPTION, (String)params[0].get("description"));
		values.put(AdEntry.F_NB_PLACE, Integer.parseInt((String)params[0].get("nbPlace")));
		values.put(AdEntry.F_AIR_CONDITIONNER, (String)params[0].get("airConditionner"));
		values.put(AdEntry.F_HEATER, (String)params[0].get("heater"));
		
		db.insert(AdEntry.T_AD, null, values);
		
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
}
