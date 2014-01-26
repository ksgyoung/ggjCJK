package com.cjk.thecloud.networking;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import com.cjk.thecloud.util.BluetoothUtils;

public class ConnectThread extends Thread {
    
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    protected static final int SUCCESS_CONNECT = 0;
    private Handler handler;
  
    public ConnectThread(BluetoothDevice device, Handler handler) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        this.handler = handler;
        mmDevice = device;
        Log.i("BLUETOOTH", "construct");
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(UUID.randomUUID());
            
        } catch (IOException e) { 
            Log.i("BLUETOOTH", "get socket failed");
             
        }
        mmSocket = tmp;
    }
  
    public void run() {
        // Cancel discovery because it will slow down the connection
        BluetoothUtils.getInstance().getBluetoothAdapter().cancelDiscovery();
        Log.i("BLUETOOTH", "connect - run");
        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();
            Log.i("BLUETOOTH", "connect - succeeded");
            Log.d("BLUETOOTH", "SERVER!!!!");
        } catch (IOException connectException) {    Log.i("BLUETOOTH", "connect failed");
            // Unable to connect; close the socket and get out
            try {
                mmSocket.close();
            } catch (IOException closeException) { }
            return;
        }
  
        // Do work to manage the connection (in a separate thread)
    
        handler.obtainMessage(SUCCESS_CONNECT, mmSocket).sendToTarget();
        
        
    }
  

    public void sendMessageToDevice(String message) {
    	OutputStream mmOutStream;
		try {
			mmOutStream = mmSocket.getOutputStream();
			mmOutStream.write(mmSocket.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}