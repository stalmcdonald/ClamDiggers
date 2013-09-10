/*
 * Crystal McDonald
 * Java II
 * 1309
 * Week 2
 */
package com.cm.clamdiggers;

import com.example.clamdiggers.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TideActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tide);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tide, menu);
		return true;
	}

}
