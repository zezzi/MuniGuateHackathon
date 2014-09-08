package com.hackaton.municiclismo;

import java.util.ArrayList;

import com.hackaton.municiclismo.data.DBAdapter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class SeleccionarPerfil extends ActionBarActivity {
		
		private MuniApplication appState;
		private DBAdapter db;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.seleccionar_perfil);
			appState = ((MuniApplication)getApplicationContext()); 
			db = appState.getDb();
			db.open();
			getSupportActionBar().setTitle("Perfiles Disponibles");
			getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#89e02f")));
			ListView Listado = (ListView)findViewById(R.id.perfilListado);
			ArrayList<String> datos= datosLista();
			ListAdapterProfile adaptador = new ListAdapterProfile(appState, R.layout.profile_single_text, datos,"profileList");
	        Listado.setAdapter(adaptador);  
	        Listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
	            	//Deberia inicializar una actividad no solo darle finish;
	            	//Intent pantallaInicial=new Intent(appState,MainActivity.class);
	            	//String distanciaSeleccionada= (String) parent.getItemAtPosition(position);
	            	//pantallaInicial.putExtra("ruta",distanciaSeleccionada);
	            	//Log.d("ruta",distanciaSeleccionada);
	            	finish();
	            	//startActivity(pantallaInicial);
	            	
	            } 
	            
	 		});	
			
		}
		
		
		public ArrayList<String> datosLista(){   
	     	 ArrayList<String> datos = new ArrayList<String>();
	     	  datos.add("Chapin");
	          datos.add("El Sombreron");
	          datos.add("Maya Tour");
	          datos.add("La llorona");
	          datos.add("Xibalba");
	          datos.add("Quetzalcoatl");
	          return datos;
	     }

}
