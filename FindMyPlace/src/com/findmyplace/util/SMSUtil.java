//package com.findmyplace.util;
//
//import android.app.Activity;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.telephony.SmsManager;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.example.findmyplace.R;
//import com.findmyplace.fragment.UserRoutsFragment;
//
//public class SMSUtil {
//
//	private void sendMessage(String number, String location, String description, String address) {
//		Log.v("UserRoutsFragment","phoneNumber" + number);
//		String message = getActivity().getString(R.string.share_location_message);
//		String findMeScheme = getActivity().getString(R.string.find_me_scheme);
//		
//		message += address  + description ;
//		
//		Log.v("UserRoutsFragment", "Message = " + message + ", Lenght = " + message.length());
//              
//	    String SENT = "SMS_SENT";
//	    String DELIVERED = "SMS_DELIVERED";
//		PendingIntent sentPI = PendingIntent.getBroadcast(UserRoutsFragment.this.getActivity(), 0,
//                new Intent(SENT), 0);
//
//        PendingIntent deliveredPI = PendingIntent.getBroadcast(UserRoutsFragment.this.getActivity(),
//                0, new Intent(DELIVERED), 0);
//
//
//        getActivity().registerReceiver(new BroadcastReceiver() {
//
//            @Override
//            public void onReceive(Context arg0, Intent arg1) {
//                switch (getResultCode()) {
//                case Activity.RESULT_OK:
//                    Toast.makeText(UserRoutsFragment.this.getActivity(), "SMS sent",
//                            Toast.LENGTH_SHORT).show();
//                    break;
//                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                    Toast.makeText(UserRoutsFragment.this.getActivity(), "Generic failure",
//                            Toast.LENGTH_SHORT).show();
//                    break;
//                case SmsManager.RESULT_ERROR_NO_SERVICE:
//                    Toast.makeText(UserRoutsFragment.this.getActivity(), "No service",
//                            Toast.LENGTH_SHORT).show();
//                    break;
//                case SmsManager.RESULT_ERROR_NULL_PDU:
//                    Toast.makeText(UserRoutsFragment.this.getActivity(), "Null PDU",
//                            Toast.LENGTH_SHORT).show();
//                    break;
//                case SmsManager.RESULT_ERROR_RADIO_OFF:
//                    Toast.makeText(UserRoutsFragment.this.getActivity(), "Radio off",
//                            Toast.LENGTH_SHORT).show();
//                    break;
//
//                }
//            }
//        }, new IntentFilter(SENT));
//        // ---when the SMS has been delivered---
//        getActivity().registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context arg0, Intent arg1) {
//                switch (getResultCode()) {
//                case Activity.RESULT_OK:
//                    Toast.makeText(UserRoutsFragment.this.getActivity(), "SMS delivered",
//                            Toast.LENGTH_SHORT).show();
//                    break;
//                case Activity.RESULT_CANCELED:
//                    Toast.makeText(UserRoutsFragment.this.getActivity(), "SMS not delivered",
//                            Toast.LENGTH_SHORT).show();
//                    break;
//                }
//            }
//        }, new IntentFilter(DELIVERED));
//
//  
//		
//        
//		SmsManager sms = SmsManager.getDefault();
//		sms.sendTextMessage(number, null, message, sentPI, deliveredPI);
//	}
//
//	
//}
