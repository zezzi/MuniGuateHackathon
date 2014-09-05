package com.hackaton.municiclismo.data;

import java.lang.reflect.Field;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;


class DBHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	
	static final String TABLE_LATLON = "latlong";
	static final String TABLE_ACTIVITY = "activity";
	static final String TABLE_OPTIONS="options";
	static final String TABLE_INFO = "info";
	private static final String DATABASE_NAME = "muniguatedb.db";	
				
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public DBHelper(Context context, int version) {
		super(context, DATABASE_NAME, null, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		
		String activities_sql = "";
		String latlng_sql = "";	
		String options_sql="";
		Class<?> activities_class = Activities.class;
		Class<?> latlng_class = LatLong.class;
		Class<?> options_class = Options.class;
		
		
		activities_sql = getSqlQueryText(activities_class);
		latlng_sql = getSqlQueryText(latlng_class);
		options_sql = getSqlQueryText(options_class);
		
				
		activities_sql = "CREATE TABLE " + TABLE_ACTIVITY + " (" + activities_sql.substring(0, activities_sql.length()-1) + ")";
		latlng_sql = "CREATE TABLE " + TABLE_LATLON + " (" + latlng_sql.substring(0, latlng_sql.length()-1) + ")";
		options_sql = "CREATE TABLE " + TABLE_OPTIONS + " (" + options_sql.substring(0, options_sql.length()-1) + ")";
		String info_sql = "CREATE TABLE " + TABLE_INFO + " (id integer primary key, last_updated text, check_count integer)"; 
		
		database.execSQL(activities_sql);
		database.execSQL(latlng_sql);
		database.execSQL(options_sql);	
		
		Log.d("se han creado las tablas", "se han creado las tablas");
	}

	
	public String getSqlQueryText( Class<?> clase)
	{
		String query="";
		Field[] fl = clase.getDeclaredFields();
		for (Field f : fl) {
			try {
				String _name = f.getName();
				if (f.getType().equals(Bitmap.class)) {
					query += _name + " blob,";
				} else if (f.getType().equals(Integer.class)) {
					query += _name + " integer,";
				} else if (f.getType().equals(String.class)) {
					if (_name.equals("id")) {
						query += "id integer primary key, type integer,";	
					} else {
						query += _name + " text,";	
					}						
				}
			} catch (SecurityException e) {
			} catch (IllegalArgumentException e) {						
			}
		}
		
		return query;
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DBHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");			
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LATLON);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPTIONS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);
		onCreate(db);
	}

}