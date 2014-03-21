package com.findmyplace.fragment;

import com.example.findmyplace.R;
import com.findmyplace.interfaces.LocationListenerI;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RouteFragment extends Fragment implements LocationListenerI{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.location_layout, container, false);
		
		
		
		
		return view;
		
	}
	
	
	@Override
	public void updateLocation(Location location) {
		// TODO Auto-generated method stub
		
	}

}
