package com.hackaton.municiclismo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hackaton.municiclismo.data.DBAdapter;
import com.hackaton.municiclismo.data.LatLong;
import com.hackaton.municiclismo.data.Places;
import com.hackaton.municiclismo.util.Utilities;

public class ParqueosFragment extends Fragment{
	
	private MuniApplication appState;
	private DBAdapter db;
	Utilities mUtilities;
	private HashMap<Marker, Places> servicesMarkers;
	MapFragment mSupportMapFragment;
	private GoogleMap  map;
	Location loc ;
	private HashMap<Marker, LatLong> posMarkerMap = new HashMap<Marker, LatLong>();
	
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);  
	     appState = ((MuniApplication) getActivity().getApplicationContext());   
		 db = appState.getDb();
		 db.open();
		 
	  }
	 
	 @Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {
		
		 
		}
	 
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View rootView = inflater.inflate(R.layout.parqueos_fragment, container,
	                false);
	        map =((SupportMapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
			 
	        if (map!=null){
	        	map.setMyLocationEnabled(true);
	        	
	        	moveMapToMyLocation();
	        	LayoutInflater inflaterlayout =(LayoutInflater) appState.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
	 	       	map.setInfoWindowAdapter(new CustomInfoWindow(inflaterlayout));
	 	       	setupServices();
	 	       	setupParkins();
	        }
	       
	     return rootView;
	 }
	 
	 public void setupServices(){
			List <Places> services_lista=new ArrayList<Places>();
			
			Places restaurante1 = new Places();
			restaurante1.setId("1");
			restaurante1.setTitle("Taller de Biciletas A");
			restaurante1.setComment("De todo en Reparaciones COmpra de Bicicletas");
			restaurante1.setLatitud("14.6234549");
			restaurante1.setLongitud("-90.5146601");
			restaurante1.setName("Taller de Bicicletas A");
			restaurante1.setType("service");
			restaurante1.setPhone("45636332");
			restaurante1.setAddress("12 Calle 12-34 zona 4");
			
			Places restaurante2 = new Places();
			restaurante2.setId("1");
			restaurante2.setTitle("Taller de Biciletas B");
			restaurante2.setComment("De todo en Reparaciones Pinchazo de Bicicletas");
			restaurante2.setLatitud("14.6229773");
			restaurante2.setLongitud("-90.509639");
			restaurante2.setName("Taller de Bicicletas B");
			restaurante2.setType("service");
			restaurante2.setPhone("45636332");
			restaurante2.setAddress("11 avenida 2-34 zona 4");
			
			Places restaurante3 = new Places();
			restaurante3.setId("1");
			restaurante3.setTitle("Taller de Biciletas C");
			restaurante3.setComment("De todo en Reparaciones Cascos para Bicicletas");
			restaurante3.setLatitud("-45.64");
			restaurante3.setLongitud("-90.50");
			restaurante3.setName("Taller de Bicicletas C");
			restaurante3.setType("service");
			restaurante3.setPhone("45636332");
			restaurante3.setAddress("11 avenida 89-2  zona 10");
			
			
			Places restaurante4 = new Places();
			restaurante4.setId("1");
			restaurante4.setTitle("Taller de Biciletas E");
			restaurante4.setComment("De todo en Reparaciones SOlo reparaciones");
			restaurante4.setLatitud("14.6215862");
			restaurante4.setLongitud("-90.5154969");
			restaurante4.setName("Taller de Bicicletas E");
			restaurante4.setType("service");
			restaurante4.setPhone("45636332");
			restaurante4.setAddress("11 avenida 2-34 zona 4");
			
			
			Places restaurante5 = new Places();
			restaurante5.setId("1");
			restaurante5.setTitle("Taller de Biciletas E");
			restaurante5.setComment("De todo en Reparaciones :D");
			restaurante5.setLatitud("14.6215862");
			restaurante5.setLongitud("-90.5154969");
			restaurante5.setName("Taller de Bicicletas E");
			restaurante5.setType("service");
			restaurante5.setPhone("45636332");
			restaurante5.setAddress("11 avenida 2-34 zona 4");
			
			services_lista.add(restaurante1);
			services_lista.add(restaurante2);
			services_lista.add(restaurante3);
			services_lista.add(restaurante4);
			services_lista.add(restaurante5);
			
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
	                nextScreen.putExtra("phone",lugar_presionado.getPhone());
	                nextScreen.putExtra("type", lugar_presionado.getAddress());
	                startActivityForResult(nextScreen, 0);
	            }
	        });
	  	map.setMapType(GoogleMap.MAP_TYPE_NORMAL);   
		}
		
		public void setupParkins(){
			List <Places> services_lista=new ArrayList<Places>();
			
			Places parking1 = new Places();
			parking1.setId("1");
			parking1.setTitle("Parqueo de Bicicletas");
			parking1.setComment("10 parqueos zona 4 una linda instalacions para ir a arreglar bicicletas");
			parking1.setLatitud("14.621184");
			parking1.setLongitud("-90.5146601");
			parking1.setName("Parqueo de Bicicletas");
			parking1.setType("parking");
			parking1.setPhone("");
			parking1.setAddress("11 avenida 89-2  zona 4");
			
			Places parking2 = new Places();
			parking2.setId("1");
			parking2.setTitle("Parqueo de Bicicletas");
			parking2.setComment("10 parqueos zona 4  una linda instalacions para ir a arreglar bicicletas");
			parking2.setLatitud("14.621184");
			parking2.setLongitud("-90.5185117");
			parking2.setName("Parqueo de Bicicletas");
			parking2.setType("parking");
			parking2.setPhone("");
			parking2.setAddress("11 avenida 89-2  zona 4");
			
			Places parking3 = new Places();
			parking3.setId("1");
			parking3.setTitle("Parqueo de Bicicletas");
			parking3.setComment("10 parqueos zona 4 Dos  una linda instalacions para ir a arreglar bicicletas");
			parking3.setLatitud("14.6205041");
			parking3.setLongitud("-90.5160404");
			parking3.setName("Parqueo de Bicicletas");
			parking3.setType("parking");
			parking3.setPhone("");
			parking3.setAddress("11 avenida 89-2  zona 4");
			
			
			
			
			services_lista.add(parking1);
			services_lista.add(parking2);
			services_lista.add(parking3);
			
			
			
	  	  for (Places temp : services_lista) {
	  		 
	  		  Marker marker_res = map.addMarker(new MarkerOptions()
	            					.position(new LatLng(Double.valueOf(temp.getLatitud()).doubleValue(),Double.valueOf(temp.getLongitud()).doubleValue()))
	            					.title(temp.getTitle())
	            					.snippet(temp.getComment())
	            					.icon(BitmapDescriptorFactory
	            					.fromResource(R.drawable.mapicon2)));
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
	                nextScreen.putExtra("phone",lugar_presionado.getPhone());
	                nextScreen.putExtra("type", lugar_presionado.getType());
	                startActivityForResult(nextScreen, 0);
	            }
	        });
	  	map.setMapType(GoogleMap.MAP_TYPE_NORMAL);   
		}
		
		private void moveMapToMyLocation() {
		    LocationManager locationManager = (LocationManager)  appState.getSystemService(Context.LOCATION_SERVICE);
		    Criteria crit = new Criteria();
		    loc = locationManager.getLastKnownLocation(locationManager.getBestProvider(crit, false));
		    loc = locationManager.getLastKnownLocation("gps");
		     if(loc != null){
		    	map.moveCamera(CameraUpdateFactory.newLatLngZoom(new 
		                        LatLng(loc.getLatitude(),loc.getLongitude()), 35)); 
		    	
		    	map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
		    	
		    		
		     }	
		}
		
		 @Override

	     public void onDestroyView() {

	         super.onDestroyView();

	         if (map != null) {

	             getActivity().getSupportFragmentManager().beginTransaction().remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).commit();

	             map = null;

	         }

	     }
			

}
