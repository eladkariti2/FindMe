package com.findmyplace.fragment;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.findmyplace.R;
import com.findmyplace.activites.MainActivity;
import com.findmyplace.adapters.RouteStoryAdapter;
import com.findmyplace.adapters.TitleLocationInfoAdapter;
import com.findmyplace.adapters.UserLocationsAdapter;
import com.findmyplace.interfaces.LocationListenerI;
import com.findmyplace.model.APModel;
import com.findmyplace.model.MapModel.RMDirection;
import com.findmyplace.model.MapModel.RMLegs;
import com.findmyplace.storyPointUtil.StoryPointBuilder;
import com.findmyplace.storyPointUtil.StoryPointHolder;
import com.findmyplace.util.APConstant;
import com.findmyplace.util.MapRouteUtil;
import com.findmyplace.util.MapUtil;
import com.findmyplace.util.database.DataBaseUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RouteFragment extends Fragment implements LocationListenerI{

	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private View drawerMenuContainer;
	ActionBarDrawerToggle drawerToggle;
	boolean isMenuOpen = false;
	boolean firsLocation = true;
	boolean displayZoom = false;
	View view;
	GoogleMap map;
	RMDirection direction;
	LatLng destinationLocation;
	Location currentLocation;
	Timer timer ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.route_layout, container, false);

		return view;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);	
		((MainActivity)getActivity()).startLocationListener(); 

		Bundle b = getArguments();
		double latitude = b.getDouble(APConstant.LOCATION_LANTITUDE);
		double longitude = b.getDouble(APConstant.LOCATION_LONGITUDE);
		destinationLocation = new LatLng(latitude, longitude);

		initView();

	}
	
	@Override
	public void onStart() {
		super.onStart();
		// Set the drawer toggle as the DrawerListener
		drawerLayout.setDrawerListener(drawerToggle);//TO DO check if there is better implementation for the listener.
	}

	private void initView() {

		drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
		drawerMenuContainer = view.findViewById(R.id.drawerMenuContainer);

		drawerList = (ListView)drawerMenuContainer.findViewById(R.id.list_route_story);


		//Enable the menu toggle only while the activity is about to display.
		//menuButtonsContainer.setOnClickListener(menuClickListener);

		drawerToggle = new ActionBarDrawerToggle(getActivity(), /* host Activity */
				drawerLayout, /* DrawerLayout object */
				R.drawable.fb_share_icon, /*
				 * nav drawer icon to replace 'Up'
				 * caret
				 */
				R.string.app_name, /* "open drawer" description */
				R.string.app_name /* "close drawer" description */
				) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				isMenuOpen = false;
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				isMenuOpen = true;
			}
		};

	}


	@Override
	public void updateLocation(Location location) {
		if(firsLocation){
			firsLocation = !firsLocation;

			initilizeMap();
			setupMap(location);		
			setupStoryPoint();

			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					final CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							map.moveCamera(zoom);
							map.setMyLocationEnabled(true);
							displayZoom = true;
						}
					});

				}
			}, 2000);

		}else{
			if(displayZoom){
				LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
				CameraUpdate center= CameraUpdateFactory.newLatLng(position);			
				map.moveCamera(center);
				displayZoom = false;
			}
			//map.addMarker(MapUtil.getMarker(new LatLng(location.getLatitude(),location.getLongitude())));
		}

	}

	private void setupStoryPoint() {
	   List<StoryPointHolder> storyPoints = StoryPointBuilder.getStoryPointHolder(getActivity(), direction);
		RouteStoryAdapter adapter = new RouteStoryAdapter(getActivity(), storyPoints);
	
		drawerList.setAdapter(adapter);
		drawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				   toggelMenu();
					StoryPointHolder holder = (StoryPointHolder) view.getTag();
					if(!"true".equals(holder.getExtention(StoryPointBuilder.IS_FULL_ROUTE, "false"))){
						LatLng location = holder.getLocation();
						CameraUpdate center= CameraUpdateFactory.newLatLng(location);			
						map.moveCamera(center);
						// Mark each path of the path with marker
					    Marker marker = map.addMarker(new MarkerOptions().position(location).title(holder.getTitle())
								.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));					
						marker.showInfoWindow();						
						stopTimer();						
						startTimer(marker);		
				}
			}
		});
	}

	protected void startTimer(final Marker marker) {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				RouteFragment.this.getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						 marker.remove();
					}
				});
			}
		}, 2500);
	}

	protected void stopTimer() {
		if(timer != null){
			timer.cancel();
			timer = null;
		}
	}

	protected synchronized void toggelMenu() {
		if (isMenuOpen) {
			drawerLayout.closeDrawer(drawerMenuContainer);
		} else {
			drawerLayout.openDrawer(drawerMenuContainer);
		}
		isMenuOpen = !isMenuOpen;
	}

	private void setupMap(Location location) {
		List<RMDirection> directions =  MapRouteUtil.getRoute(getActivity(), new LatLng(location.getLatitude(), location.getLongitude()), destinationLocation);
		direction = directions.get(0);
		MapUtil.drawGDirection(direction, map);

		//center the point on map
		LatLng northEast = direction.getmNorthEastBound();
		LatLng southWest= direction.getmSouthWestBound();

		LatLngBounds.Builder builder = new LatLngBounds.Builder();		
		builder.include(northEast);
		builder.include(southWest);
		LatLngBounds bounds = builder.build();
		int padding = 100; // offset from edges of the map in pixels
		final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

		map.setOnMapLoadedCallback(new OnMapLoadedCallback() {

			@Override
			public void onMapLoaded() {
				// TODO Auto-generated method stub
				map.moveCamera(cu);
				drawerLayout.setVisibility(View.VISIBLE);
				view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
			}
		});

	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (map == null) {
			SupportMapFragment fragment = (SupportMapFragment) (getActivity().getSupportFragmentManager().findFragmentById(R.id.main_content));
			map = fragment.getMap();
			// check if map is created successfully or not
			if (map == null) {
				Toast.makeText(getActivity(),
						getActivity().getString(R.string.map_error), Toast.LENGTH_SHORT)
						.show();
			}else{
				map.setInfoWindowAdapter(new TitleLocationInfoAdapter(getActivity()));
			}
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		try {
			SupportMapFragment fragment = (SupportMapFragment) getActivity()
					.getSupportFragmentManager().findFragmentById(
							R.id.main_content);
			if (fragment != null) getFragmentManager().beginTransaction().remove(fragment).commit();

		} catch (IllegalStateException e) {
		}
	}
}
