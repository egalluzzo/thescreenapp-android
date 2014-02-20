package com.thescreenapp.android.view.candidate;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thescreenapp.android.R;
import com.thescreenapp.android.view.DetailsFragment;
import com.thescreenapp.model.Candidate;
import com.thescreenapp.model.ScreenModelObject;

public class CandidateDetailFragment extends DetailsFragment {
	
	protected final String TAG = this.getClass().getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "Creating something...");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "Creating view...");
		View rootView = inflater.inflate(R.layout.fragment_detail_candidate, container, false);
		Bundle bundle = getArguments();
		if( bundle != null ) {
			Object obj = bundle.get(EXTRA_DETAILS_ID);
			if( obj instanceof ScreenModelObject ) {
				loadDetails((ScreenModelObject) obj, rootView);
			}
		}
		return rootView;
	}

	@Override
	public void loadDetails(ScreenModelObject detail) {
		loadDetails(detail, getView());
	}
	
	protected void loadDetails(ScreenModelObject detail, View view) {
		Log.d(TAG, "Loading details...");
		if( detail instanceof Candidate ) { 
			Candidate candidate = (Candidate) detail;
			((TextView) view.findViewById(R.id.text_name)).setText(candidate.getFullName());
			((TextView) view.findViewById(R.id.text_email)).setText(candidate.getEmail());
			DateFormat sdf = SimpleDateFormat.getDateTimeInstance();
			((TextView) view.findViewById(R.id.text_meta_data)).setText(sdf.format(candidate.getCreationDate()));
		}
	}

}
