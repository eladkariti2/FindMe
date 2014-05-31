package com.findmyplace.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.Session;
import com.facebook.Session.NewPermissionsRequest;
import com.facebook.SessionState;


public class FacebookUtil {
	public static final List<String> PUBLISH_PERMISSIONS= Arrays.asList(new String[]{"publish_actions" , "publish_stream", "manage_pages"});
	public static final List<String> EXTENDED_READ_PERMISSIONS= Arrays.asList(new String[]{ "friends_online_presence", "xmpp_login"});


	public static final String TAG = "FacebookUtil";

	public static String getFBAuthToken(Context context){
		PreferenceUtil prefUtil = PreferenceUtil.getInstance();
		return prefUtil.getStringPref(APConstant.FACEBOOK_ACCESS_TOKEN_KEY, null);

	}

	public static void setFBAuthToken(Context context, String value){
		PreferenceUtil prefUtil = PreferenceUtil.getInstance();
		prefUtil.setStringPref(APConstant.FACEBOOK_ACCESS_TOKEN_KEY, value);
	}

	public static long getFBTokenExpiration(Context context){
		PreferenceUtil prefUtil = PreferenceUtil.getInstance();
		return prefUtil.getLongPref(APConstant.FACEBOOK_ACCESS_EXPIRATION_KEY, 0);

	}

	public static void setFBTokenExpiration(Context context, long value){
		PreferenceUtil prefUtil = PreferenceUtil.getInstance();
		prefUtil.setLongPref(APConstant.FACEBOOK_ACCESS_EXPIRATION_KEY, value);
	}

	// Which permissions should be requested
	public static FBPermissions getApplicationFBPermissions(){

		FBPermissions result = new FBPermissions();
		result.setPublishPermissions(PUBLISH_PERMISSIONS);

		return result;
	}

	public static boolean publishPermissionRequestHasChanged(Session session, FBPermissions newPermissions){
		boolean result = false;
		if(newPermissions.getPublishPermissions()!= null){
			result = !isSubsetOf(newPermissions.getPublishPermissions(), session.getPermissions());
		}
		return result;

	}

	public static boolean readPermissionRequestHasChanged(Session session, FBPermissions newPermissions){
		boolean result = false;
		if(newPermissions.getReadPermissions()!= null){
			result = !isSubsetOf(newPermissions.getReadPermissions(), session.getPermissions());
		}
		return result;

	}
	
	private static boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isTokenValid(){

		Session session = Session.getActiveSession();
		return session != null && session.isOpened();
	}
	
	public static boolean isTokenValid(List<String> neededPermissions){

		Session session = Session.getActiveSession();
		return session != null && session.isOpened() && isSubsetOf(neededPermissions, session.getPermissions());
	}
	
	public static void loginTofacebook(Context context){

		Session session = Session.getActiveSession();
		if(!isTokenValid()){
			session.openForRead(new Session.OpenRequest((Activity) context));
		}
	}

	//this is facebook session listener
	public  static class SessionStatusCallback implements Session.StatusCallback {

		Context context;
		Session.StatusCallback callbackDelegate;

		public SessionStatusCallback(Context context,Session.StatusCallback callbackDelegate){
			this.context = context;
			this.callbackDelegate = callbackDelegate;
		}

		@Override
		public void call(Session session, SessionState state,Exception exception) {
			switch (state) {
			case OPENED:
				Log.d(TAG,"session OPENED");
				Log.d(TAG,"onComplete ");
				Log.d(TAG,"permissions " + session.getPermissions());

				FacebookUtil.setFBAuthToken(context, session.getAccessToken());
				Log.d("APFacebookAuth","access token is " + session.getAccessToken());

				FacebookUtil.setFBTokenExpiration(context,session.getExpirationDate().getTime());

				if(FacebookUtil.publishPermissionRequestHasChanged(session, FacebookUtil.getApplicationFBPermissions())){
					session.requestNewPublishPermissions(new NewPermissionsRequest((Activity) context, FacebookUtil.PUBLISH_PERMISSIONS));
				}
				else if(FacebookUtil.readPermissionRequestHasChanged(session, FacebookUtil.getApplicationFBPermissions())){
					session.requestNewReadPermissions(new NewPermissionsRequest((Activity) context, FacebookUtil.EXTENDED_READ_PERMISSIONS));
				}
				break;

			case OPENED_TOKEN_UPDATED:
				Log.d("APFacebookAuth","session OPENED_TOKEN_UPDATED");
				break;
			case CLOSED:
				Log.d("APFacebookAuth","session CLOSED");
				break;
			case CLOSED_LOGIN_FAILED:
				Log.d("APFacebookAuth","CLOSED_LOGIN_FAILED state " + exception.getMessage());
				break;
			case CREATED:
				Log.d("APFacebookAuth","session CREATED");
				break;
			case CREATED_TOKEN_LOADED:
				Log.d("APFacebookAuth","session CREATED_TOKEN_LOADED");
				break;
			case OPENING:
				Log.d("APFacebookAuth","session OPENING");
				break;

			}

			if(callbackDelegate != null){
				callbackDelegate.call(session, state, exception);
			}

		}
	}


	public static class FBPermissions{
		private List<String> readPermissions;
		private List<String> publishPermissions;

		public List<String> getPermissions(){
			ArrayList<String> permissions = new ArrayList<String>();
			if(publishPermissions!= null){
				permissions.addAll(publishPermissions);
			}
			if(readPermissions!= null){
				permissions.addAll(readPermissions);
			}

			return permissions;
		}

		public List<String> getReadPermissions() {
			return readPermissions;
		}
		public void setReadPermissions(List<String> readPermissions) {
			this.readPermissions = readPermissions;
		}
		public List<String> getPublishPermissions() {
			return publishPermissions;
		}
		public void setPublishPermissions(List<String> publishPermissions) {
			this.publishPermissions = publishPermissions;
		}


	}
}
