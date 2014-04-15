package com.findmyplace.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.findmyplace.R;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class TitleLocationInfoAdapter implements InfoWindowAdapter  {

	LayoutInflater inflater = null;
	
	public TitleLocationInfoAdapter(Context context){
		inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getInfoContents(Marker marker) {
		 View popup=inflater.inflate(R.layout.location_title, null);

		    TextView tv=(TextView)popup.findViewById(R.id.title_info);

		    tv.setText(marker.getTitle());
		    tv=(TextView)popup.findViewById(R.id.snippet);
		    tv.setText(marker.getSnippet());

		    return(popup);
	}

	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}

}
