package com.findmyplace.adapters;

import java.util.List;

import com.example.findmyplace.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RouteStoryAdapter extends BaseAdapter {
	
	Context context ;
	List<String> data;
	LayoutInflater infalater ;
	
	public RouteStoryAdapter(Context context , List<String> story){
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
		
		String text = (String)getItem(position);
		storyText.setText(text);
		
		return convertView;
	}

}
