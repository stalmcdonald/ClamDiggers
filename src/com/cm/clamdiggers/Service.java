/*
 * Crystal McDonald
 * Java II
 * 1309
 * Week 2
 * 
 * (S.Dubin)
 */
package com.cm.clamdiggers;

import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.cm.clamdiggers.DataFile;

import android.app.Activity;
import android.app.IntentService;

import android.content.Intent;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import android.util.Log;

public class Service extends IntentService{
	
	
	public static final String MESSENGER_KEY = "messenger";
    public static final String TIDAL_CITY = "tidal_city";
    public static final String FINAL_URL = "final_URL";
    
    String tidal_city;
	String url;
	
	Messenger messenger;
	Message message;
	
	URL finalUrl;

	public Service() {
		super("Service");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		//Log.i("onHandleIntent", "started");
		Log.i("SERVICE", "Service started, onHandleIntent()");
		message = Message.obtain();
		
		//JSON web api call and Storage here, retrieving info to handle service
		Bundle extras = intent.getExtras();
		
		//access to the handler
		//KEYS
		messenger = (Messenger)extras.get(MESSENGER_KEY);
		tidal_city =(String) extras.getString(TIDAL_CITY);
		url = (String) extras.getString(FINAL_URL);
		
		
		//Call JSON web api here
		try {
			finalUrl = new URL(url);//("http://api.wunderground.com/api/3e64fa36c4f09bdd/tide/q/WA/seattle.json");
			String response = WebFile.getURLSTringResponse(finalUrl);
			
			//data being stored in file here
			JSONObject json;
			
			//log cat displays json data that is held in tideInfo.txt
			Log.i("SERVICE", response);	
			
			try {
				json = new JSONObject(response);
				DataFile.storeStringFile(getBaseContext(), "tideInfo.txt", json.toString(), false);
				//Log.d("JSON DEBUG", json.toString());
				message.arg1 = Activity.RESULT_OK;
			} catch (JSONException e) {
                
				Log.e("JSON EXCEPTION", e.getMessage().toString());
				e.printStackTrace();
				message.arg1 = Activity.RESULT_CANCELED;
			}
            
		} catch (Exception e) {
			Log.e("URL STRING RESPONSE EXCEPTION", e.getMessage().toString());
		}
		
		
        try {
            
            message.arg1 = Activity.RESULT_OK;
            message.obj = "Data Service Complete";
           
            //send message to activity
            messenger.send(message);
            Log.d("SERVICE CLASS", "onHandleIntent()");
            
        } catch (RemoteException e) {
            Log.e("EXCEPTION ON HANDLE INTENT", e.getMessage().toString());
            e.printStackTrace();
        }
		
	}
    
}
