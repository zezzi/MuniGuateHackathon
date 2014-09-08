package com.hackaton.municiclismo;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Created by 30142444 on 9/6/2014.
 */
public class PriceAdapter extends BaseAdapter{
    private Context activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;

    public PriceAdapter(Context a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView artist = (TextView)vi.findViewById(R.id.description); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.coins); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        // Setting all values in listview
      /*  title.setText(song.get(CustomizedListView.KEY_TITLE));
        artist.setText(song.get(CustomizedListView.KEY_DESCRIPTION));
        duration.setText(song.get(CustomizedListView.KEY_COINS));
        thumb_image.setImageResource(R.drawable.biki);*/
        
        title.setText(song.get("title"));
        artist.setText(song.get("description"));
        duration.setText(song.get("coins"));

        return vi;
    }
}

