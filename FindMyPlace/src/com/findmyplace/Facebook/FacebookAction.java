package com.findmyplace.Facebook;

import android.content.Context;
import android.os.Bundle;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.findmyplace.inferface.AsyncTaskListener;

public class FacebookAction {

	FacebookFeed mFeed;
	AsyncTaskListener<Boolean> mListener;
	Context mContext;
	
	public FacebookAction(Context context,FacebookFeed feed){
		this(context,feed,null);
	}
	
	public FacebookAction(Context context,FacebookFeed feed,AsyncTaskListener<Boolean> listener){
		mFeed = feed;
		mListener = listener;
		mContext = context;
	}
	
	public void execute(){
		 Bundle params = new Bundle();
		    params.putString("name", mFeed.getName());
		    params.putString("caption", mFeed.getCaption());
		    params.putString("description",mFeed.getDescription());
		    params.putString("link", "https://developers.facebook.com/android");
		    params.putString("picture", mFeed.getPicture());

		    WebDialog feedDialog = (
		        new WebDialog.FeedDialogBuilder(mContext,
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
//		                        Toast.makeText(getActivity(),
//		                            "Posted story, id: "+postId,
//		                            Toast.LENGTH_SHORT).show();
		                    } else {
		                        // User clicked the Cancel button
//		                        Toast.makeText(getActivity().getApplicationContext(), 
//		                            "Publish cancelled", 
//		                            Toast.LENGTH_SHORT).show();
		                    }
		                } else if (error instanceof FacebookOperationCanceledException) {
		                    // User clicked the "x" button
//		                    Toast.makeText(getActivity().getApplicationContext(), 
//		                        "Publish cancelled", 
//		                        Toast.LENGTH_SHORT).show();
		                } else {
//		                    // Generic, ex: network error
//		                    Toast.makeText(getActivity().getApplicationContext(), 
//		                        "Error posting story", 
//		                        Toast.LENGTH_SHORT).show();
		                }
		            }

		        })
		        .build();
		    feedDialog.show();
	}
}
