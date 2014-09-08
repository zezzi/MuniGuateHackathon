package com.hackaton.municiclismo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import java.util.ArrayList;
import java.util.HashMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CustomizedListView extends Fragment {
    // All static variables
    static final String URL = "http://api.androidhive.info/music/music.xml";
    // XML node keys
    static final String KEY_SONG = "song"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_COINS = "coins";
    static final String KEY_LIST_IMAGE = "list_image";

    ListView list;
    PriceAdapter adapter;
    private MuniApplication appState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View v = inflater.inflate(R.layout.premios_fragment, container, false);

        ArrayList<HashMap<String, String>> pricesList = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(KEY_ID, "1");
        map.put(KEY_TITLE, "Bicimania");
        map.put(KEY_DESCRIPTION, "15% Descuento");
        map.put(KEY_COINS, "C.1200");
        map.put(KEY_LIST_IMAGE,"");
        pricesList.add(map);
        
        map.put(KEY_ID, "2");
        map.put(KEY_TITLE, "Premio 2");
        map.put(KEY_DESCRIPTION, "Descuento 2");
        map.put(KEY_COINS, "C.1100");
        map.put(KEY_LIST_IMAGE,"");
        pricesList.add(map);
        
        map.put(KEY_ID, "3");
        map.put(KEY_TITLE, "Bicimania");
        map.put(KEY_DESCRIPTION, "15% Descuento");
        map.put(KEY_COINS, "C.1500");
        map.put(KEY_LIST_IMAGE,"");
        pricesList.add(map);

        list=(ListView)v.findViewById(R.id.list);

        // Getting adapter by passing xml data ArrayList
        adapter = new PriceAdapter(appState,pricesList);
        list.setAdapter(adapter);

        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view,
                                    int position, long id) {

            }
        });
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appState = ((MuniApplication) getActivity().getApplicationContext());
    }
}
