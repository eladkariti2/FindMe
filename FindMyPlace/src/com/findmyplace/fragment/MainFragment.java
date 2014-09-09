package com.findmyplace.fragment;

import com.example.findmyplace.R;
import com.findmyplace.adapters.TitleLocationInfoAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainFragment extends Fragment{

	GoogleMap mMap;
	View mView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.main_fragment_layout, container, false);

		return mView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		initilizeMap();
		initMapViewOptions();
		initComponent();
	}

	private void initMapViewOptions() {
	
		mView.findViewById(R.id.map_hybrid_view).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(mMap != null){
					mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				}
			}
		});	
		mView.findViewById(R.id.map_normal_view).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(mMap != null){
					mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				}
			}
		});	
	}

	private void initComponent() {


	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if(isAdded()){
			if (mMap == null) {
				SupportMapFragment fragment = (SupportMapFragment) (getActivity().getSupportFragmentManager().findFragmentById(R.id.main_content));
				mMap = fragment.getMap();
				// check if map is created successfully or not
				if (mMap == null) {
					Toast.makeText(getActivity(),
							getActivity().getString(R.string.map_error), Toast.LENGTH_SHORT)
							.show();
				}else{
					mMap.setInfoWindowAdapter(new TitleLocationInfoAdapter(getActivity()));
				}
			}
		}
	}


}
