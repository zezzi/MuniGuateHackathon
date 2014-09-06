package com.hackaton.municiclismo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.hackaton.municiclismo.data.DBAdapter;
public class LoginScreen extends ActionBarActivity {
	
	private MuniApplication appState;
	private DBAdapter db;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginscreen);
		appState = ((MuniApplication)getApplicationContext()); 
		db = appState.getDb();
		getSupportActionBar().setTitle("Seleccionar Distancia");
		getSupportActionBar().hide();
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#89e02f")));
		Button login=(Button)findViewById(R.id.loginbutton);
		login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
          		Intent intent = new Intent(appState,MainActivity.class);
           	 	startActivity(intent);
            }
      });
	}

}
