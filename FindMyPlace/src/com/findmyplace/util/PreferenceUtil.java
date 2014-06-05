package com.findmyplace.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.findmyplace.FindMyPlace;



public class PreferenceUtil {

	 private static PreferenceUtil instance;
	 private SharedPreferences sharedPreferences = null;
	    
	    public static synchronized PreferenceUtil getInstance() {
	    	if (instance == null) {
	    		instance = new PreferenceUtil();
	    	}
	    	return instance;
	    }
	    
	    private PreferenceUtil() {
	        this(null);
	    }
	    
	    private PreferenceUtil(String PREF_FILE) {
	        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(FindMyPlace.getAppContext());
	    }

	    
	    public SharedPreferences.Editor getEditor(){
	        return sharedPreferences.edit();
	    }
	    
	    // int
	    public int getIntPref(String key, int defaultValue) {
	        return sharedPreferences.getInt(key,defaultValue);
	    }
	    
	    public void setIntPref(String key,int value){
	        SharedPreferences.Editor editor = getEditor();
	        editor.putInt(key, value);
	        editor.commit();
	    }
	    
	    
	    // long
	    public Long getLongPref(String key, long defaultValue) {
	        return sharedPreferences.getLong(key,defaultValue);
	    }
	    
	    public void setLongPref(String key,long value){
	        SharedPreferences.Editor editor = getEditor();
	        editor.putLong(key, value);
	        editor.commit();
	    }
	    
	    // boolean
	    public boolean getBooleanPref(String key,boolean defaultValue){
	        return sharedPreferences.getBoolean(key,defaultValue);
	    }

	    public void setBooleanPref(String key,boolean value){
	        SharedPreferences.Editor editor = getEditor();
	        editor.putBoolean(key, value);
	        editor.commit();
	    }
	    // String
	    public String getStringPref(String key,String defaultValue){
	        return sharedPreferences.getString(key,defaultValue);
	    }

	    public void setStringPref(String key,String value){
	        SharedPreferences.Editor editor = getEditor();
	        editor.putString(key,value);
	        editor.commit();
	    }

}
