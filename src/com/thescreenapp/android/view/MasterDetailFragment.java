package com.thescreenapp.android.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thescreenapp.android.R;

public abstract class MasterDetailFragment extends Fragment {
	
	public static interface Callbacks {
		void onSectionAttached(MasterDetailFragment fragment);
	}
	
	public MasterDetailFragment() {
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View rootView;
    	
    	// On a tablet (600+ dp width), display master and detail side-by-side.
    	// On a phone (< 600 dp width), display just the master fragment.
    	
    	DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
    	int widthInDp = (int) Math.round((float)displayMetrics.widthPixels / displayMetrics.density);
    	if (widthInDp >= 600) {
            rootView = inflater.inflate(R.layout.fragment_master_detail, container, false);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.section_master, getMasterFragment());
            transaction.add(R.id.section_detail, getDetailFragment());
            transaction.commit();
    	} else {
            rootView = inflater.inflate(R.layout.fragment_master_detail_single, container, false);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.section_master_detail, getMasterFragment());
            transaction.commit();
    	}
    	
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof Callbacks) {
        	((Callbacks)activity).onSectionAttached(this);
        }
    }
    
    protected abstract Fragment getMasterFragment();
    protected abstract Fragment getDetailFragment();
}
