package com.hackaton.municiclismo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import com.hackaton.municiclismo.data.Activities;
import com.hackaton.municiclismo.data.DBAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;



public class BicicletearFragment extends Fragment{
	
	private MuniApplication appState;
	private DBAdapter db;
	
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);  
	     appState = ((MuniApplication) getActivity().getApplicationContext());   
		 db = appState.getDb();
		 db.open();
	  }
	 
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View rootView = inflater.inflate(R.layout.fragment_bicicletear, container,
	                false);
	        Button iniciarActividad=(Button)rootView.findViewById(R.id.seleccionarDistancia);
			iniciarActividad.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	          		Intent intent = new Intent(appState,GPSActivity.class);
	          		Activities _e=buildFromClass();
	          		intent.putExtra("activity",_e.getId());
	          		db.insertActivity(_e);
	           	 	startActivity(intent);
	            }
	      });
			ListView Listado = (ListView)rootView.findViewById(R.id.datosK);
			ArrayList<String> datos= datosLista();
			ListAdapter adaptador = new ListAdapter(appState, R.layout.simple_layout, datos,"simpleList");
	        Listado.setAdapter(adaptador);  
	        Listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) { 
	            	
	            } 
	            
	 		});	
	    return rootView;
	 }
	 
	 public Activities buildFromClass(){
			Activities _e=new Activities();
			int cuenta=db.getActivities("").getCount();
			cuenta=cuenta+1;
			Calendar today = Calendar.getInstance();
			SimpleDateFormat _format = new SimpleDateFormat("ddMMyyyy HH:mm:ssZ");
			String str_date = _format.format(today.getTime());
			_e.setId(cuenta+"");
			_e.setName("");
			_e.setComment("");
			_e.setBegin(str_date);
			_e.setEnd("");
			_e.setImg("");
			_e.setLink("www.google.com");
			_e.setEffort("low");
			_e.setTime("10:00");
			_e.setTypeActivity("cycling");
			return _e;
		}
		
	 public ArrayList<String> datosLista(){   
      	 ArrayList<String> datos = new ArrayList<String>();
           datos.add("10km");
           datos.add("Ruta");
           datos.add("Notificaciones");
           return datos;
      }

}
