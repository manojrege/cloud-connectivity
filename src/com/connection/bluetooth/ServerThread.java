package com.connection.bluetooth;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

public class ServerThread extends Thread {
	

long msb=1;
long lsb=10;
BluetoothReceiver receiver;	
private BluetoothServerSocket serversocket;
private final UUID uuid=new UUID(msb,lsb);
	
public ServerThread()
{
	BluetoothServerSocket temp=null;
	try
	{
		receiver.adapter.listenUsingRfcommWithServiceRecord("cloudconnectivity",uuid);
	}
	catch(IOException e)
	{
		serversocket=temp;
		e.printStackTrace();
	}
}
public void run()
{
	
	BluetoothSocket socket=null;
	while(true)
	{
		try
		{
			socket=serversocket.accept();
		}
		catch(IOException e)
		{
			
			break;
		}
		
	}
	if(socket!=null)
	{
		 //manageConnectedSocket(socket);
         try {
			serversocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         //break;
	}
	}
public void cancel() {
    try {
     serversocket.close();
    } catch (IOException e) { }
}
}
