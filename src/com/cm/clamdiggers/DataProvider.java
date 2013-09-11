/*
 * Crystal McDonald
 * Java II
 * 1309
 * Week 2
 * Content Provider
 */
package com.cm.clamdiggers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DataProvider extends ContentProvider {

	//create a private string based on what package is called
	private static final String AUTH = "com.cm.clamdiggers.DataProvider";
	//static variable for the whole application  path to the content provider
	//uri is like a url (web address) to the database in the application
	//creating private uri for app
	public static final Uri TIDES_URI = Uri.parse("content://"+AUTH+"/"+DbHelper.TABLE_NAME);//change dbHelper to another class
	
	final static int TIDE = 1;
	
	//setting up variables for database
	SQLiteDatabase db;
	DbHelper dbhelper;//change class name
	
	//creating uri matcher
	private final static UriMatcher uriMatcher;
	
	static
	{
		//initializes uriMatcher
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		int code;
		//adding uri to uriMatcher
		uriMatcher.addURI(AUTH, DbHelper.TABLE_NAME, code);
	}
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
