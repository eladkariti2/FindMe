package com.findmyplace.ads;

import android.view.View;

public interface APBannerI {

	public void startAd();

	public void stopAd();

	public void pauseAd();

	public void resumeAd();

	public void destroyAD();
	
	public View getBannerView();

	public void refresh();
}
