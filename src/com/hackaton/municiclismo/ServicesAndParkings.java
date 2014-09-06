package com.hackaton.municiclismo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hackaton.municiclismo.data.DBAdapter;
import com.hackaton.municiclismo.data.LatLong;
import com.hackaton.municiclismo.data.Places;
import com.hackaton.municiclismo.util.Utilities;

public class ServicesAndParkings extends Activity{
	
	private MuniApplication appState;
	Utilities mUtilities;
	private DBAdapter db;
	private HashMap<Marker, Places> servicesMarkers;
	
	SupportMapFragment mSupportMapFragment;
	private GoogleMap  map;
	private HashMap<Marker, LatLong> posMarkerMap = new HashMap<Marker, LatLong>();
	
	String ActivityId;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finish_tracking);
		/*appState = (MuniApplication)getApplicationContext();
		mUtilities = ((MuniApplication) getApplicationContext()).getUtilities();
		db = appState.getDb();
		Bundle extras = getIntent().getExtras();
		MapFragment mm =((MapFragment)getFragmentManager().findFragmentById(R.id.mapServices));
		if(mm!=null){
			map=mm.getMap();
			map.setMyLocationEnabled(true);
			if(map!=null){
				map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				setupServices();
				setupParkins();
				moveMapToMyLocation();
			}
		}*/
	}
	
	public void setupServices(){
		List <Places> services_lista=new ArrayList<Places>();
		
		Places restaurante1 = new Places();
		restaurante1.setId("1");
		restaurante1.setTitle("Taller de Biciletas A");
		restaurante1.setComment("De todo en Reparaciones");
		restaurante1.setLatitud("-45.54");
		restaurante1.setLongitud("-90.34");
		restaurante1.setName("Taller de Bicicletas A");
		restaurante1.setType("service");
		restaurante1.setPhone("45636332");
		restaurante1.setAddress("12 Calle 12-34 zona 4");
		
		Places restaurante2 = new Places();
		restaurante1.setId("1");
		restaurante1.setTitle("Taller de Biciletas B");
		restaurante1.setComment("De todo en Reparaciones");
		restaurante1.setLatitud("-45.58");
		restaurante1.setLongitud("-90.40");
		restaurante1.setName("Taller de Bicicletas B");
		restaurante1.setType("service");
		restaurante1.setPhone("45636332");
		restaurante1.setAddress("11 avenida 2-34 zona 4");
		
		Places restaurante3 = new Places();
		restaurante1.setId("1");
		restaurante1.setTitle("Taller de Biciletas C");
		restaurante1.setComment("De todo en Reparaciones");
		restaurante1.setLatitud("-45.64");
		restaurante1.setLongitud("-90.50");
		restaurante1.setName("Taller de Bicicletas C");
		restaurante1.setType("service");
		restaurante1.setPhone("45636332");
		restaurante1.setAddress("11 avenida 89-2  zona 10");
		
		
		Places restaurante4 = new Places();
		restaurante1.setId("1");
		restaurante1.setTitle("Taller de Biciletas E");
		restaurante1.setComment("De todo en Reparaciones");
		restaurante1.setLatitud("-45.58");
		restaurante1.setLongitud("-90.50");
		restaurante1.setName("Taller de Bicicletas E");
		restaurante1.setType("service");
		restaurante1.setPhone("45636332");
		restaurante1.setAddress("11 avenida 2-34 zona 4");
		
		
		services_lista.add(restaurante1);
		services_lista.add(restaurante2);
		services_lista.add(restaurante3);
		services_lista.add(restaurante4);
		servicesMarkers = new HashMap<Marker, Places>();
  	  for (Places temp : services_lista) {
  		 
  		  Marker marker_res = map.addMarker(new MarkerOptions()
            					.position(new LatLng(Double.valueOf(temp.getLatitud()).doubleValue(),Double.valueOf(temp.getLongitud()).doubleValue()))
            					.title(temp.getTitle())
            					.snippet(temp.getComment())
            					.icon(BitmapDescriptorFactory
            					.fromResource(R.drawable.mapicon)));
  		  servicesMarkers.put(marker_res, temp);
  	  }
  	  map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
            	Places lugar_presionado=servicesMarkers.get(marker);
                Intent nextScreen = new Intent(appState, DetailActivity.class);
                nextScreen.putExtra("lugar_title",lugar_presionado.getTitle());
                nextScreen.putExtra("lugar_desc",lugar_presionado.getComment());
                nextScreen.putExtra("lugar_address",lugar_presionado.getAddress());
                nextScreen.putExtra("lugar_code",lugar_presionado.getPhone());
                startActivityForResult(nextScreen, 0);
            }
        });

	}
	
	
	
	public void setupParkins(){
		
	}
	
	private void moveMapToMyLocation() {
    	LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    	Criteria crit = new Criteria();
    	Location loc = locationManager.getLastKnownLocation(locationManager.getBestProvider(crit, false));
    	map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(),loc.getLongitude()), 35)); 
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        

    }
}
