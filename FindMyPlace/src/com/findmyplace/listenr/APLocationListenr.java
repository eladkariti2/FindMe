package com.findmyplace.listenr;

import com.findmyplace.activites.MainActivity;
import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class APLocationListenr implements LocationListener{

	MainActivity _context;
	
	public APLocationListenr(MainActivity context){
		_context = context;
	}
	
	@Override
	public void onLocationChanged(Location location) {
			
		_context.updateLocation(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
