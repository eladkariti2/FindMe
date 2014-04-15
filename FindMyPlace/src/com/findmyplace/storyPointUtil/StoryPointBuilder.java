package com.findmyplace.storyPointUtil;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.findmyplace.model.MapModel.RMDirection;
import com.findmyplace.model.MapModel.RMLegs;
import com.findmyplace.model.MapModel.RMPath;
import com.findmyplace.util.MapUtil;
import com.findmyplace.util.StringUtil;
import com.google.android.gms.maps.model.LatLng;

public class StoryPointBuilder {

	public final static String IS_FULL_ROUTE = "isFullRoute";
	
	public static List<StoryPointHolder> getStoryPointHolder(Context context,RMDirection direction){
		
		  List<StoryPointHolder> story = new ArrayList<StoryPointHolder>();
			
			for (RMLegs legs : direction.getLegsList()) {
				StoryPointHolder legStoryPoint = new StoryPointHolder();
				legStoryPoint.setDistance(legs.getDistance());
				legStoryPoint.setDuration(legs.getDuration());
				legStoryPoint.setExtention(IS_FULL_ROUTE,"true");
				story.add(legStoryPoint);
				
				for (RMPath path : legs.getPathsList()) {
					StoryPointHolder storyPoint = new StoryPointHolder();
					storyPoint.setHtmlText(StringUtil.parseHtml(path.getHtmlText()));
					storyPoint.setDistance(path.getDistance());
					storyPoint.setDuration(path.getDuration());
					LatLng location = path.getPath().get(0).getLatLng();
					storyPoint.setTitle(MapUtil.getAddrres(context,location.latitude,location.longitude));
					storyPoint.setLocation(location);
					story.add(storyPoint);
				}

			}	    
			
		return story;
	}

	
}
