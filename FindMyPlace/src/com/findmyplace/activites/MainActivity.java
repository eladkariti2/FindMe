package com.findmyplace.activites;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.example.findmyplace.R;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.UiLifecycleHelper;
import com.findmyplace.fragment.HomeFragment;
import com.findmyplace.fragment.RouteFragment;
import com.findmyplace.interfaces.LocationListenerI;
import com.findmyplace.listenr.APLocationListenr;
import com.findmyplace.util.FacebookUtil;
import com.findmyplace.util.OSUtil;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends FragmentActivity {

	private static String Tag = "MainActivity";

	protected  UiLifecycleHelper facebookSessionLifeCycleHelper;
	protected Session.StatusCallback facebookSessionCallback;
	
	LatLng _currentPosition ;
	LocationManager _locationManager;
	Fragment _currentFrgment;
	TextView _title;

	APLocationListenr _locationListener = new APLocationListenr(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(Tag,"oncreate");

		Session session = Session.getActiveSession();
		facebookSessionLifeCycleHelper = new UiLifecycleHelper(this, new FacebookUtil.SessionStatusCallback(this,  initFacebookSessionCallback()));
		
		if(!UrlSchemeUtil.parseData(getIntent().getData())){
			if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) { 
				// Activity was brought to front and not created, 
				// Thus finishing this will get us to the last viewed activity 
				finish(); 
				return; 
			} 
		}

		setContentView(R.layout.base_activity);

		OSUtil.setLocale(this, "he");

		initView();

		_currentFrgment = new HomeFragment();
		addFragment(_currentFrgment,false,"");
		facebookSessionLifeCycleHelper.onCreate(savedInstanceState);
	}

	public StatusCallback initFacebookSessionCallback() {
		return null;
	}
	
	public void addFragment(Fragment fragment, boolean addToBackStack,String tag) {
		addFragment(fragment,addToBackStack,tag,false);
	}

	public void addFragment(Fragment fragment, boolean addToBackStack,String tag,boolean toAdd) {
		_currentFrgment = fragment;
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();

		if(fm.getBackStackEntryCount()>0 && !toAdd){
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


	private void initView() {
		_title = (TextView) findViewById(R.id.navigation_title);
	}



	public void updateLocation(Location location){
		if(_currentFrgment instanceof  LocationListenerI ){
			//Update location if needed
			((LocationListenerI)_currentFrgment).updateLocation(location);

			//Stop listen to location if it's not route
			if(!(_currentFrgment instanceof RouteFragment)){
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(Tag,"onResume");
		facebookSessionLifeCycleHelper.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(Tag,"onStart");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d(Tag,"oncreonStopate");
		facebookSessionLifeCycleHelper.onStop();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(Tag,"onDestroy");
		facebookSessionLifeCycleHelper.onDestroy();
	}

	public void updateTitle(String title){
		_title.setText(title);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		facebookSessionLifeCycleHelper.onSaveInstanceState(outState);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(Tag,"onActivityResult");
		facebookSessionLifeCycleHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		facebookSessionLifeCycleHelper.onPause();
	}

}
