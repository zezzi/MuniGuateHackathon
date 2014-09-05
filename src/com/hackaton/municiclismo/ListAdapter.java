package com.hackaton.municiclismo;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class ListAdapter extends ArrayAdapter
{

    Context c;
    int MytextView;
    ArrayList<String> l;
    String type;

    public ListAdapter(Context context, int textViewResourceId, ArrayList<String> list, String type) {
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
        TextView t = (TextView)v.findViewById(R.id.textoList);
        ImageView tt=(ImageView)v.findViewById(R.id.imageList);
       
        if(type.equals("simpleList")){
        	if(l.get(position).equals("Ruta")){
        		tt.setImageResource(R.drawable.routeshort);
        	}else if(l.get(position).equals("Notificaciones")){
        		tt.setImageResource(R.drawable.alarm);
        
        	}else{
        		tt.setImageResource(R.drawable.routebike);
        	}
        }else if(type.equals("distanceList")){
        	tt.setImageResource(R.drawable.biki);
        }
        t.setText(l.get(position));
        return v;
    }
}
