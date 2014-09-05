package com.hackaton.municiclismo.util;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

public class Utilities {
	private Handler gpsHandler;
	private Context ctx;
	private Location mLocation;
	private LocationManager mLocationManagerNTW;
	private LocationListener mLocationListenerNTW;
	private LocationManager mLocationManagerGPS;
	private LocationListener mLocationListenerGPS;
	private NotificationManager mNotificationManager;
	private int frequency;
	private int iGpsStatus;
	private int mSignalStrength;
	private int satelliteCount;
	private float speed;
	private float heading;
	private String gpsStatus;
	private String gpsAge;
	private int iLocationStatus;
	//Status de red de coneccion de Datos
	public static final int NETWORK_STATUS_WIFI = 1;
	public static final int NETWORK_STATUS_MOBILE = 2;
	public static final int NETWORK_STATUS_NOTHING = 3;
	//Status de GPS
	public static final int LOCATION_STATUS_NETWORK = 1;
	public static final int LOCATION_STATUS_GPS = 2;
	public static final int LOCATION_STATUS_NOTHING=3;
	
	public Utilities( Context context, int frequency, Handler handler) {
		this.ctx = context;
		this.frequency = frequency;
		
        mSignalStrength = 0;
		satelliteCount = 0;
		speed=0;
		heading = 0;
		gpsStatus = "";
		gpsAge = "OLD";
		iGpsStatus = LocationProvider.TEMPORARILY_UNAVAILABLE;
		mLocationManagerGPS = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
		mLocationManagerNTW = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
		iLocationStatus = GPSStatus();
		gpsHandler = handler;
		mLocationListenerGPS = new LocationListener() {
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				if (location!=null) {
					if (mLocation != null) {
						heading = mLocation.bearingTo(location);
					}
				}
				mLocation = location;
				speed = location.getSpeed();
				
				//Log.e("LOCVER", "verificando por GPS la posicion " + mLocation.getLongitude() + "," + mLocation.getLatitude());
				//Log.e("LIB", "onLocationChangeGPS");
			}

			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				iLocationStatus = GPSStatus();
				gpsHandler.sendEmptyMessage(Constants.MUNI_GPSOFF);
				//Log.e("LIB", "onProviderDisabledGPS");
			}

			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				iLocationStatus = GPSStatus();
				gpsHandler.sendEmptyMessage(Constants.MUNI_GPSON);
				//Log.e("LIB", "onProviderEnabledGPS");
			}


			@Override
			public void onStatusChanged(String arg0, int status, Bundle arg2) {
				// TODO Auto-generated method stub
				iGpsStatus = status;
				
			}
		};
		mLocationListenerNTW = new LocationListener() {
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				if ((iLocationStatus == LOCATION_STATUS_NETWORK) || ((iLocationStatus == LOCATION_STATUS_GPS) && (mLocation== null))) {
					if (location!=null) {
						if (mLocation != null) {
							heading = mLocation.bearingTo(location);
						}
					}
					mLocation = location;
					speed = location.getSpeed();
					//Evaluar Geofences
					//Log.e("MUNI", "verificando por RED la posicion " + mLocation.getLongitude() + "," + mLocation.getLatitude());
				}
			}

			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				iLocationStatus = GPSStatus();
				//Log.e("LIB", "onProviderDisabledNTW");
			}

			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				iLocationStatus = GPSStatus();
					//Log.e("LIB", "onProviderEnabledNTW");
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				//Log.e("LIB", "onProviderChangedNTW");
				// TODO Auto-generated method stub
			}
		};
		mLocationManagerGPS.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0,mLocationListenerGPS);	
		mLocationManagerNTW.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,frequency,0,mLocationListenerNTW);
		mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
	}
	
	//Obtiene Tipo de Conexion
	public int hasConnection() {
		int networkStatus = NETWORK_STATUS_NOTHING;
	    ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(
	        Context.CONNECTIVITY_SERVICE);

	    NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    if (wifiNetwork != null) { 
	    		if (wifiNetwork.isConnected()) {
	    			networkStatus = NETWORK_STATUS_WIFI;
	    		}
	    }

	    NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    if (mobileNetwork != null){
	    	if (mobileNetwork.isConnected()) {
	    		networkStatus = NETWORK_STATUS_MOBILE;
	    	}
	    }

	    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	    if (activeNetwork != null){
	    	if (activeNetwork.isConnected()) {
	    		return networkStatus;	
	    	}else { networkStatus = NETWORK_STATUS_NOTHING; }
	    }
	    return networkStatus;
	  }
	
	//Obtiene posicion actual
	public Location getPosition() {
				if (mLocation== null) {
					mLocation = mLocationManagerGPS.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					if (mLocation == null) {
						mLocation = mLocationManagerNTW.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					}
				}
				return mLocation;
	}
	
	//Obtiene el estatus del GPS
	public int GPSStatus() {
		int locationStatus = LOCATION_STATUS_NOTHING;
		if(mLocationManagerGPS.isProviderEnabled(LocationManager.GPS_PROVIDER )){
			locationStatus = LOCATION_STATUS_GPS;
		}
		else if(mLocationManagerGPS.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			locationStatus = LOCATION_STATUS_NETWORK;
		}
		return locationStatus;
	}

}
