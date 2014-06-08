package com.findmyplace.ads;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

public class DoubleClickBannerView implements APBannerI  {

	private static final String TAG = "DoubleClickBannerView";
	private PublisherAdView adView;
	
	public DoubleClickBannerView(Context context,String adID){
		 adView = new PublisherAdView(context);
		 adView.setAdUnitId(adID);
		 adView.setAdSizes(AdSize.BANNER);
	}
	
	@Override
	public void startAd() {
		if(adView != null){
			adView.loadAd(new PublisherAdRequest.Builder().build());
		}
		else{
			Log.e(TAG, "Ad view is null");
		}
		
	}

	@Override
	public void stopAd() {
		
	}

	@Override
	public void pauseAd() {
		// TODO Auto-generated method stub
		if(adView != null){
			adView.pause();
		}
		else{
			Log.e(TAG, "Ad view is null");
		}
	}

	@Override
	public void resumeAd() {
		if(adView != null){
			adView.resume();
		}
		else{
			Log.e(TAG, "Ad view is null");
		}
		
	}

	@Override
	public View getBannerView() {
		// TODO Auto-generated method stub
		return adView;
	}

	@Override
	public void refresh() {
		stopAd();
		startAd();
	}

	@Override
	public void destroyAD() {
		if(adView != null){
			adView.destroy();
		}
		else{
			Log.e(TAG, "Ad view is null");
		}
		
	}

}
