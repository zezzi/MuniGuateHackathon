package com.hackaton.municiclismo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.hackaton.municiclismo.data.Activities;
import com.hackaton.municiclismo.data.DBAdapter;

public class PerfilFragment extends Fragment{
	
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
	        View rootView = inflater.inflate(R.layout.perfil_fragment, container,
	                false);
	        ImageButton botondePerfil=(ImageButton)rootView.findViewById(R.id.perfilImage);
	        botondePerfil.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	          		Intent intent = new Intent(appState,SeleccionarPerfil.class);
	           	 	startActivity(intent);
	            }
	      });
	    return rootView;
	 }

}