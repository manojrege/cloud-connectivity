package com.connection.notification;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
//import android.os.Bundle;
import android.util.Log;
import com.google.android.c2dm.C2DMBaseReceiver;
import com.google.gson.Gson;
import com.oauth2.Auth;
import com.oauth2.DeviceRegistration;
public class C2DMReceiver extends C2DMBaseReceiver {
	
	public WifiManager wifi;
	public WifiConfiguration cfg;
	public String userid;
	String str;
	NotificationManager notmgr;
	BroadcastReceiver receiver;
	DeviceRegistration dr;
	int netid;
	public C2DMReceiver() {
		// Email address currently not used by the C2DM Messaging framework
		super("dummy@google.com");
		this.cfg=new WifiConfiguration();
		//this.notmgr=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
	}
	public C2DMReceiver(Context c) {
		
		// Email address currently not used by the C2DM Messaging framework
		super("dummy@google.com");
		this.cfg=new WifiConfiguration();
		//this.userid=userid;
		//System.out.println(this.userid);
		//this.wifi=(WifiManager)c.getSystemService(Context.WIFI_SERVICE);
	}

	@Override
	public void onRegistered(Context context, String registrationId)
			throws java.io.IOException {
		// The registrationId should be send to your applicatioin server.
		// We just log it to the LogCat view
		// We will copy it from there
		Log.e("C2DM", "Registration ID arrived: Fantastic!!!");
		Log.e("C2DM", registrationId);
		this.userid=RegisterActivity.user;
		System.out.println(this.userid);
		Intent auth = new Intent(getApplicationContext(),Auth.class);
		auth.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		auth.putExtra("registration_id",registrationId);
		startActivity(auth);
		//this.dr=new DeviceRegistration(registrationId,userid);
		//this.dr.setRegistrtionid();
		//this.dr.postRegistrationRequest();
		//Toast.makeText(this,response, Toast.LENGTH_LONG).show();
	};

	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.e("C2DM", "Message: Fantastic!!!");
		long startTime=System.nanoTime();
		// Extract the payload from the message
		Payload payload;
		Bundle extras = intent.getExtras();
		if (extras != null) {
			str=(String) extras.get("payload");
			System.out.println("hello");
			JSONObject obj;
			try {
				obj = new JSONObject(str);
				Gson gson=new Gson();
				payload=new Payload();
				payload=gson.fromJson(obj.toString(),Payload.class);
				//System.out.println("Network-Type:-"+payload.network_type);
				//System.out.println("SSID:-"+payload.ssid);
				//System.out.println("Channel:-"+payload.channel);
				//System.out.println("Key:-"+payload.key);
				
				this.cfg.SSID="\"".concat(payload.ssid).concat("\"");
				//this.cfg.preSharedKey=parameters[1];
				//this.cfg.allowedKeyManagement= new BitSet(Integer.parseInt(parameters[2]));
				this.cfg.status=WifiConfiguration.Status.DISABLED;
				this.cfg.priority=40;
				//Connecting to a open network
				if(payload.network_type.equals("OPEN"))
				{
					this.cfg.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
					this.cfg.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
					this.cfg.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
					this.cfg.allowedAuthAlgorithms.clear();
					this.cfg.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
					this.cfg.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
					this.cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
					this.cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
					this.cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
					this.cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
				}
				//connecting to a WEP enabled network
				else if(payload.network_type.equals("WEP"))
				{
					this.cfg.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
					this.cfg.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
					this.cfg.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
					this.cfg.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
					this.cfg.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
					this.cfg.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
					this.cfg.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
					this.cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
					this.cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
					this.cfg.wepKeys[0] = "\"".concat(payload.key).concat("\"");
					this.cfg.wepTxKeyIndex = 0;
				}
				//connecting to a WPA/WPA2 enabled network
				else
				{
					this.cfg.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
					this.cfg.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
					this.cfg.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
					this.cfg.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
					this.cfg.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
					this.cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
					this.cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
					this.cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
					this.cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
					this.cfg.preSharedKey = "\"".concat(payload.key).concat("\"");
				}
				//System.out.println(cfg.preSharedKey);
				this.cfg.status=WifiConfiguration.Status.ENABLED;
				this.wifi=(WifiManager)getSystemService(Context.WIFI_SERVICE);
				this.wifi.setWifiEnabled(true);
				while(this.wifi.getWifiState()!=WifiManager.WIFI_STATE_ENABLED);
				
				//System.out.println(Long.toString(executionTime));
				int netid=this.wifi.addNetwork(this.cfg);
				//System.out.println(Integer.toString(netid));
				this.wifi.enableNetwork(netid,true);
				//while(this.cfg.status!=WifiConfiguration.Status.CURRENT);
				long executionTime=System.nanoTime()-startTime;
				//int rssi=this.wifi.getConnectionInfo().getRssi();
				System.out.println(Long.toString(executionTime));
				}
			catch (JSONException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	@Override
	public void onError(Context context, String errorId) {
		Log.e("C2DM", "Error occured!!!");
	}
}