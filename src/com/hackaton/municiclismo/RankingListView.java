package com.hackaton.municiclismo;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class RankingListView extends Fragment {
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
    RankingAdapter adapter;
    private MuniApplication appState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View v = inflater.inflate(R.layout.ranking_fragment, container, false);

        ArrayList<HashMap<String, String>> rankingList = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(KEY_ID, "1");
        map.put(KEY_TITLE, "Xibalba");
        map.put(KEY_DESCRIPTION, "Juan Perez");
        map.put(KEY_COINS, "XP: 3000");
        map.put(KEY_LIST_IMAGE,"");
        rankingList.add(map);
        
        map.put(KEY_ID, "2");
        map.put(KEY_TITLE, "Chapin");
        map.put(KEY_DESCRIPTION, "Renata Morales");
        map.put(KEY_COINS, "XP: 2400");
        map.put(KEY_LIST_IMAGE,"");
        rankingList.add(map);
        
        map.put(KEY_ID, "3");
        map.put(KEY_TITLE, "Maya Tour");
        map.put(KEY_DESCRIPTION, "Luis Gonzalez");
        map.put(KEY_COINS, "XP: 1000");
        map.put(KEY_LIST_IMAGE,"");
        rankingList.add(map);

        list=(ListView)v.findViewById(R.id.list);

        // Getting adapter by passing xml data ArrayList
        adapter = new RankingAdapter(appState,rankingList);
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