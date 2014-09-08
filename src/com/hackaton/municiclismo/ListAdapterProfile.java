package com.hackaton.municiclismo;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapterProfile extends ArrayAdapter {

	    Context c;
	    int MytextView;
	    ArrayList<String> l;
	    ArrayList<String> descrip=new ArrayList<String>() {{
	        add("Dícese del que maneja con orgullo su brujula por la ciudad");
	        add("El Casco del pueblo. No, en serio ¿Has visto lo que usan los ciclistas en los pueblos?");
	        add("La bici para llegar del punto A al punto B cómoda y accesible");
	        add("Dícese del que maneja con orgullo su brujula por la ciudad");
	        add("El Casco del pueblo. No, en serio ¿Has visto lo que usan los ciclistas en los pueblos?");
	        add("Dícese del que maneja con orgullo su brujula por la ciudad");
	    }};
	    String type;

	    public ListAdapterProfile(Context context, int textViewResourceId, ArrayList<String> list, String type) {
	        super(context, textViewResourceId, list);
	        // TODO Auto-generated constructor stub
	        this.c = context;
	        this.MytextView = textViewResourceId;
	        this.l = list;
	        this.type = type;
	    }
	    
	    @Override
	    public View  getView(int position, View convertView, ViewGroup parent)
	    {
	        View v;
	        LayoutInflater inflator = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        v  = inflator.inflate(MytextView, null, true);
	        TextView t = (TextView)v.findViewById(R.id.titleProfile);
	        TextView desc = (TextView)v.findViewById(R.id.DescripProfile);
	        ImageView tt=(ImageView)v.findViewById(R.id.profileImagePP);
	        switch(position){
	        	case 0:
	        		tt.setImageResource(R.drawable.profile3);
	        		break;
	        	case 1:
	        		tt.setImageResource(R.drawable.profile2);
	        		break;
	        	case 2:
	        		tt.setImageResource(R.drawable.profile);
	        		break;
	        	case 3:
	        		tt.setImageResource(R.drawable.profile4);
	        		break;
	        	case 4:
	        		tt.setImageResource(R.drawable.profile5);
	        		break;
	        	case 5:
	        		tt.setImageResource(R.drawable.profile6);
	        		break;
	        	case 6:
	        		tt.setImageResource(R.drawable.profile);
	        		break;
	        }
	        
	        t.setText(l.get(position));
	        desc.setText(descrip.get(position));
	        
	        return v;
	    }
	}