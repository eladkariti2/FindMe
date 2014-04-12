package com.findmyplace.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class OSUtil {

	public static void setLocale(Context context,String localString){
		Locale locale = new Locale(localString);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		context.getResources().updateConfiguration(config,
				context.getResources().getDisplayMetrics());
		
	}
	
	public static boolean saveLocationPicture(Context context, String path,Bitmap imageBitmap){
		 // Create a path where we will place our private file on external
	    // storage.
	    File file = new File(context.getExternalFilesDir(null), path);

	    try {
	        OutputStream os = new FileOutputStream(file);
	        
	        imageBitmap.compress(Bitmap.CompressFormat.PNG, 50, os);
	        os.close();
	        
	    } catch (IOException e) {
	        // Unable to create file, likely because external storage is
	        // not currently mounted.
	        Log.w("ExternalStorage", "Error writing " + file, e);
	    }
	    
	    return true;
	}
	
	public static Bitmap getLocationPicture(Context context, String path){
	
	    File file = new File(context.getExternalFilesDir(null), path);
	    BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
	    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), options);
	    
	    return bitmap;
	}
	
	public static boolean hasExternalStoragePrivateFile(Context context, String path) {
	    
		boolean result = false;
		// Get path for the file on external storage.  If external
	    // storage is not currently mounted this will fail.
	    File file = new File(context.getExternalFilesDir(null), path);
	    if (file != null) {
	        result = file.exists();
	    }
	    return result;
	}
}
