package com.hackaton.municiclismo;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.hackaton.municiclismo.data.Activities;
import com.hackaton.municiclismo.data.DBAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.Tab;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	
	private MuniApplication appState;
	private DBAdapter db;
	private ViewPager mPager;	
	ActionBar mActionbar;	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		getSupportActionBar().setTitle("");
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#89e02f")));
		/** Getting a reference to action bar of this activity */
		mActionbar = getSupportActionBar();		
		
		/** Set tab navigation mode */
        mActionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        /** Getting a reference to ViewPager from the layout */
        mPager = (ViewPager) findViewById(R.id.pager);

        /** Getting a reference to FragmentManager */
        FragmentManager fm = getSupportFragmentManager();

        /** Defining a listener for pageChange */
        ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener(){
                @Override
                public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        mActionbar.setSelectedNavigationItem(position);
                }

        };
        
        /** Setting the pageChange listener to the viewPager */
        mPager.setOnPageChangeListener(pageChangeListener);

        /** Creating an instance of FragmentPagerAdapter */
        MyFragmentPageAdapterMuni fragmentPagerAdapter = new MyFragmentPageAdapterMuni(fm);

        /** Setting the FragmentPagerAdapter object to the viewPager object */
        mPager.setAdapter(fragmentPagerAdapter);

        mActionbar.setDisplayShowTitleEnabled(true);
        
        /** Defining tab listener */
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {

                        @Override
                        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
                        }

                        @Override
                        public void onTabSelected(Tab tab, FragmentTransaction ft) {
                                mPager.setCurrentItem(tab.getPosition());

                        }

                        @Override
                        public void onTabReselected(Tab tab, FragmentTransaction ft) {
                        }
                };
                
                /** Creating fragment1 Tab */
                Tab tab = mActionbar.newTab()
                                   .setText("Bicicletear")                                   
                                   .setTabListener(tabListener);

                mActionbar.addTab(tab);

                /** Creating fragment2 Tab */
                tab = mActionbar.newTab()
                               .setText("Perfil")                               
                               .setTabListener(tabListener);

                mActionbar.addTab(tab);        
                
                /** Creating fragment3 Tab */
                tab = mActionbar.newTab()
                               .setText("MuniPremios")                               
                               .setTabListener(tabListener);

                mActionbar.addTab(tab);  
                
                
                /** Creating fragment3 Tab */
                tab = mActionbar.newTab()
                               .setText("Ranking")                               
                               .setTabListener(tabListener);

                mActionbar.addTab(tab);  
                
                tab = mActionbar.newTab()
                        .setText("Servicios")                               
                        .setTabListener(tabListener);

                mActionbar.addTab(tab);
                
                
              
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public Activities buildFromClass(){
		Activities _e=new Activities();
		int cuenta=db.getActivities("").getCount();
		cuenta=cuenta+1;
		Calendar today = Calendar.getInstance();
		SimpleDateFormat _format = new SimpleDateFormat("ddMMyyyy HH:mm:ssZ");
		String str_date = _format.format(today.getTime());
		_e.setId(cuenta+"");
		_e.setName("");
		_e.setComment("");
		_e.setBegin(str_date);
		_e.setEnd("");
		_e.setImg("");
		_e.setLink("www.google.com");
		_e.setEffort("low");
		_e.setTime("10:00");
		_e.setTypeActivity("cycling");
		return _e;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
