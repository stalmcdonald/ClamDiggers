/*
 * Crystal McDonald
 * Java II
 * 1309
 * Week 2
 * Content Provider
 */
package com.cm.clamdiggers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class DataProvider extends ContentProvider {
	
	//creating authority
	//create a private string based on what package is called
	public static final String AUTH = "com.cm.clamdiggers.DataProvider";
	
	public static class TideData implements BaseColumns{
		
		//set up uri definitions
		public static final Uri TIDE_URI_ALL = Uri.parse("content://"+AUTH+"/"+"/items/all");// all tide types or tidetypes for 3 days
//		public static final Uri TIDE_URI_A = Uri.parse("content://"+AUTH+"/"+"/items/location/");
//		public static final Uri TIDE_URI_B = Uri.parse("content://"+AUTH+"/"+"/items/pretty/");
		public static final Uri TIDE_URI_CURRENT = Uri.parse("content://"+AUTH+"/"+"/items/current/");
		public static final Uri TIDE_URI_CLAMMERS = Uri.parse("content://"+AUTH+"/"+"/items/clammer/");
		
		
		public static final String CONTENT_TYPE = "vnd.andoid.cursor.dir/vnd.cm.tide.item";
		public static final String CONTENT_ITEM_TYPE = "vnd.andoid.cursor.item/vnd.cm.tide.item";
		
		//Define columns
		public static final String LOCATION_COLUMN = "tideSite";//location 
		public static final String CALENDAR_COLUMN = "pretty";//date
		public static final String PREDICTION_COLUMN = "type";//lo/hi tide
		public static final String SWELL_COLUMN = "height";//wave height
		
		//defining what columns to be returned
		public static final String[] PROJECTION= { "_Id", LOCATION_COLUMN, CALENDAR_COLUMN,PREDICTION_COLUMN,SWELL_COLUMN};
		
		//constructor for TideData
		private TideData() {};
	}
	

	public static final int ITEMS = 1;//define items is the uri for returning all items collected from json string
    //permit items to be collected into string
	public static final int ITEMS_ID = 2;//DB world index starts at 1
	public static final int ITEMS_ALLTIDE_FILTER = 3;
	public static final int ITEMS_CURRENTTIDE_FILTER = 4;
	public static final int ITEMS_LOWTIDE_FILTER = 5;
	
	//create uri matcher
	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	//set static data up before execution of runtime to save start up time
	static{
		uriMatcher.addURI(AUTH, "items/all/", ITEMS);//return all items in JSON file collected
		//uriMatcher.addURI(AUTH, "items/all/", ITEMS_ID);//request items by index number
		
		uriMatcher.addURI(AUTH, "/items/clammers/", ITEMS_LOWTIDE_FILTER);//gets current tide
		uriMatcher.addURI(AUTH, "/items/current/", ITEMS_CURRENTTIDE_FILTER);//cycles through all low tides for the city
		uriMatcher.addURI(AUTH, "/items/all/", ITEMS_ALLTIDE_FILTER);//provides all tide data
	}

//CAUSES ERROR	
//	final static int TIDE = 1;
	
	//not allowing data to be deleted
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		//unsupported method added throw
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("null")
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		
		switch (uriMatcher.match(uri)) {
		case ITEMS:
			return TideData.CONTENT_TYPE;
			
		case ITEMS_ID:
		case ITEMS_LOWTIDE_FILTER:
		
			@SuppressWarnings("unused")
			String lowTideFiltered = uri.getLastPathSegment();
			//Log.e("Request low tides: " +lowTideFiltered);
			
			JSONArray recordArray = null;
			
			for (int i = 0; i < recordArray.length(); i++);
			{
				//try{
					//field = recordArray.getJSONObject(i).getJSONObject(DbHelper.JSON_DATA);
				//}
			}
			
		case ITEMS_CURRENTTIDE_FILTER:
		
			return TideData.CONTENT_ITEM_TYPE;
		}
		return null;
	}

	//adds more data but this is a read only data Content Provider
	//not allowing data to be inserted
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		//unsupported method added throw
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	
	//not sure how to set this part up.
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		String TAG = "URI MATCHER";
		MatrixCursor result = new MatrixCursor(TideData.PROJECTION);
		
		String JSONString = DataFile.readStringFile2(getContext(), "tideInfo.txt");//make sure have data stored to have something to return
		JSONObject job = null;
		JSONArray recordArray = null;//tideSummary
		JSONObject field = null;
		JSONArray locArray = null;//tideInfo


		try {
		job = new JSONObject(JSONString);
		recordArray = job.getJSONObject(DbHelper.JSON_TIDE).getJSONArray("tideSummary");
//		    recordArray = job.getJSONObject("tide").getJSONArray("tideSummary");
//			recordArray = job.getJSONArray(DbHelper.JSON_SUMMARY);
//			result.addRow(new Object[] { i + 1, field.get(DbHelper.JSON_DATE),field.get(DbHelper.JSON_PRETTY),field.get(DbHelper.JSON_DATA),
//					field.get(DbHelper.JSON_SWELL)});
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (recordArray == null)
		{
			Log.e("query", "null array");
			return result;
		}
		
		switch (uriMatcher.match(uri)){
		case ITEMS:
			Log.i(TAG, "items");
			for(int i = 0; i < recordArray.length(); i++) {
				{
					try {
						Log.i("recordArray",recordArray.getJSONObject(i).toString());
						//parsing JSON Data here
						field = recordArray.getJSONObject(i).getJSONObject(DbHelper.JSON_DATA);
						Log.d("DATA PROVIDER", "field: " +field);
						result.addRow(new Object[] { i + 1, field.get("tidesite"),
								//field.getJSONObject("data"),//drill into object to get prediction/wave height 
								field.get("type"),
								field.get("type"),//prediction
								field.get("height")});//swell/waveheight
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
                
			}
			break;
			
		case ITEMS_ID:
			for(int i1 = 0; i1 < locArray.length(); i1++) {
				try {
					Log.i("locArray",locArray.getJSONObject(i1).toString());
					
					//if(field.getString(new Object[] { i1 + 1, field.get(DbHelper.JSON_DATA);
						//field.get(DbHelper.JSON_SWELL)}));
				} catch (JSONException e1) {
					//  block
					e1.printStackTrace();
				}
			}
			break;
		default:
			Log.e("query", "uri not valid: " + uri.toString());
		}
		
		return result;
			
		}
	//not allowing data to be updated
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		//unsupported method added throw
			throw new UnsupportedOperationException();
	}

}
