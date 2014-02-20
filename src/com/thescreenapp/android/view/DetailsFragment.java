package com.thescreenapp.android.view;

import com.thescreenapp.model.ScreenModelObject;

import android.support.v4.app.Fragment;

public abstract class DetailsFragment extends Fragment {
	
	public final static String EXTRA_DETAILS_ID = "com.thescreenapp.android.view.EXTRA_DETAILS_ID";
	
	public abstract void loadDetails(ScreenModelObject detail);
}
