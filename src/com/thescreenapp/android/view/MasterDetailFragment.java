package com.thescreenapp.android.view;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thescreenapp.android.R;
import com.thescreenapp.android.view.MasterListFragment.DetailsListener;
import com.thescreenapp.model.ScreenModelObject;

public abstract class MasterDetailFragment extends Fragment implements Serializable, DetailsListener {

	private static final long serialVersionUID = 8045372239266387723L;
	private static final String MASTER_FRAG_TAG = "master_frag_tag";
	private static final String DETAILS_FRAG_TAG = "details_frag_tag";
	private boolean detailsInline = false;
	
	public static interface Callbacks {
		void onSectionAttached(MasterDetailFragment fragment);
	}
		
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View rootView;
    	
    	Bundle bundle = new Bundle();
    	bundle.putSerializable(MasterListFragment.EXTRA_DETAILS_LISTENER, this);
    	
    	Fragment masterFragment = getMasterFragment();
    	masterFragment.setArguments(bundle);
    	
    	Fragment detailFragment = getDetailFragment();
    	
    	// On a tablet (600+ dp width), display master and detail side-by-side.
    	// On a phone (< 600 dp width), display just the master fragment.
    	DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
    	int widthInDp = (int) Math.round((float)displayMetrics.widthPixels / displayMetrics.density);
    	detailsInline = widthInDp >= 600;
    	if ( detailsInline ) {
            rootView = inflater.inflate(R.layout.fragment_master_detail, container, false);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.section_master, masterFragment, MASTER_FRAG_TAG);
            transaction.add(R.id.section_detail, detailFragment, DETAILS_FRAG_TAG);
            transaction.commit();
    	} else {
            rootView = inflater.inflate(R.layout.fragment_master_detail_single, container, false);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.section_master_detail, masterFragment, MASTER_FRAG_TAG);
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
    
    @Override
    public void initDetail(ScreenModelObject detail) {
    	//only load detail if on tablet
    	if (detailsInline) {
			loadDetailInline(detail);
		}
    }

    
    @Override
    public void openDetails(ScreenModelObject detail) {
    	if (detailsInline) {
			loadDetailInline(detail);
		} else {
			Fragment masterFrag = getChildFragmentManager().findFragmentByTag(MASTER_FRAG_TAG);
			
			Bundle bundle = new Bundle();
	    	bundle.putSerializable(DetailsFragment.EXTRA_DETAILS_ID, detail);
	    	
	    	DetailsFragment detailFrag = getNonNullDetailFragment();
			detailFrag.setArguments(bundle);
			
			FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
			//do not want to remove, just move to the background
			transaction.addToBackStack(MASTER_FRAG_TAG);
			transaction.hide(masterFrag);

			transaction.add(R.id.section_master_detail, detailFrag,DETAILS_FRAG_TAG);
			
			transaction.commit();
		}
    }
    
    private void loadDetailInline(ScreenModelObject detail) {
    	DetailsFragment detailFrag = (DetailsFragment) 
    			getChildFragmentManager().findFragmentByTag(DETAILS_FRAG_TAG);
    	detailFrag.loadDetails(detail);
    }

    protected abstract Fragment getMasterFragment();
    protected abstract DetailsFragment getDetailFragment();
    
    private DetailsFragment getNonNullDetailFragment() {
    	DetailsFragment detailFrag = (DetailsFragment) 
    			getChildFragmentManager().findFragmentByTag(DETAILS_FRAG_TAG);
    	if( detailFrag == null ) {
    		detailFrag = getDetailFragment();
    	}
    	return detailFrag;
    }
    
}
