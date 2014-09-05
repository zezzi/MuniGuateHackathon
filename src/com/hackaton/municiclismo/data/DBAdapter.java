package com.hackaton.municiclismo.data;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.hackaton.municiclismo.MuniApplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DBAdapter {
	
	private SQLiteDatabase database;
	private DBHelper dbHelper;
	
	public DBAdapter(Context context) {
		dbHelper = new DBHelper(context);
	}
	
	public void close() {
		try {
			if (database.isOpen()) {
				database.close();
			}
			if (dbHelper != null) {
				dbHelper.close();	
			}
		} catch (Exception e){}
	}
	
	public synchronized void open() throws SQLException {
			try {
				database = dbHelper.getWritableDatabase();
			} catch (SQLiteException ex) {
				try {
					Log.e("error", "DB abierta RO " + ex.getMessage());
					database = dbHelper.getReadableDatabase();				
				} catch (SQLiteException ex1) {
					Log.e("error", "Imposible abrir la base de datos " + ex1.getMessage());
				}
			}			
	}
	
	
	//************** Acciones a la BD para Elementos ********************
	//inserta objetos tipo Elementos a la BD
	public synchronized long insertActivity(Activities _e) {	   		
		ContentValues newElement = buildFromObject(_e);
		
		if (!database.isOpen()) {
			open();
		}		
		long rowId = -1;
		try {
			rowId = database.insert(DBHelper.TABLE_ACTIVITY, null, newElement);
			Log.d("rowId", rowId + "");
		} catch (Exception e){
			Log.e("InsertErrorDBApder", e.toString());
		}
		Log.d("return", rowId + "");
		return rowId;		
	}
	
	
	//modifica elementos en la BD
	public synchronized boolean updateActivity(Activities _e) {
		ContentValues updatedElement = buildFromObject(_e);		
		if (!database.isOpen()) {
			open();
		}		
		boolean result = false;
		try {
			result = database.update(DBHelper.TABLE_ACTIVITY, updatedElement,  "id =" + _e.getId(), null) > 0;
		} catch (Exception e){}
	    return result;
	}
	
	
	//elimina elementos en la BD
	public synchronized boolean removeActivity(Activities _e) {
		return removeActivity(_e.getId());
	}
	
	public synchronized boolean removeActivity(String id) {
		if (!database.isOpen()) {
			this.open();
		}		
		boolean result = false;
		try {		
			result = database.delete(DBHelper.TABLE_ACTIVITY, "id =" + id, null) > 0;
		} catch (Exception e){}

		return result;
	}
	
	//retorna un curosr con elementos a partir de un where	
	public synchronized Cursor getActivities(String whereClause) {
		if (!database.isOpen()) {
			this.open();
		}

		Cursor _data = new MatrixCursor(new String[]{""});
		try {
			_data = database.query(DBHelper.TABLE_ACTIVITY, getKeysFromClass(Activities.class), 
								   whereClause, null, null, null, "name");
		} catch (Exception e) {
			Log.e("ErrorIngetActivity", e.toString());
		}
			
		return _data;
	}
		
	
	//retorna un elemento a partir de un cursor
		public synchronized Activities getActivityFromCursor (Cursor _c, int type, int index) {	 
			Activities e = (Activities)getObjectFromCursor(_c,type,index);
			return e;
		}
	
	
	
				
				
				//************** Acciones a la BD para LatLng ********************
				
				private ContentValues buildFromObject(LatLong _c) {		
				    ContentValues newElement = new ContentValues();
					    newElement.put("id", _c.getId());    
						newElement.put("latitude", _c.getLatitud());
						newElement.put("longitude", _c.getLongitud());				
						newElement.put("activity", _c.getActivity());	
						newElement.put("date_posted", _c.getDatePosted());	
					return newElement;		
				}
				
				
				//inserta objetos tipo Comments a la BD
				public synchronized long insertLatLng(LatLong _c) {	   		
					ContentValues newlatlng = buildFromObject(_c);
					
					if (!database.isOpen()) {
						open();
					}		
					long rowId = -1;
					try {
						rowId = database.insert(DBHelper.TABLE_LATLON, null, newlatlng);
						Log.d("rowId", rowId + "");
					} catch (Exception e){
						Log.e("InsertErrorDBApder", e.toString());
					}
					Log.d("return", rowId + "");
					return rowId;		
				}
				
				//modifica Comments  en la BD
				public synchronized boolean updateLatLng(LatLong _c) {
					ContentValues updatelatlng = buildFromObject(_c);		
					if (!database.isOpen()) {
						open();
					}		
					boolean result = false;
					try {
						result = database.update(DBHelper.TABLE_LATLON, updatelatlng,  "id =" + _c.getId(), null) > 0;
					} catch (Exception e){}
				    return result;
				}
				
				//elimina Comments en la BD
				public synchronized boolean removeLatLng(LatLong _c) {
					return removeLatLng(_c.getId());
				}
				
				public synchronized boolean removeLatLng(String id) {
					if (!database.isOpen()) {
						this.open();
					}		
					boolean result = false;
					try {		
						result = database.delete(DBHelper.TABLE_LATLON, "id =" + id, null) > 0;
					} catch (Exception e){}

					return result;
				}
				
				//retorna un curosr con Phones a partir de un where	
				public synchronized Cursor getLatLng(String whereClause) {
					if (!database.isOpen()) {
						this.open();
					}

					Cursor _data = new MatrixCursor(new String[]{""});
					try {
						_data = database.query(DBHelper.TABLE_LATLON, getKeysFromClass(LatLong.class), 
											   whereClause, null, null, null, null);
					} catch (Exception e) {
						Log.e("ErrorIngetElements", e.toString());
					}
						
					return _data;
				}
					
				//retorna un Phones a partir de un cursor
					public synchronized LatLong getLanLngsFromCursor (Cursor _c, int type, int index) {
						return (LatLong)getObjectFromCursor(_c,type,index);
					}
				
				
					//************** Acciones a la BD para Options ********************
					
					private ContentValues buildFromObjectComments(Options _c) {		
					    ContentValues newElement = new ContentValues();
						    newElement.put("id", _c.getId());    
							newElement.put("name", _c.getName());
							newElement.put("description", _c.getDescription());					
						return newElement;		
					}
					
					
					//inserta objetos tipo Comments a la BD
					public synchronized long insertOptions(Options _c) {	   		
						ContentValues newoptions = buildFromObjectComments(_c);
						if (!database.isOpen()) {
							open();
						}		
						long rowId = -1;
						try {
							rowId = database.insert(DBHelper.TABLE_OPTIONS, null, newoptions);
							Log.d("rowId", rowId + "");
						} catch (Exception e){
							Log.e("InsertErrorDBApder", e.toString());
						}
						Log.d("return", rowId + "");
						return rowId;		
					}
					
					//modifica Comments  en la BD
					public synchronized boolean updateOptions(Options _c) {
						ContentValues updateoptions = buildFromObjectComments(_c);		
						if (!database.isOpen()) {
							open();
						}		
						boolean result = false;
						try {
							result = database.update(DBHelper.TABLE_OPTIONS, updateoptions,  "id =" + _c.getId(), null) > 0;
						} catch (Exception e){}
					    return result;
					}
					
					//elimina Comments en la BD
					public synchronized boolean removeOptions(Options _c) {
						return removeOptions(_c.getId());
					}
					
					public synchronized boolean removeOptions(String id) {
						if (!database.isOpen()) {
							this.open();
						}		
						boolean result = false;
						try {		
							result = database.delete(DBHelper.TABLE_OPTIONS, "id =" + id, null) > 0;
						} catch (Exception e){}

						return result;
					}
					
					//retorna un curosr con Phones a partir de un where	
					public synchronized Cursor getOptions(String whereClause) {
						if (!database.isOpen()) {
							this.open();
						}

						Cursor _data = new MatrixCursor(new String[]{""});
						try {
							_data = database.query(DBHelper.TABLE_OPTIONS, getKeysFromClass(Options.class), 
												   whereClause, null, null, null, null);
						} catch (Exception e) {
							Log.e("ErrorIngetElements", e.toString());
						}
							
						return _data;
					}
						
					//retorna un Phones a partir de un cursor
						public synchronized Options getOptionsFromCursor (Cursor _c, int type, int index) {
							return (Options)getObjectFromCursor(_c,type,index);
						}
					
								
	
	public synchronized void setLastUpdated(Date d) {
		SimpleDateFormat _format = new SimpleDateFormat("ddMMyyyy'T'HH:mm:ssZ");
		String str_date = _format.format(d);		
		ContentValues newElement = new ContentValues();
		newElement.put("last_updated",str_date);
						
		if (!database.isOpen()) {
			open();
		}
						
		Cursor _data = database.query( DBHelper.TABLE_INFO, null, "id = 1", null, null, null, "");
		try {
			if (_data.moveToFirst()) {		
				database.update(DBHelper.TABLE_INFO, newElement,  "id = 1", null);
			} else {
				database.insert(DBHelper.TABLE_INFO, null, newElement);
			}
		} catch (Exception e){}
		_data.close();
	}
	
			
	public synchronized Date getLastUpdated(){
				Date last_updated = null;
				
				if (!database.isOpen()) {
					this.open();
				}				
				Cursor _data = database.query( DBHelper.TABLE_INFO, new String[]{"last_updated"}, "id = 1", null, null, null, "");
				if (_data.moveToFirst()) {
					String str_last_updated = _data.getString(_data.getColumnIndex("last_updated"));
					_data.close();
					SimpleDateFormat _format = new SimpleDateFormat("ddMMyyyy'T'HH:mm:ssZ");
			    	try {
						last_updated = _format.parse(str_last_updated);
					} catch (Exception e) {}
				}
				return last_updated;
	}
	
	//elimina todas las tablas de la BD
	public synchronized void deleteAllElements() {
		if (!database.isOpen()) {
			open();
		}		
		try {
			database.delete(DBHelper.TABLE_ACTIVITY, null, null);
			database.delete(DBHelper.TABLE_LATLON, null, null);
			database.delete(DBHelper.TABLE_OPTIONS, null, null);

		} catch (Exception e) {}
	}
	
	public String[] getKeysFromClass(Class<?> cl) {
		ArrayList<String> keys = new ArrayList<String>();		
		Field[] fl = cl.getDeclaredFields();
		for (Field f : fl) {
			if (  f.getType().equals(String.class)
				||f.getType().equals(Bitmap.class)
				|| f.getType().equals(Integer.class)
				) {
				keys.add(f.getName());				
			}
		}
		/*if (cl == Elemento.class || cl==Place.class) {
			keys.add("tipo");
		}*/
		return keys.toArray(new String[keys.size()]);
	}
	
	public String[] getKeysFromClassCursor(Class<?> cl) {
		ArrayList<String> keys = new ArrayList<String>();		
		Field[] fl = cl.getDeclaredFields();
		for (Field f : fl) {
			if (  f.getType().equals(String.class)
				||f.getType().equals(Bitmap.class)
				|| f.getType().equals(Integer.class)
				) {
				if(keys.equals("id")){
					keys.add("id as _id");
				}else{
					keys.add(f.getName());
				}
			}
		}
		/*if (cl == Elemento.class || cl==Place.class) {
			keys.add("tipo");
		}*/
		return keys.toArray(new String[keys.size()]);
	}
	
	
	private ContentValues buildFromObject(Activities _e) {	
	    ContentValues newElement = new ContentValues();
		    newElement.put("id", _e.getId());    
			newElement.put("name", _e.getName());
			newElement.put("comment", _e.getComment());
			newElement.put("begin", _e.getBegin());
			newElement.put("end", _e.getEnd());
			newElement.put("img_url", _e.getImg());
			newElement.put("link", _e.getLink());
			newElement.put("effort", _e.getEffort());
			newElement.put("time", _e.getTime());
			newElement.put("type_activity", _e.getTypeActivity());
		return newElement;		
	}
	
	//retorna una arreglo de bytes a partir de un bitmap
	public byte[] getArrayfromBitmap(Bitmap value)
	{					
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
		value.compress(CompressFormat.PNG, 0, bos); 
		return bos.toByteArray();	
	}
	
	//retorna un objeto a partir de un cursor
		public synchronized Object getObjectFromCursor (Cursor _c, int type, int index) {
			Object _e = null;
			
			if (_c.moveToPosition(index)) {
				Class<?> cl =  Activities.class;
				
	   			 switch (type) {
				 	case 1:	cl = Activities.class;
		 					_e = new Activities();
				 			break;
				 	case 2: cl = LatLong.class;
				 			_e = new LatLong();
		 					break;
				 	case 3: cl = Options.class;
 							_e = new Options();
							break;
				 }
				
				
				
				Field[] fl = cl.getDeclaredFields();
				for (Field f : fl) {				
					try {
						String _name = f.getName();
						if (f.getType().equals(String.class)) {
							
							String _value = _c.getString(_c.getColumnIndex(_name));
							Method method = (_e.getClass()).getMethod("set" + MuniApplication.toCamelCase(_name), String.class);
							method.invoke(_e,_value);
							
						} else if (f.getType().equals(Integer.class)) {
							Integer _value = _c.getInt(_c.getColumnIndex(_name));
							Method method = (_e.getClass()).getMethod("set" + MuniApplication.toCamelCase(_name), Integer.class);
							method.invoke(_e,_value); 
						} else if (f.getType().equals(Bitmap.class)) {
							try {
								byte[] imgBytes = _c.getBlob(_c.getColumnIndex(_name));
								BitmapFactory.Options options=new BitmapFactory.Options();
								options.inPurgeable = true;
								options.inInputShareable = true;							
								Bitmap _value = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length, options);
								Method method = (_e.getClass()).getMethod("set" + MuniApplication.toCamelCase(_name), Bitmap.class);
								method.invoke(_e,_value);
							} catch (Exception imageIsNull) {}											
						}
					} catch (SecurityException e) {
					} catch (NoSuchMethodException e) {
					} catch (IllegalArgumentException e) {
					} catch (IllegalAccessException e) {
					} catch (IllegalStateException e) {
					} catch (InvocationTargetException e) {						
					}					
				}
			}	
			return _e;
		}

	
	
	


}
