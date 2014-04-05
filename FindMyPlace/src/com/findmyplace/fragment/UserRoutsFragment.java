package com.findmyplace.fragment;

import java.util.List;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.findmyplace.R;
import com.findmyplace.activites.MainActivity;
import com.findmyplace.adapters.UserLocationsAdapter;
import com.findmyplace.interfaces.LocationListenerI;
import com.findmyplace.model.APModel;
import com.findmyplace.util.APConstant;
import com.findmyplace.util.database.DataBaseUtil;

public class UserRoutsFragment  extends Fragment implements LocationListenerI{

	ListView _list;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.location_list_layout, container, false);
		
		_list = (ListView)view.findViewById(R.id.locations_list);
		
		return view;
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		List<APModel> locations = DataBaseUtil.getAllLocations(getActivity());
		UserLocationsAdapter adapter = new UserLocationsAdapter(getActivity(), locations);
		_list.setAdapter(adapter);
		_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				APModel model = (APModel)view.getTag();
				
				Bundle bundle = new Bundle();
				bundle.putDouble(APConstant.LOCATION_LANTITUDE, model.getLatitude());
				bundle.putDouble(APConstant.LOCATION_LONGITUDE, model.getLongitude());
				
				Fragment routeFragment = new RouteFragment();
				
				routeFragment.setArguments(bundle);
				((MainActivity) getActivity()).addFragment(routeFragment, true, "",true);
				
			}
		});
		
	}
	
	
	@Override
	public void updateLocation(Location location) {
		// TODO Auto-generated method stub
		
	} 

}
