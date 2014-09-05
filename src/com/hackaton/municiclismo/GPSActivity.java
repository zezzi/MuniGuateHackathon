package com.hackaton.municiclismo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.hackaton.municiclismo.data.DBAdapter;
import com.hackaton.municiclismo.util.Utilities;
import com.hackaton.municiclismo.data.LatLong;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GPSActivity extends ActionBarActivity{
	
	private MuniApplication appState;
	Utilities mUtilities;
	private static Timer timer;
	private TextView cronometro;
	TextView latitude;
	TextView longitude;
	TextView paceTextChange;
	TextView avgPace;
	private GoogleMap  map;
	Location locd ;
	int i=0;
	private Button stopButton;
	private Button pauseButton;
	
	private long startTime = 0L;
	private Handler myHandler = new Handler();
	long timeInMillies = 0L;
	long timeSwap = 0L;
	long finalTime = 0L;
	private DBAdapter db;
	int ActivityId=0;
	double distanceTotal;
	double prevLat=0;
	double prevLon=0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tracking_request);
		distanceTotal=0;
		appState = (MuniApplication)getApplicationContext();
		mUtilities = ((MuniApplication) getApplicationContext()).getUtilities();
		db = appState.getDb();
		Bundle extras = getIntent().getExtras();
		ActivityId = Integer.parseInt(extras.getString("activity"));
     
		//Coloco mi posicion Actual
		Location l = mUtilities.getPosition();
		latitude=(TextView)findViewById(R.id.latitude);
		longitude=(TextView)findViewById(R.id.logintud);
		latitude.setText(l.getLatitude()+"");
		longitude.setText(l.getLongitude()+"");
		prevLat=l.getLatitude();
		prevLon=l.getLongitude();
		timer = new Timer("alertTimer",true);
		
		//Inicializo el timer que ingresara las coordenandas cada 5 segundos
		timer.scheduleAtFixedRate(new PeriodicAction(), 0, 3000);
		
		//Boton de Pausa falta reinicializar y cambiar el texto a start cuando pausa
		cronometro = (TextView) findViewById(R.id.cronometro);
		avgPace = (TextView) findViewById(R.id.avgPace);
		pauseButton = (Button) findViewById(R.id.pauseTimer);
		pauseButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if(pauseButton.getText().equals("Pause")){
					timeSwap += timeInMillies;
					myHandler.removeCallbacks(updateTimerMethod);
					pauseButton.setText("Start");
				}else if(pauseButton.getText().equals("Start")){
					startTime = SystemClock.uptimeMillis();
					myHandler.postDelayed(updateTimerMethod, 0);
					pauseButton.setText("Pause");
				}

			}
		});
		
		
		//Debe parar el cronometro y preguntar si quiere terminar la ruta
		stopButton = (Button) findViewById(R.id.stopTimer);
		
		stopButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				timeSwap += timeInMillies;
				myHandler.removeCallbacks(updateTimerMethod);
				AlertDialog.Builder builder = new AlertDialog.Builder(GPSActivity.this);
				builder.setTitle("Finalizar");  
				builder.setMessage("Esta seguro que desea finzaliar la actividad"); 
				// Add the buttons
				builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				               // User clicked OK button
				        	   Intent finishActivity=new Intent(appState,FinishActivity.class);
								finishActivity.putExtra("idActivity", ActivityId);
								finish();
								startActivity(finishActivity);
				           }
				       });
				builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				               // User cancelled the dialog
				        	   startTime = SystemClock.uptimeMillis();
								myHandler.postDelayed(updateTimerMethod, 0);
				           }
				       });
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
		startTime = SystemClock.uptimeMillis();
		myHandler.postDelayed(updateTimerMethod, 0);
		
		
	}
	
	private void moveMapToMyLocation() {
	    LocationManager locationManager = (LocationManager)  this.getSystemService(Context.LOCATION_SERVICE);
	    Criteria crit = new Criteria();
	    locd = locationManager.getLastKnownLocation(locationManager.getBestProvider(crit, false));
	    locd = locationManager.getLastKnownLocation("gps");
	     if(locd != null){
	    	map.moveCamera(CameraUpdateFactory.newLatLngZoom(new 
	                        LatLng(locd.getLatitude(),locd.getLongitude()), 35)); 	
	    	map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	     }	
	    }
	
	private class PeriodicAction extends TimerTask {
		@Override
		public void run() {
			Log.d("FTRANS", "transmitio " + (new Date()).toString());
			try{
				saveActualLocationInDB();
            }catch(Exception ex){
                ex.printStackTrace();
            }
		}
	}
	
	private void saveActualLocationInDB() {
			final Location location = mUtilities.getPosition();
			Log.d("WTM","Latitude "+location.getLatitude()+" Longitud "+location.getLongitude());
			LatLong _c=new LatLong();
			int cuenta=db.getLatLng("").getCount();
			cuenta=cuenta+1;
			Calendar today = Calendar.getInstance();
			SimpleDateFormat _format = new SimpleDateFormat("ddMMyyyy'T'HH:mm:ss.SSZ");
			String str_date = _format.format(today.getTime());
			_c.setId(cuenta+"");
			_c.setDatePosted(str_date);
			_c.setLatitud(location.getLatitude()+"");
			_c.setLongitud(location.getLongitude()+"");
			_c.setActivity(ActivityId);
			db.insertLatLng(_c);
			if(prevLat!=0 && prevLon!=0){
				Location loc1 = new Location("");
				loc1.setLatitude(prevLat);
				loc1.setLongitude(prevLon);

				Location loc2 = new Location("");
				loc2.setLatitude(location.getLatitude());
				loc2.setLongitude(location.getLongitude());

				float distanceInMeters = loc1.distanceTo(loc2);
				/*double distanceInMeters=distance(prevLat,prevLon,location.getLatitude(),location.getLongitude());
				*/
				distanceTotal=distanceTotal+distanceInMeters;
				Log.d("Distancia",distanceTotal+"");
				
			}
			runOnUiThread(new Runnable() {
			    public void run() {
			    	latitude=(TextView)findViewById(R.id.latitude);
					longitude=(TextView)findViewById(R.id.logintud);
					latitude.setText(location.getLatitude()+" "+i);
					longitude.setText(location.getLongitude()+" "+i);
					
					paceTextChange=(TextView)findViewById(R.id.paceTextChange);
					avgPace=(TextView)findViewById(R.id.avgPace);
					paceTextChange.setText(String.format("%.2f", distanceTotal) +"");
					int seconds = (int) (finalTime / 1000);
					int minutes = seconds / 60;
					//double avgPaceCal=((minutes+seconds)/distanceTotal)/60;
					//avgPace.setText(String.format("%.2f", avgPaceCal)+"");
					prevLat=location.getLatitude();
					prevLon=location.getLongitude();
					i++;
			    }
			});	
	}
	
	private double distance(double lat1, double lon1, double lat2, double lon2) {
	      double theta = lon1 - lon2;
	      double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
	      dist = Math.acos(dist);
	      dist = rad2deg(dist);
	      dist = dist * 60 * 1.1515;
	       return (dist);
	    }

	   private double deg2rad(double deg) {
	      return (deg * Math.PI / 180.0);
	    }
	   private double rad2deg(double rad) {
	      return (rad * 180.0 / Math.PI);
	    }
	
	private Runnable updateTimerMethod = new Runnable() {
		public void run() {
			timeInMillies = SystemClock.uptimeMillis() - startTime;
			finalTime = timeSwap + timeInMillies;
			int seconds = (int) (finalTime / 1000);
			int minutes = seconds / 60;
			seconds = seconds % 60;
			int milliseconds = (int) (finalTime % 1000);
			cronometro.setText("" + minutes + ":"+ String.format("%02d", seconds) ); //+ ":"+ String.format("%02d", milliseconds));
			myHandler.postDelayed(this, 0);
		}

	};
	
	
	//Va en ON CREATE si quiero mapa
	/*map =((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
    map.setMyLocationEnabled(true);
    if (map!=null){
    	Log.d("entra ", "entra mapa");
    
    		moveMapToMyLocation();
    	map.setMapType(GoogleMap.MAP_TYPE_NORMAL);       	
    }*/
	/*Button iniciarActividad=(Button)findViewById(R.id.startActivityButton);
	iniciarActividad.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
      	  
      		Intent intent = new Intent(appState,GPSActivity.class);
       	 	startActivity(intent);
        }
  });*/
	
	
}
