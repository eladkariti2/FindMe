package com.findmyplace.util;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.text.Html;
import android.util.Log;
import android.view.WindowManager;

import com.findmyplace.FindMyPlace;

public class OSUtil {

	
	
	private static final String TAG = "OSUtil";

	public static void setLocale(Context context,String localString){
		Locale locale = new Locale(localString);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		context.getResources().updateConfiguration(config,
				context.getResources().getDisplayMetrics());
		
	}
	
	public static void launchMail(Context context,String[] addresses, String subject, String body, boolean isHtml) {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("text/html");
		if(addresses != null){
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, addresses);
		}
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, isHtml ? Html.fromHtml(body) : body);
		
		startEmailActivity(context, emailIntent);
	}
	
	private static void startEmailActivity(Context context, Intent emailIntent) {
		try{
			if(!(context instanceof Activity)){
				emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			}
			context.startActivity(emailIntent);
		}
		catch (Exception e){
			Log.e(TAG, "Error when trying to send mail - startEmailActivity");
		}
	}
	
	public static String getAppVersion(Context context) {
		String result = "";
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			result = info.versionName;
		} catch (Exception e) {

		}
		return result;
	}
	
	public static String getDeviceModel() {
		return android.os.Build.MODEL;
	}
	
	public static int getAppVersionCode(Context context) {
		int result = 0;
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			result = info.versionCode;
		} catch (Exception e) {

		}
		return result;
	}
	
	public static int getAPIVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}
	
	public static int getScreenHeight(Context context) {
		return ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
				.getHeight();
	}
	
	public static int getScreenWidth(Context context) {
		return ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
				.getWidth();
	}
	
	public static String getDeviceData(Context context) {
		StringBuffer deviceData = new StringBuffer();
	
		deviceData.append("Package: ").append(OSUtil.getPackageName(context))
				.append("\n");
		deviceData.append("Version name: ")
				.append(OSUtil.getAppVersion(context)).append("\n");
		deviceData.append("Version code: ")
				.append(OSUtil.getAppVersionCode(context)).append("\n");
		deviceData.append("Device model: ").append(OSUtil.getDeviceModel())
				.append("\n");
		deviceData.append("OS API Version: ").append(OSUtil.getAPIVersion())
		.append("\n");
		deviceData.append("Screen size: ")
				.append(OSUtil.getScreenWidth(context)).append("x")
				.append(OSUtil.getScreenHeight(context)).append("\n");
		deviceData.append("Screen density: ")
				.append(OSUtil.getScreenDensity(context)).append("\n");
		return deviceData.toString();
	}
	
	public static float getScreenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}
	
	
	
	public static String getPackageName() {
		return getPackageName(FindMyPlace.getAppContext());
	}
	
	public static String getPackageName(Context context) {
		return context.getPackageName();
	}
	

}
