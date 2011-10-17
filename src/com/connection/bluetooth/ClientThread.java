package com.connection.bluetooth;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class ClientThread extends Thread{
	
private BluetoothSocket socket;
private final BluetoothDevice device;
long msb=1;
long lsb=10;
private final UUID uuid=new UUID(msb,lsb);
BluetoothReceiver receiver;

public ClientThread(BluetoothDevice mmdevice)
{
	BluetoothSocket tmp=null;
	device=mmdevice;
	try
	{
		tmp=device.createRfcommSocketToServiceRecord(uuid);
	}
	catch(IOException e)
	{
		socket=tmp;
	}
}

public void run()
{
	 receiver.adapter.cancelDiscovery();
	 
     try {
         // Connect the device through the socket. This will block
         // until it succeeds or throws an exception
         socket.connect();
     } catch (IOException connectException) {
         // Unable to connect; close the socket and get out
         try {
             socket.close();
         } catch (IOException closeException) { }
         return;
     }

     // Do work to manage the connection (in a separate thread)
     //manageConnectedSocket(socket);

	
}
public void cancel() {
    try {
        socket.close();
    } catch (IOException e) { }
}

}
