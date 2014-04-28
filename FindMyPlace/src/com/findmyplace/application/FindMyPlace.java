package com.findmyplace.application;

import java.util.Locale;

import android.app.Application;
import android.content.res.Configuration;

public class FindMyPlace extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	
	}
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onConfigurationChangedBehaviour(this,newConfig);        
    }  
 

    public static void onConfigurationChangedBehaviour(Application context, Configuration newConfig) {
    	
    }
}
