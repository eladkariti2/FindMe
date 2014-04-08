package com.findmyplace.util;

import android.graphics.Color;

import com.example.findmyplace.R;
import com.findmyplace.model.MapModel.RMDirection;
import com.findmyplace.model.MapModel.RMLegs;
import com.findmyplace.model.MapModel.RMPath;
import com.findmyplace.model.MapModel.RMPoint;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapUtil {

	public static CircleOptions getCircleOption(LatLng position) {
		return new CircleOptions()
		.center(position)
		.radius(30)
		.fillColor(Color.parseColor("#fbe3e3e3")) // default
		.strokeColor(0x10000000).strokeWidth(5);   
	}

	public static MarkerOptions getMarker(LatLng position) {
		return new MarkerOptions().position(position)
				.title("Current Location")
				.snippet("")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
	}

	/**
	 * Draw on the given map the given GDirection object
	 * 
	 * @param direction
	 *            The google direction to draw
	 * @param map
	 *            The map to draw on
	 */
	public static void drawGDirection(RMDirection direction, GoogleMap map) {
		// The polylines option to create polyline
		PolylineOptions lineOptions = new PolylineOptions();

		// Browse the directions' legs and then the leg's paths
		for (RMLegs legs : direction.getLegsList()) {
			for (RMPath path : legs.getPathsList()) {

				//lineOptions.add(path.getPath().get(0).getLatLng());
				//lineOptions.add(path.getPath().get(path.getPath().size() -1).getLatLng());
				// browse the GDPoint that define the path
				for (RMPoint point : path.getPath()) {

					// Add the point to the polyline
					lineOptions.add(point.getLatLng());
				}

			}
			// Mark the first point of the path with a HUE_AZURE marker
			map.addMarker(new MarkerOptions().position(legs.getStartPoint().getLatLng()).title(legs.getmStartAddress())
					.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

			// Mark the last point of the path with a HUE_AZURE marker
			map.addMarker(new MarkerOptions().position(legs.getEndPoint().getLatLng()).title(legs.getmEndAddress())
					.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
			
			// A 5 width Polyline please
			lineOptions.width(8);		
			lineOptions.color(Color.BLUE);
			lineOptions.geodesic(true);
			// Drawing polyline in the Google Map for the i-th route
			map.addPolyline(lineOptions);
		}
	}


}
