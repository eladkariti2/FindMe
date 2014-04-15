package com.findmyplace.util;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.android.maps.GeoPoint;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

public class StringUtil {

	public static boolean isEmpty(String str) {
		boolean result = false;
		if (str == null || str.length() == 0) {
			result = true;
		}
		return result;
	}

	public static String parseHtml(String html) {
		String str = null;
		
		str = android.text.Html.fromHtml(html).toString();
		
		return str;
	}
	
	public static String ConvertPointToLocation(Context context,GeoPoint point) {
        String address = "";
        String country = "";
        Geocoder geoCoder = new Geocoder(context,
                Locale.getDefault());
        Log.i("StringUtil", " Locale.getDefault(): " +  Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocation(
                    point.getLatitudeE6() / 1E6,
                    point.getLongitudeE6() / 1E6, 1);
            if (addresses.size() > 0) {
                for (int index = 0; index < addresses.get(0).getMaxAddressLineIndex(); index++){
                    address += addresses.get(0).getAddressLine(index) + " ";
                }
                country = addresses.get(0).getCountryName();
               
                Log.i("StringUtil", "address: " + address + ", country: " + country);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String result = address  + ", " + country;
        return result;
    }
	
	
	
}
