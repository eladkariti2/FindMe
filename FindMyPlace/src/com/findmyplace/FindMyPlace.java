package com.findmyplace;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

public class FindMyPlace extends Application{

	protected static Context context;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d("FindMyPlace","onCreate");
		onCreateBehaviour(this);
	}

	public static void onCreateBehaviour(Application context) {
		FindMyPlace.context = context.getApplicationContext();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.d("FindMyPlace","onConfigurationChanged");
	} 
	
	public static Context getAppContext(){
		return context;
	}
}
