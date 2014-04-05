package com.findmyplace.fragment;

import com.example.findmyplace.R;
import com.findmyplace.activites.MainActivity;
import com.findmyplace.util.database.DataBaseUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeFragment extends Fragment{

	View _getRoute;
	View _saveLocation;
	
	@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
		}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_layout, container, false);
		_getRoute = view.findViewById(R.id.get_route);
		_saveLocation = view.findViewById(R.id.save_location);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		_getRoute.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Fragment userRouteFragment = new UserRoutsFragment();
				((MainActivity) getActivity()).addFragment(userRouteFragment, true, "");			
			}
		});
		
		_saveLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Fragment saveLocationFragment = new SaveLocationFragment();
				((MainActivity) getActivity()).addFragment(saveLocationFragment, true, "");
				
			}
		});
	}
}
