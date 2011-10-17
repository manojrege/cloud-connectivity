package com.connection.notification;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.connection.wifi.WiFiScanReceiver;
import com.google.android.c2dm.C2DMessaging;
import com.oauth2.Auth;

public class RegisterActivity extends Activity {
	/** Called when the activity is first created. */
	C2DMReceiver c2dm;
	//WiFiScanReceiver wifiscan;
	public static String user;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		c2dm=new C2DMReceiver(this.getApplication());
		//c2dm.wifi=(WifiManager)getSystemService(Context.WIFI_SERVICE);
		//wifiscan=new WiFiScanReceiver(this.c2dm);
		
		// Now do something smart based on the information
		if(c2dm.receiver==null)
		{
			c2dm.receiver=new WiFiScanReceiver(this.c2dm);
			registerReceiver(c2dm.receiver, new IntentFilter(WifiManager.ACTION_PICK_WIFI_NETWORK));
		}
	}

	public void register(View view) {
		Log.e("Super", "Starting registration");
		Toast.makeText(this, "Starting", Toast.LENGTH_LONG).show();
		EditText text = (EditText) findViewById(R.id.editText1);
		user=text.getText().toString();
		C2DMessaging.register(this, text.getText().toString());
		//startActivity(new Intent(RegisterActivity.this,Auth.class));
        //finish();
		//Auth auth=new Auth();
		//auth.AuthRequest();
	}
	/*@Override
	public void onStop()
	{
		//C2DMessaging.unregister(this);
		//unregisterReceiver(c2dm.receiver);
	}*/
	/*@Override
	public void onDestroy()
	{
		
		C2DMessaging.unregister(this);
		unregisterReceiver(c2dm.receiver);
	}
}*/
}
			