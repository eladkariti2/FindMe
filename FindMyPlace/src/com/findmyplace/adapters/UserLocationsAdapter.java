package com.findmyplace.adapters;

import java.util.List;
import java.util.zip.Inflater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findmyplace.R;
import com.facebook.android.Facebook;
import com.findmyplace.fragment.UserRoutsFragment;
import com.findmyplace.model.APModel;
import com.findmyplace.util.APConstant;
import com.findmyplace.util.FacebookUtil;
import com.findmyplace.util.MapRouteUtil;
import com.findmyplace.util.OSUtil;
import com.findmyplace.util.StringUtil;
import com.findmyplace.util.database.DataBaseUtil;
import com.google.android.gms.maps.model.LatLng;

public class UserLocationsAdapter extends BaseAdapter {

	Context _context;
	Fragment _fragment;
	List<APModel>  _data;
	LayoutInflater _infalater ;


	public UserLocationsAdapter (Context context,Fragment fragmnet,List<APModel> data){
		_context = context;
		_data = data;
		_fragment = fragmnet;
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
		View shareIconContainer = convertView.findViewById(R.id.share_icon_container);
		View sendMessageIconContainer = convertView.findViewById(R.id.send_icon_container);
		 ImageView locationImg = (ImageView)convertView.findViewById(R.id.image);
		
		
		convertView.setTag(getItem(position));
		deleteIconContainer.setTag(position);
		sendMessageIconContainer.setTag(position);
		shareIconContainer.setTag(position);
		
		String address = _data.get(position).getAddress();
		String description = _data.get(position).getLocationDescription();

		addressTextView.setText(address);
		descriptionTextView.setText(description);

		String imaString = _data.get(position).getImage();
		if(!StringUtil.isEmpty(imaString) && OSUtil.hasExternalStoragePrivateFile(_context, imaString)){
			locationImg.setImageBitmap(OSUtil.getLocationPicture(_context, _data.get(position).getImage()));
		}

		shareIconContainer.setOnClickListener(shareClickListenr);
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


	OnClickListener sendMessageClickListenr = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int position  = (Integer)v.getTag();
			APModel model = ((APModel)getItem(position));
			
			((UserRoutsFragment)_fragment).updateShareModel(model);
			Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
			((UserRoutsFragment)_fragment).startActivityForResult(intent, APConstant.PICK_CONTACT);
			
			
		}
	};
	
	OnClickListener shareClickListenr = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int position  = (Integer)v.getTag();
			APModel model = ((APModel)getItem(position));
			String location ="" + model.getLatitude() + "," + model.getLongitude();
			FacebookUtil.postLocation(_context,location,model.getLocationDescription(),model.getAddress());			
		}
	};
}
