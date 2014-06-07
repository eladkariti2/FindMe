package com.findmyplace.activites;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.Session;
import com.facebook.Session.NewPermissionsRequest;
import com.facebook.SessionState;
import com.findmyplace.Facebook.FacebookAction;
import com.findmyplace.util.FacebookUtil;

public class FacebookAuthenticationActivity extends Activity {
	
	
	public static final String TAG = "FacebookAuthenticationActivity";
	private static final String ACTION_KEY = "actionKey";
	
	public static HashMap<String,FacebookAction> fbActionsMap = new HashMap<String, FacebookAction>(0); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		loginToFacebook();
	}
	
	private void loginToFacebook() {
		Session session = Session.getActiveSession();
		if(session != null){
			session.openForRead(new Session.OpenRequest(this).setCallback(new SessionStatusCallback()));
		}
	}

	private void onSuccefullyFinished(){
		FacebookAction action =  fbActionsMap.get(ACTION_KEY);
		if(action != null){
			action.execute();
			removeAction();
		}
	    finish();
	}
	
	private void removeAction() {
		fbActionsMap.remove(ACTION_KEY);
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode,  data);
	}
	
	public  static void StartFacebookAuthenticationActivity(Context context){
		StartFacebookAuthenticationActivity(context,null);
	}
	
	public  static void StartFacebookAuthenticationActivity(Context context,FacebookAction action){
		Intent intent = new Intent(context,FacebookAuthenticationActivity.class);
		
		if(action != null){
			fbActionsMap.put(ACTION_KEY, action);
		}
		
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivity(intent);
	}

	//this is facebook session listener
		private  class SessionStatusCallback implements Session.StatusCallback {

			
			boolean hasPendingPublishPermissions = false;//it's false because  the app don't need more permission if we need to ask for more permission change it's to true.
			boolean hasPendingReadPermissions = false;
		
			
			@Override
			public void call(Session session, SessionState state,Exception exception) {

				switch (state) {
				case OPENED:
					Log.d(TAG,"session OPENED");
					Log.d(TAG,"onComplete ");
					Log.d(TAG,"permissions " + session.getPermissions());

					
					FacebookUtil.setFBAuthToken(FacebookAuthenticationActivity.this, session.getAccessToken());
					Log.d(TAG,"access token is " + session.getAccessToken());
					FacebookUtil.setFBTokenExpiration(FacebookAuthenticationActivity.this,session.getExpirationDate().getTime());
					
					if(hasPendingPublishPermissions && FacebookUtil.publishPermissionRequestHasChanged(session, FacebookUtil.getApplicationFBPermissions())){
						session.requestNewPublishPermissions(new NewPermissionsRequest( FacebookAuthenticationActivity.this, FacebookUtil.PUBLISH_PERMISSIONS));
						hasPendingPublishPermissions = false;
					}
					else if(hasPendingReadPermissions && FacebookUtil.readPermissionRequestHasChanged(session, FacebookUtil.getApplicationFBPermissions())){
						session.requestNewReadPermissions(new NewPermissionsRequest(FacebookAuthenticationActivity.this, FacebookUtil.EXTENDED_READ_PERMISSIONS));
						hasPendingReadPermissions = false;
					}else{
						onSuccefullyFinished();
					}
					break;

				case OPENED_TOKEN_UPDATED:
					Log.d(TAG,"session OPENED_TOKEN_UPDATED");
					if(!hasPendingPublishPermissions && !hasPendingReadPermissions){
						onSuccefullyFinished();
					}
					else if(hasPendingPublishPermissions){
						session.requestNewPublishPermissions(new NewPermissionsRequest(FacebookAuthenticationActivity.this, FacebookUtil.PUBLISH_PERMISSIONS));
						hasPendingPublishPermissions = false;
					}
					else if (hasPendingReadPermissions){
						session.requestNewReadPermissions(new NewPermissionsRequest(FacebookAuthenticationActivity.this, FacebookUtil.EXTENDED_READ_PERMISSIONS));
						hasPendingReadPermissions = false;
					}

					break;
				case CLOSED:
					Log.d(TAG,"session CLOSED");
					break;
				case CLOSED_LOGIN_FAILED:
					Log.d(TAG,"CLOSED_LOGIN_FAILED state " + exception.getMessage());
					
					break;
				case CREATED:
					Log.d(TAG,"session CREATED");
					break;
				case CREATED_TOKEN_LOADED:
					Log.d(TAG,"session CREATED_TOKEN_LOADED");
					break;
				case OPENING:
					Log.d(TAG,"session OPENING");
					break;

				}

			}
		}
}
