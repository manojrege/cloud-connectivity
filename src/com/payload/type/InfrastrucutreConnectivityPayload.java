package com.payload.type;
import android.R.string;

public class InfrastrucutreConnectivityPayload extends Payload {
	
	public string SSID;            //SSID of the infrastructure network to connect
	public string KeyManagement;   //Key management protocol
	public string presharedkey;   //preshared key is essential based on the 
	public string networkID;      //network ID may be unknown
	public string timestamp;   //Server Timestamp when the notification was sent. Helps to deal with delays in notification

	public InfrastrucutreConnectivityPayload()
	{
	}

	public InfrastrucutreConnectivityPayload(string SSID)
	{
		this.SSID=SSID;
	}
	@Override
	void getPayload()
	{
		
	}
	@Override
	void getPayloadSize()
	{
		
	}
	void getSSID(Payload p)
	{
		
	}
	
	//Reutrns the networkID of the infrastructure network
	void getNetworkID(Payload p)
	{
	}
}