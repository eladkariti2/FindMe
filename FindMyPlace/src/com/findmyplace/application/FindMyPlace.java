package com.findmyplace.application;

import java.util.Locale;

import android.app.Application;
import android.content.res.Configuration;

public class FindMyPlace extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Locale locale = new Locale("he");
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());
	}
}
