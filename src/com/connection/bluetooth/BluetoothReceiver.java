package com.connection.bluetooth;

import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.ArrayAdapter;

public class BluetoothReceiver {
	

BluetoothAdapter adapter;
	BluetoothReceiver()
{
	
}

public void setBluetooth()
{
	adapter= BluetoothAdapter.getDefaultAdapter();
	if(adapter==null)
	{
		System.out.println("Adapter could not be enabled");
	}
	if(adapter.isEnabled())
	{
		adapter.enable();
		while(adapter.getState()!=BluetoothAdapter.STATE_ON);
	}
}

@SuppressWarnings("unchecked")
public Set getPairedDevices()
{
	//@SuppressWarnings("rawtypes")
	///ArrayAdapter array =new ArrayAdapter();
	Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
	return pairedDevices;
	//for (BluetoothDevice device: pairedDevices)
	//{
	//	array.add(device.getName()+device.getAddress());
	//}
}

public void BluetoothDiscovery()
{
BroadcastReceiver receiver=new BroadcastReceiver()
{
	public void onReceive(Context context, Intent intent)
	{
		String action=intent.getAction();
		if(action.equals(BluetoothDevice.ACTION_FOUND))
		{
			BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		}
	}
};
IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//registerReceiver(receiver, filter); // Don't forget to unregister during onDestroy
}
}

