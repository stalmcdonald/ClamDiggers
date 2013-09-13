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
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class DataProvider extends ContentProvider {
	
	//creating authority
	//create a private string based on what package is called
	public static final String AUTH = "com.cm.clamdiggers.DataProvider";
	
	public static class TideData implements BaseColumns{
		
		//set up uri definitions
		public static final Uri TIDE_URI_A = Uri.parse("content://"+AUTH+"/"+"/itemsa");
		public static final Uri TIDE_URI_B = Uri.parse("content://"+AUTH+"/"+"/itemsb");
		public static final Uri TIDE_URI_C = Uri.parse("content://"+AUTH+"/"+"/itemsc");
		public static final Uri TIDE_URI_D = Uri.parse("content://"+AUTH+"/"+"/itemsd");
		
		
		public static final String CONTENT_TYPE = "vnd.andoid.cursor.dir/vnd.cm.tide.item";
		public static final String CONTENT_ITEM_TYPE = "vnd.andoid.cursor.item/vnd.cm.tide.item";
		
		//Define columns
		public static final String LOCATION_COLUMN = "location";
		public static final String CALENDAR_COLUMN = "calendar";
		public static final String PREDICTION_COLUMN = "prediction";
		public static final String SWELL_COLUMN = "swell";
		
		//defining what columns to be returned
		public static final String[] PROJECTION= { "_Id", LOCATION_COLUMN, CALENDAR_COLUMN,PREDICTION_COLUMN,SWELL_COLUMN};
		
		//constructor for TideData
		private TideData() {};
	}
	

	public static final int ITEMS = 1;//define items is the uri for returning all items collected from json string
    //permit items to be collected into string
	public static final int ITEMS_ID = 2;//DB world index starts at 1
	
	//create uri matcher
	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	//set static data up before execution of runtime to save start up time
	static{
		uriMatcher.addURI(AUTH, "items/", ITEMS);//return all items in JSON file collected
		uriMatcher.addURI(AUTH, "items/abc", ITEMS_ID);//request items by index number
	}

//CAUSES ERROR	
//	final static int TIDE = 1;
	
	//not allowing data to be deleted
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		//unsupported method added throw
		throw new UnsupportedOperationException();
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		
		switch (uriMatcher.match(uri)) {
		case ITEMS:
			return TideData.CONTENT_TYPE;
			
		case ITEMS_ID:
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
		
		MatrixCursor result = new MatrixCursor(TideData.PROJECTION);
		
		String JSONString = DataFile.readStringFile(getContext(), DataFile.FILE_NAME2, false);//make sure have data stored to have something to return
		JSONObject job = null;
		JSONArray recordArray = null;//tideSummary
		JSONObject field = null;
		JSONObject tide = null;
		JSONObject data = null;
		JSONObject height = null;
		JSONObject date = null;
		JSONObject pretty = null;
		JSONObject type = null;
		JSONArray locArray = null;//tideInfo
		JSONObject tideInfo = null;

		try {
		job = new JSONObject(JSONString);
		
		    recordArray = job.getJSONObject("tide").getJSONArray("tideSummary");
			recordArray = job.getJSONArray(DbHelper.JSON_SUMMARY);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (recordArray == null)
		{
			return result;
		}
		
		switch (uriMatcher.match(uri)){
		case ITEMS:
			for(int i = 0; i < recordArray.length(); i++) {
				{
					try {
						Log.i("recordArray",recordArray.getJSONObject(i).toString());
						field = recordArray.getJSONObject(i).getJSONObject(DbHelper.JSON_SUMMARY);
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
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
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
