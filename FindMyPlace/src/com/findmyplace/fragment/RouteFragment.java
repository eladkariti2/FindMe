package com.findmyplace.fragment;

import java.util.List;

import com.example.findmyplace.R;
import com.findmyplace.adapters.UserLocationsAdapter;
import com.findmyplace.interfaces.LocationListenerI;
import com.findmyplace.model.APModel;
import com.findmyplace.util.database.DataBaseUtil;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RouteFragment extends Fragment implements LocationListenerI{

	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private View drawerMenuContainer;
	ActionBarDrawerToggle drawerToggle;
	boolean isMenuOpen = false;
	
	View view;
	
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
		
		initView();
		
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
		// TODO Auto-generated method stub
		
	}

}
