package com.findmyplace.activites;


import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.findmyplace.R;
import com.findmyplace.fragment.HomeFragment;
import com.findmyplace.fragment.RouteFragment;
import com.findmyplace.interfaces.LocationListenerI;
import com.findmyplace.listenr.APLocationListenr;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends FragmentActivity {

	LatLng _currentPosition ;
	LocationManager _locationManager;
	Fragment currentFrgment;

	APLocationListenr _locationListener = new APLocationListenr(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_activity);

		Locale locale = new Locale("he");
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());

		initComponent();

		currentFrgment = new HomeFragment();
		addFragment(currentFrgment,false,"");
	}

	private void addFragment(Fragment fragment, boolean addToBackStack,String tag) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();

		if(fm.getBackStackEntryCount()>0 ){
			fm.popBackStack();
			fragmentTransaction.replace(R.id.content, fragment,tag);
		}
		else{
			fragmentTransaction.add(R.id.content, fragment,tag);
		}
		if (addToBackStack) {
			fragmentTransaction.addToBackStack(null);
		}

		fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
		fragmentTransaction.commit();
	}

	/***
	 * function to check if the GPS is active, and to set the location manger. 
	 */
	private void initComponent() {

	}



	public void updateLocation(Location location){
		if(currentFrgment instanceof  LocationListenerI ){
			//Update location if needed
			((LocationListenerI)currentFrgment).updateLocation(location);
			
			//Stop listen to location if it's not route
			if(!(currentFrgment instanceof RouteFragment)){
				stopLocationListener();
			}
		}
		else{
			startLocationListener();
		}		
	}

	public void stopLocationListener() {
		_locationManager.removeUpdates(_locationListener);
	}

	public void startLocationListener() {
		// Acquire a reference to the system Location Manager
		_locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if(!_locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			buildAlertMessageNoGps();
		}

		_locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,_locationListener);
	}

	private void buildAlertMessageNoGps() {
		String message = getString(R.string.gps_disable);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setPositiveButton(getString(R.string.ok_button), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

			}
		});

		builder.setNegativeButton(getString(R.string.cancel_button),  new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();		
			}
		});

		builder.setMessage(message);

		AlertDialog alert = builder.create();
		alert.show();
	}

	public void addSaveLocationFragment(Fragment saveLocationFragment) {
		currentFrgment = saveLocationFragment;
		addFragment(saveLocationFragment,true,"");
		
	}
}
