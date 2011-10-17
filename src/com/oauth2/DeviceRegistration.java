package com.oauth2;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DeviceRegistration {
	
	public String registration_id;
	public String user_id;
	public DeviceRegistration(String registration_id, String user_id)
	{
		this.registration_id=registration_id;
		this.user_id=user_id;
	}
	public DeviceRegistration(String user_id)
	{
		this.user_id=user_id;
	}
	
	public void setRegistrtionid(String registrationid)
	{
		this.registration_id=registrationid;
	}
	public String postRegistrationRequest()
	{
		System.out.println("Post Request called");
		String response = null;
		try
		{
		URL url = new URL("http://cloudadhocconnectivity.appspot.com/contextconnectivity");
		StringBuilder data = new StringBuilder();
		data.append("email_id").append("=").append(this.user_id).append("&");
	    data.append("registration_id").append("=").append(this.registration_id);
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setDoOutput(true);
	    connection.setUseCaches(false);
	    connection.setRequestMethod("POST");
	    connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
	    //connection.setRequestProperty("Authorization", "GoogleLogin auth="+this.authtoken);
	    connection.connect();
	    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
	    writer.write(data.toString());
	    writer.close();
	    response=connection.getResponseMessage();
	    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	    	System.out.println("Everything OK..Device Registered");
	    	System.out.println(connection.getResponseMessage());
	    } 
	    else {
	    	// Server returned HTTP error code.
	    	System.out.println("HTTP ERROR CODE "+Integer.toString(connection.getResponseCode()));
	    	System.out.println(connection.getResponseMessage());
	    }
	    
} catch (MalformedURLException e) {
    // ...
} catch (IOException eio) {
    // ...
}
return response;
}}
