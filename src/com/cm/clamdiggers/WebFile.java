/*
 * 
 * Crystal McDonald
 * Java II
 * 1308
 * Week 2
 * 
 * (S.Dubin)
 */
package com.cm.clamdiggers;
import java.io.BufferedInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class WebFile {
	//build functionality to connect to a website and pull data back and test for internet connection


		static Boolean _conn = false; //always assume there's no connection
		static String _connectionType = "Unavailable"; //unavailable by default
		
		public static String getConnnectionType(Context context){ //runs function
			netInfo(context);
			return _connectionType;
		}
		
		public static Boolean getConnectionStatus(Context context){ //returns boolean
			netInfo(context);
			return _conn; //returns connection status
		}
		
		//test Internet connection on android device
		private static void netInfo(Context context){
			//creating 2 values representing if connected and what type of connection
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();//gets dynamic network info.
			if(ni != null){
				if(ni.isConnected()){
					_connectionType = ni.getTypeName();//if there's a connection and what type it is
					_conn = true;//actually do have a connection
			}
		
		}
	}	
		/*
		 * Working URL responder
		 */
		public static String getURLSTringResponse(URL url){
			String response = "";
			
			//run context int specific
			//try catch statement
			try{
				Log.d("WEB FILE", "url="+url.toString());
				URLConnection conn = url.openConnection();//open connection to a server
				//accept the info url returns thru buffer input string
				
				BufferedInputStream bin = new BufferedInputStream(conn.getInputStream());//get data in order, get new stream and attach it to buffer
				
				byte[] contentBytes = new byte[1024];
				//loop thru bytes needed to get and keep track of them
				int bytesRead = 0;
				//holds data as it comes in as a string form.
				StringBuffer responseBuffer = new StringBuffer();
				
				//allow the bytes read to count up until the entire file is there
				while((bytesRead = bin.read(contentBytes))!= -1){
					response = new String(contentBytes,0,bytesRead);
					//content buffered into a single string
					responseBuffer.append(response);
					
				}
								
				Log.d("WEB FILE", "response"+response);
	    		// Verify JSON String
	    		JSONObject json = null;
	        	try {
	    			json = new JSONObject(response);
	    			Log.i("JSON", "valid json object");
	    		} catch (JSONException e) {
	    			// TODO Auto-generated catch block
	    			//Log.e("ERROR storeJSON", uri.toString());
	    			Log.e("EXCEPTION", e.getMessage().toString());
	    		}
	        	
	        	if (json != null)
	        	{
	        		//Implement a Data Storage method
	        		//DataFile.storeStringFile(context, "crystal_tide_data.txt", json.toString());
	        	}
				
				
				
				//response buffer holds all data
				return responseBuffer.toString();
			} catch (Exception e){
				//Log.e("WebFile, URL RESPONSE ERROR", e.toString());
				e.printStackTrace();
			}
				
			
			return response;
		}
	}
