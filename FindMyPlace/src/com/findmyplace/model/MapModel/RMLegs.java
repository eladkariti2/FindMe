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


public class RMLegs {
	
	List<RMPath> mPathsList;
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
	 * Starting address
	 */
	String mStartAddress;
	/**
	 * Ending Address
	 */
	String mEndAddress;

	/**
	 * Starting Address
	 */
	RMPoint mStartPoint;
	
	/**
	 * Ending Address
	 */
	RMPoint mEndPoint;
	
	/**
	 * @param pathsList
	 */
	public RMLegs(List<RMPath> pathsList) {
		super();
		this.mPathsList = pathsList;
	}

	/**
	 * @return the mLegsList
	 */
	public final List<RMPath> getPathsList() {
		return mPathsList;
	}

	/**
	 * @param mLegsList the mLegsList to set
	 */
	public final void setPathsList(List<RMPath> mPathsList) {
		this.mPathsList = mPathsList;
	}

	/**
	 * @return the mDistance
	 */
	public final int getmDistanceTime() {
		return mDistanceTime;
	}

	/**
	 * @param mDistance the mDistance to set
	 */
	public final void setmDistanceTime(int mDistance) {
		this.mDistanceTime = mDistance;
	}

	/**
	 * @return the mDuration
	 */
	public final int getmDurationTime() {
		return mDurationTime;
	}

	/**
	 * @param mDuration the mDuration to set
	 */
	public final void setmDurationTime(int mDuration) {
		this.mDurationTime = mDuration;
	}

	/**
	 * @return the mStartAddress
	 */
	public final String getmStartAddress() {
		return mStartAddress;
	}

	/**
	 * @param mStartAddress the mStartAddress to set
	 */
	public final void setmStartAddress(String mStartAddress) {
		this.mStartAddress = mStartAddress;
	}

	/**
	 * @return the mEndAddress
	 */
	public final String getmEndAddress() {
		return mEndAddress;
	}

	/**
	 * @param mEndAddress the mEndAddress to set
	 */
	public final void setmEndAddress(String mEndAddress) {
		this.mEndAddress = mEndAddress;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder strB=new StringBuilder("RMLegs\r\n");
		for(RMPath path:mPathsList) {
			strB.append(path.toString());
			strB.append("\r\n");
		}
		return strB.toString();
	}

	public void setStartPoint(RMPoint startPoint) {
		mStartPoint = startPoint;
	}

	public void setEndPoint(RMPoint endPoint) {
		mEndPoint = endPoint;
	}
	
	public RMPoint getStartPoint() {
		return mStartPoint ;
	}

	public RMPoint getEndPoint() {
		return mEndPoint ;
	}

	public void setDistance(String disance) {
		mDistance = disance;
	}

	public void setDuration(String duration) {
		mDuration = duration;
	}
	
	public final String getDuration() {
		return mDuration;
	}
	
	public final String getDistance() {
		return mDistance;
	}
}
