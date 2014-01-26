package com.cjk.thecloud.networking;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.util.Log;

import com.cjk.thecloud.util.BluetoothUtils;

public class BluetoothServer extends Thread {
	private BluetoothServerSocket bluetoothSocket;
	private BluetoothAdapter bluetoothAdapter;
	
	public BluetoothServer() {
			
		try {
			BluetoothUtils bluetoothUtils = BluetoothUtils.getInstance();
			bluetoothSocket = bluetoothUtils.getBluetoothAdapter().listenUsingRfcommWithServiceRecord(Build.DEVICE, UUID.randomUUID());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		Log.d("BLUETOOTH", "Running server");
		BluetoothSocket socket = null;
		
		while(true) {
			Log.d("BLUETOOTH", "waiting...");
			try {
				socket = bluetoothSocket.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				break;
			}
		}
		
		//If connection was accepted
		if(socket != null) {
			//go to other thread
			Log.d("BLUETOOTH", "Connection was accepted");
			
			try {
				bluetoothSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void cancel() {
		try {
			bluetoothSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
