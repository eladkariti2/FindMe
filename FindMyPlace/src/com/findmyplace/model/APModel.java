package com.findmyplace.model;

import java.util.HashMap;

public abstract class APModel {

    private String m_ID;
    private double  m_longitude;
    private double  m_latitude;
    private double  m_z;
    private String m_title;
    private String m_imagePath;
     private String m_address;
    private String m_locationDescription;
    private HashMap<String, String> extentions =new HashMap<String, String>();
    
    public double getLongitude()
    {
            return m_longitude;
    }
    
    public void setLongitude(double longitude) {
            this.m_longitude = longitude;
    }
    
    public double getLatitude() {
            return m_latitude;
    }

    public void setLatitude(double latitude) {
            this.m_latitude = latitude;
    }

    public double getZ() {
            return m_z;
    }

    public void setZ(double z) {
            this.m_z = z;
    }

    public String getID() {
            return m_ID;
    }

    public void setID(String id) {
            this.m_ID = id;
    }
    
      public String getLocationDescription() {
            return m_locationDescription;
    }

    public void setLocationDescription(String description) {
            this.m_locationDescription = description;
    }

    public String getTitle() {
            return m_title;
    }

    public void setTitle(String title) {
            this.m_title = title;
    }

    public String getImage() {
            return m_imagePath;
    }

    public void setImage(String path) {
            this.m_ID = path;
    }
    
    public void setLocation(double longitude,double latitude )
    {
            m_longitude = longitude;
            m_latitude = latitude;
    }
    
    public void setLocation(double longitude,double latitude ,double z )
    {
            m_longitude=longitude;
            m_latitude=latitude;
            m_z=z;
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

	public void setAddress(String address) {
		m_address = address;
		
	}
	public String getAddress() {
		return m_address;
		
	}
    
}