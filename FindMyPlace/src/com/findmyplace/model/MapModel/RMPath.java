/**<ul>
 * <li>GoogleMapSample</li>
 * <li>com.android2ee.formation.librairies.google.map.utils.direction.model</li>
 * <li>13 sept. 2013</li>
 * 
 * <li>======================================================</li>
 *
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 *
 /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br> 
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 *  Belongs to <strong>Mathias Seguy</strong></br>
 ****************************************************************************************************************</br>
 * This code is free for any usage except training and can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * 
 * *****************************************************************************************************************</br>
 *  Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 *  Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br> 
 *  Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 *  <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */
package com.findmyplace.model.MapModel;

import java.util.List;


public class RMPath {

	List<RMPoint> mPath;
	/**
	 * The distance of the path
	 */
	int mDistanceTime;
	String mDistance;
	/**
	 * The duration of the path
	 */
	int mDurationTime;
	String mDuration;
	/**
	 * The travel mode of the path
	 */
	String mTravelMode;
	/**
	 * The Html text associated with the path
	 */
	String mHtmlText;

	
	/**
	 * @param path The list of GDPoint that makes the path
	 */
	public RMPath(List<RMPoint> path) {
		super();
		this.mPath = path;
	}

	/**
	 * @return the mPath
	 */
	public final List<RMPoint> getPath() {
		return mPath;
	}

	/**
	 * @param mPath the mPath to set
	 */
	public final void setPath(List<RMPoint> mPath) {
		this.mPath = mPath;
	}

	/**
	 * @return the mPath
	 */
	public final List<RMPoint> getmPath() {
		return mPath;
	}

	/**
	 * @return the mDistance
	 */
	public final int getDistanceTime() {
		return mDistanceTime;
	}

	public final String getDistance() {
		return mDistance;
	}
	/**
	 * @return the mDuration
	 */
	public final int getDurationTime() {
		return mDurationTime;
	}
	public final String getDuration() {
		return mDuration;
	}
	/**
	 * @return the mTravelMode
	 */
	public final String getTravelMode() {
		return mTravelMode;
	}

	/**
	 * @return the mHtmlText
	 */
	public final String getHtmlText() {
		return mHtmlText;
	}

	/**
	 * @param mPath the mPath to set
	 */
	public final void setmPath(List<RMPoint> mPath) {
		this.mPath = mPath;
	}

	/**
	 * @param mDistanceTime the mDistance to set
	 */
	public final void setDistanceTime(int distance) {
		this.mDistanceTime = distance;
	}
	public void setDistance(String distance) {

		this.mDistance = distance;
	}

	
	/**
	 * @param mDurationTime the mDuration to set
	 */
	public final void setDurationTime(int duration) {
		this.mDurationTime = duration;
	}
	public void setDuration(String duration) {
		this.mDuration = duration;
		
	}
	/**
	 * @param mTravelMode the mTravelMode to set
	 */
	public final void setTravelMode(String travelMode) {
		this.mTravelMode = travelMode;
	}

	/**
	 * @param mHtmlText the mHtmlText to set
	 */
	public final void setHtmlText(String htmlText) {
		this.mHtmlText = htmlText;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder strB=new StringBuilder("RMPath\r\n");
		for(RMPoint point:mPath) {
			strB.append(point.toString());
			strB.append(point.toString());
			strB.append(",");
		}
		return strB.toString();
	}

	

}
