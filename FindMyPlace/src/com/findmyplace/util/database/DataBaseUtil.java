package com.findmyplace.util.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.findmyplace.model.APModel;
import com.findmyplace.model.APParkingModel;

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
		dbHandler.open();
		dbHandler.insert( description, address, latitude, longitude, imagePath, "");
		
	}

	public static 	List<APModel> getAllLocations(Context context) {
		RMDataBaseHandler dbHandler = new RMDataBaseHandler(context);
		dbHandler.open();
		Cursor cursor = dbHandler.getAllLocations();
		
		List<APModel> locations = new ArrayList<APModel>();
		for(cursor.moveToNext(); !cursor.isAfterLast() ; cursor.moveToNext()){
			APModel model = new APParkingModel();
			model.setID(cursor.getString(cursor.getColumnIndex(RMDataBaseHandler.ID)));
			model.setAddress(cursor.getString(cursor.getColumnIndex(RMDataBaseHandler.LOCATION_ADDRESS)));
			model.setLocationDescription(cursor.getString(cursor.getColumnIndex(RMDataBaseHandler.LOCATION_NAME)));
			model.setLatitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex(RMDataBaseHandler.LOCATION_LATITUDE))));
			model.setLongitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex(RMDataBaseHandler.LOCATION_LONGITUDE))));
			model.setImage(cursor.getString(cursor.getColumnIndex(RMDataBaseHandler.IMAGE_URL)));
			
			locations.add(model);
		}
		
		
		dbHandler.close();
		return locations;
		
	}

	public static boolean deleteLocation(Context context, String id) {
		RMDataBaseHandler dbHandler = new RMDataBaseHandler(context);
		dbHandler.open();
		Boolean  isDeleted = dbHandler.delete(id);
	
		if (isDeleted){
			Log.d("DataBaseUtil", "Location deleted, ID = " + id);
		}else{
			Log.d("DataBaseUtil", "Location not deleted, ID = " + id);
		}
			
		dbHandler.close();
		
		return isDeleted;
	}

}
