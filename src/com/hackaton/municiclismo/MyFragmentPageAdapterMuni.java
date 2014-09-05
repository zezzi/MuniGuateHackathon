package com.hackaton.municiclismo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hackaton.municiclismo.BicicletearFragment;
import com.hackaton.municiclismo.PerfilFragment;
import com.hackaton.municiclismo.PremiosFragment;
import com.hackaton.municiclismo.RankingFragment;
import com.hackaton.municiclismo.ParqueosFragment;

public class MyFragmentPageAdapterMuni extends FragmentPagerAdapter{
	
	final int PAGE_COUNT = 5;
	
	/** Constructor of the class */
	public MyFragmentPageAdapterMuni(FragmentManager fm) {
		super(fm);
		
	}

	/** This method will be invoked when a page is requested to create */
	@Override
	public Fragment getItem(int arg0) {
		Bundle data = new Bundle();
		switch(arg0){
		
			/** tab1 is selected */
			case 0:
				BicicletearFragment fragment1 = new BicicletearFragment();				
				return fragment1;
				
			/** tab2 is selected */
			case 1:
				PerfilFragment fragment2 = new PerfilFragment();
				return fragment2;	
				
			/** tab1 is selected */
			case 2:
				PremiosFragment fragment3 = new PremiosFragment();				
				return fragment3;
				
			/** tab2 is selected */
			case 3:
				RankingFragment fragment4 = new RankingFragment();
				return fragment4;
				
			case 4:
				ParqueosFragment fragment5 = new ParqueosFragment();
				return fragment5;	
		}
		
		return null;
	}

	/** Returns the number of pages */
	@Override
	public int getCount() {		
		return PAGE_COUNT;
	}
}