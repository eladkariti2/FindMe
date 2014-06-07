package com.findmyplace.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;

import com.example.findmyplace.R;
import com.findmyplace.FindMyPlace;

import android.content.Context;
import android.util.Log;



public class APDebugUtil {

	public static boolean getIsInDebugMode(){


		boolean result = false;
		try {
			Class c =  Class.forName(OSUtil.getPackageName()+ ".BuildConfig");
			Log.d("check", c.getName());
			Field debugField = c.getField("DEBUG");
			result = debugField.getBoolean(null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	private static void setDeafultUncaughtExceptionHandler(){

		boolean displayUncaughtExceptionHandler = true ; // PreferenceUtil.getInstance().getBooleanPref(APDebugUtil.ENABLE_MAILING_EXCEPTIONS, true);
		
		if(getIsInDebugMode() && displayUncaughtExceptionHandler){ 
			
			final Thread.UncaughtExceptionHandler androidDefaultUEH = Thread.getDefaultUncaughtExceptionHandler();

			Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
				public void uncaughtException(Thread thread, Throwable ex) {

					StringBuilder log=new StringBuilder();

					try {
						Process process = Runtime.getRuntime().exec("logcat -d");
						BufferedReader bufferedReader = new BufferedReader(
								new InputStreamReader(process.getInputStream()));


						String line;
						while ((line = bufferedReader.readLine()) != null) {
							log.append(line);

						}
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						ex.printStackTrace(pw);
						Context context = FindMyPlace.getAppContext();
						OSUtil.launchMail(context, new String[]{context.getResources().getString(R.string.debug_mail_address)},"crash log - " + OSUtil.getPackageName(),sw.toString() + "\n" + OSUtil.getDeviceData(context) , false);
						Log.e("TestApplication", "Uncaught exception is: " + log.toString());
					} catch (IOException e) {
						Log.d("TestApplication", e.getMessage());
					}



					// log it & phone home.
					androidDefaultUEH.uncaughtException(thread, ex);
				}
			};
			Thread.setDefaultUncaughtExceptionHandler(handler);
		}


	}
}
