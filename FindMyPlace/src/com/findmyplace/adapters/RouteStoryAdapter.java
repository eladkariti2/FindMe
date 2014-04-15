package com.findmyplace.adapters;

import java.util.List;

import com.example.findmyplace.R;
import com.findmyplace.storyPointUtil.StoryPointBuilder;
import com.findmyplace.storyPointUtil.StoryPointHolder;
import com.findmyplace.util.StringUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RouteStoryAdapter extends BaseAdapter {
	
	private static final int MAIN_ROUTE = 0;
	private static final int ROUTE_ITEM = 1;
	
	Context context ;
	List<StoryPointHolder> data;
	LayoutInflater infalater ;
	
	public RouteStoryAdapter(Context context , List<StoryPointHolder> story){
		this.context = context;
		data = story;
		infalater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = infalater.inflate(R.layout.route_story_item, parent, false);
		}
		
		TextView storyText = (TextView)convertView.findViewById(R.id.route_story_text);
		TextView duration = (TextView)convertView.findViewById(R.id.route_story_duration);
		TextView distance = (TextView)convertView.findViewById(R.id.route_story_distance);
		StoryPointHolder holder = (StoryPointHolder)getItem(position);
		
		if (getItemViewType(position) == MAIN_ROUTE)
		{
			duration.setText(holder.getDuration());
			distance.setText(holder.getDistance());
			storyText.setVisibility(View.GONE);
		}
		else{
			storyText.setVisibility(View.VISIBLE);
			storyText.setText(holder.getHtmlText());
			duration.setText(holder.getDuration());
			distance.setText(holder.getDistance());
		}
		convertView.setTag(holder);
		
		return convertView;
	}

	@Override
	public int getItemViewType(int position) {
		StoryPointHolder item = (StoryPointHolder)getItem(position);
		if("true".equals(item.getExtention(StoryPointBuilder.IS_FULL_ROUTE, "false"))){
			return MAIN_ROUTE;
		}
		return ROUTE_ITEM;
	}

		
}
