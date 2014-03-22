package com.findmyplace.fragment;

import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findmyplace.R;
import com.findmyplace.activites.MainActivity;
import com.findmyplace.interfaces.LocationListenerI;
import com.findmyplace.model.APModel;
import com.findmyplace.model.APParkingModel;
import com.findmyplace.model.MapModel.RMDirection;
import com.findmyplace.providers.MyImageProvider;
import com.findmyplace.util.MapRouteUtil;
import com.findmyplace.util.MapUtil;
import com.findmyplace.util.StringUtil;
import com.findmyplace.util.database.DataBaseUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.maps.GeoPoint;

public class SaveLocationFragment extends Fragment implements LocationListenerI{

	GoogleMap _map;
	LocationManager _locationManager;
	APModel _locationModel ;
	Button _saveLocation;
	ImageView _locationImage ;
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater	.inflate(R.layout.save_location_layout, container, false);
		_saveLocation = (Button)layout.findViewById(R.id.save_location_button);

		return layout;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initilizeMap();
		initComponent();

	}	

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (_map == null) {
			SupportMapFragment fragment = (SupportMapFragment) (getActivity().getSupportFragmentManager().findFragmentById(R.id.map));
			_map = fragment.getMap();

			// check if map is created successfully or not
			if (_map == null) {
				Toast.makeText(getActivity(),
						getActivity().getString(R.string.map_error), Toast.LENGTH_SHORT)
						.show();
			}
		}
	}


	/***
	 * function to check if the GPS is active, and to set the location manger. 
	 */
	private void initComponent() {
		((MainActivity)getActivity()).startLocationListener();
		_saveLocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				saveLocation();	
			}
		});
	}

	protected void getRoute(LatLng destenation, LatLng position) {

		List<RMDirection> directions =	MapRouteUtil.getRoute(getActivity(), position, destenation);
		MapUtil.drawGDirection(directions.get(0), _map);
	}

	@Override
	public void updateLocation(Location location) {
		Log.d("SaveLocationFragment", "Location - latitude: " + location.getLatitude() +", longitude: " + location.getLongitude());
		int lat = (int) (location.getLatitude() * 1E6);
		int lng = (int) (location.getLongitude() * 1E6);
		GeoPoint point = new GeoPoint(lat,lng);	
		String addressText = StringUtil.ConvertPointToLocation(getActivity(), point);	

		TextView address = (TextView)getActivity().findViewById(R.id.address_name);
		address.setText(addressText);

		LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
		CameraUpdate center= CameraUpdateFactory.newLatLng(position);
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

		_map.setMyLocationEnabled(true);
		_map.moveCamera(center);
		_map.animateCamera(zoom);

		getActivity().findViewById(R.id.progrees_bar).setVisibility(View.GONE);
		getActivity().findViewById(R.id.save_location_container).setVisibility(View.VISIBLE);

		_locationModel = new APParkingModel();
		_locationModel.setLongitude(location.getLatitude());
		_locationModel.setLatitude(location.getLongitude());
		_locationModel.setAddress(addressText);

	}

	private void saveLocation() {

		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.save_location_dialog);

		EditText locationName = (EditText) dialog.findViewById(R.id.location_name);
		Button cancelButton = (Button)dialog.findViewById(R.id.cancel_button);
		Button okButton = (Button)dialog.findViewById(R.id.ok_button);
		_locationImage = (ImageView)dialog.findViewById(R.id.location_image);
		TextView address = (TextView)dialog.findViewById(R.id.address);
		address.setText(_locationModel.getAddress());
		
		//Check if the device has camera, if not then hide picture container
		PackageManager pm = getActivity().getPackageManager();
		boolean hasCamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
		_locationImage.setVisibility(hasCamera ? View.VISIBLE : View.GONE);

		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.hide();
			}
		});
		
		okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {		
				DataBaseUtil.saveUserLocationInDB(getActivity(),_locationModel);
				dialog.hide();
			}
		});

		locationName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {	
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String name = new StringBuilder(s).toString();
			}
		});

		_locationImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dispatchTakePictureIntent();
			}
		});

		dialog.show();
	}

	//Strat 

	//Calling for app that can take picture and waite to result.
	static final int REQUEST_IMAGE_CAPTURE = 1;

	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, MyImageProvider.CONTENT_URI);
			startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		}
	}


	//Handle the picture result.
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
			Bundle extras = data.getExtras();
			Log.d("ELAD",extras.toString());
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			
			_locationImage.setImageBitmap(imageBitmap);
		}
	}

	//End

}
