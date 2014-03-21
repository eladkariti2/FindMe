package com.findmyplace.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.findmyplace.model.MapModel.RMDirection;
import com.findmyplace.model.MapModel.RMLegs;
import com.findmyplace.model.MapModel.RMPath;
import com.findmyplace.model.MapModel.RMPoint;
import com.google.android.gms.internal.en;
import com.google.android.gms.maps.model.LatLng;

//http://wptrafficanalyzer.in/blog/author/george/
public class DirectionsJSONParser {
	static String TAG = "DirectionsJSONParser";

	/**
	 * Receives a JSONObject and returns a GDirection
	 * 
	 * @param jObject
	 *            The Json to parse
	 * @return The GDirection defined by the JSon Object
	 */
	public static List<RMDirection> parse(JSONObject jObject) {
				
		List<RMDirection> directionsList = new ArrayList<RMDirection>();
		RMDirection currentGDirection = null;
		List<RMLegs> legs = new ArrayList<RMLegs>();	
		RMLegs currentLeg = null;
		List<RMPath> paths = new ArrayList<RMPath>();
		RMPath currentPath = null;
		RMPoint startPoint = null;
		RMPoint endPoint = null;
		
		// JSON Array representing Routes
		JSONArray jRoutesArray = null;
		JSONObject jRoute;
		JSONObject jBound;
		// JSON Array representing Legs
		JSONArray jLegsArray = null;
		JSONObject jLeg;
		// JSON Array representing Step
		JSONArray jStepsArray = null;
		JSONObject jStep;
		String polyline = "";
		try {
			jRoutesArray = jObject.getJSONArray("routes");
			Log.v(TAG, "routes found : " + jRoutesArray.length());
		
			/** Traversing all routes */
			for (int i = 0; i < jRoutesArray.length(); i++) {
				jRoute=(JSONObject) jRoutesArray.get(i);
				jLegsArray = jRoute.getJSONArray("legs");
				Log.v(TAG, "routes[" + i + "] contains jLegs found : " + jLegsArray.length());
				
				/** Traversing all legs */
				for (int j = 0; j < jLegsArray.length(); j++) {
					jLeg=(JSONObject) jLegsArray.get(j);
					jStepsArray = jLeg.getJSONArray("steps");
					Log.v(TAG, "routes[" + i + "]:legs[" + j + "] contains jSteps found : " + jStepsArray.length());
					
					/** Traversing all steps */
					for (int k = 0; k < jStepsArray.length(); k++) {
						jStep = (JSONObject) jStepsArray.get(k);
						polyline = (String) ((JSONObject) (jStep).get("polyline")).get("points");
						
						// Build the List of GDPoint that define the path
						List<RMPoint> list = decodePoly(polyline);
						
						if(k == 0){
							LatLng point =list.get(0).getLatLng();
							startPoint = new RMPoint(point.latitude, point.longitude);
						}
						else if(k == jStepsArray.length() -1){
							LatLng point =list.get(list.size() -1).getLatLng();
							endPoint = new RMPoint(point.latitude, point.longitude);
						}
						
						// Create the GDPath
						currentPath = new RMPath(list);
						currentPath.setDistanceTime(((JSONObject)jStep.get("distance")).getInt("value"));
						currentPath.setDistance(((JSONObject)jStep.get("distance")).getString("text"));
						currentPath.setDurationTime(((JSONObject)jStep.get("duration")).getInt("value"));
						currentPath.setDuration(((JSONObject)jStep.get("duration")).getString("text"));
						
						//String direction =StringUtil.parseHtml(jStep.getString("html_instructions"));
						
						currentPath.setHtmlText(jStep.getString("html_instructions"));
						currentPath.setTravelMode(jStep.getString("travel_mode"));
						
						Log.v(TAG,"routes[" + i + "]:legs[" + j + "]:Step[" + k + "] contains Points found : "
										+ list.size());
						Log.v(TAG,"routes[" + i + "]:legs[" + j + "]:Step[" + k + "] : " + currentPath.toString());
						
						// Add it to the list of Path of the Direction
						paths.add(currentPath);
					}
					// 
					currentLeg=new RMLegs(paths);
					currentLeg.setmDistanceTime(((JSONObject)jLeg.get("distance")).getInt("value"));
					currentLeg.setDistance(((JSONObject)jLeg.get("distance")).getString("text"));
					currentLeg.setmDurationTime(((JSONObject)jLeg.get("duration")).getInt("value"));
					currentLeg.setDuration(((JSONObject)jLeg.get("duration")).getString("text"));
					currentLeg.setmEndAddress(jLeg.getString("end_address"));
					currentLeg.setmStartAddress(jLeg.getString("start_address"));
					currentLeg.setStartPoint(startPoint);
					currentLeg.setEndPoint(endPoint);
					
					legs.add(currentLeg);
					
					Log.v(TAG, "Added a new Path and paths size is : " + paths.size());
				}
				// Build the GDirection using the paths found
				currentGDirection = new RMDirection(legs);
				jBound=(JSONObject)jRoute.get("bounds");
				currentGDirection.setmNorthEastBound(new LatLng(
						((JSONObject)jBound.get("northeast")).getDouble("lat"),
						((JSONObject)jBound.get("northeast")).getDouble("lng")));
				currentGDirection.setmSouthWestBound(new LatLng(
						((JSONObject)jBound.get("southwest")).getDouble("lat"),
						((JSONObject)jBound.get("southwest")).getDouble("lng")));
				currentGDirection.setCopyrights(jRoute.getString("copyrights"));
				directionsList.add(currentGDirection);
			}

		} catch (JSONException e) {
			Log.e(TAG, "Parsing JSon from GoogleDirection Api failed, see stack trace below:", e);
		} catch (Exception e) {
			Log.e(TAG, "Parsing JSon from GoogleDirection Api failed, see stack trace below:", e);
		}
		return directionsList;
	}

	/**
	 * Method to decode polyline points
	 * Courtesy :
	 * http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction
	 * -api-with-java
	 */
	private static List<RMPoint> decodePoly(String encoded) {

		List<RMPoint> poly = new ArrayList<RMPoint>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;
			poly.add(new RMPoint((double) lat / 1E5, (double) lng / 1E5));
		}

		return poly;
	}
}