package com.hackaton.municiclismo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackaton.municiclismo.data.DBAdapter;

public class ParqueosFragment extends Fragment{
	
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
	        View rootView = inflater.inflate(R.layout.parqueos_fragment, container,
	                false);
	    return rootView;
	 }

}
