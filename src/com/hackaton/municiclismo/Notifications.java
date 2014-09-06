package com.hackaton.municiclismo;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hackaton.municiclismo.data.DBAdapter;

public class Notifications extends ActionBarActivity {
	
	private MuniApplication appState;
	private DBAdapter db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distance_activity);
		appState = ((MuniApplication)getApplicationContext()); 
		db = appState.getDb();
		db.open();
		getSupportActionBar().setTitle("Notificaciones");
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#89e02f")));
		ListView Listado = (ListView)findViewById(R.id.distanciasListado);
		ArrayList<String> datos= datosLista();
		ListAdapter adaptador = new ListAdapter(appState, R.layout.simple_layout, datos,"notificationList");
        Listado.setAdapter(adaptador);  
        Listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) { 
            	Intent pantallaInicial=new Intent(appState,MainActivity.class);
            	String distanciaSeleccionada= (String) parent.getItemAtPosition(position);
            	pantallaInicial.putExtra("notification",distanciaSeleccionada);
            	Log.d("notification",distanciaSeleccionada);
            	//finish();
            	startActivity(pantallaInicial);
            	
            } 
            
 		});	
		
	}
	
	
	public ArrayList<String> datosLista(){   
     	 ArrayList<String> datos = new ArrayList<String>();
         datos.add("Si");
         datos.add("No");
          return datos;
     }

	

}