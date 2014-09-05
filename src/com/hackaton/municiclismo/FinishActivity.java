package com.hackaton.municiclismo;

import java.util.HashMap;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hackaton.municiclismo.data.DBAdapter;
import com.hackaton.municiclismo.data.LatLong;
import com.hackaton.municiclismo.util.Utilities;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FinishActivity extends Activity{
	
	private MuniApplication appState;
	Utilities mUtilities;
	private DBAdapter db;
	
	SupportMapFragment mSupportMapFragment;
	private GoogleMap  map;
	private HashMap<Marker, LatLong> posMarkerMap = new HashMap<Marker, LatLong>();
	String ActivityId;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finish_tracking);
		appState = (MuniApplication)getApplicationContext();
		mUtilities = ((MuniApplication) getApplicationContext()).getUtilities();
		db = appState.getDb();
		Bundle extras = getIntent().getExtras();
		ActivityId = extras.getString("idActivity");
		MapFragment mm =((MapFragment)getFragmentManager().findFragmentById(R.id.map));
		if(mm!=null){
			map=mm.getMap();
			map.setMyLocationEnabled(true);
			if(map!=null){
				map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				setupRoute(ActivityId);
			}
		}
	}
	
	public void setupRoute(String ActivityId){
			Cursor latlngobjects=db.getLatLng("activity="+ActivityId);
			for(int i=0;i<latlngobjects.getCount();i++){
				LatLong pos_c=db.getLanLngsFromCursor(latlngobjects,2,i);
				LatLng positiond= new LatLng(Double.valueOf(pos_c.getLatitud()).doubleValue(),Double.valueOf(pos_c.getLongitud()).doubleValue());
				Marker marker_res = map.addMarker(new MarkerOptions()
              	.position(positiond)
              	.title(pos_c.getDatePosted()));
              	posMarkerMap.put(marker_res, pos_c);

			}
			
	}

}
