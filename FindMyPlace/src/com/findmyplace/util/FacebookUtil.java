package com.findmyplace.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.Session.NewPermissionsRequest;
import com.facebook.SessionState;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.findmyplace.Facebook.FacebookAction;
import com.findmyplace.Facebook.FacebookPermissions;


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
	public static FacebookPermissions getApplicationFBPermissions(){

		FacebookPermissions result = new FacebookPermissions();
		result.setPublishPermissions(PUBLISH_PERMISSIONS);

		return result;
	}

	public static boolean publishPermissionRequestHasChanged(Session session, FacebookPermissions newPermissions){
		boolean result = false;
		if(newPermissions.getPublishPermissions()!= null){
			result = !isSubsetOf(newPermissions.getPublishPermissions(), session.getPermissions());
		}
		return result;

	}

	public static boolean readPermissionRequestHasChanged(Session session, FacebookPermissions newPermissions){
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
	
	public static void postFeedTofacebook(Context context){

		Session session = Session.getActiveSession();
		if(!isTokenValid()){
			session.openForRead(new Session.OpenRequest((Activity) context));
		}
	}
	

	//this is facebook session listener
	public  static class SessionStatusCallback implements Session.StatusCallback {

		Context context;
		Session.StatusCallback callbackDelegate;
		boolean hasPendingPublishPermissions = true;
		boolean hasPendingReadPermissions = true;
		
		public SessionStatusCallback(Context context,Session.StatusCallback callbackDelegate){
			this.context = context;
			this.callbackDelegate = callbackDelegate;
		}
		
		public SessionStatusCallback(Context context,Session.StatusCallback callbackDelegate,String action){
			this.context = context;
			this.callbackDelegate = callbackDelegate;
		}

		@Override
		public void call(Session session, SessionState state,Exception exception) {

			switch (state) {
			case OPENED:
				Log.d("APFacebookAuth","session OPENED");
				Log.d("APFacebookAuth","onComplete ");
				Log.d("APFacebookAuth","permissions " + session.getPermissions());

				
				FacebookUtil.setFBAuthToken(context, session.getAccessToken());
				Log.d("APFacebookAuth","access token is " + session.getAccessToken());
				FacebookUtil.setFBTokenExpiration(context,session.getExpirationDate().getTime());
				
				if(hasPendingPublishPermissions && FacebookUtil.publishPermissionRequestHasChanged(session, FacebookUtil.getApplicationFBPermissions())){
					session.requestNewPublishPermissions(new NewPermissionsRequest((Activity) context, FacebookUtil.PUBLISH_PERMISSIONS));
					hasPendingPublishPermissions = false;
				}
				else if(hasPendingReadPermissions && FacebookUtil.readPermissionRequestHasChanged(session, FacebookUtil.getApplicationFBPermissions())){
					session.requestNewReadPermissions(new NewPermissionsRequest((Activity) context, FacebookUtil.EXTENDED_READ_PERMISSIONS));
					hasPendingReadPermissions = false;
				}
				break;

			case OPENED_TOKEN_UPDATED:
				Log.d("APFacebookAuth","session OPENED_TOKEN_UPDATED");
				if(!hasPendingPublishPermissions && !hasPendingReadPermissions){
//					onSuccefullyFinished();
				}
				else if(hasPendingPublishPermissions){
					session.requestNewPublishPermissions(new NewPermissionsRequest((Activity) context, FacebookUtil.PUBLISH_PERMISSIONS));
					hasPendingPublishPermissions = false;
				}
				else if (hasPendingReadPermissions){
					session.requestNewReadPermissions(new NewPermissionsRequest((Activity) context, FacebookUtil.EXTENDED_READ_PERMISSIONS));
					hasPendingReadPermissions = false;
				}

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

		}
	}


	public static void postLocation(Context context,String location,String description,String name) {
		FacebookAction action = new FacebookAction();
		action.setName(name);
		action.setDescription(description);
		action.setName(name);
		String imageURL = MapRouteUtil.getLocationStaticImageURL(location);
		action.setPicture(imageURL);
		postAction(action,context);
	}

	private static void postAction(FacebookAction action,Context context) {
		if(isTokenValid()){
			 Bundle params = new Bundle();
			    params.putString("name", action.getName());
			    params.putString("caption", action.getCaption());
			    params.putString("description", action.getDescription());
			    params.putString("link", "https://developers.facebook.com/android");
			    params.putString("picture", action.getPicture());

			    WebDialog feedDialog = (
			        new WebDialog.FeedDialogBuilder(context,
			            Session.getActiveSession(),
			            params))
			        .setOnCompleteListener(new OnCompleteListener() {

			            @Override
			            public void onComplete(Bundle values,
			                FacebookException error) {
			                if (error == null) {
			                    // When the story is posted, echo the success
			                    // and the post Id.
			                    final String postId = values.getString("post_id");
			                    if (postId != null) {
//			                        Toast.makeText(getActivity(),
//			                            "Posted story, id: "+postId,
//			                            Toast.LENGTH_SHORT).show();
			                    } else {
			                        // User clicked the Cancel button
//			                        Toast.makeText(getActivity().getApplicationContext(), 
//			                            "Publish cancelled", 
//			                            Toast.LENGTH_SHORT).show();
			                    }
			                } else if (error instanceof FacebookOperationCanceledException) {
			                    // User clicked the "x" button
//			                    Toast.makeText(getActivity().getApplicationContext(), 
//			                        "Publish cancelled", 
//			                        Toast.LENGTH_SHORT).show();
			                } else {
//			                    // Generic, ex: network error
//			                    Toast.makeText(getActivity().getApplicationContext(), 
//			                        "Error posting story", 
//			                        Toast.LENGTH_SHORT).show();
			                }
			            }

			        })
			        .build();
			    feedDialog.show();
		}
		
	}


	
}
