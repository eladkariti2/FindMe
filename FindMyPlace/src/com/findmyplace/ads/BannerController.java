package com.findmyplace.ads;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class BannerController {

	private ViewGroup mBannerContainer ;
	private String mAdID ;
	private Context mContext;
	private APBannerI bannerView;


	public BannerController(Context context,ViewGroup bannerContainer,String adId){
		mBannerContainer = bannerContainer;
		mAdID = adId;
		mContext = context;

		initBannerView();
	}


	private void initBannerView() {
		bannerView  = new DoubleClickBannerView(mContext,mAdID);
	}


	public void displayBanner(){
		mBannerContainer.removeAllViews();
		mBannerContainer.addView(bannerView.getBannerView());
		bannerView.startAd();
	}

	public void pauseBanners() {
		bannerView.pauseAd();	
	}

	public void stopBanners() {
		bannerView.stopAd();
	}

	public void resumeBanners() {
		bannerView.resumeAd();
	}

	public void refreshBanners() {
		bannerView.refresh();

	}

	public void distroyBanners() {
		bannerView.destroyAD();

	}
	
}
