package com.findmyplace.storyPointUtil;

import java.util.HashMap;

import com.google.android.gms.maps.model.LatLng;

public class StoryPointHolder {

	private String htmlText;
	private String distance;
	private String duration;
	private LatLng location ; 
	private String title ; 
	private HashMap<String, String> extentions =new HashMap<String, String>();

	public String getHtmlText() {
		return htmlText;
	}
	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getExtention(String key,String defaultValue)
	{
		String value = defaultValue;
		if(extentions.containsKey(key))
		{
			value= extentions.get(key);
		}
		return value;
	}

	public void setExtention(String key,String value)
	{
		extentions.put(key, value);
	}
	
	public LatLng getLocation() {
		return location;
	}
	public void setLocation(LatLng location) {
		this.location = location;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


}
