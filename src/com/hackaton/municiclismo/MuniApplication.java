package com.hackaton.municiclismo;






import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import com.hackaton.municiclismo.data.DBAdapter;
import com.hackaton.municiclismo.util.Utilities;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

public class MuniApplication extends Application {
	
	private boolean checkingForData;
	//private JSONReader reader;
	private String domain;
	private SharedPreferences p;
	private SharedPreferences.Editor edit;
	private Context ctx;
	private DBAdapter db;
	Utilities mUtilities;
	
	
	private Handler gpsHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
		}
		
	};
	
	public void onCreate(){
        super.onCreate();       
        setDB();
        setJSONReader();   
        setDomain(getString(R.string.domain));
        p = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mUtilities = new Utilities(getApplicationContext(), 5000,gpsHandler);
		ctx = this;
		edit = p.edit();
        //VolleyHelper.init(getApplicationContext()); 	
        
	}
	
	
	public void setDB() {
		if (db == null) {
			db = new DBAdapter(getApplicationContext());
			db.open();
			db.close();
			exportDatabse("muniguatedb.db");
		}
	}
	
	public DBAdapter getDb(){
		if (this.db == null) {
			this.db = new DBAdapter(getApplicationContext());
			
		}		
		return this.db;
	}
	
	public void setJSONReader(){
		/*if (reader == null) {
			reader = new JSONReader(getApplicationContext());
		}*/
	}
	
	//public JSONReader getReader(){
		//return this.reader;
	//}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getDomain(){
		return domain;
	}
	public Utilities getUtilities() {
		return mUtilities;
	}
	
	public boolean connectionAvailable() {
		boolean connection = true;
		NetworkInfo info = (NetworkInfo) ((ConnectivityManager) 				
				getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();	    		
	    if (info == null || !info.isConnected() ) {
	    	connection = false;
	    } else if (info.isRoaming()){
	    	connection = false;
	    }
	    return connection;
	} 
	
	public void exportDatabse(String databaseName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//"+getPackageName()+"//databases//"+databaseName+"";
                String backupDBPath = "backupmuni.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {

        }
    }
	
	public static String toCamelCase(String s) {
		String[] parts = s.split("_");
		String camelCaseString = "";
		if (parts.length > 1) {
			
		   for (String part : parts) {
			
			   if (part.length() > 1)
				   
				   camelCaseString = camelCaseString + toProperCase(part);
			   else
				   camelCaseString = camelCaseString + part.toUpperCase();
		   }
		} else {
		   camelCaseString = toProperCase(s);
		}

		return camelCaseString;
	}
	
	public static String toProperCase(String s) {
	    return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}
	

}
