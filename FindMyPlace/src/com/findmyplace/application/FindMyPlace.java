package com.findmyplace.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

public class FindMyPlace extends Application {

	protected static Context context;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		context = this;
	
	}
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onConfigurationChangedBehaviour(this,newConfig);        
    }  
 

    public static void onConfigurationChangedBehaviour(Application context, Configuration newConfig) {
    	
    }

	public static Context getAppContext(){
		return context;
	}
}
