package com.findmyplace.fragment;

import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.findmyplace.R;
import com.findmyplace.activites.MainActivity;
import com.findmyplace.adapters.UserLocationsAdapter;
import com.findmyplace.model.APModel;
import com.findmyplace.util.APConstant;
import com.findmyplace.util.database.DataBaseUtil;

public class UserRoutsFragment  extends Fragment {
	
	ListView _list;

	APModel _shareModel = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.location_list_layout, container, false);

		_list = (ListView)view.findViewById(R.id.locations_list);

		return view;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		List<APModel> locations = DataBaseUtil.getAllLocations(getActivity());
		UserLocationsAdapter adapter = new UserLocationsAdapter(getActivity(),this, locations);
		_list.setAdapter(adapter);
		_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				APModel model = (APModel)view.getTag();

				Bundle bundle = new Bundle();
				bundle.putDouble(APConstant.LOCATION_LANTITUDE, model.getLatitude());
				bundle.putDouble(APConstant.LOCATION_LONGITUDE, model.getLongitude());

				Fragment routeFragment = new RouteFragment();

				routeFragment.setArguments(bundle);
				((MainActivity) getActivity()).addFragment(routeFragment, true, "",true);

			}
		});

	}


	
	public void updateShareModel(APModel model){
		_shareModel = model;
	}

	//Handle the picture result.
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK)
		{	
			Uri contactData = data.getData();
			@SuppressWarnings("deprecation")
			Cursor c = getActivity().managedQuery(contactData, null, null, null, null);
			if (c.moveToFirst())
			{
				String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

				String hasPhone =
						c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

				if (hasPhone.equalsIgnoreCase("1")) 
				{
					Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, 
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
					phones.moveToFirst();
					String cNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					
				//	sendMessage(cNumber,_shareModel.getLongitude() + "," + _shareModel.getLatitude(),_shareModel.getLocationDescription(),_shareModel.getAddress());
				}
			}
			
		}

	}
}
