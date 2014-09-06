package com.hackaton.municiclismo;

import com.hackaton.municiclismo.data.DBAdapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DetailActivity  extends ActionBarActivity{
	
	
	private MuniApplication appState;
	private DBAdapter db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_activity);
		appState = ((MuniApplication)getApplicationContext()); 
		db = appState.getDb();
		db.open();
		Bundle extras=getIntent().getExtras();
		String title=(String) extras.get("lugar_title");
		String descrip=(String) extras.get("lugar_desc");
		String lugar_address=(String) extras.get("lugar_address");
		String phonet=(String) extras.get("phone");
		String type=(String) extras.get("type");
		
		String distanceChosed=extras.getString("distancia");
		getSupportActionBar().setTitle(title);
		TextView direccion=(TextView)findViewById(R.id.textView2);
		direccion.setText(lugar_address);
		TextView phone=(TextView)findViewById(R.id.textView22);
		direccion.setText(phonet);
		TextView descript=(TextView)findViewById(R.id.descrip_data);
		direccion.setText(lugar_address);
		Log.d("typeData",type);
		if(type.equals("service")){
			LinearLayout a=(LinearLayout)findViewById(R.id.parkingListView);
			a.setVisibility(View.GONE);
			ImageView tags=(ImageView)findViewById(R.id.icon4);
			tags.setVisibility(View.GONE);
		}
		
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#89e02f")));
	}

}
