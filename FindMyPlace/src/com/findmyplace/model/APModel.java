package com.findmyplace.model;

import java.util.HashMap;

public abstract class APModel {

    private String m_ID;
    private double  m_x;
    private double  m_y;
    private double  m_z;
    private String m_title;
    private String m_imagePath;
    private HashMap<String, String> extentions =new HashMap<String, String>();
    
    public double getX()
    {
            return m_x;
    }
    
    public void setX(double x) {
            this.m_x = x;
    }
    
    public double getY() {
            return m_y;
    }

    public void setY(double y) {
            this.m_y = y;
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
    
    public void setLocation(double x,double y )
    {
            m_x=x;
            m_y=y;
    }
    
    public void setLocation(double x,double y ,double z )
    {
            m_x=x;
            m_y=y;
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
    
}