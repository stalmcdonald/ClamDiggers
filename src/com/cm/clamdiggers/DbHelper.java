package com.cm.clamdiggers;

import android.content.Context;

public class DbHelper {

	public static String FILE_NAME = "tideInfo.txt";
	
	public static String JSON_TIDE = "tide";
	public static String JSON_DATA = "type";
	public static String JSON_INFO = "tideInfo";
	public static String JSON_SUMMARY = "tideSummary";
	public static String JSON_LOCATION = "tidesite";
	public static String JSON_DATE = "pretty";
	public static String JSON_SWELL = "height";
	
	private final String tideURL = "http://api.wunderground.com/api/d4509f6df6f598a0/tide/q/WA/";
	private Context _context;
	
	
	

}
