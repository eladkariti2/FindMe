package com.findmyplace.adapters;

import java.util.List;
import java.util.zip.Inflater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findmyplace.R;
import com.findmyplace.model.APModel;
import com.findmyplace.util.database.DataBaseUtil;

public class UserLocationsAdapter extends BaseAdapter {

	Context _context;
	List<APModel>  _data;
	LayoutInflater _infalater ;
	
	public UserLocationsAdapter (Context context,List<APModel> data){
		_context = context;
		_data = data;
		_infalater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return _data.size();
	}

	@Override
	public Object getItem(int position) {
		return _data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = _infalater.inflate(R.layout.location_item, parent, false);
		}
		
		
		
		TextView addressTextView = (TextView)convertView.findViewById(R.id.address);
		TextView descriptionTextView = (TextView)convertView.findViewById(R.id.decription);
		View deleteIconContainer = convertView.findViewById(R.id.delete_icon_container);
		
		convertView.setTag(getItem(position));
		deleteIconContainer.setTag(position);
		
		String address = _data.get(position).getAddress();
		String description = _data.get(position).getLocationDescription();
		
		addressTextView.setText(address);
		descriptionTextView.setText(description);
		
		
		deleteIconContainer.setOnClickListener(deleteClickListenr);
		
		convertView.setTag(getItem(position));
		
		return convertView;
	}
	
	
	OnClickListener deleteClickListenr = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int position  = (Integer)v.getTag();
			
			String id = ((APModel)getItem(position)).getID();
			if(DataBaseUtil.deleteLocation(_context,id))
			{
				_data.remove(position);
				notifyDataSetChanged();
				
				Toast.makeText(_context, _context.getString(R.string.location_item_remove_location_success),Toast.LENGTH_SHORT).show();
			}
			else{
				Toast.makeText(_context, _context.getString(R.string.location_item_remove_location_success),Toast.LENGTH_SHORT).show();
			}
		}
	};

}
