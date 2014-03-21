package com.findmyplace.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.findmyplace.model.MapModel.RMDirection;
import com.google.android.gms.maps.model.LatLng;

public class MapRouteUtil {

	private final static  String PREFIX = "http://maps.googleapis.com/maps/api/directions/json?";
	static String TAG = "MapRouteUtil";

	public static List<RMDirection> getRoute(Context context,LatLng source,LatLng destenation)
	{
		List<RMDirection> directions = null;
		String sourceString = "" + source.latitude + "," + source.longitude;
		String destenationString = "" + destenation.latitude + "," + destenation.longitude;

		Log.d(TAG, "Source = " + sourceString + ", Destination = " + destenationString);            

		String routeURL = createURL(sourceString,destenationString);

		try {
			String jsonReasponse = ServerUtil.doGet(routeURL,true);
			directions =	parseJsonGDir(jsonReasponse);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  directions;
	}

	private static String createURL(String sourceString, String destenationString) {

		String routeURL = PREFIX;

		HashMap< String, String> dic = new  HashMap<String, String>();

		dic.put(APConstant.ROUTE_ORIGIN, sourceString);
		dic.put(APConstant.ROUTE_DESTENATION, destenationString);
		dic.put(APConstant.ROUTE_MODE,APConstant.ROUTE_WALKING);
		dic.put(APConstant.ROUTE_SENSOR, "true");
		dic.put(APConstant.ROUTE_REGION, "he");
		routeURL += mapAsString(dic);

		return routeURL;
	}

	private static String mapAsString(HashMap<String, String> dic) {
		StringBuilder sb = new StringBuilder();

		Iterator<Map.Entry<String, String>> entries = dic.entrySet().iterator();

		while(entries.hasNext())
		{
			Map.Entry<String, String> entry = entries.next();
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
			sb.append("&");
		}
		//remove the last '&'
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}



	/**
	 * Parse the Json to build the GDirection object associated
	 * 
	 * @param json
	 *            The Json to parse
	 * @return The GDirection define by the JSon
	 */
	private static List<RMDirection> parseJsonGDir(String json) {
		// JSon Object to parse
		JSONObject jObject;
		// The GDirection to return
		List<RMDirection> directions = null;

		try {
			// initialize the JSon
			jObject = new JSONObject(json);
			// Starts parsing data
			directions = DirectionsJSONParser.parse(jObject);
		} catch (Exception e) {
			Log.e(TAG, "Parsing JSon from GoogleDirection Api failed, see stack trace below:", e);
		}
		return directions;
	}

}
