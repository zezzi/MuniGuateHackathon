package com.hackaton.municiclismo;

import com.hackaton.municiclismo.data.DBAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class DistanceActivity extends ActionBarActivity {
	
	private MuniApplication appState;
	private DBAdapter db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distance_activity);
		appState = ((MuniApplication)getApplicationContext()); 
		db = appState.getDb();
		db.open();
	}
	

}
