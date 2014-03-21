package com.findmyplace.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class ServerUtil {

	private final static String ACCEPT_LANGUAGE =  "Accept-Language";
	
	 public static String doGet(String urlParam) throws Exception
     {
             URL url = new URL(urlParam);
             String json = null;
             HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
             
             try 
             {
                     Log.i(ServerUtil.class.getSimpleName(), "Url: " + urlParam);
                     
                     InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                     json = readStream(in);
                     
                     Log.i(ServerUtil.class.getSimpleName(), "Json Route: " + json);
             }
             finally 
             {
                     urlConnection.disconnect();
             }

             return json;
     }

	 public static String doGet(String urlParam,boolean addHeaders) throws Exception
     {
             URL url = new URL(urlParam);
             String json = null;
             HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
             if(addHeaders){
            	 urlConnection = addHeadersToConnections(urlConnection);
             }
             try 
             {
                     Log.i(ServerUtil.class.getSimpleName(), "Url: " + urlParam);
                     
                     InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                     json = readStream(in);
                     
                     Log.i(ServerUtil.class.getSimpleName(), "Json Route: " + json);
             }
             finally 
             {
                     urlConnection.disconnect();
             }

             return json;
     }
	 
     private static HttpURLConnection addHeadersToConnections(
			HttpURLConnection urlConnection) {
  
    	 urlConnection.setRequestProperty (ACCEPT_LANGUAGE ,"he");
		 return urlConnection;
	}

	private static String readStream(InputStream in) throws IOException
     {
             BufferedReader r = new BufferedReader(new InputStreamReader(in));
             StringBuilder total = new StringBuilder();
             String line;
             while ((line = r.readLine()) != null) {
                     total.append(line);
             }
             return total.toString();
     }
}
