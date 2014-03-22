package com.findmyplace.util.database;

import android.content.Context;
import android.util.Log;

import com.findmyplace.model.APModel;

public class DataBaseUtil {

	public static void saveUserLocationInDB(Context context,APModel locationModel) {
		String longitude = locationModel.getLongitude() + "";
		String latitude = locationModel.getLatitude() + "";;
		String imagePath = locationModel.getImage();
		String address = locationModel.getAddress();
		String description = locationModel.getLocationDescription();	
		
		Log.d("DataBaseUtil","Insert to DB address = " + address + ", description = " + description + ", latitude = " + latitude + ", " +
				"longitude = " + longitude + ", image path = " + imagePath);
		
		RMDataBaseHandler dbHandler = new RMDataBaseHandler(context);
		dbHandler.insert( description, address, latitude, longitude, imagePath, "");
		
	}

}
