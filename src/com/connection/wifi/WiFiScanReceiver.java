package com.connection.wifi;
import com.connection.notification.C2DMReceiver;
import com.google.android.c2dm.C2DMBroadcastReceiver;

import android.content.Context;
import android.content.Intent;
public class WiFiScanReceiver extends C2DMBroadcastReceiver {

	
C2DMReceiver cr;
	public WiFiScanReceiver(C2DMReceiver instance)
	{
		//super();
		this.cr=instance;
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		//List<ScanResult> results = wifi.getScanResults();
		cr.wifi.addNetwork(cr.cfg);
		cr.wifi.reconnect();
	}
	
	//Returns a valid wificonfiguration object when the networks parameters are sent to it
	//
	public void configure(String parameters[])
	{
		//WifiConfiguration cfg = new WifiConfiguration();
		//System.out.println(parameters[0]);
		//cfg.SSID=parameters[0];
		//cfg.hiddenSSID=Boolean.parseBoolean(parameters[1]);
		//cfg.preSharedKey=parameters[2];
	}
}